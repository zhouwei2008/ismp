<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="layout" content="main" />
<title>吉高-转账</title>
	<link href="${resource(dir:'css',file:'xtgl.css')}" rel="stylesheet" type="text/css" />
	<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'mystyle.css')}" media="all" />

	<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
	<g:javascript library="prototype" />

</head>
<body>



<!-- 主体 -->
<div id="content">
    <div id="main">
        <br>
        <h1><g:if test="${transferResult==null}">错误</g:if>
            <g:elseif test="${!transferResult.result}">操作失败</g:elseif>
            <g:else>转账完成</g:else>
        </h1>

        <div class="content_t">
            <g:if test="${transferResult==null}">
                <img src="${resource(dir:'images',file:'error.gif')}" width="89" height="89" />
            </g:if>
            <g:elseif test="${transferResult.result}">
                <img src="${resource(dir:'images',file:'ok.gif')}" width="89" height="89" />
            </g:elseif>
            <g:else>

                <!-- 显示操作信息 -->
                <div class="balanceShow" style="margin-left:10px;margin-top:15px">
                    <span class=" fn-ml15">可用余额：<em class="ft-green"><g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/></em> 元</span>
                    <br><br>
                        <span class=" fn-ml15">成功总笔数：<em class="ft-green">${transferResult==null?0:transferResult.sucNum}</em></span>
                        <span class=" fn-ml15">成功总金额：<em class="ft-green"><g:formatNumber currencyCode="CNY" number="${transferResult==null?0:transferResult.sucMoney/100}" type="currency"/></em> 元</span>
                        <br><br>
                        <span class=" fn-ml15">失败总笔数：<em class="ft-green">${transferResult==null?0:transferResult.failNum}</em></span>
                        <span class=" fn-ml15">失败总金额：<em class="ft-green"><g:formatNumber currencyCode="CNY" number="${transferResult==null?0:transferResult.failMoney/100}" type="currency"/></em> 元</span>
                        <br><br>
                        %{--<span class=" fn-ml15">成功笔数：<em class="ft-green">${transferResult==null?"":transferResult.sucNum}</em></span>--}%
                        <span class=" fn-ml15"><a href="${createLink(controller:'transfer',action:'getBatchTemplateErrorFile',params:[file:transferResult.failFile])}" title="Excel格式" class="download-excel">下载失败报告</a></span>
                </div>

            </g:else>
        </div>
    </div>
</div>
</body>
</html>
