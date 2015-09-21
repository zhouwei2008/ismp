<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-交易信息编辑</title>

		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css/flick',file:'jquery-ui-1.8.7.custom.css')}" media="all" />
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'js/ext/css',file:'ext-all.css')}" media="all" />
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'js/ext/css',file:'style.css')}" media="all" />
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'Paging.js')}"></script>
		<g:javascript library="jquery" />
		<script charset="utf-8" src="${resource(dir:'js',file:'jquery-ui-1.8.7.custom.min.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'application.js')}"></script>
		<script src="${resource(dir:'js',file:'jquery.validate.min.js')}" type="text/javascript"></script>

        <script charset="utf-8" src="${resource(dir:'js/ext/js',file:'common.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js/ext/js',file:'ext-base.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js/ext/js',file:'ext-all.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js/jquery',file:'jquery.autocomplete.js')}"></script>
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
          <div class="grxx_tabel">

                <h1><span class="left" style=" font-size:12px;">交易信息编辑&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></h1>

                    <g:hiddenField name="tradeId" id="tradeId" value="${trade?.id}" />
                    <g:hiddenField name="batchId" id="batchId" value="${trade?.TbAgentpayInfo.id}" />
                 <div style="float:left; width:90%; margin:20px;display:inline;">
                <table class="right_frome">
                  <tr>
                      <td align="right" valign="top"><b>序号:</b></td>
                      <td width="160px;" valign="top"><g:textField name="tradeNum" id="tradeNum" readonly="true" value="${trade?.tradeNum}" class="input180" /></td>
                  </tr>
                    <tr>
                      <td align="right" valign="top"><b>银行帐号:</b></td>
                      <td valign="top"><g:textField name="tradeCardnum" id="tradeCardnum" value="${trade?.tradeCardnum}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>开户名:</b></td>
                      <td width="160px;" valign="top"><g:textField name="tradeCardname" id="tradeCardname" value="${trade?.tradeCardname}" class="input180" /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>开户行:</b></td>
                      <td valign="top"><g:textField name="tradeAccountname" id="tradeAccountname" value="${trade?.tradeAccountname}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>分行:</b></td>
                      <td valign="top"><g:textField name="tradeBranchbank" id="tradeBranchbank" value="${trade?.tradeBranchbank}"  class="input180" /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>支行:</b></td>
                      <td valign="top"><g:textField name="tradeSubbranchbank" id="tradeSubbranchbank" value="${trade?.tradeSubbranchbank}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>公/私:</b></td>
                      <td valign="top"><g:textField name="tradeAccounttype" id="tradeAccounttype" value="${trade?.tradeAccounttype}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>金额:</b></td>
                      <td valign="top"><g:textField name="tradeAmount" id="tradeAmount" value="${trade?.tradeAmount}" class="input180"  /></td>
                  </tr>
                   <tr>
                      <td align="right" valign="top"><b>币种:</b></td>
                      <td valign="top"><g:textField name="tradeAmounttype" id="tradeAmounttype" value="${trade?.tradeAmounttype}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>省:</b></td>
                      <td valign="top"><g:textField name="tradeProvince" id="tradeProvince" value="${trade?.tradeProvince}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>市:</b></td>
                      <td valign="top"><g:textField name="tradeCity" id="tradeCity" value="${trade?.tradeCity}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>手机号:</b></td>
                      <td valign="top"><g:textField name="tradeMobile" id="tradeMobile" value="${trade?.tradeMobile}" class="input180"  /></td>
                  </tr>
                    <tr>
                      <td align="right" valign="top"><b>证件类型:</b></td>
                      <td valign="top"><g:select name="certificateType" id="certificateType" value="${trade?.certificateType}" from="${dsf.TbBizCustomer.certificateTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="right_top_h2_input"/></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>证件号:</b></td>
                      <td valign="top"><g:textField name="certificateNum" id="certificateNum" value="${trade?.certificateNum}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>用户协议号:</b></td>
                      <td valign="top"><g:textField name="contractUsercode" id="contractUsercode" value="${trade?.contractUsercode}" class="input180"  /></td>
                  </tr>
                  <tr>
                      <td align="right" valign="top"><b>商户订单号:</b></td>
                      <td valign="top"><g:textField name="tradeCustorder" id="tradeCustorder" value="${trade?.tradeCustorder}" class="input180"  /></td>
                  </tr>

                  <tr>
                      <td align="right" valign="top"><b>备注:</b></td>
                      <td valign="top"><g:textField name="tradeRemark2" id="tradeRemark2" value="${trade?.tradeRemark2}"  class="input180" /></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td>
                          <g:actionSubmit action="payboxupdate" style="color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" value="保存修改"></g:actionSubmit>
                          <input type="button" id="btnClear" name="btnClear" style="margin-left:10px; color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" value="清除">
                      </td>
                  </tr>
              </table>
               </div>

          </div>
      </div>

<script type="text/javascript">
$(document).ready(function() {
        $("#tradeCardname").autocomplete("${createLink(controller:'tbBizCustomer',action:'getCollData')}", {
            minChars: 0, //双击空白文本框时显示全部提示数据
            max: 500,
            width: 300,
            matchContains: true,
            formatItem: function(data, i, total) {
                var row = eval("(" + data + ")");
                //alert("item is " + row);
                return "<I>" + row.cardName + " - " + row.bank + "</I>"; //改变匹配数据显示的格式
                //return "<I>" + row.cardName + " - " + row.bank + " - " + row.desc2 + "</I>"; //改变匹配数据显示的格式
            },
            formatMatch: function(data, i, total) {
                var row = eval("(" + data + ")");
                return row.cardName;
            },
            formatResult: function(data) {
                var row = eval("(" + data + ")");
                return row.cardName;
            }
        }).result(SearchCallback); //选中匹配数据中的某项数据时，调用插件的result()方法
        //自定义返回匹配结果函数
        function SearchCallback(event, data, formatted) {
            var row = eval("(" + data + ")");
            $("#tradeId").val(row.id);
            document.getElementById("tradeCardname").focus();
            $("#tradeCardnum").val(row.cardNum);
            document.getElementById("tradeCardnum").focus();
            $("#tradeAccountname").val(row.bank);
            document.getElementById("tradeAccountname").focus();
            $("#tradeBranchbank").val(row.branchBank);
            document.getElementById("tradeBranchbank").focus();
            $("#tradeSubbranchbank").val(row.subbranchBank);
            document.getElementById("tradeSubbranchbank").focus();
            $("#tradeProvince").val(row.province);
            document.getElementById("tradeProvince").focus();
            $("#tradeCity").val(row.city);
            document.getElementById("tradeCity").focus();
            $("#tradeAccounttype").val(row.accountType);
            document.getElementById("tradeAccounttype").focus();
            $("#contractUsercode").val(row.contractCode);
            document.getElementById("contractUsercode").focus();
            $("#tradeRemark2").val(row.remark);
            document.getElementById("tradeRemark2").focus();
            $("#certificateType").val(row.certificateType);
            document.getElementById("certificateType").focus();
            $("#certificateNum").val(row.certificateNum);
            document.getElementById("certificateNum").focus();
            $("#tradeMobile").val(row.tradeMobile);
            document.getElementById("tradeMobile").focus();
            $("#tradeCustorder").val(row.tradeCustorder);
            document.getElementById("tradeCustorder").focus();
        };
    $("#btnClear").removeAttr('onclick');
    $("#btnClear").click(function(){
        $("#tradeCardname").val("");
        $("#tradeCardnum").val("");
        $("#tradeAccountname").val("");
        $("#tradeProvince").val("");
        $("#tradeCity").val("");
        $("#tradeBranchbank").val("");
        $("#tradeSubbranchbank").val("");
        $("#tradeAmount").val("");
        $("#tradeAccounttype").val("");
        $("#contractUsercode").val("");
        $("#tradeRemark2").val("");
        $("#certificateType").val("");
        $("#certificateNum").val("");
        $("#tradeMobile").val("");
        $("#tradeCustorder").val("");
    });
    jQuery.validator.addMethod("stringCheck", function(value, element) {
            return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
        }, "<br><font color=red size=1>只能包括中英文、数字和下划线。</font>");
    jQuery.validator.addMethod("money", function(a, b) {
            return this.optional(b) || /^\d+(\.\d{0,2})?$/i.test(a)
        }, "Please enter a valid amount.");
	$("#searchform").validate({
		rules: {
			tradeCardname:{required:true,stringCheck:true,maxlength:25},
            tradeAccountname:{required:true,stringCheck:true,maxlength:25},
            tradeCardnum:{required:true, digits:true, rangelength:[9,32]},
            tradeBranchbank:{required:true, stringCheck:true,maxlength:25},
            tradeSubbranchbank :{required:true, stringCheck:true,maxlength:25},
            tradeProvince :{required:true,stringCheck:true, maxlength:8},
            tradeCity :{required:true,stringCheck:true, maxlength:15},
            tradeAmount :{required:true, money:true, range:[0.01,999999999]},
            tradeAccounttype :{required:true},
            contractUsercode:{required:true,rangelength:[15,15]},
            certificateType :{required:true},
            certificateNum :{required:true,stringCheck:true, maxlength:18},
            tradeMobile :{required:true,stringCheck:true, maxlength:13},
            tradeCustorder :{required:true,stringCheck:true, maxlength:30},
            tradeRemark2:{required:true,stringCheck:true,maxlength:15}
		},
		messages: {
			tradeCardname:{required:"<br><font color=red size=1>请输入客户名称。</font>",maxlength:"<br><font color=red size=1>您输入的客户名称长度超过{0}个字符。</font>"},
            tradeAccountname:{required:"<br><font color=red size=1>请选择客户开户行。</font>",maxlength:"<br><font color=red size=1>您输入的银行名称长度超过{0}个字符。</font>"},
            tradeCardnum:{required:"<br><font color=red size=1>请输入客户账号。</font>", digits:"<br><font color=red size=1>账户号只能输入数字。</font>" ,rangelength:"<br><font color=red size=1>您输入的账号的长度不在 {0} 和 {1} 之间。</font>"},
            tradeBranchbank:{required:"<br><font color=red size=1>请输入分行。</font>", maxlength:"<br><font color=red size=1>您输入的分行长度超过{0}个字符。</font>"},
            tradeSubbranchbank:{required:"<br><font color=red size=1>请输入支行。</font>", maxlength:"<br><font color=red size=1>您输入的支行长度超过{0}个字符。</font>"},
            tradeProvince :{required:"<br><font color=red size=1>请输入开户行所在省。</font>",maxlength:"<br><font color=red size=1>您输入的省份长度超过{0}个字符。</font>"},
            tradeCity :{required:"<br><font color=red size=1>请输入开户行所在市。</font>", maxlength:"<br><font color=red size=1>您输入的市长度超过{0}个字符。</font>"},
            tradeAmount :{required:"<br><font color=red size=1>请输入金额。</font>", money:"<br><font color=red size=1>无效金额。</font>",range:"<br><font color=red size=1>输入的金额不在 {0} 和 {1} 之间。</font>"},
            tradeAccounttype :{required:"<br><font color=red size=1>请选择账户类型。</font>"},
            contractUsercode:{required:"<br><font color=red size=1>请输入用户协议号。</font>",rangelength:"<br><font color=red size=1>您输入的协议号长度不是{0}。</font>"},
            certificateType :{required:"<br><font color=red size=1>请选择证件类型。</font>"},
            certificateNum:{required:"<br><font color=red size=1>请输入证件号。</font>",maxlength:"<br><font color=red size=1>您输入的证件号长度超过{0}个字符。</font>"},
            tradeMobile:{required:"<br><font color=red size=1>请输入手机号。</font>",maxlength:"<br><font color=red size=1>您输入的手机号长度超过{0}个字符。</font>"},
            tradeCustorder:{required:"<br><font color=red size=1>请输入商户订单号。</font>",maxlength:"<br><font color=red size=1>您输入的商户订单号长度超过{0}个字符。</font>"},
            tradeRemark2:{required:"<br><font color=red size=1>请输入备注。</font>",maxlength:"<br><font color=red size=1>您输入的备注长度超过{0}个字符。</font>"}
		}
	});
});

</script>
</body>
</html>
