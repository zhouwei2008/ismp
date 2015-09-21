<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-账务详情</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'sjfw.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
</head>
<body>
<div class="xtgl_content">
    %{--<span class="left" style="margin-left:10px;">--}%
        %{--<a href="${request.contextPath}/${session.level3Map['settle/index'].modelPath}?direction=<g:if test="${params.direction==''}">all</g:if><g:else>${params.direction}</g:else>">返回</a>--}%
    %{--</span>--}%
    <table width="100%" class="right_list_tablebx" id="dataTbl">
        <tr class="c1">
            <td colspan="2" scope="col">
                <div class="xtgl_h1">
                    <b>账务详细信息</b>
                </div>
            </td>
        </tr>
        <tr class="c2"><td width="17%" class="right_fnt">流水号：</td><td width="83%" class="left_fnt">${acSeq.transaction.tradeNo}</td></tr>
        <tr class="c1"><td class="right_fnt">交易类型：</td><td class="left_fnt">${acSeq.transaction.transTypeMap[acSeq.transaction.transferType]}</td></tr>
        <tr class="c1"><td class="right_fnt">时间：</td><td class="left_fnt">${acSeq.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td></tr>
        <tr class="c1"><td class="right_fnt">收入（元）：</td><td class="left_fnt"><g:formatNumber currencyCode="CNY" number="${acSeq.debitAmount/100}" type="currency"/></td></tr>
        <tr class="c2"><td class="right_fnt">支出（元）：</td><td class="left_fnt"><g:formatNumber currencyCode="CNY" number="${acSeq.creditAmount/100}" type="currency"/></td></tr>
        <tr class="c1"><td class="right_fnt">账户余额（元）：</td><td class="left_fnt"><g:formatNumber currencyCode="CNY" number="${acSeq.balance/100}" type="currency"/></td></tr>
        <tr class="c1"><td class="right_fnt">摘要：</td><td class="left_fnt">${acSeq.transaction.subjict == 'null' ? '' : acSeq.transaction.subjict?.encodeAsHTML()}</td></tr>
        %{--<tr class="c1"><td class="right_fnt">备注：</td><td class="left_fnt">${acSeq.transaction.note=='null'?'':acSeq.transaction.note?.encodeAsHTML()}</td></tr>--}%
    </table>
</div>

</body>
</html>