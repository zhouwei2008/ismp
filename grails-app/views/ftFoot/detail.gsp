<%@ page import="ismp.TradeBase; settle.FtSrvTradeType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-结算查询</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js/My97DatePicker', file: 'WdatePicker.js')}"></script>
    <script type="text/javascript">
        function search(obj) {
            if (obj) {
                if ($("offset"))
                    $("offset").value = 0;
            }
            $("#searchform").submit();
        }
    </script>
</head>
<body>
<!--内容区开始-->
    <div class="InContent">
        <div class="searchForm">
        <h1>结算查询</h1>
        </div>
        <!--搜索结果开始-->
        <div class="searchResult">
            <div class="trdeDetail_grid">

                <table class="gridStyle_normal">
                    <thead>
                    <tr>
                        <td>订单支付日期范围</td>
                        <td>结算日期</td>
                        <td>订单笔数</td>
                        <td>订单金额(元)</td>
                        <td>退款金额(元)</td>
                        <td>订单净额(元)</td>
                        <td>手续费金额(元)</td>
                        <td>结算金额(元)</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${a1}-${a2}</td>
                        <td>${b}</td>
                        <td>${c}</td>
                        <td>${d}</td>
                        <td>${e}</td>
                        <td>${f}</td>
                        <td><strong style="color:green">${g}</strong></td>
                        <td><strong style="color:red">${h}</strong></td>
                    </tr>
                    </tbody>
                </table>
                <div class="searchForm">
                    <h1>结算明细</h1>
                </div>

                <table class="gridStyle_normal">
                    <thead>
                    <tr>
                        <td>序号</td>
                        <td>流水号</td>
                        <td>商户订单号</td>
                        <td>支付时间</td>
                        <td>交易类型</td>
                        <td>交易金额（元）</td>
                        <td>手续费金额（元）</td>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${result}" status="i" var="trade">
                        <tr>
                            <td>${i + 1}</td>
                            <td>${trade.SEQ_NO}</td>
                            <td>${TradeBase.findByTradeNo(trade.SEQ_NO)?.outTradeNo}</td>
                            <td><g:formatDate format="yyyy.MM.dd HH:mm:ss" date="${trade.BILL_DATE}"/></td>
                            <td>${FtSrvTradeType.findByTradeCode(trade.TRADE_CODE)?.tradeName}</td>
                            <td><strong style="color:red">${Math.abs(trade.AMOUNT).toBigDecimal().divide(100,2,0).toPlainString()}</strong></td>
                            <td>
                                <strong style="color:green">
                                <g:if test="${trade.POST_FEE}">${Math.abs(trade.POST_FEE).toBigDecimal().divide(100,2,0).toPlainString()}</g:if>
                                <g:elseif test="${trade.PRE_FEE}">${Math.abs(trade.PRE_FEE).toBigDecimal().divide(100,2,0).toPlainString()}</g:elseif>
                                 </strong>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

                <g:form controller="ftFoot" action="detail" method="post" name="searchform">
                    <g:hiddenField name="pay_no" value="${pay_no}"/>
                    <g:hiddenField name="ref_no" value="${ref_no}"/>
                    <g:hiddenField name="a1" value="${a1}"/>
                    <g:hiddenField name="a2" value="${a2}"/>
                    <g:hiddenField name="b" value="${b}"/>
                    <g:hiddenField name="c" value="${c}"/>
                    <g:hiddenField name="d" value="${d}"/>
                    <g:hiddenField name="e" value="${e}"/>
                    <g:hiddenField name="f" value="${f}"/>
                    <g:hiddenField name="g" value="${g}"/>
                    <g:hiddenField name="h" value="${h}"/>

                    <div class="tableFoot clearFloat">
                        <div class="pagination">
                            <g:pageNavi total="${total}"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
        <!--搜索结果结束-->
    </div>
    <!--内容区结束-->
</form>
</body>
</html>