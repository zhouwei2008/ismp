package com.ecard.products.pay.data.analyse.details;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;

public class AccountResponsibilityChain extends AbsDetailResponsibilityChain {

	public AccountResponsibilityChain(AbsDetailResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	// 000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,说明
	@Override
	public String processSelf(String r, int count) {
		// TODO Auto-generated method stub
		String [] rs = r.split(",");
		if(rs.length < 2 || StringUtils.isEmpty(rs[1])){
			return Constants.Verify.DETAIL_NULLACCOUNT_ERRMSG(count);
		}
		// 获取账号
		String account = rs[1];
		String rex = "^([0-9]|-){9,32}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(account);
		if(m.find()){
			return null;
		}
		return Constants.Verify.DETAIL_ACCOUNT_ERRMSG(count);
	}

	public static void main(String[] args) {
		String r = "000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,说明";
		StringTokenizer st = new StringTokenizer(r, ",");
		// 跳过序号
		st.nextToken();
		// 获取账号
		String account = st.nextToken();
		String rex = "^\\d{32}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(account);
		if(m.find()){
			System.out.println("ok");
		}else{
			System.out.println("fail");
		}
	}
}
