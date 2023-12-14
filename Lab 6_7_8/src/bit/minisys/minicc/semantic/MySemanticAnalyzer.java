package bit.minisys.minicc.semantic;

import bit.minisys.minicc.parser.ast.ASTCompilationUnit;
import bit.minisys.minicc.parser.ast.ASTDeclaration;
import bit.minisys.minicc.parser.ast.ASTFunctionDefine;
import bit.minisys.minicc.parser.ast.ASTNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

public class MySemanticAnalyzer implements IMiniCCSemantic {
    //存储所有符号表
    public ArrayList<ArrayList<SymbolTable>> total_table = new ArrayList<>();

    @Override
    public String run(String iFile) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ASTCompilationUnit program = (ASTCompilationUnit) mapper.readValue(new File(iFile), ASTCompilationUnit.class);
        System.out.println("in Semantic");
        System.out.println("errors:");
        System.out.println("------------------------------------");

        //全局符号表,存储在total_table第一位
        ArrayList<SymbolTable> global_table= new ArrayList<>();
        total_table.add(global_table);

        //DFS遍历语法树
        for (ASTNode item:program.items){
            String class_name = "AST"+ item.getType();
            //函数定义
            if(class_name.equals("ASTFunctionDefine")){
                //类型转换为ASTFunctionDefine，便于匹配visit
                ASTFunctionDefine fdf = (ASTFunctionDefine) item;
                MySemanticVisitor visitor = new MySemanticVisitor();
                visitor.global_table = total_table;
                //递归调用visit
                fdf.accept(visitor);
                //为每个函数添加符号表
                total_table.add(visitor.cur_table);
                //添加函数名
                total_table.get(0).add(visitor.cur_table.get(0));
            }
            //变量声明
            else if(class_name.equals("ASTDeclaration")){
                ASTDeclaration dc = (ASTDeclaration) item;
                MySemanticVisitor dc_visitor = new MySemanticVisitor();
                dc_visitor.global_table=total_table;
                //递归调用visit
                item.accept(dc_visitor);
                //添加声明
                total_table.get(0).addAll(dc_visitor.cur_table);
            }
        }

        //遍历所有的符号表，查找重复声明错误
        for(int i=0;i<total_table.size();i++){
            ArrayList<SymbolTable> tmp_table = total_table.get(i);
            //全局符号表,存储在total_table第一位
            if(i==0){
                //查找当前位置之后有无重复声明
                for(int j=0;j<tmp_table.size();j++){
                    for(int k=j+1;k<tmp_table.size();k++){
                        if((tmp_table.get(j).Name.equals(tmp_table.get(k).Name))&&(tmp_table.get(j).Kind.equals(tmp_table.get(k).Kind))&&(tmp_table.get(k).Kind.equals("VariableDeclarator")||tmp_table.get(k).Kind.equals("FunctionDeclarator"))){
                            System.out.println("ES02 >> Declaration:"+tmp_table.get(k).Name+" has been declarated.");
                        }
                    }
                }
            }
            //函数符号表
            else{
               //查找当前位置之后的变量有无重复声明
               for(int j=0;j<tmp_table.size();j++){
                   for(int k=j+1;k<tmp_table.size();k++){
                       if((tmp_table.get(j).Name.equals(tmp_table.get(k).Name))&&(tmp_table.get(j).Kind.equals(tmp_table.get(k).Kind))&&(tmp_table.get(k).Kind.equals("VariableDeclarator"))){
                           System.out.println("ES02 >> Declaration:"+tmp_table.get(k).Name+" has been declarated.");
                       }
                   }
               }
            }
        }
        System.out.println("------------------------------------");
        System.out.println("4. Semantic Finished!");
        //System.out.println(global_table);
        return null;
    }
}

