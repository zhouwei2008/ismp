<%@ page import="gateway.GwOrder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-交易详情</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <g:javascript library="jquery"/>
</head>
<body>

<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>交易详细</h1>
        <div class="normalContent">

            <div class="Content800">
                <div class="lineHeight50 clearFloat">
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">交易号：</label><span class="contentTxt fl width250">${trade?.tradeNo}</span>
                        <label class="labelTxtR fl width150">交易类型：</label><span class="contentTxt fl width250">${trade?.tradeTypeMap[trade?.tradeType]}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">时间：</label><span class="contentTxt fl width250">${trade.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</span>
                        <label class="labelTxtR fl width150">付款方：</label><span class="contentTxt fl width250">${trade?.payerName}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">收款方：</label><span class="contentTxt fl width250">${trade?.payeeName}</span>
                        <label class="labelTxtR fl width150">交易金额：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${trade?.amount/100}" type="currency"/></span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">交易状态：</label><span class="contentTxt fl width250">${ismp.TradeBase.statusMap[trade.status]}</span>
                    </div>

                    <g:if test="${trade.tradeType=='refund'}">
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">退款申请备注：</label><span class="contentTxt fl width250">${trade?.refundApply == 'null' ? '' : trade.refundApply}</span>
                            <label class="labelTxtR fl width150">退款备注：</label><span class="contentTxt fl width250">${trade?.note == 'null' ? '' : trade.note}</span>
                        </div>
                    </g:if>


                    <g:if test="${trade.tradeType=='payment'}">
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">商品名称：</label><span class="contentTxt fl width250">${trade?.subject}</span>
                            <label class="labelTxtR fl width150">买家真实姓名：</label><span class="contentTxt fl width250">${GwOrder.findById(trade.tradeNo)?.buyerRealname}</span>
                        </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">备注：</label><span class="contentTxt fl width650">${trade?.note == 'null' ? '' : trade.note}</span>
                        </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">商品描述：</label><span class="contentTxt fl width650">${GwOrder.get(trade.tradeNo)?.body}</span>
                        </div>
                    </g:if>

                    <g:if test="${trade.tradeType=='transfer'}">
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">付款理由：</label><span class="contentTxt fl width250">${trade?.subject}</span>
                            <label class="labelTxtR fl width150">提交方式：</label><span class="contentTxt fl width250">${trade.submitTypeMap[trade.submitType]}</span>
                        </div>
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">操作员：</label><span class="contentTxt fl width250">${trade.submitter}</span>
                        </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">备注：</label><span class="contentTxt fl width650">${trade?.note == 'null' ? '' : trade.note}</span>
                        </div>
                    </g:if>

                    <g:if test="${trade.tradeType=='withdraw'}">
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">提现银行：</label><span class="contentTxt fl width250">${trade?.customerBankCode}</span>
                            <label class="labelTxtR fl width150">银行帐户名：</label><span class="contentTxt fl width250">${trade?.customerBankAccountName}</span>
                        </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">银行账号：</label><span class="contentTxt fl width250">${trade?.customerBankAccountNo}</span>
                            <label class="labelTxtR fl width150">转账手续费：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${trade?.transferFee/100}" type="currency"/></span>
                        </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">实转金额：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${trade?.realTransferAmount/100}" type="currency"/></span>
                            <label class="labelTxtR fl width150">处理状态：</label><span class="contentTxt fl width250">${trade?.handleStatusMap[trade.handleStatus]}</span>
                        </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">备注：</label><span class="contentTxt fl width650">${trade?.note == 'null' ? '' : trade.note}</span>
                        </div>
                    </g:if>
                </div>
            </div>

        </div>
    </div>
</div>
<!--内容区结束-->



</body>
</html>
