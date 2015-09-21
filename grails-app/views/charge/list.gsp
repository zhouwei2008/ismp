<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-充提记录</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
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
<div class="trnmenu"> <span class="left trnmenutlt">交易管理：</span>
  <div class="rtnms">
    <ul>
        <g:if test="${session.level3Map != null && session.level3Map['charge/index'] != null}">
            <li>
                <a href="${request.contextPath}/${session.level3Map['charge/index'].modelPath}">充值</a>
            </li>
        </g:if>
        <li class="rtncnt blue">充值记录</li>
        %{--<g:if test="${session.level3Map != null && session.level3Map['withdraw/index'] != null}">--}%
            %{--<li>--}%
                %{--<a href="${request.contextPath}/${session.level3Map['withdraw/index'].modelPath}">提现</a>--}%
            %{--</li>--}%
        %{--</g:if>--}%
        %{--<g:if test="${session.level3Map != null && session.level3Map['withdraw/list'] != null}">--}%
            %{--<li>--}%
                %{--<a href="${request.contextPath}/${session.level3Map['withdraw/list'].modelPath}">提现记录</a>--}%
            %{--</li>--}%
        %{--</g:if>--}%
    </ul>
  </div>
</div>
<form action="list" method="post" name="searchform" id="searchform" style="width:960px; margin:0 auto">
    <div class="serch">
      <p>搜索</p>
      <table class="serchtlb">
      <tr>
        <td scope="col" colspan="2">&nbsp;&nbsp;开始日期:
            <g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10"/>
            &nbsp;&nbsp;结束日期:
            <g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10"/>
        </td>
        <td scope="col"></td>
        <td scope="col">
            <input type="submit" class="serchbtn" value="搜索" />
        </td>
        <td scope="col"></td>
        <td scope="col"></td>
      </tr>
    </table>
  </div>

    <div class="serchlitst">

        <table class="tlb1">
          <tr>
            <th class="txtCenter" scope="col">流水号</th>
            <th class="txtCenter" scope="col">充值时间</th>
            <th class="txtCenter" scope="col">充值金额（元）</th>
            <th class="txtCenter" scope="col">状态</th>
          </tr>
            <g:each in="${tradeList}" status="i" var="trade">
                <tr>
                    <td scope="col" class="txtCenter">${trade.tradeNo}</td>
                    <td scope="col" class="txtCenter">${trade.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                    <td class="txtCenter" scope="col"><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/></strong></td>
                    <td scope="col" class="txtCenter">${trade.statusMap[trade.status]}</td>
                </tr>
            </g:each>
        </table>
        <div class="page">
          <g:pageNavi total="${tradeListTotal}"/>
          </div>
    </div>
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