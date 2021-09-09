package controller;

import domain.ASTNode;
import domain.Lexeme;
import domain.TokenId;

public class Parser {
    private Lexer l;

    public Parser(String fname) {
        this.l = new Lexer(fname); // Lexer 객체에서 Reader 객체를 생성하기 위한 파일 명을 전달해줌
    }

    public ASTNode parse() {
        Lexeme t; // Lexer 객체가 반환한 Lexeme 객체를 담기 위한 변수
        ASTNode root = new ASTNode();
        ASTNode cur = root;
        TokenId originTokenId = TokenId.TOPEN_BRACKET;
        while(true) {
            t = l.scanner(); // 토큰 하나를 읽어옴
            switch(t.getId()) {
                case TOPEN_BRACKET :
                case TCLOSE_BRACKET :
                originTokenId = TokenId.TOPEN_BRACKET;
                case TECHO :
                case TSHOW :
                case TDEL :
                cur.setSelf(t);
                cur.setFirstChild(new ASTNode());
                cur = cur.getFirstChild();
                break;

                case TLIST_DIR :
                cur.setSelf(t);
                ASTNode originOfListDir = findOriginNode(cur, originTokenId);
                originOfListDir.setSecondChild(new ASTNode());
                cur = originOfListDir.getSecondChild();
                break;

                case TVALUE :
                cur.setSelf(t);
                ASTNode originOfValue;
                originOfValue = cur.getParent().getSecondChild() == cur
                    ? findOriginNode(cur, TokenId.TOPEN_BRACKET)
                    : findOriginNode(cur, originTokenId);
                originOfValue.setSecondChild(new ASTNode());
                cur = originOfValue.getSecondChild();
                break;

                case TMOV :
                cur.setSelf(t);
                cur.setFirstChild(new ASTNode());
                cur = cur.getFirstChild();
                originTokenId = TokenId.TMOV;
                break;

                case TERROR :
                this.l.closeReader();
                return null;

                case TNULL :
                cur.setSelf(t);
                this.l.closeReader();
                return root;
            }
        }
    }

    private ASTNode findOriginNode(ASTNode cur, TokenId origin) {
        while(cur.getSelf().getId() != origin) {
            cur = cur.getParent();
        }
        return cur;
    }
}
