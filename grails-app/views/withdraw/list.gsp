<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-提现记录</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    %{--<script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>--}%
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
<form action="list" method="post" name="searchform" id="searchform">
<!--内容区开始-->
<div class="InContent">
    <div class="searchForm">
        <h1>提现记录</h1>
        <div class="searchContent">
            <ul class="formContent clearFloat">
                <li>
                    <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"  />
                </li>
                <li>
                    <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"  />
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
                        <td>流水号</td>
                        <td>提现时间</td>
                        <td class="money">提现金额(元)</td>
                        <td class="btnArea">状态</td>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${tradeList}" status="i" var="trade">
                    <tr>
                        <td>${trade.tradeNo}</td>
                        <td>${trade.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                        <td><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/></strong></td>
                        <td>${trade.handleStatusMap[trade.handleStatus]}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                <div class="pagination">
                    <g:pageNavi total="${tradeListTotal}"/>
                </div>
            </div>
        </div>
    </div>
    <!--搜索结果结束-->
</div>
<!--内容区结束-->
</form>
<script type="text/javascript">

    $(function() {
        var dates=new Date();
        var diff=dates.setYear(dates.getFullYear()-2);
        var yearsday=(new Date()-diff)/86400000;
        $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -yearsday, maxDate: "+0D" });
        $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -yearsday, maxDate: "+0D" });
    });

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
                startDate:{dateISO:true},
                endDate:{dateISO:true,compareDate:"#startDate",rangeDate:"#startDate"}
            },
            messages: {
                startDate:{dateISO:"无效时间格式"},
                endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期",rangeDate:"日期范围必须3个月内"}
            }
        });
    });
</script>
</body>
</html>
