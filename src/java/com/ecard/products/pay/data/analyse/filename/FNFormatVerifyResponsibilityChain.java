package com.ecard.products.pay.data.analyse.filename;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;
/**
 * 校验文件名格式
 * 
 * @author tkZhu
 *
 */
public class FNFormatVerifyResponsibilityChain extends ResponsibilityChain {

	public FNFormatVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Demo:100000000001_S0020110601_00001.txt
	 */
	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		String rex = "^\\d{15}_(S|F){1}\\d{10}_\\d{5}.(TXT|XML|XLS)$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		if(m.find()){
			return null;
		}
		return Constants.Verify.FNAME_FORMAT_ERRMSG;
	}
	
//	public static void main(String[] args) {
//		String r = "100000000001_S0020110601_00001.txt";
//		String rex = "^\\d{12}_(S|F){1}\\d{10}_\\d{5}.(txt|xml|xsl)$";
//		Pattern p = Pattern.compile(rex);
//		Matcher m = p.matcher(r);
//		if(m.find()){
//			System.out.println("aa");
//		}
//	}

}
