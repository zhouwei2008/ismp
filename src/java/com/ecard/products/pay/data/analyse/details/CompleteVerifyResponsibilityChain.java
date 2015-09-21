package com.ecard.products.pay.data.analyse.details;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.constants.Constants;

public class CompleteVerifyResponsibilityChain extends AbsDetailResponsibilityChain {

	public CompleteVerifyResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}
	
	private int splits = 11;

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String rex = ",";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		int num = 0;
		while(m.find()){
			num ++;
		}
		if(splits != num){
			return Constants.Verify.DETAIL_COMPLETE_ERRMSG(count);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String r = "000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,省;市,协议号,用途";
		String rex = ",";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		int num = 0;
		while(m.find()){
			num ++;
		}
	}


}
