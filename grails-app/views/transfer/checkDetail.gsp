<%@ page import="gateway.GwOrder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-转账交易详情</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>

    <script type="text/javascript">
        function checkPass(str) {
            if (confirm("您确认要审批通过？")) {
                window.document.location.href = '${createLink(controller:'transfer', action:'checkPass', params:['statusFlag':'1'])}&id=' + str;
            }
        }
        function checkRefuse(str) {
            if (confirm("您确认要审批拒绝？")) {
                window.document.location.href = '${createLink(controller:'transfer', action:'checkRefuse', params:['statusFlag':'1'])}&id=' + str;
            }
        }
    </script>

    <div class="serchlitst">

        <table class="tlb1">
            <tr>
                <th class="txtLeft" scope="col" colspan="2">
                    <b>转账交易详情</b>
                </th>
            </tr>
            <tr><td class="txtRight" scope="col" width="100">交易号：</td><td class="txtLeft" scope="col">${trade?.tradeNo}</td></tr>
            <tr><td class="txtRight" scope="col">交易类型：</td><td class="txtLeft" scope="col">${trade?.tradeTypeMap[trade?.tradeType]}</td></tr>
            <tr><td class="txtRight" scope="col">时间：</td><td class="txtLeft" scope="col">${trade.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td></tr>
            <tr><td class="txtRight" scope="col">付款方：</td><td class="txtLeft" scope="col">${trade?.payerName}</td></tr>
            <tr class="c2"><td class="right_fnt">收款方：</td><td class="txtLeft" scope="col">${trade?.payeeName}</td></tr>
            <tr><td class="txtRight" scope="col">交易金额：</td><td class="txtLeft" scope="col"><g:formatNumber currencyCode="CNY" number="${trade?.amount/100}" type="currency"/></td></tr>
            <tr class="c2"><td class="right_fnt">交易状态：</td><td class="txtLeft" scope="col">${ismp.TradeBase.statusMap[trade.status]}</td></tr>
            <tr class="c2"><td class="right_fnt">备注：</td><td class="txtLeft" scope="col">${trade?.note == 'null' ? '' : trade.note}</td></tr>
            <tr class="c2"><td class="right_fnt">付款理由：</td><td class="txtLeft" scope="col">${trade?.subject}</td></tr>
            <tr><td class="txtRight" scope="col">提交方式：</td><td class="txtLeft" scope="col">${trade.submitTypeMap[trade.submitType]}</td></tr>
            <tr><td class="txtRight" scope="col">操作员：</td><td class="txtLeft" scope="col">${trade.submitter}</td></tr>
            <tr class="c1">
                <td align="center" colspan="2">
                    <input type="button" onclick="checkPass(${trade.id});" class="btn mglf10" value="审核通过">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" onclick="checkRefuse(${trade.id});" class="btn mglf10" value="审核拒绝">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="back" name="back" class="btn mglf10" value="返回" onclick="javascript:history.go(-1);">
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
