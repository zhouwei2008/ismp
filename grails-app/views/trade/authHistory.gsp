<%@ page import="ismp.TradePayment; ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-退款审核拒绝交易</title>
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

<form action="authHistory" method="post" name="searchform" id="searchform">
<!--内容区开始-->
<div class="InContent">
    <div class="searchForm">
        <h1>审核拒绝记录</h1>
        <div class="searchContent">
            <ul class="formContent clearFloat">

                <li>
                    <label class="txtRight">平台交易号:</label><input name="tradeNo" type="text" id="tradeNo" value="${params.tradeNo}" maxlength="40"/>
                </li>
                <li>
                    <label class="txtRight">商户订单号:</label><input name="outTradeNo" type="text" id="outTradeNo" value="${params.outTradeNo}" maxlength="80"/>
                </li>
                <li>
                    <label class="txtRight">金额区间:</label> <input type="text" name="amountMin" id="amountMin" maxlength="10" class="w70" value="${params.amountMin}"/><span>-</span>
                    <input type="text" name="amountMax" id="amountMax" maxlength="10" value="${params.amountMax}" class="w70"/>元
                </li>
                <li>
                    <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"  />
                </li>
                <li>
                    <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"  />
                </li>

                <li class="formBtnArea">
                    <button class="formBtn">搜索</button>
                </li>
            </ul>
        </div>
    </div>


    <!--搜索结果开始-->
    <div class="searchResult">
        <div class="trdeDetail_grid">
            <table class="gridStyle_normal">
                <thead>
                <tr>
                    <td class="longData">创建时间</td>
                    <td class="orderNum">商品订单号</td>
                    <td class="trdeNum">平台交易号</td>
                    <td class="money">交易金额(元)</td>
                    <td class="money">退款金额(元)</td>
                    <td class="btnArea">操作</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${tradeList}" status="i" var="trade">
                    <%
                        def tb = TradeBase.findByOutTradeNoAndTradeNo(trade.outTradeNo,trade.tradeNo)
                        def tbo = TradeBase.findByRootIdAndTradeType(tb.rootId, 'payment')
                        def tp = TradePayment.findById(tbo.id)
                    %>
                    <tr>
                        <td>${trade.uploadTime.format("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>${trade.outTradeNo}</td>
                        <td>${trade.tradeNo}</td>
                        <td><g:formatNumber currencyCode="CNY" number="${tb.amount/100}" type="currency"/></td>
                        <td><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/></td>
                        <td>
                            <g:if test="${session.level3Map != null && session.level3Map['trade/refundAuthDetail'] != null}">
                                <a href="${request.contextPath}/${session.level3Map['trade/refundAuthDetail'].modelPath}/${trade.id}" class="gridBtn_normal">详细</a>
                            </g:if>
                        </td>
                    </tr>
                </g:each>

                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                <g:pageNavi total="${tradeListTotal}"/>
            </div>
        </div>
    </div>
    <!--搜索结果结束-->
</div>
<!--内容区结束-->
</form>

<script type="text/javascript">
    $(document).ready(function() {
        jQuery.validator.addMethod("money", function(a, b) {
            return this.optional(b) || /^\d+(\.\d{0,2})?$/i.test(a)
        }, "Please enter a valid amount.");
        jQuery.validator.addMethod("ge", function(value, element, param) {
            var target = $(param).unbind(".validate-equalTo").bind("blur.validate-equalTo", function() {
                $(element).valid();
            });
            if (target.val() == "" || value == "") return true;
            else return parseFloat(value) >= parseFloat(target.val());
        }, "Please enter a valid value.");
        jQuery.validator.addMethod("compareDate", function(value, element, param) {
            var startDate = jQuery(param).val();
            if (startDate == "" || value == "") {
                return true;
            } else {
                var date1 = new Date(Date.parse(startDate.replaceAll("-", "/")));
                var date2 = new Date(Date.parse(value.replaceAll("-", "/")));
                return date1 <= date2;
            }
        }, "Please enter a valid value.");
        $("#searchform").validate({
            rules: {
                amountMin:{money:true,range:[0.01,100000000]},
                amountMax:{money:true,range:[0.01,100000000],ge:"#amountMin"},
                startDate:{dateISO:true},
                endDate:{dateISO:true,compareDate:"#startDate"}
            },
            messages: {
                amountMin:{money:"无效金额",range:"无效金额范围"},
                amountMax:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
                startDate:{dateISO:"无效时间格式"},
                endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期"}
            }
        });
    });
</script>
</body>
</html>
