<div class="userxx">
  <div class="usetop">
    <p><span class="left">下午好，<span class="blue">${session.cmCustomer?.name.encodeAsHTML()}</span><span class="usetxt">&nbsp;(${session.cmCustomerOperator.defaultEmail})&nbsp;</span>
         <span class="blue usetxt ">
             <g:if test="${session.level3Map != null && session.level3Map['security/index'] != null}">
                 <a href="${request.contextPath}/${session.level3Map['security/index'].modelPath}">账户信息</a>
             </g:if>
         </span>
       </span>
       <span class="right usetxt b"><strong>可用金额：</strong><span class="doler"><g:if test="${acAccount_Main}"><g:formatNumber currencyCode="CNY" number="${acAccount_Main.balance/100}" type="currency"/></g:if><g:else>0</g:else></span><strong> 元</strong></span>
       <span class="right usetxt b"><strong>未结算金额：</strong><span class="doler"><g:if test="${acAccount_srv}"><g:formatNumber currencyCode="CNY" number="${acAccount_srv.balance/100}" type="currency"/></g:if><g:else>0</g:else></span><strong> 元</strong></span>
    </p>
  </div>
  <div class="usebtm"> <span class="left">
    <g:if test="${session.level3Map != null && session.level3Map['charge/index'] != null}">
        <span class="anniu_2">
            %{--<a href="${request.contextPath}/${session.level3Map['charge/index'].modelPath}">--}%
                <input type="button" class="btn mglf10 red"  value="充值" onclick="doCharge()"/>
            %{--</a>--}%
        </span>
    </g:if>
    <g:if test="${session.level3Map != null && session.level3Map['withdraw/index'] != null}">
        <span class="anniu_2">
            %{--<a href="${request.contextPath}/${session.level3Map['withdraw/index'].modelPath}">--}%
                <input type="button" class="btn mglf10" value="提现" onclick="doWithdraw()"/>
            %{--</a>--}%
        </span>
    </g:if>
    <g:if test="${session.level3Map != null && session.level3Map['transfer/index'] != null}">
        <span class="anniu_2">
            <a href="${request.contextPath}/transfer/index">
                <input type="button" class="btn mglf10" value="转账" onclick="doTransfer()"/>
            </a>
        </span>
    </g:if>
    </span>
    <span class="right fonthui">上次登录时间：${session.lastLoginTime?session.lastLoginTime:"本次为首次登录！"}
              <g:if test="${PWDMsg}">
                  <strong class="hs" style = "color:red">${PWDMsg}</strong><br/>
              </g:if>
        <br/>
  </span>
  </div>
</div>
<form method="post" name="jumpform" id="jumpform">
</form>
<script>
    function doCharge() {
        var form = document.getElementById('jumpform');
        form.action = "../charge/index";
        form.submit();
    }
    function doWithdraw() {
        var form = document.getElementById('jumpform');
        form.action = "../withdraw/index";
        form.submit();
    }
    function doTransfer() {
        var form = document.getElementById('jumpform');
        form.action = "../transfer/index";
        form.submit();
    }
</script>