package com.ecard.products.pay.data.analyse.details;

import java.io.*;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class Remark2ResponsibilityChain extends AbsDetailResponsibilityChain {

	public Remark2ResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
        try{
		    if(rs.length < 12 && StringUtils.isEmpty(rs[11])){
			    return Constants.Verify.DETAIL_NULLINFO2_ERRMSG(count);
		    }
        }catch(Exception ex){
            ex.printStackTrace();
            return Constants.Verify.DETAIL_NULLINFO2_ERRMSG(count);
         }
		String remark = rs[11];
		String anRemark = null;
	    try {
			anRemark = new String(remark.getBytes("GBK"), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(anRemark.length()>30){
			return Constants.Verify.DETAIL_OVERINFO2_ERRMSG(count);
		}
		return null;
	}
	
}
