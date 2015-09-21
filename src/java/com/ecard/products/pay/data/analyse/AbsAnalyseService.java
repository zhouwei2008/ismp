package com.ecard.products.pay.data.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boss.BoAgentPayServiceParams;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.ObjectUtils;
import org.apache.log4j.Logger;
import com.ecard.products.constants.Constants;
import com.ecard.products.pay.web.servlet.env.SessionUserBean;
import com.ecard.products.utils.CollectionUtils;
import com.ecard.products.utils.StringUtils;
import com.ecard.products.utils.security.EasypayFunction;

/**
 * 解析校验文件，并将结果响应给用户
 * 1、校验正确，返回接收确认信息，将文件名*.temp修改为*
 * 2、校验错误，返回接收异常信息，并将具体错误信息返回给用户，将文件名*.temp修改为*.bug
 * @author tkZhu
 *
 */
public abstract class AbsAnalyseService implements AnalyseService<String, String>{
	
	private Logger logger = Logger.getLogger(AbsAnalyseService.class);
	
	protected ResponsibilityChain fileChain = null;
	protected ResponsibilityChain titleChain = null;
	protected AbsDetailResponsibilityChain detailChain = null;
	private SessionUserBean sub = null;

	public AtomicInteger atomic = new AtomicInteger(1);

	public AbsAnalyseService() {
		// TODO Auto-generated constructor stub
		buildFileResponsibilityChain();
		buildTitleResponsibilityChain();
		buildDetailResponsibilityChain();
	}
	
	public void setUserBean(SessionUserBean userBean){
		this.sub = userBean;
		this.fileChain.setUserBean(userBean);
	}

	protected abstract void buildFileResponsibilityChain();

	protected abstract void buildTitleResponsibilityChain();
	
	protected abstract void buildDetailResponsibilityChain();
	
	public String analyse(String filePath) {
		/*String fileName = new File(filePath).getName();
		String fnMsg = analyseFName(fileName);
		if(StringUtils.isNotEmpty(fnMsg)){
			return fnMsg;
		}*/

		// 存放总金额
		BigDecimal allAmount = new BigDecimal("0");
		// 文件标题
		String title = null;
		// 
		Scanner in = null;;
		try {
			in = new Scanner(new File(filePath), "GBK");
			boolean top = true;
			String line = null;
			while(in.hasNextLine()){
                line = in.nextLine();
                if(line == null || line.equals("")){
                    break;
                }
				if(top){
					title = line;
					top = false;
					continue;
				}
                int tempCount = atomic.getAndIncrement();
				String detailMsg = analyseDetail(line, tempCount);
				if(StringUtils.isNotEmpty(detailMsg)){
					return detailMsg;
				}
                // 加入单笔金额限定
                detailMsg = analyseDetailAmount(line, tempCount);
                if(StringUtils.isNotEmpty(detailMsg)){
                    return detailMsg;
                }
				allAmount = addAmount(allAmount, line);
			}
		} catch (IOException e) {
			logger.error("检测文件" + filePath + "时，出现异常:", e);
			return Constants.Verify.SYS_CHECK_ERRMSG;
		}finally{
			try {
				in.close();
			} catch (Exception e) {
				logger.error("IO关闭异常", e);
				return Constants.Verify.SYS_CHECK_ERRMSG;
			}
		}

          // 解析Title 包括：总笔数、总金额、业务编码、验证码
		logger.info(String.format("【总数：%d 总金额: %s】",(atomic.get()-1) ,allAmount.doubleValue()));
        String key = sub != null ? sub.getCmCustomer().getApiKey() : "";
		String titleMsg = analyseTitle(title, atomic.get()-1, allAmount.doubleValue(), key);
		if(StringUtils.isNotEmpty(titleMsg)){
			return titleMsg;
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
             if(currDayAmount.doubleValue() + allAmount.doubleValue() > dayLimitMoney.longValue()){
                 return Constants.Verify.DETAIL_DAYAMOUNTOVER_ERRMSG;
             }
        }

		return null;
	}
	
	/**
	 * 解析FN 
	 */
	private String analyseFName(String fName){
        String fn = fName.substring(fName.lastIndexOf(File.separator) + 1);
		return fileChain.process(fn);
	}
	
	/**
	 * 解析TOP 
	 */
	private String analyseTitle(String title, int count, double amount, String key){
		logger.info("开始解析总额了");
        if(count == 0){
            return Constants.Verify.TITLE_NOCOUNT_ERRMSG;
        }
		String [] titles = title.split(",");
		if(CollectionUtils.isEmpty(titles) || titles.length != 3){
			return Constants.Verify.TITLE_TOTALCOUNT_ERRMSG;
		}
		//
        try{
		    if(StringUtils.isEmpty(titles[0]) ||
				Integer.parseInt(titles[0]) != count){
                return Constants.Verify.TITLE_COUNT_ERRMSG;
		    }
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
		    return Constants.Verify.TITLE_COUNT_ERRMSG;
        }

		//
        try{
		    if(StringUtils.isEmpty(titles[1]) ||
				amount != Double.valueOf(titles[1]).doubleValue()){
			    return Constants.Verify.TITLE_AMOUNT_ERRMSG;
		    }
		 }catch (NumberFormatException nfe){
            nfe.printStackTrace();
		    return Constants.Verify.TITLE_AMOUNT_ERRMSG;
        }
		// 
		if(StringUtils.isEmpty(titles[2])){
			return Constants.Verify.TITLE_BIZTYPE_ERRMSG;
		}
        Pattern p = Pattern.compile("^\\d{5}$");
		Matcher m =p.matcher(titles[2]);
		if(!m.find()){
            return Constants.Verify.TITLE_BIZTYPEFORMAT_ERRMSG;
        }
		return null;
	}
	
	/**
	 * 解析DETAIL 
	 */
	private String analyseDetail(String detail, int count){
		return detailChain.process(detail, count);
	}

    private String analyseDetailAmount(String line, int count){
        double amount = new BigDecimal(line.split(",")[7]).doubleValue();
        Double limit = sub.getBoAgentPayServiceParams().getLimitMoney();
        if(limit!=null && amount > limit.doubleValue()){
            return Constants.Verify.DETAIL_AMOUNTOVER_ERRMSG(count);
        }
        return null;
    }

	// 结算金额
	private BigDecimal addAmount(BigDecimal allAmount , String line){
		return allAmount.add(new BigDecimal(line.split(",")[7]));
	}
}
