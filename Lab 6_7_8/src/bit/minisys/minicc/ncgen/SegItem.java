package bit.minisys.minicc.ncgen;

public class SegItem {
    public String Segname;
    public String linecount;
    SegItem(String name,String count){
        //标号
        Segname = name ;
        //四元式行数
        linecount = count;
    }
}
