package bit.minisys.minicc.scanner;

import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyCGrammarScanner {
    public MyCGrammarScanner(String tokenFileName,CommonTokenStream tokens) throws IOException {
        System.out.println("-> Using MyCGrammarScanner ");
        //将词法分析的结果写入到同名文件中，文件后缀为.tokens
        FileWriter fileWriter = new FileWriter(new File(tokenFileName));
        //将每一个词法分析的结果转换为字符串类型，写入文件中
        for(int i=0;i<tokens.getNumberOfOnChannelTokens();i++){
            fileWriter.write(tokens.get(i).toString());
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
}
