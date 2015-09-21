<%--
  Created by IntelliJ IDEA.
  User: shuo_zhang
  Date: 11-11-21
  Time: 下午1:09
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="ismp.GetRandom" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="layout" content="main" />
<title>吉高-转账</title>
	<link href="${resource(dir:'css',file:'xtgl.css')}" rel="stylesheet" type="text/css" />
	<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'mystyle.css')}" media="all" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css',file:'jygl.css')}" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'sjfw.css')}" media="all" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'js/jquery.uploadify2.1',file:'uploadify.css')}"/>
    <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery-1.4.4.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/jquery.uploadify2.1',file:'swfobject.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/jquery.uploadify2.1',file:'jquery.uploadify.v2.1.0.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'PassGuardCtrl1026.js')}"></script>


	<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
	<g:javascript library="prototype" />
    <g:javascript library="jquery"/>
    <style type="text/css">
       .ocx_style{border:1px solid #7F9DB9; width:120px; height:15px;}
      .down{border:1px solid #7F9DB9;	line-height: 20px;text-align:center;}
    </style>
	%{--<script>--}%
		%{--function search(obj){--}%
			%{--if(obj){--}%
				%{--if($("offset"))--}%
				%{--$("offset").value=0;--}%
			%{--}--}%
			%{--$F('searchform').submit();--}%
		%{--}--}%
	%{--</script>--}%
	%{--<style type="text/css">--}%
		%{--.reload-code{color:#07679C;cursor:pointer;}--}%
		%{--.btn-input{--}%
				%{--background:url("${resource(dir:'images',file:'grxxanniu.gif')}") no-repeat transparent;--}%
				%{--border:0 none;--}%
				%{--cursor:pointer;--}%
				%{--width:71px;height:26px;--}%
				%{--color:#fff;--}%
				%{--vertical-align:middle;--}%
				%{--text-align:center;--}%
				%{--float:left;--}%
		%{--}--}%
		%{--.btn-input:hover {	background:url("${resource(dir:'images',file:'grxxanniu1.gif')}") no-repeat transparent;}--}%
	%{--</style>--}%
</head>
<body>



<!-- 主体 -->
<div id="content">
	<div id="main">
        <br>
        <div class="title" style="margin-left:10px;margin-top:80px" >
            <div class="cwmx_mxsm">
                <span class="left">
                    批量转账
                </span>
                <span class="left" style="margin-left:10px;">
                    <a href="${request.contextPath}/transfer/index"> 单笔转账</a>
                    %{--<g:if test="${session.level3Map != null && session.level3Map['transfer/buy'] != null}">--}%
                        %{--<a href="${request.contextPath}/${session.level3Map['trade/buy'].modelPath}">单笔转账</a>--}%
                        %{--<a href="${createLink(controller: 'trade', action: 'buy')}">所有买入的交易</a>--}%
                    %{--</g:if>--}%
                     <g:if test="${session.level3Map != null && session.level3Map['transfer/index'] != null}">
					    <a href="${request.contextPath}/${session.level3Map['transfer/index'].modelPath}" seed="inpourLink">转账记录</a>
                        %{--<a href="../transfer/list" seed="inpourLink">转账记录</a>--}%
                    </g:if>
                    %{--<g:if test="${session.level3Map != null && session.level3Map['trade/sale'] != null}">--}%
                        %{--<g:if test="${session.level3Map != null && session.level3Map['trade/buy'] != null}">|</g:if>--}%
                        %{--<a href="${request.contextPath}/${session.level3Map['trade/sale'].modelPath}">批量转账</a>--}%
                        %{--<a href="${createLink(controller: 'trade', action: 'sale')}">所有卖出的交易</a>--}%
                    %{--</g:if>--}%
                </span>
            </div>
            %{--<br>--}%
                %{--<h2>转账</h2> <br>--}%
				%{--<div class="link">--}%
                    %{--<g:if test="${session.level3Map != null && session.level3Map['transfer/list'] != null}">--}%
					    %{--<a href="${request.contextPath}/${session.level3Map['transfer/list'].modelPath}" seed="inpourLink">转账记录</a>--}%
                        %{--<a href="../transfer/list" seed="inpourLink">转账记录</a>--}%
                    %{--</g:if>--}%
				%{--</div>--}%
                %{--<br>--}%
                %{--<div>--}%
                    %{--<a href="${request.contextPath}/transfer/index"> 单笔转账</a>--}%
                    %{--<a href="${request.contextPath}/transfer/batchStep1"> 批量转账</a>--}%
                %{--</div>--}%

        </div>
        %{--<br><br>--}%
        %{--<div class="cwmx_content">--}%
            %{--<div class="cwmx_mxsm">--}%
                %{--<span class="left"><strong>我要付款</strong></span>--}%
            %{--</div>--}%
        %{--</div>--}%
        <div class="jyjd_loding">
            <span class="left">1.上传转账文件</span>
            <span class="left"><img src="${resource(dir:'images',file:'right_ico.gif')}" width="21" height="20"></span>
            <span class="hsfong left">2.确认信息</span>
            <span class="left"><img src="${resource(dir:'images',file:'right_ico.gif')}"  width="21" height="20"></span>
            <span class="left">3.完成</span>
        </div>
        <div class="jyjd">
            <g:if test="${transferCheck!=null&&transferCheck.t_result}">
                <h1>文件校验成功</h1>
            </g:if>
            <g:else>
                <h1>文件校验出错</h1>
            </g:else>
            <table width="100%" class="right_list_tablebx_as" id="dataTbl">
                <div id="msg" class="fm-error" style="color:red">${flash.message?.encodeAsHTML()}</div>

                <tr >
                    <td  width="30%" height="20%" class="right_fnt">可用余额：</td>
                    <td  width="200">
                        <em class="ft-green"><g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/></em> 元
                    </td>

                </tr>
                <g:if test="${transferCheck!=null&&transferCheck.t_result}">
                <tr class="c2">
                    <td height="20%" class="right_fnt" >批量转账账户总数量：</td>
                    <td  width="200">
                        ${transferCheck==null?0:transferCheck.suc_count}
                    </td>
                </tr>
                <tr class="c2">
                    <td height="20%" class="right_fnt" >批量转账总金额：</td>
                    <td  width="200">
                        <em class="ft-green"><g:formatNumber currencyCode="CNY" number="${transferCheck==null?0:transferCheck.suc_total/100}" type="currency"/></em> 元
                    </td>
                </tr>
                <tr class="c2">
                    <td height="20%" class="right_fnt" >转账后剩余金额：</td>
                    <td  width="200">
                        <em class="ft-green"><g:formatNumber currencyCode="CNY" number="${transferCheck==null?0:transferCheck.suc_tLeft/100}" type="currency"/></em> 元
                    </td>
                </tr>
                <tbody id="tBody1">
                <tr class="c2">
                    <td class="right_fnt">请输入校验码：</td>
                    <td class="left_fnt">
                                    <label class="fm-label posreltop2" for="captcha" style="margin-top:0">校验码：</label>
                                    <input type = "text" class="i-text i-text-authcode" id="captcha" name="captcha" maxLength="5" value="" />
                                    <img align="absMiddle" id="authCodeImg" src="../captcha/index?t=${new Date().getTime()}" alt="请输入您看到的内容" width="100" height="30" style="cursor:pointer"/>
                                    <span class="fm-link reload-code" id="reload-checkcode" style="cursor:pointer">看不清，换一张</span>
                                    <div class="fm-explain">输入上图中的文字。</div>
                    </td>
                </tr>
                <tr class="c2">
                    <td class="right_fnt"></td>
                    <td class="left_fnt">
                        <span class="btn btn-ok"><input tabindex="4" type="button" value="下一步" onclick="next2();"/></span>
                        <span class="btn btn-ok"><input  tabindex="4" type="button" value="返回"  style="margin-left:10px;" onclick="last1();"/></span>
                    </td>
                </tr>
                </tbody>
                <tbody id="tBody2" style="display:none">
                    <g:form action="batchStep4" method="post" name="actionForm">
                         %{--<input type="hidden" name="paypass" id="paypass" >--}%
                        <input type="hidden" id="upname" name="upname" value = "${transferCheck.file}"maxLength="80"/>
                        <tr class="c2">
                            <td class="right_fnt">请输入支付密码：</td>
                            <td class="left_fnt">
                                <input type="password" seed="fk_input_money" name="paypass" maxLength="13" class="i-text i-text-password" id="paypass" value="" tabindex="1"/>
                                  %{--<script type="text/javascript">IntPassGuardCtrl("_ocx_password","0","1","","ocx_style");</script>--}%
                               %{--<script type="text/javascript">SetPassGuardCtrl("_ocx_password",13,"","","");</script>--}%
                            </td>
                        </tr>
                        <tr class="c2">
                            <td class="right_fnt">请输入手机验证码：</td>
                            <td class="left_fnt">
                                <span style="float:left">
                                    <input class="i-text i-text-authcode" id="mobile_captcha" name="mobile_captcha" maxLength="6" value="" />
                                </span>
                                <span class="fm-link reload-code" style="float:left"><input id="btn_reload" type="button" value="发送验证码" /></span>
                                <div class="fm-explain">输入您手机上收到的验证码。</div>
                            </td>
                        </tr>
                        <tr class="c2">
                            <td class="right_fnt"></td>
                            <td class="left_fnt">
                                <span class="btn btn-ok">
                                    <input id="nextbtn" tabindex="4" type="button" value="确认转账" seed="saveAccount" onclick="formSubmit(this);" />
                                    <input  tabindex="4" type="button" value="返回" style="margin-left:10px;" onclick="last1();"/>
                                </span>
                            </td>
                        </tr>

                </g:form>
                </tbody>
                </g:if>
                <g:else>
                    <g:uploadForm name="batchStep2"  method="post" action="batchStep2" enctype="multipart/form-data">
                        <tr class="c2">
                            <td height="20%" class="right_fnt" >导入批量转账文件：</td>
                            <td class="left_fnt">
                                <input type="file" name="uploadNew" id="uploadNew" contentEditable="false" style="width:400px;height:25px;margin-top:5px;margin-bottom:5px" />
                            </td>
                        </tr>
                        <tr class="c2">
                            <td height="20%" class="right_fnt">错误信息：</td>
                            <td class="left_fnt">
                                ${transferCheck.t_msg==null?"未知错误！":transferCheck.t_msg}
                            </td>
                        </tr>
                        <g:if test="${transferCheck!=null&&!transferCheck.dt_result}">
                            <tr class="c2">
                                <td height="20%" class="right_fnt">错误报告：</td>
                                <td class="left_fnt">
                                    <span><a href="${createLink(controller:'transfer',action:'getBatchTemplateReportFile',params:[file:transferCheck.file])}" title="Excel格式" class="download-excel">下载错误报告</a></span>
                                </td>
                            </tr>

                        </g:if>
                        <tr class="c2">
                            <td class="right_fnt"></td>
                            <td class="left_fnt">
                                <g:actionSubmit style="margin-top:8px;margin-left:20px" class="btn-input" action="batchStep2" value="确认" onclick="return check();"/>
                                %{--<input id="nextbtn" tabindex="4" type="button" value="确认" seed="saveAccount" onclick="formSubmit();" />--}%
                            </td>
                        </tr>
                        <tr class="c2">
                            <td height="20%" class="right_fnt"></td>
                            <td class="left_fnt">
                                <a href="${createLink(controller: 'transfer', action: 'getBatchTemplateFile')}" title="Excel格式">模板下载</a>
                            </td>

                        </tr>
                    </g:uploadForm>
                </g:else>


            </table>
        </div>
    </div>
</div>
<script type="text/javascript">

		    function check(){
                var file= document.getElementById('uploadNew')

                if(file.value==""){
                   alert("请选择批量转账文件!");
                    return false;
                }else if(file.value.substring(file.value.lastIndexOf('.'))!='.xls'
                        && file.value.substring(file.value.lastIndexOf('.'))!='.xlsx'
                        && file.value.substring(file.value.lastIndexOf('.'))!='.XLS'
                        && file.value.substring(file.value.lastIndexOf('.'))!='.XLSX'){
                    alert("文件格式不正确!文件必须为EXCEL文件");
                     return false;
                } else {
                    return true;
                }

            }
</script>
<script>
    E.onDOMReady(function(){
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

    var smsTool = {
        sendCaptcha : function(){
            Server.sendRequest('${createLink(controller:'transfer',action:'sendMobileCaptcha')}','',this.callbackSuccess);
        },
        callbackSuccess : function(response){
            alert("系统已经发送本次转账操作的验证码到您绑定的手机上了，请查收。")
            var btn_reload = D.get("btn_reload");
            btn_reload.disabled = false;
        }
    }
    </script>
    <script>
        function last1(){
            window.location.href("${request.contextPath}/transfer/batchStep1");
        }

        function next2(){
            var ptr = document.getElementById("captcha");
            if(ptr.value!="")
            {
                checkPortrait(ptr.value);
            }else{
//                alert(document.getElementById("upname").value)
                alert("请输入校验码！");
            }
        }
        var xmlHttp;
        function createXMLHttpRequest() {
            if (window.ActiveXObject) {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest) {
                xmlHttp = new XMLHttpRequest();
            }
        }
        function checkPortrait(code) {

            var url = "${createLink(controller:'transfer',action:'batchStep3')}?"
                    + "code="+code;
//            alert(url);
            createXMLHttpRequest();
            xmlHttp.onreadystatechange = handleStateChange;
            xmlHttp.open("GET", url, true);
            xmlHttp.send(null);
        }

        function handleStateChange() {
            if(xmlHttp.readyState == 4) {
                if(xmlHttp.status == 200) {
                    checkedCaptcha(xmlHttp.responseText);
                }
            }
        }

        function checkedCaptcha(rsps){
            var obj = eval("("+rsps+")");
//            alert(rsps);
            if(obj.result){
//                alert('OK');
                showNext();
            }else{
                var ptr = document.getElementById("captcha");
                ptr.value = "";
                alert(obj.msg);
            }
        }
        function showNext(){
            var tb1 =   document.getElementById("tBody1");
            var tb2 =   document.getElementById("tBody2");
            tb1.style.display ="none";
            tb2.style.display ="";
        }



    </script>
    <script>
         function formSubmit(obj){
//            if(GetPassGuardCtrlLength('_ocx_password')==0 ){
//                alert("支付密码不能为空");
//                _$("_ocx_password").focus();
//                return false;
//            }
            if($('#paypass').val().trim() == ''){
                alert("支付密码不能为空");
                $('#paypass').focus();
                return false;
            }
            if ($('#mobile_captcha').val().trim() == '') {

                alert("请输入手机验证码");

                $('#mobile_captcha').focus();

                return false;

            }
            %{--<%--}%
              %{--String  mcrypt_key=GetRandom.generateString(32);--}%
               %{--session.setAttribute("mcrypt_key",mcrypt_key);--}%
             %{--%>--}%
             %{--var  data_arr='<%=mcrypt_key%>';--}%
             %{--ResetPassGuardCtrl("_ocx_password",data_arr);--}%
            %{--_$("paypass").value=GetPassGuardCtrlKeyCode("_ocx_password");//获得密码密文,赋值给表单--}%
             var ptrS = D.get("nextbtn");
             ptrS.disabled = true;
             checkPCaptcha.check(_$("paypass").value);
//             document.all.actionForm.submit();

        }
        var checkPCaptcha = {
            check : function(type){
                              %{--alert('${createLink(controller:'transfer',action:'checkMobileCaptcha',params:[mobile_captcha:''])}'+type);--}%
                Server.sendRequest('${createLink(controller:'transfer',action:'checkPayPassCaptcha',params:[paypass:''])}'+type,'',this.callbackSuccess);
            },
            callbackSuccess : function(response){
                var obj = eval("("+response.responseText+")");
                if(obj.result){
                    var ptr2 = document.getElementById("mobile_captcha");
                    checkMCaptcha.check(ptr2.value);
                }else{
                    var ptrS = D.get("nextbtn");
                    ptrS.disabled = false;
                    var ptrP = D.get("paypass");
                    ptrP.value = '';
                    alert(obj.msg);
                    $('#paypass').focus();
                }
            }
        }


        var checkMCaptcha = {
            check : function(type){
                              %{--alert('${createLink(controller:'transfer',action:'checkMobileCaptcha',params:[mobile_captcha:''])}'+type);--}%
                Server.sendRequest('${createLink(controller:'transfer',action:'checkMobileCaptcha',params:[mobile_captcha:''])}'+type,'',this.callbackSuccess);
            },
            callbackSuccess : function(response){
                var obj = eval("("+response.responseText+")");
                if(obj.result){
                    document.all.actionForm.submit();
                }else{
                    var ptrS = D.get("nextbtn");
                    ptrS.disabled = false;
                    var ptrP = D.get("mobile_captcha");
                    ptrP.value = '';
                    alert(obj.msg);
                }
            }
        }


    </script>
</body>
</html>



%{--<%@ page import="boss.BoNews" %>--}%
%{--<%@ page import="trade.AccountCommandPackage;"%>--}%
%{--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">--}%
%{--<html xmlns="http://www.w3.org/1999/xhtml">--}%
%{--<head>--}%
    %{--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--}%
    %{--<meta name="layout" content="main" />--}%
    %{--<title>吉高-批量转账</title>--}%
    %{--<link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>--}%
    %{--<script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>--}%
    %{--<script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>--}%
    %{--<g:javascript library="jquery"/>--}%
    %{--<script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>--}%
    %{--<script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>--}%
    %{--<script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>--}%
%{--</head>--}%
%{--<body>--}%
    %{--<div class="userxx">--}%
      %{--<div class="usetop">--}%
        %{--<p><span class="left">下午好，<span class="blue">${session.cmCustomer?.name.encodeAsHTML()}</span><span class="usetxt">&nbsp;(${session.cmCustomerOperator.defaultEmail})&nbsp;</span><span class="blue usetxt ">账户信息</span></span>--}%
           %{--<span class="right usetxt b"><strong>可用金额：</strong><span class="doler"><g:if test="${acAccount_Main}"><g:formatNumber currencyCode="CNY" number="${acAccount_Main.balance/100}" type="currency"/></g:if><g:else>0</g:else></span> <strong> 元</strong></span></p>--}%
      %{--</div>--}%
      %{--<div class="usebtm"> <span class="left">--}%
        %{--<g:if test="${session.level3Map != null && session.level3Map['charge/index'] != null}">--}%
            %{--<span class="anniu_2"><a href="${request.contextPath}/${session.level3Map['charge/index'].modelPath}"><input type="button" class="btn mglf10 red"  value="充值" /></a></span>--}%
        %{--</g:if>--}%
        %{--<g:if test="${session.level3Map != null && session.level3Map['withdraw/index'] != null}">--}%
            %{--<span class="anniu_2"><a href="${request.contextPath}/${session.level3Map['withdraw/index'].modelPath}"><input type="button" class="btn mglf10" value="提现" /></a></span>--}%
        %{--</g:if>--}%
        %{--<g:if test="${session.level3Map != null && session.level3Map['transfer/index'] != null}">--}%
            %{--<span class="anniu_2"><a href="${request.contextPath}/transfer/index"><input type="button" class="btn mglf10" value="转账" /></a></span>--}%
        %{--</g:if>--}%
        %{--</span><span class="right fonthui">上次登录时间：${session.lastLoginTime?session.lastLoginTime:"本次为首次登录！"}<br />--}%
                  %{--<g:if test="${PWDMsg}">--}%
                      %{--<strong class="hs" style = "color:red">${PWDMsg}</strong><br/>--}%
                  %{--</g:if></span> </div>--}%
    %{--</div>--}%
    %{--<div class="main_box">--}%
      %{--<div class="main_left">--}%
        %{--<div class="zxgg">--}%
          %{--<div class="zxggtlt">--}%
            %{--<p>最新公告</p>--}%
          %{--</div>--}%
          %{--<ul class="list12" class="list12">--}%
              %{--<%--}%
                %{--def query = {--}%
                    %{--eq('sysId', 'RONGPAY')--}%
                    %{--eq('msgColumn', '吉高')--}%
                %{--}--}%
                %{--def query1 = {--}%
                    %{--eq('sysId', 'RONGPAY')--}%
                    %{--eq('msgColumn', '商户接入')--}%
                %{--}--}%
                %{--def query2 = {--}%
                    %{--eq('sysId', 'RONGPAY')--}%
                    %{--eq('msgColumn', '生活服务')--}%
                %{--}--}%

                %{--params.offset = 0--}%
                %{--params.max = 6--}%
                %{--params.sort = params.sort ? params.sort : "dateCreated"--}%
                %{--params.order = params.order ? params.order : "desc"--}%
                %{--def boNews = BoNews.createCriteria().list(params, query)--}%
                %{--def boNews1 = BoNews.createCriteria().list(params, query1)--}%
                %{--def boNews2 = BoNews.createCriteria().list(params, query2)--}%
              %{--%>--}%
            %{--<g:each in="${boNews}" var="x" status="i">--}%
                %{--<g:if test="${i<10 && x.msgColumn=='吉高'}">--}%
                    %{--<li>${x.dateCreated.format("yyyy-MM-dd")}&nbsp;&nbsp;<a title="${x.content}">${AccountCommandPackage.getContentStr(x.content)}</a></li>--}%
                %{--</g:if>--}%
            %{--</g:each>--}%
          %{--</ul>--}%
        %{--</div>--}%
        %{--<div class="cpfw">--}%
          %{--<div class="zxggtlt">--}%
            %{--<p>常见问题</p>--}%
          %{--</div>--}%
          %{--<ul class="list12">--}%
          %{--</ul>--}%
        %{--</div>--}%
      %{--</div>--}%
      %{--<div class="txmenu">--}%
        %{--<span class="left trnmenutlt">交易管理：</span>--}%
  	    %{--<div class="rtnms">--}%
            %{--<ul>--}%
              %{--<li class="rtncnt blue">批量转账</li>--}%
              %{--<g:if test="${session.level3Map != null && session.level3Map['transfer/index'] != null}">--}%
                %{--<li>--}%
                    %{--<a href="${request.contextPath}/${session.level3Map['transfer/index'].modelPath}">转账记录</a>--}%
                %{--</li>--}%
              %{--</g:if>--}%
            %{--</ul>--}%
        %{--</div>--}%
    %{--</div>--}%
    %{--<form action="batchStep2" method="post" name="actionForm" id="actionForm" style="width:960px; margin:0 auto">--}%
  %{--<div class="txbox">--}%
  	%{--<dl>--}%
        %{--<g:if test="${transferCheck!=null&&transferCheck.t_result}">--}%
            %{--<dt>文件校验成功</dt>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
            %{--<dt>文件校验出错</dt>--}%
        %{--</g:else>--}%
        %{--<div id="msg" style="color:red">${flash.message?.encodeAsHTML()}</div>--}%
        %{--<g:if test="${transferCheck!=null&&transferCheck.t_result}">--}%
            %{--<dt>账户总数量：</dt>--}%
            %{--<dd>${transferCheck==null?0:transferCheck.suc_count}</dd>--}%
            %{--<dt>转账总金额：</dt>--}%
            %{--<dd><span class="doler"><g:formatNumber currencyCode="CNY" number="${transferCheck==null?0:transferCheck.suc_total/100}" type="currency"/>元</span></dd>--}%
            %{--<dt>剩余金额：</dt>--}%
            %{--<dd><span class="doler"><g:formatNumber currencyCode="CNY" number="${transferCheck==null?0:transferCheck.suc_tLeft/100}" type="currency"/>元</span></dd>--}%
            %{--<dt></dt>--}%
            %{--<dd></dd>--}%
            %{--<dt></dt>--}%
            %{--<dd></dd>--}%
            %{--<dt></dt>--}%
            %{--<dd></dd>--}%
            %{--<dt></dt>--}%
            %{--<dd></dd>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
        %{--</g:else>--}%


      %{--<g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">--}%
          %{--<dt>上传转账文件：</dt>--}%
          %{--<dd>--}%
              %{--<input type="file" name="uploadNew" id="uploadNew" contentEditable="false" style="width:400px;height:25px;margin-top:5px;margin-bottom:5px" />--}%
              %{--&nbsp;<a href="${createLink(controller: 'transfer', action: 'getBatchTemplateFile')}" title="Excel格式" style="color:blue;">模板下载</a>--}%
          %{--</dd>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
          %{--因您的可用余额为0，所以无法进行转账操作--}%
      %{--</g:else>--}%
  	%{--</dl>--}%

    %{--<div class="xybbtn">--}%
        %{--<g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">--}%
    	    %{--<input type="submit" class="btn mglf10" value="下一步" onclick="return check();"/>--}%
        %{--</g:if>--}%
    %{--</div>--}%
  %{--</div>--}%
  %{--</form>--}%
  %{--</div>--}%

    %{--<script type="text/javascript">--}%

        %{--function check(){--}%
            %{--var file= document.getElementById('uploadNew')--}%

            %{--if(file.value==""){--}%
               %{--alert("请选择批量转账文件!");--}%
                %{--return false;--}%
            %{--}else if(file.value.substring(file.value.lastIndexOf('.'))!='.xls'--}%
                    %{--&& file.value.substring(file.value.lastIndexOf('.'))!='.xlsx'--}%
                    %{--&& file.value.substring(file.value.lastIndexOf('.'))!='.XLS'--}%
                    %{--&& file.value.substring(file.value.lastIndexOf('.'))!='.XLSX'){--}%
                %{--alert("文件格式不正确!文件必须为EXCEL文件");--}%
                 %{--return false;--}%
            %{--} else {--}%
                %{--return true;--}%
            %{--}--}%

        %{--}--}%
    %{--</script>--}%
%{--</body>--}%
%{--</html>--}%
