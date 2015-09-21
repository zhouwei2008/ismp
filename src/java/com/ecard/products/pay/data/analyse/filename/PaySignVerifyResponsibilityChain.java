package com.ecard.products.pay.data.analyse.filename;

import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;

/**
 * 校验代付标记
 * 
 * @author tkZhu
 *
 */
public class PaySignVerifyResponsibilityChain extends ResponsibilityChain {

	public PaySignVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		if(r.indexOf("F")==-1){
			return Constants.Verify.FNAME_PAYSIGN_ERRMSG;
		}
		return null;
	}

}
