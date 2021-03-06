package listener.main;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import generated.MiniCParser;
import generated.MiniCParser.Fun_declContext;
import generated.MiniCParser.Local_declContext;
import generated.MiniCParser.ParamsContext;
import generated.MiniCParser.Type_specContext;
import generated.MiniCParser.Var_declContext;
import listener.main.SymbolTable.Type;

import javax.xml.validation.Validator;

import static listener.main.BytecodeGenListenerHelper.*;


public class SymbolTable {
	enum Type {
		INT, INTARRAY, VOID, ERROR
	}
	
	static public class VarInfo {
		Type type; 
		int id;
		int initVal;
		
		public VarInfo(Type type,  int id, int initVal) {
			this.type = type;
			this.id = id;
			this.initVal = initVal;
		}
		public VarInfo(Type type,  int id) {
			this.type = type;
			this.id = id;
			this.initVal = 0;
		}
	}
	
	static public class FInfo {
		public String sigStr;
	}
	
	private Map<String, VarInfo> _lsymtable = new HashMap<>();	// local v.
	private Map<String, VarInfo> _gsymtable = new HashMap<>();	// global v.
	private Map<String, FInfo> _fsymtable = new HashMap<>();	// function 
	
		
	private int _globalVarID = 0;
	private int _localVarID = 0;
	private int _labelID = 0;
	private int _tempVarID = 0;
	
	SymbolTable(){
		initFunDecl();
		initFunTable();
	}
	
	void initFunDecl(){		// at each func decl
		_localVarID = 0;
		_labelID = 0;
		_tempVarID = 32;		
	}

	// Put variable into local symbol table
	void putLocalVar(String varname, Type type){
		//<(0) Fill here>

		// Use Initializer instead of `putLocalVarWithInitVal`
		VarInfo var = new VarInfo(type, _localVarID++);
		_lsymtable.put(varname, var);
	}

	// Put variable into global symbol table
	void putGlobalVar(String varname, Type type){
		//<(1) Fill here>

		// Use Initializer instead of `putGlobalVarWithInitVal`
		VarInfo var = new VarInfo(type, _globalVarID++);
		_gsymtable.put(varname, var);
	}

	// Put variable into local symbol table with init value
	void putLocalVarWithInitVal(String varname, Type type, int initVar){
		//<(2) Fill here>
		VarInfo var = new VarInfo(type, _localVarID++, initVar);
		_lsymtable.put(varname, var);
	}

	// Put variable into global symbol table with init value
	void putGlobalVarWithInitVal(String varname, Type type, int initVar){
		//<(3) Fill here>
		VarInfo var = new VarInfo(type, _globalVarID++, initVar);
		_gsymtable.put(varname, var);
	}

	// Put parameters into local symbol table
	void putParams(MiniCParser.ParamsContext params) {
		for(int i = 0; i < params.param().size(); i++) {
			//<(4) Fill here>

			// Use BytecodeGenListenerHelper's Method to get param name
			String varname = getParamName(params.param(i));
			Type vartype = null;

			// Determine type of parameter
			//		`type_spec` of param must be int
			if(getTypeText(params.param(i).type_spec()).equals("I")) {
				if(isArrayParamDecl(params.param(i))) {
					vartype = Type.INTARRAY;
				} else {
					vartype = Type.INT;
				}
			}

			putLocalVar(varname, vartype);
		}
	}
	
	private void initFunTable() {
		FInfo printlninfo = new FInfo();
		printlninfo.sigStr = "java/io/PrintStream/println(I)V";
		
		FInfo maininfo = new FInfo();
		maininfo.sigStr = "main([Ljava/lang/String;)V";
		_fsymtable.put("_print", printlninfo);
		_fsymtable.put("main", maininfo);
	}

	// Get specification of function by function name
	public String getFunSpecStr(String fname) {		
		// <(5) Fill here>
		return _fsymtable.get(fname).sigStr;
	}

	// Get specification of function by function declaration
	public String getFunSpecStr(Fun_declContext ctx) {
		// <(6) Fill here>
		String fname = getFunName(ctx);
		return getFunSpecStr(fname);
	}

	// Put specification of function by function declaration
	public String putFunSpecStr(Fun_declContext ctx) {
		String fname = getFunName(ctx);
		String argtype = "";	
		String rtype = "";
		String res = "";
		
		// <(7) Fill here>

		// Use BytecodeGenListenerHelper's Method to get param type text
		argtype += getParamTypesText(ctx.params());

		// Use BytecodeGenListenerHelper's Method to get return type text
		rtype += getTypeText(ctx.type_spec());
		
		res += fname + "(" + argtype + ")" + rtype;
		
		FInfo finfo = new FInfo();
		finfo.sigStr = res;
		_fsymtable.put(fname, finfo);
		
		return res;
	}

	// Get variable id from symbol table by variable name
	String getVarId(String name){
		// <(8) Fill here>

		//	Trying to find variable in local symbol table
		VarInfo lvar = _lsymtable.get(name);
		if (lvar != null) {
			return "" + lvar.id;
		}

		// 	When variable is not found in local symbol table,
		//		Trying to find it in global symbol table.
		VarInfo gvar = _gsymtable.get(name);
		if (gvar != null) {
			return "" + gvar.id;
		}

		//	When found nothing
		return null;
	}
	
	Type getVarType(String name){
		VarInfo lvar = (VarInfo) _lsymtable.get(name);
		if (lvar != null) {
			return lvar.type;
		}
		
		VarInfo gvar = (VarInfo) _gsymtable.get(name);
		if (gvar != null) {
			return gvar.type;
		}
		
		return Type.ERROR;	
	}
	String newLabel() {
		return "label" + _labelID++;
	}
	
	String newTempVar() {
		String id = "";
		return id + _tempVarID--;
	}

	// Get global variable id
	public String getVarId(Var_declContext ctx) {
		// <(9) Fill here>
		String sname = "";
		sname += getVarId(ctx.IDENT().getText());
		return sname;
	}

	// local
	public String getVarId(Local_declContext ctx) {
		String sname = "";
		sname += getVarId(ctx.IDENT().getText());
		return sname;
	}
}
