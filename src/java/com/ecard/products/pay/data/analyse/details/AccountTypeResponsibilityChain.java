package com.ecard.products.pay.data.analyse.details;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class AccountTypeResponsibilityChain extends AbsDetailResponsibilityChain {

	public AccountTypeResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 7 || StringUtils.isEmpty(rs[6])){
			return Constants.Verify.DETAIL_NULLACCTYPE_ERRMSG(count);
		}
		String type = rs[6];
		String rex = "^(0|1)$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(type);
		if(m.find()){
			return null;
		}
		return Constants.Verify.DETAIL_FORMATACCTYPE_ERRMSG(count);
	}

}
