package packagedata;
import java.io.*;

import packagedata.interfaces.DataParser;

import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by IntelliJ IDEA.
 * User: test
 * Date: 12-2-15
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
public class XlsDataParser implements DataParser {

    public  String CombinedString(String filePath,int num ) throws Exception{
            List list = new ArrayList();
			Workbook rwb = null;
			Cell cell = null;
			InputStream stream = new FileInputStream(filePath);
			rwb = Workbook.getWorkbook(stream);
			Sheet sheet = rwb.getSheet(0);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < sheet.getRows(); i++) {
				if(sheet.getCell(0,i).getContents()==""){break;}
                if(i==0){continue;}
				String[] str = new String[num];

				for (int j = 0; j < num; j++) {
					cell = sheet.getCell(j, i);
					str[j] = cell.getContents();
                    String line=new String(str[j]);
					sb.append(line+",");

				}
					sb.append("|");
			}
        stream.close();
        return sb.toString().replace(",|", "|");
    }
}

