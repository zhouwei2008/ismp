<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-退款信息确认</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>
<div class="trnmenu"> <span class="left trnmenutlt">退款信息确认：</span>
      <div class="main_box">
      	  <g:form action="confirm" useToken="true" name="actionForm">
               <input type="hidden" name="buyer_name" value="${session.cmLoginCertificate.loginCertificate}"/>
               <input type="hidden" name="buyer_id" value="${session.cmCustomer.id}"/>
                <input type="hidden" name="model" id="model" value="${model}">
                <input type="hidden" name="id" id="id" value="${trade.id}"/>
                <input type="hidden" name="amount" id="amount" value="${amount}">
                <input type="hidden" name="description" id="description" value="${description}">

           <div class="serchlitst">
                <g:if test="${flash.message}">
                    <div class="message"><font color="red">${flash.message}</font></div>
                </g:if>
                     <table class="tlb1" >
                         <tr>
                             <td class="txtRight b" width="150">商户订单号：</td><td class="txtLeft">${trade.outTradeNo}</td>
                             <td class="txtRight b" width="150">平台交易号：</td><td class="txtLeft">${trade.tradeNo}</td>
                         </tr>
                         <tr>
                             <td class="txtRight b">交易类型：</td><td class="txtLeft">${trade.tradeTypeMap[trade.tradeType]}</td>
                             <td class="txtRight b">交易状态：</td><td class="txtLeft">${ismp.TradeBase.statusMap[trade.status]}</td>
                         </tr>
                         <tr>
                             <td class="txtRight b">付款方：</td><td class="txtLeft">${trade.payerName}</td>
                             <td class="txtRight b">收款方：</td><td class="txtLeft">${trade.payeeName}</td>
                         </tr>
                          <tr>
                             <td class="txtRight b">交易金额：</td><td class="txtLeft"><span><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/>元</span></td>
                             <td class="txtRight b">已退金额： </td><td class="txtLeft"><span><g:if test="${trade.refundAmount>0}"><g:formatNumber currencyCode="CNY" number="${(trade.refundAmount)/100}" type="currency"/></g:if><g:else>0</g:else>元</span></td>
                         </tr>
                         <tr>
                              <td class="txtRight b">可退金额：</td>
                              <td class="txtLeft" colspan="3"><span><g:if test="${(trade.amount-(trade.refundAmount))>0}"><g:formatNumber currencyCode="CNY" number="${(trade.amount-(trade.refundAmount))/100}" type="currency"/></g:if><g:else>0</g:else>元</span></td>
                         </tr>
                         <tr>
                             <td class="txtRight b">退款金额：</td>
                             <td class="txtLeft" colspan="3"><span>${amount}元</span></td>
                         </tr>
                         <tr>
                             <td class="txtRight b">退款备注：</td>
                             <td colspan="3" class="txtLeft">${description}</td>
                         </tr>
                         <tr>
                             <td class="txtRight b">
                                支付密码：
                             </td>
                             <td class="txtLeft" colspan="3">
                                 <input name="payPwd" id="payPwd" type="password" maxlength="64"/>
                             </td>
                         </tr>
                         <tr>
                             <td></td>
                             <td colspan="3">
                     <input type="submit" class="btn mglf10" value="确认退款" />
                     &nbsp;&nbsp;&nbsp;&nbsp;
                     <input type="button" id="back" name="back" class="btn mglf10" value="返回"/>
                             </td>
                         </tr>
                     </table>

           </div>
            </g:form>
      </div>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#back").removeAttr('onclick');
            $("#back").click(function(){
               var url = "${createLink(controller:'refund',action:'toindex')}?id=" + $("#id").val() +"&amount=" + $("#amount").val() +"&description=" + $("#description").val();
               window.document.location.href = url;
            });
        });

    </script>
</body>
</html>