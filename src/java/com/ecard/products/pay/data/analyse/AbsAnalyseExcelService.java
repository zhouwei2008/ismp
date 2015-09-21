package com.ecard.products.pay.data.analyse;

import boss.BoAgentPayServiceParams;
import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.pay.web.servlet.env.SessionUserBean;
import com.ecard.products.utils.ObjectUtils;
import com.ecard.products.utils.StringUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: tkZhu
 * Date: 11-8-15
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbsAnalyseExcelService implements AnalyseService<String, String>{

    private Logger log = Logger.getLogger(AbsAnalyseExcelService.class);
    protected ResponsibilityChain fileChain = null;
    protected AbsDetailResponsibilityChain detailChain = null;

    private SessionUserBean sub = null;

    public AbsAnalyseExcelService(){
        buildFileResponsibilityChain();
		buildDetailResponsibilityChain();
    }

    public void setUserBean(SessionUserBean userBean){
		this.sub = userBean;
		this.fileChain.setUserBean(userBean);
	}

    protected abstract void buildFileResponsibilityChain();
    protected abstract void buildDetailResponsibilityChain();

    public String analyse(String filePath) {

       /* String fileName = new File(filePath).getName();
        System.out.println("File Path == " + filePath + " File Name == " + fileName);
		String fnMsg = analyseFName(fileName);
		if(StringUtils.isNotEmpty(fnMsg)){
			return fnMsg;
		}*/

        Workbook wb = null;
        AtomicInteger atomic = new AtomicInteger(0);
		DecimalFormat df = new DecimalFormat("#.##");
		double totalAmount = 0.00;
		try {
            log.info("文件名字 ： " + filePath);
			wb = Workbook.getWorkbook(new FileInputStream(filePath));
			int size = 16;
			//通过Workbook的getSheets方法获取工作簿集（从0开始）
			Sheet rs = wb.getSheet(0);

            log.info("ROWS is " + rs.getRows());
			par:for (int j = 0; j < rs.getRows(); j++) {
				// 跳过第一行
				if(j == 0){
					continue par;
				}
                Cell[] cs = rs.getRow(j);
                if(null == cs[0].getContents() || "".equals(cs[0].getContents())){
                        log.info("解析该批次文件结束了!");
                        break par;
                }
                StringBuffer line = new StringBuffer();
				// Item
				//atomic.getAndIncrement();
				for(int z = 0; z < size && z < cs.length; z++){
					Cell c = cs[z];
					line.append(c.getContents());
					line.append(",");
					/*if(z == 14){
						totalAmount = new BigDecimal(Double.toString(totalAmount)).add(new BigDecimal(c.getContents())).doubleValue();
					}*/
				}
				String rlt = line.length()==0 ? "0" : line.substring(0, line.length()-1);
				log.info("item " + j + " line is " + rlt);

                int tempCount = atomic.addAndGet(1);
                String detailMsg = analyseDetail(rlt, tempCount);
				if(StringUtils.isNotEmpty(detailMsg)){
					return detailMsg;
				}
                // 加入单笔金额限定
                totalAmount = new BigDecimal(Double.toString(totalAmount)).add(new BigDecimal(cs[14].getContents())).doubleValue();
                detailMsg = analyseDetailAmount(totalAmount, tempCount);
                if(StringUtils.isNotEmpty(detailMsg)){
                    return detailMsg;
                }
			}
        }catch (Exception e) {
            e.printStackTrace();
			log.error("检测文件" + filePath + "时，出现异常:", e);
			return Constants.Verify.SYS_CHECK_ERRMSG;
		}finally{
			try {
				//最后不要忘记关闭wb以释放资源：
			    wb.close();
			} catch (Exception e) {
				log.error("IO关闭异常", e);
				return Constants.Verify.SYS_CHECK_ERRMSG;
			}
		}

         // 当日限制
        Long currDayCount =  sub != null ? (sub.getDayCount()!=null?sub.getDayCount():Long.valueOf("0")) : Long.valueOf("0");
        Double currDayAmount = sub != null ? (sub.getDayAmount()!=null?sub.getDayAmount():Double.valueOf("0")) : Double.valueOf("0");
        BoAgentPayServiceParams boPayServiceParams = sub.getBoAgentPayServiceParams();
        Long dayLimitCount = boPayServiceParams.getDayLimitTrans();
        Double dayLimitMoney = boPayServiceParams.getDayLimitMoney();
        if(ObjectUtils.isNotEmpty(dayLimitCount) && dayLimitCount.longValue() != 0){
             if(currDayCount.longValue() + Long.valueOf(atomic.get()-1).longValue() > dayLimitCount.longValue()){
                 return Constants.Verify.DETAIL_DAYCOUNTOVER_ERRMSG;
             }
        }
        if(ObjectUtils.isNotEmpty(dayLimitMoney) && dayLimitMoney.longValue() != 0){
             if(currDayAmount.doubleValue() + totalAmount > dayLimitMoney.longValue()){
                 return Constants.Verify.DETAIL_DAYAMOUNTOVER_ERRMSG;
             }
        }
		return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private String analyseDetailAmount(double amount, int count){
        Double limit = sub.getBoAgentPayServiceParams().getLimitMoney();
        if(limit!=null && amount > limit.doubleValue()){
            return Constants.Verify.DETAIL_AMOUNTOVER_ERRMSG(count);
        }
        return null;
    }

    private String analyseFName(String fn){
        return fileChain.process(fn);
    }

    /**
	 * 解析DETAIL
	 */
	private String analyseDetail(String detail, int count){
		return detailChain.process(detail, count);
	}
}
