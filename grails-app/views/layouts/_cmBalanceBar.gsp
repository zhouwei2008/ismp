      	<div class="grxx_left">
			<div class="grxx_left_about">
				<h1>${session.cmCustomer?.registrationName}</h1>
				<h2>${session.cmLoginCertificate.loginCertificate}</h2>
                <g:if test="${session.level3Map != null && session.level3Map['balanceInfo'] != null}">
                    <h3><span style="font-size:12px;">可用余额:</span><span class="hsfong">
                    <g:if test="${acAccount_Main}"><g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/></g:if>
                    <g:else>
                        <%def acAccount_Main=account.AcAccount.findByAccountNo(session.cmCustomer.accountNo)%>
                        <g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/>
                    </g:else>
                    </span></h3>
                </g:if>
				<div class="anniu">
                    <g:if test="${session.level3Map != null && session.level3Map['charge/index'] != null}">
					    <span class="anniu_2"><a href="${request.contextPath}/${session.level3Map['charge/index'].modelPath}">立即充值</a></span>
					    %{--<span class="anniu_2"><a href="${createLink(controller:'charge',action:'index')}">立即充值</a></span>--}%
					</g:if>

                    <g:if test="${session.level3Map != null && session.level3Map['trade/account'] != null}">
                        <span class="anniu_2"><a href="${request.contextPath}/${session.level3Map['trade/account'].modelPath}">账务明细</a></span>
                        %{--<span class="anniu_2"><a href="${createLink(controller:'trade',action:'account')}">账务明细</a></span>--}%
				    </g:if>
                </div>
			</div>            
        </div>