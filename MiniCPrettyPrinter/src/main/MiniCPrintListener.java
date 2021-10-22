package main;

import gen.MiniCBaseListener;
import gen.MiniCParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MiniCPrintListener extends MiniCBaseListener {
    private int level = 0;
    private int space = 0;
    private int nline = 0;
    private String res = "";

    private void printIndent() {
        this.res += "\n";
        for(int i = 0; i < this.level; i++) {
            this.res += "....";
        }
    }

    private boolean isBinaryOperation(MiniCParser.ExprContext ctx) {
        return ctx.getChildCount() == 3 && (ctx.getChild(1) != ctx.expr());
    }

    @Override public void enterProgram(MiniCParser.ProgramContext ctx) {
        this.nline = ctx.getChildCount() - 1;
    }

    @Override
    public void exitProgram(MiniCParser.ProgramContext ctx) {
        File file = new File(String.format("[HW3]201702004.c")); // 본인 학번으로 변경해주세요.
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(this.res);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void enterDecl(MiniCParser.DeclContext ctx) { }

    @Override public void exitDecl(MiniCParser.DeclContext ctx) {
        if(this.nline > 0) {
            this.res += "\n";
            this.nline--;
        }
    }

    @Override public void enterVar_decl(MiniCParser.Var_declContext ctx) {
        if(ctx.getChildCount() == 5) {
            this.space += 2;
        }
    }

    @Override public void exitVar_decl(MiniCParser.Var_declContext ctx) { }

    @Override public void enterType_spec(MiniCParser.Type_specContext ctx) {
        this.space++;
    }

    @Override public void exitType_spec(MiniCParser.Type_specContext ctx) { }

    @Override public void enterFun_decl(MiniCParser.Fun_declContext ctx) { }

    @Override public void exitFun_decl(MiniCParser.Fun_declContext ctx) { }

    @Override public void enterParams(MiniCParser.ParamsContext ctx) { }

    @Override public void exitParams(MiniCParser.ParamsContext ctx) { }

    @Override public void enterParam(MiniCParser.ParamContext ctx) { }

    @Override public void exitParam(MiniCParser.ParamContext ctx) { }

    @Override public void enterStmt(MiniCParser.StmtContext ctx) { }

    @Override public void exitStmt(MiniCParser.StmtContext ctx) { }

    @Override public void enterExpr_stmt(MiniCParser.Expr_stmtContext ctx) {
        this.printIndent();
    }

    @Override public void exitExpr_stmt(MiniCParser.Expr_stmtContext ctx) { }

    @Override public void enterWhile_stmt(MiniCParser.While_stmtContext ctx) {
        this.space++;
        this.printIndent();
    }

    @Override public void exitWhile_stmt(MiniCParser.While_stmtContext ctx) { }

    @Override public void enterCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
        this.printIndent();
        this.level++;
    }

    @Override public void exitCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
    }

    @Override public void visitErrorNode(ErrorNode node) { }

    @Override public void enterLocal_decl(MiniCParser.Local_declContext ctx) {
        if(ctx.getChildCount() == 5) {
            this.space += 2;
        }
        this.printIndent();
    }

    @Override public void exitLocal_decl(MiniCParser.Local_declContext ctx) { }

    @Override public void enterIf_stmt(MiniCParser.If_stmtContext ctx) {
        this.space++;
        this.printIndent();
    }

    @Override public void exitIf_stmt(MiniCParser.If_stmtContext ctx) { }

    @Override public void enterReturn_stmt(MiniCParser.Return_stmtContext ctx) { }

    @Override public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) { }

    @Override public void enterExpr(MiniCParser.ExprContext ctx) {
        if(this.isBinaryOperation(ctx)) {
            this.space += 2;
        }
    }

    @Override public void exitExpr(MiniCParser.ExprContext ctx) { }

    @Override public void enterArgs(MiniCParser.ArgsContext ctx) { }

    @Override public void exitArgs(MiniCParser.ArgsContext ctx) { }

    @Override public void enterEveryRule(ParserRuleContext ctx) { }

    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    
    @Override public void visitTerminal(TerminalNode node) {
        switch(node.getText()) {
            case "}" :
                this.level--;
                this.printIndent();
                break;
        }
        this.res += node.getText();
        if(this.space > 0) {
            this.res += " ";
            this.space--;
        }
    }
}
