package com.ecard.products.pay.data.analyse.impl;


import com.ecard.products.pay.data.analyse.AbsAnalyseExcelService;
import com.ecard.products.pay.data.analyse.AbsAnalyseService;
import com.ecard.products.pay.data.analyse.detailextends.*;
import com.ecard.products.pay.data.analyse.details.*;
import com.ecard.products.pay.data.analyse.filename.DateVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.filename.ExistVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.filename.FNFormatVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.filename.PaySignVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.BizOrgVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.NoSameBizOrgVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.PayServiceVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.VersionVerifyResponsibilityChain;

public class ExcelPayAnalyseService extends AbsAnalyseExcelService {


	@Override
	protected void buildFileResponsibilityChain() {
		// TODO Auto-generated method stub
		// service
		PayServiceVerifyResponsibilityChain payService = new PayServiceVerifyResponsibilityChain(null);
		VersionVerifyResponsibilityChain version = new VersionVerifyResponsibilityChain(payService);
		NoSameBizOrgVerifyResponsibilityChain noSame = new NoSameBizOrgVerifyResponsibilityChain(version);
		BizOrgVerifyResponsibilityChain bizOrg = new BizOrgVerifyResponsibilityChain(noSame);
		// base
		PaySignVerifyResponsibilityChain sign = new PaySignVerifyResponsibilityChain(bizOrg);
		DateVerifyResponsibilityChain date = new DateVerifyResponsibilityChain(sign);
		ExistVerifyResponsibilityChain exist = new ExistVerifyResponsibilityChain(date);
		FNFormatVerifyResponsibilityChain fname = new FNFormatVerifyResponsibilityChain(exist);
		fileChain = new FNFormatVerifyResponsibilityChain(fname);
	}


	@Override
	protected void buildDetailResponsibilityChain() {
		// TODO Auto-generated method stub
        //保单获取成本,广东分公司,管理费用-保单获取成本,2011-07-06,44-060001040001385,871362632408091001,茂名康顺车辆信息咨询部,中国银行茂名分行光华北支行,广东省,茂名市,,,,线下,21.8,手续费茂名6
        NoteResponsibilityChain nrc = new NoteResponsibilityChain(null);
        AmountdResponsibilityChain arc = new AmountdResponsibilityChain(nrc);
        AccCityResponsibilityChain acrc = new AccCityResponsibilityChain(arc);
        AccProvinceResponsibilityChain aprc = new AccProvinceResponsibilityChain(acrc);
        BankResponsibilityChain brc = new BankResponsibilityChain(aprc);
        AccNamedResponsibilityChain anrc = new AccNamedResponsibilityChain(brc);
        detailChain = new AccResponsibilityChain(anrc);
	}
}
