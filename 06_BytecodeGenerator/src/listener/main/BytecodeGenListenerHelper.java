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
	static int initVal(Local_declContext ctx) {
		return Integer.parseInt(ctx.LITERAL().getText());
	}

	// local_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static boolean isArrayDecl(Local_declContext ctx) {
		return ctx.getChildCount() == 6;
	}
	
	static boolean isDeclWithInit(Local_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}

	// Check return type of function is void
	static boolean isVoidF(Fun_declContext ctx) {
		// <(0) Fill in>
		return getTypeText(ctx.type_spec()).equals("V");
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

	//	Get jasmine-like type text
	//		int	=> I
	//		void => V
	static String getTypeText(Type_specContext typespec) {
		// <(1) Fill in>
		switch(typespec.getText()) {
			case "int": return "I";
			case "void": return "V";
		}
		return "";
	}

	// Get name of the parameter
	static String getParamName(ParamContext param) {
		// <(2) Fill in>
		return param.IDENT().getText();
	}
	
	static String getParamTypesText(ParamsContext params) {
		String typeText = "";
		
		for(int i = 0; i < params.param().size(); i++) {
			MiniCParser.Type_specContext typespec = (MiniCParser.Type_specContext)  params.param(i).getChild(0);
			typeText += getTypeText(typespec) + ";";
		}
		return typeText;
	}

	// Get name of the local variable
	static String getLocalVarName(Local_declContext local_decl) {
		// <(3) Fill in>
		return local_decl.IDENT().getText();
	}

	// Get name of the function from function declaration
	static String getFunName(Fun_declContext ctx) {
		// <(4) Fill in>
		return ctx.IDENT().getText();
	}

	// Get name of the function from function call
	static String getFunName(ExprContext ctx) {
		// <(5) Fill in>
		return ctx.IDENT().getText();
	}
	
	static boolean noElse(If_stmtContext ctx) {
		return ctx.getChildCount() == 5;
	}

	// Get class prolog
	static String getClassProlog() {
		// <(6) Fill in>
		return ".class public Test" + "\n"
			+ ".super java/lang/Object" + "\n"
			+ ".method public <init>()V" + "\n"
			+ "\t" + "aload_0"
			+ "\t" + "invokenonvirtual java/lang/Object/<init>()V" + "\n"
			+ "\t" + "return" + "\n"
			+ ".end method" + "\n";
	}
	
	static String getCurrentClassName() {
		return "Test";
	}
}
