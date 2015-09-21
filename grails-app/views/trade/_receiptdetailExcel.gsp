<%@ page import="ismp.CmCustomerBankAccount; ismp.TradeWithdrawn; account.AcAccount; ismp.TradeBase" %>
<%@ page import="ismp.change" %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<style type="text/css">

body { font-family:'宋体'; text-align:center; margin:0 auto; padding:0; background:#FFF; font-size:12px; color:#333; }
body > div { text-align:center; margin-right:auto; margin-left:auto; }
body { background: url(../img/bodybg.gif) repeat-x #f0f0f0; }
div, form, ul, ol, li, span, p { margin:0; padding:0; border:0; }
img, a img { border:0; margin:0; padding:0; }
h1, h2, h3, h4, h5, h6 { margin:0; padding:0; font-size:12px; font-weight:normal; }
ul, ol, li { list-style:none }
table, td, input { font-size:12px; padding:0 }
/* 默认链接颜色 */
a { outline-style:none; color:#333; text-decoration:none }
a:hover { color:#c00; text-decoration:underline; }



/* 通用属性 */
.left { float:left; }
.right { float:right; }
.clear { clear:both; font-size:1px; width:1px; height:0; visibility:hidden; margin-top:0px!important; *margin-top:-1px;
line-height:0 }/*ie and firefox1.5 updata */
/*文字对齐方式*/
.txtLeft { text-align:left }
.txtRight { text-align:right }
.txtCenter { text-align:center }
.pdleft { padding-left:30px; }
.pdleft8 { padding-left:8px; }
.font14 { font-size:14px; }



.huidantlt{width:700px; height:30px; line-height:30px; font-size:14px; font-weight:bold}
.huidanbox { width:700px; height:270px; border-top: solid 4px #1b8bc5;background:#fff; border-collapse:collapse; }
.huidanboxtb{border-collapse:collapse; width:400px;}
.huidanboxtb tr td{height:25px; border-collapse:collapse;border:solid 1px #ccc}
.huidanboxtb tr th{height:25px; font-weight:bold; border-collapse:collapse;border:solid 1px #ccc}

.huidanbox p{line-height:30px; text-align:left; font-weight:bold}
</style>
</head>

<body>

<div class="huidantlt">吉高业务凭证（电子回单）</div>
<div class="huidanbox">
<p>交易号：${acSeq?.transaction.tradeNo}</p>
		<table width="100%" class="huidanboxtb">
             <g:hiddenField name="tradeNo" value="${acSeq?.transaction.tradeNo}"></g:hiddenField>
				<tr>
						<th colspan="2" scope="col">付款方</th>
						<th colspan="2" scope="col">收款方</th>
				</tr>
				<tr>
						<td width="19%" class="txtRight">名称：</td>
                            <g:if test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='fee_rfd'}">
                                   <td width="31%" align="left">&nbsp;吉高</td>
                            </g:if>
                             <g:elseif test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='charge'}">
                                   <td width="31%" align="left">&nbsp;</td>
                            </g:elseif>
                            <g:else>
                                   <td width="31%" align="left">&nbsp;${acSeq?.transaction.fromAccount.accountName}</td>
                            </g:else>
						<td width="18%" class="txtRight">名称： </td>
                            <g:if test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='fee'}">
                                <td width="32%" align="left">&nbsp;吉高</td>
                            </g:if>
                            <g:else>
                                  <td width="32%" align="left">&nbsp;${acSeq?.transaction.toAccount.accountName}</td>
                            </g:else>
				</tr>
                <tr>
                    <g:if test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='charge'}">
                         <td  class="txtRight">付款银行：</td>
						<td  align="left">&nbsp;</td>
                    </g:if>
                    <g:elseif test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='fee_rfd'}">
                         <td  class="txtRight">吉高账户：</td>
						<td  align="left">&nbsp;</td>
                    </g:elseif>
                    <g:else>
                        <td  class="txtRight">吉高账户：</td>
						<td  align="left">&nbsp;${tBase?.payerCode}</td>
                    </g:else>
                    <g:if test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='withdrawn'}">
                         <td  class="txtRight">收款银行：</td>
						<td  align="left">&nbsp;${CmCustomerBankAccount.get(TradeWithdrawn.get(tBase?.id)?.customerBankAccountId)?.branch!=null ? CmCustomerBankAccount.get(TradeWithdrawn.get(tBase?.id)?.customerBankAccountId)?.branch : ''+CmCustomerBankAccount.get(TradeWithdrawn.get(tBase?.id)?.customerBankAccountId)?.subbranch!=null? CmCustomerBankAccount.get(TradeWithdrawn.get(tBase?.id)?.customerBankAccountId)?.subbranch : ''}</td>
                    </g:if>
                     <g:elseif test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='fee'}">
                         <td  class="txtRight">吉高账户：</td>
						<td  align="left">&nbsp;</td>
                    </g:elseif>
                    <g:else>
                        <td  class="txtRight">吉高账户： </td>
						<td align="left">&nbsp;${tBase?.payeeCode}</td>
                    </g:else>
				</tr>
                 <g:if test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='charge'}">
                    <tr>
						<td  class="txtRight">账号：</td>
						<td  align="left">&nbsp;</td>
						<td  class="txtRight">&nbsp;</td>
						<td align="left">&nbsp;</td>
				</tr>
                   </g:if>
                   <g:if test="${acSeq!=null&&acSeq.transaction.transferType!= null&&acSeq.transaction.transferType=='withdrawn'}">
                    <tr>
						<td  class="txtRight">&nbsp;</td>
						<td  align="left">&nbsp;</td>
						<td  class="txtRight">账号：</td>
						<td align="left">&nbsp;${TradeWithdrawn.get(tBase?.id)?.customerBankNo}</td>
				</tr>
                 </g:if>
				<tr>
						<td class="txtRight">交易金额（大写）： </td>
						<td align="left">&nbsp;${change.toBigAmt(acSeq?.transaction.amount/100)}</td>
						<td class="txtRight">交易金额（小写）： </td>
						<td align="left">&nbsp;<g:formatNumber currencyCode="CNY" number="${acSeq?.transaction.amount/100}" type="currency"/></td>
				</tr>
			    <tr>
						<td class="txtRight">费用（大写）： </td>
						<td align="left">&nbsp;${change.toBigAmt(fee/100)}
                        </td>
						<td class="txtRight">费用（小写）： </td>
						<td align="left">&nbsp;<g:formatNumber currencyCode="CNY" number="${fee/100}" type="currency"/>
                        </td>
				</tr>
				<tr>
						<td class="txtRight">实付金额（大写）： </td>
						<td align="left">&nbsp;${change.toBigAmt((acSeq?.transaction.amount+fee)/100)}</td>
						<td class="txtRight">实付金额（小写）： </td>
						<td align="left">&nbsp;<g:formatNumber currencyCode="CNY" number="${(acSeq?.transaction.amount+fee)/100}" type="currency"/></td>
				</tr>
				<tr>
						<td class="txtRight">交易类型：</td>
						<td colspan="3" align="left">&nbsp;${acSeq.transaction.transTypeMap[acSeq?.transaction.transferType]}</td>
				</tr>
				<tr>
						<td class="txtRight">交易时间：</td>
						<td colspan="3" align="left">&nbsp;${strdate}</td>
				</tr>
				<tr>
						<td class="txtRight">备注：</td>
						<td colspan="3" align="left">&nbsp;${acSeq.transaction.subjict == 'null' ? '' : acSeq.transaction.subjict?.encodeAsHTML()}</td>
				</tr>
				<tr>
						<td colspan="4" align="left">&nbsp;温馨提示：本回单不作为收款方发货依据，并请勿重复记账。</td>
				</tr>
		</table>
</div>
</body>
</html>
