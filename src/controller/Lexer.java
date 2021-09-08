package controller;

import fileIO.Reader;
import domain.Lexeme;
import domain.TokenId;

public class Lexer {
    private Reader r;

    public Lexer(String fname) {
        this.r = new Reader(fname);
    }

    public Lexeme scanner() {
        char ch = this.r.next();
        if(ch != '\0') {
            System.out.print(ch);
            switch(ch) {
                case '(' : return new Lexeme(TokenId.TOPEN_BRACKET, "");
                case ')' : return new Lexeme(TokenId.TCLOSE_BRACKET, "");
                case 'e' :
                    if(this.beforeWhitespace() == "cho") {
                        return new Lexeme(TokenId.TECHO, "");
                    }
                case 'l' :
                    if(this.beforeWhitespace() == "ist_dir") {
                        return new Lexeme(TokenId.TLIST_DIR, "");
                    }
                case 'd' :
                    if(this.beforeWhitespace() == "el") {
                        return new Lexeme(TokenId.TDEL, "");
                    }
                case 'm' :
                    if(this.beforeWhitespace() == "ov") {
                        return new Lexeme(TokenId.TMOV, "");
                    }
                case 's' :
                    if(this.beforeWhitespace() == "how") {
                        return new Lexeme(TokenId.TSHOW, "");
                    }
                case '\"' :
                    return new Lexeme(TokenId.TVALUE, this.beforeDoubleQuotationMarks());
                default :
                    if(ch != ' ' && ch != '\t' && ch != '\n') {
                        return new Lexeme(TokenId.TERROR, "");
                    }
            }
        }
        return new Lexeme(TokenId.TNULL, "");
    }

	private String beforeWhitespace() {
		String res = "";
		char ch = this.r.next();
		while(ch != '\0' && ch != ' ' && ch != '\t' && ch != '\n') {
			res += ch;
            ch = this.r.next();
		}
		return res;
	}

    private String beforeDoubleQuotationMarks() {
		String res = "";
		char ch = this.r.next();
		while(ch != '\0' && ch != '\"') {
			res += ch;
            ch = this.r.next();
		}
		return res;
    }
}
