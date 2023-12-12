package bit.minisys.minicc;

import MyAntlr4CGrammar.MyAntlr4CGrammarLexer;
import MyAntlr4CGrammar.MyAntlr4CGrammarParser;
import bit.minisys.minicc.scanner.MyCGrammarScanner;
import org.antlr.v4.runtime.ANTLRInputStream;
//import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyBITMiniCC {
    public static void main(String[] args)throws IOException {
//        for(int i=0;i<args.length;i++){
//            System.out.println(args[i]);
//            System.out.println(i);
//        }
        String inputFile=args[0];
        InputStream is = System.in;
        is = new FileInputStream(inputFile);
        // 新建一个 CharStream, 读取待测试的c语言程序
        ANTLRInputStream input = new ANTLRInputStream(is);
        // 新建一个词法分析器, 处理输入的 CharStream
        MyAntlr4CGrammarLexer lexer = new MyAntlr4CGrammarLexer(input);
        // 新建一个词法符号的缓冲区, 用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 新建一个语法分析器,处理词法符号缓冲区中的内容
        MyAntlr4CGrammarParser parser = new MyAntlr4CGrammarParser(tokens);
        ParseTree tree = parser.compilationUnit();
        // 用LISP风格打印生成的树
        //System.out.println(tree.toStringTree(parser));

        String fName = inputFile.trim();
        String temp[] = fName.split("\\\\");
        //词法分析的结果写入到同名文件中，文件后缀为.tokens
        String tokenFileName =temp[temp.length - 1] + ".tokens";
        //使用MyCGrammarScanner进行词法分析
        MyCGrammarScanner myCGrammarScanner = new MyCGrammarScanner(tokenFileName,tokens);
    }
}
