<%@ page import="role.Role" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-操作员管理</title>
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
<form action="list" method="post" name="searchform" id="searchform">
<!--内容区开始-->
<div class="InContent">
    <div class="searchForm">
        <h1>用户管理</h1>
    </div>
    <!--搜索结果开始-->
    <div class="searchResult">
        <div class="trdeDetail_grid">
            <table class="gridStyle_normal">
                <thead>
                <tr>
                    <td>姓名</td>
                    <td>Email</td>
                    <td>手机</td>
                    <td>角色</td>
                    <td>创建时间</td>
                    <td>状态</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${cmCustomerOperatorInstanceList}" status="i" var="cmCustomerOperator">
                    <tr>
                        <td>${cmCustomerOperator.name.encodeAsHTML()}</td>
                        <td>${cmCustomerOperator.defaultEmail.encodeAsHTML()}</td>
                        <td>${cmCustomerOperator.defaultMobile?cmCustomerOperator.defaultMobile.encodeAsHTML():"未绑定手机"}</td>
                        <td><g:if test="${cmCustomerOperator.roleSet}">${Role.get(Integer.valueOf(cmCustomerOperator.roleSet))?.roleName}</g:if></td>
                        <td>${cmCustomerOperator.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>${cmCustomerOperator.statusMap[cmCustomerOperator.status]}</td>
                        <td>
                            <g:if test="${cmCustomerOperator.id!=session.cmCustomerOperator.id}">
                                <g:if test="${session.level3Map != null && session.level3Map['operator/updateStatus'] != null}">
                                    <a href="${request.contextPath}/${session.level3Map['operator/updateStatus'].modelPath}/${cmCustomerOperator.id}" class="gridBtn_normal"><g:if test="${cmCustomerOperator.status=='normal'}">停用</g:if><g:if test="${cmCustomerOperator.status=='disabled'}">启用</g:if><g:if test="${cmCustomerOperator.status=='locked'}">解锁</g:if><g:if test="${cmCustomerOperator.status=='deleted'}">　　</g:if></a>
                                </g:if>
                                <g:if test="${session.level3Map != null && session.level3Map['operator/resetLoginPass'] != null}">
                                    <g:if test="${session.level3Map != null && session.level3Map['operator/updateStatus'] != null}">|</g:if>
                                    <a href="${request.contextPath}/${session.level3Map['operator/resetLoginPass'].modelPath}/${cmCustomerOperator.id}" class="gridBtn_normal">重置登录密码</a>
                                </g:if>
                                <g:if test="${session.level3Map != null && session.level3Map['operator/edit'] != null}">
                                    <g:if test="${(session.level3Map != null && session.level3Map['operator/updateStatus'] != null) || (session.level3Map != null && session.level3Map['operator/resetLoginPass'] != null)}">|</g:if>
                                    <a href="${request.contextPath}/${session.level3Map['operator/edit'].modelPath}/${cmCustomerOperator.id}" class="gridBtn_normal">修改</a>
                                </g:if>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <div class="tableFoot clearFloat">

                <g:pageNavi total="${cmCustomerOperatorInstanceTotal}"/>
            </div>
        </div>

        <g:if test="${session.level3Map != null && session.level3Map['operator/create'] != null}">
            <div class="Content100 clearFloat">
               <input type="button" class="btn-default" onclick="window.location.href='${createLink(controller:'operator',action:'create')}';" value="新增用户" />
            </div>
        </g:if>

    </div>
    <!--搜索结果结束-->

</div>
<!--内容区结束-->
</form>
</body>
</html>
