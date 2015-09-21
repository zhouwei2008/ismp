package com.ecard.products.pay.data.analyse.detailextends;

import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.StringUtils;

public class AccCityResponsibilityChain extends AbsDetailResponsibilityChain {

	public AccCityResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 用户名
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 10 || StringUtils.isEmpty(rs[9])){
			return Constants.Verify.DETAIL_NULLINFO_ERRMSG(count);
		}
        try{
            if(new String(rs[9].getBytes("GBK"),"iso-8859-1").length()>50){
                return Constants.Verify.DETAIL_OVERINFO_ERRMSG(count);
            }
        }catch (Exception ex){
            return  Constants.Verify.DETAIL_OVERINFO_ERRMSG(count);
        }
		return null;
	}

}
