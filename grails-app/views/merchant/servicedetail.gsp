<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-服务详情</title>
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
<div class="InContent">
    <div class="boxContent">
        <h1>服务详细信息</h1>
        <div class="normalContent">
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">合同号：</div>
                <div class="labelText fl">${service?.contractNo}</div>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width150">服务类型：</div>
                <div class="labelText fl">${service?.serviceMap[service.serviceCode]}</div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">生效时间：</div>
                <div class="labelText fl">${service.startTime?.format('yyyy-MM-dd HH:mm:ss')}</div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">到期时间：</div>
                <div class="labelText fl">${service.endTime?.format('yyyy-MM-dd HH:mm:ss')}</div>
            </div>

            <div class="Content800 clearFloat">
                <div class="labelText fl width150">服务状态：</div>
                <div class="labelText fl">${service.enable == true ? "有效" : "过期"}</div>
            </div>

            <g:if test="${service?.serviceCode=='agentpay' ||  service?.serviceCode=='agentcoll'}">
                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">对公手续费：</div>
                    <div class="labelText fl"><g:formatNumber number="${service?.procedureFee}" type="currency" currencyCode="CNY"/>/笔</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">对私手续费：</div>
                    <div class="labelText fl"><g:formatNumber number="${service?.perprocedureFee}" type="currency" currencyCode="CNY"/>/笔</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">单笔限额：</div>
                    <div class="labelText fl"><g:formatNumber number="${service?.limitMoney}" type="currency" currencyCode="CNY"/></div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">当日限笔：</div>
                    <div class="labelText fl"><g:formatNumber number="${service?.dayLimitTrans}"/></div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">当日限额：</div>
                    <div class="labelText fl"><g:formatNumber number="${service?.dayLimitMoney}" type="currency" currencyCode="CNY"/></div>
                </div>
            </g:if>
            <g:else>
                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">服务参数：</div>
                    <div class="labelText fl">${service?.serviceParams}</div>
                </div>
           </g:else>
        </div>
    </div>
</div>
<!--内容区结束-->
</body>
</html>
