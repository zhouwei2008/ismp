<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>绑定手机</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-store"/>
    <meta http-equiv="expires" content="0"/>
    <g:javascript library="prototype" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <style>
    #message{
        color:green
    }
    #error{
        color:red
    }
    </style>
    <script>
        function step2()
        {
            $("message").innerHTML='您的操作已提交，请稍候...';
//            $("btn1").hide();
            $("error").innerHTML='';
            new Ajax.Request('${createLink(controller:'operator',action:'bindMobile2')}',{asynchronous:true,evalScripts:true,onSuccess:function(e){step2_res(e)},parameters:Form.serialize($('form1'))});
            return false;
        }
        function step2_res(response)
        {
            $("message").innerHTML='';
            if(response.responseText !='ok')
            {
                $("error").innerHTML=response.responseText;
//                $("btn1").show();
            }else{
                $("error").innerHTML='';
                $("layer1").hide();
                $("layer2").show();
            }
        }
        function step3()
        {
            $("message").innerHTML='您的操作已提交，请稍候...';
//            $("btn2").hide();
            $("error").innerHTML='';
            new Ajax.Request('${createLink(controller:'operator',action:'saveBindMobile')}',{asynchronous:true,evalScripts:true,onSuccess:function(e){step3_res(e)},parameters:Form.serialize($('form2'))});
            return false;
        }
        function step3_res(response)
        {
            $("message").innerHTML='';
            if(response.responseText !='ok')
            {
                $("error").innerHTML=response.responseText;
//                $("btn2").show();
            }else{
                $("error").innerHTML='';
                $("message").innerHTML='绑定手机成功';
            }
        }
    </script>
</head>
<body>
<!--内容区开始-->
<div class="InContent">

        <div class="boxContent">
            <h1>绑定手机</h1>
            <div class="normalContent">
                    <div class="Content800 clearFloat" id="layer1">
                        <form id="form1" method="post">
                        <div class="labelText fl width250">请输入您要绑定的手机号：</div>
                        <div class="labelContent fl">
                            <input id="mobile" name="mobile" class="normalInput" type="text" maxlength="40">
                            <input type="button" class="btn-default" onclick="step2()" value="下一步"/>
                        </div>
                        </form>
                    </div>

                    <div class="Content800 clearFloat" id="layer2" style="display:none">
                    <form id="form2" method="post">
                        <div class="labelText fl width250">请输入您手机上收到的验证码：</div>
                        <div class="labelContent fl">
                            <input id="mobile_captcha" name="mobile_captcha" class="normalInput" type="text" maxlength="40">
                            <input type="button" class="btn-default" onclick="step3()" value="下一步"/>
                        </div>
                      </form>
                    </div>

                <div id="message"></div>
                <div id="error"></div>
            </div>
        </div>
</div>
<!--内容区结束-->


</body>
</html>