<%@ page import=" ismp.TradeRefund; ismp.TradePayment; ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-订单管理-网上支付</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css/flick',file:'jquery-ui-1.8.7.custom.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'Paging.js')}"></script>
		<g:javascript library="jquery" />
		<script charset="utf-8" src="${resource(dir:'js',file:'jquery-ui-1.8.7.custom.min.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'application.js')}"></script>
		<script src="${resource(dir:'js',file:'jquery.validate.min.js')}" type="text/javascript"></script>
		<script type="text/javascript">
			function search(obj){
				if(obj){
					if($("offset"))
					$("offset").value=0;
				}
				$("#searchform").submit();
			}
		</script>
		<style>
		.btn-input{
				background:url("${resource(dir:'images',file:'grxxanniu.gif')}") no-repeat transparent;
				border:0 none;
				cursor:pointer;
				width:71px;height:27px;
				color:#fff;
				vertical-align:middle;
				text-align:center;
				float:left;
		}
		.btn-input:hover {	background:url("${resource(dir:'images',file:'grxxanniu1.gif')}") no-repeat transparent;}
		</style>
</head>
<body>

<div class="cwmx_content">
	%{--<div class="cwmx_mxsm">--}%
		%{--<span class="left"><strong>所有卖出的交易</strong></span>--}%
		%{--<span class="left" style="margin-left:10px;">--}%
            %{--<g:if test="${session.level3Map != null && session.level3Map['trade/buy'] != null}">--}%
                %{--<a href="${request.contextPath}/${session.level3Map['trade/buy'].modelPath}">所有买入的交易</a>--}%
                %{--<a href="${createLink(controller:'trade',action:'buy')}">所有买入的交易</a>--}%
            %{--</g:if>--}%

            %{--<g:if test="${session.level3Map != null && session.level3Map['trade/refund'] != null}">--}%
                %{--<g:if test="${session.level3Map != null && session.level3Map['trade/buy'] != null}">|</g:if>--}%
                %{--<a href="${request.contextPath}/${session.level3Map['trade/refund'].modelPath}">退款交易</a>--}%
                %{--<a href="${createLink(controller:'trade',action:'refund')}">退款交易</a>--}%
            %{--</g:if>--}%

            %{--<g:if test="${session.level3Map != null && session.level3Map['trade/batchRefund'] != null}">--}%
                %{--<g:if test="${session.level3Map != null && session.level3Map['trade/refund'] != null}">|</g:if>--}%
                %{--<a href="${request.contextPath}/${session.level3Map['trade/batchRefund'].modelPath}">批量退款</a>--}%
                %{--<a href="${createLink(controller: 'trade', action: 'batchRefund')}">批量退款</a>--}%
            %{--</g:if>--}%
        %{--</span>--}%
	%{--</div>--}%
	<div class="mcjy_serchtj01">
        <g:form action="netOrdersList" method="post" name="searchform">
		<table align="center" class="cwmx_list_table">
			<tr>
				<td scope="col">平台交易号：
					<input name="orderId" type="text" class="i-text i-text-s" id="orderId" value="${params.orderId}" maxlength="40" onblur="value=value.replace(/[\^]|[^0-9]/g, '')" onkeyup="value=value.replace(/[\^]|[^0-9]/g, '')" onbeforepaste="clipboardData.setData('text ',clipboardData.getData( 'text').replace(/[\^]|[^0-9]/g, ''))" />
				</td>
				<td scope="col">
				   状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：&nbsp;&nbsp;<g:select name="status" value="${params.status}" from="${gateway.GwOrder.statusMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="right_top_h2_input"/>

				</td>
				<td rowspan="3" scope="col"><input type="submit" class="btn-input" value="搜索"></td>
			</tr>
            <tr>
				   %{--<td>银行订单号：--}%
					%{--<input name="outTradeNo1" type="text" class="i-text i-text-s" id="outTradeNo1" value="${params.outTradeNo1}" maxlength="80"/>--}%
				%{--</td>--}%
                <td>交易对方：
					<input name="buyer" type="text" class="i-text i-text-s" id="buyer" value="${params.buyer}" maxlength="80" onblur="value=value.replace(/[\^]|[^!^@^#^$^%^&^*^(^)^_^-^+^=^.^\一-\龥^a-z^A-Z^0-9]/g, '')" onkeyup="value=value.replace(/[\^]|[^!^@^#^$^%^&^*^(^)^_^-^+^=^.^\一-\龥^a-z^A-Z^0-9]/g, '')" onbeforepaste="clipboardData.setData('text ',clipboardData.getData( 'text').replace(/[\^]|[^!^@^#^$^%^&^*^(^)^_^-^+^=^.^\一-\龥^a-z^A-Z^0-9]/g, ''))"/>
				</td>
				%{--<td scope="col">--}%
				   %{--订单类型：<g:select name="orderType" value="${params.orderType}" from="${gateway.GwOrder.orderTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="right_top_h2_input"/>--}%

				%{--</td>--}%
			</tr>
            <tr>
				<td>商户订单号：
					<input name="orderNum" type="text" class="i-text i-text-s" id="orderNum" value="${params.orderNum}" maxlength="80" onblur="value=value.replace(/[\^]|[^-^a-z^A-Z^0-9]/g, '')" onkeyup="value=value.replace(/[\^]|[^-^a-z^A-Z^0-9]/g, '')" onbeforepaste="clipboardData.setData('text ',clipboardData.getData( 'text').replace(/[\^]|[^-^a-z^A-Z^0-9]/g, ''))"/>
				</td>
				<td>
				   商品名称：<input name="subject" type="text" class="i-text i-text-s" id="subject" value="${params.subject}" maxlength="80" onblur="value=value.replace(/[\^]|[^\一-\龥^a-z^A-Z^0-9]/g, '')" onkeyup="value=value.replace(/[\^]|[^\一-\龥^a-z^A-Z^0-9]/g, '')" onbeforepaste="clipboardData.setData('text ',clipboardData.getData( 'text').replace(/[\^]|[^\一-\龥^a-z^A-Z^0-9]/g, ''))"/>

				</td>
			</tr>
            <tr>
                <td >金额区间：
					<input type="text" name="amountMin" id="amountMin" class="i-text i-text-s i-text-amount" maxlength="10" value="${params.amountMin}"/>
					-<input type="text" name="amountMax" id="amountMax" class="i-text i-text-s i-text-amount" maxlength="10" value="${params.amountMax}"/>元
				</td>
                 <td>
				开始日期：<g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                 结束日期：<g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10" class="right_top_h2_input"/>

				</td>
			</tr>

		</table>
	</div>
                <div class="w936">
                        <div id="tb_" class="tb_">
							<ul style="padding-left:10px;">
									<li id="tb_1" class="hovertab">网上支付</li>
							</ul>
                        </div>
                        <div class="ctt">
                          <div class="dis" id="tbc_01" >
                            <table width="100%" class="right_list_table" id="test">
                              <tr>
                                <th scope="col">平台交易号</th>
                                <th scope="col">创建时间</th>
                                <th scope="col">支付时间</th>
                                <th scope="col">商户订单号</th>
                                <th scope="col">商品名称</th>
                                <th scope="col">交易对方</th>
                                <th scope="col">金额（元）</th>
                                <th scope="col">退款金额（元）</th>
                                <th scope="col">状态</th>
                                <th scope="col">银行交易号</th>
                                <th scope="col">收单银行</th>
                                <th scope="col">操作</th>
                              </tr>
							  <g:each in="${ordersList}" status="i" var="orders">
                              <tr>
                                <td>${orders.id}</td>
                                <td>${orders.createdate.format("yyyy-MM-dd HH:mm:ss")}</td>
                                <td>${orders.closedate.format("yyyy-MM-dd HH:mm:ss")}</td>
                                <td>${orders.ordernum}</td>
                                <td>${orders.subject}</td>
                                <td>${orders.buyer_name}</td>
                                <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${orders.amount/100}" type="currency"/></strong></td>
                                <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${orders.refund_amount/100}" type="currency"/></strong></td>
                                <td>${gateway.GwOrder.statusMap[orders.ordersts]}</td>
                                <td>${orders.trxnum}</td>
                                %{--<td><g:if test="${orders.trxnum == null && orders.ordersts == '3'}">余额支付</g:if><g:else>${orders.acquirer_name}</g:else></td>--}%
                                <td>${orders.acquirer_name}</td>
                                <td>
                                    %{--<g:if test="${session.level3Map != null && session.level3Map['gwOrder/netOrdersTrxs'] != null && orders.trxnum != null}">--}%
                                        %{--<a href="${request.contextPath}/${session.level3Map['gwOrder/netOrdersTrxs'].modelPath}/${orders.id}">付款历史</a>--}%
                                    %{--</g:if>--}%
                                    <g:if test="${session.level3Map != null && session.level3Map['gwOrder/netOrdersSubList'] != null && orders.royalty_type == '12'}">
                                         %{--<g:if test="${session.level3Map != null && session.level3Map['gwOrder/netOrdersTrxs'] != null && orders.trxnum != null}">|&nbsp;</g:if>--}%
                                         <a href="${request.contextPath}/${session.level3Map['gwOrder/netOrdersSubList'].modelPath}/${orders.id}">合单查询</a>
                                    </g:if>
                                    %{--<g:if test="${session.level3Map != null && session.level3Map['gwOrder/netOrdersRefund'] != null}">--}%
                                         %{--<g:if test="${session.level3Map != null && ((session.level3Map['gwOrder/netOrdersTrxs'] != null && orders.trxnum != null) ||(session.level3Map['gwOrder/netOrdersSubList'] != null && orders.royalty_type == '12'))}">|&nbsp;</g:if>--}%
                                         %{--<a href="${request.contextPath}/${session.level3Map['gwOrder/netOrdersRefund'].modelPath}/${orders.id}">退款</a>--}%
                                    %{--</g:if>--}%
								</td>
                              </tr>
							  </g:each>
                            </table>
							<g:pageNavi total="${count}"/>
                              <g:if test="${count>0}">
                                    <div>
                                        <span><a href="#" title="Excel格式" class="download-excel">&nbsp;&nbsp;下载Excel</a></span>
                                    </div>
                                </g:if>
							</g:form>
                  </div>
                        </div>
                </div>
      </div>
<script type="text/javascript">
  $(function() {
    var dates=new Date();
    var diff=dates.setYear(dates.getFullYear()-2);
    var yearsday=(new Date()-diff)/86400000;
    $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -yearsday, maxDate: "+0D" });
    $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -yearsday, maxDate: "+0D" });
  });

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
			amountMin:{money:true,range:[0.00,100000000]},
			amountMax:{money:true,range:[0.00,100000000],ge:"#amountMin"},
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


	//----------下载部分处理-------
	E.on(D.query(".download-exc"),"click",function(e){
		download("csv");
		E.preventDefault(e);
	});
	E.on(D.query(".download-txt"),"click",function(e){
		download("txt");
		E.preventDefault(e);
	});
    E.on(D.query(".download-excel"),"click",function(e){
		download("excel");
		E.preventDefault(e);
	});
	function download(type){
		var f = D.get("searchform");
		f.action=f.action+"?format="+type;
		f.submit();
		f.action = "netOrdersList";
		f.method = "post";
	};
});
    var j=0 ;
    function refund(i) {
        if(j!=0 && j!=i){
            document.getElementById(j).style.display = 'none';
        }
        document.getElementById(i).style.display = '';
         j=i;
    }

     function closes(i) {
        document.getElementById(i).style.display = 'none';
    }
</script>
</body>
</html>
