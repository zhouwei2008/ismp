package com.ecard.products.pay.data.analyse.detailextends;

import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.StringUtils;

public class BankResponsibilityChain extends AbsDetailResponsibilityChain {

	public BankResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,说明
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 8 || StringUtils.isEmpty(rs[7])){
			return Constants.Verify.DETAIL_NULLBANKNAME_ERRMSG(count);
		}
        String bank = "银行";
	    String branch = "分行";
		String subBranch = "支行";
        try{
            String s = rs[7];
            System.out.println("bank is " + s);
//            if(s.indexOf(bank)== -1){  //2011-11-07 华安保险去掉银行限制,保证信用社或都不带银行的储汇局为合法
//		    	return Constants.Verify.DETAIL_NULLBANKNAME_ERRMSG(count);
//		    }
//		    if(s.indexOf(branch)== -1){
//		    	return Constants.Verify.DETAIL_NULLBRANCH_ERRMSG(count);
//		    }
//		    if(s.indexOf(subBranch)== -1){
//		    	return Constants.Verify.DETAIL_NULLSUBBRANCH_ERRMSG(count);
//		    }
            if(new String(s.getBytes("GBK"),"iso-8859-1").length()>100){
                return Constants.Verify.DETAIL_BANKNAMEOVER_ERRMSG(count);
            }
        }catch (Exception ex){
            return  Constants.Verify.DETAIL_BANKNAMEOVER_ERRMSG(count);
        }
		return null;
	}

}
