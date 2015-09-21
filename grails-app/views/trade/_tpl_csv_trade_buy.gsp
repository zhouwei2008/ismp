# 吉高交易明细查询
# 账    号：[${session.cmLoginCertificate.loginCertificate}]
# 起始日期：[${params.startDate}]      终止日期: [${params.endDate}]
#-----------------------------------------交易明细列表----------------------------------------
# 交易号,商户订单号,创建时间,类型,卖家,商品名称,金额（元）,交易状态
<g:each in="${tradeList}" status="i" var="trade">${trade.tradeNo}		,${trade.outTradeNo}	,${trade.dateCreated.format('yyyy-MM-dd HH:mm:ss')},${trade.tradeType},${trade.payeeName},${trade.subject},<g:formatNumber number="${trade.amount/100}" format="#.00#"/>,${ismp.TradeBase.statusMap[trade.status]}
</g:each>
#-------------------------------------------------------------------------------------------
# 交易笔数：${tradeListTotal}笔
# 交易总额：<g:formatNumber number="${totalamount/100}" format="#.00#"/>元
# 导出时间：[${new Date().format('yyyy年MM月dd日 HH:mm:ss')}]      用户：${session.cmCustomer.registrationName}
