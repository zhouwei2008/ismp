<%--
  Created by IntelliJ IDEA.
  User: zhaoshuang
  Date: 12-11-27
  Time: 上午9:40
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-在线申请</title>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'decode.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js/jquery',file:'jquery.autocomplete.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}?t=${new Date().getTime()}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'common.js')}"></script>
</head>
<body>
      <div class="trnmenu">
        <span class="left trnmenutlt">服务申请：</span>
      </div>
      <div class="main_box">
      	  <g:form name="confirmForm" id="confirmForm" action="confirm">
              <input type="hidden" id="showMsg"/>
              <g:hiddenField name="registrationType" id="registrationType" value="${params.registrationType}"/>
              <div class="serchlitst">
                 <table class="tlb1" >
                     <tr>
                         <td width="150" class="txtRight b"><font color="red">* </font>网站域名:</td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="companyWebsite" id="companyWebsite" value="${params.companyWebsite}"  maxlength="100"/></td>
                         <td class="txtLeft" style="padding-left:10px;">例如:http://www.gicard.net</td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>商户名称:</b></td>
                         <td class="txtLeft" style="padding-left:10px;">
                             <g:textField name="registrationName" id="registrationName" value="${params.registrationName}" maxlength="50"/>
                         </td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr style="display:none;">
                         <td class="txtRight b"><b><font color="red">* </font>商户性质:</b></td>
                         <td class="txtLeft" style="padding-left:10px;">
                             <g:radio value="C" name="radioC" id="radioC" checked="true"/>企业&nbsp;&nbsp;
                             <g:radio value="P" name="radioP" id="radioP"/>个人
                         </td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>所在区域:</b></td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="belongToArea" id="belongToArea" value="${params.belongToArea}" maxlength="50"/></td>
                         <td class="txtLeft" style="padding-left:10px;">例如:天津</td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>所属行业:</b></td>
                         <td class="txtLeft" style="padding-left:10px;">
                             <g:select name="belongToBusiness" id="belongToBusiness" value="${params.belongToBusiness}" from="${ismp.CmApplicationInfo.businessMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="right_top_h2_input"/>
                         </td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>联系人:</b></td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="bizMan" id="bizMan" value="${params.bizMan}" maxlength="30"/></td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>手机:</b></td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="bizMPhone" id="bizMPhone" value="${params.bizMPhone}" maxlength="11"/></td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b>座机:</b></td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="bizPhone" id="bizPhone" value="${params.bizPhone}" maxlength="20"/></td>
                         <td class="txtLeft" style="padding-left:10px;">例如:022-12345678</td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>邮箱:</b></td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="bizEmail" id="bizEmail" value="${params.bizEmail}" maxlength="30"/></td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>通信地址:</b></td>
                         <td class="txtLeft" style="padding-left:10px;" colspan="2"><g:textField name="officeLocation" id="officeLocation" value="${params.officeLocation}" maxlength="100" style="width:500px;"/></td>
                     </tr>
                     <tr>
                         <td class="txtRight b"><b><font color="red">* </font>邮编:</b></td>
                         <td class="txtLeft" style="padding-left:10px;"><g:textField name="zipCode" id="zipCode" value="${params.zipCode}" maxlength="6"/></td>
                         <td class="txtLeft" style="padding-left:10px;"></td>
                     </tr>
                     <tr>
                         <td colspan="3">
                             <input type="submit" id="btnConfirm" name="btnConfirm" class="btn mglf10" value="提交" />
                             &nbsp;&nbsp;&nbsp;&nbsp;
                             <input type="button" id="btnClose" name="btnClose" class="btn mglf10" value="取消" />
                         </td>
                     </tr>
                 </table>
              </div>
          </g:form>
      </div>

<script type="text/javascript">

    document.getElementById("registrationType").value = "C";

    $("#radioC").removeAttr('onclick');
    $("#radioC").click(function(){
        document.getElementById("registrationType").value = "C";
        document.getElementById("radioP").checked = false;
    });
    $("#radioP").removeAttr('onclick');
    $("#radioP").click(function(){
        document.getElementById("registrationType").value = "P";
        document.getElementById("radioC").checked = false;
    });

    $("#btnClose").removeAttr('onclick');
    $("#btnClose").click(function(){
        window.close();
    });

    function checkEmail() {
        $.ajax({
            type: "POST",
            url: "${createLink(action:'checkEmail',controller:'application')}",
            data: "bizEmail="+encodeURL($('#bizEmail').val().trim()),
            cache: false,
            async: false,
            success: function(json){
                document.getElementById("showMsg").value = json.msg;
            }
        })
    }

    $(document).ready(function() {

        jQuery.validator.addMethod("stringCheck", function(value, element) {
            return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
        }, "<br><font color=red size=1>只能包括中英文、数字和下划线。</font>");
        jQuery.validator.addMethod("webCheck", function(value, element) {
            return this.optional(element) || /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/.test(value);
        }, "<br><font color=red size=1>请输入正确的网址格式。</font>");
        jQuery.validator.addMethod("mPhoneCheck", function(value, element) {
            return this.optional(element) || /^1[3|4|5|8][0-9]\d{8}$/.test(value);
        }, "<br><font color=red size=1>请输入正确的手机号码格式。</font>");
        jQuery.validator.addMethod("phoneCheck", function(value, element) {
            return this.optional(element) || /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(value);
        }, "<br><font color=red size=1>请输入正确的电话号码格式。</font>");
        jQuery.validator.addMethod("zipCodeCheck", function(value, element) {
            return this.optional(element) || /^[0-9]/.test(value);
        }, "<br><font color=red size=1>只能是6位数字。</font>");
        jQuery.validator.addMethod("emailCheck", function(value, element) {
            checkEmail();
            if (document.getElementById("showMsg").value == "success") {
                document.getElementById("showMsg").value = "";
                return true;
            } else {
                return false;
            }
        }, "<br><font color=red size=1>该邮箱已被注册。</font>");


        $("#confirmForm").validate({
            rules: {
                companyWebsite:{required:true,maxlength:100,webCheck:true},
                registrationName:{required:true,maxlength:50,stringCheck:true},
                belongToArea:{required:true,maxlength:50,stringCheck:true},
                belongToBusiness:{required:true},
                bizMan:{required:true,maxlength:30,stringCheck:true},
                bizMPhone:{required:true,maxlength:11,mPhoneCheck:true},
                bizPhone:{phoneCheck:true,maxlength:20},
                bizEmail:{required:true,maxlength:30,email:true,emailCheck:true},
                officeLocation :{required:true,maxlength:100},
                zipCode :{required:true,maxlength:6,zipCodeCheck:true}
            },
            messages: {
                companyWebsite:{required:"<br><font color=red size=1>请输入网站域名。</font>",maxlength:"<br><font color=red size=1>您输入的网站域名长度超过{0}个字符。</font>"},
                registrationName:{required:"<br><font color=red size=1>请输入商户名称。</font>",maxlength:"<br><font color=red size=1>您输入的商户名称长度超过{0}个字符。</font>"},
                belongToArea:{required:"<br><font color=red size=1>请输入所在区域。</font>",maxlength:"<br><font color=red size=1>您输入的所在区域长度超过{0}个字符。</font>"},
                belongToBusiness:{required:"<br><font color=red size=1>请选择所属行业。</font>"},
                bizMan:{required:"<br><font color=red size=1>请输入联系人。</font>",maxlength:"<br><font color=red size=1>您输入的联系人长度超过{0}个字符。</font>"},
                bizMPhone:{required:"<br><font color=red size=1>请输入手机号码。</font>",maxlength:"<br><font color=red size=1>您输入的手机号码长度超过{0}个字符。</font>"},
                bizPhone:{maxlength:"<br><font color=red size=1>您输入的电话号码长度超过{0}个字符。</font>"},
                bizEmail:{required:"<br><font color=red size=1>请输入邮箱。</font>",email:"<br><font color=red size=1>请输入正确的邮箱格式。</font>",maxlength:"<br><font color=red size=1>您输入的邮箱长度超过{0}个字符。</font>"},
                officeLocation:{required:"<br><font color=red size=1>请输入通信地址。</font>",maxlength:"<br><font color=red size=1>您输入的通信地址长度超过{0}个字符。</font>"},
                zipCode :{required:"<br><font color=red size=1>请输入邮编。</font>",maxlength:"<br><font color=red size=1>您输入的邮编长度超过{0}个字符。</font>"}
            }
        });
    });

</script>
</body>
</html>