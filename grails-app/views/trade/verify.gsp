<%@ page import="ismp.TradePayment; ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-退款审核</title>
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

        javascript:window.history.forward(1);
        function KeyDown(){
            if   ((event.keyCode==8)&&
                    (event.srcElement.type!="text"&&event.srcElement.type!="textarea"&&event.srcElement.type!="password")){
                event.keyCode=0;
                event.returnvalue=false;
            }
        }

        function search(obj) {
            if (obj) {
                if ($("offset"))
                    $("offset").value = 0;
            }
            $("#searchform").submit();
        }
        function checkAllBox() {
            var len = document.getElementsByName("box").length;
            for (i = 0; i < len; i++) {
                if (document.getElementById("allBox").checked) {
                    document.getElementsByName("box")[i].checked = true;
                }
                else {
                    document.getElementsByName("box")[i].checked = false;
                }
            }
        }

        function selectCheck() {
            var len = document.getElementsByName("box").length;
            var ids="";
            var flag = 0;
            for (i = 0; i < len; i++) {
                if (document.getElementsByName("box")[i].checked) {
                    ids=ids+document.getElementsByName("box")[i].value+",";
                    flag = 1;
                }
            }

            document.getElementById("ids").value=ids;

            if (flag == 0) {
                alert("请选择要审核的退款！");
                return false;
            }
            else {
                window.location.href='${createLink(controller:'trade',action:'refundPass')}'+ '?ids='+ids ;
            }
        }

        function refundRefuse(){
            var len = document.getElementsByName("box").length;
            var ids="";
            var flag = 0;
            for (i = 0; i < len; i++) {
                if (document.getElementsByName("box")[i].checked) {
                    ids=ids+document.getElementsByName("box")[i].value+",";
                    flag = 1;
                }
            }
            document.getElementById("ids").value=ids;

            if (flag == 0) {
                alert("请选择要审核的退款！");
                return false;
            }else {
                appPass();
            }

        }

        function appPass() {
            document.getElementById("reason").style.display = '';
            return false;
        }
        function checkReason() {
            var len = document.getElementsByName("box").length;
            var ids="";
            var flag = 0;
            for (i = 0; i < len; i++) {
                if (document.getElementsByName("box")[i].checked) {
                    ids=ids+document.getElementsByName("box")[i].value+",";
                    flag = 1;
                }
            }
            document.getElementById("ids").value=ids;

            if (document.getElementById("note").value == '请输入拒绝原因' || document.getElementById("note").value == '') {
                alert("请输入拒绝原因!");
            } else {
                var reason = document.getElementById("note").value;
                document.forms[0].action="${createLink(controller:'trade',action:'refundRefuse')}"
                document.forms[0].ids.value=ids;
                document.forms[0].submit();
                //window.location.href='${createLink(controller:'trade',action:'refundRefuse')}'+ '?ids='+ids +'&note=' + reason;
            }
        }
    </script>
</head>
<body>
</div>

<g:form action="verify" method="post" name="searchform" id="searchform" >
    <g:hiddenField name="ids" id="ids"/>
    <g:hiddenField name="batch" value="${flash.message}"/>
    <!--内容区开始-->
    <div class="InContent">
        <div class="searchForm">
            <h1>退款审核</h1>
            <div class="searchContent">
                <ul class="formContent clearFloat">

                    <li>
                        <label class="txtRight">平台交易号:</label><input name="tradeNo" type="text" id="tradeNo" value="${params.tradeNo}" maxlength="40"/>
                    </li>
                    <li>
                        <label class="txtRight">商户订单号:</label><input name="outTradeNo" type="text" id="outTradeNo" value="${params.outTradeNo}" maxlength="80"/>
                    </li>
                    <li>
                        <label class="txtRight">金额区间:</label><input type="text" name="amountMin" id="amountMin" maxlength="10" value="${params.amountMin}" class="w70"/><span>-</span><input type="text" name="amountMax" id="amountMax" maxlength="10" value="${params.amountMax}" class="w70"/>元
                    </li>
                    <li>
                        <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"/>
                    </li>
                    <li>
                        <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"/>
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
                        <td class="orderNum">商户订单号</td>
                        <td class="trdeNum">平台交易号</td>
                        <td class="money">交易金额（元）</td>
                        <td class="money">退款金额（元）</td>
                        <td class="btnArea">全选<input type="checkbox" id="allBox" name="allBox" onclick="checkAllBox();"></td>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tradeList}" status="i" var="trade">
                        <%
                            def tb = TradeBase.findByOutTradeNoAndTradeNo(trade.outTradeNo,trade.tradeNo)
                        %>
                        <tr>
                            <td class="blue">${trade.uploadTime.format("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${trade.outTradeNo}</td>
                            <td>${trade.tradeNo}</td>
                            <td><strong style="color:green"><g:formatNumber currencyCode="CNY" number="${tb.amount/100}" type="currency"/></strong></td>
                            <td><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/></strong></td>
                            <td><g:checkBox name="box" value="${trade.id}" checked="false"  ></g:checkBox></td>
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
        <g:if test="${tradeListTotal>0}">
            <table>
                <tr>
                    <g:if test="${session.level3Map != null && session.level3Map['trade/refundPass'] != null}">
                            <input type="button" onclick="selectCheck();" class="btn-default" value="审核通过">
                    </g:if>
                    <g:if test="${session.level3Map != null && session.level3Map['trade/refundRefuse'] != null}">
                             <input type="button" onclick="refundRefuse();" class="btn-default" value="审核拒绝">
                    </g:if>
                </tr>
            </table>
        </g:if>

    </div>
    <!--内容区结束-->
</g:form>

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
                endDate:{dateISO:true,compareDate:"#startDate",rangeDate:"#startDate"}
            },
            messages: {
                amountMin:{money:"无效金额",range:"无效金额范围"},
                amountMax:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
                startDate:{dateISO:"无效时间格式"},
                endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期",rangeDate:"日期范围必须3个月内"}
            }
        });
    });
</script>
</body>
</html>
