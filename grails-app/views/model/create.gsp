

<%@ page import="model.Model" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'model.label', default: 'Model')}" />
        <title>添加新功能</title>
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="modelList"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>添加新功能</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${modelInstance}">
            <div class="errors">
                <g:renderErrors bean="${modelInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'model.button.submit.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
