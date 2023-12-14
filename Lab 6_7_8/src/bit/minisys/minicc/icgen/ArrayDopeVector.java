package bit.minisys.minicc.icgen;

import java.util.ArrayList;

public class ArrayDopeVector {
    public String name;             //数组名称
    public String type;             //数组类型
    public Integer dim;             //数组维数
    public ArrayList<ArrayRange> bound = new ArrayList<>(); //数组每一维的上下限组成的链表
    public String addr;             //数组首地址
}
