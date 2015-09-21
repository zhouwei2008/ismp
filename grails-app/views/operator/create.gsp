<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-新增操作员</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>

<!--内容区开始-->
<g:form action="save" name="actionForm" >
    <input type="hidden" value="${createLink(controller:'operator',action: 'sendMobileCaptcha')}" id="actionUrl" name="actionUrl"/>
<div class="InContent">
    <div class="boxContent">
        <h1>用户管理</h1>
        <div class="normalContent">
            <g:if test="${flash.message}">
                <div class="message" style="color: #FF3F00">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cmCustomerOperatorInstance}">
                <div class="message">
                    <g:renderErrors bean="${cmCustomerOperatorInstance}" as="list" />
                </div>
            </g:hasErrors>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">角色名称：</div>
                <div class="labelContent fl">
                    <label for="roleSet"></label>
                    <g:select name="roleSet" class="selectStyle"  from="${cmCustomerOperatorInstance.roleSetMap}" value="${cmCustomerOperatorInstance?.roleSet?.encodeAsHTML()}" optionKey="key" optionValue="value"  />
                </div>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">姓名：</div>
                <div class="labelContent fl"><input type="text" class="normalInput" name="name" id="name" value="${cmCustomerOperatorInstance?.name?.encodeAsHTML()}" maxLength="32"/></div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">Email：</div>
                <div class="labelContent fl">
                    <input type="text" class="normalInput" id="defaultEmail" name="defaultEmail" value="${cmCustomerOperatorInstance?.defaultEmail?.encodeAsHTML()}" maxLength="64"/>
                    <span class="rightTips">此Email为登录时的账户名，不可更改。<br/>成功添加操作员后，请通知该操作员登录邮箱，根据邮件提示设置登录密码。</span>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">手机验证码：</div>
                <div class="labelContent fl">
                    <input type="text" class="normalInput" style="width:70px;" id="mobile_captcha" name="mobile_captcha" value="" maxLength="6"/>
                    <input type="button"  id="btn_reload" class="findCustBtn" name="btn"  value="获取验证码" />
                    <span class="rightTips">点击“获取验证码”按钮，输入您手机上收到的验证码。</span>
                </div>
            </div>


            <div class="Content800 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <input type="button"  class="btn-default" value="确定" onclick="this.form.submit()" />
                    <input type="button" class="btn-default" onclick="window.location.href='${createLink(controller:'operator',action:'list')}';" value="取消">
                </div>
            </div>
        </div>
    </div>
</div>
</g:form>
<!--内容区结束-->

<script type="text/javascript" language="javascript">

    E.on(D.get("btn_reload"),"click",function()
    {
        sendMobileCaptchaAjax();
        document.getElementById("btn_reload").disabled = true;
    });
    var timer;
    var sendMobileCaptchaAjax=function()
    {
        var a=D.get("actionUrl").value;

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
            document.getElementById("btn_reload").disabled  = false;
            document.getElementById("btn_reload").value =" 发送验证码 ";
            clearInterval(timer);
            time = 60;
            return false;
        }
        time = time-1;
        document.getElementById("btn_reload").value = "  剩 余("+time+")  ";
    }

    /*检查邮箱有效性*/
    E.on(D.get("defaultEmail"),"blur",function()
    {
        var a="checkEmailInfo?defaultEmail=" + encodeURL($('#defaultEmail').val().trim());

        var b=
        {

            success:function(c)
            {
                var d=c.responseText;
                if(d != "false")
                {
                    document.getElementById("checkresult").style.display = ""
                    document.getElementById("checkresult").innerText = d;
                }else{
                    if(d.indexOf("<title>登录</title>")>0)
                    {
                        alert("您的登录已超时，请重新登录");
                        self.parent.AP.widget.xBox.hide();
                        self.parent.location.reload();
                    }else{
                        alert(d);
                    }
                }
            }
        };
        AP.ajax.asyncRequest("POST",a,b)
    });

    E.on($('#defaultEmail'), "focus", function(e) {
        document.getElementById("checkresult").style.display = "none"
    });

    new AP.widget.Validator(
            {
                formId:"actionForm",
                ruleType:"id",
                onSubmit:true,
                onSuccess: function() {

                },
                rules:{
                    'name':{required:true,desc:"姓名"},
                    'mobile_captcha':{required:true,desc:"手机验证码"},
                    'defaultEmail':{type:'email',required:true,desc:"Email"}
                }
            });
</script>
</body>
</html>
