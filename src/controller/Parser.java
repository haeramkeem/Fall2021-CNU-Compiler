package controller;

import domain.Lexeme;

public class Parser {
    private Lexer l;

    public Parser(String fname) {
        this.l = new Lexer(fname);
    }

    public String parse() {
        Lexeme t;
        String res = "#include <stdio.h>\n#include <stdlib.h>\n\nint main() {\n";
        boolean isMov = false;
        while(true) {
            t = l.scanner();
            switch(t.getId()) {
                case TOPEN_BRACKET :
                    res += "\n";
                    break;
                case TCLOSE_BRACKET :
                    res += "\");";
                    isMov = false;
                    break;
                case TECHO :
                    res += isMov ? "echo \"" : "printf(\"";
                    break;
                case TLIST_DIR :
                    res += (isMov ? "" : "system(\"") + "ls -al";
                    break;
                case TDEL :
                    res += (isMov ? "" : "system(\"") + "rm -rf";
                    break;
                case TMOV :
                    res += "system(\"";
                    isMov = true;
                    break;
                case TSHOW :
                    res += (isMov ? "" : "system(\"") + "cat";
                    break;
                case TVALUE :
                    res += (isMov ? ">" : "") + t.getVal();
                    break;
                case TERROR :
                    return "";
                case TNULL :
                    return res;
            }
        }
    }
}
