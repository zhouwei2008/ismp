<div class="topmenu">
    	<div class="topbox">
        	<div class="logo"></div>
        	<div class="topuser">欢迎，${session.cmCustomerOperator.name} [<a href="${createLink(controller:'login',action:'logout')}" class=" blue">退出</a>] | <a href="http://www.gicard.net/index.html" target="_blank" class=" blue">吉高首页</a></div>
    	</div>
        <div class="grxx_menu">
        <!-- 导航 -->
           <div id="nav">

                <div class="nav-container">
                    <ul>
                        <li class="nav-master
                        <g:if test="${(controllerName=='index' && actionName=='account')||
                                           (controllerName=='security' && actionName=='index')||
                                           (controllerName=='charge' && actionName=='index')||
                                           (controllerName=='charge' && actionName=='confirm')||
                                           (controllerName=='charge' && actionName=='list')||
                                           (controllerName=='withdraw' && actionName=='index')||
                                           (controllerName=='withdraw' && actionName=='step2')||
                                           (controllerName=='withdraw' && actionName=='list')||
                                           (controllerName=='transfer' && actionName=='index')||
                                           (controllerName=='transfer' && actionName=='step2')||
                                           (controllerName=='transfer' && actionName=='batchStep1')||
                                           (controllerName=='transfer' && actionName=='batchStep2')||
                                           (controllerName=='transfer' && actionName=='batchStep4')||
                                           (controllerName=='transfer' && actionName=='check')||
                                           (controllerName=='transfer' && actionName=='checkDetail')||
                                           (controllerName=='transfer' && actionName=='list')}">current</g:if>">
                            <a class="nav-master-a" href="${createLink(controller:'index',action:'account')}"><strong>我的账户</strong></a>
                        </li>
                        <g:each in="${session.levelList1}" var="levelList1">
                            <li class="nav-master
                            <g:if test="${controllerName in [levelList1.modelPath] && !levelList1.modelPath.equals('security')}">current</g:if>
                            <g:if test="${'security' in [levelList1.modelPath]&&controllerName.equals('operator')}">current</g:if>
                            <g:elseif test="${'security' in [levelList1.modelPath]&&controllerName.equals('role')}">current</g:elseif>
                            <g:elseif test="${'security' in [levelList1.modelPath]&&controllerName.equals('model')}">current</g:elseif>
                            <g:elseif test="${'security' in [levelList1.modelPath]&&controllerName.equals('syslog')}">current</g:elseif>
                            <g:elseif test="${'trade' in [levelList1.modelPath]&&controllerName.equals('precharge')}">current</g:elseif>
                            <g:elseif test="${'settle' in [levelList1.modelPath]&&controllerName.equals('ftFoot')}">current</g:elseif>
                            <g:elseif test="${'tbAgentpayInfo' in [levelList1.modelPath]&&controllerName.equals('agentpay')}">current</g:elseif>
                            <g:elseif test="${'tbAgentpayInfo' in [levelList1.modelPath]&&controllerName.equals('tbAgentPayBox')}">current</g:elseif>
                            ">
                                <g:if test="${session.level2DefaultIndexPage.get(levelList1.modelPath)}">
                                    <a class="nav-master-a" href="${request.contextPath}/${session.level2DefaultIndexPage.get(levelList1.modelPath).modelPath}"><strong>${levelList1.modelName}</strong></a>
                                </g:if>
                                <g:else>
                                    <a class="nav-master-a" href="#"><strong>${levelList1.modelName}</strong></a>
                                </g:else>

                                <ul class="nav-sub">
                                    <g:each in="${session.levelList2}" var="levelList2">
                                        <g:if test="${levelList1.point.equals(levelList2.parentPoint) && !levelList2.modelPath.equals('security/index')}">
                                            <li>
                                                <a href="${request.contextPath}/${levelList2.modelPath}"<g:if test="${(controllerName+'/'+actionName)==levelList2.modelPath}"> class="current"</g:if>>
                                                    <span>${levelList2.modelName}</span>
                                                </a>
                                            </li>
                                        </g:if>
                                    </g:each>
                                 </ul>
                            </li>
                        </g:each>
                       <g:if test="${session.cmCustomer.customerNo==grailsApplication.config.tianWuCode}">
                            <li class="nav-master
                               <g:if test="${(controllerName=='outAndIn' && actionName=='index')}">current</g:if>">
                                     <a class="nav-master-a" href="${createLink(controller:'outAndIn',action:'index')}"><strong>出入金查询</strong></a>
                             </li>
                         </g:if>
                    </ul>
                </div>
            </div>
        <!-- /导航 -->
</div>
</div>