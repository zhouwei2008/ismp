<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-企业信息</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>

<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>企业信息</h1>
        <div class="normalContent">

            <div class="Content800">
                <div class="lineHeight50 clearFloat">
                    <p class="IDString">查询商户编号(MID)：<br />以下Merchant ID 请保存，在支付接口集成时，需要使用<br /><i>${session.cmCustomer.customerNo}</i></p>
                    <p class="IDString">查询安全校验码(Key)：<br />以下安全校验码请保存，在支付接口集成时，需要使用：<br /><i>${session.cmCustomer.apiKey}</i></p>
                    <div class="separateLine"></div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">营业执照编码：</label><span class="contentTxt fl width250">${cmCorporationInfo.businessLicenseCode}</span>
                        <label class="labelTxtR fl width150">企业税号：</label><span class="contentTxt fl width250">${cmCorporationInfo.taxRegistrationNo}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">组织机构代码：</label><span class="contentTxt fl width250">${cmCorporationInfo.organizationCode}</span>
                        <label class="labelTxtR fl width150">执照登记时间：</label><span class="contentTxt fl width250">${cmCorporationInfo.registrationDate?.format("yyyy-MM-dd")}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">执照有效期：</label><span class="contentTxt fl width250">${cmCorporationInfo.licenseExpires?.format("yyyy-MM-dd")}</span>
                        <label class="labelTxtR fl width150">注册资金(万)：</label><span class="contentTxt fl width250">${cmCorporationInfo.registeredFunds}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">经营范围：</label><span class="contentTxt fl width250">${cmCorporationInfo.businessScope}&nbsp;</span>
                        <label class="labelTxtR fl width150">注册地：</label><span class="contentTxt fl width250">${cmCorporationInfo.registeredPlace}&nbsp;</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">公司人数：</label><span class="contentTxt fl width250">${cmCorporationInfo.numberOfStaff}&nbsp;</span>
                        <label class="labelTxtR fl width150">公司电话：</label><span class="contentTxt fl width250">${cmCorporationInfo.companyPhone}&nbsp;</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">法人：</label><span class="contentTxt fl width250">${cmCorporationInfo.corporate}&nbsp;</span>
                        <label class="labelTxtR fl width150">办公地址：</label><span class="contentTxt fl width250">${cmCorporationInfo.officeLocation}&nbsp;</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">联系人：</label><span class="contentTxt fl width250">${cmCorporationInfo.contact}&nbsp;</span>
                        <label class="labelTxtR fl width150">邮编：</label><span class="contentTxt fl width250">${cmCorporationInfo.zipCode}&nbsp;</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">联系人电话：</label><span class="contentTxt fl width250">${cmCorporationInfo.contactPhone}</span>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
<!--内容区结束-->

</body>
</html>
