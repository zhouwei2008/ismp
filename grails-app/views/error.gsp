<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-store"/>
    <meta http-equiv="expires" content="0"/>
    <title>吉高-信息提示</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <g:javascript library="jquery"/>
</head>
<body>

    <div class="main_box">
        <div class="tipsico">
            <g:if test="${type=='ok'}"><img src="${resource(dir:'images/info',file:'Gnome.png')}" width="64" height="64" /></g:if>
            <g:elseif test="${type=='warn'}"><img src="${resource(dir:'images/info',file:'Emblem.png')}" width="64" height="64" /></g:elseif>
            <g:else><img src="${resource(dir:'images/info',file:'error.png')}" width="64" height="64" /></g:else>
        </div>
        <div class="tipstxt">
            <p>
                <b style="color:red; font-size:24px;"><g:if test="${type=='error'}">错误</g:if><g:elseif test="${type=='ok'}">操作成功</g:elseif><g:elseif test="${type=='warn'}">提示</g:elseif><g:else>页面不存在</g:else></b>
            </p>
            <p>
                <g:if test="${msg}">${msg}</g:if>
                    <g:elseif test="${succcount||failcount}">
                        <g:if test="${succcount}"> 交易成功<font color="green">${succcount}</font>笔 </g:if>
                        <g:if test="${failcount}"> 交易失败<font color="green">${failcount}</font>笔</g:if>
                    </g:elseif>
                <g:elseif test="${succTransferCount||failTransferCount}">
                    转账审批<font color="red">${totalCount}</font>笔，
                    <g:if test="${succTransferCount}">转账审批成功<font color="green">${succTransferCount}</font>笔</g:if>
                    <g:if test="${failTransferCount}">转账审批失败<font color="green">${failTransferCount}</font>笔
                         失败描述：${failReson}
                    </g:if>
                </g:elseif>
                <g:else>
                    您要访问的页面不存在，请检查您输入的网址。
                </g:else>
            </p>
            <p>
                <g:if test="${msg=='设置登录密码成功'}">
                    <input type="button" class="btn mglf10" value="登陆" style="margin-left:16px" onclick="window.location='${createLink(controller:'login',action:'login')}'"/>
                </g:if>
                <g:if test="${msg=='商户绑定手机成功'}">
                    <input type="button" class="btn mglf10" value="登陆" style="margin-left:16px" onclick="window.location='${createLink(controller:'login',action:'login')}'"/>
                </g:if>
            </p>
        </div>
    </div>
    %{--<!--  error--}%
      	%{--<div class="message">--}%
		%{--<strong>Error ${request.'javax.servlet.error.status_code'}:</strong> ${request.'javax.servlet.error.message'.encodeAsHTML()}<br/>--}%
		%{--<strong>Servlet:</strong> ${request.'javax.servlet.error.servlet_name'}<br/>--}%
		%{--<strong>URI:</strong> ${request.'javax.servlet.error.request_uri'}<br/>--}%
		%{--<g:if test="${exception}">--}%
	  		%{--<strong>Exception Message:</strong> ${exception.message?.encodeAsHTML()} <br />--}%
	  		%{--<strong>Caused by:</strong> ${exception.cause?.message?.encodeAsHTML()} <br />--}%
	  		%{--<strong>Class:</strong> ${exception.className} <br />--}%
	  		%{--<strong>At Line:</strong> [${exception.lineNumber}] <br />--}%
	  		%{--<strong>Code Snippet:</strong><br />--}%
	  		%{--<div class="snippet">--}%
	  			%{--<g:each var="cs" in="${exception.codeSnippet}">--}%
	  				%{--${cs?.encodeAsHTML()}<br />--}%
	  			%{--</g:each>--}%
	  		%{--</div>--}%
		%{--</g:if>--}%
  	%{--</div>--}%
        %{--<g:if test="${exception}">--}%
          %{--<g:each in="${exception.stackTraceLines}">--}%
              %{--${it.encodeAsHTML()}--}%
          %{--</g:each>--}%
        %{--</g:if>--}%
     %{---->--}%
</body>
</html>
