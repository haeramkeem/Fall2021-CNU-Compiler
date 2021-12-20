package main;

import gen.MiniCBaseListener;
import gen.MiniCParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MiniCPrintListener extends MiniCBaseListener {
    // 인덴트 Level을 체크하기 위한 프로퍼티
    private int level = 0;
    // 결과를 트리구조로 저장하기 위한 프로퍼티
    private ParseTreeProperty<String> newTexts = new ParseTreeProperty<String>();

    /**
     * 입력받은 인덴트 레벨로 상응하는 문자열 형식의 인덴트를 반환함
     * @param level 인텐트 레벨
     * @return String 인덴트 레벨에 따라 변환된 문자열
     */
    private String indent(int level) {
        String acc = "";
        for(int i = 0; i < level; i++) {
            acc += "....";
        }
        return acc;
    }

    // 파싱 종료 이후 결과를 파일에 출력하기 위한 부분
    @Override public void exitProgram(MiniCParser.ProgramContext ctx) {
        String program = "";

        for (int i = 0; i < ctx.getChildCount(); i++) {
            newTexts.put(ctx, ctx.decl(i).getText()); //ParseTree인 newText에 decl을 넣음
            program += newTexts.get(ctx.getChild(i)); //ctx의 child에 들어갔다가 나오면서 출력
        }

        System.out.println(program);
        File file = new File(String.format("[HW3]201702004.c"));

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(program);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // decl의 파싱이 완료되었을 때
    @Override public void exitDecl(MiniCParser.DeclContext ctx) {
        String str = null;
        if(ctx.var_decl() != null) {
            // decl : var_decl 생성규칙 처리부
            str = newTexts.get(ctx.var_decl());

        } else if(ctx.fun_decl() != null) {
            // decl : fun_decl 생성규칙 처리부
            str = newTexts.get(ctx.fun_decl());
        }

        // 처리된 decl을 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitVar_decl(MiniCParser.Var_declContext ctx) {
        // 변수선언에서의 타입선언 처리 결과를 얻어옴
        String str = newTexts.get(ctx.type_spec());

        // 식별자 이름을 얻어옴
        str += " " + ctx.IDENT().getText();

        switch(ctx.getChildCount()) {

            // var_decl	: type_spec IDENT '=' LITERAL ';' 생성규칙 처리부
            case 5 : str += " = " + ctx.LITERAL().getText(); break;

            // var_decl	: type_spec IDENT '[' LITERAL ']' ';' 생성규칙 처리부
            case 6 : str += "[" + ctx.LITERAL().getText() + "]"; break;
        }

        // 처리한 var_decl을 트리에 삽입함
        newTexts.put(ctx, str + ";\n");
    }

    @Override public void exitType_spec(MiniCParser.Type_specContext ctx) {
        String str = null;
        if(ctx.INT() != null) {
            // type_spec : INT 생성규칙 처리부
            str = ctx.INT().getText();

        } else if(ctx.VOID() != null) {
            // type_spec : VOID 생성규칙 처리부
            str = ctx.VOID().getText();
        }

        // 처리한 type_spec을 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitFun_decl(MiniCParser.Fun_declContext ctx) {
        // fun_decl	: type_spec IDENT '(' params ')' compound_stmt 생성규칙을 처리함
        String str = newTexts.get(ctx.type_spec());
        str += " " + ctx.IDENT().getText();
        str += "(" + newTexts.get(ctx.params()) + ")";
        str += newTexts.get(ctx.compound_stmt());

        // 처리한 fun_decl을 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitParams(MiniCParser.ParamsContext ctx) {
        String str = "";
        if(ctx.param(0) != null) {
            // params : param (',' param)* 생성규칙 처리부
            str += newTexts.get(ctx.param(0));

            // 연달아 등장할 수 있는 param들도 같이 처리해줌
            for(int i = 1; i < ctx.param().size(); i++) {
                str += ", " + newTexts.get(ctx.param(i));
            }

        } else if(ctx.VOID() != null) {
            // params : VOID 생성규칙 처리부
            str += ctx.VOID().getText();
        }

        // 처리한 params를 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitParam(MiniCParser.ParamContext ctx) {
        // param : type_spec IDENT 생성규칙 처리부
        String str = newTexts.get(ctx.type_spec());
        str += " " + ctx.IDENT().getText();

        // param : type_spec IDENT '[' ']' 생성규칙에 대해서는 추가적인 처리를 해줌
        if(ctx.getChildCount() == 4) {
            str += "[]";
        }

        // 처리된 param을 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitStmt(MiniCParser.StmtContext ctx) {
        String str = null;
        if(ctx.expr_stmt() != null) {
            // stmt : expr_stmt 생성규칙 처리부
            str = newTexts.get(ctx.expr_stmt());

        } else if(ctx.compound_stmt() != null) {
            // stmt : compound_stmt 생성규칙 처리부
            str = newTexts.get(ctx.compound_stmt());

        } else if(ctx.if_stmt() != null) {
            // stmt : if_stmt 생성규칙 처리부
            str = newTexts.get(ctx.if_stmt());

        } else if(ctx.while_stmt() != null) {
            // stmt : while_stmt 생성규칙 처리부
            str = newTexts.get(ctx.while_stmt());

        } else if(ctx.return_stmt() != null) {
            // stmt : return_stmt 생성규칙 처리부
            str = newTexts.get(ctx.return_stmt());
        }

        // 처리한 stmt를 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitExpr_stmt(MiniCParser.Expr_stmtContext ctx) {
        // expr_stmt : expr ';' 생성규칙 처리부
        String str = newTexts.get(ctx.expr()) + ";\n";

        // 처리한 expr_stmt를 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitWhile_stmt(MiniCParser.While_stmtContext ctx) {
        // while_stmt : WHILE '(' expr ')' stmt 생성규칙 처리부
        String str = ctx.WHILE().getText();
        str += " (" + newTexts.get(ctx.expr());
        str += ") " + newTexts.get(ctx.stmt());

        // 처리한 while_stmt를 트리에 삽입함
        newTexts.put(ctx, str);
    }

    // compound_stmt가 시작되면 인덴트 레벨을 하나 올려줌
    @Override public void enterCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
        this.level++;
    }

    @Override public void exitCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
        // 여는 중괄호의 경우에는 하나 낮은 레벨의 인덴트가 적용되므로 1을 감소시킨 인덴트 레벨로 인덴트를 채움
        String str = "\n" + this.indent(this.level - 1) + "{\n";

        // 중괄호 이후에 나올 수 있는 local_decl들을 처리함
        for(int i = 0; i < ctx.local_decl().size(); i++) {

            // 현재 레벨에서의 인덴트를 채운 뒤 local_decl을 처리함
            str += this.indent(this.level) + newTexts.get(ctx.local_decl(i));
        }

        // 중괄호 이후에 나올 수 있는 stmt들을 처리함
        for(int i = 0; i < ctx.stmt().size(); i++) {

            // 현재 레벨에서의 인덴트를 채운 뒤 stmt를 처리함
            str += this.indent(this.level) + newTexts.get(ctx.stmt(i));
        }

        // 닫는 중괄호의 경우에도 하나 낮은 인덴트 레베리 적용되므로 1을 감소시킨 인덴트 레벨로 인덴트를 채움
        str += this.indent(this.level - 1) + "}\n";

        // 처리한 compound_stmt를 트리에 삽입함
        newTexts.put(ctx, str);

        // 인덴트 레벨을 1 감소시킴
        this.level--;
    }

    @Override public void exitLocal_decl(MiniCParser.Local_declContext ctx) {
        // 공통부분인 type_spec IDENT를 처리함
        String str = newTexts.get(ctx.type_spec());
        str += " " + ctx.IDENT().getText();

        switch(ctx.getChildCount()) {
            // local_decl : type_spec IDENT '=' LITERAL ';' 생성규칙 처리부
            case 5 : str += " = " + ctx.LITERAL().getText(); break;

            // local_decl : type_spec IDENT '[' LITERAL ']' ';' 생성규칙 처리부
            case 6 : str += "[" + ctx.LITERAL().getText() + "]"; break;
        }

        // 처리한 local_decl을 트리에 삽입함
        newTexts.put(ctx, str + ";\n");
    }

    @Override public void exitIf_stmt(MiniCParser.If_stmtContext ctx) {
        // 공통부분인 if (expr) 부분을 처리함
        String str = ctx.IF().getText();
        str += " (" + newTexts.get(ctx.expr());
        str += ") " + newTexts.get(ctx.stmt(0));

        // 뒤이어 else문이 등장하는 경우를 처리함
        if(ctx.stmt(1) != null && ctx.ELSE() != null) {
            str += " " + ctx.ELSE().getText();
            str += " " + newTexts.get(ctx.stmt(1));
        }

        // 처리한 if_stmt를 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) {
        // 반환값이 없는 경우
        String str = ctx.RETURN().getText();

        // 반환값이 있는 경우
        if(ctx.expr() != null) {
            str += " " + newTexts.get(ctx.expr());
        }

        // 처리한 return_stmt를 트리에 삽입함
        newTexts.put(ctx, str + ";\n");
    }

    @Override public void exitExpr(MiniCParser.ExprContext ctx) {
        String str = "";
        // 자식의 갯수를 이용해 분류함
        switch (ctx.getChildCount()) {
            case 1 :
                // 자식이 1개인 경우 : literal이나 ident
                if(ctx.LITERAL() != null) {

                    // literal인 경우 처리부
                    str += ctx.LITERAL().getText();
                } else if(ctx.IDENT() != null) {

                    // ident인 경우 처리부
                    str += ctx.IDENT().getText();
                }
                break;

            case 2 :
                // 자식이 2개인 경우 : 단항연산자
                str += ctx.getChild(0).getText();
                str += newTexts.get(ctx.expr(0));
                break;

            case 3 :
                // 자식이 3개인 경우 : 이항연산자이거나 변수에 값을 할당하는 구문, 혹은 소괄호 구문
                if(ctx.getChild(1) != ctx.expr()) {
                    if(ctx.IDENT() == null) {

                        // 이항연산자인 경우
                        str += newTexts.get(ctx.expr(0));
                        str += " " + ctx.getChild(1).getText();
                        str += " " + newTexts.get(ctx.expr(1));

                    } else  {
                        // 변수에 값을 할당하는 구문
                        str += ctx.IDENT().getText();
                        str += " " + ctx.getChild(1).getText();
                        str += " " + newTexts.get(ctx.expr(0));
                    }
                } else {

                    // 소괄호 구문인 경우
                    str += "(" + ctx.expr() + ")";
                }
                break;

            case 4 :
                // 자식이 4개인 경우 : 배열 인덱스 참조 구문이거나 함수 호출구문
                str += ctx.IDENT().getText();
                if(ctx.expr(0) != null) {
                    // 배열 인덱스 참조 구문일 경우
                    str += "[" + newTexts.get(ctx.expr(0)) + "]";

                } else if(ctx.args() != null) {
                    // 함수 호출 구문인 경우
                    str += "(" + ctx.args().getText() + ")";
                }
                break;

            case 6 :
                // 자식이 6개인 경우 : 배열의 특정 인덱스에 값을 할당하는 구문
                str += ctx.IDENT().getText();
                str += "[" + newTexts.get(ctx.expr(0)) + "]";
                str += " = " + newTexts.get(ctx.expr(1));
                break;
        }

        // 처리한 expr을 트리에 삽입함
        newTexts.put(ctx, str);
    }

    @Override public void exitArgs(MiniCParser.ArgsContext ctx) {
        String str = "";
        // args	: expr (',' expr)* 생성규칙에 대한 처리부
        if(ctx.expr(0) != null) {

            // 처음 등장하는 expr를 처리함
            str += newTexts.get(ctx.expr(0));

            // 뒤이어 등장할 수 있는 expr들을 처리함
            for(int i = 1; i < ctx.expr().size(); i++) {
                str += ", " + newTexts.get(ctx.expr(i));
            }
        }
        newTexts.put(ctx, str);
    }
}
