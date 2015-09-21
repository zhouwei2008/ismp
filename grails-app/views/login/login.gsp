<%@ page import="boss.BoNews" %>
<%@ page import="trade.AccountCommandPackage;" %>
<%@ page import="ismp.GetRandom" %>
<%@ page import="javax.servlet.http.Cookie" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>吉高商户服务-登录</title>
<link href="${resource(dir: 'css', file: 'common.css')}?t=${new Date().getTime()}" rel="stylesheet" type="text/css"/>
<link href="${resource(dir: 'css', file: 'style.css')}?t=${new Date().getTime()}" rel="stylesheet" type="text/css"/>
<link href="${resource(dir: 'css', file: 'jquery.skippr.css')}?t=${new Date().getTime()}" rel="stylesheet" type="text/css"/>
    <g:javascript library="jquery"/>
    <g:javascript library="prototype"/>

    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}?t=${new Date().getTime()}"></script>
</head>
<%
    request.getSession().invalidate();//清空session
    Cookie cookie = request.getCookies()[0];//获取cookie
    cookie.setMaxAge(0);//让cookie过期 ；
%>
<body onload="init()">
<!--顶部-->
<div class="header">
	<div class="contentArea">
    	<div class="topBar"></div>
        <div class="logo"></div>
        <div class="mainMenuArea">
        	<ul>
            	<li><a href="#" class="mainMneu current">首页</a></li>
            	<li><a href="#" class="mainMneu">产品服务</a></li>
            	<li><a href="#" class="mainMneu">解决方案</a></li>
            </ul>
        </div>
    </div>
</div>


<!--轮播图区域-->
<div class="sliderPic">
            <div class="focus" id="focus">
                <div id="focus_m" class="focus_m">
                    <ul>
                        <li class="li_1"><a href="#" hidefocus="true"></a></li>
                        <li class="li_2"><a href="#" hidefocus="true"></a></li>
                        <li class="li_3"><a href="#" hidefocus="true"></a></li>
                    </ul>
                </div>
                <a href="javascript:;" class="focus_l" id="focus_l" hidefocus="true" title="上一张"><b></b><span></span></a>
                <a href="javascript:;" class="focus_r" id="focus_r" hidefocus="true" title="下一张"><b></b><span></span></a>
            </div>

            <!--
            <div id="random2">
                <div class="slidePicOne">
                	<div class="slidePicArea">
                    	<img src="${resource(dir: 'images', file: 'slide_pic1.jpg')}" />
                    </div>
                </div>
                <div class="slidePicTwo">
                	<div class="slidePicArea">
                    	<a href="#"><img src="${resource(dir: 'images', file: 'slide_pic2.jpg')}" /></a>
                    </div>
                </div>
                <div class="slidePicThree">
                	<div class="slidePicArea">
                    	<a href="#"><img src="${resource(dir: 'images', file: 'slide_pic3.jpg')}" /></a>
                    </div>
                </div>
            </div>
            -->

			<div class="mainLogin">
                <div class="tagline">
                    <!--登录区域-->
                    <g:form action="authenticate" method="post" name="loginForm"  onsubmit="return false;">
                    <div class="loginArea" style="">
                        <div class="loginContent" >
                            <g:if test="${errmsg}">
                                <div class="loginTips">${errmsg.encodeAsHTML()}</div>
                            </g:if>
                            <h1>欢迎登录</h1>
                            <div class="login-fields clearFloat">
                                <div class="field">
                                 <input type="text" name="loginname" id="loginname"  placeholder="用户名" class="login username-field" tabindex="1" maxlength="64" value="${params.loginname}"/>
                                </div>
                                <div class="field">
                                    <input type="password" value="" placeholder="密&nbsp;&nbsp;码" name="loginpwd" id="loginpwd"   onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}"
                                           onblur="if(!value) {value=defaultValue; this.type='text';}" class="login password-field" tabindex="1" maxlength="64"/>
                                </div>
                                <div class="field">
                                 <input type="text" name="captcha" id="captcha" placeholder="验证码" class="login verify_code-field"  tabindex="3" maxlength="4"/>
                                 <div class="verify_img"><img id="LoginCaptcha" class="reload-code" onclick="refreshLoginCaptcha();" src="${createLink(controller: 'captcha', action: 'index')}" title="点击图片刷新校验码" alt="点击图片刷新校验码" width="70" height="36"/></div>
                                </div>

                                <div class="field">
                                    <input type="text" name="mobile_captcha" id="mobile_captcha" placeholder="手机验证码" class="login verify_code-field"  tabindex="3" maxlength="6"/>
                                    <div style="float:left;width:20px;"> <button  id="btn_reload" class="mobileBtn">获取验证码</button></div>
                                </div>
                            </div>

                            <div class="login-actions">
                                <div class="loginBtnArea"><input class="loginBtn" type="submit" onclick="do_login();" value="登陆"/><a href="${createLink(controller: 'login', action: 'forget_login')}" target="_parent">忘记密码?</a> </div>
                            </div>

                        </div>
                    </div>
                    </g:form>
                    <!--登录区域结束-->
                </div>
            </div>
</div>


<!--内容介绍区-->
<div class="mainContent">
	<div class="mainContentArea" style="width:1000px;margin:0 auto;">
        <ul>
            <li>
                <div class="mainIcon icon1"></div>
                <div class="mainIconText">让您安心支付</div>
                <div class="mainIconTextSub"><a href="#">网上银行支付</a> | <a href="#">快捷支付
                </a></div>
            </li>


            <li>
                <div class="mainIcon icon2"></div>
                <div class="mainIconText">随时随地 缴费付款</div>
                <div class="mainIconTextSub"><a href="#">吉高PC支付</a> | <a href="#">吉高手机支付</a></div>
            </li>


            <li>
                <div class="mainIcon icon3"></div>
                <div class="mainIconText">付款是件容易的事</div>
                <div class="mainIconTextSub"><a href="#">安全控件</a> | <a href="#">动态口令验证</a></div>
            </li>

        </ul>
    </div>
</div>

<g:render template="/layouts/footer"/>
</div>

<script charset="utf-8" src="${resource(dir: 'js', file: 'jquery.min.js')}"></script>
<script charset="utf-8" src="${resource(dir: 'js', file: 'jquery.skippr.min.js')}"></script>
<script charset="utf-8" src="${resource(dir: 'js', file: 'slider.js')}"></script>
<script>

    function refreshLoginCaptcha() {

        $('#LoginCaptcha').attr("src", "${createLink(controller:'captcha',action:'index')}?" + Math.random() * 100000);

    }


    function init() {

        if (top.location.href != this.location.href) {

            top.location.href = this.location.href;
        }

    }

    function do_login() {

        if ( $.trim($('#loginname').val()) == '' || $.trim($('#loginname').val()) == '请输入Email') {

            alert("请输入账户名");

            $('#loginname').focus();

            return false;

        }

        if($.trim($('#loginpwd').val()) == ''){
            alert("密码不能为空");
            $("#loginpwd").focus();
            return false;
        }
        if ($.trim($('#captcha').val()) == '') {
            alert("请输入验证码");
            $('#captcha').focus();
            return false;
        }
        if ($.trim($('#mobile_captcha').val()) == '') {
            alert("请输入手机验证码");
            $('#mobile_captcha').focus();
            return false;
        }
        document.loginForm.submit();

    }


    E.on('btn_reload','click',function(){
        if ($('#loginname').val().trim() == '' || $('#loginname').val().trim() == '请输入Email') {

            alert("请输入账户名");

            $('#loginname').focus();

            return false;

        }

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
            var loginname = $('#loginname').val().trim();
            Server.sendRequest('${createLink(controller:'login',action:'sendLoginMobileCaptcha',)}',"loginname="+$("#loginname").val(),this.callbackSuccess);
        },
        callbackSuccess : function(response){
            if(response.responseText == 'ok'){
                alert("系统已经发送本次登陆操作的验证码到您绑定的手机上了，请查收");
                timer0=setInterval("timere()",1000);
            }else{
                alert(response.responseText);
            }
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
</script>


</body>
</html>
