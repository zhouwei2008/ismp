# 吉高代收付批次明细查询
# 账    号：[${session.cmLoginCertificate.loginCertificate}]
# 起始日期：[${params.startDate}]      终止日期: [${params.endDate}]
#-----------------------------------------交易批次列表-----------------------------------------1
# 交易号,创建时间,流水号,类型,交易金额（元）,状态,<g:if test="${type == 'F'}">手续费（元）,实际金额（元）,</g:if>收/付款人,收/付款人账号,开户行,分行,支行,省,市,证件类型,证件号,手机号,用户协议号,商户订单号,完成时间,用途,交易状态,反馈原因
<g:each in="${tradeList}" status="i" var="trade">${trade.id}		,<g:formatDate date="${trade.tradeSubdate}" dateStyle="yyyyMMdd hh:mm:ss"/>		,${trade.tradeNum}		,${trade.typeMap[trade.tradeType]},<g:formatNumber number="${trade.tradeAmount}" format="#0.00#"/>		,${trade.tradeStatusMap[trade.tradeStatus]},<g:if test="${type == 'F'}"><g:formatNumber currencyCode="CNY" number="${trade.tradeFee}" format="#0.00#" />,<g:formatNumber currencyCode="CNY" number="${trade.tradeAccamount}" format="#0.00#" />,</g:if>${trade.tradeCardname},${trade.tradeCardnum}		,${trade.tradeAccountname},${trade.tradeBranchbank},${trade.tradeSubbranchbank},${trade.tradeProvince},${trade.tradeCity},${trade.certificateTypeMap[trade.certificateType]},${trade.certificateNum}		,${trade.tradeMobile}		,${trade.contractUsercode}		,${trade.tradeCustorder}		,${trade.tradeDonedate?trade.tradeDonedate.format("yyyy-MM-dd HH:mm:ss"):""}		,${trade.tradeRemark}		,${trade.tradeFeedbackcode}		,${trade.tradeReason}
</g:each>
#-------------------------------------------------------------------------------------------
# 交易笔数：${tradeListTotal}笔
# 交易总额：<g:formatNumber number="${totalamount}" format="#0.00#"/>元
# 导出时间：[${new Date().format('yyyy年MM月dd日 HH:mm:ss')}]      用户：${session.cmCustomer.registrationName}
