package listener.main;

import java.util.Hashtable;

import generated.MiniCParser;
import generated.MiniCParser.ExprContext;
import generated.MiniCParser.Fun_declContext;
import generated.MiniCParser.If_stmtContext;
import generated.MiniCParser.Local_declContext;
import generated.MiniCParser.ParamContext;
import generated.MiniCParser.ParamsContext;
import generated.MiniCParser.Type_specContext;
import generated.MiniCParser.Var_declContext;
import listener.main.SymbolTable;
import listener.main.SymbolTable.VarInfo;

public class BytecodeGenListenerHelper {
	
	// <boolean functions>
	// fun_decl	: type_spec IDENT '(' params ')' compound_stmt
	static boolean isFunDecl(MiniCParser.ProgramContext ctx, int i) {
		return ctx.getChild(i).getChild(0) instanceof MiniCParser.Fun_declContext;
	}
	
	// type_spec IDENT '[' ']'
	static boolean isArrayParamDecl(ParamContext param) {
		return param.getChildCount() == 4;
	}
	
	// global vars
	static int initVal(Var_declContext ctx) {
		return Integer.parseInt(ctx.LITERAL().getText());
	}

	// var_decl	: type_spec IDENT '=' LITERAL ';
	static boolean isDeclWithInit(Var_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}

	// var_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static boolean isArrayDecl(Var_declContext ctx) {
		return ctx.getChildCount() == 6;
	}

	// <local vars>
	// local_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static int initVal(Local_declContext ctx) {
		return Integer.parseInt(ctx.LITERAL().getText());
	}

	static boolean isArrayDecl(Local_declContext ctx) {
		return ctx.getChildCount() == 6;
	}
	
	static boolean isDeclWithInit(Local_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}
	
	static boolean isVoidF(Fun_declContext ctx) {
		// <(0) Fill in>
		return getTypeText(ctx.type_spec()).equals("void");
	}
	
	static boolean isIntReturn(MiniCParser.Return_stmtContext ctx) {
		return ctx.getChildCount() ==3;
	}

	static boolean isVoidReturn(MiniCParser.Return_stmtContext ctx) {
		return ctx.getChildCount() == 2;
	}
	
	// <information extraction>
	static String getStackSize(Fun_declContext ctx) {
		return "32";
	}
	static String getLocalVarSize(Fun_declContext ctx) {
		return "32";
	}

	static String getTypeText(Type_specContext typespec) {
		// <(1) Fill in>
		return typespec.getText();
	}

	// params
	static String getParamName(ParamContext param) {
		// <(2) Fill in>
		return param.IDENT().getText();
	}
	
	static String getParamTypesText(ParamsContext params) {
		String typeText = "";
		
		for(int i = 0; i < params.param().size(); i++) {
			MiniCParser.Type_specContext typespec = (MiniCParser.Type_specContext)  params.param(i).getChild(0);
			typeText += getTypeText(typespec);
			if(i + 1 != params.param().size()) {
				typeText += ", ";
			}
		}
		return typeText;
	}
	
	static String getLocalVarName(Local_declContext local_decl) {
		// <(3) Fill in>
		return local_decl.IDENT().getText();
	}
	
	static String getFunName(Fun_declContext ctx) {
		// <(4) Fill in>
		return ctx.IDENT().getText();
	}
	
	static String getFunName(ExprContext ctx) {
		// <(5) Fill in>
		return ctx.IDENT().getText();
	}
	
	static boolean noElse(If_stmtContext ctx) {
		return ctx.getChildCount() < 5;
	}
	
	static String getClassProlog() {
		// <(6) Fill in>
		return ".class public Test" + "\n"
			+ ".super java/lang/Object" + "\n"
			+ ".method public <init>()V" + "\n"
			+ "\t" + "aload_0"
			+ "\t" + "invokenonvirtual java/lang/Object/<init>()" + "\n"
			+ "\t" + "return" + "\n"
			+ ".end method" + "\n";
	}
	
	static String getCurrentClassName() {
		return "Test";
	}
}
