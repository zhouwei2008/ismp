<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-申请提现</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="prototype"/>
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
              <li class="rtncnt blue">申请提现</li>
              <g:if test="${session.level3Map != null && session.level3Map['withdraw/list'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['withdraw/list'].modelPath}">提现记录</a>
                </li>
              </g:if>
            </ul>
        </div>
    </div>
    <form action="save" method="post" name="actionForm" id="actionForm">
        <input type="hidden" name="amount" value="${params.amount}">
        <div class="txbox">
            <dl>
              <dt>您的吉高账户：</dt>
              <dd>${session.cmCustomer?.name.encodeAsHTML()}&nbsp;(${session.cmCustomerOperator.defaultEmail})</dd>
              <dt>银行名称：</dt>
              <dd>${cmCustomerBankAccount.bankCode}${cmCustomerBankAccount.branch}${cmCustomerBankAccount.subbranch}</dd>
              <dt>开户名：</dt>
              <dd>${cmCustomerBankAccount.bankAccountName}</dd>
              </dl>
              <table class="txtlb">
                <tr>
                  <th colspan="2" scope="col">请确认以下的信息是否正确，以确保您的提款成功。</th>
                </tr>
                <tr>
                  <td width="149" class="txtRight">真实姓名</td>
                  <td width="439" class="txtLeft">${cmCustomerBankAccount.bankAccountName}</td>
                </tr>
                <tr>
                  <td class="txtRight">提现银行账户</td>
                  <td class="txtLeft">${cmCustomerBankAccount.bankAccountNo}</td>
                </tr>
                <tr>
                  <td class="txtRight">提现金额</td>
                  <td class="txtLeft"><span class="doler">${params.amount?.encodeAsHTML()}元</span></td>
                </tr>
              </table>
              <div class="txing">
                <p>到账时间:预计明日24：00之前，请耐心等待</p>
              </div>
            <dl>
              <dt>手机验证码：</dt>
              <dd>
                <input name="mobile_captcha" id="mobile_captcha" type="text" maxlength="6"/>
                <input id="btn_reload" class="serchbtn" type="button" value="获取验证码" />
              </dd>
              <dt>支付密码：</dt>
              <dd>
                <input name="paypass" id="paypass" type="password" maxlength="40"/>
              </dd>
            </dl>
            <div class="xybbtn">
                <input type="button" class="btn mglf10" value="确认" onclick="dosave()"/>
            </div>
          </div>
    </form>
  </div>

<script>
    E.on('btn_reload','click',function(){
        var btn_reload = D.get("btn_reload");
        btn_reload.disabled = true;
        smsTool.sendCaptcha()
    });

    var Server={
        sendRequest : function(url,data,callback){
            new Ajax.Request(url,{asynchronous:true,evalScripts:true,onSuccess:function(e){callback(e)},parameters:data});
        }
    }

    var timer0;
    var smsTool = {
        sendCaptcha : function(){
            var btn_reload = D.get("btn_reload");
            btn_reload.disabled = true;
            Server.sendRequest('${createLink(controller:'withdraw',action:'sendMobileCaptcha')}','',this.callbackSuccess);
        },
        callbackSuccess : function(response){
            alert("系统已经发送本次提现操作的验证码到您绑定的手机上了，请查收");
            timer0=setInterval("timere()",1000);
//            var btn_reload = D.get("btn_reload");
//            btn_reload.disabled = false;
        }
    }

    var time=60;
    function timere()
    {
      if(time==0){
          document.getElementById("btn_reload").disabled  = false;
          document.getElementById("btn_reload").value =" 获取验证码 ";
          clearInterval(timer0);
          time = 60;
          return false;
      }
        time = time-1;
        document.getElementById("btn_reload").value = "  剩 余("+time+")  ";
    }

     function dosave(){
        if ($('#mobile_captcha').val().trim() == '') {

            alert("请输入手机验证码");

            $('#mobile_captcha').focus();

            return false;

        }
        if ($('#paypass').val().trim() == '') {

            alert("支付密码不能为空");

            $('#paypass').focus();

            return false;

        }
        document.actionForm.submit();
     }
</script>
</body>
</html>
