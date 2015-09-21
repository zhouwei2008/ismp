package com.ecard.products.pay.data.analyse.details;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class BranchResponsibilityChain extends AbsDetailResponsibilityChain {

	public BranchResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,说明
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 4 || StringUtils.isEmpty(rs[3])){
			return Constants.Verify.DETAIL_NULLBRANCH_ERRMSG(count);
		}
       try{
            if(new String(rs[3].getBytes("GBK"),"iso-8859-1").length()>50){
                return Constants.Verify.DETAIL_BRANCHOVER_ERRMSG(count);
            }
        }catch (Exception ex){
            return  Constants.Verify.DETAIL_BRANCHOVER_ERRMSG(count);
       }
		return null;
	}

}
