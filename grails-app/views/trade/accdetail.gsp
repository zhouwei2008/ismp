<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <g:if test="${flag==1}">
        <title>吉高-分润详情</title>
    </g:if>
    <g:else>
        <title>吉高-收支详情</title>
    </g:else>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <g:javascript library="jquery"/>
</head>
<body>

<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <g:if test="${flag==1}">
            <h1>分润详细信息</h1>
        </g:if>
        <g:else>
            <h1>收支详细信息</h1>
        </g:else>


        <div class="normalContent">

            <div class="Content800">
                <div class="lineHeight50 clearFloat">
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">流水号：</label><span class="contentTxt fl width250">${acSeq.transaction.tradeNo}</span>
                        <label class="labelTxtR fl width150">交易类型：</label><span class="contentTxt fl width250">${acSeq.transaction.transTypeMap[acSeq.transaction.transferType]}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">时间：</label><span class="contentTxt fl width250">${acSeq.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</span>
                        <label class="labelTxtR fl width150">收入（元）：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${acSeq.debitAmount/100}" type="currency"/></span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">支出（元）：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${acSeq.creditAmount/100}" type="currency"/></span>
                        <label class="labelTxtR fl width150">账户余额（元）：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${acSeq.balance/100}" type="currency"/></span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">摘要：</label><span class="contentTxt fl width650">${acSeq.transaction.subjict == 'null' ? '' : acSeq.transaction.subjict?.encodeAsHTML()}</span>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
<!--内容区结束-->
</body>
</html>
