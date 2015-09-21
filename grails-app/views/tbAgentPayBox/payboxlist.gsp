<%@ page import="dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-报盘文件确认</title>
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
              <div class="cwmx_mxsm">
              <span class="left"><strong>报盘文件确认</strong></span>
              </div>
          <g:form action="payboxlist" method="post" name="searchform">
              <g:hiddenField name="format"/>
              <div class="mcjy_serchtj">
				<table align="center" class="cwmx_list_table">
					<tr>
						<td scope="col">商户名称：
                           &nbsp;&nbsp;&nbsp;&nbsp; ${customername}
						</td>
					</tr>
                    <tr>
						<td scope="col">上传文件名：
                           &nbsp;&nbsp;&nbsp; ${type.batchFilename}
						</td>
					</tr>
                    <tr>
						<td scope="col">业务类型：&nbsp;&nbsp;&nbsp;&nbsp;批量代付
						</td>
					</tr>
                     <tr>
						<td scope="col">总笔数：
                           &nbsp;&nbsp;&nbsp;&nbsp; ${type.batchCount}笔
						</td>
					</tr>
                    <tr>
						<td scope="col">总金额：
                           &nbsp;&nbsp;&nbsp;&nbsp; ${type.batchAmount}元
						</td>
                        <td scope="col" align="right">
                        <g:actionSubmit action="payboxsubmit" style="color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" value="确认提交"></g:actionSubmit>&nbsp;&nbsp;
					    </td>
                    </tr>
				</table>
        </div>
        <div>
                <div class="w936">
                        <div class="ctt">
                          <div class="dis" id="tbc_01" >
                            <table width="100%" class="right_list_table" id="test">
                              <tr>
                                <th scope="col">序号</th>
                                <th scope="col">开户名</th>
                                <th scope="col">客户账号</th>
                                <th scope="col">公/私</th>
                                <th scope="col">金额</th>
                                <th scope="col">币种</th>
                                <th scope="col">手机号</th>
                                <th scope="col">证件类型</th>
                                <th scope="col">证件号</th>
                                <th scope="col">备注</th>
                                <th scope="col">检验结果</th>
                                <th scope="col">操作</th>
                              </tr>
							  <g:each in="${tradeList}" status="i" var="trade">
                              <tr>
                                <td>${trade.tradeNum}</td>
                                <td>${trade.tradeCardname}</td>
                                <td>${trade.tradeCardnum}</td>
                                <td>${trade.tradeAccounttype}</td>
                                <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${trade.tradeAmount}" type="currency"/></strong></td>
                                <td>${trade.tradeAmounttype}</td>
                                <td>${trade.tradeMobile}</td>
                                <td>${trade.certificateType}</td>
                                <td>${trade.certificateNum}</td>
                                <td>${trade.tradeRemark}</td>
                                <td>${trade.errMsg}</td>
                                <td><g:if test="${session.level3Map != null && session.level3Map['tbAgentpayBox/payboxupdate'] != null}">
                                <a href="${request.contextPath}/${session.level3Map['tbAgentpayBox/payboxupdate'].modelPath}/${trade.id}"><b>编辑</b></a>
                                </g:if>&nbsp;&nbsp;
                                    <g:link action="payboxdel" value="删除" params="${trade.id}">删除</g:link>
                                </td>
                              </tr>
							  </g:each>
                            </table>
							<g:pageNavi total="${tradeListTotal},${max}"/>
                      </div>
                    </div>
                </div>
            </div>
          </g:form>
        </div>

<script type="text/javascript">
  $(function() {
    $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
    $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
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
E.on(D.query(".download-exc"),"click",function(e){
	download("csv");
	E.preventDefault(e);
});
E.on(D.query(".download-txt"),"click",function(e){
	download("txt");
	E.preventDefault(e);
});
  E.on(D.query(".download-excel"),"click",function(e){
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
