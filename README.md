# BITMiniCC-Coursework

北京理工大学 编译原理 课程作业

[BIT-MiniCC](https://github.com/jiweixing/BIT-MiniCC)

[TOC]

## Lab 1 语言认知实验

### Lab 1 实验内容

使用C++、Python、Java、Haskell和80386汇编语言编写矩阵相乘的程序，输入两个矩阵，输出结果矩阵，并分析相应的执行效果。对采用这几种语言实现的编程效率，程序的规模，程序的运行效率进行对比分析。

[C++](./Lab%201/C++/)
[Python](./Lab%201/Python/)
[Java](./Lab%201/Java/)
[Haskell](./Lab%201/Haskell/)
[Assembly](./Lab%201/Assembly/)


## Lab 2 编译器认知实验

### Lab 2 实验内容

本实验主要的内容为在Linux平台上安装和运行工业界常用的编译器GCC和LLVM，安装完成后编写简单的测试程序，使用编译器编译，并观察中间输出结果。

### Lab 2 对比分析

编写矩阵乘法程序测试GCC编译器编译优化功能，代码如下所示。输入gcc MatrixMul.c -O0 -o MatrixMul进行编译。同理，依次使用-O1 -O2 -O3选项对输入程序进行编译优化，运行结果见表3-1。

```C
    #include "stdio.h"
    #include "time.h"
    int Matrix*\_A[1000][1000],Matrix\_*B[1000][1000],Matrix\_C[1000][1000];
    int main()
    {
    
        int i,j,k;
        int begin,end;
        begin = clock();
        for(i=0;i<1000;i++)
           `for(j=0;j<1000;j++)
               `Matrix\_A[i][j] =i+j;
        for(i=0;i<1000;i++)
           `for(j=0;j<1000;j++)
               `Matrix\_B[i][j] =i+j;
        for(i=0;i<1000;i++)
           `for(j=0;j<1000;j++)
               `for(k=0;k<1000;k++)
                   `Matrix\_C[i][j]+=Matrix\_A[i][k]\*Matrix\_B[k][j];
        end = clock();
        printf("time: %d ms \n",end-begin);
        return 0;
    
    }
```

使用-O1 -O2选项进行编译优化的效果十分明显，与-O0相比性能提升约四倍。使用-O3选项编译时性能提升显著，运行时间缩短一个数量级。但与宿主机运行代码相比，在虚拟机上运行代码速度较慢。

表3-1 GCC运行结果统计表

|GCC|时间1(ms)|时间2(ms)|时间3(ms)|时间4(ms)|时间5(ms)|平均时间(ms)|
| - | - | - | - | - | - | - |
|-O0|4087328|4393503|4155060|4218494|4282588|4227395|
|-O1|1151910|1024480|1021575|1078047|1071586|1069520|
|-O2|1052587|1067636|975330|1013604|1065309|1034893|
|-O3|266926|260501|283485|288291|283052|276451|

#### Lab 2 LLVM运行结果分析
编写矩阵乘法程序测试GCC编译器编译优化功能，使用的代码同3.1。输入clang MatrixMul.c -O0 -o clang-MatrixMul0 进行编译。同理，依次使用-O1 -O2 -O3选项对输入程序进行编译优化，运行结果见表3-2。

LLVM进行-O1编译优化时性能提升约三倍左右，但-O1与-O2相比性能提升并不明显。-O3与-O2相比速度下降，可能是过于激进的程序优化策略降低了运行速度导致的。

表3-2 LLVM运行结果统计表

|LLVM|时间1(ms)|时间2(ms)|时间3(ms)|时间4(ms)|时间5(ms)|平均时间(ms)|
| - | - | - | - | - | - | - |
|-O0|2894380|2976568|2976425|2847495|3134554|2965884|
|-O1|1044367|1032334|1074861|1068426|1133515|1070701|
|-O2|975198|945787|979890|906524|921615|945803|
|-O3|890064|981636|967800|926541|964598|946128|

#### Lab 2 GCC与LLVM运行结果对比分析**
横向对比GCC与LLVM的编译后程序运行时间，使用相同的程序多次运行统计平均运行时间。在-O0选项下，GCC平均运行时间显著慢于LLVM。-O1与-O2优化下二者运行时间相差不大。但在-O3优化下，二者性能相差三倍之多，GCC平均运行时间显著缩短。在本次实验中，GCC编译器-O3选项编译优化效果最为显著，性能提升最高。

表3-3 GCC与LLVM运行结果对比统计表

|编译优化|GCC平均时间(ms)|LLVM平均时间(ms)|
| - | - | - |
|-O0|4227395|2965884|
|-O1|1069520|1070701|
|-O2|1034893|945803|
|-O3|276451|946128|

## Lab 3 词法分析实验
[Antlr-v4 C grammar](https://github.com/antlr/grammars-v4/blob/master/c/C.g4)

### Lab 3 实验内容

本实验主要的内容为根据C语言的词法规则，设计识别C语言所有单词类的词法分析器的确定有限状态自动机，并使用Java、C\C++或者Python其中任何一种语言，采用程序中心法或者数据中心法设计并实现词法分析器。词法分析器的输入为C语言源程序，输出为属性字流。

可以选择手动编码实现词法分析器，也可以选择使用Flex自动生成词法分析器。需要注意的是，Flex生成的是C为实现语言的词法分析器，如果需要生成Java为实现语言的词法分析器，可以尝试JFlex或者ANTLR。

在本次实验中，我使用ANTLR v4，自动生成以Java为实现语言的词法分析器。

### Lab 3 过程及步骤

#### Lab 3 安装Antlr v4
在Antlr官网下载Antlr 4.12.0 jar，并将其路径添加到系统变量CLASSPATH中。除了jar文件路径以外，在CLASSPATH中还要添加路径“.”。在Antlr jar的同级目录下新建两个bat文件，分别为antlr4.bat和grun.bat，对antlr运行环境进行配置。antlr4.bat和grun.bat文件内容如下所示。

1. java org.antlr.v4.Tool %\*    （antlr4.bat)
1. java org.antlr.v4.gui.TestRig %\*    （grun.bat)

输入antlr4和grun命令，结果如下图所示，Antlr环境配置到此进行完毕。

#### Lab 3 编写Antlr .g4文件

本次实验以C语言作为源语言，使用Antlr v4构建C语言词法分析器。依照实验材料中C11语言规范，编写Antlr v4的.g4文件，从而生成以Java为实现语言的词法分析器。

Antlr的文件以grammar关键字为开头，包括文法规则和词法规则。文法规则以小写字母开头，词法规则以大写字母开头。由于本次实验尚不涉及语法分析部分且课程并未学完，因此只编写了词法规则，语法规则使用Antlr C语言语法样例代码。

C语言共有34个关键字，如下图所示。首先在.g4文件中定义C语言的关键字，标识符以大写字母开头，由于数量过多仅截取部分代码。

```
1. */\*定义c语言关键字\*/*

2. Auto : 'auto';
3. Break : 'break';
4. Case : 'case';
5. Char : 'char';
6. Const : 'const';
7. Continue : 'continue';
8. Default : 'default';
9. Do : 'do';
10. Double : 'double';
11. Else : 'else';
12. Enum : 'enum';
13. Extern : 'extern';
14. Float : 'float';
15. For : 'for';
16. Goto : 'goto';
```

同关键字定义方式相同，定义C语言的运算符和界限符。由于数量过多，仅截取部分代码。

```
1. */\*定义c语言运算符和界限符\*/*
1. LeftParen : '(';
1. RightParen : ')';
1. LeftBracket : '[';
1. RightBracket : ']';
1. LeftBrace : '{';
1. RightBrace : '}';

1. Less : '<';
1. LessEqual : '<=';
1. Greater : '>';
```

依照C11语法规则定义C语言标识符，C语言标识符定义方式如下图所示。标识符可以以下划线或字母开头，包含下划线、字母、数字。标识符定义代码如下所示。


```
1. */\*定义c语言标识符\*/*
1. */\*fragment 后的词法只能被前面的引用，不能出现在文法规则中\*/*
1. Identifier
1. `    `:   IdentifierNondigit
1. `        `(   IdentifierNondigit
1. `        `|   Digit
1. `        `)\*
1. `    `;

1. fragment
1. IdentifierNondigit
1. `    `:   Nondigit
1. `    `;

1. */\*Nondigit代表所有大小写字母\*/*
1. fragment
1. Nondigit
1. `    `:   [a-zA-Z\_]
1. `    `;

1. */\*Digit代表10个数字\*/*
1. fragment
1. Digit
1. `    `:   [0-9]
1. `    `;
```

依照语法规则定义C语言整型常量，包括整型常数、十进制常数、八进制常数、十六进制常数、二进制常数等。除此之外，定义整型数字后缀，如unsigned、long、longlong等。由于代码过长仅截取关键代码。

```
1. */\*定义c语言整型常量，可以为10进制、16进制、8进制、2进制\*/*
2. fragment
3. IntegerConstant
4. `    `:   DecimalConstant IntegerSuffix?
5. `    `|   OctalConstant IntegerSuffix?
6. `    `|   HexadecimalConstant IntegerSuffix?
7. `    `| BinaryConstant
8. `    `;
9. */\*定义整数后缀,unsigned、long、longlong\*/*
10. fragment
11. IntegerSuffix
12. `    `:   UnsignedSuffix LongSuffix?
13. `    `|   UnsignedSuffix LongLongSuffix
14. `    `|   LongSuffix UnsignedSuffix?
15. `    `|   LongLongSuffix UnsignedSuffix?
16. `    `;
```

依照语法规则定义C语言浮点型常量，包括十进制浮点数和十六进制浮点数。十进制浮点数包括小数部分、指数部分和浮点数后缀。浮点数部分可以既包括整数部分和小数部分，也可以只有整数部分和小数点，省略小数部分，因此需要识别两种不同的浮点数。

```
1. */\*定义c语言浮点型常量，包括十进制浮点数和十六进制浮点数\*/*
2. fragment
3. FloatingConstant
4. `    `:   DecimalFloatingConstant
5. `    `|   HexadecimalFloatingConstant
6. `    `;

7. */\*十进制浮点数包括小数部分、指数部分和浮点数后缀\*/*
8. fragment
9. DecimalFloatingConstant
10. `    `:   FractionalConstant ExponentPart? FloatingSuffix?
11. `    `|   DigitSequence ExponentPart FloatingSuffix?
12. `    `;
13. */\*匹配形如21.3或21.的浮点数\*/*
14. fragment
15. FractionalConstant
16. `    `:   DigitSequence? '.' DigitSequence
17. `    `|   DigitSequence '.'
18. `    `;
```

依照语法规则定义C语言字符常量。在定义的过程中需要注意转义字符，避免产生定义时的错误。由于代码过长仅截取关键代码。


```
1. fragment
2. CharacterConstant
3. `    `:   '\'' CCharSequence '\''
4. `    `|   'L\'' CCharSequence '\''
5. `    `|   'u\'' CCharSequence '\''
6. `    `|   'U\'' CCharSequence '\''
7. `    `;

8. /\*定义字符串，字符至少出现1次以上\*/
9. fragment
10. CCharSequence
11. `    `:   CChar+
12. `    `;

13. /\*定义字符常量，除了单引号、\r、\n等符号以外的所有字符\*/
14. fragment
15. CChar
16. `    `:   ~['\\\r\n]
17. `    `|   EscapeSequence
18. `    `;
```

依照语法规则定义C语言字符串字面量，包括前缀和空白字符等。字符串字面量需要去掉双引号、换行符等字符。

```
1. */\*定义c语言字符串字面量\*/*
2. StringLiteral
3. `    `:   EncodingPrefix? '"' SCharSequence? '"'
4. `    `;

5. */\*定义字符串前缀\*/*
6. fragment
7. EncodingPrefix
8. `    `:   'u8'
9. `    `|   'u'
10. `    `|   'U'
11. `    `|   'L'
12. `    `;

13. fragment
14. SCharSequence
15. `    `:   SChar+
16. `    `;
```

最后，定义include语句、块注释和行级注释等语句形式。至此，C语言词法规则已定义完成。

```
1. */\*定义include语句形式\*/*
1. IncludeDirective
1. `    `:   '#' Whitespace? 'include' Whitespace? (('"' ~[\r\n]\* '"') | ('<' ~[\r\n]\* '>' )) Whitespace? Newline
1. `        `-> skip
1. `    `;
1. BlockComment
1. `    `:   '/\*' .\*? '\*/'
1. `        `-> skip
1. `    `;

1. LineComment
1. `    `:   '//' ~[\r\n]\*
1. `        `-> skip
1. `    `;
```

#### Lab 3 生成词法分析器及语法分析器

输入antlr4 MyAntlr4CGrammar.g4 -visitor命令生成visitor类，再输入javac MyAntlr4CGrammer\*.java命令进行编译，生成对应的C语言词法分析器和语法分析器。

将词法分析器和语法分析器打包，生成jar文件，便于后续在BitMiniCC中直接使用。

### Lab 3 测试并运行

将词法分析器和语法分析器打包生成的jar文件添加进BitMiniCC的库文件中，便于后续使用。

BitMiniCC自带4.8版本的Antlr，但词法分析器是通过4.12.0版本的Antlr生成的，在运行时会出现报错。因此，将antlr-4.8-complete.jar文件移出lib，将antlr-4.12.0-complete.jar添加进库文件。

在src/bit/minisys/minicc路径下新建MyBitMiniCC.java文件，用于接收C语言源文件，并使用词法分析器进行处理。在src/bit/minisys/minicc/scanner路径下新建MyCGrammarScanner.java文件，将词法分析结果写入文件中存储。代码如下所示。

```
1. package bit.minisys.minicc;

1. import MyAntlr4CGrammar.MyAntlr4CGrammarLexer;
1. import MyAntlr4CGrammar.MyAntlr4CGrammarParser;
1. import bit.minisys.minicc.scanner.MyCGrammarScanner;
1. import org.antlr.v4.runtime.ANTLRInputStream;
1. *//import org.antlr.runtime.ANTLRInputStream;*
1. import org.antlr.v4.runtime.CommonTokenStream;
1. import org.antlr.v4.runtime.tree.ParseTree;

1. import java.io.FileInputStream;
1. import java.io.IOException;
1. import java.io.InputStream;

1. public class MyBITMiniCC {
1. `    `public static void main(String[] args)throws IOException {
1. *//        for(int i=0;i<args.length;i++){*
1. *//            System.out.println(args[i]);*
1. *//            System.out.println(i);*
1. *//        }*
1. `        `String inputFile=args[0];
1. `        `InputStream is = System.in;
1. `        `is = new FileInputStream(inputFile);
1. `        `*// 新建一个 CharStream, 读取待测试的c语言程序*
1. `        `ANTLRInputStream input = new ANTLRInputStream(is);
1. `        `*// 新建一个词法分析器, 处理输入的 CharStream*
1. `        `MyAntlr4CGrammarLexer lexer = new MyAntlr4CGrammarLexer(input);
1. `        `*// 新建一个词法符号的缓冲区, 用于存储词法分析器将生成的词法符号*
1. `        `CommonTokenStream tokens = new CommonTokenStream(lexer);
1. `        `*// 新建一个语法分析器,处理词法符号缓冲区中的内容*
1. `        `MyAntlr4CGrammarParser parser = new MyAntlr4CGrammarParser(tokens);
1. `        `ParseTree tree = parser.compilationUnit();
1. `        `*// 用LISP风格打印生成的树*
1. `        `*//System.out.println(tree.toStringTree(parser));*

1. `        `String fName = inputFile.trim();
1. `        `String temp[] = fName.split("\\\\");
1. `        `*//词法分析的结果写入到同名文件中，文件后缀为.tokens*
1. `        `String tokenFileName =temp[temp.length - 1] + ".tokens";
1. `        `*//使用MyCGrammarScanner进行词法分析*
1. `        `MyCGrammarScanner myCGrammarScanner = new MyCGrammarScanner(tokenFileName,tokens);
1. `    `}
1. }

1. package bit.minisys.minicc.scanner;

1. import org.antlr.v4.runtime.CommonTokenStream;

1. import java.io.File;
1. import java.io.FileWriter;
1. import java.io.IOException;
1. import java.util.List;

1. public class MyCGrammarScanner {
1. `    `public MyCGrammarScanner(String tokenFileName,CommonTokenStream tokens) throws IOException {
1. `        `System.out.println("-> Using MyCGrammarScanner ");
1. `        `*//将词法分析的结果写入到同名文件中，文件后缀为.tokens*
1. `        `FileWriter fileWriter = new FileWriter(new File(tokenFileName));
1. `        `*//将每一个词法分析的结果转换为字符串类型，写入文件中*
1. `        `for(int i=0;i<tokens.getNumberOfOnChannelTokens();i++){
1. `            `fileWriter.write(tokens.get(i).toString());
1. `            `fileWriter.write("\n");
1. `        `}
1. `        `fileWriter.close();
1. `    `}
1. }
```

## Lab 4 C语言语法文法设计与验证实验
