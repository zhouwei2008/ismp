<%@ page import="ismp.TradeBase" %>
#吉高分润明细查询
#账号：[${session.cmCustomer.customerNo}]
#起始日期：[${params.startDate}]   终止日期: [${params.endDate}]
#-----------------------------------------分润明细列表----------------------------------------
时间                      商户订单号                 交易对象账户                 流水号                 类型      收入金额（+元）        支出金额（-元）        账户余额（元）         摘要
<g:each in="${acSeqList}" status="i" var="acSeq">${acSeq.dateCreated.format("yyyy-MM-dd HH:mm:ss")}     ${acSeq.transaction.outTradeNo}     ${TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeId == session.cmCustomer.id ?(TradeBase.findByTradeNo(acSeq.transaction.tradeNo).payerCode==null ?TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payerName : TradeBase.findByTradeNo(acSeq.transaction.tradeNo).payerCode) : TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeCode==null ?TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeName : TradeBase.findByTradeNo(acSeq.transaction.tradeNo).payeeCode }	      ${acSeq.transaction.tradeNo}	      ${acSeq.transaction.transTypeMap[acSeq.transaction.transferType]}       +<g:formatNumber number="${(acSeq.debitAmount?acSeq.debitAmount:0)/100}" format="#0.00#"/>                -<g:formatNumber number="${(acSeq.creditAmount?acSeq.creditAmount:0)/100}" format="#0.00#"/>                <g:formatNumber number="${(acSeq.balance?acSeq.balance:0)/100}" format="#0.00#"/>         ${acSeq.transaction.subjict=='null'?'':acSeq.transaction.subjict}
</g:each>
#----------------------------------------分润明细列表结束-------------------------------------
#支出合计：${out_count?out_count:0}笔，共-<g:formatNumber number="${(out_amount?out_amount:0)/100}" format="#0.00#"/>元
#收入合计：${in_count?in_count:0}笔，共<g:formatNumber number="${(in_amount?in_amount:0)/100}" format="#0.00#"/>元
#导出时间：[${new Date().format('yyyy年MM月dd日 HH:mm:ss')}]
