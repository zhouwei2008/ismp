<%@ page import="dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批次明细</title>
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
        function search(obj){
            if(obj){
                if($("offset"))
                    $("offset").value=0;
            }
            $("#searchform").submit();
        }
    </script>
</head>
<body>

<g:form action="detaillist" method="post" name="searchform">
    <g:hiddenField name="format"/>
    <input name="id" type="hidden" id="id" value="${params.id}"/>
    <input name="tradeflag" type="hidden" id="tradeflag" value="${params.tradeflag}"/>
<!--内容区开始-->
<div class="InContent">
    <div class="searchForm">
        <h1>批次明细</h1>
        <div class="searchContent">
            <ul class="formContent clearFloat">
                <li>
                    <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"  />
                </li>
                <li>
                    <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"  />
                </li>
                <li>
                    <label class="txtRight">批次交易号:</label><input name="tradeNo" type="text" id="tradeNo" value="${params.tradeNo}" maxlength="80"/>
                </li>
                <li>
                     <label class="txtRight">收/付款人:</label><input name="tradeCardname" type="text" id="tradeCardname" value="${params.tradeCardname}" maxlength="40"/>
                </li>
                <li>
                    <label class="txtRight">开户行:</label><input name="tradeAccountname" type="text" id="tradeAccountname" value="${params.tradeAccountname}" maxlength="80"/>
                </li>
                <li>
                    <label class="txtRight">状态:</label><g:select name="tradeStatus" value="${params.tradeStatus}" from="${TbAgentpayDetailsInfo.tradeStatusMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" />
                </li>
                <li>
                    <label class="txtRight">交易状态:</label><g:select name="tradeFeedbackcode" value="${params.tradeFeedbackcode}" from="${['成功','失败']}" noSelection="${['':'-请选择-']}" />
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
                    <td>流水号</td>
                    <td class="orderNum">交易号</td>
                    <td>客户账户</td>
                    <td class="money">交易金额（元）</td>
                    <td>帐户类型</td>
                    <td>收/付款人</td>
                    <td>证件类型</td>
                    <td>证件号</td>
                    <td>手机号</td>
                    <td>状态</td>
                    <td>用户协议号</td>
                    <td>商户订单号</td>
                    <td>备注</td>
                    %{--<td>交易状态</td>--}%
                    %{--<td>反馈原因</td>--}%
                    %{--<td>完成时间</td>--}%
                </tr>
                </thead>
                <tbody>
                <g:each in="${tradeList}" status="i" var="trade">
                    <tr style="height:80px;">
                        <td>${trade.tradeSubdate.format("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>${trade.tradeNum}</td>
                        <td>${trade.id}</td>
                        <td>${trade.tradeAccountname}<br>${trade.tradeBranchbank},${trade.tradeSubbranchbank}<br>${trade.tradeCardnum}<br>${trade.tradeProvince},${trade.tradeCity}</td>
                        <td><g:formatNumber currencyCode="CNY" number="${trade.tradeAmount}" type="currency"/></td>
                        <td>${trade.accountTypeMap[trade.tradeAccounttype]}</td>
                        <td>${trade.tradeCardname}</td>
                        <td>${trade.certificateTypeMap[trade.certificateType]}</td>
                        <td>${trade.certificateNum}</td>
                        <td>${trade.tradeMobile}</td>
                        <td>${trade.tradeStatusMap[trade.tradeStatus]}</td>
                        <td>${trade.contractUsercode}</td>
                        <td>${trade.tradeCustorder}</td>
                        <td>${trade.tradeRemark}</td>
                        %{--<g:if test="${tradeflag==0}" >--}%
                        %{--<td>${trade.tradeFeedbackcode}</td>--}%
                        %{--<td>${trade.tradeReason}</td>--}%
                        %{--<td>${trade.tradeDonedate?trade.tradeDonedate.format("yyyy-MM-dd HH:mm:ss"):""}</td>--}%
                        %{--</g:if>--}%
                    </tr>
                </g:each>
                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                <g:if test="${tradeListTotal>0}">
                    <a href="#" class="downCount" id="download-txt">下载TXT</a>
                    <a href="#" class="downCount" id="download-exc">下载CSV</a>
                    <a href="#" class="downCount" id="download-exc">下载Excel</a>
                </g:if>
                <g:pageNavi total="${tradeListTotal}"/>
            </div>
        </div>
    </div>
    <!--搜索结果结束-->

</div>
<!--内容区结束-->
</g:form>

<script type="text/javascript">
    $(function() {
        $("#tradeCardname").val("");
        $("#tradeAccountname").val("");
        $("#tradeFeedbackcode").val("");
        $("#tradeStatus").val("");
    });

    $(document).ready(function() {

        $("#serch").click(function(){

            $("#tradeCardname").val("");
            $("#tradeAccountname").val("");
            $("#tradeFeedbackcode").val("");
            $("#tradeStatus").val("");
        });

        jQuery.validator.addMethod("money",function(a,b){return this.optional(b)||/^\d+(\.\d{0,2})?$/i.test(a)},"Please enter a valid amount.");
        jQuery.validator.addMethod("ge", function(value, element, param) {
            var target = $(param).unbind(".validate-equalTo").bind("blur.validate-equalTo", function() {
                $(element).valid();
            });
            if(target.val()==""||value=="") return true;
            else return parseFloat(value) >= parseFloat(target.val());
        }, "Please enter a valid value.");
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
        $("#searchform").validate({
            rules: {
                amountMin:{money:true,range:[0.01,100000000]},
                amountMax:{money:true,range:[0.01,100000000],ge:"#amountMin"},
                startDate:{dateISO:true},
                endDate:{dateISO:true,compareDate:"#startDate"}
            },
            messages: {
//			amountMin:{money:"无效金额",range:"无效金额范围"},
//			amountMax:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
                startDate:{dateISO:"无效时间格式"},
                endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期"}
            }
        });
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
        download("xls");
        E.preventDefault(e);
    });
    function download(type){
        var f = D.get("searchform");
        $("#format").val(type)
        f.submit();
        $("#format").val("")
    };
</script>
</body>
</html>
