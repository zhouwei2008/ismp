package com.ecard.products.pay.data.analyse;

import java.io.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import com.ecard.products.constants.Constants;

public class Data2FileService {

	private Logger logger = Logger.getLogger(Data2FileService.class);



    public String processXlsData(String fname, InputStream in, String customer, String srcType){
		 // 生成文件路径
        System.out.println("proc xls is " + fname);
       // String fDate = DateUtils.getDefaultDateBySDF8();
        //String fDate = fname.split("_")[1].substring(3);
		String dirPath = Constants.Config.DATA_ROOT + File.separator + srcType + File.separator + customer;
        if(!new File(dirPath).exists()){
            new File(dirPath).mkdirs();
        }
		//FileUtils.makeNewDir(dirPath);
        String tarFile = fname.replaceFirst(".XLS|.xls", ".XLS");
		String filePath = dirPath + File.separator + tarFile;
        if(new File(filePath + Constants.FILEState.SUFFIX_DONE).exists()){
            return "exist";
        }
		//logger.info("target file is " + tarFile);
		Workbook wb = null;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"GBK"));
			wb = Workbook.getWorkbook(in);
			//通过Workbook的getSheets方法获取工作簿集（从0开始）
			Sheet rs = wb.getSheet(0);
			for (int j = 0; j < rs.getRows(); j++) {
				Cell[] cs = rs.getRow(j);
				if(cs.length == 0){
					break;
				}
				if(j == 0){
					// top
					String t1 = cs[0].getContents();
					String t2 = cs[1].getContents();
					String t3 = cs[2].getContents();
					StringBuffer buffer = new StringBuffer();
					buffer.append(t1.trim())
						.append(",")
						.append(t2.trim())
						.append(",")
						.append(t3.trim());
					String top = buffer.toString();
					logger.info("top = " + top);
					bw.write(top);
					bw.flush();
					bw.newLine();
					continue;
				}
                if(cs[0]==null){
                    break;
                }
				String l1 = cs[0].getContents();
                if(l1==null || l1.trim().equals("")){
					break;
				}
				String l2 = cs[1].getContents();
				String l3 = cs[2].getContents();
				String l4 = cs[3].getContents();
				String l5 = cs[4].getContents();
				String l6 = cs[5].getContents();
				String l7 = cs[6].getContents();
				String l8 = cs[7].getContents();
				String l9 = cs[8].getContents();
				String l10 = cs[9].getContents();
				String l11 = cs[10].getContents();
				String l12 = cs[11].getContents();

				StringBuffer buffer = new StringBuffer();
				buffer.append(l1);buffer.append(",");
				buffer.append(l2);buffer.append(",");
				buffer.append(l3);buffer.append(",");
				buffer.append(l4);buffer.append(",");
				buffer.append(l5);buffer.append(",");
				buffer.append(l6);buffer.append(",");
				buffer.append(l7);buffer.append(",");
				buffer.append(l8);buffer.append(",");
				buffer.append(l9);buffer.append(",");
				buffer.append(l10);buffer.append(",");
				buffer.append(l11);buffer.append(",");
				buffer.append(l12);
				String item = buffer.toString();
				logger.info(item);
				bw.write(item);
				if(j != rs.getRows()-1){
					bw.newLine();
				}
				bw.flush();
			}
			//最后关闭bw以释放资源
			bw.close();
			//最后不要忘记关闭wb以释放资源：
			wb.close();
		} catch (Exception e) {
			logger.error("转换文件" + filePath + "时出现异常。");
			//改名字
			new File(tarFile).renameTo(new File(tarFile + Constants.FILEState.SUFFIX_EXCEPTION));
            return new File(tarFile + Constants.FILEState.SUFFIX_EXCEPTION).getAbsolutePath();
			//改名字
		}

        System.out.println("XLS ======================================== " + filePath);
        return filePath;
    }

	public String processData(String fname, InputStream in,String customer, String srcType){

        // 生成文件路径
//        String fDate = DateUtils.getDefaultDateBySDF8();
        //String fDate = fname.split("_")[1].substring(3);
		String dirPath = Constants.Config.DATA_ROOT + File.separator + srcType + File.separator + customer;
        if(!new File(dirPath).exists()){
            new File(dirPath).mkdirs();
        }
		//FileUtils.makeNewDir(dirPath);
		String filePath = dirPath + File.separator + fname.replace(".txt", ".TXT");
        if(new File(filePath + Constants.FILEState.SUFFIX_DONE).exists()){
            return "exist";
        }
        System.out.println("======================================== " + filePath);
		try {
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(filePath));
			BufferedInputStream bin = new BufferedInputStream(in);
			byte [] bs = new byte[1024];
			int len = 0;
			while((len = bin.read(bs,0,1024))!=-1){
				bout.write(bs, 0, len);
				bout.flush();
			}
			bin.close();
			bout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("写入文件" + filePath + "时出现异常。");
			//改名字
			new File(filePath).renameTo(new File(filePath + Constants.FILEState.SUFFIX_EXCEPTION));
            return new File(filePath + Constants.FILEState.SUFFIX_EXCEPTION).getAbsolutePath();
		}
		return filePath;
	}
/*
	private String processXLS(String filePath){
        // 解析Excel xls xls
        //1选取Excel文件，2选择工作簿，3选择Cell，4读取信息。
        // 那么现在就可以看看JXL中这四步骤如何体现：
        //通过Workbook的静态方法getWorkbook选取Excel文件
        logger.info("xls file is" + filePath);
        String tarFile = filePath.replaceFirst(".XLS", ".TXT");
        logger.info("target file is " + tarFile);
        Workbook wb = null;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tarFile),"GBK"));
            wb = Workbook.getWorkbook(new FileInputStream(filePath));
            int size = 12;
            //通过Workbook的getSheets方法获取工作簿集（从0开始）
            Sheet rs = wb.getSheet(0);
            for (int j = 0; j < rs.getRows(); j++) {
                Cell[] cs = rs.getRow(j);
                StringBuffer line = new StringBuffer();
                for(int z = 0; z < size && z < cs.length; z++){
                    Cell c = cs[z];
                    if(j == 0 && (null == c.getContents()||""==c.getContents())){
                        break;
                    }
                    line.append(c.getContents());
                    line.append(",");
                    //log.info c.getContents()
                }
                *//*for (Cell cell : cs) {
                    line.append(cell.getContents())
                    line.append(",")
                    if(j == 0 && (null == cell.getContents())){
                        break
                    }
                }*//*
                String rlt = line.substring(0, line.length()-1);
                logger.info("item line is " + rlt);
                bw.write(rlt);
                if(j != rs.getRows()-1){
                    bw.newLine();
                }
                bw.flush();
            }
            //最后关闭bw以释放资源
            bw.close();
            //最后不要忘记关闭wb以释放资源：
            wb.close();
        } catch (Exception e) {
            logger.error("转换文件" + filePath + "到" + tarFile + "时出现异常。");
			//改名字
			new File(tarFile).renameTo(new File(tarFile + Constants.FILEState.SUFFIX_EXCEPTION));
            return new File(tarFile + Constants.FILEState.SUFFIX_EXCEPTION).getAbsolutePath();
        }

        *//*def br = new BufferedReader(new FileReader(tarFile))
        def line = null;
        def ai = new AtomicInteger(0)
        while((line = br.readLine()) != null){
            log ai.incrementAndGet()
            log line
        }*//*
        return tarFile;
    }*/
	
	
	
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		new Data2FileService().analyFile();
	}
	
	public String analyFile(){
		String fname = "D:\\root\\20110601\\100000000001_s0020110601_00001.txt.temp";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line = null;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
