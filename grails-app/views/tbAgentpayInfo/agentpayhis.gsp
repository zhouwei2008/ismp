<%@ page import="dsf.TbAgentpayInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-代付交易</title>
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

<form action="agentpayhis" method="post" name="searchform" id="searchform">
<!--内容区开始-->
<div class="InContent">
    <div class="searchForm">
        <h1>代付交易</h1>
        <div class="searchContent">
            <ul class="formContent clearFloat">


                <li>
                    <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"/>
                </li>
                <li>
                    <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"/>
                </li>
                <li>
                    <label class="txtRight">上传文件名:</label><input name="filename" type="text" id="filename" value="${params.filename}" maxlength="80"/>
                </li>
                <li>
                    <label class="txtRight">批次交易号:</label><input name="tradeNo" type="text" id="tradeNo" value="${params.tradeNo}" maxlength="40"/>
                </li>
                <li>
                    <label class="txtRight">状态:</label><g:select name="batchStatus" value="${params.batchStatus}" from="${TbAgentpayInfo.statusMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}"/>
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
                    <td>类型</td>
                    <td class="longData">创建日期</td>
                    <td>批次交易号</td>
                    <td class="shortNum">总数</td>
                    <td class="money">总金额(元)</td>
                    <td>状态</td>
                    <td class="money">手续费</td>
                    <td>结算方式</td>
                    <td class="money">实付金额(元)</td>
                    <td>备注</td>
                    <td>上传文件名</td>
                    <td class="btnArea">操作</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${tradeList}" status="i" var="trade">
                    <tr>
                        <td>${trade.batchTypeMap[trade.batchType]}</td>
                        <td>${trade.batchDate}</td>
                        <td>${trade.id}</td>
                        <td>${trade.batchCount}</td>
                        <td><g:formatNumber currencyCode="CNY" number="${trade.batchAmount}" type="currency"/></td>
                        <td>${trade.statusMap[trade.batchStatus]}</td>
                        <td><g:formatNumber currencyCode="CNY" number="${trade.batchFee}" type="currency"/></td>
                        <td>${trade.feeTypeMap[trade.batchFeetype]}</td>
                        <td><g:formatNumber currencyCode="CNY" number="${trade.batchAccamount}" type="currency"/></td>
                        <td>${trade.batchRemark1}</td>
                        <td>${trade.batchFilename}</td>
                        <td>
                            <g:if test="${session.level3Map != null && session.level3Map['tbAgentpayInfo/detaillist'] != null}">
                                <a href="${request.contextPath}/${session.level3Map['tbAgentpayInfo/detaillist'].modelPath}?id=${trade.id}&tradeflag=1" class="gridBtn_normal">查看明细</a>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                    <g:if test="${tradeListTotal>0}">
                        <a href="#" class="downCount" id="download-txt">下载TXT</a>
                        <a href="#" class="downCount" id="download-exc">下载CSV</a>
                    </g:if>
                    <g:pageNavi total="${tradeListTotal}"/>
            </div>
        </div>
    </div>
    <!--搜索结果结束-->

</div>
<!--内容区结束-->
</form>

<script type="text/javascript">
    $(function() {

        $("#filename").val("");
        $("#batchStatus").val("");
    });

    $(document).ready(function() {

        $("#serch").click(function(){

            $("#filename").val("");
            $("#batchStatus").val("");
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
                amountMin:{money:"无效金额",range:"无效金额范围"},
                amountMax:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
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
    function download(type){
        var f = D.get("searchform");
        f.action=f.action+"?format="+type;
        f.submit();
        f.action = "agentpayhis";
        f.method = "post";
    };

</script>
</body>
</html>
