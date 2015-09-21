package com.ecard.products.pay.data.analyse.details;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class SubBranchResponsibilityChain extends AbsDetailResponsibilityChain {

	public SubBranchResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 5 || StringUtils.isEmpty(rs[4])){
			return Constants.Verify.DETAIL_NULLSUBBRANCH_ERRMSG(count);
		}
         try{
            if(new String(rs[4].getBytes("GBK"),"iso-8859-1").length()>50){
                return Constants.Verify.DETAIL_SUBBRANCHOVER_ERRMSG(count);
            }
        }catch (Exception ex){
            return  Constants.Verify.DETAIL_SUBBRANCHOVER_ERRMSG(count);
        }
		return null;
	}

}
