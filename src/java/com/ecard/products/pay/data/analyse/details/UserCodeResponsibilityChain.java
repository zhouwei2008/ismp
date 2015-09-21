package com.ecard.products.pay.data.analyse.details;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class UserCodeResponsibilityChain extends AbsDetailResponsibilityChain {

	public UserCodeResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 11 && StringUtils.isEmpty(rs[10])){
			return Constants.Verify.DETAIL_NULLUSERCODE_ERRMSG(count);
		}
		// 获取协议号
		String usercode = rs[10];
		String rex = "^\\d{15}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(usercode);
		if(m.find()){
			return null;
		}
		return Constants.Verify.DETAIL_FORMATUSERCODE_ERRMSG(count);
	}
	
}
