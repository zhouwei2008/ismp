<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-接口证书上传</title>
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
<g:form method="post" controller="agentpay" action="certupload" enctype="multipart/form-data">
<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>代付</h1>
        <div class="normalContent">
            <g:if test="${flash.message}">
                <div class="message" style="color: #FF3F00">${flash.message}</div>
            </g:if>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">证书类型：</div>
                <div class="labelContent fl">
                     <g:select name="type" class="selectStyle" value="${params.type}" from="${serviceMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择证书类型-']}" />
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">选择证书：</div>
                <div class="labelContent fl">
                    <input type="file"   name="certFile" style="width:350px;height:24px;"/>
                </div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">&nbsp;</div>
                <div class="labelContent fl">
                    <input type="button"  class="btn-default" value="确定" onclick="this.form.submit()" />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" class="btn-default" onclick="window.location.href='${createLink(controller:'agentpay',action:'certlist')}';" value="取消" />
                </div>
            </div>

        </div>
    </div>
</div>
</g:form>
<!--内容区结束-->
</body>
</html>
