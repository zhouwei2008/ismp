<%@ page import="model.Model" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-接口证书管理</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script>
        function search(obj){
            function createCert() {
                window.location.href = "${createLink(controller:'agentpay',action:'certedit')}";
            }
        }
    </script>
</head>
<body>
<!--内容区开始-->
<div class="InContent">
    <!--搜索结果开始-->
    <div class="searchResult">
    <div class="searchForm">
        <h1>证书管理</h1>
    </div>
        <div class="trdeDetail_grid">
            <table class="gridStyle_normal">
                <thead>
                <tr>
                    <td>证书类型</td>
                    <td class="longData">上传时间</td>
                    <td class="btnArea">操作</td>
                </tr>
                </thead>
                <tbody>
                    <g:each in="${certList}" status="i" var="cert">
                        <tr>
                            <td>${cert.certName}</td>
                            <td>${cert.certDate?.format('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>
                                <a href="${createLink(controller:'agentpay',action:'certedit')}/${cert.id}" style="color:blue">更新</a>
                            </td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                <g:link controller="agentpay" action="downcert" class="downCount">吉高委托结算证书下载</g:link>
            </div>
        </div>

        <div class="Content100 clearFloat">
            <input type="button" class="btn-default" onclick="window.location.href='${createLink(controller:'agentpay',action:'certedit')}';" value="新增证书" />
        </div>
    </div>
    <!--搜索结果结束-->
</div>
</body>
</html>
