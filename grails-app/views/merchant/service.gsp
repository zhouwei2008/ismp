<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-服务信息</title>
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
    <div class="searchForm">
        <h1>服务信息</h1>
    </div>


    <!--搜索结果开始-->
    <div class="searchResult">
        <div class="trdeDetail_grid">
            <table class="gridStyle_normal">
                <thead>
                <tr>
                    <td>服务类型</td>
                    <td>生效时间</td>
                    <td>到期时间</td>
                    <td>状态</td>
                    <td class="btnArea">详情</td>

                </tr>
                </thead>
                <tbody>
                <g:each in="${boCustomerServiceList}" status="i" var="boCustomerService">
                    <tr>
                        <td>${boCustomerService.serviceMap[boCustomerService.serviceCode]}</td>
                        <td>${boCustomerService.startTime?.format('yyyy-MM-dd HH:mm:ss')}</td>
                        <td>${boCustomerService.endTime?.format('yyyy-MM-dd HH:mm:ss')}</td>
                        <td>
                            <g:if test="${boCustomerService.enable==true}">
                                <strong style="color:green">有效</strong>
                            </g:if>
                            <g:else>
                                <strong style="color:red">过期</strong>
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${session.level3Map != null && session.level3Map['merchant/servicedetail'] != null}">
                                <a href="${request.contextPath}/${session.level3Map['merchant/servicedetail'].modelPath}/${boCustomerService.id}" class="gridBtn_normal">查看</a>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
    <!--搜索结果结束-->

</div>
<!--内容区结束-->

</body>
</html>
