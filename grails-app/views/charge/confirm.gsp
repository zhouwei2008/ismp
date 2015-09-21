<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-充值确认</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>
    <g:render template="/layouts/baseInfo"/>
    <div class="main_box">
      <div class="main_left">
        <div class="zxgg">
          <div class="zxggtlt">
            <p>最新公告</p>
          </div>
          <g:render template="/layouts/news"/>
        </div>
        <div class="cpfw">
          <div class="zxggtlt">
            <p>常见问题</p>
          </div>
          <ul class="list12">
          </ul>
        </div>
      </div>
      <div class="txmenu">
        <span class="left trnmenutlt">交易管理：</span>
  	    <div class="rtnms">
            <ul>
              <li class="rtncnt blue">充值</li>
              <g:if test="${session.level3Map != null && session.level3Map['charge/list'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['charge/list'].modelPath}">充值记录</a>
                </li>
              </g:if>
            </ul>
        </div>
    </div>
    <form action="create" method="post" name="actionForm" id="actionForm" target="_blank">

        <div class="txbox">
            <input type="hidden" name="bankname" id="bankname" value="${params.bankname?.encodeAsHTML()}"/>
             <input type="hidden" name="buyer_name" id="buyer_name" value="${session.cmLoginCertificate.loginCertificate}"/>
             <input type="hidden" name="buyer_id" id="buyer_id" value="${session.cmCustomer.customerNo}"/>

            <dl>
              <dt>充值方式：</dt>
              <dd><img src="${resource(dir:'images/bank',file:params.bankname?.encodeAsHTML() + "_OUT.gif")}" width="100" class="left" height="20" style="margin-top:4px;"/></dd>
                <dt>充值渠道：</dt>
                <input type="hidden" name="preference" id="preference" value="${params.channel?.encodeAsHTML()}"/>
                <dd>
                    <g:if test="${(params.channel?.encodeAsHTML()).indexOf('_') == -1}">
                        个人
                    </g:if>
                    <g:else>
                        企业
                    </g:else>
                </dd>
                %{--<g:if test="${channelMap.size()==1}">--}%
                     %{--<input type="hidden" name="preference" id="preference" value="${channelMap.values().toArray(new String[0])[0]}"/>--}%
                    %{--<dd>--}%
                        %{--<g:if test="${(channelMap.values().toArray(new String[0])[0]).indexOf('_') == -1}">--}%
                            %{--个人--}%
                        %{--</g:if>--}%
                        %{--<g:else>--}%
                            %{--企业--}%
                        %{--</g:else>--}%
                    %{--</dd>--}%
                %{--</g:if>--}%
                %{--<g:else>--}%
                    %{--<dd>--}%
                        %{--<g:radioGroup name="preference" id="preference" labels="${channelMap.keySet().toArray(new String[0])}" values="${channelMap.values().toArray(new String[0])}" COLUMNS="2">--}%
                        %{--${it.radio} ${it.label=='1' ? '个人':'企业'}--}%
                        %{--</g:radioGroup>--}%
                    %{--</dd>--}%
                 %{--</g:else>--}%
              <g:if test="${(params.channel?.encodeAsHTML() == 'SPDB_B2B')||(params.channel?.encodeAsHTML() == 'CMBC_B2B')}">
                  <dt>付款企业客户号：</dt>
                  <dd><input name="payCusNo" id="payCusNo" type="text" maxlength="20"/></dd>
              </g:if>
                <g:if test="${params.channel?.encodeAsHTML() == 'BOCM_B2B'}">
                  <dt>付款帐号：</dt>
                  <dd><input name="payCusNo" id="payCusNoid" type="text" size="30"  maxlength="30"/></dd>
              </g:if>
              <dt>充值账户：</dt>
              <dd> ${session.cmLoginCertificate.loginCertificate}</dd>
              <dt>账户余额：</dt>
              <dd><span class="doler"><g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/>元</span></dd>
              <dt>充值金额：</dt>
              <dd><input name="amount" id="amount" type="text" maxlength="12"/>&nbsp;元</dd>
            </dl>
            <div class="xybbtn">
                <input type="button" id="nextStep" name="btnConfirm" class="btn mglf10" value="确定"/>
            </div>
          </div>
    </form>
  </div>

    <script>
       $(document).ready(function() {

            $("#nextStep").removeAttr('onclick');
            $("#nextStep").click(function(){
                var bankname = $("#bankname").val();
                var preference = $("#preference").val();
                if(preference==null ||preference==''){
                    var p = $('[name=preference]:checked').val();
                    if(p==null||p==''){
                        alert("请选择充值渠道。");
                        return false;
                    }
                    preference = p;
                }

                if (${params.channel?.encodeAsHTML() == 'SPDB_B2B'}||${params.channel?.encodeAsHTML() == 'CMBC_B2B'}) {
                     var payCusNo = $("#payCusNo").val();
                    if(payCusNo == ''){
                       alert("请输入付款企业客户号。");
                       $("#payCusNo").focus;
                       return false;
                    }
                    if(!/^[1-9]/.test(payCusNo)){
                       alert("付款企业客户号格式不正确。");
                       $("#payCusNo").focus;
                       return false;
                    }
                }
                var amount = $("#amount").val();
                if(amount == ''){
                   alert("请输入充值金额。");
                   $("#amount").focus;
                   return false;
                }
                if(!/^(\d+|[1-9])(\.\d{0,2}){0,1}$/.test(amount)){
                   alert("充值金额格式不正确。");
                   $("#amount").focus;
                   return false;
                }
              if(confirm("确定充值吗？")) {
                  $('#actionForm').submit();
              }
            });
     });
    </script>
</body>
</html>
