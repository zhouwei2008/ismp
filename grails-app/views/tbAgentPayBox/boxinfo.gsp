<%@ page import="dsf.TbAgentpayInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批量提交</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-1.8.2.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-1.9.0.custom.min.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
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

<form action="boxinfo" method="post" name="searchform" id="searchform">
<!--内容区开始-->
<div class="InContent">
    <div class="searchForm">
        <h1>批量提交</h1>
        <div class="searchContent">
            <ul class="formContent clearFloat">

                <li>
                    <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"/>
                </li>
                <li>
                    <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"/>
                </li>
                <li>
                    <label class="txtRight">上传文件名:</label><input type="text" name="filename" id="filename" maxlength="40" value="${params.filename}"/>
                </li>
                <li>
                    <label class="txtRight">批次交易号:</label><input type="text" name="tradeNo" id="tradeNo" maxlength="40" value="${params.tradeNo}"/>
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
                    <td>批次交易号</td>
                    <td class="longData">创建日期</td>
                    <td>上传文件名</td>
                    <td class="shortNum">笔数</td>
                    <td class="money">金额(元)</td>
                    <td>交易类型</td>
                    <td class="btnArea">操作</td>
                    <td >确认提交</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${tradeList}" status="i" var="trade">
                    <tr>
                        <td>${trade.id}</td>
                        <td>${trade.batchDate}</td>
                        <td>${trade.batchFilename}</td>
                        <td>${trade.batchCount}</td>
                        <td><g:formatNumber currencyCode="CNY" number="${trade.batchAmount}" type="currency"/></td>
                        <td>${trade.batchType=='F'?"代付":"代收"}</td>
                        <td>
                            <a href="${createLink(controller:'tbAgentPayBox',action:'boxlist')}/${trade.id}"  class="gridBtn_normal">详细</a>
                            |
                            <a href="${createLink(controller:'tbAgentPayBox',action:'boxdel')}/${trade.id}" onclick="return confirm('您确认执行删除操作吗？')" class="gridBtn_normal">删除</a>
                        </td>
                        <td>
                            <a href="${createLink(controller:'tbAgentPayBox',action:'boxsubmit')}/${trade.id}" onclick="return confirm('您确认执行提交操作吗？')" class="gridBtn_normal">提交</a>
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


    $(document).ready(function() {
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
        f.action = "boxinfo";
        f.method = "post";
    };

</script>
</body>
</html>
