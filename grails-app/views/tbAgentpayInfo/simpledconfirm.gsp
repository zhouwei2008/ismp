<%@ page import="java.text.DecimalFormat; dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>吉高-单笔代收申请确认</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" media="all" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}?t=${new Date().getTime()}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'common.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'decode.js')}"></script>
    <script type="text/javascript">
        function winClose()
        {
            window.parent.win3.close();
        }
    </script>
</head>
<body>
    <div class="trnmenu" style="width:500px;">
        <span class="left trnmenutlt">单笔代收申请确认：</span>
    </div>

      	<g:form name="actionForm">
            <g:hiddenField name="tradeId" id="tradeId" value="${params.tradeId}" />
            <g:hiddenField name="tradeCardname" id="tradeCardname" value="${java.net.URLDecoder.decode(params.tradeCardname)}" />
            <g:hiddenField name="tradeAccountname" id="tradeAccountname" value="${java.net.URLDecoder.decode(params.tradeAccountname)}" />
            <g:hiddenField name="tradeCardnum" id="tradeCardnum" value="${java.net.URLDecoder.decode(params.tradeCardnum)}" />
            <g:hiddenField name="tradeBranchbank" id="tradeBranchbank" value="${java.net.URLDecoder.decode(params.tradeBranchbank)}" />
            <g:hiddenField name="tradeSubbranchbank" id="tradeSubbranchbank" value="${java.net.URLDecoder.decode(params.tradeSubbranchbank)}" />
            <g:hiddenField name="tradeProvince" id="tradeProvince" value="${java.net.URLDecoder.decode(params.tradeProvince)}" />
            <g:hiddenField name="tradeCity" id="tradeCity" value="${java.net.URLDecoder.decode(params.tradeCity)}" />
            <g:hiddenField name="tradeAmount" id="tradeAmount" value="${params.tradeAmount}" />
            <g:hiddenField name="tradeAccounttype" id="tradeAccounttype" value="${params.tradeAccounttype}" />
            <g:hiddenField name="contractUsercode" id="contractUsercode" value="${params.contractUsercode}" />
            <g:hiddenField name="tradeRemark" id="tradeRemark" value="${java.net.URLDecoder.decode(params.tradeRemark)}" />
            <g:hiddenField name="certificateType" id="certificateType" value="${params.certificateType}" />
            <g:hiddenField name="certificateNum" id="certificateNum" value="${java.net.URLDecoder.decode(params.certificateNum)}" />
            <g:hiddenField name="tradeMobile" id="tradeMobile" value="${java.net.URLDecoder.decode(params.tradeMobile)}" />
            <div class="openlitst">
                 <table class="tlb1" >
                     <tr>
                         <td width="150" class="txtRight b">客户名称:</td>
                         <td class="txtLeft">${params.tradeCardname}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">客户开户行:</td>
                         <td class="txtLeft">${params.tradeAccountname}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">银行账户:</td>
                         <td class="txtLeft">${params.tradeCardnum}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">开户行所在省:</td>
                         <td class="txtLeft" >${params.tradeProvince}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">开户行所在市:</td>
                         <td class="txtLeft">${params.tradeCity}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">开户行分行:</td>
                         <td class="txtLeft">${params.tradeBranchbank}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">开户行支行:</td>
                         <td class="txtLeft" >${params.tradeSubbranchbank}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">金额:</td>
                         <td class="txtLeft">${params.tradeAmount}</td>
                         <td class="txtLeft" ></td>
                     </tr>
                     <tr>
                         <td class="txtRight b">账户类型:</td>
                         <td class="txtLeft"><g:select name="tradeAccounttype" id="tradeAccounttype" disabled="true" value="${params.tradeAccounttype}" from="${dsf.TbAgentpayDetailsInfo.accountTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}"/></td>
                         <td class="txtLeft"></td>
                     </tr>
                     <tr>
                         <td class="txtRight b">代扣协议号:</td>
                         <td class="txtLeft">${params.contractUsercode}</td>
                         <td class="txtLeft" ></td>
                     </tr>
                     <tr>
                         <td class="txtRight b">证件类型:</td>
                         <td class="txtLeft"><g:select name="certificateType" id="certificateType" disabled="true" value="${params.certificateType}" from="${dsf.TbBizCustomer.certificateTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}"/></td>
                     </tr>
                     <tr>
                         <td class="txtRight b">证件号:</td>
                         <td class="txtLeft">${params.certificateNum}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">手机号:</td>
                         <td class="txtLeft">${params.tradeMobile}</td>
                     </tr>
                     <tr>
                         <td class="txtRight b">备注:</td>
                         <td class="txtLeft">${params.tradeRemark}</td>
                     </tr>
                     <tr>
                         <td colspan="2">
                             <input type="button" name="btnConfirm" id="btnConfirm" class="btn mglf10" value="提交" />
                             &nbsp;&nbsp;&nbsp;&nbsp;
                             <input type="button" class="btn mglf10" onclick="winClose()" value="返回" />
                         </td>
                     </tr>
                 </table>
            </div>
        </g:form>

    <script type="text/javascript">
          $(document).ready(function() {

            //ajax get 请求
              $('#btnConfirm').click(function(){
                  var tradeId = encodeURL($("#tradeId").val());
                  var tradeCardname = encodeURL($("#tradeCardname").val());
                  var tradeCardnum = encodeURL($("#tradeCardnum").val());
                  var tradeAccountname = encodeURL($("#tradeAccountname").val());
                  var tradeProvince = encodeURL($("#tradeProvince").val());
                  var tradeCity = encodeURL($("#tradeCity").val());
                  var tradeBranchbank = encodeURL($("#tradeBranchbank").val());
                  var tradeSubbranchbank = encodeURL($("#tradeSubbranchbank").val());
                  var tradeAmount = encodeURL($("#tradeAmount").val());
                  var tradeAccounttype = encodeURL($("#tradeAccounttype").val());
                  var contractUsercode = encodeURL($("#contractUsercode").val());
                  var tradeRemark = encodeURL($("#tradeRemark").val());
                  var certificateType=encodeURL($("#certificateType").val()); //新加字段证件类型
                  var certificateNum=encodeURL($("#certificateNum").val()); //新加字段证件号
                  var tradeMobile=encodeURL($("#tradeMobile").val());//新加字段手机号
                  //var tradeCustorder=encodeURL($("#tradeCustorder").val());   //商户订单号
                  var url = "${createLink(controller:'tbAgentpayInfo',action:'simpledeductnew')}"+
                             "?tradeId="+tradeId+"&tradeCardname="+tradeCardname+"&tradeCardnum="+tradeCardnum+"&tradeAccountname="+tradeAccountname+"&tradeBranchbank="+tradeBranchbank+
                             "&tradeSubbranchbank="+tradeSubbranchbank+"&tradeProvince="+tradeProvince+"&tradeCity="+tradeCity+"&tradeAmount="+tradeAmount+
                             "&tradeAccounttype="+tradeAccounttype+"&contractUsercode="+contractUsercode+"&tradeRemark="+tradeRemark+"&certificateType="+certificateType+
                             "&certificateNum="+certificateNum+"&tradeMobile="+tradeMobile;

                            var b =
                            {
                                success:function(c) {
                                    var d = eval("(" + c.responseText + ")");
                                    if (d.status == "true") {
                                        alert(d.msg);
                                    } else {
                                        alert(d.msg);
                                    }
                                    window.parent.document.location.href = "${createLink(controller:'tbAgentpayInfo',action:'simplededuct')}";
                                    window.parent.win2.close();
                                },failure:function(c) {
                                AP.widget.xBox.hide();
                                alert("脚本出现错误,请稍后再试")
                            }
                            };
                            AP.ajax.asyncRequest("POST", url, b)
              });
        });
    </script>
</body>
</html>