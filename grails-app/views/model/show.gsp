
<%@ page import="model.Model" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'model.label', default: 'Model')}" />
        <title>功能${entityName}</title>
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="modelList"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>功能${entityName}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="model.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="model.modelName.label" default="Model Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "modelName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="model.modelPath.label" default="Model Path" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "modelPath")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="model.operate.label" default="Operate" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "operate")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="model.parentPoint.label" default="Parent Point" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "parentPoint")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="model.point.label" default="Point" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "point")}</td>
                            
                        </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="model.modelLevel.label" default="modelLevel" /></td>

                        <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "modelLevel")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="model.modelindex.label" default="modelindex" /></td>

                        <td valign="top" class="value">${modelInstance.modelindex}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="model.serviceCode.label" default="serviceCode" /></td>

                        <td valign="top" class="value">${fieldValue(bean: modelInstance, field: "serviceCode")}</td>

                    </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${modelInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
