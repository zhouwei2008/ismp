

<%@ page import="model.Model" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'model.label', default: 'Model')}" />
        <title>编辑${entityName}</title>
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="modelList"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>编辑${entityName}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${modelInstance}">
            <div class="errors">
                <g:renderErrors bean="${modelInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${modelInstance?.id}" />
                <g:hiddenField name="version" value="${modelInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="modelName"><g:message code="model.modelName.label" default="Model Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'modelName', 'errors')}">
                                    <g:textField name="modelName" value="${modelInstance?.modelName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="modelPath"><g:message code="model.modelPath.label" default="Model Path" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'modelPath', 'errors')}">
                                    <g:textField name="modelPath" value="${modelInstance?.modelPath}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="operate"><g:message code="model.operate.label" default="Operate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'operate', 'errors')}">
                                    <g:textField name="operate" value="${modelInstance?.operate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="parentPoint"><g:message code="model.parentPoint.label" default="Parent Point" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'parentPoint', 'errors')}">
                                    <g:textField name="parentPoint" value="${modelInstance?.parentPoint}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="point"><g:message code="model.point.label" default="Point" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'point', 'errors')}">
                                    <g:textField name="point" value="${modelInstance?.point}" />
                                </td>
                            </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="modelLevel"><g:message code="model.modelLevel.label" default="modelLevel" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'modelLevel', 'errors')}">
                                <g:textField name="modelLevel" value="${modelInstance?.modelLevel}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="modelindex"><g:message code="model.modelindex.label" default="modelindex" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'modelindex', 'errors')}">
                                <g:textField name="modelindex" value="${modelInstance?.modelindex}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="serviceCode"><g:message code="model.serviceCode.label" default="serviceCode" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: modelInstance, field: 'serviceCode', 'errors')}">
                                <g:textField name="serviceCode" value="${modelInstance?.serviceCode}" />
                            </td>
                        </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
