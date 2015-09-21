<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-批次交易详情</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'sjfw.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
</head>
<body>


<div class="xtgl_content">
	<table width="100%" class="right_list_tablebx" id="dataTbl">
		<tr class="c1">
			<td colspan="2" scope="col" >
				<div class="xtgl_h1">
					<b>批次详细信息</b>
				</div>
			</td>
		</tr>
		<tr class="c2"><td width="17%" class="right_fnt">批次交易号：</td><td width="83%" class="left_fnt">${trade?.id}</td></tr>
		<tr class="c1"><td class="right_fnt">交易类型：</td><td class="left_fnt">${trade?.batchTypeMap[trade?.batchType]}</td></tr>
        <tr class="c1"><td class="right_fnt">创建时间：</td><td class="left_fnt">${trade?.batchDate}</td></tr>
		<tr class="c1"><td class="right_fnt">总数：</td><td class="left_fnt">${trade?.batchCount}</td></tr>
		<tr class="c2"><td class="right_fnt">总金额：</td><td class="left_fnt"><g:formatNumber currencyCode="CNY" number="${trade?.batchAmount}" type="currency"/></td></tr>
		<tr class="c1"><td class="right_fnt">状态：</td><td class="left_fnt">${trade?.statusMap[trade?.batchStatus]}</td></tr>
		<tr class="c2"><td class="right_fnt">手续费：</td><td class="left_fnt">${trade?.batchFee}</td></tr>
        <tr class="c2"><td class="right_fnt">结算方式：</td><td class="left_fnt">${trade?.feeTypeMap[trade?.batchFeetype]}</td></tr>
        <g:if test="${trade.batchType=='S'}">
        <tr class="c2"><td class="right_fnt">实收金额：</td><td class="left_fnt">${trade?.batchAccamount}</td></tr>
        </g:if>
        <g:if test="${trade.batchType=='F'}">
        <tr class="c2"><td class="right_fnt">实付金额：</td><td class="left_fnt">${trade?.batchAccamount}</td></tr>
        </g:if>
        <tr class="c2"><td class="right_fnt">交易描述：</td><td class="left_fnt">${trade?.batchRemark=='null'?'':trade?.batchRemark}</td></tr>
	</table>
</div>

</body>
</html>