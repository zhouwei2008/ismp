<%@ page import="ismp.TradePayment; ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-收支明细</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-1.8.2.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-1.9.0.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-timepicker-addon-chn.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
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
<form action="account" method="post" name="searchform" id="searchform">
    <g:hiddenField name="ids" id="ids"/>
    <g:hiddenField name="batch" value="${flash.message}"/>
    <!--内容区开始-->
    <div class="InContent">
        <div class="searchForm">
            <h1>收支明细</h1>
            <div class="searchContent">
                <ul class="formContent clearFloat">

                    <li class="all">
                        <label class="txtRight">资金流向:</label>
                        <input type="radio" class="inputRadio" name="direction" value="in" id="account-in" <g:if test="${params.direction=='in'||actionName=='accountin'}">checked</g:if>/>收入&nbsp;
                        <input type="radio" class="inputRadio" name="direction" value="out" id="account-out" <g:if test="${params.direction=='out'||actionName=='accountout'}">checked</g:if>/>支出&nbsp;
                        <input type="radio" class="inputRadio" name="direction" value="all" id="account-all" <g:if test="${(params.direction==null||params.direction=='all')&&actionName=='account'}">checked</g:if>/>所有
                    </li>

                    <li>
                        <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"/>
                    </li>
                    <li>
                        <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"/>
                    </li>

                    <li class="all">
                        <label class="txtRight">类型:</label>
                        <input type="checkbox" class="inputRadio" name="tradeType" value="typeAll" id="typeAll" <g:if test="${params.tradeType=='typeAll'||params.tradeType==null}">checked</g:if>/>所有
                        <input type="checkbox" class="inputRadio" name="tradeType" value="charge" id="charge" <g:if test="${'charge' in params.tradeType}">checked</g:if>/>充值
                        <input type="checkbox" class="inputRadio" name="tradeType" value="withdrawn" id="withdrawn" <g:if test="${'withdrawn' in params.tradeType}">checked</g:if>/>提现
                        <input type="checkbox" class="inputRadio" name="tradeType" value="payment" id="payment" <g:if test="${'payment' in params.tradeType}">checked</g:if>/>支付
                        <input type="checkbox" class="inputRadio" name="tradeType" value="fee" id="fee" <g:if test="${'fee' in params.tradeType}">checked</g:if>/>手续费
                        <input type="checkbox" class="inputRadio" name="tradeType" value="transfer" id="transfer" <g:if test="${'transfer' in params.tradeType}">checked</g:if>/>转账
                        <input type="checkbox" class="inputRadio" name="tradeType" value="refund" id="refund" <g:if test="${'refund' in params.tradeType}">checked</g:if>/>退款
                        <input type="checkbox" class="inputRadio" name="tradeType" value="agentpay" id="agentpay" <g:if test="${'agentpay' in params.tradeType}">checked</g:if>/>代付
                        <input type="checkbox" class="inputRadio" name="tradeType" value="settle" id="settle" <g:if test="${'settle' in params.tradeType}">checked</g:if>/>结算
                        <input type="checkbox" class="inputRadio" name="tradeType" value="adjust" id="adjust" <g:if test="${'adjust' in params.tradeType}">checked</g:if>/>调账
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
                        <td class="trdeNum">流水号</td>
                        <td>类型</td>
                        <td class="money">收入(元)</td>
                        <td class="money">支出(元)</td>
                        <td class="money">账户余额(元)</td>
                        <td>摘要</td>
                        <td class="btnArea">详细</td>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${acSeqList}" status="i" var="acSeq">
                        <tr>
                            <td>${acSeq.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${acSeq.transaction?.outTradeNo == '0'?'':acSeq.transaction?.outTradeNo}</td>
                            <td>${acSeq.transaction.tradeNo}</td>
                            <td>${acSeq.transaction.transTypeMap[acSeq.transaction.transferType]}</td>
                            <td><g:if test="${acSeq.debitAmount>0}">+<g:formatNumber currencyCode="CNY" number="${acSeq.debitAmount/100}" type="currency"/></g:if></td>
                            <td><g:if test="${acSeq.creditAmount>0}">-<g:formatNumber currencyCode="CNY" number="${acSeq.creditAmount/100}" type="currency"/></g:if></td>
                            <td><g:formatNumber currencyCode="CNY" number="${acSeq.balance/100}" type="currency"/></td>
                            <td>${acSeq.transaction.subjict == 'null' ? '' : acSeq.transaction.subjict?.encodeAsHTML()}</td>
                            <td>
                                <g:if test="${session.level3Map != null && session.level3Map['trade/accdetail'] != null}">
                                    <a href="${request.contextPath}/${session.level3Map['trade/accdetail'].modelPath}/${acSeq.id}" class="gridBtn_normal">查看</a>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="tableFoot clearFloat">
                    <g:if test="${acSeqListTotal>0}">
                        <a href="#" class="downCount" id="download-txt">下载TXT</a>
                        <a href="#" class="downCount" id="download-exc">下载CSV</a>
                        <a href="#" class="downCount" id="download-excel">下载Excel</a>
                    </g:if>
                    <g:pageNavi total="${acSeqListTotal}"/>
                </div>
            </div>
        </div>
        <!--搜索结果结束-->
    </div>
    <!--内容区结束-->
</form>

<script type="text/javascript">

    $(document).ready(function() {
        jQuery.validator.addMethod("compareDate", function(value, element, param) {
            var startDate = jQuery(param).val();
            if(startDate==""||value=="")
            {
                return true;
            }else{
                var date1 = new Date(Date.parse(startDate.replaceAll("-", "/")));
                var date2 = new Date(Date.parse(value.replaceAll("-", "/")));
                return date1 <= date2;
            }
        }, "Please enter a valid value.");
        jQuery.validator.addMethod("rangeDate", function(value, element, param) {
            var startDate = jQuery(param).val();
            if(startDate==""||value=="")
            {
                return true;
            }else{
                var date1 = new Date(Date.parse(startDate.replaceAll("-", "/")));
                var date3  =date1.setMonth(date1.getMonth()+3);
                var date2 = new Date(Date.parse(value.replaceAll("-", "/")));
                return (date2-date3)<= 0;
            }
        }, "Please enter 90 days range value.");
        $("#searchform").validate({
            rules: {
                endDate:{compareDate:"#startDate",rangeDate:"#startDate"}
            },
            messages: {
                endDate:{compareDate:"结束日期必须大于开始日期",rangeDate:"日期范围必须3个月内"}
            }
        });

        /*
         * 不可选与可选
         * disabl
         * Id：    DOMID
         * check： 1."checked"为选中 2."unchecked"为不选中
         * disabl： 1."disabl"不可操作 2."undisabl"可操作
         */
        function disabl(Id, check, disabl) {
            if (check == "checked") {
                D.get(Id).checked = true;
            } else if (check == "unchecked") {
                D.get(Id).checked = false;
            }
            if (disabl == "disabl") {
                D.get(Id).disabled = true;
                D.get(Id).readOnly = true;
            } else if (disabl == "undisabl") {
                D.get(Id).disabled = false;
                D.get(Id).readOnly = false;
            }
        }

        function checke() {
            if (D.get("account-in").checked) {
                disabl("charge", "null", "undisabl");
                disabl("withdrawn", "unchecked", "disabl");
            }
            if (D.get("account-out").checked) {
                disabl("charge", "unchecked", "disabl");
                disabl("withdrawn", "null", "undisabl");
            }
            if (D.get("account-all").checked) {
                disabl("charge", "null", "undisabl");
                disabl("withdrawn", "null", "undisabl");
            }
        }

        E.on(D.get("account-in"), "click", function(e) {
            checke();
        });

        E.on(D.get("account-out"), "click", function(e) {
            checke();
        });

        E.on(D.get("account-all"), "click", function(e) {
            checke();
        });
        checke();

        E.on(D.get("typeAll"), "click", function(e) {
            if (D.get("typeAll").checked) {
                disabl("charge", "unchecked", "null");//充值
                disabl("withdrawn", "unchecked", "null");//提现
                disabl("transfer", "unchecked", "null");//转账
                disabl("payment", "unchecked", "null");//支付
                disabl("fee", "unchecked", "null");//服务费
//                disabl("fee_rfd", "unchecked", "null");//服务费
//                disabl("royalty", "unchecked", "null");//服务费
//                disabl("royalty_rfd", "unchecked", "null");//服务费
                disabl("refund", "unchecked", "null");//服务费
//                disabl("frozen", "unchecked", "null");//服务费
//                disabl("unfrozen", "unchecked", "null");//服务费
                disabl("agentpay", "unchecked", "null");//服务费
//                disabl("agentcoll", "unchecked", "null");//服务费
                disabl("settle", "unchecked", "null");//服务费
                disabl("adjust", "unchecked", "null");//服务费
            }
            checke();
        });
//        E.on([D.get("payment"), D.get("charge"), D.get("transfer"), D.get("withdrawn"), D.get("fee"), D.get("refund"), D.get("refund_rfd"), D.get("fee_rfd"), D.get("frozen"), D.get("unfrozen"), D.get("royalty"),D.get("royalty_rfd"),D.get("agentpay"),D.get("agentcoll")], "click", function(e) {
        E.on([D.get("payment"), D.get("charge"), D.get("transfer"), D.get("withdrawn"), D.get("fee"), D.get("refund"), D.get("refund_rfd"),D.get("agentpay"),D.get("adjust")], "click", function(e) {
            disabl("typeAll", "unchecked", "null");//所有
        });

        //----------下载部分处理-------
        E.on(D.get("download-exc"),"click",function(e){
            download("csv");
            E.preventDefault(e);
        });
        E.on(D.get("download-txt"),"click",function(e){
            download("txt");
            E.preventDefault(e);
        });
        E.on(D.get("download-excel"),"click",function(e){
            download("excel");
            E.preventDefault(e);
        });
        function download(type){
            var f = D.get("searchform");
            f.action=f.action+"?format="+type;
            f.submit();
            f.action = "account";
            f.method = "post";
        };

    });
</script>
</body>
</html>
