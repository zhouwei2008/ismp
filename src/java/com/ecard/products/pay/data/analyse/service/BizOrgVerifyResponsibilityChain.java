package com.ecard.products.pay.data.analyse.service;

import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;


import com.ecard.products.utils.ObjectUtils;
import ismp.CmCustomer;

/**
 * 是否获取到商户
 * 
 * @author tkZhu
 *
 */
public class BizOrgVerifyResponsibilityChain extends ResponsibilityChain {

	public BizOrgVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		CmCustomer cc = getUserBean().getCmCustomer();
		if(ObjectUtils.isEmpty(cc)){
			return Constants.Verify.FNAME_NOBIZORG_ERRMSG;
		}
		return null;
	}
}
