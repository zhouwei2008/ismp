package com.ecard.products.pay.data.analyse.detailextends;

import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.StringUtils;

public class AccNamedResponsibilityChain extends AbsDetailResponsibilityChain {

	public AccNamedResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 用户名
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 7 || StringUtils.isEmpty(rs[6])){
			return Constants.Verify.DETAIL_NULLACCNAME_ERRMSG(count);
		}
        try{
            if(new String(rs[6].getBytes("GBK"),"iso-8859-1").length()>50){
                return Constants.Verify.DETAIL_ACCNAMEOVER_ERRMSG(count);
            }
        }catch (Exception ex){
            return  Constants.Verify.DETAIL_ACCNAMEOVER_ERRMSG(count);
        }
		return null;
	}

}
