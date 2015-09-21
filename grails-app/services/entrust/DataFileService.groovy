package entrust
import com.ecard.products.constants.Constants;
import org.springframework.web.multipart.MultipartFile;
import jxl.Workbook;
import org.apache.log4j.Logger
import jxl.Sheet;

class DataFileService {

    static transactional = true
    private Logger logger = Logger.getLogger(DataFileService.class);
    def RESP_RESULT_SUCC = 'true' //成功
    def RESP_RESULT_FAIL = 'false' //失败
    def  processData(String fname, MultipartFile mfile, String customer, String srcType){
       //保存XLS文件
       final String mname=fname;//文件名
       String dirPath = Constants.Config.DATA_ROOT + File.separator + srcType + File.separator + customer; //保存全路径
         if(!new File(dirPath).exists()){
            new File(dirPath).mkdirs(); //创建文件路径
       }
       if(fname.indexOf(".")==-1 || !Constants.AllowFType.allowTypes().contains(fname.substring(fname.lastIndexOf(".")+1))){
             return respXml(RESP_RESULT_FAIL,"上传文件类型错误,请重新上传!","");
       }
       if(mfile.getOriginalFilename().indexOf(",")!= -1 || mfile.getOriginalFilename().indexOf("-")!= -1 || fname.substring(0,fname.lastIndexOf(".")).indexOf(".")!=-1){

             return respXml(RESP_RESULT_FAIL,"上传文件名格式不正确，包含',- .'等非法字符。","");
       }
       //加文件效验,是否为 TXT,XLS文件
       //判断相同文件名文件是否已存在
       String [] ss = new File(dirPath).list(new FilenameFilter() {

              public boolean accept(File dir, String name) {

                  if(name.indexOf(".")!=-1 && name.substring(0, name.indexOf(".")).equalsIgnoreCase(mname.substring(0, mname.indexOf(".")))){
                       return true;
                  }
                  else{
                      return false;
                  }
              }
        });

        if(ss.length>0){
            return respXml(RESP_RESULT_FAIL,Constants.Verify.FNAME_EXIST_ERRMSG,"");
        }
        String tarFile =fname.substring(0,fname.indexOf(".")+1)+fname.substring(fname.indexOf(".")+1).toUpperCase();
		String filePath = dirPath + File.separator + tarFile;
        File file = new File(filePath);  //建立新文件
        try{
            mfile.transferTo(file);     //保存文件
        }catch (Exception e){
            logger.error("保存文件" + filePath + "时出现异常。");
            return respXml(RESP_RESULT_FAIL,Constants.Verify.NONAME_CHECK_ERRMSG,"");
        }
        System.out.println("XLS ======================================== " + filePath);
        return respXml(RESP_RESULT_SUCC,"",filePath);
    }
     /**
     * 响应
     * @param result 响应结果 成功/失败
     * @param errMsg 原因
     * @return
     */
    def respXml(String res, String msg,String path){
        String[] r=new String[3];
           r[0]=res;
           r[1]=msg;
           r[2]=path;
        return r;
    }

    def deletefile(String batchFilename,String dirPathnew){

        final String fn = batchFilename
        File dird = new File(dirPathnew);
        File[] fs = dird.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.startsWith(fn + ".");
            }
        });
        try{
            if(fs){
                fs[0].delete()
            }
        }
        catch(Exception e) {
           e.printStackTrace()
        }
    }

   def readFileByLines(String filePath,String fileName) {
       BufferedReader reader = null;
       int line = 0;
       InputStream stream = new FileInputStream(filePath);
       try {
            if(fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase().equals("XLS")){
                   Workbook rwb = null;
                   rwb = Workbook.getWorkbook(stream);
                   Sheet sheet = rwb.getSheet(0);
                   for (int i = 0; i < sheet.getRows(); i++) {
                        if(sheet.getCell(0,i).getContents()==""){break;}
                        line++;
                   }
                   stream.close();
            }
            else{
                File file = new File(filePath);

                reader = new BufferedReader(new FileReader(file));
                // 一次读入一行，直到读入null为文件结束
                while ( reader.readLine() != null) {
                    // 显示行号
                   // System.out.println("line " + line + ": " + tempString);
                    line++;
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
           if(stream!=null){
               try {
                    stream.close();
                } catch (IOException e1) {
                }
           }

            return line;
        }

    }


}
