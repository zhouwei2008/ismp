<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-修改操作员信息</title>
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
<g:form action="update" name="actionForm">
    <g:hiddenField name="id" value="${cmCustomerOperatorInstance?.id}" />
    <g:hiddenField name="version" value="${cmCustomerOperatorInstance?.version}" />
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
                <div class="labelText fl width150">角色：</div>
                <div class="labelContent fl">
                    <g:select name="roleSet" class="selectStyle" from="${cmCustomerOperatorInstance.roleSetMap}" value="${cmCustomerOperatorInstance?.roleSet.encodeAsHTML()}" optionKey="key" optionValue="value" />
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">姓名：</div>
                <div class="labelContent fl">
                    <input type="text" name="name" id="name" class="normalInput" value="${cmCustomerOperatorInstance?.name.encodeAsHTML()}" maxLength="32"/>
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

<script>
    new AP.widget.Validator(
            {
                formId:"actionForm",
                ruleType:"id",
                onSubmit:true,
                onSuccess: function() {

                },
                rules:{
                    'name':{required:true,desc:"姓名"}
                }
            });
</script>
</body>
</html>
