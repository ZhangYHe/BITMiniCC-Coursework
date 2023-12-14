package bit.minisys.minicc.semantic;

import bit.minisys.minicc.parser.ast.*;
import bit.minisys.minicc.pp.internal.T;

import bit.minisys.minicc.semantic.MySemanticAnalyzer;

import java.util.ArrayList;

public class MySemanticVisitor implements ASTVisitor{
    //cur_table 当前符号表
    public ArrayList<SymbolTable> cur_table = new ArrayList<>();
    public ArrayList<ArrayList<SymbolTable>> global_table ;
    public int current_tokenId;
    public int is_in_loop =0;
    public int is_return=0;

    //返回0时出现错误
    public int check_notdefine(ASTIdentifier id){
        int flag=0;
        //在当前函数符号表搜索
        for(int i=0;i<cur_table.size();i++){
            if(cur_table.get(i).Name.equals(id.value)){
                flag=1;
                break;
            }
        }
        if(flag==0) {
            //在全局符号表搜索
            for (int i = 0; i < global_table.get(0).size(); i++) {
                if (global_table.get(0).get(i).Name.equals(id.value)) {
                    flag = 1;
                    break;
                }
            }
        }
        return flag;
    }
    //返回0时出现错误
    public int check_notdefine(ASTExpression expr){
        String type = expr.getType();
        int flag=0;
        if(type.equals("Identifier")){
            ASTIdentifier id = (ASTIdentifier) expr;
            flag = check_notdefine(id);
            if(flag==0){
                System.out.println("ES01 >> Identifier "+id.value+" is not defined.");
            }
        }
        return flag;
    }
    //返回0时函数重复定义
    public int check_funcdef(SymbolTable item){
        int flag=1;

        //遍历全局符号表
        //查找函数名相同的函数定义
        for(int i=1;i<global_table.size();i++){
            if((global_table.get(i).get(0).Name.equals(item.Name))&&(global_table.get(i).get(0).Kind.equals(item.Kind))){
                flag=0;
            }
        }
        return flag;
    }

    //初始化列表
    @Override
    public void visit(ASTInitList initList) throws Exception {
        //遍历所有声明
        initList.declarator.accept(this);
        //遍历所有声明表达式
        for(int i=0;i<(initList.exprs.size());i++){
            ASTExpression expr = initList.exprs.get(i);
            expr.accept(this);
        }
    }

    //变量声明
    @Override
    public void visit(ASTDeclarator declarator) throws Exception {
        String type = declarator.getType();
        //区分变量声明和函数声明
        if(type.equals("VariableDeclarator")){
            ASTVariableDeclarator vd = (ASTVariableDeclarator) declarator;
            vd.accept(this);
        }else if(type.equals("FunctionDeclarator")){
            ASTFunctionDeclarator fd = (ASTFunctionDeclarator) declarator;
            fd.accept(this);
        }
    }
    //表达式
    @Override
    public void visit(ASTExpression expression) throws Exception {
        String expr_type = expression.getType();
        //区分函数调用、二元表达式和变量标识符
        if(expr_type.equals("FunctionCall")){
            ASTFunctionCall fc = (ASTFunctionCall) expression;
            fc.accept(this);
        }else if(expr_type.equals("BinaryExpression")) {
            ASTBinaryExpression be = (ASTBinaryExpression) expression;
            be.accept(this);
        }else if(expr_type.equals(("Identifier"))){
            ASTIdentifier id = (ASTIdentifier) expression;
            //检查标识符是否定义
            int flag = check_notdefine(id);
            if(flag==0){
                System.out.println("ES01 >> Identifier "+id.value+" is not defined.");
            }
        }
    }

    //函数调用
    @Override
    public void visit(ASTFunctionCall funcCall) throws Exception {
        String type = funcCall.funcname.getType();
        //记录函数名
        if(type.equals("Identifier")){
            ASTIdentifier id = (ASTIdentifier) funcCall.funcname;
            //检查函数是否定义
            int flag = check_notdefine(id);
            if(flag==0){
                System.out.println("ES01 >> FunctionCall:"+id.value+" is not declarated.");
            }

            //找到函数在全局符号表中的位置
            int func_def_ord =0;
            for(int i=0;i<global_table.get(0).size();i++){
                if(global_table.get(0).get(i).Name.equals(id.value)){
                    func_def_ord=i;
                    break;
                }
            }
            //没有参数，无需检验参数是否一致
            if(global_table.get(0).size()<1){
                return;
            }
            //参数个数不匹配
            if(funcCall.argList.size()!=global_table.get(0).get(func_def_ord).params.size()){
                System.out.println("ES04 >> FunctionCall:"+cur_table.get(0).Name+"'s param num is not matched.");
            }else{
                for(int i=0;i<funcCall.argList.size();i++){
                    if(global_table.get(0).get(func_def_ord).params.get(i).equals("int")){
                        if(funcCall.argList.get(i).getType().equals("IntegerConstant")){
                            continue;
                        }
                        //参数类型不匹配
                        else {
                            System.out.println("ES04 >> FunctionCall:"+cur_table.get(0).Name+"'s param type is not matched.");
                        }
                    }
                }
            }
        }
    }

    //标识符
    @Override
    public void visit(ASTIdentifier identifier) throws Exception {
        //记录标识符信息
        SymbolTable item = new SymbolTable();
        item.Name=identifier.value;
        item.Scope_entry = identifier.tokenId;
        this.current_tokenId = identifier.tokenId;
        this.cur_table.add(item);
    }

    //声明
    @Override
    public void visit(ASTDeclaration declaration) throws Exception {
        for(int i=0;i<(declaration.initLists.size());i++){
            declaration.initLists.get(i).accept(this);
        }
        //修改声明类型
        for(int i=0;i<cur_table.size();i++){
            cur_table.get(i).Type = declaration.specifiers.get(0).value;
        }
    }

    //函数定义
    @Override
    public void visit(ASTFunctionDefine functionDefine) throws Exception {
        is_return=0;
        String temp_type = functionDefine.declarator.getType();
        if(temp_type.equals("FunctionDeclarator")) {
            ASTFunctionDeclarator fd = (ASTFunctionDeclarator) functionDefine.declarator;
            fd.accept(this);
            if(this.cur_table.size()!=0){
                //函数符号表第一项为函数定义
                this.cur_table.get(0).Kind = "FunctionDefine";
                //检查函数是否重复定义
                int flag = check_funcdef(this.cur_table.get(0));
                if(flag==0){
                    System.out.println("ES02 >> FunctionDefine:"+this.cur_table.get(0).Name+" is defined.");
                }
            }
        }
        functionDefine.body.accept(this);
        if(is_return==0){
            System.out.println("ES08 >> Function:"+this.cur_table.get(0).Name+" must have a return in the end.");
        }
    }

    //循环语句
    @Override
    public void visit(ASTIterationStatement iterationStat) throws Exception {
        this.is_in_loop = 1;
        String tmp_type = iterationStat.stat.getType();
        if(tmp_type.equals("CompoundStatement")){
            ASTCompoundStatement cstmt = (ASTCompoundStatement) iterationStat.stat;
            cstmt.accept(this);
        }
        //递归结束，退出循环
        this.is_in_loop = 0;
    }

    //二元表达式
    @Override
    public void visit(ASTBinaryExpression binaryExpression) throws Exception {
        //检查标识符是否定义
        if(binaryExpression.expr1.getType().equals("Identifier")){
            check_notdefine(binaryExpression.expr1);
        }
        //检查函数是否定义
        if(binaryExpression.expr2.getType().equals("FunctionCall")){
            ASTFunctionCall fc = (ASTFunctionCall) binaryExpression.expr2;
            fc.accept(this);
        }
        //检查标识符是否定义
        else if(binaryExpression.expr2.getType().equals("Identifier")){
            check_notdefine(binaryExpression.expr2);
        }
    }

    //参数声明
    @Override
    public void visit(ASTParamsDeclarator paramsDeclarator) throws Exception {
        String tmp_type = paramsDeclarator.declarator.getType();
        //从table_cur开始为参数声明
        int table_cur = cur_table.size();
        if(tmp_type.equals("VariableDeclarator")){
            ASTVariableDeclarator vd = (ASTVariableDeclarator)paramsDeclarator.declarator;
            vd.accept(this);
        }
        //更新参数类型
        for(int i=table_cur;i<cur_table.size();i++){
            cur_table.get(i).Type = paramsDeclarator.specfiers.get(0).value;
        }
    }

    //表达式语句
    @Override
    public void visit(ASTExpressionStatement expressionStat) throws Exception {
        for(int i=0;i<expressionStat.exprs.size();i++){
            expressionStat.exprs.get(i).accept(this);
        }
    }

    //函数声明
    @Override
    public void visit(ASTFunctionDeclarator functionDeclarator) throws Exception {
        String temp_type = functionDeclarator.declarator.getType();

        if(temp_type.equals("VariableDeclarator")) {
            ASTVariableDeclarator vd = (ASTVariableDeclarator) functionDeclarator.declarator;
            vd.accept(this);
            if(cur_table.size()!=0){
                cur_table.get(cur_table.size()-1).Kind = "FunctionDeclarator";
            }
        }

        //遍历参数列表
        for(int i=0;i<functionDeclarator.params.size();i++){
            functionDeclarator.params.get(i).accept(this);
        }
        //参数列表不为空
        if(cur_table.size()>1){
            //更新参数类型
            for(int i=1;i<cur_table.size();i++){
                cur_table.get(0).params.add(cur_table.get(i).Type);
            }
        }
    }

    //变量声明
    @Override
    public void visit(ASTVariableDeclarator variableDeclarator) throws Exception {
        variableDeclarator.identifier.accept(this);
        //更新符号表中的类型
        cur_table.get(cur_table.size()-1).Kind = "VariableDeclarator";
    }

    //Label语句
    @Override
    public void visit(ASTLabeledStatement labeledStat) throws Exception {
        //从table_cur开始为label
        int table_cur = cur_table.size();
        labeledStat.label.accept(this);
        //更新label的Kind
        cur_table.get(table_cur).Kind = "label";

//        for(int i=0;i<cur_table.size();i++) {
//            System.out.println("---");
//            System.out.println(cur_table.get(i).Kind);
//            System.out.println(cur_table.get(i).Name);
//            System.out.println("---");
//        }

        //label后的语句
        ASTStatement stmt = (ASTStatement) labeledStat.stat;
        stmt.accept(this);
    }

    //goto语句
    @Override
    public void visit(ASTGotoStatement gotoStat) throws Exception {
        ASTIdentifier id = (ASTIdentifier) gotoStat.label;
        //检查goto标识符是否定义
        int flag = check_notdefine(id);
        if(flag==0){
            System.out.println("ES07 >> Label: "+id.value+" is not defined.");
        }
    }

    //return语句
    @Override
    public void visit(ASTReturnStatement returnStat) throws Exception {
        //有return，更新is_return标志
        is_return=1;
    }

    //语句块
    @Override
    public void visit(ASTCompoundStatement compoundStat) throws Exception {
        for(int i=0;i<(compoundStat.blockItems.size());i++){
            ASTNode node = compoundStat.blockItems.get(i);
            convert2stmt(node);
        }
    }

    public void convert2stmt(ASTNode node)throws Exception{
        String tmp_type = node.getType();
        if(tmp_type.equals("Declaration")){
            ASTDeclaration dc = (ASTDeclaration)node;
            dc.accept(this);
        }
        //语句块
        else if(tmp_type.equals("CompoundStatement")){
            ASTCompoundStatement cstmt = (ASTCompoundStatement) node;
            MySemanticVisitor visitor_2=new MySemanticVisitor();
            cstmt.accept(visitor_2);

            //有封闭作用域，将符号表加入上一级作用域
            SymbolTable sub_block = new SymbolTable();
            sub_block.Kind = "sub_block";
            sub_block.sub_table=visitor_2.cur_table;
            this.cur_table.add(sub_block);

        }else if(tmp_type.equals("Expression")){

        }else if(tmp_type.equals("BreakStatement")){
            //break语句不在循环中
            if(this.is_in_loop!=1){
                System.out.println("ES03 >> BreakStatement:must be in a LoopStatement.");
            }
        }else if (tmp_type.equals("IterationStatement")){
            ASTIterationStatement istmt = (ASTIterationStatement) node;
            istmt.accept(this);
        }else if(tmp_type.equals("ExpressionStatement")){
            ASTExpressionStatement estmt = (ASTExpressionStatement) node;
            estmt.accept(this);
        }
        else if(tmp_type.equals("LabeledStatement")){
            ASTLabeledStatement lbstmt = (ASTLabeledStatement) node;
            lbstmt.accept(this);
        }
        else if(tmp_type.equals("ReturnStatement")){
            ASTReturnStatement rtstmt = (ASTReturnStatement) node;
            rtstmt.accept(this);
        }
    }

    @Override
    public void visit(ASTToken token) throws Exception {

    }

    @Override
    public void visit(ASTTypename typename) throws Exception {

    }

    @Override
    public void visit(ASTStatement statement) throws Exception {

    }

    @Override
    public void visit(ASTCharConstant charConst) throws Exception {

    }

    @Override
    public void visit(ASTArrayAccess arrayAccess) throws Exception {

    }

    @Override
    public void visit(ASTCompilationUnit program) throws Exception {

    }

    @Override
    public void visit(ASTBreakStatement breakStat) throws Exception {

    }

    @Override
    public void visit(ASTFloatConstant floatConst) throws Exception {

    }

    @Override
    public void visit(ASTIntegerConstant intConst) throws Exception {

    }

    @Override
    public void visit(ASTMemberAccess memberAccess) throws Exception {

    }

    @Override
    public void visit(ASTStringConstant stringConst) throws Exception {

    }

    @Override
    public void visit(ASTUnaryTypename unaryTypename) throws Exception {

    }

    @Override
    public void visit(ASTCastExpression castExpression) throws Exception {

    }

    @Override
    public void visit(ASTArrayDeclarator arrayDeclarator) throws Exception {

    }

    @Override
    public void visit(ASTUnaryExpression unaryExpression) throws Exception {

    }

    @Override
    public void visit(ASTSelectionStatement selectionStat) throws Exception {

    }

    @Override
    public void visit(ASTContinueStatement continueStatement) throws Exception {

    }

    @Override
    public void visit(ASTPostfixExpression postfixExpression) throws Exception {

    }

    @Override
    public void visit(ASTConditionExpression conditionExpression) throws Exception {

    }

    @Override
    public void visit(ASTIterationDeclaredStatement iterationDeclaredStat) throws Exception {

    }

}
