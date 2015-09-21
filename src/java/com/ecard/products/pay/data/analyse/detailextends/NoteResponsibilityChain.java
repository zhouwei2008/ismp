package com.ecard.products.pay.data.analyse.detailextends;

import java.io.*;

import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.StringUtils;

public class NoteResponsibilityChain extends AbsDetailResponsibilityChain {

	public NoteResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
        System.out.println("length is " + rs.length);
		if(rs.length == 15){
			return Constants.Verify.DETAIL_NULLINFO2_ERRMSG(count);
		}
		String remark = rs[15];
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
