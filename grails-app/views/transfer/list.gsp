<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-转账</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
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
            <li>
                <a href="${request.contextPath}/transfer/index">单笔转账</a>
            </li>
            <li class="rtncnt blue">转账记录</li>
            %{--<li>--}%
                %{--<a href="${request.contextPath}/transfer/batchStep1"> 批量转账</a>--}%
            %{--</li>--}%
            <g:if test="${session.level3Map != null && session.level3Map['transfer/check'] != null}">
                <li>
                    <a href="${request.contextPath}/transfer/check"> 转账审核</a>
                </li>
            </g:if>
    </ul>
  </div>
</div>
<form action="list" method="post" name="searchform" id="searchform" style="width:960px; margin:0 auto">
    <div class="serch">
      <p>搜索</p>
          <table class="serchtlb">
          <tr>
            <td width="400" scope="col">&nbsp;&nbsp;&nbsp;&nbsp;开始日期:
                <g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10"/>
                &nbsp;&nbsp;结束日期:
                <g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10"/>
            </td>
            <td width="252" scope="col">转入账户:
              <input name="to_Account" type="text" id="to_Account" value="${params.to_Account}" maxlength="40"/>
            </td>
            <td width="100" scope="col">
                <input type="submit" class="serchbtn" value="搜索" />
            </td>
            <td scope="col" class="blue">
                <a href="#" id="serch">更多条件</a>
            </td>
          </tr>
        </table>
        <table class="serchtlb" style="display:none" id="serchmore">
          <tr>
            <td width="400" scope="col">&nbsp;&nbsp;&nbsp;
                <select name="flag" onChange="doSelectChange(this)">
                    <g:if test="${params.flag == '1'}">
                        <option value="1" selected>流水号</option>
                        <option value="2">商户订单号</option>
                    </g:if>
                    <g:else>
                        <option value="1">流水号</option>
                        <option value="2"selected>商户订单号</option>
                    </g:else>
                </select>
                <g:if test="${params.flag == '1'}">
                    <input name="flow_No" type="text" id="flow_No" value="${params.flow_No}" maxlength="80"/>
                    <input name="outTrade_No" type="text" id="outTrade_No" value="${params.outTrade_No}" maxlength="80" style="display:none"/>
                </g:if>
                <g:else>
                    <input name="flow_No" type="text" id="flow_No" value="${params.flow_No}" maxlength="80" style="display:none"/>
                    <input name="outTrade_No" type="text" id="outTrade_No" value="${params.outTrade_No}" maxlength="80"/>
                </g:else>
            </td>
            <td scope="col" colspan="3">金额区间:
              <input type="text" name="amt_s" id="amt_s" maxlength="10" value="${params.amt_s}"/>
              -<input type="text" name="amt_b" id="amt_b" maxlength="10" value="${params.amt_b}"/>元
            </td>
          </tr>
        </table>
    </div>

    <div class="serchlitst">

        <table class="tlb1">
          <tr>
            <g:if test="${params.flag == '1'}">
                <th class="txtCenter" scope="col">流水号</th>
            </g:if>
            <g:else>
                <th class="txtCenter" scope="col">商户订单号</th>
            </g:else>
            <th class="txtCenter" scope="col">转入账户</th>
            <th class="txtCenter" scope="col">转账时间</th>
            <th class="txtCenter" scope="col">金额（元）</th>
            <th class="txtCenter" scope="col">转帐原因</th>
            <th class="txtCenter" scope="col">客户备注</th>
            <th class="txtCenter" scope="col">审批状态</th>
            <th class="txtCenter" scope="col">转账状态</th>
            <th class="txtCenter" scope="col">操作</th>
          </tr>

          <g:if test="${params.flag == '1'}">
              <g:each in="${tradeList}" status="i" var="trade">
              <tr>
                <td class="txtCenter" scope="col">${trade.tradeNo}</td>
                <td class="txtCenter" scope="col">${trade.payeeCode}</td>
                <td scope="col" class="blue">${trade.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                <td class="txtCenter" scope="col"><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/></strong></td>
                <td class="txtCenter" scope="col">${trade.subject}</td>
                <td class="txtCenter" scope="col">${trade.note}</td>
                <td class="txtCenter" scope="col">
                    <g:if test="${trade?.transferModel=='open' && trade?.status=='starting'}">待审批</g:if>
                    <g:if test="${trade?.transferModel=='open' && trade?.status=='completed'}">审批完成</g:if>
                    <g:if test="${trade?.transferModel=='open' && trade?.status=='closed'}">审批拒绝</g:if>
                </td>
                <td class="txtCenter" scope="col">${trade.statusMap[trade.status]}</td>
                <td class="txtCenter" scope="col">
                    <a href="${request.contextPath}/transfer/detail/${trade.id}" style="color:blue">详细</a>
                </td>
              </tr>
              </g:each>
          </g:if>


          <g:else>
              <g:each in="${tradeList}" status="i" var="trade">
              <tr>
                <td class="txtCenter" scope="col">${trade.outTradeNo}</td>
                <td class="txtCenter" scope="col">${trade.payeeCode}</td>
                <td scope="col" class="blue">${trade.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                <td class="txtCenter" scope="col"><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/></strong></td>
                <td class="txtCenter" scope="col">${trade.subject}</td>
                <td class="txtCenter" scope="col">${trade.note}</td>
                <td class="txtCenter" scope="col">
                    <g:if test="${trade?.transferModel=='open' && trade?.status=='starting'}">待审批</g:if>
                    <g:if test="${trade?.transferModel=='open' && trade?.status=='completed'}">审批完成</g:if>
                    <g:if test="${trade?.transferModel=='open' && trade?.status=='closed'}">审批拒绝</g:if>
                </td>
                <td class="txtCenter" scope="col">${trade.statusMap[trade.status]}</td>
                <td class="txtCenter" scope="col">
                    <a href="${request.contextPath}/transfer/detail/${trade.id}" style="color:blue">详细</a>
                </td>
              </tr>
              </g:each>
          </g:else>

        </table>
        <div class="page">
            <g:if test="${tradeListTotal>0}">
                <div class="elxdwn blue"><a href="#" class="download-excel">下载统计结果</a></div>
            </g:if>
          <g:pageNavi total="${tradeListTotal}"/>
          </div>
    </div>
</form>

<script type="text/javascript">
  $(function() {
    $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
    $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });

    $("#flow_No").val("");
    $("#outTrade_No").val("");
    $("#amt_s").val("");
    $("#amt_b").val("");
  });

$(document).ready(function() {

    $("#serch").click(function(){
        $("#flow_No").val("");
        $("#outTrade_No").val("");
        $("#amt_s").val("");
        $("#amt_b").val("");
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
	$("#searchform").validate({
		rules: {
            amt_s:{money:true,range:[0.01,100000000]},
			amt_b:{money:true,range:[0.01,100000000],ge:"#amt_s"},
			startDate:{dateISO:true},
			endDate:{dateISO:true,compareDate:"#startDate"}
		},
		messages: {
            amt_s:{money:"无效金额",range:"无效金额范围"},
			amt_b:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
			startDate:{dateISO:"无效时间格式"},
			endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期"}
		}
	});
    E.on(D.query(".download-excel"), "click", function(e) {
            download("excel");
            E.preventDefault(e);
        });
        function download(type) {
            var f = D.get("searchform");
            f.action = f.action + "?format=" + type;
            f.submit();
            f.action = "index";
            f.method = "post";
        }
});

    function emptyAll(){
        $("#startDate").val("");
        $("#endDate").val("");
        $("#amt_s").val("");
        $("#amt_b").val("");
        $("#flow_No").val("");
        $("#outTrade_No").val("");
        $("#to_Account").val("");
    }

    function doSelectChange(obj){
//        alert(obj.value);
        alert(obj.val());
        if(obj.value == 1){
            document.getElementById("flow_No").style.display = "";
            document.getElementById("outTrade_No").style.display="none";
        }else if(obj.value == 2){
            document.getElementById("flow_No").style.display = "none";
            document.getElementById("outTrade_No").style.display="";
        }

    }
</script>
</body>
</html>
