	<!-- 右侧提示框  开始 -->
	<div id="aside">
		<!-- $referPage -->
			
		<!-- 如果list为空, 则隐藏 -->
		<div class="aside-main  aside-notice-hide fn-hide    hasSecurityLevel ">  
			<!-- 个人信息面板 开始 -->
			<div class="aside-info">
				<h2 class="fn-hide">账户信息</h2>
				<dl>
					<dt id="J_aside_acctname" data-host="https://lab.alipay.com" ><span title="wangxiao@trustair.com.cn">wangxiao@trustair.com.cn</span><em class="ico-vip"><s class="pop_extend_txt fn-hide">会员等级：普通账户</s></em></dt>
					<dd class="aside-balance">
						<ul>
							<li class="aside-available"><span>可用余额</span><span class="aside-amount "><em>0.00</em>元</span></li> 	
						</ul>
					</dd>

					<dd class="aside-action">
						<ul>
                            <g:if test="${session.level3Map != null && session.level3Map['charge/index'] != null}">
							    <li><a class="btn-icon btn-addfunds" href="" target="_blank" seed="aside-ljcz"><span>立即充值</span></a></li>
                            </g:if>
                            <g:if test="${session.level3Map != null && session.level3Map['withdraw/index'] != null}">
                                <li><a class="btn-icon btn-withdraw" href=""  target="_blank" seed="aside-tx"><span>提现</span></a></li>
                            </g:if>

						</ul>
					</dd>
				</dl>
			</div>
			<!-- 个人信息面板 结束 -->

			<!-- 提醒信息 开始 -->
			<div class="aside-notice">
				<div class="aside-mlist-container " id="J_asidemlist">
					<ul></ul>
				</div>

				<div class="aside-page">
					<span class="action">第<em id="J_aside_current_page">1</em>页，共<em id="J_aside_total_page"></em>页</span>
					<span class="link">
						  <a class="btn-fixed fn-hide" href="#pre">上一页</a>
						  <a class="btn-fixed fn-hide" href="#next">下一页</a>
					</span>
				</div>
			</div>
			<!-- 提醒信息 结束 -->  
		</div>
		<div class="aside-bar" title="展开侧边栏"><dl><dt class="fn-hide">wangxiao@trustair.com.cn</dt></dl><em class="ico ico-alarm"><a href="#">查看提醒</a></em></div>
	</div>
	<!-- 右侧提示框  结束 -->