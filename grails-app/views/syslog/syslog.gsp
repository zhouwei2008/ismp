<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-登录日志</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
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
<form action="syslog" method="post" name="searchform" id="searchform" style="width:960px; margin:0 auto">
    <div class="serchlitst">

        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <table class="tlb1">
          <tr>
            <th class="txtCenter" scope="col">姓名</th>
            <th class="txtCenter" scope="col">登录账号</th>
            <th class="txtCenter" scope="col">登录IP</th>
            <th class="txtCenter" scope="col">登录时间</th>
            <th class="txtCenter" scope="col">登录结果</th>
          </tr>
            <g:each in="${cmLoginLogList}" status="i" var="cmLoginLog">
                <tr>
                    <td class="txtCenter" scope="col">${cmLoginLog.customerOperator.name}</td>
                    <td class="txtCenter" scope="col">${cmLoginLog.loginCertificate}</td>
                    <td class="txtCenter" scope="col">${cmLoginLog.loginIp}</td>
                    <td class="blue" scope="col">${cmLoginLog.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                    <td class="txtCenter" scope="col">${cmLoginLog.loginResult}</td>
                </tr>
            </g:each>
        </table>
        <div class="page">
          <g:pageNavi total="${cmLoginLogListTotal}"/>
          </div>
    </div>
</form>
</body>
</html>
