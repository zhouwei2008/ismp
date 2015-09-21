<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-登录日志</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script>
        function search(obj){
            if(obj){
                if($("offset"))
                    $("offset").value=0;
            }
            document.getElementById("searchform").submit();
        }
    </script>
</head>
<body>
<form action="index" method="post" name="searchform" id="searchform">
    <!--内容区开始-->
    <div class="InContent">
        <div class="searchForm">
            <h1>登录日志</h1>
        </div>
        <!--搜索结果开始-->
        <div class="searchResult">
            <div class="trdeDetail_grid">
                <table class="gridStyle_normal">
                    <thead>
                    <tr>
                        <td>操作员</td>
                        <td>操作名称</td>
                        <td>IP地址</td>
                        <td class="longData">操作时间</td>
                        <td>登陆结果</td>
                    </tr>
                    </thead>
                    <tbody>
                      <g:each in="${cmLoginLogList}" status="i" var="cmLoginLog">
                            <tr>
                                <td>${cmLoginLog.customerOperator.name}</td>
                                <td>${cmLoginLog.loginCertificate}</td>
                                <td>${cmLoginLog.loginIp}</td>
                                <td>${cmLoginLog.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                                <td>${cmLoginLog.loginResult}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
                <div class="tableFoot clearFloat">
                    <g:pageNavi total="${cmLoginLogListTotal}"/>
                </div>
            </div>
        </div>
        <!--搜索结果结束-->

    </div>
    <!--内容区结束-->
</form>
</body>
</html>
