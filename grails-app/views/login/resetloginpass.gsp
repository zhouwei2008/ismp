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
<form action="${createLink(controller:'login',action: 'saveBindMobile')}" name="actionForm" id="actionForm">
    <input type="hidden" name="method" value="email">
    <input type="hidden" name="id" value="${cmCustomerOperatorId}">
    <input type="hidden" name="verification" value="${verification}">

    <!--内容区开始-->
    <div class="InContent">
        <div class="boxContent">
            <h1>用户管理</h1>
            <div class="normalContent">
                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">请输入您要绑定的手机号：</div>
                    <div class="labelContent fl"><input type="text" id="mobile" name="mobile" value="" maxLength="64"/></div>
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
    new AP.widget.Validator(
            {
                formId:"actionForm",
                ruleType:"id",
                onSubmit:true,
                onSuccess: function()
                {
                    return false
                },rules:{
                'mobile':{
                    minLength:11,
                    required:true,
                    depends:true,
                    desc:"手机号"
                }
            }
            });
    E.on('mobile','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });
</script>
</body>
</html>