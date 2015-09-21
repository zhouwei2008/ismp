<%@ page import="ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-出入金交易</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-1.8.2.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-1.9.0.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-timepicker-addon-chn.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>

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
<div class="trnmenu"> <span class="left trnmenutlt">出入金查询：</span>
  <div class="rtnms">
    <ul><li>出入金查询</li></ul>
  </div>
</div>
<form action="list" method="post" name="searchform" id="searchform" style="width:960px; margin:0 auto">
    <div class="serch">
      <p>搜索</p>
      <table class="serchtlb">
          <tr>
            <td width="252" scope="col">&nbsp;&nbsp;开始日期:
                <g:textField name="startDate" value="${params.startDate}" readonly="readonly"/>
            </td>
            <td width="252" scope="col">&nbsp;&nbsp;结束日期:
                <g:textField name="endDate" value="${params.endDate}" readonly="readonly"/>
            </td>
          </tr>
          <tr>
             <td width="252" scope="col">&nbsp;&nbsp;&nbsp;&nbsp;合同号:
              <input name="contractno" type="text" id="contractno" value="${params.contractno}" maxlength="80"/>
            </td>
               <td width="252" scope="col">交易流水号:
              <input name="ordernum" type="text" id="ordernum" value="${params.ordernum}" maxlength="80"/>
            </td>
          </tr>
          <tr>
               <td width="252" scope="col">&nbsp;&nbsp;会员名称:
              <input name="name" type="text" id="name" value="${params.name}" maxlength="80"/>
            </td>
             <td width="252" scope="col">&nbsp;&nbsp;&nbsp;&nbsp;席位号:
              <input name="no" type="text" id="no" value="${params.no}" maxlength="80"/>
            </td>
             <td width="252" scope="col">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资金类别:
              <g:select name="tradetype" value="${params.tradetype}" from="${ismp.TradeBase.paymentTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}"/>
            </td>
            <td scope="col">
                <input type="submit" class="serchbtn" value="搜索" />
            </td>
          </tr>
        </table>
    </div>

    <div class="serchlitst">

        <table class="tlb1">
          <tr>
            <th class="txtCenter" scope="col">合同号</th>
            <th class="txtCenter" scope="col">交易日期</th>
            <th class="txtCenter" scope="col">交易时间</th>
            <th class="txtCenter" scope="col">交易流水号</th>
              <th class="txtCenter" scope="col">账户余额（元）</th>
            <th class="txtCenter" scope="col">交易金额（元）</th>

            <th class="txtCenter" scope="col">会员名称</th>
            <th class="txtCenter" scope="col">会员席位号</th>
            <th class="txtCenter" scope="col">资金类别</th>
          </tr>
            <g:each in="${outAndInList}" status="i" var="outAndIn">
                <tr>
                    <td class="txtCenter" scope="col">${outAndIn.contractno}</td>
                    <td class="txtCenter" scope="col">${outAndIn.tradetime.format("yyyy-MM-dd")}</td>
                    <td scope="col" class="blue">${outAndIn.tradetime.format("HH:mm:ss")}</td>
                    <td class="txtCenter" scope="col">${outAndIn.ordernum}</td>
                    <td class="txtCenter" scope="col"><strong style="color:red"><g:if test="${outAndIn.balance != null && outAndIn.balance != ''}">￥${Double.parseDouble(outAndIn.balance)/100}</g:if><g:else>￥0.00</g:else></strong></td>
                    <td class="txtCenter" scope="col"><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${outAndIn.amount/100}" type="currency"/></strong></td>
                    <td class="txtCenter" scope="col">${outAndIn.name}</td>
                    <td class="txtCenter" scope="col">${outAndIn.no}</td>
                    <td class="txtCenter" scope="col"><g:if test="${outAndIn.tradetype != null && outAndIn.tradetype == 'in'}">入金</g:if><g:elseif test="${outAndIn.tradetype != null && outAndIn.tradetype == 'out'}">出金</g:elseif><g:elseif test="${outAndIn.tradetype != null && outAndIn.tradetype == 'fee'}">手续费</g:elseif><g:elseif test="${outAndIn.tradetype != null && outAndIn.tradetype == 'pay'}">支付</g:elseif><g:elseif test="${outAndIn.tradetype != null && outAndIn.tradetype == 'get'}">收款</g:elseif></td>
                </tr>
            </g:each>
        </table>
         <div  align="left">总计 ：&nbsp;&nbsp;出金总金额：<strong style="color:red"><g:if test="${outListCount != null && outListCount != ''}">${outListCount/100}</g:if><g:else>0.00</g:else></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入金总金额：<strong style="color:red"><g:if test="${inListCount != null && inListCount != ''}">${inListCount/100}</g:if><g:else>0.00</g:else></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手续费总金额：<strong style="color:red"><g:if test="${feeListCount != null && feeListCount != ''}">${feeListCount/100}</g:if><g:else>0.00</g:else></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付总金额：<strong style="color:red"><g:if test="${payListCount != null && payListCount != ''}">${payListCount/100}</g:if><g:else>0.00</g:else></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收款总金额：<strong style="color:red"><g:if test="${getListCount != null && getListCount != ''}">${getListCount/100}</g:if><g:else>0.00</g:else></strong></div>
        <div class="page">
            <g:if test="${outAndInListTotal>0}">
                <div class="elxdwn blue"><a href="#" class="download-excel">下载统计结果</a></div>
            </g:if>
          <g:pageNavi total="${outAndInListTotal}"/>
          </div>
    </div>
</form>

<script type="text/javascript">

    $(function() {
        var dates = new Date();
        $('#startDate').datetimepicker({
            showSecond: true,
            timeFormat: 'hh:mm:ss',
            stepHour: 2,
            stepMinute: 10,
            stepSecond: 10,
            maxDateTime:dates
        });
        $('#endDate').datetimepicker({
            showSecond: true,
            timeFormat: 'hh:mm:ss',
            stepHour: 2,
            stepMinute: 10,
            stepSecond: 10,
            maxDateTime:dates
        });

        $("#tradeNo").val("");
        $("#status").val("");
        $("#tradeOpp").val("");
        $("#subject").val("");
        $("#tradeType").val("");
        $("#amountMin").val("");
        $("#amountMax").val("");
    });

    $(document).ready(function() {

      $("#serch").click(function(){
        $("#tradeNo").val("");
        $("#status").val("");
        $("#tradeOpp").val("");
        $("#subject").val("");
        $("#tradeType").val("");
        $("#amountMin").val("");
        $("#amountMax").val("");
      });
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
                amountMin:{money:true,range:[0.01,100000000]},
                amountMax:{money:true,range:[0.01,100000000],ge:"#amountMin"},
                endDate:{compareDate:"#startDate",rangeDate:"#startDate"}
            },
            messages: {
                amountMin:{money:"无效金额",range:"无效金额范围"},
                amountMax:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
                endDate:{compareDate:"结束日期必须大于开始日期",rangeDate:"日期范围必须3个月内"}
            }
        });
    });

    //----------下载部分处理-------
    E.on(D.query(".download-exc"), "click", function(e) {
        download("csv");
        E.preventDefault(e);
    });
    E.on(D.query(".download-txt"), "click", function(e) {
        download("txt");
        E.preventDefault(e);
    });
    E.on(D.query(".download-excel"), "click", function(e) {
        download("excel");
        E.preventDefault(e);
    });

    function download(type) {
        var f = D.get("searchform");
        f.action = f.action + "?format=" + type;
        f.submit();
        f.action = "list";
        f.method = "post";
    }

</script>
</body>
</html>
