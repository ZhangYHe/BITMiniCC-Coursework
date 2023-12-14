package bit.minisys.minicc.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import bit.minisys.minicc.pp.internal.A;
import org.antlr.v4.gui.TreeViewer;

import com.fasterxml.jackson.databind.ObjectMapper;

import bit.minisys.minicc.MiniCCCfg;
import bit.minisys.minicc.internal.util.MiniCCUtil;
import bit.minisys.minicc.parser.ast.*;

/*
 * PROGRAM     --> FUNC_LIST
 * FUNC_LIST   --> FUNC FUNC_LIST | e
 * FUNC        --> TYPE ID '(' ARGUMENTS ')' CODE_BLOCK
 * TYPE        --> INT
 * ARGS   	   --> e | ARG_LIST
 * ARG_LIST    --> ARG ',' ARGLIST | ARG
 * ARG    	   --> TYPE ID
 * CODE_BLOCK  --> '{' STMTS '}'
 * STMTS       --> STMT STMTS | e
 * STMT        --> RETURN_STMT
 *
 * RETURN STMT --> RETURN EXPR ';'
 *
 * EXPR        --> TERM EXPR'
 * EXPR'       --> '+' TERM EXPR' | '-' TERM EXPR' | e
 *
 * TERM        --> FACTOR TERM'
 * TERM'       --> '*' FACTOR TERM' | e
 *
 * FACTOR      --> ID
 *
 */

class ScannerToken{
	public String lexme;
	public String type;
	public int	  line;
	public int    column;
}

public class ExampleParser implements IMiniCCParser {

	private ArrayList<ScannerToken> tknList;
	private int tokenIndex;
	private ScannerToken nextToken;

	@Override
	public String run(String iFile) throws Exception {
		System.out.println("Parsing...");

		String oFile = MiniCCUtil.removeAllExt(iFile) + MiniCCCfg.MINICC_PARSER_OUTPUT_EXT;
		String tFile = MiniCCUtil.removeAllExt(iFile) + MiniCCCfg.MINICC_SCANNER_OUTPUT_EXT;

		tknList = loadTokens(tFile);
		tokenIndex = 0;

		ASTNode root = program();


		String[] dummyStrs = new String[16];
		TreeViewer viewr = new TreeViewer(Arrays.asList(dummyStrs), root);
		viewr.open();

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File(oFile), root);

		//TODO: write to file


		return oFile;
	}


	private ArrayList<ScannerToken> loadTokens(String tFile) {
		tknList = new ArrayList<ScannerToken>();

		ArrayList<String> tknStr = MiniCCUtil.readFile(tFile);

		for(String str: tknStr) {
			if(str.trim().length() <= 0) {
				continue;
			}

			ScannerToken st = new ScannerToken();
			//[@0,0:2='int',<'int'>,1:0]
			String[] segs;
			if(str.indexOf("<','>") > 0) {
				str = str.replace("','", "'DOT'");

				segs = str.split(",");
				segs[1] = "=','";
				segs[2] = "<','>";

			}else {
				segs = str.split(",");
			}
			st.lexme = segs[1].substring(segs[1].indexOf("=") + 1);
			st.type  = segs[2].substring(segs[2].indexOf("<") + 1, segs[2].length() - 1);
			String[] lc = segs[3].split(":");
			st.line = Integer.parseInt(lc[0]);
			st.column = Integer.parseInt(lc[1].replace("]", ""));

			tknList.add(st);
		}

		return tknList;
	}

	private ScannerToken getToken(int index){
		if (index < tknList.size()){
			return tknList.get(index);
		}
		return null;
	}

	public void matchToken(String type) {
		if(tokenIndex < tknList.size()) {
			ScannerToken next = tknList.get(tokenIndex);
			if(!next.type.equals(type)) {
				System.out.println("[ERROR]Parser: unmatched token, expected = " + type + ", "
						+ "input = " + next.type);
			}
			else {
				tokenIndex++;
			}
		}
	}

	//PROGRAM --> FUNC_LIST
	public ASTNode program() {
		ASTCompilationUnit p = new ASTCompilationUnit();
		ArrayList<ASTNode> fl = funcList();
		if(fl != null) {
			//p.getSubNodes().add(fl);
			p.items.addAll(fl);
		}
		p.children.addAll(p.items);
		return p;
	}

	//FUNC_LIST --> FUNC FUNC_LIST | e
	public ArrayList<ASTNode> funcList() {
		ArrayList<ASTNode> fl = new ArrayList<ASTNode>();

		nextToken = tknList.get(tokenIndex);
		if(nextToken.type.equals("EOF")) {
			return null;
		}
		else {
			ASTNode f = func();
			fl.add(f);
			ArrayList<ASTNode> fl2 = funcList();
			if(fl2 != null) {
				fl.addAll(fl2);
			}
			return fl;
		}
	}

	//FUNC --> TYPE ID '(' ARGUMENTS ')' CODE_BLOCK
	public ASTNode func() {
		ASTFunctionDefine fdef = new ASTFunctionDefine();

		ASTToken s = type();

		fdef.specifiers.add(s);
		fdef.children.add(s);

		ASTFunctionDeclarator fdec = new ASTFunctionDeclarator();
		fdec.declarator = declor();
		// 在declor中使用ASTIdentifier

		// ASTIdentifier id = new ASTIdentifier();
		// id.tokenId = tokenIndex;
		// matchToken("Identifier");
		// fdef.children.add(id);

		matchToken("'('");
		ArrayList<ASTParamsDeclarator> pl = arguments();
		matchToken("')'");

		//fdec.identifiers.add(id);
		if(pl != null) {
			fdec.params.addAll(pl);
			fdec.children.addAll(pl);
		}

		ASTCompoundStatement cs = codeBlock();

		fdef.declarator = fdec;
		fdef.children.add(fdec);
		fdef.body = cs;
		fdef.children.add(cs);


		return fdef;
	}

	//TYPE --> INT |FLOAT | CHART
	public ASTToken type() {
		ScannerToken st = tknList.get(tokenIndex);

		ASTToken t = new ASTToken();
		if(st.type.equals("'int'")) {
			t.tokenId = tokenIndex;
			t.value = st.lexme;
			tokenIndex++;
		}
		return t;
	}

	//ARGUMENTS --> e | ARG_LIST
	public ArrayList<ASTParamsDeclarator> arguments() {
		nextToken = tknList.get(tokenIndex);
		if(nextToken.type.equals("')'")) { //ending
			return null;
		}
		else {
			ArrayList<ASTParamsDeclarator> al = argList();
			return al;
		}
	}

	//ARG_LIST --> ARGUMENT ',' ARGLIST | ARGUMENT
	public ArrayList<ASTParamsDeclarator> argList() {
		ArrayList<ASTParamsDeclarator> pdl = new ArrayList<ASTParamsDeclarator>();
		ASTParamsDeclarator pd = argument();
		pdl.add(pd);

		nextToken = tknList.get(tokenIndex);
		if(nextToken.type.equals("','")) {
			matchToken("','");
			ArrayList<ASTParamsDeclarator> pdl2 = argList();
			pdl.addAll(pdl2);
		}

		return pdl;
	}

	//ARGUMENT --> TYPE ID
	public ASTParamsDeclarator argument() {
		ASTParamsDeclarator pd = new ASTParamsDeclarator();
		ASTToken t = type();
		pd.specfiers.add(t);

		ASTIdentifier id = new ASTIdentifier();
		id.tokenId = tokenIndex;
		id.value = nextToken.lexme;
		//id.children.add(id.value);
		matchToken("Identifier");

		ASTVariableDeclarator vd =  new ASTVariableDeclarator();
		vd.identifier = id;
		pd.declarator = vd;
		pd.children.add(vd);
		vd.children.add(id);

		return pd;
	}



	//CODE_BLOCK --> '{' STMTS '}'
	public ASTCompoundStatement codeBlock() {
		ASTCompoundStatement cs = new ASTCompoundStatement();
		nextToken = tknList.get(tokenIndex);
		if(!nextToken.type.equals("'{'")){
			System.out.println("[ERROR]Parser: unmatched token, expected = {"  + ", "
					+ "input = " + nextToken.type);
		}
		matchToken("'{'");
		LinkedList<ASTNode> blockItems = new LinkedList<>();
		nextToken = tknList.get(tokenIndex);
		while(!nextToken.type.equals("'}'")){
//			System.out.println("codeBlock");
//			System.out.println(nextToken.line);
//			System.out.println(nextToken.column);
			//局部变量声明
			if(nextToken.type.equals("'int'")){
				System.out.println("codeBlock");
				System.out.println(nextToken.line);
				System.out.println(nextToken.column);
				//matchToken("'int'");
				ASTDeclaration dec = decl();
				blockItems.add(dec);
				nextToken = tknList.get(tokenIndex);
				matchToken("';'");
			}
			//复合语句
			else{
				ASTCompoundStatement comstmt = stmts();
				blockItems.add(comstmt);
			}
			nextToken = tknList.get(tokenIndex);
		}
		matchToken("'}'");
		cs.blockItems = blockItems;
		return cs;

	}

	//STMTS --> STMT STMTS | e
	public ASTCompoundStatement stmts() {
		nextToken = tknList.get(tokenIndex);
		if (nextToken.type.equals("'}'"))
			return null;
		else {
			if(nextToken.type.equals("'int'")){
				return null;
			}
			ASTCompoundStatement cs = new ASTCompoundStatement();
			ASTStatement s = stmt();
			cs.blockItems.add(s);

			ASTCompoundStatement cs2 = stmts();
			if(cs2 != null)
				cs.blockItems.add(cs2);
			return cs;

//			if(s!=null) {
//				ASTCompoundStatement cs2 = stmts();
//				if (cs2 != null)
//					cs.blockItems.add(cs2);
//			}
		}
	}

	//STMT --> ASSIGN_STMT | RETURN_STMT | DECL_STMT | FUNC_CALL
	public ASTStatement stmt() {
		nextToken = tknList.get(tokenIndex);
//		System.out.println(nextToken.line);
//		System.out.println(nextToken.column);
		if(nextToken.type.equals("'return'")) {
			return returnStmt();
		}
		else if(nextToken.type.equals("'if'")){
			return selstmt();
		}
		else if(nextToken.type.equals("'for'")){
			// for(int i; ... ; ...)
			nextToken = tknList.get(tokenIndex+2);
			if(nextToken.type.equals("'int'")){
				nextToken = tknList.get(tokenIndex);
				return itedstmt();
			}
			// for(i;i ... ; ...)
			else if(nextToken.type.equals("Identifier")){
				nextToken = tknList.get(tokenIndex);
				return itestmt();
			}
			else{
				nextToken = tknList.get(tokenIndex);
				return itestmt();
			}
		}
/*		else if(nextToken.type.equals("'int'")) {
			//assign_stmt
			//nextToken = tknList.get(tokenIndex);
//			matchToken("'int'");
//			return assign_stmt();

			//ASTStatement s = stmt();
//			LinkedList<ASTNode> blockItems = new LinkedList<>();
//			ASTDeclaration dec = decl();
//			blockItems.add(dec);
//			nextToken = tknList.get(tokenIndex);
//			matchToken("';'");
//			s.specifiers
//			return decl();


			decl();
			return null;
		}
*/
//		// 函数调用
//		else if(nextToken.type.equals("Identifier")&&tknList.get(tokenIndex+1).type.equals("'('")) {
//			//表达式语句
//			ASTExpressionStatement estmt = new ASTExpressionStatement();
//			LinkedList<ASTExpression> exprs = new LinkedList<>();
//
//			ASTFunctionCall fc =
//		}
		else if(nextToken.type.equals("Identifier")){
			//表达式语句或函数调用
			ASTExpressionStatement estmt = new ASTExpressionStatement();
			LinkedList<ASTExpression> exprs = new LinkedList<>();

			ASTExpression expr = expr();
			nextToken = tknList.get(tokenIndex);
			if(expr!=null){
				while(nextToken.type.equals("';'")){
					nextToken = tknList.get(tokenIndex);
					exprs.add(expr);
					matchToken("';'");
					expr = expr();
				}
				estmt.exprs = exprs;
				return estmt;
			}
			//只有一个标识符
			else{
				return null;
			}
		}
		else{
			System.out.println(nextToken.line);
			System.out.println(nextToken.column);
			System.out.println("[ERROR]Parser: unreachable stmt!");
			return null;
		}
	}

	//RETURN_STMT --> RETURN EXPR ';'
	public ASTReturnStatement returnStmt() {
		matchToken("'return'");
		ASTReturnStatement rs = new ASTReturnStatement();
		ASTExpression e = expr();
		matchToken("';'");
		rs.expr.add(e);
		return rs;
	}

	//FUNCALL
//	public ASTFunctionCall funcall(){
//		nextToken = tknList.get(tokenIndex);
//		ASTFunctionCall fc = new ASTFunctionCall();
//		fc.funcname = expr();
//	}


	//EXPR --> TERM EXPR'
	public ASTExpression expr() {
		ASTExpression term = term();
		nextToken = tknList.get(tokenIndex);

//		System.out.println("expr");
//		System.out.println(nextToken.line);
//		System.out.println(nextToken.column);
//		System.out.println(nextToken.type);

		//赋值表达式
		if(nextToken.type.equals("'='"))
		{
			nextToken = tknList.get(tokenIndex);
			ASTAssignmentExpression be = assexpr();
			if(be != null) {
				be.id = (ASTIdentifier) term;
				return be;
			}else {
				return term;
			}
		}
		//关系表达式或者条件表达式
		else if(nextToken.type.equals("'<'")||nextToken.type.equals("'>'")){
			nextToken = tknList.get(tokenIndex+2);
			//条件表达式
			//a>b ? a:b;
			if(nextToken.type.equals("'?'")){
				nextToken = tknList.get(tokenIndex);
				ASTRelationalExpression re = relexpr();
				ASTConditionExpression ce = conexpr();
				if(re!=null){
					re.expr1=term;
					ce.condExpr = re;
				}
				return ce;
			}
			//关系表达式
			else{
				nextToken = tknList.get(tokenIndex);
				ASTRelationalExpression re = relexpr();
				if(re!=null){
					re.expr1=term;
					return re;
				}else {
					return term;
				}
			}

		}
		// 常量
		else if(nextToken.type.equals("IntegerConstant")&&(tknList.get(tokenIndex+1).type.equals("','")||tknList.get(tokenIndex+1).type.equals("';'"))){
			//System.out.println("expr");
			ASTIntegerConstant intc = new ASTIntegerConstant();
			intc.tokenId = tokenIndex;
			intc.value = Integer.valueOf(nextToken.lexme.charAt(1))-48;
			matchToken("IntegerConstant");
			return intc;
		}
		// x++ , x--
		else if(nextToken.type.equals("'++'")||nextToken.type.equals("'--'")){
			ASTPostfixExpression pe = new ASTPostfixExpression();

			ASTToken tkn = new ASTToken();
			tkn.tokenId = tokenIndex;
			tkn.value = nextToken.type;

			if(nextToken.type.equals("'++'"))
				matchToken("'++'");
			else if(nextToken.type.equals("'--'"))
				matchToken("'--'");

			pe.op=tkn;
			return pe;

		}
//		else if(nextToken.type.equals("Identifier")&&tknList.get(tokenIndex+1).type.equals("'('")){
//			ASTExpression ep
//		}
		//二元表达式：+-*/
		else{

			nextToken = tknList.get(tokenIndex);

//			System.out.println("expr");
//			System.out.println(nextToken.line);
//			System.out.println(nextToken.column);

			ASTBinaryExpression be = expr2();
			if(be != null) {
				be.expr1 = term;
				return be;
			}else {
				return term;
			}
		}
	}

	//EXPR' --> '+' TERM EXPR' | '-' TERM EXPR' | e
	public ASTBinaryExpression expr2() {
		nextToken = tknList.get(tokenIndex);
		if (nextToken.type.equals("';'"))
			return null;

		if(nextToken.type.equals("'+'")||nextToken.type.equals("'-'")){
			ASTBinaryExpression be = new ASTBinaryExpression();

			ASTToken tkn = new ASTToken();
			tkn.tokenId = tokenIndex;
			tkn.value = nextToken.type;
			if(nextToken.type.equals("'+'"))
				matchToken("'+'");
			else if(nextToken.type.equals("'-'"))
				matchToken("'-'");
			be.op = tkn;
			be.expr2 = term();

			ASTBinaryExpression expr = expr2();
			if(expr != null) {
				expr.expr1 = be;
				return expr;
			}

			return be;
		}
		else {
			return null;
		}
	}

	//TERM --> FACTOR TERM2
	public ASTExpression term() {

		nextToken=tknList.get(tokenIndex);

		System.out.println("term");
		System.out.println(tknList.get(tokenIndex).line);
		System.out.println(tknList.get(tokenIndex).column);

		ASTExpression f = factor();
		ASTBinaryExpression be = term2();

		if(be != null) {
			be.expr1 = f;
			return be;
		}else {
			return f;
		}
//		}else{
//			return null;
//		}

	}

	//TERM'--> '*' FACTOR TERM' | '/' FACTOR TERM' | e
	public ASTBinaryExpression term2() {
		nextToken = tknList.get(tokenIndex);
		if(nextToken.type.equals("'*'")){
			ASTBinaryExpression be = new ASTBinaryExpression();

			ASTToken tkn = new ASTToken();
			tkn.tokenId = tokenIndex;
			matchToken("'*'");

			be.op = tkn;
			be.expr2 = factor();

			ASTBinaryExpression term = term2();
			if(term != null) {
				term.expr1 = be;
				return term;
			}
			return be;
		}else {
			return null;
		}
	}

	//FACTOR --> '(' EXPR ')' | ID | CONST | FUNC_CALL
	public ASTExpression factor() {
		nextToken = tknList.get(tokenIndex);
		if(nextToken.type.equals("Identifier")&&(!tknList.get(tokenIndex+1).type.equals("'('"))) {
			System.out.println("factor identifier");
			System.out.println(tknList.get(tokenIndex).line);
			System.out.println(tknList.get(tokenIndex).column);

			ASTIdentifier id = new ASTIdentifier();
			id.tokenId = tokenIndex;
			id.value = nextToken.lexme;
			matchToken("Identifier");
			return id;
		}
		else if(nextToken.type.equals("IntegerConstant")){
			ASTIntegerConstant intc = new ASTIntegerConstant();
			intc.tokenId = tokenIndex;
			intc.value = Integer.valueOf(nextToken.lexme.charAt(1))-48;
			matchToken("IntegerConstant");
			return intc;
		}
		// 函数调用
		else if(nextToken.type.equals("Identifier")&&tknList.get(tokenIndex+1).type.equals("'('")) {
			System.out.println("factor func");
			System.out.println(tknList.get(tokenIndex).line);
			System.out.println(tknList.get(tokenIndex).column);

			ASTFunctionCall fc = new ASTFunctionCall();
			ASTIdentifier id = new ASTIdentifier();
			id.tokenId = tokenIndex;
			id.value = nextToken.lexme;
			fc.funcname=id;
			matchToken("Identifier");
			matchToken("'('");
			nextToken = tknList.get(tokenIndex);
			fc.argList = new ArrayList<>();
			//ArrayList<ASTNode> arg = new ArrayList<ASTNode>();
			while(!nextToken.type.equals("')'"))
			{
				System.out.println("factor func++");
				System.out.println(tknList.get(tokenIndex).line);
				System.out.println(tknList.get(tokenIndex).column);

				if(nextToken.type.equals("Identifier")){
					ASTIdentifier idf = new ASTIdentifier();
					idf.tokenId=tokenIndex;
					idf.value=nextToken.lexme;
					matchToken("Identifier");
					//System.out.println("-------");
//					arg.add(idf);
					fc.argList.add(idf);
					nextToken = tknList.get(tokenIndex);
				}
				else if(nextToken.type.equals("IntegerConstant")){
					ASTIntegerConstant intc = new ASTIntegerConstant();
					intc.tokenId = tokenIndex;
					intc.value = Integer.valueOf(nextToken.lexme.charAt(1))-48;
					matchToken("IntegerConstant");
					//arg.add(intc);
					fc.argList.add(intc);
					nextToken = tknList.get(tokenIndex);
				}
				if(nextToken.type.equals("','"))
					matchToken("','");
				else if(nextToken.type.equals("')'"))
					matchToken("')'");

				System.out.println("factor func--");
				System.out.println(tknList.get(tokenIndex).line);
				System.out.println(tknList.get(tokenIndex).column);
			}

//			matchToken("')'");
//			fc.argList.addAll(arg);
//			fc.argList=arg;
//			System.out.println("-------");
			return fc;
		}
		else {
			return null;
		}
	}

	// if-else
	public ASTSelectionStatement selstmt(){
		if(nextToken.type.equals("'if'")) {
			matchToken("'if'");
			nextToken = tknList.get(tokenIndex);
			ASTSelectionStatement sel = new ASTSelectionStatement();
			if(nextToken.type.equals("'('")) {
				matchToken("'('");
				LinkedList<ASTExpression> elistr = new LinkedList<>();
				elistr.add(expr());
				sel.cond = elistr;
				matchToken("')'");
				nextToken = tknList.get(tokenIndex);
			}else {
				System.out.println("[ERROR]Parser:selectionStatement unmatched token, expected = ("  + ", "
						+ "input = " + nextToken.type);
			}
			if(nextToken.type.equals("'{'")){
				sel.then = codeBlock();
				//matchToken("'{'");
				//nextToken = tknList.get(tokenIndex);
			}else{
				sel.then = stmt();
			}
			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("'else'")){
				matchToken("'else'");
				nextToken = tknList.get(tokenIndex);
				if(nextToken.type.equals("'{'")){
					sel.otherwise = codeBlock();
				}else {
					sel.otherwise = stmt();
				}
			}
			return sel;
		}else {
			return null;
		}
	}

	// for(i; ... ; ...)
	public ASTIterationStatement itestmt(){
		if(nextToken.type.equals("'for'")){
			matchToken("'for'");
			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("'('")){
				matchToken("'('");
			}else {
				System.out.println("[ERROR]Parser:iterationStatement unmatched token, expected = ("  + ", "
						+ "input = " + nextToken.type);
			}

			nextToken = tknList.get(tokenIndex);

			ASTIterationStatement ite = new ASTIterationStatement();
			LinkedList<ASTExpression> init = new LinkedList<>();
			LinkedList<ASTExpression> cond = new LinkedList<>();
			LinkedList<ASTExpression> step = new LinkedList<>();
			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("')'")){
				System.out.println("[ERROR]Parser:iterationStatement lack cond,step, expected token= ;"  + ", "
						+ "input = " + nextToken.type);
			}
			while(!nextToken.type.equals("';'")){
				init.add(expr());
				nextToken = tknList.get(tokenIndex);
				if(nextToken.type.equals("','")){
					matchToken("','");
				}
			}
			matchToken("';'");
			nextToken = tknList.get(tokenIndex);
			while(!nextToken.type.equals("';'")){
				cond.add(expr());
				nextToken = tknList.get(tokenIndex);
				if(nextToken.type.equals("','")){
					matchToken("','");
				}
			}
			matchToken("';'");
			nextToken = tknList.get(tokenIndex);
			while(!nextToken.type.equals("')'")){
				step.add(expr());
				nextToken = tknList.get(tokenIndex);
				if(nextToken.type.equals("','")){
					matchToken("','");
				}
			}
			matchToken("')'");
			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("'{'")){
				ASTCompoundStatement stat = codeBlock();
				ite.stat = stat;
			}else{
				ite.stat = stmt();
			}
			ite.cond = cond;
			ite.init = init;
			ite.step = step;

			return ite;

		}else {
			return null;
		}
	}

	// for(int i; ... ; ...)
	public ASTIterationDeclaredStatement itedstmt(){
		matchToken("'for'");
		nextToken = tknList.get(tokenIndex);
		matchToken("'('");
		nextToken = tknList.get(tokenIndex);
		ASTIterationDeclaredStatement ite = new ASTIterationDeclaredStatement();
		ASTDeclaration init = new ASTDeclaration();
		LinkedList<ASTExpression> cond = new LinkedList<>();
		LinkedList<ASTExpression> step = new LinkedList<>();
		init = decl();
		matchToken("';'");
		nextToken = tknList.get(tokenIndex);
		while(!nextToken.type.equals("';'")){
			cond.add(expr());
			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("','")){
				matchToken("','");
			}
		}
		matchToken("';'");
		nextToken = tknList.get(tokenIndex);

//		System.out.println("itedstmt");
//		System.out.println(nextToken.line);
//		System.out.println(nextToken.column);

		while(!nextToken.type.equals("')'")){
			step.add(expr());
			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("','")){
				matchToken("','");
			}
		}
		matchToken("')'");
		nextToken = tknList.get(tokenIndex);

		System.out.println("itedstmt");
		System.out.println(nextToken.line);
		System.out.println(nextToken.column);

		if(nextToken.type.equals("'{'")){
			ASTCompoundStatement stat = codeBlock();
			ite.stat = stat;
		}else{
			ite.stat = stmt();
		}
		ite.cond = cond;
		ite.init = init;
		ite.step = step;

		return ite;

	}

	//ASSIGN_STMT --> ID = EXPR
	public ASTAssignmentStatement assign_stmt(){
		ASTAssignmentStatement assstmt = new ASTAssignmentStatement();
		nextToken = tknList.get(tokenIndex);
		ASTIdentifier id = new ASTIdentifier();
		id.value =  nextToken.lexme;
		id.tokenId = tokenIndex;
		assstmt.id = id;
		matchToken("Identifier");
		nextToken = tknList.get(tokenIndex);

		ASTToken tkn = new ASTToken();
		tkn.tokenId = tokenIndex;
		tkn.value = "=";
		matchToken("'='");
		assstmt.op = tkn;

		nextToken = tknList.get(tokenIndex);
		assstmt.expr = expr();

		nextToken = tknList.get(tokenIndex);
		if(nextToken.type.equals("';'"))
			matchToken("';'");

		return assstmt;
	}

	public ASTUnaryExpression unaexpr(){
		return null;
	}

	// x ? a:b;
	public ASTConditionExpression conexpr(){
		ASTConditionExpression cond = new ASTConditionExpression();
		nextToken = tknList.get(tokenIndex);
		if(!nextToken.type.equals("'?'")){
			System.out.println("[ERROR]Parser:conditionExpression unmatched token, expected = ?"  + ", "
					+ "input = " + nextToken.type);
			return null;
		}
		else{
			matchToken("'?'");
			nextToken = tknList.get(tokenIndex);
			LinkedList<ASTExpression> trueexpr = new LinkedList<>();
			while(!nextToken.type.equals("':'")){
				trueexpr.add(expr());
				nextToken = tknList.get(tokenIndex);
			}
			cond.trueExpr = trueexpr;
			matchToken("':'");
			nextToken = tknList.get(tokenIndex);
			cond.falseExpr = expr();
			nextToken = tknList.get(tokenIndex);
			if(!nextToken.type.equals("';'")){
				System.out.println("[ERROR]Parser:conditionExpression unmatched token, expected = ;"  + ", "
						+ "input = " + nextToken.type);
				return null;
			}

			return cond;
		}
	}

	//赋值表达式
	public ASTAssignmentExpression assexpr(){
		nextToken = tknList.get(tokenIndex);
		ASTAssignmentExpression ae = new ASTAssignmentExpression();
		ASTToken tkn = new ASTToken();
		if(nextToken.type.equals("'='")){
			tkn.tokenId = tokenIndex;
			tkn.value = nextToken.type;
			matchToken("'='");
		}
		ae.op = tkn;
		ae.expr = expr();
		return ae;
	}

	// 关系表达式
	public ASTRelationalExpression relexpr(){
		nextToken = tknList.get(tokenIndex);
		ASTRelationalExpression re = new ASTRelationalExpression();
		ASTToken tkn = new ASTToken();
		tkn.tokenId = tokenIndex;
		if(nextToken.type.equals("'<'")){
			tkn.value = nextToken.type;
			matchToken("'<'");
		}else if(nextToken.type.equals("'>'")){
			tkn.value = nextToken.type;
			matchToken("'>'");
		}
		re.op = tkn;
		re.expr2 = expr();
		return re;
	}

	// 声明语句 int a = 1;
	public ASTDeclaration decl(){
		// 类型
		nextToken = tknList.get(tokenIndex);

		System.out.println("decl");
		System.out.println(nextToken.line);
		System.out.println(nextToken.column);

		ASTDeclaration dc = new ASTDeclaration();
		LinkedList<ASTToken> specifiers =  new LinkedList<>();
		ASTToken specifier = new ASTToken();
		specifier.tokenId = tokenIndex;
		specifier.value = nextToken.type;
		matchToken(nextToken.type);
		specifiers.add(specifier);
		dc.specifiers = specifiers;

		// 标识符
		nextToken = tknList.get(tokenIndex);
		LinkedList<ASTInitList> initLists =  new LinkedList<>();

		do{
			System.out.println("decl+");
			System.out.println(nextToken.line);
			System.out.println(nextToken.column);

			ASTInitList initList = new ASTInitList();
			initList.declarator = declor();

			System.out.println("decl++");
			System.out.println(tknList.get(tokenIndex).line);
			System.out.println(tknList.get(tokenIndex).column);

			nextToken = tknList.get(tokenIndex);
			//声明后面接有初始化列表
			if(nextToken.type.equals("'='")) {
				matchToken("'='");
				nextToken = tknList.get(tokenIndex);
				//数组初始化
				// a[2]={0,1}
				if(nextToken.type.equals("'{'")){
					matchToken("'{'");
					LinkedList<ASTExpression> inlist_exprs = new LinkedList<>();
					while (!nextToken.type.equals("'}'")) {
						ASTExpression expr = expr();
						inlist_exprs.add(expr);
						nextToken = tknList.get(tokenIndex);
						if (nextToken.type.equals("','")) {
							matchToken("','");
						}
						nextToken = tknList.get(tokenIndex);
					}
					initList.exprs = inlist_exprs;
					matchToken("'}'");
				}
				//函数调用
				// int a = fun(a, ... );
//				else if(nextToken.type.equals("Identifier")&&tknList.get(tokenIndex+1).type.equals("'('")){
//					System.out.println("decl func");
//					System.out.println(tknList.get(tokenIndex).line);
//					System.out.println(tknList.get(tokenIndex).column);
//
//					LinkedList<ASTExpression> inlist_exprs = new LinkedList<>();
//					while (!nextToken.type.equals("';'")) {
//						//ASTExpression expr = expr();
//						ASTFunctionCall fc = funcall();
//						inlist_exprs.add(fc);
//						nextToken = tknList.get(tokenIndex);
//					}
//					initList.exprs = inlist_exprs;
//				}
				// 初始化
				// int a=1;
				else{
					System.out.println("decl+++");
					System.out.println(tknList.get(tokenIndex).line);
					System.out.println(tknList.get(tokenIndex).column);

					LinkedList<ASTExpression> inlist_exprs = new LinkedList<>();
//					while (!nextToken.type.equals("';'")) {
					while ((!nextToken.type.equals("';'"))&&(!nextToken.type.equals("','"))) {
						ASTExpression expr = expr();
						inlist_exprs.add(expr);
						nextToken = tknList.get(tokenIndex);
					}
					initList.exprs = inlist_exprs;
				}
			}

			nextToken = tknList.get(tokenIndex);
			if(nextToken.type.equals("','")){
				matchToken("','");
			}
			initLists.add(initList);
		} while(nextToken.type.equals("','"));

		System.out.println("+++++");

		dc.initLists = initLists;

//		nextToken= tknList.get(tokenIndex);
//		if(nextToken.type.equals("';'")){
//			matchToken("';'");
//		}
		return dc;
	}

	//识别变量标识符
	//int a=1; -> a
	public ASTDeclarator declor(){

		nextToken= tknList.get(tokenIndex);

		System.out.println("declor");
		System.out.println(nextToken.line);
		System.out.println(nextToken.column);

		if(nextToken.type.equals("Identifier")){

			nextToken = tknList.get(tokenIndex+1);
			//数组
			//a[2]
			if(nextToken.type.equals("'['")){
				nextToken = tknList.get(tokenIndex);
				ASTVariableDeclarator vd = new ASTVariableDeclarator();
				ASTIdentifier id = new ASTIdentifier();
				id.value = nextToken.lexme;
				id.tokenId = tokenIndex;
				matchToken("Identifier");
				vd.identifier = id;

				nextToken= tknList.get(tokenIndex);
				ASTArrayDeclarator ad = new ASTArrayDeclarator();
				ad.declarator = vd;
				nextToken= tknList.get(tokenIndex);
				matchToken("'['");
				ad.expr = expr();
				nextToken= tknList.get(tokenIndex);
				if(!nextToken.type.equals("']'")){
					System.out.println("[ERROR]Parser: unmatched token, expected = ]"  + ", "
							+ "input = " + nextToken.type);
				}else{
					matchToken("']'");
				}
				return ad;
			}
//			else if(nextToken.type.equals("'='")){
//				// 声明
//				//assign
//				System.out.println("----");
//				System.out.println(nextToken.line);
//				System.out.println(nextToken.column);
//
//				nextToken = tknList.get(tokenIndex);
//				ASTAssignmentExpression as = new ASTAssignmentExpression();
//				return null;
//			}
			//标识符
			else{
				nextToken= tknList.get(tokenIndex);
				ASTVariableDeclarator vd = new ASTVariableDeclarator();
				ASTIdentifier id = new ASTIdentifier();
				id.value = nextToken.lexme;
				id.tokenId = tokenIndex;
				matchToken("Identifier");
				vd.identifier = id;
				return vd;
			}

		}
		else{
			return null;
		}

	}
}
