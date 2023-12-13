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
  - [Lab 5 语法分析实验作业](#lab-5-语法分析实验作业)
    - [Lab 5 实验内容](#lab-5-实验内容)
    - [Lab 5 实验过程及步骤](#lab-5-实验过程及步骤)
      - [Lab 5 扩充C语言文法子集](#lab-5-扩充c语言文法子集)
      - [Lab 5 语法分析](#lab-5-语法分析)
      - [Lab 5 扩充语句块](#lab-5-扩充语句块)
      - [Lab 5 函数调用](#lab-5-函数调用)
      - [Lab 5 if-else语句](#lab-5-if-else语句)
      - [Lab 5 for循环语句](#lab-5-for循环语句)
      - [Lab 5 声明语句](#lab-5-声明语句)

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

## Lab 5 语法分析实验作业

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
