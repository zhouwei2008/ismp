<!--顶部-->
<div class="InHeader">
    <div class="contentArea">
        <div class="topBar"><span>${session.cmLoginCertificate.loginCertificate}[${session.cmCustomerOperator?.name}]</span>[<a href="${createLink(controller:'login',action:'logout')}">退出</a>]</div>
        <div class="logo"></div>
        <div class="InMenu">
            <ul class="menuFirst" id="mainMenu">

                <g:each in="${session.levelList1}" var="levelList1">
                    <g:if test="${controllerName=='security' && actionName=='index' && levelList1.modelPath.equals('index')}">
                        <li><a class="menuItem current" href="${createLink(controller:'index',action:'account')}">我的账户</a>
                    </g:if>
                    <g:elseif test="${controllerName=='withdraw' && actionName=='index' && levelList1.modelPath.equals('index')}">
                        <li><a class="menuItem current" href="${createLink(controller:'index',action:'account')}">我的账户</a>
                    </g:elseif>
                    <g:else>
                        <li><a class="menuItem
                        <g:if test="${controllerName in [levelList1.modelPath] &&!controllerName.equals('trade')}"> current</g:if>
                        <g:if test="${'index' in [levelList1.modelPath]&&controllerName.equals('operator')}"> current</g:if>
                        <g:elseif test="${'index' in [levelList1.modelPath]&&controllerName.equals('role')}"> current</g:elseif>
                        <g:elseif test="${'index' in [levelList1.modelPath]&&controllerName.equals('model')}"> current</g:elseif>
                        <g:elseif test="${'index' in [levelList1.modelPath]&&controllerName.equals('syslog')}"> current</g:elseif>
                        <g:elseif test="${'trade' in [levelList1.modelPath]&&controllerName.equals('trade') && actionName!='account'}"> current</g:elseif>
                        <g:elseif test="${'settle' in [levelList1.modelPath]&&controllerName.equals('ftFoot')}"> current</g:elseif>
                        <g:elseif test="${'tbAgentpayInfo' in [levelList1.modelPath]&&controllerName.equals('agentpay')&&!actionName.equals('certlist')&&!actionName.equals('certedit')&&!actionName.equals('downcert') }"> current</g:elseif>
                        <g:elseif test="${'tbAgentpayInfo' in [levelList1.modelPath]&&controllerName.equals('tbAgentPayBox')}"> current</g:elseif>
                            <g:if test="${session.level2DefaultIndexPage.get(levelList1.modelPath)}">
                                   " href="${request.contextPath}/${session.level2DefaultIndexPage.get(levelList1.modelPath).modelPath}">${levelList1.modelName}</a>
                                </g:if>
                                <g:else>
                                    " href="#">${levelList1.modelName}</a>
                                </g:else>


                      </g:else>

                        <ul class="menuSecond">
                            <g:each in="${session.levelList2}" var="levelList2">
                                <g:if test="${levelList1.point.equals(levelList2.parentPoint)}">
                                    <li>
                                        <a href="${request.contextPath}/${levelList2.modelPath}" <g:if test="${(controllerName+'/'+actionName)==levelList2.modelPath}">class="SelectSubMenu"</g:if>>
                                            ${levelList2.modelName}
                                        </a>
                                    </li>
                                </g:if>
                            </g:each>
                        </ul>
                    </li>
                </g:each>

             </ul>
        </div>
    </div>
</div>
<!--二级菜单区域需要JS控制-->
<div class="secondMenuArea"></div>

%{--<script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-1.4.4.min.js')}"></script>--}%
%{--<script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-1.8.2.min.js')}"></script>--}%
<script type="text/javascript">
    $(document).ready(function()
    {
        if( $(".current").size()==0){
                  var controllerName = "${controllerName}";
                  var actionName = "${actionName}";
                  if((controllerName=='withdraw' && actionName=='list')||(controllerName=='trade' && actionName=='account')){
                      $(".menuFirst >li").eq(2).find('.menuItem').addClass('current');
                      $(".menuFirst >li").eq(2).find('.menuSecond').css('display', 'block')
                  }else if((controllerName=='agentpay' && actionName=='certlist')
                          ||(controllerName=='agentpay' && actionName=='certedit')
                          ||(controllerName=='agentpay' && actionName=='downcert')){
                      $(".menuFirst >li").eq(4).find('.menuItem').addClass('current');
                      $(".menuFirst >li").eq(4).find('.menuSecond').css('display', 'block');
                  }
        }else{
            $(".current").parent().find('.menuSecond').css('display', 'block');
        }
    });
</script>

