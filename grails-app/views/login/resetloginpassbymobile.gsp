<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-设置登录密码</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="prototype"/>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>
<form action="${createLink(controller:'login',action: 'saveresetloginpass')}" name="actionForm" id="actionForm">
    <input type="hidden" value="${createLink(controller:'login',action: 'sendCaptcha')}" id="actionUrl"/>
    <input type="hidden" name="method" value="mobile">
    <input type="hidden" name="mobile" value=${mobile}>
    <input type="hidden" id="id" name="id" value="${cmCustomerOperatorId}">
<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>设置登陆密码：</h1>
        <div class="normalContent">

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">手机验证码：</div>
                <div class="labelContent fl">
                    <input type="text" class="normalInput" style="width:70px;" id="mobile_captcha" name="mobile_captcha" value="" maxLength="6"/>
                    <input type="button" class="findCustBtn" id="getCaptcha" name="btn" value="获取验证码" />
                    <span class="rightTips">点击“获取验证码”按钮，输入您手机上收到的验证码。</span>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">请输入您的新登录密码：</div>
                <div class="labelContent fl">
                    <input type="password" id="password" class="normalInput" name="password" value="" maxLength="64"/>
                    <span class="rightTips">密码长度必须大于8位，且必须是数字、字母和特殊字符组合而成<</span>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">请确认您的新登录密码：</div>
                <div class="labelContent fl">
                    <input type="password" id="confirm_password" class="normalInput" name="confirm_password" value="" maxLength="64"/>
                    <span class="rightTips">密码长度必须大于8位，且必须是数字、字母和特殊字符组合而成</span>
                    <div id="loginMsg" style="color:red">${flash.message?.encodeAsHTML()}</div>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <input type="submit" class="btn-default" name="submit_btn" value="下一步">
                    <input type="button" class="btn-default" value="登陆吉高" style="margin-left:16px" onclick="window.location='${createLink(controller:'login',action:'login')}'"/>
                </div>
            </div>

        </div>
    </div>
</div>
<!--内容区结束-->
</form>
<script>

    //    new AP.widget.Validator(
    //    {
    //        formId:"actionForm",
    //        ruleType:"id",
    //        onSubmit:true,
    //        onSuccess: function()
    //        {
    //            return false
    //        },rules:{
    //                'mobile_captcha':{
    //                    minLength:6,
    //                    required:true,
    //                    depends:true,
    //                    desc:"手机验证码"
    //                },
    //                'password':{
    //                    minLength:6,
    //                    required:true,
    //                    depends:true,
    //                    desc:"新登录密码"
    //                },
    //                'confirm_password':{
    //                    minLength:6,
    //                    required:true,
    //                    depends:true,
    //                    desc:"确认新登录密码"
    //                }
    //        }
    //    });
    E.on('mobile_captcha','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });
    E.on('password','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });

    E.on(D.get("getCaptcha"),"click",function()
    {
        sendMobileCaptchaAjax();
        document.getElementById("getCaptcha").disabled = true;
    });
    var timer;
    var sendMobileCaptchaAjax=function()
    {
        var a=D.get("actionUrl").value;
        a+="?id="+escape(D.get("id").value);

        var b=
        {
            success:function(c)
            {
                var d=c.responseText;
                if(d!="ok")
                {
                    if(d.indexOf("<title>登录</title>")>0)
                    {
                        alert("您的登录已超时，请重新登录");
                        self.parent.AP.widget.xBox.hide();
                        self.parent.location.reload();
                    }else{
                        alert(d);
                    }
                }else{
                    alert("系统已经发送一条手机验证码到您绑定的手机上了，请查收");
                    timer=setInterval("time1()",1000);
                }
            },failure:function(c)
        {
            AP.widget.xBox.hide();
            alert("脚本出现错误,请稍后再试")
        }
        };
        AP.ajax.asyncRequest("POST",a,b)
    };
    var time=60;
    function time1()
    {
        if(time==0){
            document.getElementById("getCaptcha").disabled  = false;
            document.getElementById("getCaptcha").value =" 获取验证码 ";
            clearInterval(timer);
            time = 60;
            return false;
        }
        time = time-1;
        document.getElementById("getCaptcha").value = "  剩 余("+time+")  ";
    }
</script>
</body>
</html>