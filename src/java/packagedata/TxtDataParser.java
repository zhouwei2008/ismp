package packagedata;

import packagedata.interfaces.DataParser;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: test
 * Date: 12-2-15
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
public class TxtDataParser implements DataParser {
    public static String strSpace(String magcenter,int num){
         String str[]=null;
            if(str.length==num){       //相同证明数据正确
                 return magcenter;
            }
            else if(str.length<num){  //需要补空
                 int number=num-str.length;
                 for(int i=0;i<number;i++){
                     magcenter=magcenter+",";
                 }
                 return magcenter;
            }
            else {          //截断数据
                 magcenter=str[0];
                 for(int i=1;i<str.length;i++){
                     magcenter=magcenter+",";
                     magcenter=magcenter+str[i];
                 }
                return magcenter;
            }
    }
    /**
	 * 普通TXT格式
	 * @param
	 * @return
	 */
    public  String CombinedString(String filePath,int num ) throws Exception{
        StringBuffer sb=new StringBuffer();
	    BufferedReader reader = null;
        boolean top = true;
		String line = null;
        String title = null;
        InputStream stream =null;
	        try {
            stream=new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(stream,"GBK"));
			while((line = reader.readLine())!=null){
				if(top){
					title = line;
					top = false;
					continue;
				}
                if(line == null || line.equals("")){
                    break;
                }
                System.out.println("line为"+line);
                sb.append(line+"|");

	        }
	            reader.close();
                System.out.println("数据为"+ sb.toString());
                sb.append(" ");
                //return new String(sb.toString().getBytes("GBK"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
                        stream.close();
	                } catch (IOException e1) {
	                }
	            }
                return sb.toString();
	        }

    }
}
