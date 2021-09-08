package controller;

import fileIO.Reader;
import domain.Lexeme;
import domain.TokenId;

public class Lexer {
    private Reader r;

    public Lexer(String fname) {
        this.r = new Reader(fname); // 해당 파일을 읽어들이는 Reader 객체를 생성
    }

    public Lexeme scanner() {
        char ch; // 읽어들인 문자 하나를 받는 변수
        while((ch = this.r.next()) != '\0') { // 널문자가 등장하지 않는 한 작동함
            switch(ch) {
                case '(' : return new Lexeme(TokenId.TOPEN_BRACKET, ""); // 열린 괄호일때
                case ')' : return new Lexeme(TokenId.TCLOSE_BRACKET, ""); // 닫힌괄호일때
                case 'e' :
                    if(this.getRestCommand().equals("cho")) { // e로 시작하는 명령어는 echo밖에 없으므로 나머지 문자열이 cho인지 확인
                        return new Lexeme(TokenId.TECHO, "");
                    }
                case 'l' :
                    if(this.getRestCommand().equals("ist_dir")) { // l로 시작하는 명령어는 list_dir밖에 없으므로 나머지 문자열이 ist_dir인지 확인
                        return new Lexeme(TokenId.TLIST_DIR, "");
                    }
                case 'd' :
                    if(this.getRestCommand().equals("el")) { // d로 시작하는 명령어는 del밖에 없으므로 나머지 문자열이 el인지 확인
                        return new Lexeme(TokenId.TDEL, "");
                    }
                case 'm' :
                    if(this.getRestCommand().equals("ov")) { // m으로 시작하는 명령어는 mov밖에 없으므로 나머지 문자열이 ov인지 확인
                        return new Lexeme(TokenId.TMOV, "");
                    }
                case 's' :
                    if(this.getRestCommand().equals("how")) { // s로 시작하는 명령어는 show밖에 없으므로 나머지 문자열이 how인지 확인
                        return new Lexeme(TokenId.TSHOW, "");
                    }
                case '\"' :
                    return new Lexeme(TokenId.TVALUE, this.beforeDoubleQuotationMarks()); // 큰따옴표로 시작했을 때에는 문자열이므로 다음 큰따옴표가 나올때까지 읽어서 토큰의 값으로 넣어줌
                default :
                    if(ch != ' ' && ch != '\t' && ch != '\n') { // 위의 경우에 모두 해당하지 않고 whitespace도 아닌 경우에는 문법 오류이므로 오류 토큰을 반환
                        return new Lexeme(TokenId.TERROR, "");
                    }
            }
        }
        return new Lexeme(TokenId.TNULL, ""); // 널문자가 등장했을 때는 널 토큰을 반환
    }

    public void closeReader() { // Parser 객체에서 파싱이 종료되었을때 파일을 닫기 위함
        this.r.close();
    }

	private String getRestCommand() { // 나머지 명령어 문자열을 갖고옴
		String res = "";
		char ch = this.r.next();
		while(ch != '\0' && ch != ' ' && ch != '\t' && ch != '\n' && ch != ')' && ch != '\"') { // whitespace뿐 아니라 괄호나 큰따옴표가 나오면 종료
			res += ch;
            ch = this.r.next();
		}
        if(ch == ')' || ch == '\"') { // 괄호나 큰따옴표의 경우에는 별도의 토큰으로 처리하기 위해 다시 안읽은 것으로 처리
            this.r.ungetc(ch);
        }
		return res;
	}

    private String beforeDoubleQuotationMarks() { // 문자열의 나머지 부분을 갖고옴
		String res = "";
		char ch = this.r.next();
		while(ch != '\0' && ch != '\"') { // 널문자나 큰따옴표가 등장할때까지 읽어들임
			res += ch;
            ch = this.r.next();
		}
		return res;
    }
}
