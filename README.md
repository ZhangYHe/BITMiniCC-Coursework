# BITMiniCC-Coursework

北京理工大学 编译原理 课程作业

[BIT-MiniCC](https://github.com/jiweixing/BIT-MiniCC)

- [BITMiniCC-Coursework](#bitminicc-coursework)
  - [Lab 1 语言认知实验](#lab-1-语言认知实验)
    - [Lab 1 实验内容](#lab-1-实验内容)
  - [Lab 2 编译器认知实验](#lab-2-编译器认知实验)
    - [Lab 2 实验内容](#lab-2-实验内容)
    - [Lab 2 对比分析](#lab-2-对比分析)
      - [Lab 2 LLVM运行结果分析](#lab-2-llvm运行结果分析)
      - [Lab 2 GCC与LLVM运行结果对比分析](#lab-2-gcc与llvm运行结果对比分析)
  - [Lab 3 词法分析实验](#lab-3-词法分析实验)
    - [Lab 3 实验内容](#lab-3-实验内容)
    - [Lab 3 过程及步骤](#lab-3-过程及步骤)
      - [Lab 3 安装Antlr v4](#lab-3-安装antlr-v4)
      - [Lab 3 编写Antlr .g4文件](#lab-3-编写antlr-g4文件)
      - [Lab 3 生成词法分析器及语法分析器](#lab-3-生成词法分析器及语法分析器)
    - [Lab 3 测试并运行](#lab-3-测试并运行)
  - [Lab 4 C语言语法文法设计与验证实验](#lab-4-c语言语法文法设计与验证实验)
    - [Lab 4 实验内容](#lab-4-实验内容)
    - [Lab 4 过程及步骤](#lab-4-过程及步骤)
      - [Lab 4 C语言文法子集描述](#lab-4-c语言文法子集描述)
      - [Lab 4 程序推导过程](#lab-4-程序推导过程)
  - [Lab 5 语法分析实验](#lab-5-语法分析实验)
    - [Lab 5 实验内容](#lab-5-实验内容)
    - [Lab 5 实验过程及步骤](#lab-5-实验过程及步骤)
      - [Lab 5 扩充C语言文法子集](#lab-5-扩充c语言文法子集)
      - [Lab 5 语法分析](#lab-5-语法分析)
      - [Lab 5 扩充语句块](#lab-5-扩充语句块)
      - [Lab 5 函数调用](#lab-5-函数调用)
      - [Lab 5 if-else语句](#lab-5-if-else语句)
      - [Lab 5 for循环语句](#lab-5-for循环语句)
      - [Lab 5 声明语句](#lab-5-声明语句)
  - [Lab 6 语义分析实验](#lab-6-语义分析实验)
    - [Lab 6 实验内容](#lab-6-实验内容)
    - [Lab 6 实验过程及步骤](#lab-6-实验过程及步骤)
      - [Lab 6 框架设计](#lab-6-框架设计)
      - [Lab 6 遍历语法树](#lab-6-遍历语法树)
      - [Lab 6 ES01：变量使用前是否进行了定义](#lab-6-es01变量使用前是否进行了定义)
      - [Lab 6 ES02：变量是否存在重复定义](#lab-6-es02变量是否存在重复定义)
      - [Lab 6 ES03：break语句是否在循环语句中使用](#lab-6-es03break语句是否在循环语句中使用)
      - [Lab 6 ES04：函数调用的参数个数和类型是否匹配](#lab-6-es04函数调用的参数个数和类型是否匹配)
      - [Lab 6 ES07：goto的目标是否存在](#lab-6-es07goto的目标是否存在)
      - [Lab 6 ES08：函数是否以return结束](#lab-6-es08函数是否以return结束)
      - [Lab 6 ES05、ES06](#lab-6-es05es06)
  - [Lab 7 中间代码生成实验](#lab-7-中间代码生成实验)
    - [Lab 7 实验内容](#lab-7-实验内容)
    - [Lab 7 实验过程及步骤](#lab-7-实验过程及步骤)
      - [Lab 7 框架设计](#lab-7-框架设计)
      - [Lab 7 遍历语法树](#lab-7-遍历语法树)
      - [Lab 7 数组声明](#lab-7-数组声明)
      - [Lab 7 函数声明](#lab-7-函数声明)
      - [Lab 7 二元表达式](#lab-7-二元表达式)
      - [Lab 7 循环语句](#lab-7-循环语句)
      - [Lab 7 条件语句](#lab-7-条件语句)
      - [Lab 7 后缀表达式](#lab-7-后缀表达式)
      - [Lab 7 goto语句](#lab-7-goto语句)
  - [Lab 8 目标代码生成实验](#lab-8-目标代码生成实验)
    - [Lab 8 实验内容](#lab-8-实验内容)
    - [Lab 8 实验过程及步骤](#lab-8-实验过程及步骤)
      - [Lab 8 框架设计](#lab-8-框架设计)
      - [Lab 8 对中间代码生成模块的改进](#lab-8-对中间代码生成模块的改进)
        - [Lab 8 循环与选择语句](#lab-8-循环与选择语句)
        - [Lab 8 数组引用](#lab-8-数组引用)
        - [Lab 8 模块输出](#lab-8-模块输出)
        - [Lab 8 二元表达式](#lab-8-二元表达式)
        - [Lab 8 函数调用](#lab-8-函数调用)
        - [Lab 8 break语句](#lab-8-break语句)
      - [Lab 8 生成data段](#lab-8-生成data段)
      - [Lab 8 寄存器分配](#lab-8-寄存器分配)
      - [Lab 8 比较运算](#lab-8-比较运算)
      - [Lab 8 相等比较](#lab-8-相等比较)
      - [Lab 8 条件跳转](#lab-8-条件跳转)
      - [Lab 8 函数调用](#lab-8-函数调用-1)
      - [Lab 8 返回语句](#lab-8-返回语句)
      - [Lab 8 二元运算](#lab-8-二元运算)
      - [Lab 8 自增自减运算](#lab-8-自增自减运算)
      - [Lab 8 赋值运算](#lab-8-赋值运算)
      - [Lab 8 数组访问](#lab-8-数组访问)

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

#### Lab 2 GCC与LLVM运行结果对比分析

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

### Lab 4 实验内容

本实验主要的内容为以下三项内容：

（1）阅读附件提供的C语言和Java语言的规范草稿，了解语言规范化定义应包括的具体内容。

（2）选定C语言子集，并使用BNF表示方法文法进行描述，要求至少包括表达式、赋值语句、分支语句和循环语句；或者设计一个新的程序设计语言，并使用文法对该语言的词法规则和文法规则进行描述。

（3）根据自己定义的文法子集，推导出“HelloWord”程序。

以上语言定义首先要给出所使用的字母表，在此基础上使用2型文法描述语法规则。

### Lab 4 过程及步骤

#### Lab 4 C语言文法子集描述

在本次实验中，使用BNF表示方法对C语言文法子集进行描述，其中语法规则为2型文法。

程序包括返回值类型、函数名称、函数参数列表、函数体。对于函数名称、变量名称等，以字母或下划线开始，包括字母、数字、下划线。对于函数体，定义了表达式、赋值语句、分支语句、循环语句、switch-case语句等。

C语言文法子集描述如下所示。

```
1. <program> -> <external-declaration>

1. <external-declaration> -> <function-definition>

1. <function-definition> -> <type-specifier> <identifier> ( <function-declaration-list> ) <compound-statement>

1. <function-declaration-list> -> <function-declaration> | <function-declaration-list> , <function-declaration>

1. <function-declaration> -> <type-specifier> <identifier-list> | ε

1. <compound-statement> -> { <declaration-list> ; <statement-list> }

1. <declaration-list> -> <declaration> | ε

1. <declaration> -> <type-specifier> <identifier-list> ;

1. <type-specifier> -> int | char | float | double | void | short | long | signed | unsigned

1. <identifier-list> -> <identifier> | <identifier-list> , <identifier>

1. <identifier> -> <letter> <id-suffix>

1. <id-suffix> -> <letter> | <digit> | \_

1. <letter> -> a | b | ... | z | A | B | ... | Z

1. <digit> -> 0 | 1 | ... | 9

1. <statement-list> -> <statement> | <statement-list> <statement> | ε

1. <statement> -> <expression-statement> | <compound-statement> | <selection-statement> | <iteration-statement> | <jump-statement> | <function-call>

1. <expression-statement> -> <expression> ; | ;

1. <compound-statement> -> { <declaration-list> <statement-list> }

1. <selection-statement> -> if ( <expression> ) <statement> <else> | if ( <expression> ) <statement> <else> <statement> | switch ( <expression> ) <switch-block>

1. <else> -> else

1. <iteration-statement> -> while ( <expression> ) <statement> | do <statement> while ( <expression> ) ; | for ( <expression> ; <expression> ; <expression> ) <statement>

1. <jump-statement> -> goto <identifier> ; | continue ; | break ; | return <expression> ;

1. <expression> -> <assignment-expression> | <function-call> | <expression> , <assignment-expression>

1. <function-call> -> <identifier> ( <argument-list> )

1. <argument-list> -> <assignment-expression> | <string-literal> | <argument-list> , <assignment-expression> | <argument-list> , <string-literal>

1. <assignment-expression> -> <unary-expression> <assignment-operator> <assignment-expression> | <conditional-expression>

1. <assignment-operator> -> = | \*= | /= | %= | += | -= | <<= | >>= | &= | ^= | |=

1. <switch-block> -> { <switch-block-list> }

1. <switch-block-list> -> <case-statement> | <default-statement> | <case-statement> <switch-block-list> | <default-statement> <switch-block-list>

1. <case-statement> -> case <constant-expression> : <statement-list>

1. <default-statement> -> default : <statement-list>

1. <unary-expression> -> <postfix-expression> | ++ <unary-expression> | -- <unary-expression> | <unary-operator> <cast-expression>

1. <unary-operator> -> & | \* | + | - | ~ | !

1. <primary-expression> -> <identifier> | <constant> | ( <expression> )

1. <constant> -> <integer-constant> | <character-constant> | <floating-constant> | <enumeration-constant> | <string-literal>

1. <integer-constant> -> <decimal-constant> | <octal-constant> | <hexadecimal-constant>

1. <string-literal> -> " <s-char-sequence> "

1. <s-char-sequence> -> <s-char> | <s-char-sequence> <s-char>

1. <s-char> -> <escape-sequence> | <non-escape-char>
```

#### Lab 4 程序推导过程

使用2.2中定义的文法子集推导C语言“hello world“程序。 程序如下所示。

```
int main()
{
`    `printf("hello world\n");
`    `return 0;
}
```

程序推导过程如下所示。

```
<program> -> <external-declaration>

<program> -> <function-definition>

<program> -> <type-specifier> <identifier> ( <function-declaration-list> ) <compound-statement>

<program> -> int main ( <function-declaration-list> ) <compound-statement>

<program> -> int main ( <function-declaration> ) <compound-statement>

<function-declaration> -> ε

<program> -> int main () <compound-statement>

<program> -> int main () { <declaration-list> ; <statement-list> }

<program> -> int main () { <statement-list> <statement> }

<program> -> int main () { <statement> ; <statement > ; }

<program> -> int main () { <function-call> ; <statement> ; }

<program> -> int main () { <identifier> ( <argument-list> ) ; <statement> ; }

<program> -> int main () { printf ( <argument-list> ) ; <statement> ; }

<program> -> int main () { printf ( <string-literal> ) ; <statement> ; }

<program> -> int main () { printf ( “hello world\n” ) ; <statement> ; }

<program> -> int main () { printf ( “hello world\n” ) ; return 0 ; }
```

## Lab 5 语法分析实验

### Lab 5 实验内容

该实验选择C语言的一个子集，基于BIT-MiniCC构建C语法子集的语法分析器，该语法分析器能够读入词法分析器输出的存储在文件中的属性字符流，进行语法分析并进行错误处理，如果输入正确时输出JSON格式的语法树，输入不正确时报告语法错误。

### Lab 5 实验过程及步骤

#### Lab 5 扩充C语言文法子集

在本次实验所给参考文法的基础上，对其进行扩展，满足c语言语法规则，是c语言文法的子集。我扩充了局部变量声明、分支语句、循环语句、函数调用等语句。在扩充文法时，进行文法的等价变换，提取左递归并消除公共因子，从而避免程序运行时由于文法造成的死循环。同时，还添加了常量表达式、关系表达式、一元运算、二元运算、条件表达式、数组声明等。

#### Lab 5 语法分析

结合LL(1)分析方法和递归下降分析方法进行语法分析。在扩充文法后计算部分符号的FIRST集合和FOLLOW集合。将所有待匹配的终结符存入List中，维护指向当前位置序号的tokenIndex。matchToken()函数为负责匹配终结符，若匹配成功则更新tokenIndex，否则报错。采用递归下降分析方法，从Program的ASTNode根节点出发，按照文法规则递归下降分析，不断进行函数调用。匹配到终结符或结果为空时返回。在递归下降的过程中不断添加新的AST节点，动态更新。这样依次匹配待分析程序所有的终结符，便可以得到最终的JSON格式的语法分析树。若匹配失败则报告语法错误。结合LL(1)的分析方法，在分析的过程中检查待识别终结符之后的符号，从而选择对应的函数进行识别。

#### Lab 5 扩充语句块

对ExampleParser.java中的stmt语句块进行扩充，增加return语句、if-else语句、for语句和表达式语句。对于for语句，分两种情况进行判断。一种是inti结构中包含变量声明及赋值，另外一种情况则不包含。对于表达式语句，将识别到的表达式添加进列表中，最后存入ASTExpressionStatement类型的变量。代码如下图所示。

```
//STMT --> ASSIGN_STMT | RETURN_STMT | DECL_STMT | FUNC_CALL
public ASTStatement stmt() {
 nextToken = tknList.get(tokenIndex);
 //  System.out.println(nextToken.line);
 //  System.out.println(nextToken.column);
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
 else if(nextToken.type.equals("Identifier")){
  //表达式语句
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
```

#### Lab 5 函数调用

在stmt语句块中，若下一个待匹配字符为"Identifier"，则该语句应为表达式语句或函数调用。此时调用expr()函数不断匹配。expr()函数调用term()函数，term()函数调用factor()函数，在factor()函数中进行函数调用语句的识别。

若下一个待匹配字符为"Identifier"，且第二个待匹配字符为'(',此时为函数调用语句。创建ASTFunctionCall类型变量，存储函数调用部分。函数名为ASTIdentifier类型，参数列表为ArrayList类型。匹配到函数名和'('后，进行参数列表的识别，将识别结果存入ASTFunctionCall.argList列表中。当下一个待识别的终结符为')'时识别并返回，此时便可以正确识别函数调用语句。expr()函数中函数调用部分代码如下所示。

```
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
   //     arg.add(idf);
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
 return fc;
}
```

#### Lab 5 if-else语句

在stmt语句块中判断下一个待识别的终结符，若为'if'，则为if-else语句，调用分支语句进行识别。首先匹配'if'和'('，之后调用expr()函数识别if条件中的表达式。若if条件后为'{'，则为语句块，调用codeBlock()函数递归识别语句块；若if条件后没有'{'，则只有一个语句，调用stmt()函数识别这条语句。若if语句块后为'else'，则将else部分存入ASTSelectionStatement类型变量的otherwise中。else部分识别过程与if部分基本相同。在这种情况下，if语句只能匹配距离最近的else语句，若有多个else语句则会发生错误。if-else语句部分代码如下所示。

```
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
```

#### Lab 5 for循环语句

在stmt()函数中，若识别到for关键词，则递归进行for循环识别。此时分为两种情况，for初始化部分含有变量声明和不含变量声明两种情况。在匹配了'for'和'('两个终结符后，使用三个循环识别init、cond和step三个部分。对于init和cond部分，在循环过程中不断判断下一个待识别的终结符是否为';',若不是则调用expr()函数递归识别表达式，若是';'则退出循环并识别';'。对于step部分循环结束条件为')'，其余部分和init、cond相同。for循环语句部分代码如下所示。

```
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
```

#### Lab 5 声明语句

定义函数识别声明语句。在codeBlock()函数中，若下一个待识别的终结符为类型，则此时为声明语句，调用函数递归识别。

首先识别变量类型，将其存入ASTDeclaration类型变量的specifiers中，更新nextToken序号。之后调用函数识别变量标识符。若变量标识符下一个终结符为']'，则此时为数组。调用expr()函数识别'['和']'中的表达式，成功匹配后返回。若变量标识符下一个终结符不是']'，此时为普通变量声明，识别标识符后即可返回。

若变量标识符后为'=',则此时的声明语句包含初始化列表。初始化列表可以为具体数据，也可以为数组初始化列表。若'='后的终结符为'{',此时为数据初始化。循环调用expr()函数识别表达式，直到下一符号为'}'时退出循环。若若'='后的终结符不是'{'，此时为普通变量声明。例如"int a=1;"，同理调用expr()函数进行识别。声明语句部分代码如下所示。

```
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
   // 初始化
   // int a=1;
   else{
    System.out.println("decl+++");
    System.out.println(tknList.get(tokenIndex).line);
    System.out.println(tknList.get(tokenIndex).column);
    
    LinkedList<ASTExpression> inlist_exprs = new LinkedList<>();
    //     while (!nextToken.type.equals("';'")) {
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
  
  //  nextToken= tknList.get(tokenIndex);
  //  if(nextToken.type.equals("';'")){
   //   matchToken("';'");
   //  }
  return dc;
 }
```

## Lab 6 语义分析实验

### Lab 6 实验内容

语义分析阶段的工作为基于语法分析获得的分析树构建符号表，并进行语义检查。如果存在非法的结果，请将结果报告给用户其中语义检查的内容主要包括：

- 变量使用前是否进行了定义；
- 变量是否存在重复定义；
- break 语句是否在循环语句中使用；
- 函数调用的参数个数和类型是否匹配；
- 函数使用前是否进行了定义或者声明；
- 运算符两边的操作数的类型是否相容；
- 数组访问是否越界；
- goto 的目标是否存在；
- 函数是否以return结束;
- ...

本次语义检查的前（1）-（3）为要求完成内容，而其余为可选内容。在本次实验中，我完成了（1）-（5）和（8）（9）。（6）由于框架内预处理器将左移运算符拆分为"<""<"，并且我自己写的前几次实验代码没有包含左右移运算符，无法对框架内测试用例进行正确处理，因此没有实现（6）。

### Lab 6 实验过程及步骤

#### Lab 6 框架设计

本次实验需要根据AST语法树进行语义分析。结合课上所学知识，需要将AST转换为CST，建立符号表，并遍历语法树。在此期间进行错误检查。

符号表的设计如图所示。通过一个TotalTable的列表管理各个符号表，列表中的第一项为全局符号表，其余元素为函数符号表。每个函数都有一个符号表。全局符号存储在GlobalTable中，从函数符号表到全局符号表有一个引用。

符号表通过ArrayList数据结构实现。符号表中存储的数据有以下内容：

- 变量或函数名称
- 数据类型
- 语句类型
- 作用域范围
- 函数的参数类型
- 内部嵌套的作用域符号表

#### Lab 6 遍历语法树

在遍历AST语法树的过程中进行错误检测。从根节点出发，使用DFS的方式递归遍历所有节点。根据不同的AST节点重写visit()函数，不同的节点进入不同的函数中处理。在遍历语法树的过程中，对于定义语句需要分为函数定义和变量定义两种情况进行处理。对于函数定义需要为其添加符号表，并在全局符号表中添加对应的函数名和函数符号表。

在visit的过程中，需要根据AST的结构进行类型转换，从而适配不同的visit()函数。例如在判断goto目标是否存在时，需要将ASTNode类型的变量转换为ASTLabeledStatement类型，这样才能在public void visit(ASTLabeledStatement labeledStat) {}函数中处理。

遍历语法树的代码如下所示。

```
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
```

#### Lab 6 ES01：变量使用前是否进行了定义

对于每一个标识符，分别在当前函数符号表、全局符号表中搜索。若均没有找到对应的变量定义则出现错误。对于函数是否定义的判断同理。

检查变量使用前是否进行了定义的代码如下所示。

```
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
```

#### Lab 6 ES02：变量是否存在重复定义

在遍历语法树的过程中，若节点类型为ASTFunctionDefine，此时判断函数是否重定义。遍历全局符号表，查找是否有相同名称的函数定义记录。由于C语言不支持函数内重定义，因此无需遍历其他函数的符号表。该部分代码如下所示。

```
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
```

遍历过语法树后，遍历全局符号表以及所有的函数符号表，查找是否存在重复定义的符号。关键代码如下所示。

```
//查找当前位置之后有无重复声明
for(int j=0;j<tmp_table.size();j++){
 for(int k=j+1;k<tmp_table.size();k++){
  if((tmp_table.get(j).Name.equals(tmp_table.get(k).Name))&&(tmp_table.get(j).Kind.equals(tmp_table.get(k).Kind))&&(tmp_table.get(k).Kind.equals("VariableDeclarator")||tmp_table.get(k).Kind.equals("FunctionDeclarator"))){
   System.out.println("ES02 >> Declaration:"+tmp_table.get(k).Name+" has been declarated.");
  }
 }
}
```

#### Lab 6 ES03：break语句是否在循环语句中使用

使用变量is\_in\_loop记录当前语句是否位于循环中。在进入循环语句时将该变量设置为1，退出循环语句时将其重置为0。

```
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
```

若遇到BreakStatement语句时，is\_in\_loop变量值为1，这代表break语句在循环语句中使用，向用户提示并报错。

```
else if(tmp_type.equals("BreakStatement")){
 //break语句不在循环中
 if(this.is_in_loop!=1){
  System.out.println("ES03 >> BreakStatement:must be in a LoopStatement.");
 }
}
```

#### Lab 6 ES04：函数调用的参数个数和类型是否匹配

对于函数调用的ASTFunctionCall节点，首先检查函数名是否定义。其次检查函数参数个数是否一致，若参数个数不一致则报错。最后检查参数类型是否与符号表中记录的参数类型一致，若不一致则报错。

检查函数调用的参数个数和类型是否匹配的关键代码如下所示。

```
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
```

#### Lab 6 ES07：goto的目标是否存在

在遍历语法树的过程中，遇到ASTLabeledStatement类型的节点时，代表这是一个goto的标号，在符号表中记录该标号，并将标号的类型设置为“label”，随后调用参数为ASTIdentifier类型的visit()函数处理标号。若遇到ASTGotoStatement类型的goto语句，在符号表中搜索标号是否存在，若不存在则报错。

检查goto的目标是否存在的关键代码如下所示。

```
@Override
public void visit(ASTLabeledStatement labeledStat) throws Exception {
 //从table_cur开始为label
 int table_cur = cur_table.size();
 labeledStat.label.accept(this);
 //更新label的Kind
 cur_table.get(table_cur).Kind = "label";
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
```

#### Lab 6 ES08：函数是否以return结束

使用is\_return标识符记录当前函数是否有return语句。。在ASTReturnStatement类型的节点中，将is\_return标识符设置为1，这代表函数中出现了1个return语句。对于函数定义语句的ASTFunctionDefine类型的节点，在进入其visit()函数时将is\_return标识符设置为0，即将离开该visit()函数时若is\_return标识符仍为0，代表函数缺少return语句，此时报错提醒用户；若is\_return标识符为1，代表函数以return结束。

判断函数是否以return结束的部分代码如下所示。

```
functionDefine.body.accept(this);
if(is_return==0){
 System.out.println("ES08 >> Function:"+this.cur_table.get(0).Name+" must have a return in the end.");
}

//return语句
@Override
public void visit(ASTReturnStatement returnStat) throws Exception {
 //有return，更新is_return标志
 is_return=1;
}
```

#### Lab 6 ES05、ES06

本次实验我实现了ES01-ES04、ES07和ES08。对于ES05运算符两边的操作数的类型是否相容这一错误，由于测试用例中存在"<<"运算符，但框架内预处理器将左右移运算符拆分为"<"，并且我自己写的前几次实验代码没有包含左右移运算符，无法对框架内测试用例进行正确处理，因此没有实现ES05。若后续有机会，我将继续完成对ES05、ES06错误的检测。

```
int main()
{
 int a = 2;
 double b = 3.5;
 int res = a<<b;
 return 0;
}
```

## Lab 7 中间代码生成实验

### Lab 7 实验内容

以自行完成的语义分析阶段的抽象语法树为输入，或者以BIT\_MiniCC的语义分析阶段的抽象语法树为输入，针对不同的语句类型，将其翻译为中间代码序列。例如下面的输入语句：

```
int main() {
 int a, b, c;
 a = 0;
 b = 1;
 c = 2;
 c = a + b + (c + 3);
 return 0;
}
```

### Lab 7 实验过程及步骤

#### Lab 7 框架设计

本次实验需要根据语义分析阶段的抽象语法树作为输入，生成对应的中间代码。和Lab 6语义分析的操作过程相似，生成全局符号表global\_table与函数符号表cur\_table。其中全局符号表存储函数信息与全局变量信息，函数符号表存储不同函数的信息。符号表通过ArrayList数据结构实现。符号表的结构和语义分析中的一致，符号表中存储的数据有以下内容：

- 变量或函数名称
- 数据类型
- 语句类型
- 作用域范围
- 函数的参数类型
- 内部嵌套的作用域符号表

使用DFS的方式递归遍历语法树。根据不同的AST节点重写visit()函数,不同的节点进入不同的函数中处理。本次实验的中间代码选择四元式的表示方法，操作数和返回值的结构直接使用AST节点。使用CursorValue记录当前代码的行号。

与语义分析实验中不同的是，在中间代码生成的过程中需要对数组说明进行翻译。使用教材上的数组内情向量表记录数组信息，内情向量包括以下内容：

- 数组名称
- 数据类型
- 数组维数
- 数组每一维的上下限

#### Lab 7 遍历语法树

与语义分析实验中的遍历语法树方法相似。从根节点出发，使用DFS的方式递归遍历所有节点。根据不同的AST节点重写visit()函数，不同的节点进入不同的函数中处理。在遍历语法树的过程中，对于定义语句需要分为函数定义和变量定义两种情况进行处理。对于函数定义需要为其添加符号表，并在全局符号表中添加对应的函数名和函数符号表。在visit的过程中，需要根据AST的结构进行类型转换，从而适配不同的visit()函数。许多visit()函数例如public void visit(ASTExpressionStatement expressionStat)等均与语义分析中一致。

#### Lab 7 数组声明

对于数组声明语句，使用内情向量表进行翻译。每个数组对应表中的一个内情向量。初始时数组的内情向量维度为0。根据json格式的数组定义的特点，递归进行修改。若当前为ASTArrayDeclarator类型，则此时表示内层还有嵌套定义的数组，继续访问下一个维度的数组，并修改内情向量的维度+1。若当前为ASTVariableDeclarator类型，则此时已经访问到数组定义的最后一个维度，修改name并将维度+1。根据数组声明时的下标，修改内情向量表中该维度的上下限。

数组声明部分的代码如下所示。

```
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
```

#### Lab 7 函数声明

对于函数声明语句，遍历函数参数列表。若参数列表不为空，则将函数参数信息添加到函数符号表中，便于后续处理与判断。

函数声明部分的关键代码如下所示。

```
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
```

#### Lab 7 二元表达式

对于二元表达式语句，首先判断运算符的类型。共分为三大类，分别是“=”赋值表达式，“+-*/\%”等算数运算表达式，"< > ==“等逻辑运算表达式。

对于赋值操作，首先获取被赋值对象的res，根据等号右侧对象的类型分别处理。若等号右侧为二元表达式，则需要单独处理，将其整合成一个四元式。例如赋值语句a = b + c，需要生成四元式(=,b,c,a),而不是生成两个四元式 tmp1 = b + c和a = tmp1。

对于算术运算表达式和逻辑运算表达式，需要将结果存储到中间变量中，生成相应的四元式。

二元表达式部分的关键代码如下所示。

```
if (op.equals("=")) {
 // 赋值操作
 // 获取被赋值的对象res
 visit(binaryExpression.expr1);
 res = map.get(binaryExpression.expr1);
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
 } else {
  // else ...
 }
}
```

#### Lab 7 循环语句

循环语句按照教材中的方式进行翻译。其中init、cond、step分别为循环的初值表达式、终值和步长表达式；S为循环体的语句序列。翻译后的代码结构如下所示。

```
init.code
cond.code         L1
测试cond.code
(jt, , ,L2)
(jf, , ,L3)
step.code         L4
(j, , ,L1)
S.code            L2
(j, , ,L4)
      L3
          
```

在init后，cond前添加跳转标号L1，便于循环体结束后跳转回来进行下一次循环。在step前添加跳转标号L4，便于循环体结束后进行更新步长的操作。循环语句的关键代码如下所示。

```
//初始化
for(int i=0;i<iterationStat.init.size();i++){
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

//循环
if(iterationStat.stat instanceof ASTCompoundStatement){
 ASTCompoundStatement cs = (ASTCompoundStatement)iterationStat.stat;
 res = new CursorValue(cursor+3+iterationStat.step.size()+cs.blockItems.size());
}else{
 res = new CursorValue(cursor+4+iterationStat.step.size());
}

Quat quat2 = new Quat(cursor++,"Jf",res,opnd1,opnd2);
quats.add(quat2);

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
```

#### Lab 7 条件语句

条件语句按照教材中的方式进行翻译。首先判断条件表达式的类型，将cond转换为对应类型的数据。为if和else语句添加相应的跳转四元式。最后通过visit()函数分别转入相应的then语句块和else语句块生成四元式。

条件语句的关键代码如下所示。

```
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
 res = new CursorValue(cursor+2);
 Quat quat2 = new Quat(cursor++,"J",res,opnd1,opnd2);
 quats.add(quat2);
 visit(selectionStat.then);
 visit(selectionStat.otherwise);
}
```

#### Lab 7 后缀表达式

对于形如i++的后缀表达式，opnd1和res都设置为ASTpostfixExpression.expr，opnd2设置为null。

后缀表达式的关键代码如下所示。

```
if (postfixExpression.expr instanceof ASTIdentifier) {
 opnd1 =postfixExpression.expr;
} else if (postfixExpression.expr instanceof ASTIntegerConstant) {
 opnd1 = postfixExpression.expr;
}  else {
 // else ...
}
res = opnd1;
```

#### Lab 7 goto语句

对于ASTLabeledStatement类型的标号，在符号表中记录信息，包括名称、类型、作用域位置等。

goto语句标号的关键代码如下所示。

```
@Override
public void visit(ASTLabeledStatement labeledStat) throws Exception {
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
```

对于ASTGotoStatement类型的goto语句，在符号表中查询有无相同的标号。成功找到则记录label的cursor，生成对应的四元式。

goto语句的关键代码如下所示。

```
for(int i=0;i<cur_table.size();i++) {
 if(cur_table.get(i).Name.equals(gotoStat.label.value)) {
  //记录标号cursor
  Integer goto_label = cur_table.get(i).Scope_entry;
  res = new CursorValue(goto_label);;
  Quat quat = new Quat(cursor++,"J",res,opnd1,opnd2);
  quats.add(quat);
  break;
 }
```

## Lab 8 目标代码生成实验

### Lab 8 实验内容

基于BIT-MiniCC 构建目标代码生成模块，该模块能够基于中间代码选择合适的目标指令，进行寄存器分配，并生成相应平台汇编代码。

如果生成的是MIPS或者RISC-V 汇编，则要求汇编代码能够在BIT-MiniCC集成的 MIPS 或者RISC-V 模拟器中运行。需要注意的是，config.xml 的最后一个阶段“ncgen ”的"skip”属性配置为“false”,"target" 属性设置为“mips”、"x86"或者"riscv"中的一个。

如果生成的是X86汇编，则要求使用X86汇编器生成exe文件并运行。

本次实验我选择生成MIPS汇编，通过了0\_BubbleSort.c、1\_Fibonacci.c、2\_Prime.c、3\_PerfectNumber.c、5\_YangHuiTriangle.c这五个测试用例。

### Lab 8 实验过程及步骤

#### Lab 8 框架设计

本次实验我选择生成MIPS汇编。基于Lab 7 中间代码生成实验所生成的四元式，选择合适的目标指令，进行寄存器分配，最终生成汇编代码。

本次实验和中间代码生成部分紧密结合，需要根据符号表进行寄存器分配，生成data段等。因此我修改了BIT-MiniCC框架，将中间代码生成阶段的全局符号表作为函数参数传入目标代码生成模块。遍历符号表，生成.data段的字符串，为变量分配寄存器。由于跳转语句的四元式的标号和跳转行号没有明确对应关系，因此需要在目标代码生成的过程中遍历所有四元式，记录跳转标号和行号的对应关系，便于后续生成汇编的跳转语句。

全局符号表global\_table存储函数信息与全局变量信息，符号表通过ArrayList数据结构实现。符号表的结构和语义分析中的一致，符号表中存储的数据有以下内容：

- 变量或函数名称
- 数据类型
- 语句类型
- 作用域范围
- 函数的参数类型
- 内部嵌套的作用域符号表

对于加减乘除、自增自减等运算，根据运算符的类型生成相应的汇编语句。对于数组赋值、数组引用的运算，在data段定义数组，使用"la"指令加载地址，计算数组元素的地址或取出元素的值。对于函数调用的四元式，根据符号表中函数参数信息，将参数压入栈中。

使用堆栈数据结构管理临时变量、中间计算结果等信息。生成汇编语句后，将存储计算结果的寄存器压入栈内，后续使用时只需从栈中弹出即可。对于函数调用四元式，将参数压入栈中，使用时出栈并存入子函数参数寄存器中，实现函数调用。

#### Lab 8 对中间代码生成模块的改进

##### Lab 8 循环与选择语句

对于循环语句，在循环语句四元式的最后生成\_endloop标识符。使用endloop\_count变量计算循环序号。在上一次实验中使用cursor+3+iterationStat.step.size()+cs.blockItems.size()的方式计算条件不满足时跳转到的位置。但由于数组引用、数组赋值会多产生几行四元式，因此这样的计算方式在本次实验中失效。使用标识符标记循环结束位置，若条件不满足则跳转至本层循环的\_endloop标识符。

```
String endloop = "_endloop"+(endloop_count-2);
Quat quat_endloop = new Quat(cursor++,endloop);
quats.add(quat_endloop);
```

为了满足嵌套跳转的要求，对于选择语句，在当前层次的if-else结尾处设置endif标号。使用endif\_count变量维护endif序号。在进入visit((ASTSelectionStatement)statement)前将endif\_flag设置为true，从而为每个嵌套的if设置endif标号。

```
if(endif_flag){
 String endif = "_endif"+(endif_count-2);
 Quat quat_endif = new Quat(cursor++,endif);
 quats.add(quat_endif);
 endif_flag = false;
 opnd1 = null;
 opnd2 = null;
 res = new ASTIdentifier();
 ((ASTIdentifier) res).value = "endif"+(endif_count-1);
 Quat quat_jumpif = new Quat(cursor++,"J",res,opnd1,opnd2);
 quats.add(quat_jumpif);
 
}else{
 String endif = "_endif"+(endif_count-1);
 Quat quat_endif = new Quat(cursor++,endif);
 quats.add(quat_endif);
}
```

##### Lab 8 数组引用

使用Lab 7的数组内情向量表记录数组信息，内情向量包括以下内容：

- 数组名称
- 数据类型
- 数组维数
- 数组每一维的上下限

对于多维数组，ASTArrayAccess类型的节点存在嵌套定义。和数组引用的处理方式类似，若ASTArrayAccess.expr为ASTArrayAccess类型，则为多维数组；否则已经递归到数组标识符。

使用教材中的方式计算数组元素地址，根据数组维数计算不变量C。

```
int sumc = 1, tnum = 1;
LinkedList c = new LinkedList();

for(int i=limit.size()-1;i>0;i--) {
 tnum = tnum * (int)limit.get(i).upper;
 c.addFirst(tnum);
 sumc += tnum;
}
```

使用不同的符号区分数组元素引用和数组元素赋值。对于数组元素引用的四元式，符号为"=[]";数组元素赋值符号为"[]="。

```
ASTIntegerConstant d = new ASTIntegerConstant(sumc,-1);
ASTIntegerConstant d0 = new ASTIntegerConstant(0,-1); ControlLabel("0"), null);
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
Quat quat3 = new Quat(cursor++,"=[]",t3, t2, expr);
if(assarr == true)
tmparrquat = quat3;
quats.add(quat3);
map.put(arrayAccess, t3);
```

##### Lab 8 模块输出

本次实验需要根据符号表进行寄存器分配，生成data段等。因此我修改了BIT-MiniCC框架，将中间代码生成阶段的全局符号表作为函数参数传入目标代码生成模块。在符号表中存储变量信息、函数信息、常量字符串等。常量字符串将在data段生成形如\_1sc : .asciiz "please input ten int number for bubble sort:$\backslash$n"的数据。

##### Lab 8 二元表达式

在Lab 7中的二元表达式支持的运算符较少，无法满足本次实验要求。因此我扩充了二元表达式支持的运算符，例如"+=""-=""*=""/-"等。对于"+="运算符，生成的四元式opnd1和res均为BinaryExpression.expr1，opnd2为BinaryExpression.expr2。"s+=i；"语句生成等价的"(+=,s,i,s)"。对于"++"运算符，将在目标代码生成的过程中进一步处理。

若二元表达式的expr1类型为ASTArrayAccess，且符号为"="，此时为数组元素赋值，使用"[]="符号生成对应的四元式。

##### Lab 8 函数调用

由于Lab 8 的测试用例中含有Mars\_PrintStr、Mars\_PrintInt、Mars\_GetInt函数，需要对函数调用语句进行处理，分为系统函数和自定义的函数两类。若函数为Mars\_PrintStr，将字符串设置为StringConstant类型，存入全局符号表中。对于其他函数，若参数为二元表达式，在目标代码生成时将二元表达式计算结果寄存器压入栈中，函数调用时再出栈。

##### Lab 8 break语句

break语句位于循环的stat结构中，出现break语句只能跳出本层循环。使用endloop\_count变量维护循环层数。在进入iterationStat.stat前endloop\_count加1，退出iterationStat.stat后减一。出现break语句时跳转至当前endloop\_count数值的end\-loop标识符处。这样可以保证只能跳出本层循环。

```
endloop_count++;
visit(iterationStat.stat);
endloop_count--;
```

#### Lab 8 生成data段

遍历全局符号表，查询类型为"StringConstant"的元素。这些字符串是后续Mars\_PrintStr函数的参数，为其在data段生成字符串数据。关键代码如下所示。

```
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
```

#### Lab 8 寄存器分配

由于修改中间代码生成模块消耗了较多的时间，本次实验我选择使用循环分配寄存器的方式进行。扫描全局符号表，为VariableDeclarator类型的变量分配寄存器。在MIPS中16-23为子程序寄存器，为变量分配这个区间的寄存器。MIPS中24-25为临时变量，子程序使用无需保存。

```
for(int i=1;i<global_table.size();i++){
 for(int j=1;j<global_table.get(i).size();j++){
  SymbolTable item= global_table.get(i).get(j);
  if(item.Kind.equals("VariableDeclarator")){
   addvalue(regcount,item.Name,global_table.get(i).get(0).Name);
   regcount = renew(regcount);
  }
 }
}
```

#### Lab 8 比较运算

对于符号为"<""<="的四元式，使用MIPS中的slt语句进行处理。首先判断四元式中的运算数类型，若为立即数，则生成li语句，将立即数加载到寄存器中；若为"\%"开头的临时变量，则从栈中弹出寄存器的符号；若为标识符，则查询标识符对应的寄存器。生成slt语句，格式为slt rd，rs，rt (rd ←（rs<rt）)。将结果寄存器压入栈中，便于后续的处理。若运算符为"<="，则需要将opnd2的立即数+1，转换为等价的"<"运算符。

关键代码如下所示。

```
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
```

#### Lab 8 相等比较

对于"=="运算符，操作方法和比较运算类似。检查运算符的类型，判断是立即数、临时变量还是标识符。若为寄存器则出栈，若为立即数则将立即数存入寄存器中，若为标识符则查询存储的寄存器。使用sub指令相减，根据结果是否为0判断数据是否相等。将结果入栈，方便后续判断。

```
String aim_reg = "$"+regcount;
//sub rd，rs，rt; rd ← rs-rt
this_code.add("\tsub "+aim_reg+", "+tmp_reg1+", "+tmp_reg2);
regstack.push(aim_reg);
regcount = renew(regcount);
```

#### Lab 8 条件跳转

使用bne指令进行条件跳转。栈顶元素出栈，判断栈顶是否为0，跳转到相应的标号。

```
else if(str_a[0].equals("Jt")){
 String tmp = "";
 tmp+= ("bne "+regstack.pop()+", $0, "+getSegflag(str_a[3]));
 this_code.add("\t"+tmp);
}
```

#### Lab 8 函数调用

判断函数是否为Mars\_PrintStr、Mars\_PrintInt、Mars\_GetInt等系统函数还是自定义的函数。Mars\_PrintStr函数需要打印字符串，在dataItemList中根据四元式中的字符串查询变量名，使用la指令加载字符串地址。将参数传入\$4寄存器中，调用jal指令进行跳转。对于Mars\_GetInt函数无需传参，对于Mars\_PrintInt函数，判断参数是立即数还是标识符，将其存入\$4寄存器，调用jal指令进行跳转。

对于自定义的函数，在全局符号表中查询函数参数个数，从堆栈中出栈，存入\$a0 - \$a3寄存器中，如果有更多的参数，或者有传值的结构，其将被保存在栈中。子程序结束后，返回值要保存在\$v0-\$v1中。

```
for(int j=0;j<global_table.get(0).size();j++){
 if(global_table.get(0).get(j).params.size()==1){
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
```

#### Lab 8 返回语句

对于返回语句，判断返回值是否为立即数还是标识符。若为寄存器使用li指令将其存入寄存器中，再使用move指令存入\$2寄存器中；否则将标识符所在寄存器存入\$2寄存器。

```
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
```

#### Lab 8 二元运算

对于"+""-""*""-""/""\%"等运算，目标代码生成方式类似，使用add、sub、mul、div、rem指令。先判断操作数是否为立即数，若为立即数使用li指令存入寄存器中；若为临时变量从堆栈中弹出寄存器符号；若为标识符则查询存储的位置。对于"-""/""\%"等具有先后顺序的运算，由于出栈顺序和指令中的顺序不一致，需要将除数与被除数出栈后逆序拼接。

```
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
```

#### Lab 8 自增自减运算

对于自增自减运算，使用addi \$i,\$i,1指令和subi \$i,\$i,1指令进行处理。

```
else if(str_a[0] .equals( "++")){
 regstack.push(get(str_a[1]));
 this_code.add("\taddi "+get(str_a[1])+", "+get(str_a[1])+", 1");
}
```

#### Lab 8 赋值运算

对于复合赋值运算符，如"-="，使用sub指令进行处理。对于赋值语句，若没有操作数，则从堆栈中出栈；若有操作数则判断是否为立即数、临时变量还是标识符。

```
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
```

#### Lab 8 数组访问

数组访问分为数组元素赋值和数组元素引用两类。对于数组元素引用"=[]"运算符，四元式为(=[],偏移量变量,数组符号,目标变量)；对于数组元素赋值"[]="运算符，四元式为([]=,变量,偏移量,数组符号)。判断操作数是否为立即数、临时变量还是标识符，操作方法和上面类似。

```
this_code.add("\tli $"+tmpregcount+", 4");
tmp_reg1 = tmpregcount;
tmpregcount = renew_tmp(tmpregcount);
this_code.add("\tmul $"+tmp_regoff+", $"+tmp_regoff+", $"+tmp_reg1);
this_code.add("\tsub $"+tmp_reg1+", $sp, $"+tmp_regoff);
//offset为数组偏移量
this_code.add("\tla $"+regcount+", "+str_a[2]);
this_code.add("\tadd $"+tmp_reg1+", $"+tmp_reg1+", $"+regcount);
regcount = renew(regcount);
this_code.add("\tlw $"+tmp_regoff   +", ($24)");
```
