<%@ page import="model.Model" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-角色管理</title>
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
            <h1>角色管理</h1>
        </div>
        <!--搜索结果开始-->
        <div class="searchResult">
            <div class="trdeDetail_grid">
                <table class="gridStyle_normal">
                    <thead>
                    <tr>
                        <td>角色名称</td>
                        <td>创建时间</td>
                        <td>状态</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roleInstanceList}" status="i" var="roleList">
                        <tr>
                            <td>${roleList.roleName}</td>
                            <td>${roleList.createTime.format("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${roleList.roleStatusMap[roleList.roleStatus]}</td>
                            <td>
                                <g:if test="${roleList.customerId != '0'}">
                                    <g:if test="${(roleList.id != session.role.id[0]) && (roleList.customerId != '00')}">
                                        <g:if test="${session.level3Map != null && session.level3Map['model/list'] != null}">
                                            <a href="${request.contextPath}/${session.level3Map['model/list'].modelPath}/${roleList.id}" class="gridBtn_normal">权限设置</a>
                                        </g:if>
                                        <g:if test="${session.level3Map != null && session.level3Map['role/edit'] != null}">
                                            <g:if test="${session.level3Map != null && session.level3Map['model/list'] != null}">|</g:if>
                                            <a href="${request.contextPath}/${session.level3Map['role/edit'].modelPath}/${roleList.id}" class="gridBtn_normal">名称修改</a>
                                        </g:if>
                                        <g:if test="${session.level3Map != null && session.level3Map['role/updateStatus'] != null}">
                                            <g:if test="${(session.level3Map != null && session.level3Map['model/list'] != null) || (session.level3Map != null && session.level3Map['role/edit'] != null)}">|</g:if>
                                            <a href="${request.contextPath}/${session.level3Map['role/updateStatus'].modelPath}/${roleList.id}" class="gridBtn_normal"><g:if test="${roleList.roleStatus=='normal'}">关闭</g:if><g:elseif test="${roleList.roleStatus=='close'}">启动</g:elseif></a>
                                        </g:if>
                                        <g:if test="${session.level3Map != null && session.level3Map['role/remove'] != null}">
                                            <g:if test="${(session.level3Map != null && session.level3Map['model/list'] != null) || (session.level3Map != null && session.level3Map['role/edit'] != null) || (session.level3Map != null && session.level3Map['role/updateStatus'] != null)}">|</g:if>
                                            <a href="${request.contextPath}/${session.level3Map['role/remove'].modelPath}/${roleList.id}" onclick="return confirm('确定删除此角色吗？')" class="gridBtn_normal">删除</a>
                                        </g:if>
                                    </g:if>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="tableFoot clearFloat">
                    <g:pageNavi total="${roleInstanceTotal}"/>
                </div>
            </div>

            <g:if test="${session.level3Map != null && session.level3Map['role/create'] != null}">
                    <div class="Content100 clearFloat">
                        <input type="button" class="btn-default" onclick="window.location.href='${createLink(controller:'role',action:'create')}';" value="新增角色" />
                    </div>
            </g:if>
        </div>
        <!--搜索结果结束-->
    </div>
    <!--内容区结束-->
</form>
</body>
</html>
