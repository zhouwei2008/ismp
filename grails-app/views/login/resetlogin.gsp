<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-store"/>
    <meta http-equiv="expires" content="0"/>
    <meta name="layout" content="main"/>
    <title>吉高-设置登录密码</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <g:javascript library="jquery"/>
</head>
<body>
<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <div class="tipsico"><img src="${resource(dir:'images/info',file:'Gnome.png')}" width="64" height="64" /></div>
        <div class="tipstxt">
            <p>系统已经发送一封重置登录密码的邮件到您的邮箱中，请登录邮箱，点击邮件里面的链接重新设置登录密码。</p>

         </div>

        <div class="Content800 clearFloat">
            <div class="labelText fl width150">&nbsp;</div>
            <div class="labelContent fl">
                <button class="btn-default" onclick="window.location='${createLink(controller:'login',action:'forget_login')}'">确定</button>
                <button class="btn-default" onclick="window.location='${createLink(controller:'login',action:'login')}'">取消</button>
            </div>
        </div>
    </div>
</div>
<!--内容区结束-->
</div>
</body>
</html>
