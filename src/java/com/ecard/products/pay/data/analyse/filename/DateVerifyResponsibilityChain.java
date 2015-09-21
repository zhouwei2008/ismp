package com.ecard.products.pay.data.analyse.filename;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;
import com.ecard.products.utils.DateUtils;

/**
 * 校验版本号
 * 
 * @author tkZhu
 *
 */
public class DateVerifyResponsibilityChain extends ResponsibilityChain {

	public DateVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
//		String r = "100000000001_S0020110601_00001.txt";
		String rex = "\\d{8}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r.split("_")[1]);
		if(m.find()){
//			String subDate = m.group();
//			String currDate = DateUtils.getDefaultDateBySDF8();
//			if(currDate.equals(subDate)){
				return null;
//			}
		}
		
		return Constants.Verify.FNAME_DATE_ERRMSG;
	}
	
/*	public static void main(String[] args) {
		String r = "100000000001_S0020110603_00001.txt";
		String rex = "\\d{8}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r.split("_")[1]);
		if(m.find()){
			String subDate = m.group();
			String currDate = DateUtils.getDefaultDateBySDF8();
			System.out.println(subDate + " aaa " + currDate);
			if(currDate.equals(subDate)){
				System.out.println("ll");
			}
		}
	}*/
	
}
