package bit.minisys.minicc.ncgen;

//.data段
public class DataSegItem {
    public String data_name;
    public String data_value;
    DataSegItem(String name,String data){
        data_name = name;
        data_value = data;
    }
}
