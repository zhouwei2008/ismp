package com.ecard.products.pay.data.analyse.service;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boss.BoAgentPayServiceParams;
import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;
import com.ecard.products.utils.CollectionUtils;
import com.ecard.products.utils.ObjectUtils;

public class CollectServiceVerifyResponsibilityChain extends
        ResponsibilityChain {

	public CollectServiceVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		String rex = "S";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r);
		if(m.find()){
			String service = Constants.ServiceAlias.sf.get(m.group());
			BoAgentPayServiceParams bizOrgServices = getUserBean().getBoAgentPayServiceParams();
			if(ObjectUtils.isNotEmpty(bizOrgServices)){
                if(bizOrgServices.getServiceCode().equals(service)){
                    return null;
                }
			}
		}
		return Constants.Verify.FNAME_COLLECTSERVICE_ERRMSG;
	}

}
