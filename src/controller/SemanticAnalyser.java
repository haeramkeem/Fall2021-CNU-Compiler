package controller;

import java.util.ArrayList;
import java.util.List;
import domain.*;

public class SemanticAnalyser {
    private Parser p;

    public SemanticAnalyser(String fname) {
        this.p = new Parser(fname);
    }

    public String analyse() {
        ASTNode cur = p.parse();
        if(cur == null) {
            return "";
        }
        List<String> res = new ArrayList<String>();
        String toIns = "";
        while(true) {
            switch(cur.getSelf().getId()) {
                case TOPEN_BRACKET :
                case TCLOSE_BRACKET :
                cur = cur.getFirstChild();
                break;

                case TECHO :
                case TSHOW :
                case TDEL :
                case TLIST_DIR :
                toIns = getString(cur);
                cur = cur.getParent().getSecondChild();
                break;

                case TMOV :
                toIns = String.format("%s > %s", getString(cur.getFirstChild()), cur.getSecondChild().getSelf().getVal());
                cur = cur.getParent().getSecondChild();
                break;

                case TERROR :
                case TVALUE :
                return "";

                case TNULL :
                return stringify(res);
            }
            if(!toIns.isEmpty()) {
                res.add(toIns);
                toIns = "";
            }
        }
    }

    private String getString(ASTNode node) {
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

    private String stringify(List<String> src) {
        String res = "#include <stdio.h>\n#include <stdlib.h>\n\nint main() {\n";
        for(String el : src) {
            res += String.format("\tsystem(\"%s\");\n", el);
        }
        return res + "\treturn 0;\n}";
    }
}

// (echo “Hello world!”)                    system("echo 'Hello world'");
// (list_dir)                               system("ls -al");
// (echo “delete test.txt”)                 system("echo 'delete test.txt'");
// (del “test.txt”)                         system("rm -rf text.txt");
// (mov list_dir “res”)                     system("ls -al > res");
// (echo “*********test.txt********”)       system("echo '*********test.txt********'");
// (show “test.txt”)                        system("cat test.txt");
