<%@ page import="ismp.CmCustomerBankAccount; ismp.TradeWithdrawn; account.AcAccount; ismp.TradeBase" %>
<%@ page import="ismp.change" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>吉高-电子回单</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}" media="all"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" media="all"/>
</head>

<body>

<div class="huidantlt">吉高业务凭证（电子回单）</div>
<div class="huidanbox">
<p>交易号：${acSeq?.transaction.tradeNo}</p>
		<table width="100%" class="huidanboxtb">
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
                <tr>
						<td colspan="4" align="right" id="printTD"><a  href="#"  onclick="printCode()" id="printID" name="printID" >打印</a>&nbsp;&nbsp;<a  href="${request.contextPath}/${session.level3Map['trade/downReceipt'].modelPath}/${acSeq.id}" target="_blank">下载</a></td>
				</tr>
		</table>
</div>
</body>
</html>
<script>

function printCode()
{
      document.getElementById("printTD").style.display="none";
      javascript:window.print();
}
</script>
