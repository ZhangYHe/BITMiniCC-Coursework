package bit.minisys.minicc.icgen;

import bit.minisys.minicc.parser.ast.ASTIntegerConstant;
import bit.minisys.minicc.parser.ast.ASTNode;
import org.python.antlr.op.In;

// 四元式形式的中间代码, 操作数和返回值的结构直接使用AST节点，也可以自定义IR节点
public class Quat {
	private Integer cursor;
	private String op;
	private ASTNode res;
	private ASTNode opnd1;
	private ASTNode opnd2;
	private String scope=null;//指明函数体
	public Quat(Integer cursor,String op, ASTNode res, ASTNode opnd1, ASTNode opnd2) {
		this.cursor = cursor;
		this.op = op;
		this.res = res;
		this.opnd1 = opnd1;
		this.opnd2 = opnd2;

	}
	public Quat(Integer cursor,String scope){
		this.cursor = cursor;
		this.scope = scope;
	}

//    public Quat(String s, ASTNode t1, ASTNode astNode, ASTIntegerConstant dd) {
//    }

    public Integer getCursor() { return cursor; }
	public String getOp() {
		return op;
	}
	public ASTNode getOpnd1() {
		return opnd1;
	}
	public ASTNode getOpnd2() {
		return opnd2;
	}
	public ASTNode getRes() {
		return res;
	}
	public String getScope() {
		return scope;
	}
}
