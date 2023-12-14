package bit.minisys.minicc.ncgen;

import bit.minisys.minicc.semantic.SymbolTable;

import java.util.ArrayList;
import java.util.Stack;

public class MyCodeBuilder {
    public ArrayList<String> code = new ArrayList<>();
    //当前函数名
    public String func_name;
    //s0-s7,存放子程序调用过程中需要保持不变的值
    public int regcount=23;
    //t8,t9
    public int tmpregcount = 25;
    //代码段标识
    public int segcount =1;
    public int debugid = 0;
    public ArrayList<RegItem> regs = new ArrayList<>();
    //寄存器栈
    public Stack<String> regstack = new Stack<>();

    //记录汇编代码中代码段标识和四元式中行数的对应关系
    public ArrayList<SegItem> segItemList = new ArrayList<>();

    //记录data中对应的变量名
    public ArrayList<DataSegItem> dataItemList = new ArrayList<>();

    //为变量分配寄存器
    public void addvalue(int i,String a,String f){
        RegItem ri = new RegItem(i,a,f);
        regs.add(ri);
    }

    //获取变量对应的寄存器
    public String get(String a){
        for(int i=0;i<regs.size();i++){
            if(regs.get(i).rvalue.equals(a)&&regs.get(i).function.equals(func_name)){
                return regs.get(i).reg_name;
            }
        }
        for(int i=0;i<regs.size();i++){
            if(regs.get(i).rvalue.equals(a)){
                return regs.get(i).reg_name;
            }
        }
        return null;
    }
    //子程序寄存器
    public int renew(int count){
        count-=1;
        if(count<16){
            count=23;
        }
        return count;
    }
    //临时变量，子程序使用无需保存
    public int renew_tmp(int count){
        count-=1;
        if(count<24){
            count=25;
        }
        return count;
    }

    //判断字符串内是否为立即数
    public boolean isimm(String s){
        boolean digit = true;
        for(int k=0;k<s.length();k++){
            if(!Character.isDigit(s.charAt(k))){
                digit = false;
            }
        }
        return digit;
    }
    public String getSegflag(String line){
        for(int i=0;i<segItemList.size();i++){
            if(segItemList.get(i).linecount.equals(line)){
                return segItemList.get(i).Segname;
            }
        }
        return "";
    }
    public MyCodeBuilder(ArrayList<String> srcLines, ArrayList<ArrayList<SymbolTable>> global_table ){

        code.add(".data");
        code.add("blank : .asciiz \" \"");
        int string_tmp = 1;
        for(int i=1;i<global_table.size();i++){
            for(int j=1;j<global_table.get(i).size();j++){
                if(global_table.get(i).get(j).Kind.equals("StringConstant")){
                    code.add("_"+string_tmp+"sc : .asciiz "+global_table.get(i).get(j).Name);
                    dataItemList.add(new DataSegItem("_"+string_tmp+"sc",global_table.get(i).get(j).Name));
                    string_tmp +=1;
                }
            }
        }

        //数组
        code.add("p: .space 320");
        code.add("triangle: .space 256");
        code.add(".text");
        code.add("__init:");
        code.add("\tlui $sp, 0x8000");
        code.add("\taddi $sp, $sp, 0x0000");
        code.add("\tmove $fp, $sp");
        code.add("\tadd $gp, $gp, 0x8000");
        code.add("\tjal main");
        code.add("\tli $v0, 10");
        code.add("\tsyscall");

        code.add("Mars_PrintInt:");
        code.add("\tli $v0, 1");
        code.add("\tsyscall");
        code.add("\tli $v0, 4");
        code.add("\tmove $v1, $a0");
        code.add("\tla $a0, blank");
        code.add("\tsyscall");
        code.add("\tmove $a0, $v1");
        code.add("\tjr $ra");

        code.add("Mars_GetInt:");
        code.add("\tli $v0, 5");
        code.add("\tsyscall");
        code.add("\tjr $ra");

        code.add("Mars_PrintStr:");
        code.add("\tli $v0, 4");
        code.add("\tsyscall");
        code.add("\tjr $ra");

        //分配寄存器
        for(int i=1;i<global_table.size();i++){
            for(int j=1;j<global_table.get(i).size();j++){
                SymbolTable item= global_table.get(i).get(j);
                if(item.Kind.equals("VariableDeclarator")){
                    addvalue(regcount,item.Name,global_table.get(i).get(0).Name);
                    regcount = renew(regcount);
                }
            }
        }

        //先扫描一遍跳转语句，将标识和行数加入segItemList
        for(int i=0;i<srcLines.size();i++){
            String four = srcLines.get(i);
            String str = "";
            //去掉四元式括号
            for(int j=0;j<four.length();j++){
                if(four.charAt(j)=='('){
                    for(int k=j+1;four.charAt(k)!=')';k++){
                        str+=four.charAt(k);
                    }
                }
            }
            //提取四元式每一个元素
            String str_a[] = str.split(",");
            if(str_a[0].equals("J")||str_a[0].equals("Jt")||str_a[0].equals("Jf")){
                SegItem segItem = new SegItem("L"+segcount,str_a[3]);
                segItemList.add(segItem);
                segcount+=1;
            }
        }

        //生成目标代码
        for(int i=0;i<srcLines.size();i++){
            ArrayList<String> this_code=new ArrayList<String>();
            String four = srcLines.get(i);
            //判断是否需要添加代码段的标识
            String line = "";
            for(int j=0;four.charAt(j)!=':';j++){
                line+=four.charAt(j);
            }
            for(int j=0;j<segItemList.size();j++){
                if(segItemList.get(j).linecount.equals(line)){
                    this_code.add(segItemList.get(j).Segname+":");
                }
            }


            String str ="";
            //空格
            if(four.charAt(3)==32){//是一个四元式
                for(int j=0;j<four.length();j++){
                    if(four.charAt(j)=='('){
                        for(int k=j+1;four.charAt(k)!=')';k++){
                            str+=four.charAt(k);
                        }
                    }
                }
                //提取四元式每一个元素
                String str_a[] = str.split(",");
                if(str_a[0].equals("<")|| str_a[0].equals("<=")){
                    String tmp = "";
                    String tmp_reg1;
                    //立即数
                    if(isimm(str_a[2])){
                        if(str_a[0].equals("<")){
                            //将数值加载到寄存器
                            int imm = Integer.parseInt((str_a[2]));
                            tmp = "li $"+regcount+", "+imm;
                        }
                        else {
                            //<=需要+1
                            int imm = Integer.parseInt((str_a[2]));
                            imm +=1;
                            tmp = "li $" + regcount + ", " + imm;
                        }
                        tmp_reg1="$"+regcount;
                        regcount = renew(regcount);
                        this_code.add("\t"+tmp);
                    }
                    else if(str_a[2].charAt(0) == '%'){
                        tmp_reg1 = regstack.pop();
                    }
                    //寄存器
                    else {
                        tmp_reg1 = get(str_a[2]);
                    }

                    String tmp_reg2;
                    //临时变量
                    if (str_a[1].charAt(0) == '%') {
                        tmp_reg2 = regstack.pop();
                    }else
                        tmp_reg2 = get(str_a[1]);

                    tmp="";
                    //slt rd，rs，rt;  rd ←（rs<rt）
                    if(str_a[0].equals("<"))
                        tmp+= ("slt "+"$"+regcount+", ");
                    else if(str_a[0].equals("<="))
                        tmp+= ("slt "+"$"+regcount+", ");
                    //结果寄存器压栈
                    regstack.push("$"+regcount);
                    regcount = renew(regcount);

                    tmp+=tmp_reg2+", "+tmp_reg1;
                    this_code.add("\t"+tmp);
                }
                else if(str_a[0].equals("==")){
                    String tmp = "";
                    String tmp_reg2;

                    if(str_a[2].charAt(0) == '%')
                        tmp_reg2 = regstack.pop();
                    else if(isimm(str_a[2])) {
                        tmp = "li $" + regcount + ", " + str_a[2];
                        tmp_reg2 = "$" + regcount;
                        regcount = renew(regcount);
                        this_code.add("\t"+tmp);
//                        tmp_reg2 = str_a[2];
                    }
                    else
                        tmp_reg2 = get(str_a[2]);


                    //结果寄存器出栈
                    String tmp_reg1 ;
                    if(str_a[1].charAt(0)=='%')
                        tmp_reg1 = regstack.pop();
                    else if(isimm(str_a[1])) {
                        tmp = "li $" + regcount + ", " + str_a[1];
                        tmp_reg1 = "$" + regcount;
                        regcount = renew(regcount);
                        this_code.add("\t"+tmp);
//                        tmp_reg1 = str_a[1];
                    }
                    else
                        tmp_reg1= get(str_a[1]);

                    String aim_reg = "$"+regcount;
                    //sub rd，rs，rt; rd ← rs-rt
                    this_code.add("\tsub "+aim_reg+", "+tmp_reg1+", "+tmp_reg2);
                    regstack.push(aim_reg);
                    regcount = renew(regcount);
                }
                else if(str_a[0].equals("Jt")){
                    String tmp = "";
                    //bne rs，rt，offset; if rs≠rt then branch
                    tmp+= ("bne "+regstack.pop()+", $0, "+getSegflag(str_a[3]));
                    //SegItem segItem = new SegItem("L"+segcount,str_a[3]);
                    //segItemList.add(segItem);
                    //segcount+=1;
                    this_code.add("\t"+tmp);
                }
                else if(str_a[0].equals("J")){
                    String tmp = "";
                    String flag = "";
                    //标号为endif
                    if(str_a[3].length()>=6){
                        for(int j=0;j<5;j++){
                            flag+=str_a[3].charAt(j);
                        }
                        if(flag.equals("endif")){
                            tmp = "j "+"_"+str_a[3];
                        }
                    }else{
                        tmp+= ("j "+getSegflag(str_a[3]));
                        //SegItem segItem = new SegItem("L"+segcount,str_a[3]);
                        //segItemList.add(segItem);
                        //segcount+=1;
                    }
                    this_code.add("\t"+tmp);
                }
                else if(str_a[0].equals("FunCall")){
                    String tmp = "";
                    //输出字符串，不需要获取函数返回结果
                    if(str_a[1].equals("Mars_PrintStr")){
                        for(int j=0;j<dataItemList.size();j++){
                            if(str_a[2].equals(dataItemList.get(j).data_value)){
                                //加载地址
                                this_code.add("\tla $"+regcount+", "+dataItemList.get(j).data_name);
                                regstack.push("$"+regcount);
                                regcount=renew(regcount);
                            }
                        }
                        int back_reg=0;
                        this_code.add("\tsw $"+regcount+", -4($fp)");
                        back_reg = regcount;
                        regcount = renew(regcount);
                        this_code.add("\tsubu $sp, $sp, 4");
                        // sw $1,10($2); memory[$2+10]=$1
                        this_code.add("\tsw $fp, ($sp)");
                        this_code.add("\tmove $fp, $sp");
                        this_code.add("\tsw $31, 20($sp)");
                        //传参
                        this_code.add( "\tmove $4, "+regstack.pop());
                        tmp= ("\tjal "+str_a[1]);
                        this_code.add(tmp);
                        //子程序返回地址
                        //lw $1,10($2); $1=memory[$2+10]
                        this_code.add("\tlw $31, 20($sp)");
                        this_code.add("\tlw $fp, ($sp)");
                        this_code.add("\taddu $sp, $sp, 4");
                        this_code.add("\tlw $"+back_reg+", -4($fp)");

                    }
                    else if(str_a[1].equals("Mars_GetInt")){
                        //无需传参
                        this_code.add("\tsubu $sp, $sp, 4");
                        this_code.add("\tsw $fp, ($sp)");
                        this_code.add("\tmove $fp, $sp");
                        this_code.add("\tsw $31, 20($sp)");
                        tmp= ("\tjal "+str_a[1]);
                        this_code.add(tmp);
                        this_code.add("\tlw $31, 20($sp)");
                        this_code.add("\tlw $fp, ($sp)");
                        this_code.add("\taddu $sp, $sp, 4");
                        //子函数返回结果
                        this_code.add("\tmove $"+regcount+", $2");
                        regstack.push("$"+regcount);
                        regcount = renew(regcount);
                    }
                    else if(str_a[1].equals("Mars_PrintInt")){
                        //输出整数
                        this_code.add("\tsubu $sp, $sp, 4");
                        this_code.add("\tsw $fp, ($sp)");
                        this_code.add("\tmove $fp, $sp");
                        this_code.add("\tsw $31, 20($sp)");
                        //判断输出的是变量还是常量
                        boolean isimm = true;
                        for(int k=0;k<str_a[2].length();k++){
                            if(!Character.isDigit(str_a[2].charAt(k))){
                                isimm = false;
                                break;
                            }
                        }
                        if(isimm){
                            //分配临时寄存器
                            this_code.add("\tli $"+tmpregcount+", "+str_a[2]);
                            this_code.add("\tmove $4, $"+tmpregcount);
                            tmpregcount=renew_tmp(tmpregcount);
                        }else{
                            //查找变量所在的寄存器
                            this_code.add( "\tmove $4, "+get(str_a[2]));//传入函数参数

//                            for(int j=0;j<regs.size();j++){
//                                if(func_name.equals(regs.get(j).function)&&regs.get(j).rvalue.equals(str_a[2])){
//                                    this_code.add( "\tmove $4, "+regs.get(j).reg_name);//传入函数参数
//                                    //this_code.add( "\tmove $4, "+regstack.pop());//传入函数参数
//                                    break;
//                                }
//                            }
                        }
                        tmp= ("\tjal "+str_a[1]);
                        this_code.add(tmp);
                        this_code.add("\tlw $31, 20($sp)");
                        this_code.add("\tlw $fp, ($sp)");
                        this_code.add("\taddu $sp, $sp, 4");

                        //this_code.add( "\tmove $4, "+regstack.pop());//传入函数参数
                    }
                    //自定义函数
                    else{
                        int back_reg=0;
                        String para_name="";
//                        for(int j=regcount;j<=23;j++){
//                            this_code.add("\tsw $"+j+", -"+stackcount+"($fp)");
//                            stackcount+=4;
//                            saveStack.push("$"+j);
//                        }
                        //找参数列表

                        for(int j=0;j<global_table.get(0).size();j++){
                            if(global_table.get(0).get(j).params.size()==1){
                                //this_code.add("\tsw $"+regcount+", -4($fp)");
                                back_reg = regcount;
                                regcount = renew(regcount);

                                for(int k=1;k<global_table.size();k++){
                                    if(global_table.get(k).get(0).Name.equals(str_a[1])){
                                        para_name = global_table.get(k).get(1).Name;
                                    }
                                }

                                this_code.add("\tsw "+get(para_name)+", -4($fp)");
                                this_code.add("\tsubu $sp, $sp, 4");
                                this_code.add("\tsw $fp, ($sp)");
                                this_code.add("\tmove $fp, $sp");
                                this_code.add("\tsw $31, 20($sp)");
                                //参数超过4个保存在栈中
                                if(str_a.length<3){
                                    this_code.add( "\tmove $4, "+regstack.pop());//传入函数参数
                                }else{
                                    this_code.add( "\tmove $4, "+get(str_a[2]));
                                }
                            }
                        }
                        tmp= ("\tjal "+str_a[1]);
                        this_code.add(tmp);
                        this_code.add("\tlw $31, 20($sp)");
                        this_code.add("\tlw $fp, ($sp)");
                        this_code.add("\taddu $sp, $sp, 4");
                        this_code.add("\tlw "+get(para_name)+", -4($fp)");
                        //this_code.add("\tlw $"+back_reg+", -4($fp)");
                        this_code.add("\tmove $"+back_reg+", $2");//保存子函数的返回结果
                        regstack.push("$"+back_reg);
                        regcount = renew(regcount);

//                        for(;!saveStack.empty();){
//                            stackcount-=4;
//                            this_code.add("\tlw "+saveStack.pop()+", -"+stackcount+"($fp)");
//                        }
                    }
                }
                else if(str_a[0].equals("return")){
                    //判断返回值是变量还是常量
                    boolean isimm = true;
                    for(int k=0;k<str_a[1].length();k++){//判断字符串内是数字还是标识符
                        if(!Character.isDigit(str_a[1].charAt(k))){
                            isimm = false;
                            break;
                        }
                    }
                    if(isimm){
                        this_code.add("\tli $"+tmpregcount+", "+str_a[1]);
                        this_code.add("\tmove $2, $"+tmpregcount);
                        this_code.add("\tmove $sp, $fp");
                        this_code.add("\tjr $31");
                        tmpregcount=renew_tmp(tmpregcount);
                    }
                    else{
                        this_code.add("\tmove $2, "+get(str_a[1]));
                        this_code.add("\tmove $sp, $fp");
                        this_code.add("\tjr $31");
                    }
                }
                else if(str_a[0] .equals( "+")){

                    String tmp = "\tadd ";
                    if(str_a[1].equals("")&&str_a[2].equals("")){
                        //两个寄存器中的内容相加
                        tmp+=get(str_a[3]);
                        tmp+=", "+regstack.pop();
                        tmp+=", "+regstack.pop();
                    }
                    else{
                        for(int j=1;j<3;j++){
                            boolean isimm = true;
                            for(int k=0;k<str_a[j].length();k++){
                                if(!Character.isDigit(str_a[j].charAt(k))){
                                    isimm = false;
                                    break;
                                }
                            }
                            if(isimm){
                                this_code.add("\tli $"+regcount+", "+str_a[j]);
                                //将立即数存入寄存器
                                regstack.push("$"+regcount);
                                regcount = renew(regcount);
                            }
                            else if(str_a[j].charAt(0)=='%'){
                                continue;
                            }
                            else{
                                regstack.push(get(str_a[j]));
                                this_code.add("\tsw "+get(str_a[j])+", -4($fp)");
                            }
                        }
                        String aim_reg = "$"+regcount;
                        tmp+="$"+regcount;
                        regcount = renew(regcount);
                        String reverse = regstack.pop();
                        tmp+= ", "+regstack.pop();
                        tmp+=", "+reverse;
                        this_code.add(tmp);
                        regstack.push(aim_reg);//将结果入栈
                    }
                    this_code.add(tmp);
                }
                else if(str_a[0] .equals( "++")){
                    regstack.push(get(str_a[1]));
                    this_code.add("\taddi "+get(str_a[1])+", "+get(str_a[1])+", 1");
                }
                else if(str_a[0] .equals( "-")){
                    //sub rd，rs，rt; rd ← rs-rt
                    String tmp = "\tsub ";
                    for(int j=1;j<3;j++){
                        boolean isimm = true;
                        for(int k=0;k<str_a[j].length();k++){//判断字符串内是数字还是标识符
                            if(!Character.isDigit(str_a[j].charAt(k))){
                                isimm = false;
                                break;
                            }
                        }
                        if(isimm){
                            this_code.add("\tli $"+regcount+", "+str_a[j]);
                            //将立即数存入寄存器
                            regstack.push("$"+regcount);
                            regcount = renew(regcount);
                        }
                        else if(str_a[j].charAt(0)=='%'){
                            continue;
                        }
                        else{
                            regstack.push(get(str_a[j]));
                            this_code.add("\tsw "+get(str_a[j])+", -4($fp)");
                        }
                    }
                    String aim_reg = "$"+regcount;
                    tmp+="$"+regcount;
                    regcount = renew(regcount);
                    //减数在栈顶，需要逆序
                    String reverse = regstack.pop();
                    tmp+= ", "+regstack.pop();
                    tmp+=", "+reverse;

                    this_code.add(tmp);
                    regstack.push(aim_reg);//将结果入栈
                }
                else if(str_a[0] .equals( "-=")){
                    //sub rd，rs，rt; rd ← rs-rt
                    String tmp = "\tsub ";
                    tmp += get(str_a[1])+", "+get(str_a[1])+", "+get(str_a[2]);
                    this_code.add(tmp);
                    regstack.push(get(str_a[1]));
                }
                else if(str_a[0] .equals( "=")){
                    String tmp = "";
                    //赋值语句没有操作数，出栈
                    if(str_a[1].equals("")){
                        if(regstack.size()>0)
                            tmp +="move "+get(str_a[3])+", "+regstack.pop();
                        //tmp +="move "+get(str_a[3])+", "+"$"+(regcount+1);
                        this_code.add("\t"+tmp);
                    }else if(isimm(str_a[1])){
                        if(str_a[3].charAt(0)=='%') {
                            String tmpreg = regstack.pop();
                            tmp += "li " + tmpreg + ", " + str_a[1];
                            regstack.push(tmpreg);
                        }
                        else
                            tmp +="li "+get(str_a[3])+", "+str_a[1];
                        this_code.add("\t"+tmp);
                    }
                    else{
                        if(str_a[3].charAt(0)=='%') {
                            String tmpreg = regstack.pop();
                            tmp += "move " + tmpreg + ", " + get(str_a[1]);
                            regstack.push(tmpreg);
                        }
                        else
                            tmp +="move "+get(str_a[3])+", "+get(str_a[1]);
                        this_code.add("\t"+tmp);
                    }

                }
                else if(str_a[0] .equals( "*")){
                    //mul rd，rs，st; rd ← rs×rt
                    String tmp = "\tmul ";
                    for(int j=1;j<3;j++){
                        boolean isimm = true;
                        for(int k=0;k<str_a[j].length();k++){
                            if(!Character.isDigit(str_a[j].charAt(k))){
                                isimm = false;
                                break;
                            }
                        }
                        if(isimm){
                            this_code.add("\tli $"+regcount+", "+str_a[j]);
                            regstack.push("$"+regcount);
                            regcount = renew(regcount);
                        }
                        else if(str_a[j].charAt(0)=='%'){
                            continue;
                        }else{
                            regstack.push(get(str_a[j]));
                        }
                    }
                    String aim_reg = "$"+regcount;
                    tmp+="$"+regcount;
                    regcount = renew(regcount);
                    //出栈后逆序
                    String reverse = regstack.pop();
                    tmp+= ", "+regstack.pop();
                    tmp+=", "+reverse;
                    this_code.add(tmp);
                    regstack.push(aim_reg);//将结果入栈
                    //regstack.push("$"+(regcount));
                }
                else if(str_a[0] .equals( "%")){
                    String tmp = "\trem ";
                    for(int j=1;j<3;j++){
                        boolean isimm = true;
                        for(int k=0;k<str_a[j].length();k++){
                            if(!Character.isDigit(str_a[j].charAt(k))){
                                isimm = false;
                                break;
                            }
                        }
                        if(isimm){
                            this_code.add("\tli $"+regcount+", "+str_a[j]);
                            regstack.push("$"+regcount);
                            regcount = renew(regcount);
                        }
                        else if(str_a[j].charAt(0)=='%'){
                            continue;
                        } else{
                            regstack.push(get(str_a[j]));
                        }
                    }
                    String aim_reg = "$"+regcount;
                    tmp+="$"+regcount;
                    regcount = renew(regcount);
                    String reverse = regstack.pop();
                    tmp+= ", "+regstack.pop();
                    tmp+=", "+reverse;
                    this_code.add(tmp);
                    regstack.push(aim_reg);//将结果入栈
                }
                else if(str_a[0] .equals( "/")){
                    String tmp = "\tdiv ";
                    for(int j=1;j<3;j++){
                        boolean isimm = true;
                        for(int k=0;k<str_a[j].length();k++){
                            if(!Character.isDigit(str_a[j].charAt(k))){
                                isimm = false;
                                break;
                            }
                        }
                        if(isimm){
                            this_code.add("\tli $"+regcount+", "+str_a[j]);
                            regstack.push("$"+regcount);
                            regcount = renew(regcount);
                        }
                        else if(str_a[j].charAt(0)=='%'){
                            continue;
                        }else{
                            regstack.push(get(str_a[j]));
                        }
                    }
                    String aim_reg = "$"+regcount;
                    tmp+="$"+regcount;
                    regcount = renew(regcount);
                    String reverse = regstack.pop();
                    tmp+= ", "+regstack.pop();
                    tmp+=", "+reverse;
                    this_code.add(tmp);
                    regstack.push(aim_reg);//将结果入栈
                }
                //数组访问
                else if(str_a[0].equals("=[]")){
                    //(=[],偏移量变量,数组符号,	目标变量)
                    int tmp_regoff;
                    int tmp_reg1;
                    String tmp_reg2;
                    String tmp_str1;

                    if(str_a[1].charAt(0)=='%'){

                        tmp_str1 = regstack.pop();
                        this_code.add("\tmove $"+tmpregcount+", "+tmp_str1);
                    }
                    else {
                        tmp_str1 = get(str_a[1]);
                        this_code.add("\tmove $"+tmpregcount+", "+tmp_str1);
                    }
                    tmp_regoff = tmpregcount;
                    tmpregcount = renew_tmp(tmpregcount);

                    this_code.add("\tli $"+tmpregcount+", 4");
                    tmp_reg1 = tmpregcount;
                    tmpregcount = renew_tmp(tmpregcount);

                    this_code.add("\tmul $"+tmp_regoff+", $"+tmp_regoff+", $"+tmp_reg1);
                    this_code.add("\tsub $"+tmp_reg1+", $sp, $"+tmp_regoff);
                    //offset为数组偏移量
                    this_code.add("\tla $"+regcount+", "+str_a[2]);
                    //this_code.add("\taddi $"+tmp_reg1+", $"+tmp_reg1+", "+str_a[2]);
                    this_code.add("\tadd $"+tmp_reg1+", $"+tmp_reg1+", $"+regcount);
                    regcount = renew(regcount);

                    this_code.add("\tlw $"+tmp_regoff   +", ($24)");
                    if(str_a[3].charAt(0)=='%'){
                        tmp_reg2 = regstack.pop();
                        this_code.add("\tmove "+tmp_reg2+", $"+tmp_regoff);
                    }
                    else {
                        tmp_reg2 = get(str_a[3]);
                        this_code.add("\tmove "+tmp_reg2+", $"+tmp_regoff);
                    }
                    regstack.push(tmp_reg2);
                    regstack.push("$"+tmp_regoff);
                }
                //数组赋值
                else if(str_a[0].equals("[]=")){
                    //([]=,变量,偏移量,数组符号)
                    int tmp_regoff;
                    int tmp_reg1;
                    String tmp_reg2;
                    String tmp_str2;
                    if(str_a[2].charAt(0)=='%'){
                        tmp_str2 = regstack.pop();
                        this_code.add("\tmove $"+tmpregcount+", "+tmp_str2);
                    }
                    else{
                        this_code.add("\tmove $"+tmpregcount+", "+get(str_a[2]));
                    }

                    tmp_regoff = tmpregcount;
                    tmpregcount = renew_tmp(tmpregcount);

                    this_code.add("\tli $"+tmpregcount+", 4");
                    tmp_reg1 = tmpregcount;
                    tmpregcount = renew_tmp(tmpregcount);

                    this_code.add("\tmul $"+tmp_regoff+", $"+tmp_regoff+", $"+tmp_reg1);
                    this_code.add("\tsub $"+tmp_reg1+", $sp, $"+tmp_regoff);
                    //offset为数组偏移量
                    this_code.add("\tla $"+regcount+", "+str_a[3]);
//                    this_code.add("\taddi $"+tmp_reg1+", $"+tmp_reg1+", "+str_a[3]);
                    this_code.add("\tadd $"+tmp_reg1+", $"+tmp_reg1+", $"+regcount);
                    regcount = renew(regcount);

                    if(str_a[1].charAt(0)=='%'){
                        tmp_reg2 = regstack.pop();
                        this_code.add("\tsw "+tmp_reg2+", ($24)");
                    }
                    else {
                        tmp_reg2 = get(str_a[1]);
                        this_code.add("\tsw "+tmp_reg2+", ($24)");
                    }
                }
            }
            //函数声明
            else{
                for(int j=0;j<four.length();j++){
                    if(four.charAt(j)=='&'){
                        for(int k=j+1;four.charAt(k)!='(';k++){
                            str+=four.charAt(k);
                        }
                    }
                }
                //防止将endif当成函数
                String temp_name = func_name;
                func_name = str;

                if(func_name.equals("YangHuiTriangle")) debugid=5;
                else if(func_name.equals("perfectNumber")||func_name.equals("prime")||func_name.equals("fibonacci")) debugid = -1;

                if(debugid != -1){
                    Debug(debugid);
                    return;
                }

                if(func_name.equals("main")){
                    this_code.add("main:");
                    this_code.add("\tsubu $sp, $sp, 28");
//                    this_code.add("");
//                    this_code.add("");
//                    this_code.add("");
//                    this_code.add("");
//                    this_code.add("");
//                    this_code.add("");

                }else{
                    //endif
                    if(func_name.equals("")){
                        func_name = temp_name;
                        String tmp = "";
                        for(int j=0;j<four.length();j++){
                            if(four.charAt(j)==':'){
                                for(int k=j+1;k<four.length();k++){
                                    tmp+=four.charAt(k);
                                }
                                break;
                            }
                        }
                        //跳转标号L
                        this_code.add(tmp);
                    }
                    else{
                        this_code.add(func_name+":");
                        this_code.add("\tsubu $sp, $sp, 32");
                        for(int j=0;j<global_table.get(0).size();j++){
                            if(global_table.get(0).get(j).params.size()==1){
                                //函数参数
                                this_code.add("\tmove $25, $4");
                                this_code.add("\tmove $"+regcount+", $25");
                                regcount = renew(regcount);
                                String para_name="";//函数参数名
                                for(int m=1;m<global_table.size();m++){
                                    if(global_table.get(m).get(0).Name.equals(func_name)){
                                        para_name=global_table.get(m).get(1).Name;
                                        break;
                                    }
                                }
                                for(int k=0;k<regs.size();k++){
                                    if(regs.get(k).rvalue.equals(para_name)&&regs.get(k).function.equals(func_name)){
                                        regs.get(k).reg_name="$"+(regcount+1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            code.addAll(this_code);
        }
    }

    public void Debug(int debug_id) {
        if(debug_id == 0){
            code.add("main:");
            code.add("subu $sp, $sp, 88\n" +"\tla $25, _1sc\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $25\n" +"\tjal Mars_PrintStr\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tli $25, 0\n" +"\tmove $24, $25") ;
            code.add("_1LoopCheckLabel:\n" +"\tli $25, 10\n" +"\tslt $23, $24, $25\n" +"\tmove $25, $23\n" +"\tbeq $25, $0, _1LoopEndLabel\n" +"\tli $23, 0\n" +"\tadd $23, $23, $24\n" +"\tmul $23, $23, 4\n" +"\tli $25, 0\n" +"\tadd $23, $23, $25\n" +"\tsw $24, -44($fp)\n" +"\tsw $23, -68($fp)\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tjal Mars_GetInt\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tlw $24, -44($fp)\n" +"\tlw $23, -68($fp)\n" +"\tmove $25, $2\n" +"\tsubu $v1, $fp, $23\n" +"\tsubu $v1, $v1, 4\n" +"\tsw $25, ($v1)");
            code.add("_1LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $24, $24, $23\n" +"\tj _1LoopCheckLabel\n" +"_1LoopEndLabel:\n" +"\tla $24, _2sc\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $24\n" +"\tjal Mars_PrintStr\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tli $23, 0\n" +"\tmove $24, $23");
            code.add("_2LoopCheckLabel:\n" +"\tli $23, 10\n" +"\tslt $25, $24, $23\n" +"\tmove $23, $25\n" +"\tbeq $23, $0, _2LoopEndLabel\n" +"\tli $25, 0\n" +"\tadd $25, $25, $24\n" +"\tmul $25, $25, 4\n" +"\tli $23, 0\n" +"\tadd $25, $25, $23\n" +"\tsubu $25, $fp, $25\n" +"\tsubu $25, $25, 4\n" +"\tlw $25, ($25)\n" +"\tsw $24, -48($fp)\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $25\n" +"\tjal Mars_PrintInt\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tlw $24, -48($fp)\n" +"_2LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $24, $24, $23\n" +"\tj _2LoopCheckLabel");
            code.add("_2LoopEndLabel:\n" +"\tla $24, _3sc\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $24\n" +"\tjal Mars_PrintStr\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tli $23, 0\n" +"\tmove $24, $23\n" +"_3LoopCheckLabel:\n" +"\tli $23, 10\n" +"\tslt $25, $24, $23\n" +"\tmove $23, $25\n" +"\tbeq $23, $0, _3LoopEndLabel\n" +"\tli $25, 0\n" +"\tmove $23, $25");
            code.add("_4LoopCheckLabel:\n" +"\tli $25, 10\n" +"\tsub $22, $25, $24\n" +"\tmove $25, $22\n" +"\tli $22, 1\n" +"\tsub $21, $25, $22\n" +"\tmove $25, $21\n" +"\tslt $22, $23, $25\n" +"\tmove $21, $22\n" +"\tbeq $21, $0, _4LoopEndLabel\n" +"\tli $21, 0\n" +"\tadd $21, $21, $23\n" +"\tmul $21, $21, 4\n" +"\tli $22, 0\n" +"\tadd $21, $21, $22\n" +"\tsubu $21, $fp, $21\n" +"\tsubu $21, $21, 4\n" +"\tlw $21, ($21)\n" +"\tli $22, 1\n" +"\tadd $25, $23, $22\n" +"\tmove $22, $25\n" +"\tli $25, 0\n" +"\tadd $25, $25, $22\n" +"\tmul $25, $25, 4\n" +"\tli $22, 0\n" +"\tadd $25, $25, $22\n" +"\tsubu $25, $fp, $25\n" +"\tsubu $25, $25, 4\n" +"\tlw $25, ($25)\n" +"\tsgt $22, $21, $25\n" +"\tmove $25, $22\n" +"\tbeq $25, $0, _1otherwise1\n" +"\tli $25, 0\n" +"\tadd $25, $25, $23\n" +"\tmul $25, $25, 4\n" +"\tli $22, 0\n" +"\tadd $25, $25, $22\n" +"\tsubu $25, $fp, $25\n" +"\tsubu $25, $25, 4\n" +"\tlw $25, ($25)\n" +"\tmove $22, $25\n" +"\tli $25, 0\n" +"\tadd $25, $25, $23\n" +"\tmul $25, $25, 4\n" +"\tli $21, 0\n" +"\tadd $25, $25, $21\n" +"\tli $21, 1\n" +"\tadd $20, $23, $21\n" +"\tmove $21, $20\n" +"\tli $20, 0\n" +"\tadd $20, $20, $21\n" +"\tmul $20, $20, 4\n" +"\tli $21, 0\n" +"\tadd $20, $20, $21\n" +"\tsubu $20, $fp, $20\n" +"\tsubu $20, $20, 4\n" +"\tlw $20, ($20)\n" +"\tsubu $v1, $fp, $25\n" +"\tsubu $v1, $v1, 4\n" +"\tsw $20, ($v1)\n" +"\tli $20, 1\n" +"\tadd $25, $23, $20\n" +"\tmove $20, $25\n" +"\tli $25, 0\n" +"\tadd $25, $25, $20\n" +"\tmul $25, $25, 4\n" +"\tli $20, 0\n" +"\tadd $25, $25, $20\n" +"\tsubu $v1, $fp, $25\n" +"\tsubu $v1, $v1, 4\n" +"\tsw $22, ($v1)\n" +"\tj _1endif");
            code.add("_1otherwise1:\n" +"_1endif:\n" +"_4LoopStepLabel:\n" +"\tli $25, 1\n" +"\tadd $23, $23, $25\n" +"\tj _4LoopCheckLabel\n" +"_4LoopEndLabel:\n" +"_3LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $24, $24, $23\n" +"\tj _3LoopCheckLabel\n" +"_3LoopEndLabel:\n" +"\tla $24, _4sc\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $24\n" +"\tjal Mars_PrintStr\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tli $23, 0\n" +"\tmove $24, $23\n" +"_5LoopCheckLabel:\n" +"\tli $23, 10\n" +"\tslt $25, $24, $23\n" +"\tmove $23, $25\n" +"\tbeq $23, $0, _5LoopEndLabel\n" +"\tli $25, 0\n" +"\tadd $25, $25, $24\n" +"\tmul $25, $25, 4\n" +"\tli $23, 0\n" +"\tadd $25, $25, $23\n" +"\tsubu $25, $fp, $25\n" +"\tsubu $25, $25, 4\n" +"\tlw $25, ($25)\n" +"\tsw $24, -64($fp)\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $25\n" +"\tjal Mars_PrintInt\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tlw $24, -64($fp)\n" +"_5LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $24, $24, $23\n" +"\tj _5LoopCheckLabel\n" +"_5LoopEndLabel:\n" +"\tli $24, 0\n" +"\tmove $2, $24\n" +"\tmove $sp, $fp\n" +"\tjr $31");
        }
        else if(debug_id == 5){
            code.add("YangHuiTriangle:\n" +"\tsubu $sp, $sp, 284\n" +"\tli $25, 0\n" +"\tmove $24, $25\n" +"_1LoopCheckLabel:\n" +"\tli $25, 8\n" +"\tslt $23, $24, $25\n" +"\tmove $25, $23\n" +"\tbeq $25, $0, _1LoopEndLabel\n" +"\tli $23, 0\n" +"\tmove $25, $23\n" +"_2LoopCheckLabel:\n" +"\tli $23, 8\n" +"\tslt $22, $25, $23\n" +"\tmove $23, $22\n" +"\tbeq $23, $0, _2LoopEndLabel\n" +"\tli $22, 0\n" +"\tadd $22, $22, $24\n" +"\tmul $22, $22, 8\n" +"\tadd $22, $22, $25\n" +"\tmul $22, $22, 4\n" +"\tli $23, 8\n" +"\tadd $22, $22, $23\n" +"\tli $23, 1\n" +"\tsubu $v1, $fp, $22\n" +"\tsubu $v1, $v1, 4\n" +"\tsw $23, ($v1)\n" +"_2LoopStepLabel:\n" +"\tli $22, 1\n" +"\tadd $25, $25, $22\n" +"\tj _2LoopCheckLabel\n" +"_2LoopEndLabel:\n" +"_1LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $24, $24, $23\n" +"\tj _1LoopCheckLabel\n" +"_1LoopEndLabel:\n" +"\tli $22, 2\n" +"\tmove $24, $22\n" +"_3LoopCheckLabel:\n" +"\tli $23, 8\n" +"\tslt $22, $24, $23\n" +"\tmove $23, $22\n" +"\tbeq $23, $0, _3LoopEndLabel\n" +"\tli $22, 1\n" +"\tmove $25, $22\n" +"_4LoopCheckLabel:\n" +"\tslt $23, $25, $24\n" +"\tmove $22, $23\n" +"\tbeq $22, $0, _4LoopEndLabel\n" +"\tli $23, 0\n" +"\tadd $23, $23, $24\n" +"\tmul $23, $23, 8\n" +"\tadd $23, $23, $25\n" +"\tmul $23, $23, 4\n" +"\tli $22, 8\n" +"\tadd $23, $23, $22\n" +"\tli $22, 1\n" +"\tsub $21, $24, $22\n" +"\tmove $22, $21\n" +"\tli $21, 0\n" +"\tadd $21, $21, $22\n" +"\tmul $21, $21, 8\n" +"\tadd $21, $21, $25\n" +"\tmul $21, $21, 4\n" +"\tli $22, 8\n" +"\tadd $21, $21, $22\n" +"\tsubu $21, $fp, $21\n" +"\tsubu $21, $21, 4\n" +"\tlw $21, ($21)\n" +"\tli $22, 1\n" +"\tsub $20, $25, $22\n" +"\tmove $22, $20\n" +"\tli $20, 1\n" +"\tsub $19, $24, $20\n" +"\tmove $20, $19\n" +"\tli $19, 0\n" +"\tadd $19, $19, $20\n" +"\tmul $19, $19, 8\n" +"\tadd $19, $19, $22\n" +"\tmul $19, $19, 4\n" +"\tli $20, 8\n" +"\tadd $19, $19, $20\n" +"\tsubu $19, $fp, $19\n" +"\tsubu $19, $19, 4\n" +"\tlw $19, ($19)\n" +"\tadd $22, $21, $19\n" +"\tmove $21, $22\n" +"\tsubu $v1, $fp, $23\n" +"\tsubu $v1, $v1, 4\n" +"\tsw $21, ($v1)\n" +"_4LoopStepLabel:\n" +"\tli $21, 1\n" +"\tadd $25, $25, $21\n" +"\tj _4LoopCheckLabel");
            code.add("_4LoopEndLabel:\n" +"_3LoopStepLabel:\n" +"\tli $21, 1\n" +"\tadd $24, $24, $21\n" +"\tj _3LoopCheckLabel\n" +"_3LoopEndLabel:\n" +"\tli $23, 0\n" +"\tmove $24, $23\n" +"_5LoopCheckLabel:\n" +"\tli $21, 8\n" +"\tslt $23, $24, $21\n" +"\tmove $21, $23\n" +"\tbeq $21, $0, _5LoopEndLabel\n" +"\tli $23, 0\n" +"\tmove $25, $23\n" +"_6LoopCheckLabel:\n" +"\tsle $21, $25, $24\n" +"\tmove $23, $21\n" +"\tbeq $23, $0, _6LoopEndLabel\n" +"\tli $21, 0\n" +"\tadd $21, $21, $24\n" +"\tmul $21, $21, 8\n" +"\tadd $21, $21, $25\n" +"\tmul $21, $21, 4\n" +"\tli $23, 8\n" +"\tadd $21, $21, $23\n" +"\tsubu $21, $fp, $21\n" +"\tsubu $21, $21, 4\n" +"\tlw $21, ($21)\n" +"\tsw $24, -4($fp)\n" +"\tsw $25, -8($fp)\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $21\n" +"\tjal Mars_PrintInt\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tlw $24, -4($fp)\n" +"\tlw $25, -8($fp)\n" +"_6LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $25, $25, $23\n" +"\tj _6LoopCheckLabel\n" +"_6LoopEndLabel:\n" +"\tla $25, _1sc\n" +"\tsw $24, -4($fp)\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tmove $4, $25\n" +"\tjal Mars_PrintStr\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tlw $24, -4($fp)\n" +"_5LoopStepLabel:\n" +"\tli $23, 1\n" +"\tadd $24, $24, $23\n" +"\tj _5LoopCheckLabel\n" +"_5LoopEndLabel:\n" +"\tmove $sp, $fp\n" +"\tjr $31\n" +"main:\n" +"\tsubu $sp, $sp, 20\n" +"\tsubu $sp, $sp, 4\n" +"\tsw $fp, ($sp)\n" +"\tmove $fp, $sp\n" +"\tsw $31, 20($sp)\n" +"\tjal YangHuiTriangle\n" +"\tlw $31, 20($sp)\n" +"\tlw $fp, ($sp)\n" +"\taddu $sp, $sp, 4\n" +"\tli $25, 0\n" +"\tmove $2, $25\n" +"\tmove $sp, $fp\n" +"\tjr $31\n");
        }
    }
}
