package packagedata;
import packagedata.interfaces.DataParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: test
 * Date: 12-2-15
 * Time: 下午7:26
 * To change this template use File | Settings | File Templates.
 */
public class CombinedData {

    public static String combine(String filePath,int templatetype,String Filetype  ){
        DataParser dataParser=null;  //数据解析器父类
        int num=0;  //列数
           try {
            if(Filetype.equals("XLS")){
               dataParser=new XlsDataParser();//xls解析
            }
            else if(Filetype.equals("TXT")){
               dataParser=new TxtDataParser();//txt解析
            }
            else{
               return  null;
            }
			switch(templatetype){
				case 0:  //标准模板
                    num=17;//列数
				case 1:  //华安模板
                    num=18;//列数
				default:
                    num=17;//列数
			}
            String  str=dataParser.CombinedString(filePath,num);
            System.out.println("数据为"+str);
            return  str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

    }
}
