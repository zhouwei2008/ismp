<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-预付费卡充值</title>
        <link href="${resource(dir:'css',file:'xtgl.css')}" rel="stylesheet" type="text/css" />
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'mystyle.css')}" media="all" />
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
		<script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
        <g:javascript library="jquery" />
	   <script src="${resource(dir:'js',file:'jquery.validate.min.js')}" type="text/javascript"></script>

</head>
<body>
      <div class="grxx_content">
      	 <g:render template="/layouts/cmBalanceBar"/>
        <g:form action="confirm" name="actionForm">
        <div class="grxx_right">
       	  <h1>预付费卡充值</h1>
       	  <div class="grxx_tabel">
       	    <h1><span class="left" style=" font-size:12px; font-weight:normal;"><a href="${createLink(controller:'precharge',action:'index')}">预付费卡充值</a></span></h1>
              <div id="divobj1" >
                  <h2 style="margin-top:20px;"><strong class="left">持卡人姓名：</strong><input type="text" name="holderName" id="cardName" value="${params.holderName}"></h2>
                  <h2 style="margin-top:20px;"><strong class="left">持卡人卡号：</strong><input type="text" name="cardNo" id="cardNo" value="${params.cardNo}" onblur="cle(this)"></h2>
                  <h2 style="margin-top:20px;"><strong class="left">充值金额(元)：</strong><input type="text" style="margin-left:-4px;" name="amount" id="amount" value="${params.amount}" onblur="cle(this)"></h2>
                  <h2 style="margin-top:20px;"><strong class="left">备注：</strong><textarea name="note" style="margin-left:45px;" class="i-textarea" id="note" value="${params.note}"></textarea></h2>
                  <h2 style="margin-top:20px;">
                      <strong class="left">验证码：</strong><input type="text"  name="captcha" id="captcha" class="cwmx_input" maxlength="5" style="margin-left:35px;">
                      <img align="absMiddle" id="authCodeImg" src="../captcha/index?t=${new Date().getTime()}" alt="请输入您看到的内容" width="100" height="30" style="cursor:pointer"/>
		              <span class="fm-link reload-code" id="reload-checkcode" style="cursor:pointer">看不清，换一张</span>
                  </h2>
                </div>
                 <g:if test="${session.level3Map != null && session.level3Map['precharge/confirm'] != null}">
                <h2 style="margin-top:20px;">
                      <input  type="submit" style="color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" value="下一步"
                       onMouseOver="""javascript:this.style.backgroundImage='url(${resource(dir: 'images', file: 'grxxtxan1.gif')})';"""
                       onMouseOut="""javascript:this.style.backgroundImage='url(${resource(dir: 'images', file: 'grxxtxan.gif')})';"/>
                </h2>
                    </g:if>
          </div>
        </div>
          </g:form>
      </div>
<script>
    $(document).ready(function() {
            jQuery.validator.addMethod("money",function(a,b){return this.optional(b)||/^\d+(\.\d{0,2})?$/i.test(a.replaceAll(" ",""))},"Please enter a valid amount.");
            jQuery.validator.addMethod("isNum", function(value, element) { var length = value.replaceAll(" ","").length; return this.optional(element) || (length == 16 && /\d{16,16}/.test(value.replaceAll(" ","")));}, "卡号必须为16位整数");

            $("#actionForm").validate({
              rules: {
                    holderName:{maxlength:20},
                    cardNo:{required:true,isNum:true},
                    amount:{required:true,money:true,min:0.01,max:${acAccount_Main==null?0:(acAccount_Main.balance/100)>5000? 5000:acAccount_Main.balance/100}},
                    note:{maxlength:50},
                    captcha:{required:true,minlength:4}
                },
                messages: {
                    holderName:{maxlength:"持卡人姓名最多输入20个汉字"},
                    cardNo:{required:"请输入卡号",isNum:"卡号必须为16位整数"},
                    amount:{required:"请输入充值金额",money:"无效金额",min:'单笔充值金额值必须大于等于{0}',max:'单笔充值金额值必须小于等于{0}'},
                    note:{maxlength:"备注最多输入50个汉字"},
                    captcha:{required:"请输入验证码",minlength:"验证码位数不对"}
                }
            });
        });
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
    function cle(obj){
          obj.value =  obj.value.trimAll();
    }
</script>

</body>
</html>