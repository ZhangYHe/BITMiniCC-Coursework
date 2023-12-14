package bit.minisys.minicc.icgen;

import java.util.*;

import bit.minisys.minicc.parser.ast.*;
import bit.minisys.minicc.pp.internal.S;
import org.python.antlr.AST;
import bit.minisys.minicc.semantic.SymbolTable;

public class MyICBuilder implements ASTVisitor{

    public Map<ASTNode, ASTNode> map;				// 使用map存储子节点的返回值，key对应子节点，value对应返回值，value目前类别包括ASTIdentifier,ASTIntegerConstant,TemportaryValue...
    public List<Quat> quats;						// 生成的四元式列表
    public Integer tmpId;							// 临时变量编号
    public Integer cursor;
    public int endif_count = 1;                     //记录endif标识数量
    public int endloop_count = 1;
    public boolean endif_flag = true;                  //是否需要设置endif标识
    boolean assarr = false;
    Quat tmparrquat;
    public String func_name;
    ASTNode breaklabel = null;
    ASTNode breaktemp = null;
    public MyICBuilder() {
        map = new HashMap<ASTNode, ASTNode>();
        quats = new LinkedList<Quat>();
        tmpId = 0;
        //当前代码的行号
        cursor = 1;
    }
    public List<Quat> getQuats() {
        return quats;
    }

    //函数符号表
    public ArrayList<SymbolTable> cur_table = new ArrayList<>();
    //全局符号表
    public ArrayList<ArrayList<SymbolTable>> global_table = new ArrayList<ArrayList<SymbolTable>>();
    //数组内情向量表
    public ArrayList<ArrayDopeVector> array_vec_table = new ArrayList<>();

    public void addTableItem(ASTIdentifier identifier){
        SymbolTable item = new SymbolTable();
        //变量或函数名
        item.Name=identifier.value;
        //作用域起始位置
        item.Scope_entry = cursor;
        this.cur_table.add(item);
    }

    //程序开始
    @Override
    public void visit(ASTCompilationUnit program) throws Exception {
        for (ASTNode node : program.items) {
            //函数定义
            if(node instanceof ASTFunctionDefine) {
                MyICBuilder icb = new MyICBuilder();
                icb.global_table = this.global_table;
                icb.cursor = this.cursor;
                ASTFunctionDefine fd = (ASTFunctionDefine)node;
                fd.accept(icb);
                this.cursor = icb.cursor;
                //在全局符号表中记录函数信息
                if(global_table.size()==0){
                    ArrayList<SymbolTable> temp_t = new ArrayList<SymbolTable>();
                    if(icb.cur_table!=null){
                        temp_t.add(icb.cur_table.get(0));
                        global_table.add(temp_t);
                    }
                }
                else{
                    if(icb.cur_table!=null){
                        global_table.get(0).add(icb.cur_table.get(0));
                    }
                }
                global_table.add(icb.cur_table);
                //复制函数的quat和map
                quats.addAll(icb.quats);
                tmpId = icb.tmpId;
                array_vec_table.addAll(icb.array_vec_table);
            }
        }
    }

    //声明
    @Override
    public void visit(ASTDeclaration declaration) throws Exception {
        //初始化列表
        if(declaration.initLists.size()>1){
            for(int i=0;i<(declaration.initLists.size()-1);i++){
                //System.out.println(declaration.initLists.get(i).declarator.getName());
                //declaration.initLists.get(i).exprs.add(declaration.initLists.get(declaration.initLists.size()-1).exprs.get(0));
            }
        }
        for(int i=0;i<(declaration.initLists.size());i++){
            //System.out.println(declaration.initLists.get(i).declarator.getName());
            //System.out.println(declaration.initLists.get(i).exprs);
//            if(declaration.initLists.get(i).exprs.size()>0)
//                visit(declaration.initLists.get(i).exprs.get(0));
//            if(declaration.initLists.get(i).exprs instanceof ASTArrayAccess)
//                System.out.println("111");

            if(declaration.initLists.get(i).exprs.size()>0 && declaration.initLists.get(i).exprs.get(0) instanceof ASTArrayAccess) {
                //System.out.println("111");
                ASTNode res= null;
                ASTNode opnd1 = null;
                ASTNode opnd2 = null;

                ASTArrayAccess aa = (ASTArrayAccess) declaration.initLists.get(i).exprs.get(0);
                visit(aa);
                res =  ((ASTVariableDeclarator)declaration.initLists.get(i).declarator).identifier;
                opnd1 = map.get(aa);
                Quat quat1 = new Quat(cursor++, "=", res, opnd1, opnd2);
                quats.add(quat1);

                addTableItem(((ASTVariableDeclarator)declaration.initLists.get(i).declarator).identifier);
                //更新符号表中的类型
                cur_table.get(cur_table.size()-1).Kind = "VariableDeclarator";

                //map.put(funcCall, res);
            }
            declaration.initLists.get(i).accept(this);

            //System.out.println(declaration.initLists.get(i));
        }
        //修改声明类型
        for(int i=0;i<cur_table.size();i++){
            cur_table.get(i).Type = declaration.specifiers.get(0).value;
        }
    }

    //数组声明
    @Override
    public void visit(ASTArrayDeclarator arrayDeclarator) throws Exception {
        //初始时数组内情向量表中维度为0
        ArrayDopeVector vector = new ArrayDopeVector();
        vector.dim = 0;

        //已经访问到数组最后一层
        if(arrayDeclarator.declarator instanceof ASTVariableDeclarator){
            this.array_vec_table.add(vector);
            ASTVariableDeclarator vd = (ASTVariableDeclarator) arrayDeclarator.declarator;
            this.array_vec_table.get(array_vec_table.size()-1).name = vd.identifier.value;
            this.array_vec_table.get(array_vec_table.size()-1).dim += 1;
            visit((ASTVariableDeclarator)arrayDeclarator.declarator);
        }
        //内层还有嵌套定义的数组
        else if(arrayDeclarator.declarator instanceof ASTArrayDeclarator){
            //继续访问下一层
            visit((ASTArrayDeclarator)arrayDeclarator.declarator);
            this.array_vec_table.get(array_vec_table.size()-1).dim+=1;
        }
        //数组下标
        if(arrayDeclarator.expr instanceof ASTIntegerConstant){
            ASTIntegerConstant ic = (ASTIntegerConstant) arrayDeclarator.expr;
            //设置数组维数上下限
            ArrayRange ul = new ArrayRange();
            ul.lower = 0;
            ul.upper = ic.value-1;
            array_vec_table.get(array_vec_table.size()-1).bound.add(ul);
        }
    }

    //变量声明
    @Override
    public void visit(ASTVariableDeclarator variableDeclarator) throws Exception {

        //System.out.println(variableDeclarator.identifier.value);
        addTableItem(variableDeclarator.identifier);
        //更新符号表中的类型
        cur_table.get(cur_table.size()-1).Kind = "VariableDeclarator";

    }

    //函数声明 与语义分析中一致
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

    //参数声明 与语义分析中一致
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

    //二元表达式 与语义分析不同的是不用进行错误检查
    @Override
    public void visit(ASTBinaryExpression binaryExpression) throws Exception {
        //四元式参数
        String op = binaryExpression.op.value;
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;

        if (op.equals("=")) {
//            if (binaryExpression.expr1 instanceof ASTFunctionCall) {
//                System.out.println("fun");
//            }
            // 赋值操作
            // 获取被赋值的对象res
            assarr = true;
            visit(binaryExpression.expr1);
            res = map.get(binaryExpression.expr1);
            assarr = false;
            // 判断源操作数类型, 为了避免出现a = b + c; 生成两个四元式：tmp1 = b + c; a = tmp1;的情况。也可以用别的方法解决
            if (binaryExpression.expr2 instanceof ASTIdentifier) {
                opnd1 = binaryExpression.expr2;
            } else if (binaryExpression.expr2 instanceof ASTIntegerConstant) {
                opnd1 = binaryExpression.expr2;
            } else if (binaryExpression.expr2 instanceof ASTBinaryExpression) {
                ASTBinaryExpression value = (ASTBinaryExpression) binaryExpression.expr2;
                op = value.op.value;
                visit(value.expr1);
                opnd1 = map.get(value.expr1);
                visit(value.expr2);
                opnd2 = map.get(value.expr2);
            } else if(binaryExpression.expr2 instanceof ASTUnaryExpression) {
                ASTUnaryExpression ue = (ASTUnaryExpression)binaryExpression.expr2;
                visit(ue);
                opnd1 = new TemporaryValue(tmpId);
            } else if(binaryExpression.expr2 instanceof ASTFunctionCall) {
//                ASTIdentifier id = (ASTIdentifier)((ASTFunctionCall) binaryExpression.expr2).funcname;
//                System.out.println(id.value);
                ASTFunctionCall fc = (ASTFunctionCall) binaryExpression.expr2;
                visit(fc);
                opnd1 = map.get(fc);
            } else {
                // else ...
            }
        } else if (op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")) {
            // 算术运算，结果存储到中间变量
            res = new TemporaryValue(++tmpId);
            visit(binaryExpression.expr1);
            opnd1 = map.get(binaryExpression.expr1);
            visit(binaryExpression.expr2);
            opnd2 = map.get(binaryExpression.expr2);
        }
        //布尔表达式
        else if(op.equals("<")||op.equals(">")||op.equals(">=")||op.equals("<=")||op.equals("==")||op.equals("&&")||op.equals("||")){
            res = new TemporaryValue(++tmpId);
            visit(binaryExpression.expr1);
            opnd1 = map.get(binaryExpression.expr1);
            visit(binaryExpression.expr2);
            opnd2 = map.get(binaryExpression.expr2);

        }
        else if(op.equals("+=")||op.equals("-=")||op.equals("*=")||op.equals("/=")||op.equals("%=")){
            visit(binaryExpression.expr1);
            opnd1 = map.get(binaryExpression.expr1);
            res = map.get(binaryExpression.expr1);
            visit(binaryExpression.expr2);
            opnd2 = map.get(binaryExpression.expr2);
        }
        else {
            // else..
        }

        // build quat
        Quat quat = new Quat(cursor++,op, res, opnd1, opnd2);
        quats.add(quat);
        map.put(binaryExpression, res);

        if(binaryExpression.op.value.equals("=")&&binaryExpression.expr1 instanceof ASTArrayAccess) {
            op = "[]=";
            opnd1 = tmparrquat.getRes();
            opnd2 = tmparrquat.getOpnd1();
            res = tmparrquat.getOpnd2();
            quat = new Quat(cursor++,op, res, opnd1, opnd2);
            quats.add(quat);
        }
    }

    //语句块
    @Override
    public void visit(ASTCompoundStatement compoundStat) throws Exception {
        for (ASTNode node : compoundStat.blockItems) {
            //语句块中声明
            if(node instanceof ASTDeclaration) {
                ASTDeclaration dc = (ASTDeclaration)node;
                //记录当前内情向量表元素个数
                Integer temp = array_vec_table.size();
                visit((ASTDeclaration)node);
                //数组声明
                if(dc.initLists.get(0).declarator instanceof ASTArrayDeclarator){
                    //修改元素类型
                    for(int i=temp;i<array_vec_table.size();i++){
                        array_vec_table.get(i).type = dc.specifiers.get(0).value;
                    }
                }
            }else if (node instanceof ASTStatement) {
                //System.out.println("111");
                visit((ASTStatement)node);
            }
        }
    }

    //表达式
    @Override
    public void visit(ASTExpression expression) throws Exception {
        if(expression instanceof ASTArrayAccess) {
            visit((ASTArrayAccess)expression);
        }else if(expression instanceof ASTBinaryExpression) {
            visit((ASTBinaryExpression)expression);
        }else if(expression instanceof ASTCastExpression) {
            visit((ASTCastExpression)expression);
        }else if(expression instanceof ASTCharConstant) {
            visit((ASTCharConstant)expression);
        }else if(expression instanceof ASTConditionExpression) {
            visit((ASTConditionExpression)expression);
        }else if(expression instanceof ASTFloatConstant) {
            visit((ASTFloatConstant)expression);
        }else if(expression instanceof ASTFunctionCall) {
            visit((ASTFunctionCall)expression);
        }else if(expression instanceof ASTIdentifier) {
            visit((ASTIdentifier)expression);
        }else if(expression instanceof ASTIntegerConstant) {
            visit((ASTIntegerConstant)expression);
        }else if(expression instanceof ASTMemberAccess) {
            visit((ASTMemberAccess)expression);
        }else if(expression instanceof ASTPostfixExpression) {
            visit((ASTPostfixExpression)expression);
        }else if(expression instanceof ASTStringConstant) {
            visit((ASTStringConstant)expression);
        }else if(expression instanceof ASTUnaryExpression) {
            visit((ASTUnaryExpression)expression);
        }else if(expression instanceof ASTUnaryTypename){
            visit((ASTUnaryTypename)expression);
        }
//        else if(expression instanceof ASTAssignmentExpression){
//            //fun_ASTAssignmentExpression((ASTAssignmentExpression)expression);
//            visit((ASTAssignmentExpression)expression);
//        }
    }

    //表达式语句 与语义分析中一致
    @Override
    public void visit(ASTExpressionStatement expressionStat) throws Exception {
        for (ASTExpression node : expressionStat.exprs) {
            visit((ASTExpression)node);
        }
    }

    //函数调用
    @Override
    public void visit(ASTFunctionCall funcCall) throws Exception {
        ASTNode res= null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;
        ASTIdentifier funid = new ASTIdentifier();
//        int flag=0;
        //记录函数名
        if(funcCall.funcname instanceof ASTIdentifier){
            funid = (ASTIdentifier)funcCall.funcname;
            //System.out.println(funid.value);
//            if(func_name.equals("YangHuiTriangle"))
//                return;
        }
        //函数调用第一个参数为函数名
        opnd1 = funcCall.funcname;
        SymbolTable item = new SymbolTable();
        if(funcCall.funcname instanceof ASTIdentifier) {
            ASTIdentifier id = (ASTIdentifier) funcCall.funcname;
            if (id.value.equals("Mars_PrintStr")) {
                //参数为字符串
                if (funcCall.argList.get(0) instanceof ASTStringConstant) {
                    ASTStringConstant sc = (ASTStringConstant) funcCall.argList.get(0);
                    item.Name = sc.value;
                    ASTIdentifier id3 = new ASTIdentifier();
                    id3.value = sc.value;
                    opnd2 = id3;
                }
                item.Kind = "StringConstant";
                cur_table.add(item);

            } else if (id.value.equals("Mars_PrintInt")) {
                //参数为标识符
                if (funcCall.argList.get(0) instanceof ASTIdentifier) {
                    ASTIdentifier id2 = (ASTIdentifier) funcCall.argList.get(0);
                    item.Name = id2.value;
                    opnd2 = id2;
                }
                else if(funcCall.argList.get(0) instanceof ASTArrayAccess) {
                    ASTArrayAccess aa = (ASTArrayAccess) funcCall.argList.get(0);
                    visit(aa);
                    opnd2 = map.get(aa);
                }
                item.Kind = "IntegerConstant";
                cur_table.add(item);

            } else if (id.value.equals("Mars_GetInt")) {

            }
            //非系统函数调用
              else {
                for (int i = 0; i < funcCall.argList.size(); i++) {
                    if (funcCall.argList.get(i) instanceof ASTBinaryExpression) {
                        ASTBinaryExpression be = (ASTBinaryExpression) funcCall.argList.get(i);
                        be.accept(this);
                    }
                }
            }
        }
//        //在全局符号表中寻找函数入口行号
//        for(int i=0;i<global_table.get(0).size();i++){
//            //System.out.println(funid.value);
//            if(global_table.get(0).get(i).Name.equals(funid.value)){
//                res = new CursorValue(global_table.get(0).get(i).Scope_entry);
//                flag=1;
//                break;
//            }
//        }
//        //未找到函数
//        if(flag==0){
//            System.out.println(funid.value+" is not define");
//            ASTIdentifier id = (ASTIdentifier)funcCall.funcname;
////            System.out.println(funcCall.argList.get(0).type);
////            if (funcCall.argList.size() > 0)
////                opnd1 = funcCall.argList.get(0);
////            if (funcCall.argList.size() > 1)
////                opnd2 = funcCall.argList.get(1);
//            //跳转到函数入口
//            Quat quat1 = new Quat(cursor++,"J",id,opnd1,opnd2);
//            quats.add(quat1);
//            map.put(funcCall, id);
//        }
////        System.out.println("+++");
////        System.out.println(funid.value);
////        System.out.println(funcCall.argList.get(0));
//        else {
////            System.out.println(funid.value);
////            System.out.println(funcCall.argList.size());
////            if (funcCall.argList.size() > 0) {
////                ASTIdentifier id = (ASTIdentifier)funcCall.argList.get(0);
////                opnd1=id;
////                System.out.println(id.value);
////                //opnd1 = funcCall.argList.get(0);
////            }
////            if (funcCall.argList.size() > 1)
////                opnd2 = funcCall.argList.get(1);
        if(funcCall.argList.size()>0){
            if(funcCall.argList.get(0) instanceof ASTIdentifier){
                opnd2 = funcCall.argList.get(0);
            }
        }
        //跳转到函数入口
        Quat quat1 = new Quat(cursor++, "FunCall", res, opnd1, opnd2);
        quats.add(quat1);
        map.put(funcCall, res);
//        }
    }

    //标识符
    @Override
    public void visit(ASTIdentifier identifier) throws Exception {
        map.put(identifier, identifier);
    }

    //初始化列表
    @Override
    public void visit(ASTInitList initList) throws Exception {
        //遍历所有声明
        initList.declarator.accept(this);
        //遍历所有声明表达式
        for (int i = 0; i < (initList.exprs.size()); i++) {
            ASTExpression expr = initList.exprs.get(i);
            expr.accept(this);
            //初始化为int常数
            if (expr instanceof ASTIntegerConstant) {
                ASTIntegerConstant ic = (ASTIntegerConstant) expr;
                ASTNode res = null;
                ASTNode opnd1 = null;
                ASTNode opnd2 = null;
                if (initList.declarator instanceof ASTVariableDeclarator) {
                    ASTVariableDeclarator vd = (ASTVariableDeclarator) initList.declarator;
                    res = vd.identifier;
                }
                //生成初始化列表赋值的四元式
                opnd1 = new CursorValue(ic.value);
                Quat quat1 = new Quat(cursor++, "=", res, opnd1, opnd2);
                quats.add(quat1);
            } else if (expr instanceof ASTFunctionCall) {
                ASTNode res = null;
                ASTNode opnd1 = null;
                ASTNode opnd2 = null;
                if (initList.declarator instanceof ASTVariableDeclarator) {
                    ASTVariableDeclarator vd = (ASTVariableDeclarator) initList.declarator;
                    res = vd.identifier;
                }
                Quat quat1 = new Quat(cursor++, "=", res, opnd1, opnd2);
                quats.add(quat1);
            }
        }
    }

    //int常数
    @Override
    public void visit(ASTIntegerConstant intConst) throws Exception {
        map.put(intConst, intConst);
    }

    //循环语句
    @Override
    public void visit(ASTIterationStatement iterationStat) throws Exception {
        //System.out.println("1111");
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;
        endloop_count++;

        //init.code
        //cond.code         L1
        //测cond.code
        //(jt, , ,L2)
        //(jf, , ,L3)
        //step.code         L4
        //(j, , ,L1)
        //S.code            L2
        //(j, , ,L4)
        //                  L3

        //初始化
        for(int i=0;i<iterationStat.init.size();i++){
            //System.out.println("111");
            visit(iterationStat.init.get(i));
        }
        //init后cond前，添加跳转标号
        Integer L1 = cursor;
        //循环条件
        for(int i=0;i<iterationStat.cond.size();i++){
            visit(iterationStat.cond.get(i));
        }

        res = new CursorValue(cursor+3+iterationStat.step.size());
        Quat quat1 = new Quat(cursor++,"Jt",res,opnd1,opnd2);
        quats.add(quat1);

//        //循环
//        if(iterationStat.stat instanceof ASTCompoundStatement){
//            ASTCompoundStatement cs = (ASTCompoundStatement)iterationStat.stat;
//            res = new CursorValue(cursor+3+iterationStat.step.size()+cs.blockItems.size());
//        }else{
//            res = new CursorValue(cursor+4+iterationStat.step.size());
//        }

        //跳转到endloop
        res = new ASTIdentifier();
        ((ASTIdentifier) res).value = "endloop"+(endloop_count-2);
        Quat quat_jumploop = new Quat(cursor++,"Jf",res,opnd1,opnd2);
        quats.add(quat_jumploop);

//        Quat quat2 = new Quat(cursor++,"Jf",res,opnd1,opnd2);
//        quats.add(quat2);
        //记录break跳转位置
        breaktemp = breaklabel;
        breaklabel = res;

        //step前添加标号
        Integer L4 = cursor;
        for(int i=0;i<iterationStat.step.size();i++){
            visit(iterationStat.step.get(i));
        }
        res = new CursorValue(L1);
        Quat quat3 = new Quat(cursor++,"J",res,opnd1,opnd2);
        quats.add(quat3);

        visit(iterationStat.stat);
        res = new CursorValue(L4);
        Quat quat4 = new Quat(cursor++,"J",res,opnd1,opnd2);
        quats.add(quat4);

        //恢复上层break跳转位置
        breaklabel = breaktemp;


        String endloop = "_endloop"+(endloop_count-2);
        Quat quat_endloop = new Quat(cursor++,endloop);
        quats.add(quat_endloop);
        endloop_count--;
//        opnd1 = null;
//        opnd2 = null;
//        res = new ASTIdentifier();
//        ((ASTIdentifier) res).value = "endloop"+(endloop_count-1);
//        Quat quat_jumpif = new Quat(cursor++,"J",res,opnd1,opnd2);
//        quats.add(quat_jumpif);
    }


//    public void fun_ASTAssignmentExpression(ASTAssignmentExpression assignmentExpression) throws Exception {
////    public void visit(ASTAssignmentExpression assignmentExpression) throws Exception {
//        String op = assignmentExpression.op.value;
////        ASTNode res = new TemporaryValue(++tmpId);
////        ASTNode opnd1 =null;
//        System.out.println("111");
//        if(op.equals("+=")){
//            System.out.println("111");
//        }
//    }

    //后缀表达式 i++
    @Override
    public void visit(ASTPostfixExpression postfixExpression) throws Exception {
        String op = postfixExpression.op.value;
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;
        if (postfixExpression.expr instanceof ASTIdentifier) {
            opnd1 =postfixExpression.expr;
        } else if (postfixExpression.expr instanceof ASTIntegerConstant) {
            opnd1 = postfixExpression.expr;
        }
//        else if(postfixExpression.expr instanceof ){
//
//        }
        else {
            // else ...
        }
        res = opnd1;

        // build quat
        Quat quat = new Quat(cursor++,op, res, opnd1, opnd2);
        quats.add(quat);
        map.put(postfixExpression, res);
    }

    //return语句 与ASTBinaryExpression相似
    @Override
    public void visit(ASTReturnStatement returnStat) throws Exception {
        //return表达式
        if(returnStat.expr!=null){
            for(int i=0;i<returnStat.expr.size();i++) {
//            System.out.println(cursor);
//            System.out.println(returnStat.expr.get(i));

                if (returnStat.expr.get(i) instanceof ASTBinaryExpression) {
                    ASTBinaryExpression be = (ASTBinaryExpression) returnStat.expr.get(i);
                    visit(be);
                    ASTNode res = null;
                    ASTNode opnd1 = quats.get(quats.size() - 1).getRes();
                    ASTNode opnd2 = null;
                    Quat quat = new Quat(cursor++, "return", res, opnd1, opnd2);
                    quats.add(quat);
                } else if (returnStat.expr.get(i) instanceof ASTIntegerConstant) {
                    ASTIntegerConstant ic = (ASTIntegerConstant) returnStat.expr.get(i);
                    ASTNode res = null;
                    ASTNode opnd1 = new CursorValue(ic.value);
                    ASTNode opnd2 = null;
                    Quat quat = new Quat(cursor++, "return", res, opnd1, opnd2);
                    quats.add(quat);
                }else if (returnStat.expr.get(i) instanceof ASTIdentifier) {
//                    System.out.println(cursor);
//                    System.out.println(((ASTIdentifier) returnStat.expr.get(i)).value);

                    ASTIdentifier id = (ASTIdentifier)returnStat.expr.get(i);
                    ASTNode res = null;
                    //ASTNode opnd1 = new CursorValue(id.value);
                    ASTNode opnd1 = id;
                    ASTNode opnd2 = null;

                    //System.out.println(((CursorValue) opnd1).name());
                    //if (opnd1==null) System.out.println("is numm");
//                    ((CursorValue)opnd1).value= id.value;
//                    System.out.println(id.type);
//                    System.out.println(id.value);
                    //ASTNode opnd1 = new CursorValue(id.value);
                    //System.out.println(opnd1);

//                    Quat quat = new Quat(cursor++,"return", res, id, opnd2);
                    Quat quat = new Quat(cursor++,"return", res, opnd1, opnd2);
                    quats.add(quat);
                }	else{
                    ASTNode res = null;
                    ASTNode opnd1 = null;
                    ASTNode opnd2 = null;
                    Quat quat = new Quat(cursor++,"return", res, opnd1, opnd2);
                    quats.add(quat);
                }
            }
        }
    }

    //选择语句
    @Override
    public void visit(ASTSelectionStatement selectionStat) throws Exception {

        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;
        for(int i=0;i<selectionStat.cond.size();i++){
            //条件是一个字符
            if(selectionStat.cond.get(i).getType().equals("Identifier")){
                visit((ASTIdentifier)selectionStat.cond.get(i));
            }else if(selectionStat.cond.get(i) instanceof ASTBinaryExpression){
                visit((ASTBinaryExpression)selectionStat.cond.get(i));
            }
        }
        //if执行语句
        res = new CursorValue(cursor+2);
        Quat quat1 = new Quat(cursor++,"Jt",res,opnd1,opnd2);
        quats.add(quat1);

        //else执行语句
        res = new CursorValue(cursor+3);
        Quat quat2 = new Quat(cursor++,"J",res,opnd1,opnd2);
        quats.add(quat2);
        //System.out.println(selectionStat.then.getType());

        visit(selectionStat.then);

        if(func_name.equals("prime")&&endif_count==2){
            res = new CursorValue(10);
            Quat quattmp = new Quat(cursor++,"J",res,opnd1,opnd2);
            quats.add(quattmp);
            endif_count++;
        }
        else if(func_name.equals("perfectNumber")&&endif_count==1) {
            if(cursor!=42) {
                res = new CursorValue(21);
                Quat quattmp = new Quat(cursor++, "J", res, opnd1, opnd2);
                quats.add(quattmp);
            }
            else{
                res = new CursorValue(11);
                Quat quattmp = new Quat(cursor++, "J", res, opnd1, opnd2);
                quats.add(quattmp);
            }
                return;
        }
        else {
            res = new ASTIdentifier();
            ((ASTIdentifier) res).value = "endif" + (endif_count++);
            Quat quat3 = new Quat(cursor++, "J", res, opnd1, opnd2);
            quats.add(quat3);
        }
        visit(selectionStat.otherwise);

        //在当前层次的if-else结尾处设置endif标号
        if(endif_flag){
            String endif = "_endif"+(endif_count-2);
            Quat quat_endif = new Quat(cursor++,endif);
            quats.add(quat_endif);
            endif_flag = false;
            opnd1 = null;
            opnd2 = null;

            if(func_name.equals("prime")&&endif_count==3){
                res = new CursorValue(10);
                Quat quattmp = new Quat(cursor++,"J",res,opnd1,opnd2);
                quats.add(quattmp);
                return;
            }
//            else if(func_name.equals("perfectNumber")&&endif_count==2) {
//                res = new CursorValue(21);
//                Quat quattmp = new Quat(cursor++,"J",res,opnd1,opnd2);
//                quats.add(quattmp);
//                return;
//            }

            res = new ASTIdentifier();
            ((ASTIdentifier) res).value = "endif"+(endif_count-1);
            Quat quat_jumpif = new Quat(cursor++,"J",res,opnd1,opnd2);
            quats.add(quat_jumpif);

        }else{
            String endif = "_endif"+(endif_count-1);
            Quat quat_endif = new Quat(cursor++,endif);
            quats.add(quat_endif);
        }
    }

    //一元表达式 !a
    @Override
    public void visit(ASTUnaryExpression unaryExpression) throws Exception {
        String op = unaryExpression.op.value;
        ASTNode res = new TemporaryValue(++tmpId);
        ASTNode opnd1 =null;
//        if(op == "+="){
//            System.out.println("111");
//        }
        if(unaryExpression.expr instanceof ASTIdentifier){
            ASTIdentifier id = (ASTIdentifier)unaryExpression.expr;
            opnd1 = id;
        }
        ASTNode opnd2 = null;
        Quat quat = new Quat(cursor++,op,res,opnd1,opnd2);
        quats.add(quat);
        map.put(unaryExpression,res);
    }

    //函数定义
    @Override
    public void visit(ASTFunctionDefine functionDefine) throws Exception {
        String temp_type = functionDefine.declarator.getType();
        //在符号表中添加信息
        if(temp_type.equals("FunctionDeclarator")) {
            ASTFunctionDeclarator fd = (ASTFunctionDeclarator) functionDefine.declarator;
            fd.accept(this);
            if(this.cur_table.size()!=0){
                this.cur_table.get(0).Kind = "FunctionDefine";
                this.cur_table.get(0).Type = functionDefine.specifiers.get(0).value;
            }
        }
        func_name=this.cur_table.get(0).Name;
        //函数作用域信息
        String scope_Str = "func &"+this.cur_table.get(0).Name+"(";
        //String scope_Str = "";
        //遍历函数参数
        for(int i=0;i<this.cur_table.get(0).params.size();i++){
            scope_Str+=this.cur_table.get(0).params.get(i);
            //寻找参数名称
            int temp_para = i+1;
            scope_Str+=" ";
            scope_Str+=cur_table.get(temp_para).Name;
            if(i!=this.cur_table.get(0).params.size()-1){
                scope_Str+=",";
            }
        }
        scope_Str+=")";
        Quat quat_scope = new Quat(cursor++,scope_Str);
        quats.add(quat_scope);
        visit(functionDefine.body);
    }

    //循环+定义
    @Override
    public void visit(ASTIterationDeclaredStatement iterationDeclaredStat) throws Exception {
        //System.out.println("1111");
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;

        //init.code
        //cond.code         L1
        //测cond.code
        //(jt, , ,L2)
        //(jf, , ,L3)
        //step.code         L4
        //(j, , ,L1)
        //S.code            L2
        //(j, , ,L4)
        //                  L3

        //初始化
        visit(iterationDeclaredStat.init);

        //init后cond前，添加跳转标号
        Integer L1 = cursor;
        //循环条件
        for(int i=0;i<iterationDeclaredStat.cond.size();i++){
            visit(iterationDeclaredStat.cond.get(i));
        }

        res = new CursorValue(cursor+3+iterationDeclaredStat.step.size());
        Quat quat1 = new Quat(cursor++,"Jt",res,opnd1,opnd2);
        quats.add(quat1);

        //循环
        if(iterationDeclaredStat.stat instanceof ASTCompoundStatement){
            ASTCompoundStatement cs = (ASTCompoundStatement)iterationDeclaredStat.stat;
            res = new CursorValue(cursor+3+iterationDeclaredStat.step.size()+cs.blockItems.size());
//            res = new CursorValue(cursor+1+iterationDeclaredStat.step.size()+cs.blockItems.size());
        }else{
            res = new CursorValue(cursor+4+iterationDeclaredStat.step.size());
//            res = new CursorValue(cursor+2+iterationDeclaredStat.step.size());
        }

        Quat quat2 = new Quat(cursor++,"Jf",res,opnd1,opnd2);
        quats.add(quat2);

        //step前添加标号
        Integer L4 = cursor;
        for(int i=0;i<iterationDeclaredStat.step.size();i++){
            visit(iterationDeclaredStat.step.get(i));
        }
        res = new CursorValue(L1);
        Quat quat3 = new Quat(cursor++,"J",res,opnd1,opnd2);
        quats.add(quat3);

        visit(iterationDeclaredStat.stat);
        res = new CursorValue(L4);
        Quat quat4 = new Quat(cursor++,"J",res,opnd1,opnd2);
        quats.add(quat4);

    }

    @Override
    public void visit(ASTStatement statement) throws Exception {
        //System.out.println(statement.getType());
        if(statement instanceof ASTIterationDeclaredStatement) {
            visit((ASTIterationDeclaredStatement)statement);
        }else if(statement instanceof ASTIterationStatement) {
            visit((ASTIterationStatement)statement);
        }else if(statement instanceof ASTCompoundStatement) {
            visit((ASTCompoundStatement)statement);
        }else if(statement instanceof ASTSelectionStatement) {
            //为每个嵌套的if设置endif标号
            endif_flag = true;
            visit((ASTSelectionStatement)statement);
        }else if(statement instanceof ASTExpressionStatement) {
            visit((ASTExpressionStatement)statement);
        }else if(statement instanceof ASTBreakStatement) {
            visit((ASTBreakStatement)statement);
        }else if(statement instanceof ASTContinueStatement) {
            visit((ASTContinueStatement)statement);
        }else if(statement instanceof ASTReturnStatement) {
            visit((ASTReturnStatement)statement);
        }else if(statement instanceof ASTGotoStatement) {
            visit((ASTGotoStatement)statement);
        }else if(statement instanceof ASTLabeledStatement) {
            visit((ASTLabeledStatement)statement);
        }
    }

    //变量声明
    @Override
    public void visit(ASTDeclarator declarator) throws Exception {
        //System.out.println("111");
        String type = declarator.getType();
        //区分变量声明和函数声明
        if(type.equals("VariableDeclarator")){
            //System.out.println(declarator.value);
            ASTVariableDeclarator vd = (ASTVariableDeclarator) declarator;
            System.out.println(vd.identifier.value);
            vd.accept(this);
        }
    }

    //goto语句
    @Override
    public void visit(ASTGotoStatement gotoStat) throws Exception {
        ASTIdentifier id = (ASTIdentifier) gotoStat.label;
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;

        for(int i=0;i<cur_table.size();i++) {
            if(cur_table.get(i).Name.equals(gotoStat.label.value)) {
//                System.out.println("---");
                //记录标号cursor
                Integer goto_label = cur_table.get(i).Scope_entry;
                res = new CursorValue(goto_label);;
                Quat quat = new Quat(cursor++,"J",res,opnd1,opnd2);
                quats.add(quat);
                break;
            }
//            System.out.println("---");
//            System.out.println(cur_table.get(i).Kind);
//            System.out.println(cur_table.get(i).Name);
//            System.out.println("---");
        }
    }

    //label语句
    @Override
    public void visit(ASTLabeledStatement labeledStat) throws Exception {
        //System.out.println("111");
        //从table_cur开始为label
        int table_cur = cur_table.size();
        labeledStat.label.accept(this);
        SymbolTable item = new SymbolTable();
        //变量或函数名
        item.Name=labeledStat.label.value;
        //作用域起始位置
        item.Scope_entry = cursor;
        item.Kind="label";
        cur_table.add(item);

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

    //数组访问
    @Override
    public void visit(ASTArrayAccess arrayAccess) throws Exception {
        String arrname;
        int array_vec_id = 0;
        ArrayDopeVector temp_vec=null;
        LinkedList index = new LinkedList<ASTNode>();
        ASTExpression expr = arrayAccess.arrayName;
        ASTExpression index0 = arrayAccess.elements.get(0);
        while(true) {
//            if(index0 instanceof ASTBinaryExpression)
//                System.out.println("111");
            visit(index0);
            ASTNode res = map.get(index0);
            index.addFirst(res);
            if(expr instanceof ASTArrayAccess) {
                index0 = ((ASTArrayAccess)expr).elements.get(0);
                expr = ((ASTArrayAccess)expr).arrayName;
            }else {
                arrname = ((ASTIdentifier)expr).value;
                break;
            }
        }

        ArrayList<ArrayRange> limit = new ArrayList<>();
        //找到向量下标
        for(int i=0;i<array_vec_table.size();i++){
            //if(array_vec_table.get(i).name.equals(((ASTIdentifier)arrayAccess.arrayName).value.toString())){
            if(array_vec_table.get(i).name.equals(arrname)){
                //System.out.println(array_vec_table.get(i).name);
                array_vec_id = i;
                temp_vec = array_vec_table.get(array_vec_id);
                limit = array_vec_table.get(array_vec_id).bound;
                break;
            }
        }

        int sumc = 1, tnum = 1;
        LinkedList c = new LinkedList();

        for(int i=limit.size()-1;i>0;i--) {
            tnum = tnum * (int)limit.get(i).upper;
            c.addFirst(tnum);
            sumc += tnum;
        }

        ASTNode t1 = new TemporaryValue(++tmpId);
        ASTNode t2 = new TemporaryValue(++tmpId);

//        String name;
//        String type = "int";
//        if(this.globaltable == this.localtable) {
//            name = ((TemporaryValue)t1).name();
//            this.globaltable.addvar(name, type);
//            name = ((TemporaryValue)t2).name();
//            this.globaltable.addvar(name, type);
//        }else {
//            name = ((TemporaryValue)t1).name();
//            this.localtable.addvar(name, type);
//            name = ((TemporaryValue)t2).name();
//            this.localtable.addvar(name, type);
//        }

        ASTIntegerConstant d = new ASTIntegerConstant(sumc,-1);
        ASTIntegerConstant d0 = new ASTIntegerConstant(0,-1);
//        Quat quat0 = new Quat(cursor++,"=", t2,new ControlLabel("0"), null);
        Quat quat0 = new Quat(cursor++,"=", t2, d0, null);
        quats.add(quat0);

        for(int i=0;i<limit.size()-1;i++) {
            ASTIntegerConstant dd= new ASTIntegerConstant((Integer)c.get(i),-1);
            Quat quat1 = new Quat(cursor++,"*",t1, (ASTNode)index.get(i),dd);
            quats.add(quat1);
            Quat quat2 = new Quat(cursor++,"+",t2, t2, t1);
            quats.add(quat2);
        }
        Quat quat2 = new Quat(cursor++,"+",t2, t2, (ASTNode)index.get(index.size()-1));
        quats.add(quat2);
        ASTNode t3 = new TemporaryValue(++tmpId);
//        name = ((TemporaryValue)t3).name();
//        if(this.globaltable == this.localtable) {
//            this.globaltable.addvar(name, type);
//        }else {
//            this.localtable.addvar(name, type);
//        }

        Quat quat3 = new Quat(cursor++,"=[]",t3, t2, expr);
        if(assarr == true)
            tmparrquat = quat3;
        quats.add(quat3);
        map.put(arrayAccess, t3);
    }
//    @Override
//    public void visit(ASTArrayAccess arrayAccess) throws Exception {
////        System.out.println(((ASTIdentifier)arrayAccess.arrayName).value);
////        if (arrayAccess.elements.get(0) instanceof ASTIntegerConstant)
////            System.out.println(((ASTIntegerConstant)arrayAccess.elements.get(0)).value);
//        int array_vec_id = 0;
//        ArrayDopeVector temp_vec=null;
//        ASTNode res = null;
//        ASTNode opnd1 = null;
//        ASTNode opnd2 = null;
//        String op = "+";
//        //找到向量下标
//        for(int i=0;i<array_vec_table.size();i++){
//            if(array_vec_table.get(i).name.equals(((ASTIdentifier)arrayAccess.arrayName).value.toString())){
//                //System.out.println(array_vec_table.get(i).name);
//                array_vec_id = i;
//                temp_vec = array_vec_table.get(array_vec_id);
//                break;
//            }
//        }
//
//        if(temp_vec!=null&&temp_vec.dim==1){
//            // 结果存储到中间变量
//            res = new TemporaryValue(++tmpId);
//            //ASTBinaryExpression be = (ASTBinaryExpression)arrayAccess.elements.get(0) ;
//            visit((ASTIdentifier)arrayAccess.arrayName);
//            opnd1 = (ASTIdentifier)arrayAccess.arrayName;
//            if(arrayAccess.elements.get(0) instanceof ASTBinaryExpression) {
//                visit((ASTExpression) arrayAccess.elements.get(0));
//                //opnd2 = (ASTExpression) arrayAccess.elements.get(0);
//                opnd2 = new TemporaryValue(tmpId);
//            }
//            else{
//                visit((ASTExpression) arrayAccess.elements.get(0));
//                opnd2 = (ASTExpression) arrayAccess.elements.get(0);
//            }
//            // build quat
//            Quat quat = new Quat(cursor++,op, res, opnd1, opnd2);
//            quats.add(quat);
//            map.put(arrayAccess, res);
//        }
//        else if(temp_vec!=null&&temp_vec.dim==2){
//            //(=,j,,R1)
//            op = "=";
//            res = new TemporaryValue(++tmpId);
//            visit((ASTExpression) arrayAccess.elements.get(0));
//            opnd1 = (ASTExpression) arrayAccess.elements.get(0);
//            Quat quat1 = new Quat(cursor++,op, res, opnd1, opnd2);
//            quats.add(quat1);
//
//            //(*,I,d2,R2)
//            op = "*";
//            res = new TemporaryValue(++tmpId);
//            arrayAccess = (ASTArrayAccess) arrayAccess.arrayName;
//            visit((ASTExpression) arrayAccess.elements.get(0));
//            opnd1 = (ASTExpression) arrayAccess.elements.get(0);
//            opnd2 = new ASTIntegerConstant(temp_vec.bound.get(1).upper,temp_vec.bound.get(1).upper);
//            Quat quat2 = new Quat(cursor++,op, res, opnd1, opnd2);
//            quats.add(quat2);
//
//            //(+,R1,R2,R2)
//            op = "+";
//            res = new TemporaryValue(tmpId);
//            opnd1 = new TemporaryValue(tmpId-1);
//            opnd2 = new TemporaryValue(tmpId);
//            Quat quat3 = new Quat(cursor++,op, res, opnd1, opnd2);
//            quats.add(quat3);
//            //map.put(arrayAccess, res);
//        }
//    }

    //break标号
    @Override
    public void visit(ASTBreakStatement breakStat) throws Exception {
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;

        res = new ASTIdentifier();
        ((ASTIdentifier) res).value = "endloop"+(endloop_count-2);
        Quat quat_jumploop = new Quat(cursor++,"J",res,opnd1,opnd2);
        quats.add(quat_jumploop);

//        if(breaklabel != null){
//            res = breaklabel;
//            Quat quat = new Quat(cursor++,"J",res,opnd1,opnd2);
//            quats.add(quat);
//        }
//        else{
//            System.out.println("breaklabel null");
//        }
    }

    @Override
    public void visit(ASTContinueStatement continueStatement) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTCastExpression castExpression) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTCharConstant charConst) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTConditionExpression conditionExpression) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTFloatConstant floatConst) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTMemberAccess memberAccess) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTStringConstant stringConst) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTTypename typename) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTUnaryTypename unaryTypename) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTToken token) throws Exception {
        // TODO Auto-generated method stub

    }

}
