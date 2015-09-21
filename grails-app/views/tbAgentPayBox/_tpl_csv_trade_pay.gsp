# 吉高代收付批次查询
# 账    号：[${session.cmLoginCertificate.loginCertificate}]
# 起始日期：[${params.startDate}]      终止日期: [${params.endDate}]
#-----------------------------------------交易批次列表----------------------------------------
# 批次交易号, 创建时间, 类型,上传文件名,总数, 金额（元）, 状态, 手续费（元）, 结算方式,实收金额（元）,备注
<g:each in="${tradeList}" status="i" var="trade">${trade.id}		,${trade.batchDate}	,${trade.batchTypeMap[trade.batchType]},${trade.batchFilename}		,${trade.batchCount},<g:formatNumber number="${trade.batchAmount}" format="#0.00#"/>		,${trade.statusMap[trade.batchStatus]},<g:formatNumber currencyCode="CNY" number="${trade.batchFee}" format="#0.00#" />		,${trade.feeTypeMap[trade.batchFeetype]},<g:formatNumber currencyCode="CNY" number="${trade.batchAccamount}" format="#0.00#"/>		,${trade.batchRemark1}
</g:each>
#-------------------------------------------------------------------------------------------
# 交易笔数：${tradeListTotal}笔
# 交易总额：<g:formatNumber number="${totalamount}" format="#0.00#"/>元
# 导出时间：[${new Date().format('yyyy年MM月dd日 HH:mm:ss')}]      用户：${session.cmCustomer.registrationName}
