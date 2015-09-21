package com.ecard.products.pay.data.analyse.detailextends;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.constants.Constants;
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain;
import com.ecard.products.utils.StringUtils;

public class AccResponsibilityChain extends AbsDetailResponsibilityChain {

	public AccResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 保单获取成本,广东分公司,管理费用-保单获取成本,2011-07-06,44-060001040001385,871362632408091001,茂名康顺车辆信息咨询部,中国银行茂名分行光华北支行,广东省,茂名市,,,,线下,21.8,手续费茂名6
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 6 || StringUtils.isEmpty(rs[5])){
			return Constants.Verify.DETAIL_NULLACCOUNT_ERRMSG(count);
		}
		// 获取账号
//        if(rs[5].length()>=16 && rs[5].length() <=33){
//            return null;
//        }
         if(rs[5].length()>=9 && rs[5].length() <=32){
            return null;
        }
		return Constants.Verify.DETAIL_ACCOUNTLEN_ERRMSG(count); //
	}
}
