<%@ page import="ismp.DesUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-申请提现</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
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

<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>提取现金到银行卡</h1>
        <div class="normalContent">
            <!--步骤提示条开始-->
            <div class="flowSteps">
                <!--
                    当前步骤第一步样式为：current_1
                    当前步骤第二步样式为：current_2
                    当前步骤第三步样式为：current_3
                -->
                <ul id="flowStepsCurrent" class="current_1">
                    <li class="FirstStep">第一步</li>
                    <li class="SecondStep">第二步</li>
                    <li class="ThirdStep">完成提现</li>
                    <div class="clearFloat"></div>
                </ul>
            </div>
            <!--步骤提示条结束-->

            <!--第一步开始-->
            <div id="div1" style="display:block">
            <g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">
                <div class="content">
                    <div class="accountInfo clearFloat">
                        <label>您的吉高账户：</label><span>${session.cmCustomer?.name.encodeAsHTML()}&nbsp;(${session.cmCustomerOperator.defaultEmail})</span>
                        <label>银行名称：</label><span>${cmCustomerBankAccount.bankCode}${cmCustomerBankAccount.branch}${cmCustomerBankAccount.subbranch}</span>
                        <label>北京朝阳开户名：</label><span>${cmCustomerBankAccount.bankAccountName}</span>
                        <label>银行账号：</label><span>${DesUtil.decrypt(cmCustomerBankAccount?.bankAccountNo,session.cmCustomer?.customerNo) }</span>
                    </div>
                    <div class="moneyInfo clearFloat">
                        <div class="currentBanlanceArea clearFloat">
                            <label class="currentBalance">当前可用余额：</label><span class="balance"><g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/><i> 元 </i></span>
                        </div>
                        <div class="extractForm">
                            <label>申请提现金额：</label><input name="amount" id="amount" type="text" maxlength="12" value=""/><span>元</span>
                        </div>
                        <label>校验码：</label>
                        <input type="text" name="captcha" id="captcha" maxlength="4" class="w70"/>
                        <span class="veryifyCode">
                            <img align="absMiddle" id="authCodeImg" src="../captcha/index?t=${new Date().getTime()}" alt="请输入您看到的内容" width="100" height="30"/>
                        </span>
                        <span class="blue" id="reload-checkcode" style="cursor:pointer;">看不清，换一张</span>
                    </div>
                    <div class="stepsBtn">
                        <g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">
                            <button onclick="goSecondStep()">下一步</button>
                        </g:if>
                    </div>
                </div>
            </g:if>
            <g:else><div class="content"><label class="currentBalance">因您的可用余额为0，所以无法进行提现操作</label></div></g:else>
            </div>
            <!--第一步结束-->

            <!--第二步开始-->
            <div id="div2" style="display:none;">
                <div class="content">
                    <div class="subTitle">请确认以下的信息是否正确，以确保您的提款成功。</div>
                    <div class="accountInfo_check clearFloat">
                        <label>真实姓名：</label><span>${cmCustomerBankAccount.bankAccountName}</span>
                        <label>提现银行账户：</label><span>${cmCustomerBankAccount.bankAccountNo}</span>
                        <label>提取金额：</label><span class="moneyNum"><i id="withdrawAmount"></i>元</span>
                    </div>

                    <div class="tipsBlue">
                        <div>
                            到账时间：预计明日24：00之前，请耐心等待。
                        </div>
                    </div>
                    <div class="moneyInfo clearFloat">
                        <div class="extractForm_check">
                            <label>手机验证码</label><input name="mobile_captcha" id="mobile_captcha" type="text" maxlength="6" value=""/><span class="veryifyCode"><button id="btn_reload" class="veryifyBtn">获取验证码</button></span>
                            <label>支付密码</label><input name="paypass" id="paypass" type="password" maxlength="40" value=""/>
                        </div>
                    </div>

                    <div class="stepsBtn">
                        <button onclick="goThirdStep();">确认提交</button>
                    </div>
                </div>
            </div>
            <!--第二步结束-->

            <!--第三步开始-->
            <div id="div3" style="display:none;">
                <div class="content">
                    <div class="okMessage">
                        <div class="tipsIcon"></div>
                        <div class="tipsTitle"></div>
                    </div>
                    <div class="messageText">
                    </div>
                    <div class="messageBtnArea">
                        <a href="${createLink(controller:'index',action:'account')}">返回我的账户</a>
                        <a href="${request.contextPath}/${session.level3Map['withdraw/list'].modelPath}">查看提现记录</a>
                    </div>
                </div>
            </div>
            <!--第二步结束-->
        </div>
    </div>
</div>

<!--内容区结束-->

<script>
    E.on('reload-checkcode','click',function(){
        reloadAuthImg(D.get('authCodeImg'));
    });

    E.on('authCodeImg','click',function(){
        reloadAuthImg(D.get('authCodeImg'));
    });

    function reloadAuthImg(img) {
        var url = img.src.split('?')[0];
        var param = img.src.toQueryParams();
        param.r = new Date().getTime();

        var params = [];
        for(var i in param){
            params.push(i+'='+param[i]);
        }
        img.src = url + '?' + params.join('&');
    }

    var Server={
        sendRequest : function(url,data,callback){
            new Ajax.Request(url,{asynchronous:true,evalScripts:true,onSuccess:function(e){callback(e)},parameters:data});
        }
    }

    function goSecondStep(){
        var amount = $("#amount").val();
        var captcha = $("#captcha").val();

        if(amount.trim() == "" ){
             alert("请输入提现金额");
             return false;
        }

        if(!(/^\d+(\.\d{0,2})?$/i).test(amount)){
            alert("无效的金额");
            return false;
        }
        var max = ${acAccount_Main==null?0:acAccount_Main.balance/100}
        if(parseFloat(amount)>max){
            alert("金额值必须小于"+max);
            return false;
        }

        if(parseFloat(amount)<0.01){
            alert("金额值必须大于0.01");
            return false;
        }


        if(captcha.trim() == "" ){
            alert("请输入验证码");
            return false;
        }

        if(captcha.length != 4){
            alert("验证码位数不对");
             return false;
        }
        secondStep.step2()
    }

    var secondStep = {
        step2 : function(){
            Server.sendRequest('${createLink(controller:'withdraw',action:'step2',)}',"amount="+$("#amount").val()+"&captcha="+$("#captcha").val(),this.callbackSuccess);
        },
        callbackSuccess : function(response){
            if(response.responseText == "ok"){
                $("#withdrawAmount").text($("#amount").val());
                document.getElementById("flowStepsCurrent").className="current_2";
                document.getElementById("div1").style.display ="none";
                document.getElementById("div2").style.display ="block";
                document.getElementById("div3").style.display ="none";

            }else{
                alert(response.responseText);
            }
        }
    }

    E.on('btn_reload','click',function(){
        var btn_reload = D.get("btn_reload");
        btn_reload.disabled = true;
        smsTool.sendCaptcha()
    });

    var timer0;
    var smsTool = {
        sendCaptcha : function(){
            var btn_reload = D.get("btn_reload");
            btn_reload.disabled = true;
            Server.sendRequest('${createLink(controller:'withdraw',action:'sendMobileCaptcha')}','',this.callbackSuccess);
        },
        callbackSuccess : function(response){
            timer0=setInterval("timere()",1000);
            alert("系统已经发送本次提现操作的验证码到您绑定的手机上了，请查收");
        }
    }

    var time=60;
    function timere()
    {
        if(time==0){
            $('#btn_reload').attr("disabled","disabled")
            $("#btn_reload").text('获取验证码');
            clearInterval(timer0);
            time = 60;
            return false;
        }
        time = time-1;
        $("#btn_reload").text("剩 余("+time+")");
    }



    function goThirdStep(){

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

        thirdStep.save()
    }

    var thirdStep = {
        save : function(){
            Server.sendRequest('${createLink(controller:'withdraw',action:'save',)}',"amount="+$("#amount").val()+"&paypass="+$("#paypass").val()+"&mobile_captcha="+$("#mobile_captcha").val(),this.callbackSuccess);
        },
        callbackSuccess : function(response){
            if(response.responseText == "ok"){
                document.getElementById("flowStepsCurrent").className="current_3";
                document.getElementById("div1").style.display ="none";
                document.getElementById("div2").style.display ="none";
                document.getElementById("div3").style.display ="block";

                $(".tipsTitle").text("提现申请已提交，等待银行处理！");
                $(".messageText").text("到账时间预计在明日24：00前，请耐心等待。");
            }else if(response.responseText == 'fail'){
                document.getElementById("flowStepsCurrent").className="current_3";
                document.getElementById("div1").style.display ="none";
                document.getElementById("div2").style.display ="none";
                document.getElementById("div3").style.display ="block";

                $(".tipsTitle").text("提现操作失败!");
                $(".messageText").text("");
            }else{
                alert(response.responseText);
                document.getElementById("flowStepsCurrent").className="current_3";
            }
        }
    }
</script>
</body>
</html>
