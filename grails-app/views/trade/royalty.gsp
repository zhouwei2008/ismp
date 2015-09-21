<%@ page import="ismp.TradeBase" %>
<!doctype html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="layout" content="main" />
		<title>吉高-分润明细</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'cwmx.css')}" media="all" />
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
        <div class="cwmx_mxsm">
            <span class="left"><strong>分润明细</strong></span>
        </div>
        <form action="royalty" method="post" name="searchform" id="searchform">
        <div class="cwmx_serchtj">
            <table align="center" class="cwmx_list_table">
                <tr>
                    <td>
                        开始日期：<g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                        结束日期：<g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                    </td>
                    <td>
                        商户订单号：<g:textField name="outTradeNo" value="${params.outTradeNo}" size="20" class="right_top_h2_input"/>
                    </td>
                    <td >
                        <span style="text-align:center">
                            <input type="submit" class="btn-input" value="搜索">
                        </span>
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <div class="w936">
                <div id="tb_" class="tb_">
                    <ul style="padding-left:10px;">
                        <li id="tb_1" class="hovertab">分润明细</li>
                    </ul>
                </div>
                <div class="ctt">
                    <div class="dis" id="tbc_01" >
                        <table width="100%" class="right_list_table" id="test">
                            <tr>
                                <th scope="col">创建时间</th>
                                <th scope="col">商户订单号</th>
                                <th scope="col">交易对象账户</th>
                                <th scope="col">流水号</th>
                                <th scope="col">类型</th>
                                <th scope="col">收入（元）</th>
                                <th scope="col">支出（元）</th>
                                <th scope="col"> 账户余额（元）</th>
                                <th scope="col">摘要</th>
                                <th scope="col">详细</th>
                            </tr>
                            <g:each in="${acSeqList}" status="i" var="acSeq">
                            <tr>
                                <td>${acSeq.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                                <td>${acSeq.transaction.outTradeNo}</td>
                                <td>${
                                    TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeId == session.cmCustomer.id ?
                                        (TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payerCode==null ?
                                            TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payerName : TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payerCode
                                        ) : TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeCode==null ?
                                            TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeName : TradeBase.findByTradeNo(acSeq.transaction.tradeNo)?.payeeCode }</td>
                                <td>${acSeq.transaction.tradeNo}</td>
                                <td>${acSeq.transaction.transTypeMap[acSeq.transaction.transferType]}</td>
                                <td style="text-align: right;"><strong class="lvsfong"><g:if test="${acSeq.debitAmount>0}">+<g:formatNumber currencyCode="CNY" number="${acSeq.debitAmount/100}" type="currency"/></g:if></strong></td>
                                <td style="text-align: right;"><strong class="hsfong"><g:if test="${acSeq.creditAmount>0}">-<g:formatNumber currencyCode="CNY" number="${acSeq.creditAmount/100}" type="currency"/></g:if></strong></td>
                                <td style="text-align: right;"><strong><g:formatNumber currencyCode="CNY" number="${acSeq.balance/100}" type="currency"/></strong></td>
                                <td>${acSeq.transaction.subjict=='null'?'':acSeq.transaction.subjict?.encodeAsHTML()}</td>
                                <td>
                                    <g:if test="${session.level3Map != null && session.level3Map['trade/accdetail'] != null}">
                                            <a href="${request.contextPath}/${session.level3Map['trade/accdetail'].modelPath}/${acSeq.id}?flag=1">查看</a>
                                            %{--<a href="${createLink(controller:'trade',action:'accdetail')}/${acSeq.id}?flag=1">查看</a>--}%
                                        </g:if>
                                    </td>
                            </tr>
                            </g:each>
                        </table>
                        <g:pageNavi total="${acSeqListTotal}"/>
                        <g:if test="${acSeqListTotal>0}">
                        <div>
                            <span><a href="#" title="TXT格式" class="download-txt">&nbsp;&nbsp;&nbsp;&nbsp;下载TXT</a></span>
                            <span><a href="#" title="Excel格式" class="download-exc">&nbsp;&nbsp;&nbsp;&nbsp;下载CSV</a></span>
                        </div>
                        </g:if>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
 <script type="text/javascript">
  $(function() {
    var dates=new Date();
    var diff=dates.setYear(dates.getFullYear()-2);
    var yearsday=(new Date()-diff)/86400000;
    $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate:-yearsday, maxDate: "+0D" });
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


	//----------下载部分处理-------
	E.on(D.query(".download-exc"),"click",function(e){
		download("csv");
		E.preventDefault(e);
	});
	E.on(D.query(".download-txt"),"click",function(e){
		download("txt");
		E.preventDefault(e);
	});	
	function download(type){
		var f = D.get("searchform");
		f.action=f.action+"?format="+type;
		f.submit();
		f.action = "royalty";
		f.method = "post";		
	};

});
</script>     

</body>
</html>
