
<%@ page import="dsf.TbAgentpayInfo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tbAgentpayInfo.label', default: 'TbAgentpayInfo')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'tbAgentpayInfo.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="batchAmount" title="${message(code: 'tbAgentpayInfo.batchAmount.label', default: 'Batch Amount')}" />
                        
                            <g:sortableColumn property="batchBizId" title="${message(code: 'tbAgentpayInfo.batchBizId.label', default: 'Batch Biz Id')}" />
                        
                            <g:sortableColumn property="batchBizType" title="${message(code: 'tbAgentpayInfo.batchBizType.label', default: 'Batch Biz Type')}" />
                        
                            <g:sortableColumn property="batchCount" title="${message(code: 'tbAgentpayInfo.batchCount.label', default: 'Batch Count')}" />
                        
                            <g:sortableColumn property="batchCurrNum" title="${message(code: 'tbAgentpayInfo.batchCurrNum.label', default: 'Batch Curr Num')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tbAgentpayInfoInstanceList}" status="i" var="tbAgentpayInfoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tbAgentpayInfoInstance.id}">${fieldValue(bean: tbAgentpayInfoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tbAgentpayInfoInstance, field: "batchAmount")}</td>
                        
                            <td>${fieldValue(bean: tbAgentpayInfoInstance, field: "batchBizId")}</td>
                        
                            <td>${fieldValue(bean: tbAgentpayInfoInstance, field: "batchBizType")}</td>
                        
                            <td>${fieldValue(bean: tbAgentpayInfoInstance, field: "batchCount")}</td>
                        
                            <td>${fieldValue(bean: tbAgentpayInfoInstance, field: "batchCurrNum")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tbAgentpayInfoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
