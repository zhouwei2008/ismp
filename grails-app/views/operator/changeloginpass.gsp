<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>修改登录密码</title>
    <meta http-equiv="content-type" content="text/html; charset=GBK"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-store"/>
    <meta http-equiv="expires" content="0"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
</head>
<body>

<!--内容区开始-->
<form action="${createLink(controller:'operator',action: 'savechangeloginpass')}" method="post">
<div class="InContent">
    <div class="boxContent">
        <h1>修改登录密码</h1>
        <div class="normalContent">
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">请输入当前登录密码：</div>
                <div class="labelContent fl">
                      <input id="password" name="password" class="normalInput" type="password" maxlength="40"/>
                      <span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span>
                </div>

            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">请输入新的登录密码：</div>
                <div class="labelContent fl"><input id="newpassword" name="newpassword" class="normalInput" type="password" maxlength="40"/>
                    <span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">请确认新的登录密码：</div>
                <div class="labelContent fl"><input id="confirm_newpassword" name="confirm_newpassword" class="normalInput" type="password" maxlength="40"/>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <input type="submit" class="btn-default" value="下一步">
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <g:if test="${flash.message}">
                        <div class="message">${flash.message.encodeAsHTML()}</div>
                    </g:if>
                </div>
            </div>

        </div>
    </div>
</div>
</form>
<!--内容区结束-->

</body>
</html>