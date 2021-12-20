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
	
	void putLocalVar(String varname, Type type){
		//<(0) Fill here>
		VarInfo var = new VarInfo(type, _localVarID++);
		_lsymtable.put(varname, var);
	}
	
	void putGlobalVar(String varname, Type type){
		//<(1) Fill here>
		VarInfo var = new VarInfo(type, _globalVarID++);
		_gsymtable.put(varname, var);
	}
	
	void putLocalVarWithInitVal(String varname, Type type, int initVar){
		//<(2) Fill here>
		VarInfo var = new VarInfo(type, _localVarID++, initVar);
		_lsymtable.put(varname, var);
	}
	void putGlobalVarWithInitVal(String varname, Type type, int initVar){
		//<(3) Fill here>
		VarInfo var = new VarInfo(type, _globalVarID++, initVar);
		_gsymtable.put(varname, var);
	}
	
	void putParams(MiniCParser.ParamsContext params) {
		for(int i = 0; i < params.param().size(); i++) {
			//<(4) Fill here>

			// Use BytecodeGenListenerHelper's Method to get param name
			String varname = getParamName(params.param(i));
			Type vartype = null;

			// `type_spec` of param must be int
			if(getTypeText(params.param(i).type_spec()).equals("int")) {

				// If type of param is int array
				if(isArrayParamDecl(params.param(i))) {
					vartype = Type.INTARRAY;

				// If type of param is int
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
	
	public String getFunSpecStr(String fname) {		
		// <(5) Fill here>
		return _fsymtable.get(fname).sigStr;
	}

	public String getFunSpecStr(Fun_declContext ctx) {
		// <(6) Fill here>
		String fname = getFunName(ctx);
		return getFunSpecStr(fname);
	}
	
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
	
	String getVarId(String name){
		// <(8) Fill here>
		VarInfo lvar = _lsymtable.get(name);
		if (lvar != null) {
			return "" + lvar.id;
		}

		VarInfo gvar = _gsymtable.get(name);
		if (gvar != null) {
			return "" + gvar.id;
		}

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

	// global
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
