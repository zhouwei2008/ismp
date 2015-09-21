package com.ecard.products.pay.data.analyse.details;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class AmountResponsibilityChain extends AbsDetailResponsibilityChain {

	public AmountResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,说明
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 8 || StringUtils.isEmpty(rs[7])){
			return Constants.Verify.DETAIL_NULLAMOUNT_ERRMSG(count);
		}
		String amount = rs[7];
		String rex = "^\\d{1,8}(\\.\\d{1,3}){0,1}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(amount);
		if(m.find()){
            if(Double.valueOf(amount).doubleValue() > 0){
			    return null;
            }
		}
		return Constants.Verify.DETAIL_AMOUNT_ERRMSG(count);
	}

}
