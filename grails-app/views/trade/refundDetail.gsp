<%@ page import="ismp.RefundBatch; ismp.CmCustomerOperator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-退款拒绝交易详情</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
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
        <h1>交易详细</h1>
        <div class="normalContent">

            <div class="Content800">
                <div class="lineHeight50 clearFloat">
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">平台交易号：</label><span class="contentTxt fl width250">${trade?.tradeNo}</span>
                        <label class="labelTxtR fl width150">商户订单号：</label><span class="contentTxt fl width250">${trade?.outTradeNo}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">创建时间：</label><span class="contentTxt fl width250">${trade.uploadTime.format("yyyy-MM-dd HH:mm:ss")}</span>
                        <label class="labelTxtR fl width150">审核时间：</label><span class="contentTxt fl width250">${trade.authTime.format("yyyy-MM-dd HH:mm:ss")}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">商户号：</label><span class="contentTxt fl width250">${trade.customerNo}</span>
                        <%
                            def co = CmCustomerOperator.findById(trade.operatorId)
                        %>

                        <label class="labelTxtR fl width150">审核人：</label><span class="contentTxt fl width250">${co.name}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">退款金额：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${trade?.amount/100}" type="currency"/></span>
                        <label class="labelTxtR fl width150">交易状态：</label><span class="contentTxt fl width250">${ismp.TradeBase.statusMap[trade.status]}</span>
                    </div>

                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">退款申请备注：</label><span class="contentTxt fl width650">${trade?.note=='null'?'':trade.note}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">拒绝原因：</label><span class="contentTxt fl width650">${trade?.refundRefuse=='null'?'':trade.refundRefuse}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--内容区结束-->
</div>
</body>
</html>
