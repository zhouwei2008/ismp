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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="网络支付,第三方支付公司,吉高,吉高代付" />
<meta name="description" content="吉高是国内领先的第三方网上在线支付平台和大宗交易电子移动支付平台，由吉高创办，致力于为互联网用户和商户提供“安全、便捷、稳定”的网上支付服务。" />

<title>网络支付包量套餐</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css/flow/css',file:'css.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
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

    <script type="text/javascript">
    // 隔行换色
    $(function(){
        $(".tlb tr:even").addClass("demoBg");
    })
    </script>
</head>

<body>
<div class="ber"></div>
<div class="tcjs"></div>
<div class="tclb">
  <g:form name="confirmForm" id="confirmForm" action="confirm">
      <input type="hidden" id="showMsg"/>
      <g:hiddenField name="registrationType" id="registrationType" value="${params.registrationType}"/>
      <table class="tlb">
          <tr>
            <th width="22%" class="txtLeft" scope="col">服务申请：</th>
            <th class="txtRight" scope="col">*号为必填项 </th>
          </tr>
          <tr>
            <td width="200" class="txtRight">网站域名： </td>
            <td width="800" class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="companyWebsite" id="companyWebsite" value="${params.companyWebsite}"  maxlength="100"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">商户名称：</td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="registrationName" id="registrationName" value="${params.registrationName}" maxlength="50"/>
            </td>
          </tr>
          <tr style="display:none;">
            <td width="200" class="txtRight">商户性质： </td>
            <td width="800" class="txtLeft">
              <span class="red txtLeft">*</span>
              <g:radio value="C" name="radioC" id="radioC" checked="true"/>企业&nbsp;&nbsp;
              <g:radio value="P" name="radioP" id="radioP"/>个人
            </td>
          </tr>
          <tr style="display:none;">
            <td class="txtRight"></td>
            <td class="txtLeft">
            </td>
          </tr>
          <tr>
            <td class="txtRight">所在地区：</td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="belongToArea" id="belongToArea" value="${params.belongToArea}" maxlength="50"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">所属行业：</td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <label for="belongToBusiness"></label>
                <g:select name="belongToBusiness" id="belongToBusiness" value="${params.belongToBusiness}" from="${ismp.CmApplicationInfo.businessMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="right_top_h2_input"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">联系人： </td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="bizMan" id="bizMan" value="${params.bizMan}" maxlength="30"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">手机：</td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="bizMPhone" id="bizMPhone" value="${params.bizMPhone}" maxlength="11"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">座机：</td>
            <td class="txtLeft">
                <span class="red txtLeft">&nbsp;</span>
                <g:textField class="tbinput" name="bizPhone" id="bizPhone" value="${params.bizPhone}" maxlength="20"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">邮箱： </td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="bizEmail" id="bizEmail" value="${params.bizEmail}" maxlength="30"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">通信地址：</td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="officeLocation" id="officeLocation" value="${params.officeLocation}" maxlength="100"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">邮编:</td>
            <td class="txtLeft">
                <span class="red txtLeft">*</span>
                <g:textField class="tbinput" name="zipCode" id="zipCode" value="${params.zipCode}" maxlength="6"/>
            </td>
          </tr>
          <tr>
            <td class="txtRight">&nbsp;</td>
            <td class="txtLeft" style="padding-top:10px;">
                <input type="submit" id="btnConfirm" name="btnConfirm" value="&nbsp" class="btn" />
                <input type="button" value="&nbsp" class="rsbtn" onclick="onClean();"/>
            </td>
          </tr>
      </table>
  </g:form>
</div>
<g:render template="/layouts/footer"/>

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

    function onClean() {
        $(':input') .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('selected');
        return false;
    }

    function onSubmit() {
        document.getElementById("confirmForm").submit();
    }

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
        }, "<font color=red size=2>只能包括中英文、数字和下划线。</font>");
        jQuery.validator.addMethod("webCheck", function(value, element) {
            return this.optional(element) || /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/.test(value);
        }, "<font color=red size=2>请输入正确的网址格式。</font>");
        jQuery.validator.addMethod("mPhoneCheck", function(value, element) {
            return this.optional(element) || /^1[3|4|5|8][0-9]\d{8}$/.test(value);
        }, "<font color=red size=2>请输入正确的手机号码格式。</font>");
        jQuery.validator.addMethod("phoneCheck", function(value, element) {
            return this.optional(element) || /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(value);
        }, "<font color=red size=2>请输入正确的电话号码格式。</font>");
        jQuery.validator.addMethod("zipCodeCheck", function(value, element) {
            return this.optional(element) || /^[0-9]/.test(value);
        }, "<font color=red size=2>只能是6位数字。</font>");
        jQuery.validator.addMethod("emailCheck", function(value, element) {
            checkEmail();
            if (document.getElementById("showMsg").value == "success") {
                document.getElementById("showMsg").value = "";
                return true;
            } else {
                return false;
            }
        }, "<font color=red size=2>该邮箱已被注册。</font>");


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
                companyWebsite:{required:"<font color=red size=2>请输入网站域名。</font>",maxlength:"<font color=red size=2>您输入的网站域名长度超过{0}个字符。</font>"},
                registrationName:{required:"<font color=red size=2>请输入商户名称。</font>",maxlength:"<font color=red size=2>您输入的商户名称长度超过{0}个字符。</font>"},
                belongToArea:{required:"<font color=red size=2>请输入所在区域。</font>",maxlength:"<font color=red size=2>您输入的所在区域长度超过{0}个字符。</font>"},
                belongToBusiness:{required:"<font color=red size=2>请选择所属行业。</font>"},
                bizMan:{required:"<font color=red size=2>请输入联系人。</font>",maxlength:"<font color=red size=2>您输入的联系人长度超过{0}个字符。</font>"},
                bizMPhone:{required:"<font color=red size=2>请输入手机号码。</font>",maxlength:"<font color=red size=2>您输入的手机号码长度超过{0}个字符。</font>"},
                bizPhone:{maxlength:"<font color=red size=2>您输入的电话号码长度超过{0}个字符。</font>"},
                bizEmail:{required:"<font color=red size=2>请输入邮箱。</font>",email:"<font color=red size=2>请输入正确的邮箱格式。</font>",maxlength:"<font color=red size=2>您输入的邮箱长度超过{0}个字符。</font>"},
                officeLocation:{required:"<font color=red size=2>请输入通信地址。</font>",maxlength:"<font color=red size=2>您输入的通信地址长度超过{0}个字符。</font>"},
                zipCode :{required:"<font color=red size=2>请输入邮编。</font>",maxlength:"<font color=red size=2>您输入的邮编长度超过{0}个字符。</font>"}
            }
        });
    });

</script>

</body>
</html>