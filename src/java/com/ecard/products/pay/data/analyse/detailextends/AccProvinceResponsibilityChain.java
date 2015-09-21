package com.ecard.products.pay.data.analyse.detailextends;

import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.StringUtils;

public class AccProvinceResponsibilityChain extends AbsDetailResponsibilityChain {

	public AccProvinceResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 用户名
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 9 || StringUtils.isEmpty(rs[8])){
			return Constants.Verify.DETAIL_NULLINFO_ERRMSG(count);
		}
        try{
            if(new String(rs[8].getBytes("GBK"),"iso-8859-1").length()>50){
                return Constants.Verify.DETAIL_OVERINFO_ERRMSG(count);
            }
        }catch (Exception ex){
            return  Constants.Verify.DETAIL_OVERINFO_ERRMSG(count);
        }
		return null;
	}

}
