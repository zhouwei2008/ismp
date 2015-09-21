package com.ecard.products.pay.data.analyse.details;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class RepeatVerifyResponsibilityChain extends
		AbsDetailResponsibilityChain {

	private Logger logger = Logger.getLogger(RepeatVerifyResponsibilityChain.class);
	
	public RepeatVerifyResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 1 || StringUtils.isEmpty(rs[0])){
			return Constants.Verify.DETAIL_NULLNUM_ERRMSG(count);
		}
		String num = rs[0];
		logger.info("Curr Num == " + num);
		// 匹配
		String rex = "^\\d{6}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(num);
		if(!m.find()){
			return Constants.Verify.DETAIL_FORMAT_ERRMSG(count);
		}
		if(getNumSet().contains(num)){
			return Constants.Verify.DETAIL_REPEAT_ERRMSG(count);
		}else{
			getNumSet().add(num);
		}
		return null;
	}

}
