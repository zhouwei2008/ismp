package com.ecard.products.pay.data.analyse.impl;


import com.ecard.products.pay.data.analyse.AbsAnalyseService;
import com.ecard.products.pay.data.analyse.details.*;
import com.ecard.products.pay.data.analyse.filename.CollectSignVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.filename.DateVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.filename.ExistVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.filename.FNFormatVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.BizOrgVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.CollectServiceVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.NoSameBizOrgVerifyResponsibilityChain;
import com.ecard.products.pay.data.analyse.service.VersionVerifyResponsibilityChain;

public class DeductAnalyseService extends AbsAnalyseService {

	@Override
	protected void buildFileResponsibilityChain() {
		// TODO Auto-generated method stub
		// service
		CollectServiceVerifyResponsibilityChain collectService = new CollectServiceVerifyResponsibilityChain(null);
		VersionVerifyResponsibilityChain version = new VersionVerifyResponsibilityChain(collectService);
		NoSameBizOrgVerifyResponsibilityChain noSame = new NoSameBizOrgVerifyResponsibilityChain(version);
		BizOrgVerifyResponsibilityChain bizOrg = new BizOrgVerifyResponsibilityChain(noSame);
		// base
		CollectSignVerifyResponsibilityChain sign = new CollectSignVerifyResponsibilityChain(bizOrg);
		DateVerifyResponsibilityChain date = new DateVerifyResponsibilityChain(sign);
		ExistVerifyResponsibilityChain exist = new ExistVerifyResponsibilityChain(date);
		FNFormatVerifyResponsibilityChain fName = new FNFormatVerifyResponsibilityChain(exist);
		fileChain = new FNFormatVerifyResponsibilityChain(fName);
	}

	@Override
	protected void buildTitleResponsibilityChain() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void buildDetailResponsibilityChain() {
		// TODO Auto-generated method stub
		// 检测链 Demo:000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,北京;北京,111111111111111,用途
        Remark2ResponsibilityChain r2rc = new Remark2ResponsibilityChain(null);
        UserCodeResponsibilityChain urc = new UserCodeResponsibilityChain(r2rc);
		RemarkResponsibilityChain rrc = new RemarkResponsibilityChain(urc);
		AmountResponsibilityChain amrc = new AmountResponsibilityChain(rrc);
		AccountTypeResponsibilityChain atrc = new AccountTypeResponsibilityChain(amrc);
		BankNameResponsibilityChain bnrc = new BankNameResponsibilityChain(atrc);
		SubBranchResponsibilityChain sbrc = new SubBranchResponsibilityChain(bnrc);
		BranchResponsibilityChain brc = new BranchResponsibilityChain(sbrc);
		AccNameResponsibilityChain anrc = new AccNameResponsibilityChain(brc);
		AccountResponsibilityChain arc = new AccountResponsibilityChain(anrc);
		RepeatVerifyResponsibilityChain rvr = new RepeatVerifyResponsibilityChain(arc);
		detailChain = new CompleteVerifyResponsibilityChain(rvr);
	}

}
