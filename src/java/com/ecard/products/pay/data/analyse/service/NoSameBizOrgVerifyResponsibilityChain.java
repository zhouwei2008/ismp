package com.ecard.products.pay.data.analyse.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;
import ismp.CmCustomer;


public class NoSameBizOrgVerifyResponsibilityChain extends ResponsibilityChain {

	public NoSameBizOrgVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		//获取商户号
		String rex = "^\\d{15}";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		if(m.find()){
			CmCustomer bizOrg = getUserBean().getCmCustomer();
			return bizOrg.getCustomerNo().equals(m.group())?null:Constants.Verify.FNAME_NOEQUALSBIZORG_ERRMSG;
		}
		return null;
	}
	

}
