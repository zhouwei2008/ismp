package com.ecard.products.pay.data.analyse.details;

import java.io.*;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class RemarkResponsibilityChain extends AbsDetailResponsibilityChain {

	public RemarkResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 10 && StringUtils.isEmpty(rs[9])){
			return Constants.Verify.DETAIL_NULLINFO_ERRMSG(count);
		}
		String remark = rs[9];
        if(remark.indexOf(";")==-1){
            return Constants.Verify.DETAIL_FORMATINFO_ERRMSG(count);
        }
		String anRemark = null;
	    try {
			anRemark = new String(remark.getBytes("GBK"), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(anRemark.length()>50){
			return Constants.Verify.DETAIL_OVERINFO_ERRMSG(count);
		}
		return null;
	}
	
}
