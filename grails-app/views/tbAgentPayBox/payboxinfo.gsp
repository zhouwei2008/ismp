<%@ page import="dsf.TbAgentpayInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-新增代付交易</title>
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
                    <span class="left"><strong>新增代付交易</strong></span>
				</div>
          <form action="payboxinfo" method="post" name="searchform" id="searchform">
              <div class="mcjy_serchtj">
				<table align="center" class="cwmx_list_table">
					<tr>
						<td scope="col">批次交易号：
							<input name="tradeNo" type="text" class="i-text i-text-s" id="tradeNo" value="${params.tradeNo}" maxlength="40" />
						</td>
                        <td>
						创建日期：<g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
				到：<g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
						</td>
						<td rowspan="3" scope="col"><input type="submit" class="btn-input" value="搜索"></td>
					</tr>
                     <tr>
                        <td scope="col">上传文件名：
							<input name="filename" type="text" class="i-text i-text-s" id="filename" value="${params.filename}"  />
						</td>
                    </tr>
				</table>
            </div>
            <div>
                <div class="w936">
                          <div id="tb_" class="tb_">
                                        <ul style="padding-left:10px;">
                                                <li id="tb_1" class="hovertab">新增代付交易列表</li>
                                        </ul>
                        </div>
                        <div class="ctt">
                          <div class="dis" id="tbc_01" >
                            <table width="100%" class="right_list_table" id="test">
                              <tr>
                                <th scope="col">批次交易号</th>
                                <th scope="col">创建时间</th>
                                <th scope="col">上传文件名</th>
                                <th scope="col">笔数</th>
                                <th scope="col">金额</th>
                                <th scope="col">操作</th>
                                <th scope="col">确认提交</th>
                              </tr>
							  <g:each in="${tradeList}" status="i" var="trade">
                              <tr>
                                <td>${trade.id}</td>
                                <td>${trade.batchDate}</td>
                                <td>${trade.batchFilename}</td>
                                <td>${trade.batchCount} </td>
                                <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${trade.batchAmount}" type="currency"/></strong></td>
                                <td><g:if test="${session.level3Map != null && session.level3Map['tbAgentpayBox/payboxlist'] != null}">
                                <a  href="${request.contextPath}/${session.level3Map['tbAgentpayBox/payboxlist'].modelPath}/${trade.id}"><b>进入</b></a>
                                </g:if>&nbsp;&nbsp;
                                    <a href="${createLink(controller:'tbAgentpayBox',action:'payagentinfodel')}/${trade.id}">删除</a>
                                </td>
                                <td>
                                     <a href="${createLink(controller:'tbAgentpayBox',action:'payboxsubmit')}/${trade.id}">提交</a>

                                </td>
                              </tr>
							  </g:each>
                            </table>
							<g:pageNavi total="${tradeListTotal}" max="${max}"/>

                  </div>
                </div>
              </div>
            </div>
          </form>
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

</script>
</body>
</html>
