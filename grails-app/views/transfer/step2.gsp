<%@ page import="ismp.GetRandom" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-转账确认</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
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
              <li class="rtncnt blue">单笔转账</li>
              <g:if test="${session.level3Map != null && session.level3Map['transfer/index'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['transfer/index'].modelPath}">转账记录</a>
                </li>
              </g:if>
              <g:if test="${session.level3Map != null && session.level3Map['transfer/check'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['transfer/check'].modelPath}">转账审核</a>
                </li>
              </g:if>
            </ul>
        </div>
    </div>
  <g:form useToken="true" action="save" method="post" name="actionForm">
    <input type="hidden" name="to" value="${params.to}">
    <input type="hidden" name="amount" value="${params.amount}">
    <input type="hidden" name="subject" value="${params.subject}">
    <input type="hidden" name="comment" value="${params.comment}">
  <div class="txbox">
  	<dl>
    	<dt>对方账户：</dt>
        <dd>${params.to.encodeAsHTML()}</dd>
        <dt>对方姓名：</dt>
        <dd>${customerOperator_payee.name}</dd>
        <g:if test="${customerOperator_payee.customer.type in ['C','A']}">
            <dt>对方公司名称：</dt>
            <dd>${customerOperator_payee.customer.registrationName}</dd>
        </g:if>
        <dt>付款理由：</dt>
        <dd>${params.subject?.encodeAsHTML()}</dd>
        <dt>备注：</dt>
        <dd>${params.comment?.encodeAsHTML()}</dd>
        </dl>
        <dl>
        <table class="txtlb">
            <tr>
              <th colspan="2" scope="col">请确认以下的信息是否正确，以确保您的转账成功。</th>
            </tr>
            <tr>
              <td width="149" class="txtRight">对方账户</td>
              <td width="439" class="txtLeft">${params.to.encodeAsHTML()}</td>
            </tr>
            <tr>
              <td class="txtRight">付款金额</td>
              <td class="txtLeft"><span class="doler">${params.amount.encodeAsHTML()}元</span></td>
            </tr>
        </table>
        </dl>
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
  </g:form>
  </div>

<script type="text/javascript">
AralePreload = [];
var AP = AP || {};
</script>
    <script>

     E.onDOMReady(function(){


        new AP.widget.Validator({
	    formId: "actionForm",
		ruleType:"id",
	    onSubmit: true,
	    loadClass: "loading-text",
		errorTrack: true,
		onSuccess: function() {
			//新开窗口时需要使按钮不可点
			var submitBtn = D.query(':submit',D.get(this.formId))[0];
			D.addClass(submitBtn.parentNode, 'btn-ok-disabled');
			submitBtn.disabled = true;
		},
	  	rules: {
			'paypass':{
	        	required: true,
	        	desc: '支付密码'
	        },
	        'captcha_mobile':{
	        	required: true,
	        	desc: '手机验证码'
	        }
          }
	    });

    });

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
        sendCaptcha : function(type){
            Server.sendRequest('${createLink(controller:'transfer',action:'sendMobileCaptcha')}','',this.callbackSuccess);
        },
        callbackSuccess : function(response){
            alert("系统已经发送本次转账操作的验证码到您绑定的手机上了，请查收。");
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
