
<%@ page import="dsf.TbAgentpayInfo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tbAgentpayInfo.label', default: 'TbAgentpayInfo')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchAmount.label" default="Batch Amount" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchAmount")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchBizId.label" default="Batch Biz Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchBizid")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchBizType.label" default="Batch Biz Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchBiztype")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchCount.label" default="Batch Count" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchCount")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchCurrNum.label" default="Batch Curr Num" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchCurrnum")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchDate.label" default="Batch Date" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchDate")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchFinishDate.label" default="Batch Finish Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${tbAgentpayInfoInstance?.batchFinishdate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchId.label" default="Batch Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchRemark.label" default="Batch Remark" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchRemark")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchRemark1.label" default="Batch Remark1" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchRemark1")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchRemark2.label" default="Batch Remark2" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchRemark2")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchRemark3.label" default="Batch Remark3" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchRemark3")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchStatus.label" default="Batch Status" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchStatus")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchSysDate.label" default="Batch Sys Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${tbAgentpayInfoInstance?.batchSysdate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchType.label" default="Batch Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.batchVersion.label" default="Batch Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "batchVersion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.tbAgentpayDetailsInfo.label" default="Tb Agentpay Details Info" /></td>
                            
                            <td valign="top" class="value"><g:link controller="tbAgentpayDetailsInfo" action="show" id="${tbAgentpayInfoInstance?.tbAgentpayDetailsInfos?.id}">${tbAgentpayInfoInstance?.tbAgentpayDetailsInfos?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.transFailAmount.label" default="Trans Fail Amount" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "transFailamount")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.transFailNum.label" default="Trans Fail Num" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "transFailnum")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.transSucAmount.label" default="Trans Suc Amount" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "transSucamount")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tbAgentpayInfo.transSucNum.label" default="Trans Suc Num" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tbAgentpayInfoInstance, field: "transSucnum")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${tbAgentpayInfoInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
