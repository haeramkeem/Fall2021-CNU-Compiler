package controller;

import domain.Lexeme;

public class Parser {
    private Lexer l;

    public Parser(String fname) {
        this.l = new Lexer(fname); // Lexer 객체에서 Reader 객체를 생성하기 위한 파일 명을 전달해줌
    }

    public String parse() {
        Lexeme t; // Lexer 객체가 반환한 Lexeme 객체를 담기 위한 변수
        String res = "#include <stdio.h>\n#include <stdlib.h>\n\nint main() {"; // 기본 템플릿
        boolean isMov = false; // mov 명령어의 경우 처리 방식이 달라지기 때문에 mov명령어의 등장을 기록하기 위한 플래그
        while(true) {
            t = l.scanner(); // 토큰 하나를 읽어옴
            switch(t.getId()) {
                case TOPEN_BRACKET :
                    res += "\n\t"; // 열린 괄호이면 줄바꿈을 하고 인덴트를 넣어줌
                    break;
                case TCLOSE_BRACKET :
                    res += "\");"; // 닫힌 괄호이면 명령어가 종료된 것이므로 큰따옴표와 괄호를 닫아주고 세미콜론을 찍어줌
                    isMov = false; // isMov 플래그를 다시 초기화
                    break;
                case TECHO :
                    res += isMov ? "echo \"" : "printf(\""; // mov 명령어에 의해 호출되는 echo 명령어이면 bash 명령어 echo를 실행하기 위해 echo를 입력하고 아니라면 printf를 준비함
                    break;
                case TLIST_DIR :
                    res += (isMov ? "" : "system(\"") + "ls -al "; // mov 명령어에 의해 호출되지 않을 때에만 c의 system함수를 호출하여 ls -al 명령어를 처리
                    break;
                case TDEL :
                    res += (isMov ? "" : "system(\"") + "rm -rf "; // mov 명령어에 의해 호출되지 않을 때에만 c의 system함수를 호출하여 rm -rf 명령어를 처리
                    break;
                case TMOV :
                    res += "system(\""; // 그 다음에 올 명령어를 실행하기 위해 system함수를 준비
                    isMov = true; // mov 명령어가 등장하였으므로 플래그를 올려줌
                    break;
                case TSHOW :
                    res += (isMov ? "" : "system(\"") + "cat "; // mov 명령어에 의해 호출되지 않을 때에만 c의 system함수를 호출하여 cat 명령어를 처리
                    break;
                case TVALUE :
                    res += (isMov ? "> " : "") + t.getVal(); // mov 명령어에 의해 호출되었을때는 > 를 통해 redirection시킴. 아닐 경우에는 값 그대로 준비
                    break;
                case TERROR :
                    l.closeReader();
                    return ""; // 문법 에러가 났을 경우에는 reader를 닫고 빈 문자열을 반환
                case TNULL :
                    l.closeReader();
                    return res + "\n\treturn 0;\n}"; // 정상적일 경우에는 main함수의 종료를 위한 코드를 삽입
            }
        }
    }
}
