package controller;

import java.util.ArrayList;
import java.util.List;
import domain.*;

public class SemanticAnalyser {
    private Parser p;

    public SemanticAnalyser(String fname) {
        this.p = new Parser(fname); // Lexer 객체에서 Reader 객체를 생성하기 위한 파일 명을 전달해줌
    }

    /**
     * AST를 C코드로 바꾸는 함수
     * @return String
     */
    public String analyse() {
        ASTNode cur = p.parse(); // AST를 생성함
        if(cur == null) {
            return ""; // cur 이 null인 경우에는 에러가 있으므로 빈 문자열을 반환함
        }
        List<String> res = new ArrayList<String>(); // C언어에서 system함수에 들어갈 shell 명령어 배열
        String command = ""; // 생성된 shell 명령어
        while(true) {
            switch(cur.getSelf().getId()) {
                case TOPEN_BRACKET :
                case TCLOSE_BRACKET :
                cur = cur.getFirstChild(); // '(', ')'의 경우에는 첫번째 자식으로 현재 위치를 옮김
                break;

                case TECHO :
                case TSHOW :
                case TDEL :
                case TLIST_DIR : // mov가 아닌 명령어들에 대해서는
                command = getShellCommand(cur); // node에 대한 shell 명령어를 생성
                cur = cur.getParent().getSecondChild(); // ')'로 현재위치를 옮겨줌
                break;

                case TMOV : // mov일 경우에
                command = String.format("%s > %s", getShellCommand(cur.getFirstChild()), cur.getSecondChild().getSelf().getVal()); // 첫번째 자식을 이용해 shell 명령어를 만들고 그것을 두번째 자식의 Lexeme 값인 파일 경로에 redirect함
                cur = cur.getParent().getSecondChild(); // ')'로 현재위치를 옮겨줌
                break;

                case TERROR : // 혹시 TERROR가 탐지될 경우
                case TVALUE : // 정상적인 흐름에서는 TVALUE로 현재 위치가 옮겨지지 않는다
                return ""; // 비정상적인 AST

                case TNULL : // 트리의 끝에 다다른 경우
                return stringify(res); // shell명령어 배열을 C언어 코드로 바꾸고 반환
            }
            if(!command.isEmpty()) { // 저장할 shell 명령어가 있는 경우에는
                res.add(command); // 배열에 삽입
                command = ""; // 초기화
            }
        }
    }

    /**
     * 주어진 node와 그의 자식을을 이용해 shell 명령어를 만들어주는 함수
     * @param node
     * @return String
     */
    private String getShellCommand(ASTNode node) {
        String res = "";
        switch(node.getSelf().getId()) {
            case TECHO :
            res = String.format("echo '%s'", node.getFirstChild().getSelf().getVal());
            break;

            case TSHOW :
            res = String.format("cat %s", node.getFirstChild().getSelf().getVal());
            break;

            case TDEL :
            res = String.format("rm -rf %s", node.getFirstChild().getSelf().getVal());
            break;

            case TLIST_DIR :
            res = "ls -al";
            break;

            default :
        }
        return res;
    } 

    /**
     * shell 명령어 배열을 C코드로 변환해주는 함수
     * @param src
     * @return String
     */
    private String stringify(List<String> src) {
        String res = "#include <stdio.h>\n#include <stdlib.h>\n\nint main() {\n";
        for(String el : src) {
            res += String.format("\tsystem(\"%s\");\n", el);
        }
        return res + "\treturn 0;\n}";
    }
}
