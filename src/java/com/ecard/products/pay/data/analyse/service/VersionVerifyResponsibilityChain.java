package com.ecard.products.pay.data.analyse.service;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import boss.BoAgentPayServiceParams;
import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;
import com.ecard.products.utils.StringUtils;


public class VersionVerifyResponsibilityChain extends ResponsibilityChain {

	public VersionVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Demo:100000000001_S0020110601_00001.txt
	 */
	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		String rex = "(S|F){1}\\d{2}";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		if(m.find()){
			String version = m.group().replaceFirst("(S|F)", "");
			BoAgentPayServiceParams payServices = getUserBean().getBoAgentPayServiceParams();
            System.out.println("A ======================== B ");

            if(StringUtils.isNotEmpty(payServices.getBatchVersion())
                    &&payServices.getBatchVersion().equals(version)){
                return null;
            }
		}
		return Constants.Verify.FNAME_VERSION_ERRMSG;
	}
	
	public static void main(String[] args) {
		String r = "100000000001_S0020110601_00001.txt";
		String rex = "(S|F){1}\\d{2}";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		if(m.find()){
			String version = m.group().replaceFirst("(S|F)", "");
			
//			System.out.println("aa" + m.group().replaceFirst("(S|F)", ""));
		}
	}

}
