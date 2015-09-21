<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>修改支付密码</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-store"/>
    <meta http-equiv="expires" content="0"/>
    <g:javascript library="prototype" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <style>
    #message{
        color:green
    }
    #error{
        color:red
    }
    </style>
    <script>
        function step1()
        {
            $("message").innerText='您的操作已提交，请稍候...';
            $("btn1").hide();
            $("error").innerText='';
            new Ajax.Request('${createLink(controller:'operator',action:'sendDynamicKey')}',
                    {
                        asynchronous:true,
                        evalScripts:true,
                        onSuccess:function(e){
                            step1_res(e)
                        }
                    }
            );
            return false;
        }
        function step1_res(response)
        {
            $("message").innerText='';
            if(response.responseText!='ok')
            {
                $("error").innerHTML=response.responseText;
                $("btn1").show();
            }else{
                $("error").innerHTML='';
                $("btn1").hide();
                $("layer2").show();
            }
        }

        function savePaypass()
        {
            $("message").innerHTML='您的操作已提交，请稍候...';
            $("btn2").hide();
            $("error").innerHTML='';
            var formparams="mobile_captcha="+$("mobile_captcha").value+"&newpassword="+$("newpassword").value+"&confirm_newpassword="+$("confirm_newpassword").value;
            new Ajax.Request('${createLink(controller:'operator',action:'savechangepaypass')}',{asynchronous:true,evalScripts:true,onSuccess:function(e){savePaypass_res(e)},parameters:formparams});
            return false;
        }
        function savePaypass_res(response)
        {
            $("message").innerText='';
            if(response.responseText!='ok')
            {
                $("error").innerHTML=response.responseText;
                $("btn2").show();
            }else{
                $("error").innerHTML='';
                $("message").innerHTML='修改支付密码成功';
            }
        }
    </script>
</head>
<body>
<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>修改支付密码</h1>
        <div class="normalContent">
            <p class="tipsText">一、点击“获取验证码”按钮，系统会发送验证码到您已绑定的手机；<br />二、输入验证码、新的支付密码并确认后，点击确定完成修改。</p>


            <div class="Content500 clearFloat">
                <div class="labelText fl width150">手机验证码：</div>
                <div class="labelContent fl"><input id="mobile_captcha" name="mobile_captcha" class="normalInput" type="text" maxlength="40"/>
                    <input type="button" value="获取验证码" id="btn1" class="findCustBtn" onclick="step1()"/>
                </div>
            </div>

            <div class="Content500 clearFloat">
                <div class="labelText fl width150">请输入新的支付密码：</div>
                <div class="labelContent fl"><input id="newpassword" name="newpassword" class="normalInput" type="password" maxlength="40"></div>
                <div style="clear:left;margin-left:150px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
            </div>

            <div class="Content500 clearFloat">
                <div class="labelText fl width150">请确认新的支付密码：</div>
                <div class="labelContent fl"><input id="confirm_newpassword" name="confirm_newpassword" type="password" maxlength="40"></div>
                <div style="clear:left;margin-left:150px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
            </div>

            <div class="Content500 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <input type="button" value="下一步" id="btn2" class="btn-default" onclick="savePaypass();">
            </div>
           </div>

            <div id="message"></div>
            <div id="error"></div>
        </div>
    </div>
</div>
<!--内容区结束-->


</body>
</html>