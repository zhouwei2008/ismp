<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'xbox.css')}" media="all" />
<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
<script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
<script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
<g:javascript library="jquery"/>

<!--修改登录密码 xbox -->
<div class="fn-hide">
    <div  class="xbox-container" id="loginPass">
        <div class="xbox-title">
            <h2>修改登录密码</h2>
            <a id="J_xbox_colse" href="#" class="xbox-close-link">关闭</a>
        </div>

        <div class="line line-title"></div>
        <form method="post" action="#"  id="loginPassForm" name="loginPassForm">
            <input type="hidden" value="${createLink(controller:'operator',action: 'savechangeloginpass')}" id="actionUrl" name="actionUrl"/>
                <div class="normalContent" style="padding-top:0px;padding-bottom: 0px">
                    <div class="Content500 clearFloat">
                        <div class="labelText fl width150">请输入当前登录密码：</div>
                        <div class="labelContent fl">
                            <input class="normalInput" type="password" name="curloginpass" id="curloginpass" value="" maxlength="40"/></div>
                            <div style="clear:left;margin-left:120px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
                    </div>

                    <div class="Content500 clearFloat">
                        <div class="labelText fl width150">请输入新的登录密码：</div>
                        <div class="labelContent fl"><input class="normalInput" type="password" name="newloginpass" id="newloginpass" value="" maxlength="40"/></div>
                        <div style="clear:left;margin-left:120px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
                    </div>

                    <div class="Content500 clearFloat">
                        <div class="labelText fl width150">请确认新的登录密码：</div>
                        <div class="labelContent fl"><input class="normalInput" type="password" name="confirm_newloginpass" id="confirm_newloginpass" value="" maxlength="40"/></div>
                        <div style="clear:left;margin-left:120px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
                    </div>

                    <div class="Content500 clearFloat">
                        <div class="labelText fl width150">&nbsp;</div>
                        <div class="labelContent fl">
                            <input type="submit" class="btn-default" id="submitPass" value="确定"/>
                        </div>
                    </div>

                    <div class="fm-item" style="float:left">
                        <span class="loading-text fn-hide">
                            正在处理，请稍候
                        </span>
                    </div>
                </div>
        </form>
    </div>
</div>
<!--修改登录密码  end xbox -->


%{--<g:if test="${session.cmCustomerOperator.roleSet=='finance'}">--}%
<!--修改支付密码 xbox -->
<div class="fn-hide">
    <div  class="xbox-container" id="payPass">
        <div class="xbox-title">
            <h2>修改支付密码</h2>
            <a id="J_xbox_colse" href="#" class="xbox-close-link">关闭</a>
        </div>
        <div class="line line-title"></div>
        <form method="post" action="#" name="" id="payPassForm" name="payPassForm">
            <input type="hidden" value="${createLink(controller:'operator',action: 'sendDynamicKey')}" id="actionPayUrl1" name="actionPayUrl1"/>
            <input type="hidden" value="${createLink(controller:'operator',action: 'savechangepaypass')}" id="actionPayUrl2" name="actionPayUrl2"/>
            <div class="normalContent" style="padding-top:0px;padding-bottom: 0px">
                <p class="tipsText">一、点击“获取验证码”按钮，系统会发送验证码到您已绑定的手机；<br />二、输入验证码、新的支付密码并确认后，点击确定完成修改。</p>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">手机验证码：</div>
                    <div class="labelContent fl">
                        <input class="normalInput" type="text" name="mobile_captcha" id="mobile_captcha" value="" maxlength="6"/>
                        <input type="button" class="findCustBtn" id="getMC1" value="获取验证码"/>
                    </div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">长度不能小于6</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">请输入新的支付密码：</div>
                    <div class="labelContent fl"><input class="normalInput" type="password" name="newpaypass" id="newpaypass" value="" maxlength="40"/></div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">请确认新的支付密码：</div>
                    <div class="labelContent fl"><input class="normalInput" type="password" name="confirm_newpaypass" id="confirm_newpaypass" value="" maxlength="40"/></div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">要求密码长度大于8位，且必须是数字、字母和特殊字符组合</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">&nbsp;</div>
                    <div class="labelContent fl">
                        <input type="submit" class="btn-default" id="submitPass" value="确定"/>
                    </div>
                </div>

                <div class="fm-item" style="float:left">
                    <span class="loading-text fn-hide">
                        正在处理，请稍候
                    </span>
                </div>
            </div>
        </form>
    </div>
</div>
<!--修改支付密码  end xbox -->
%{--</g:if>--}%

<!--绑定手机 xbox -->
<div class="fn-hide">
    <div  class="xbox-container" id="bindMobile">
        <div class="xbox-title">
            <h2>绑定手机</h2>
            <a id="J_xbox_colse" href="#" class="xbox-close-link">关闭</a>
        </div>

        <div class="line line-title"></div>
        <form method="post" action="#"  id="bindMobileForm1" name="bindMobileForm1">
            <input type="hidden" value="${createLink(controller: 'operator',action:'bindMobile2')}" id="bindMobileUrl1" name="bindMobileUrl"/>
            <div class="normalContent" style="padding-top:0px;padding-bottom: 0px">
                <p class="tipsText">第1步</p>
                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">请输入您要绑定的手机号：</div>
                    <div class="labelContent fl">
                        <input class="normalInput"  type="text" name="mobile" id="mobile" value="" maxlength="40"/>
                    </div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">长度等于11</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">&nbsp;</div>
                    <div class="labelContent fl">
                        <input type="submit" class="btn-default" id="submitPass" value="下一步">
                    </div>
                </div>

                <div class="fm-item" style="float:left">
                    <span class="loading-text fn-hide">
                        正在处理，请稍候
                    </span>
                </div>
            </div>
        </form>

        <form method="post" action="#" id="bindMobileForm2" name="bindMobileForm2" class="fn-hide">
            <input type="hidden" value="${createLink(controller: 'operator',action:'saveBindMobile')}" id="bindMobileUrl2" name="bindMobileUrl2"/>
            <div class="normalContent" style="padding-top:0px;padding-bottom: 0px">
                <p class="tipsText">第2步</p>
                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">请输入手机验证码：</div>
                    <div class="labelContent fl">
                        <input class="normalInput" type="text" name="bind_mobile_captcha" id="bind_mobile_captcha" value="" maxlength="6"/>
                    </div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">长度不能小于6</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">&nbsp;</div>
                    <div class="labelContent fl">
                        <input type="submit" class="btn-default" id="submitPass" value="下一步">
                    </div>
                </div>

                <div class="fm-item" style="float:left">
                    <span class="loading-text fn-hide">
                        正在处理，请稍候
                    </span>
                </div>
            </div>
        </form>
    </div>
</div>
<!--绑定手机  end xbox -->

<!--修改绑定的手机 xbox -->
<div class="fn-hide">
    <div  class="xbox-container" id="updateBindMobile">
        <div class="xbox-title">
            <h2>更改手机</h2>
            <a id="J_xbox_colse" href="#" class="xbox-close-link">关闭</a>
        </div>

        <div class="line line-title"></div>
        <form method="post" action="#"  id="updateMobileForm1" name="updateMobileForm1">
            <input type="hidden" value="${createLink(controller:'operator',action: 'mobileDynamicKey')}" id="actionUpdate1"/>
            <input type="hidden" value="${createLink(controller: 'operator',action:'updateMobileNew')}" id="updateMobileUrl1"/>
            <div class="normalContent" style="padding-top:0px;padding-bottom: 0px">
                <p class="tipsText">第1步:输入原手机校验码</p>
                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">原手机校验码：</div>
                    <div class="labelContent fl">
                        <input class="normalInput"  type="text" id="mobileCaptcha" value="" maxlength="6"/>
                        <input type="button" class="findCustBtn" id="getMC2" value="获取验证码" onclick="get1()">
                    </div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">长度不能小于6</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">&nbsp;</div>
                    <div class="labelContent fl">
                        <input type="submit" class="btn-default" id="submitPass1" value="下一步">
                    </div>
                </div>

                <div class="fm-item" style="float:left">
                    <span class="loading-text fn-hide">
                        正在处理，请稍候
                    </span>
                </div>
            </div>
        </form>

        <form method="post" action="#" id="updateMobileForm2" name="updateMobileForm2" class="fn-hide">
            <input type="hidden" value="${createLink(controller:'operator',action: 'newMobileDynamicKey')}" id="actionUpdate2"/>
            <input type="hidden" value="${createLink(controller: 'operator',action:'checkMobileInfo')}" id="checkMobileInfo"/>
            <input type="hidden" value="${createLink(controller: 'operator',action:'saveUpdateMobile')}" id="updateMobileUrl2"/>
            <div class="normalContent" style="padding-top:0px;padding-bottom: 0px">
                <p class="tipsText">第2步：校验新手机号码</p>
                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">新手机号码：</div>
                    <div class="labelContent fl">
                        <input class="normalInput" type="text" name="new_mobile" id="new_mobile" value="" maxlength="11"/>
                    </div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">长度11位</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">新手机验证码：</div>
                    <div class="labelContent fl">
                        <input class="normalInput"  type="text" name="mobile_captcha2" id="mobile_captcha2" value="" maxlength="6"/>
                        <input type="button" class="findCustBtn" id="getMC3" value="获取验证码">
                    </div>
                    <div style="clear:left;margin-left:120px;"><span class="rightTips">长度不能小于6</span></div>
                </div>

                <div class="Content500 clearFloat">
                    <div class="labelText fl width150">&nbsp;</div>
                    <div class="labelContent fl">
                        <input type="submit" class="btn-default" id="submitPass" value="确定">
                    </div>
                </div>

                <div class="fm-item" style="float:left">
                    <span class="loading-text fn-hide">
                        正在处理，请稍候
                    </span>
                </div>
            </div>
        </form>
    </div>
</div>
<!--修改绑定的手机  end xbox -->



<script charset="utf-8" src="${resource(dir:'js',file:'xbox.js')}"></script>
<script charset="utf-8" src="${resource(dir:'js',file:'decode.js')}"></script>
<script charset="utf-8" src="${resource(dir:'js',file:'xbox_operator.js')}"></script>

<script type="text/javascript" language="javascript">

    //E.on(D.get("getMC2"),"click",function()
    //{
    //	updateMobileCaptcha2();
    //});
    var timer1;
    function get1()
    {
        var a=D.get("actionUpdate1").value;
        document.getElementById("getMC2").disabled = true;
        var b=
        {
            success:function(c)
            {
                var d=c.responseText;
                if(d!="ok")
                {
                    if(d.indexOf("<title>登录</title>")>0)
                    {
                        alert("您的登录已超时，请重新登录");
                        self.parent.AP.widget.xBox.hide();
                        self.parent.location.reload();
                    }else{
                        alert(d);
                    }
                }else{
                    alert("系统已经发送一条手机验证码到您绑定的手机上了，请查收");
                    timer1=setInterval("timer()",1000);
                }
            },failure:function(c)
        {
            AP.widget.xBox.hide();
            alert("脚本出现错误,请稍后再试")
        }
        };
        AP.ajax.asyncRequest("POST",a,b);
    };
    var time1=60;
    function timer()
    {
        if(time1==0){
            document.getElementById("getMC2").disabled  = false;
            document.getElementById("getMC2").value =" 发送验证码 ";
            clearInterval(timer1);
            time1 = 60;
            return false;
        }
        time1 = time1-1;
        document.getElementById("getMC2").value = "  剩 余("+time1+")  ";
    }

    /*检查手机号有效性*/
    E.on(D.get("new_mobile"),"blur",function(){
        if(D.get("new_mobile").value!=""){
            var a=D.get("checkMobileInfo").value;
            a+="?new_mobile=" + encodeURL(D.get('new_mobile').value.trim());
            var b=
            {
                success:function(c)
                {
                    var d=c.responseText;
                    if(d != "")
                    {
                        document.getElementById("checkResult").style.display = "";
                        document.getElementById("checkResult").innerText = d;
                    }else{
                        if(d.indexOf("<title>登录</title>")>0)
                        {
                            alert("您的登录已超时，请重新登录");
                            self.parent.AP.widget.xBox.hide();
                            self.parent.location.reload();
                        }else{
                            document.getElementById("checkResult").style.display = "none";
                        }
                    }
                },failure:function(c){
                AP.widget.xBox.hide();
                alert(c.responseText);
                alert("脚本出现错误,请稍后再试");
            }
            };
            AP.ajax.asyncRequest("POST",a,b);
        }
    });
    E.on(D.get("new_mobile"), "focus", function(e) {
        document.getElementById("checkResult").style.display = "none";
    });
</script>