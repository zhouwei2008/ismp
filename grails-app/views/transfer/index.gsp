<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-单笔转账</title>
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
              %{--<li>--}%
                  %{--<a href="${request.contextPath}/transfer/batchStep1">批量转账</a>--}%
              %{--</li>--}%
              <g:if test="${session.level3Map != null && session.level3Map['transfer/list'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['transfer/list'].modelPath}">转账记录</a>
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
    <form action="step2" method="post" name="actionForm" id="actionForm">
  <div class="txbox">
  	<dl>

        <g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">
            <dt><label for="to">对方账户：</label></dt>
            <dd><input name="to" id="to" type="text" maxLength="80" value="${params.to}" onblur="aaa();"/>&nbsp;账户是Email地址</dd>
            <dt>付款理由：</dt>
            <dd><input name="subject" id="subject" type="text" maxLength="128" value="${params.subject}"/></dd>
            <dt>备注(限200字)：</dt>
            <input type="hidden" id="commentText" value="${params.commentText}"/>
            <dd><textarea id="comment" name="comment" style="width:200px; height:50px;"></textarea></dd>
            <dt>付款金额：</dt>
            <dd><input name="amount" id="amount" type="text" maxLength="13" value="${params.amount}" onblur="checkmoney()" data-explain="对个人限2000.00元，对企业限1000000.00元。"/>元</dd>
            <dt><label for="captcha">校验码：</label></dt>
            <dd>
                <input type="text" name="captcha" id="captcha" maxlength="4" />
                <img align="absMiddle" id="authCodeImg" src="../captcha/index?t=${new Date().getTime()}" alt="请输入您看到的内容" width="100" height="30" style="cursor:pointer"/>
                <span class="blue" id="reload-checkcode" style="cursor:pointer;">看不清，换一张</span>
            </dd>
            <g:if test="${flash.message?.encodeAsHTML()} != ''">
            <dt></dt>
            <dd>
                <div id="loginMsg" style="color:red">${flash.message?.encodeAsHTML()}</div>
            </dd>
            </g:if>
        </g:if>
        <g:else><h3 style="margin-top:10px;">因您的可用余额为0，所以无法进行转账操作</h3></g:else>
    </dl>
    <div class="xybbtn">
        <g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">
    	    <input type="submit" class="btn mglf10" value="下一步" />
        </g:if>
    </div>
  </div>
  </form>
  </div>

<script>

    document.getElementById('comment').innerHTML = document.getElementById('commentText').value;

    E.onDOMReady(function() {

        new AP.widget.Validator({
            formId: "actionForm",
            ruleType:"id",
            onSubmit: true,
            loadClass: "loading-text",
            errorTrack: true,
            onSuccess: function() {
                if (D.get('comment').offsetWidth == 0) {
                    D.get('comment').value = '';
                }
                //新开窗口时需要使按钮不可点
                var submitBtn = D.query(':submit', D.get(this.formId))[0];
                D.addClass(submitBtn.parentNode, 'btn-ok-disabled');
                submitBtn.disabled = true;
            },
            rules: {
                'to': {
                    type: ["email", "mobile"],
                    desc: "账户名",
                    required: true,
                    error:"账户格式有误，应该是Email地址或者手机号码。"
                },
                'amount':{
                    type: "money",
                    isAmount: true,
                    minValue: 0.01,
                    required: true,
                    desc:"付款金额"
                },
                'subject':{
                    required: true,
                    desc:"付款理由"
                },
                'captcha':{
                    required: true,
                    desc: '校验码'
                },
                'comment':{
                    maxLength: 200,
                    desc:'备注',
                    error:'备注字数已经超过200的限制',
                    depends:true,
                    required:false
                }
            }
        });
    });
    E.on('to','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });
    E.on('amount','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });
    E.on('subject','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });
    E.on('captcha','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });
    E.on('comment','focus',function(){
        if(D.get("loginMsg").innerHTML!="")
            D.get("loginMsg").innerHTML="";
    });

    E.on('reload-checkcode', 'click', function() {
        reloadAuthImg(D.get('authCodeImg'));
    });
    E.on('authCodeImg', 'click', function() {
        reloadAuthImg(D.get('authCodeImg'));
    });


    function reloadAuthImg(img) {
        var url = img.src.split('?')[0];
        var param = img.src.toQueryParams();
        param.r = new Date().getTime();

        var params = [];
        for (var i in param) {
            params.push(i + '=' + param[i]);
        }
        img.src = url + '?' + params.join('&');
    }

    var lastto = "";
    function aaa() {
        if ($("to").value != lastto) {
            lastto = $("to").value;
            checkPortrait.sendRequest($("to").value);
            checkComp.sendRequest($("to").value);
        }
    }

    var Server = {
        sendRequest : function(url, data, callback) {
            new Ajax.Request(url, {asynchronous:true,evalScripts:true,onSuccess:function(e) {
                callback(e)
            },parameters:data});
        }
    }

    /* 检查对方账号 */
    var checkPortrait = {
        sendRequest : function(type) {
            Server.sendRequest('${createLink(controller:'transfer',action:'getPayeeInfo')}', "to=" + $("to").value, this.callbackSuccess);
        },
        callbackSuccess : function(response) {
           $("checkresult").innerHTML = response.responseText;
        }
    }
     /* 检查转入账户是否是企业账号
     *  date:2012-07-10
     *  返回值 result success：表示是企业账户，fail：表示不是企业账户
     *  params: toEmail 转入客户账户Email
     */
    //是否是b2b转账标记 0：不是、1：是   默认为0.
    var B2BSign = 0;
    var checkComp = {
        sendRequest : function(type) {
            Server.sendRequest('${createLink(controller:'transfer',action:'getIsComp')}', "toEmail=" + $("to").value, this.callbackSuccess);
        },
        callbackSuccess : function(result) {
            if(result.responseText=='success') {
                B2BSign = 1
            }
        }
    }
    //判断转账金额是否超越限制金额
    function checkmoney(){
        if(B2BSign==1){
            if(document.getElementById("amount").value>1000000){
                $("amountExplain").innerHTML="<font color='red'>付款金额不能大于1000000元</font>" ;
                $("amountExplain").focus();
            }
        }else{
             if(document.getElementById("amount").value>2000){
                $("amountExplain").innerHTML="<font color='red'>付款金额不能大于2000元</font>"
            }
        }
    }
</script>

</body>
</html>
