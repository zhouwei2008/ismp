package packagedata.interfaces;
import java.io.File;
/**
 * Created by IntelliJ IDEA.
 * User: wangzhao
 * Date: 12-2-15
 * Time: 下午7:14
 * 数据分析器,用来将文件解析,得到需要的字符串.
 */
public interface DataParser {
    //得到文件,得到文件类型
    public String CombinedString(String filePath,int templatetype ) throws Exception;


}
