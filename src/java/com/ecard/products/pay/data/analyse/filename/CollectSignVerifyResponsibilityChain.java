package com.ecard.products.pay.data.analyse.filename;

import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;

/**
 * 校验待收标记
 * 
 * @author tkZhu
 *
 */
public class CollectSignVerifyResponsibilityChain extends ResponsibilityChain {

	public CollectSignVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
		if(r.indexOf("S")==-1){
			return Constants.Verify.FNAME_COLLECTSIGN_ERRMSG;
		}
		return null;
	}

	/*public static void main(String[] args) {
		String r = "aaS1";
		if(r.indexOf("S")==-1){
			System.out.println(Constants.Verify.FNAME_COLLECTSIGN_ERRMSG);
		}
		System.out.println("OK");
	}*/
}
