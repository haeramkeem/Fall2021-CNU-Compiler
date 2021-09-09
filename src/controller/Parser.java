package controller;

import domain.ASTNode;
import domain.Lexeme;
import domain.TokenId;

public class Parser {
    private Lexer l;

    public Parser(String fname) {
        this.l = new Lexer(fname); // Lexer 객체에서 Reader 객체를 생성하기 위한 파일 명을 전달해줌
    }

    /**
     * Parse.parse() 출력 AST 예시
     * 
     * 입력된 .hf 파일의 내용
     * (echo "Hello world!")
     * 
     * 출력 AST
     * <TOPEN_BRACKET, "">
     *          | ------------------- |
     *      <TECHO, "">         <TCLOSE_BRACKET, "">
     *          |
     * <TVALUE, "Hello world!">
     */
    public ASTNode parse() {
        Lexeme t; // Lexer 객체가 반환한 Lexeme 객체를 담기 위한 변수
        ASTNode root = new ASTNode(); // 루트 노드
        ASTNode cur = root; // iterate 하기 위한 현재 노드

        /**
         * origin은 명령어를 context에 대한 정보임
         *      만약 (echo "abc") 처럼 '(' 이후 바로 명령어가 등장한다면, originTokenId는 TOPEN_BRACKET이 됨
         *      하지만 만일 (mov echo "abc" "text.txt") 처럼 mov 명령어 이후에 등장하는 명령어의 경우에는 originTokenId는 TMOV가 됨
         */
        TokenId originTokenId = TokenId.TOPEN_BRACKET;
        while(true) {
            t = l.scanner(); // 토큰 하나를 읽어옴
            switch(t.getId()) {
                case TOPEN_BRACKET :
                case TCLOSE_BRACKET :
                originTokenId = TokenId.TOPEN_BRACKET; // 괄호가 여닫힐때는 originTokenId를 초기화함
                case TECHO :
                case TSHOW :
                case TDEL :
                cur.setSelf(t); // 현재 위치의 Lexeme을 저장함
                cur.setFirstChild(new ASTNode(cur)); // 현재 위치의 첫번째 자식을 parent를 자신으로 설정해준 상태에서 생성함
                cur = cur.getFirstChild(); // 현재 위치를 첫번째 자식으로 옮김
                break;

                case TLIST_DIR :
                cur.setSelf(t); // 현재 위치의 Lexeme을 저장함
                ASTNode originOfListDir = findOriginNode(cur, originTokenId); // list_dir의 경우에는 더 이상 받을 인자가 없으므로 명령어가 호출된 context를 찾음
                originOfListDir.setSecondChild(new ASTNode(originOfListDir)); // context의 두번째 자식을 생성하고 부모를 연결해줌. 두번째 자식인 경우에는 context가 '('라면 ')'이 나올 것을 기대하기 때문이고, mov이면 파일 저장 위치가 나올 것을 기대하기 때문임
                cur = originOfListDir.getSecondChild(); // context의 두번째 자식으로 현재 위치를 옮겨줌
                break;

                case TVALUE :
                cur.setSelf(t); // 현재 위치의 Lexeme을 저장함
                ASTNode originOfValue = cur.getParent().getSecondChild() == cur
                    ? findOriginNode(cur, TokenId.TOPEN_BRACKET) // 현재위치의 부모의 두번째 자식이 자기 자신이라면, 자신이 두번째 자식인 것이고, value가 두번째 자식에 들어가는 경우는 mov 명령어의 파일 저장 위치이기 때문에 mov 명령어가 종료된 것을 알 수 있음. 따라서 TOPEN_BRACKET을 이용해 context를 찾게 된다.
                    : findOriginNode(cur, originTokenId); // 그렇지 않다면, 저장된 originTokenId를 이용해 context를 찾음
                originOfValue.setSecondChild(new ASTNode(originOfValue)); // TLIST_DIR와 같은 이유로 두번째 자식으로 현재 위치를 옮기기 위해 객체를 생성하고 부모를 연결해줌
                cur = originOfValue.getSecondChild(); // context의 두번째 자식으로 현재 위치를 옮겨줌
                break;

                case TMOV :
                cur.setSelf(t); // 현재 위치의 Lexeme을 저장함
                cur.setFirstChild(new ASTNode(cur)); // 현재 위치의 첫번째 자식을 parent를 자신으로 설정해준 상태에서 생성함
                cur = cur.getFirstChild(); // 현재 위치를 첫번째 자식으로 옮김
                originTokenId = TokenId.TMOV; // context가 mov가 나온 상황이므로 originTokenId를 TMOV로 바꿔준다
                break;

                case TERROR :
                this.l.closeReader(); // 파일 닫고
                return null; // null을 반환

                case TNULL :
                cur.setSelf(t); // TNULL을 저장해 트리가 종요되었음을 알리고
                this.l.closeReader(); // 파일 닫고
                return root; // AST트리의 root를 반환함
            }
        }
    }

    /**
     * 현재 위치에서 주어진 TokenId를 이용해 context node를 찾는 함수
     */
    private ASTNode findOriginNode(ASTNode cur, TokenId origin) {
        while(cur.getSelf().getId() != origin) {
            cur = cur.getParent();
        }
        return cur;
    }
}
