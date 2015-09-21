<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-绑定手机</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'hetong.css')}" media="all" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'mystyle.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
  </head>
  <body>
  <div class="content">
          <form method="post" action="${createLink(controller:'login',action: 'step2')}" id="actionForm">
          <input type="hidden" name="method" value="mobile">
		  <input type="hidden" id="id" name="id" value="${cmCustomerOperator}">
        	<h1>绑定手机</h1>
			<h2><strong>您还没有绑定手机号，为保证您的账户安全，请先绑定手机号</strong></h2>
			<div class="content_t" style="height:40px">
						<div class="fm-item" style="margin-left:200px;">
							<label class="fm-label" for="mobile" style="margin-top:0">手机号：</label>
							<input name="mobile" id="mobile" class="i-text" type="text" maxlength="11" value=""/>
						</div>
                        <div class="fm-item" style="margin-left:200px;">
							<div id="Msg" class="fm-error" style="color:red">${flash.message?.encodeAsHTML()}</div>
						</div>
			</div>
       		<div class="anniu">
                   <span id="btn1"><input type="submit"class="anniu_2" style="margin-left:400px;" value="确定" tabindex="4"/> <br></span>
			</div>
		</form>
  </div>
<script>
new AP.widget.Validator(
{
	formId:"actionForm",
	ruleType:"id",
	onSubmit:true,
	onSuccess: function()
	{

	},rules:{
			'mobile':{
				minLength:11,
				required:true,
				depends:true,
				desc:"手机号"
			}
	}
});
E.on('mobile','focus',function(){
	if(D.get("Msg").innerHTML!="")
		D.get("Msg").innerHTML="";
});
</script>
  </body>
</html>