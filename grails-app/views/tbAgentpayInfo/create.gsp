

<%@ page import="dsf.TbAgentpayInfo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tbAgentpayInfo.label', default: 'TbAgentpayInfo')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tbAgentpayInfoInstance}">
            <div class="errors">
                <g:renderErrors bean="${tbAgentpayInfoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchAmount"><g:message code="tbAgentpayInfo.batchAmount.label" default="Batch Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchAmount', 'errors')}">
                                    <g:textField name="batchAmount" value="${tbAgentpayInfoInstance?.batchAmount}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchBizId"><g:message code="tbAgentpayInfo.batchBizId.label" default="Batch Biz Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchBizId', 'errors')}">
                                    <g:textField name="batchBizId" value="${tbAgentpayInfoInstance?.batchBizId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchBizType"><g:message code="tbAgentpayInfo.batchBizType.label" default="Batch Biz Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchBizType', 'errors')}">
                                    <g:textField name="batchBizType" value="${tbAgentpayInfoInstance?.batchBizType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchCount"><g:message code="tbAgentpayInfo.batchCount.label" default="Batch Count" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchCount', 'errors')}">
                                    <g:textField name="batchCount" value="${fieldValue(bean: tbAgentpayInfoInstance, field: 'batchCount')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchCurrNum"><g:message code="tbAgentpayInfo.batchCurrNum.label" default="Batch Curr Num" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchCurrNum', 'errors')}">
                                    <g:textField name="batchCurrNum" value="${tbAgentpayInfoInstance?.batchCurrNum}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchDate"><g:message code="tbAgentpayInfo.batchDate.label" default="Batch Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchDate', 'errors')}">
                                    <g:textField name="batchDate" value="${tbAgentpayInfoInstance?.batchDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchFinishDate"><g:message code="tbAgentpayInfo.batchFinishDate.label" default="Batch Finish Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchFinishDate', 'errors')}">
                                    <g:datePicker name="batchFinishDate" precision="day" value="${tbAgentpayInfoInstance?.batchFinishDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchId"><g:message code="tbAgentpayInfo.batchId.label" default="Batch Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchId', 'errors')}">
                                    <g:textField name="batchId" value="${tbAgentpayInfoInstance?.batchId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchRemark"><g:message code="tbAgentpayInfo.batchRemark.label" default="Batch Remark" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchRemark', 'errors')}">
                                    <g:textField name="batchRemark" value="${tbAgentpayInfoInstance?.batchRemark}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchRemark1"><g:message code="tbAgentpayInfo.batchRemark1.label" default="Batch Remark1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchRemark1', 'errors')}">
                                    <g:textField name="batchRemark1" value="${tbAgentpayInfoInstance?.batchRemark1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchRemark2"><g:message code="tbAgentpayInfo.batchRemark2.label" default="Batch Remark2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchRemark2', 'errors')}">
                                    <g:textField name="batchRemark2" value="${tbAgentpayInfoInstance?.batchRemark2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchRemark3"><g:message code="tbAgentpayInfo.batchRemark3.label" default="Batch Remark3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchRemark3', 'errors')}">
                                    <g:textField name="batchRemark3" value="${tbAgentpayInfoInstance?.batchRemark3}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchStatus"><g:message code="tbAgentpayInfo.batchStatus.label" default="Batch Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchStatus', 'errors')}">
                                    <g:textField name="batchStatus" value="${tbAgentpayInfoInstance?.batchStatus}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchSysDate"><g:message code="tbAgentpayInfo.batchSysDate.label" default="Batch Sys Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchSysDate', 'errors')}">
                                    <g:datePicker name="batchSysDate" precision="day" value="${tbAgentpayInfoInstance?.batchSysDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchType"><g:message code="tbAgentpayInfo.batchType.label" default="Batch Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchType', 'errors')}">
                                    <g:textField name="batchType" value="${tbAgentpayInfoInstance?.batchType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="batchVersion"><g:message code="tbAgentpayInfo.batchVersion.label" default="Batch Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'batchVersion', 'errors')}">
                                    <g:textField name="batchVersion" value="${tbAgentpayInfoInstance?.batchVersion}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tbAgentpayDetailsInfo"><g:message code="tbAgentpayInfo.tbAgentpayDetailsInfo.label" default="Tb Agentpay Details Info" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'tbAgentpayDetailsInfo', 'errors')}">
                                    <g:select name="tbAgentpayDetailsInfo.id" from="${dsf.TbAgentpayDetailsInfo.list()}" optionKey="id" value="${tbAgentpayInfoInstance?.tbAgentpayDetailsInfo?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="transFailAmount"><g:message code="tbAgentpayInfo.transFailAmount.label" default="Trans Fail Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'transFailAmount', 'errors')}">
                                    <g:textField name="transFailAmount" value="${tbAgentpayInfoInstance?.transFailAmount}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="transFailNum"><g:message code="tbAgentpayInfo.transFailNum.label" default="Trans Fail Num" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'transFailNum', 'errors')}">
                                    <g:textField name="transFailNum" value="${fieldValue(bean: tbAgentpayInfoInstance, field: 'transFailNum')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="transSucAmount"><g:message code="tbAgentpayInfo.transSucAmount.label" default="Trans Suc Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'transSucAmount', 'errors')}">
                                    <g:textField name="transSucAmount" value="${tbAgentpayInfoInstance?.transSucAmount}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="transSucNum"><g:message code="tbAgentpayInfo.transSucNum.label" default="Trans Suc Num" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tbAgentpayInfoInstance, field: 'transSucNum', 'errors')}">
                                    <g:textField name="transSucNum" value="${fieldValue(bean: tbAgentpayInfoInstance, field: 'transSucNum')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
