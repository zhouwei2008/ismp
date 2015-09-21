<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-修改角色</title>
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
<g:form action="update" name="actionForm">
    <g:hiddenField name="id" value="${roleInstance?.id}"></g:hiddenField>
    <g:hiddenField name="model" value="${roleInstance?.model}"></g:hiddenField>
    <g:hiddenField name="customerId" value="${roleInstance?.customerId}"></g:hiddenField>
<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>角色管理</h1>
        <div class="normalContent">
            <g:if test="${flash.message}">
                <div class="message" style="color: #FF3F00">${flash.message}</div>
            </g:if>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">所属系统：</div>
                <div class="labelContent fl">${roleInstance?.belongSys}</div>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">角色名称：</div>
                <div class="labelContent fl">
                    <input type="text"  class="normalInput" id="roleName" name="roleName" value="${roleInstance?.roleName}" maxlength="32"/>
                </div>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">创建时间：</div>
                <div class="labelContent fl"> ${roleInstance?.createTime.format("yyyy-MM-dd HH:mm:ss")}</div>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">状态：</div>
                <div class="labelContent fl">
                    ${roleInstance?.roleStatusMap[roleInstance?.roleStatus]}
                </div>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <input type="button"  class="btn-default" value="确定" onclick="this.form.submit()" />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" class="btn-default" onclick="window.location.href='${createLink(controller:'role',action:'list')}';" value="取消" />
                </div>
            </div>

        </div>
    </div>
</div>
<!--内容区结束-->
</g:form>
</body>
</html>
