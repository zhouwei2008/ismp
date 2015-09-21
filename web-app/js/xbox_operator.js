new AP.widget.xBox({el:D.query(".loginPassBut"),type:"dom",value:"#loginPass",noScroll:true,modal:true,width:550,height:400});
new AP.widget.xBox({el:D.query(".payPassBut"),type:"dom",value:"#payPass",noScroll:true,modal:true,width:550,height:400});
new AP.widget.xBox({el:D.query(".apiKeyPassBut"),type:"dom",value:"#apiKeyPass",noScroll:true,modal:true,width:550,height:400});
new AP.widget.xBox({el:D.query(".bindMobileBut"),type:"dom",value:"#bindMobile",noScroll:true,modal:true,width:550,height:400});
new AP.widget.xBox({el:D.query(".updateBindMobileBut"),type:"dom",value:"#updateBindMobile",noScroll:true,modal:true,width:550,height:400});
new AP.widget.xBox({el:D.query(".welcomeMsgBut"),type:"dom",value:"#welcomeMsg",noScroll:true,modal:true,width:550,height:400});

E.on(D.query(".conBut"),"click",function()
{
	self.parent.AP.widget.xBox.hide();
	self.parent.location.reload();
});


new AP.widget.Validator(
{
	formId:"loginPassForm",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		loginPassAjax();
		E.stopEvent(a);
		return false
	},rules:{
			'curloginpass':{required:true,depends:false,desc:"当前登录密码"},
			'newloginpass':{minLength:6,required:true,depends:false,desc:"新的登录密码"},
			'confirm_newloginpass':{minLength:6,required:true,depends:false,desc:"确认登录密码"}		
	}
});

var loginPassAjax=function()
{
	var a=D.get("actionUrl").value;
	a+="?password="+escape(D.get("curloginpass").value);
	a+="&newpassword="+escape(D.get("newloginpass").value);
	a+="&confirm_newpassword="+escape(D.get("confirm_newloginpass").value);
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
				alert("修改登录密码成功");
				AP.widget.xBox.hide();
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};


E.on(D.get("getMC1"),"click",function()
{
	sendMobileCaptchaAjax();
    document.getElementById("getMC1").disabled = true;
});
var timer0;
var sendMobileCaptchaAjax=function()
{
	var a=D.get("actionPayUrl1").value;
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
                timer0=setInterval("timere()",1000);
				//AP.widget.xBox.hide();
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};

var time=60;
function timere()
{
  if(time==0){
      document.getElementById("getMC1").disabled  = false;
      document.getElementById("getMC1").value =" 发送验证码 ";
      clearInterval(timer0);
      time = 60;
      return false;
  }
    time = time-1;
    document.getElementById("getMC1").value = "  剩 余("+time+")  ";
}

E.on(D.get("getMC3"),"click",function()
{
	updateMobileCaptcha2();
    document.getElementById("getMC3").disabled = true;
});
var timer1;
var updateMobileCaptcha2=function()
{
	var a=D.get("actionUpdate2").value;
	a+="?new_mobile="+escape(D.get("new_mobile").value);
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
                timer1=setInterval("timer2()",1000);
			}
		},failure:function(c)
		{
//			AP.widget.xBox.hide();
			alert("请输入新手机号码。")
		}
	};
	AP.ajax.asyncRequest("POST",a,b);
};
var time2=60;
function timer2()
{
  if(time2==0){
      document.getElementById("getMC3").disabled  = false;
      document.getElementById("getMC3").value =" 发送验证码 ";
      clearInterval(timer1);
      time2 = 60;
      return false;
  }
    time2 = time2-1;
    document.getElementById("getMC3").value = "  剩 余("+time2+")  ";
}

new AP.widget.Validator(
{
	formId:"payPassForm",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		payPassAjax();
		E.stopEvent(a);
		return false
	},rules:{
			'mobile_captcha':{minLength:6,required:true,depends:false,desc:"手机验证码"},
			'newpaypass':{minLength:6,required:true,depends:false,desc:"新的支付密码"},
			'confirm_newpaypass':{minLength:6,required:true,depends:false,desc:"确认支付密码"}
	}
});

var payPassAjax=function()
{
	var a=D.get("actionPayUrl2").value;
	a+="?mobile_captcha="+escape(D.get("mobile_captcha").value);
	a+="&newpassword="+escape(D.get("newpaypass").value);
	a+="&confirm_newpassword="+escape(D.get("confirm_newpaypass").value);
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
				alert("修改支付密码成功,新密码在重新登录后生效。");
				AP.widget.xBox.hide();
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};

new AP.widget.Validator(
{
	formId:"bindMobileForm1",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		bindMobile2Ajax();
		E.stopEvent(a);
		return false
	},rules:{
			'mobile':{type:'mobile',required:true,depends:false,desc:"手机号"}
	}
});

var bindMobile2Ajax=function()
{
	var a=D.get("bindMobileUrl1").value;
	a+="?mobile="+escape(D.get("mobile").value);
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
				//隐藏第一个下一步按钮，显示第二个下一步按钮和手机验证码输入框
				D.addClass(D.get("bindMobileForm1"),"fn-hide");
				D.removeClass(D.get("bindMobileForm2"),"fn-hide");
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};

new AP.widget.Validator(
{
	formId:"bindMobileForm2",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		saveBindMobileAjax();
		E.stopEvent(a);
		return false
	},rules:{
			'bind_mobile_captcha':{minLength:6,required:true,depends:false,desc:"手机验证码"}
	}
});

var saveBindMobileAjax=function()
{
	var a=D.get("bindMobileUrl2").value;
	a+="?mobile_captcha="+escape(D.get("bind_mobile_captcha").value);
	//alert(a);
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
				alert("手机绑定成功");
				self.parent.AP.widget.xBox.hide();
				self.parent.location.reload();
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};


new AP.widget.Validator(
{
	formId:"updateMobileForm1",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		updateMobileAjax();
		E.stopEvent(a);
		return false
	},rules:{
			'mobileCaptcha':{minLength:6,required:true,depends:false,desc:"手机验证码"}
	}
});

var updateMobileAjax=function()
{
	var a=D.get("updateMobileUrl1").value;
	a+="?mobileCaptcha="+escape(D.get("mobileCaptcha").value);

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
				//隐藏第一个下一步按钮，显示第二个下一步按钮和手机验证码输入框
				D.addClass(D.get("updateMobileForm1"),"fn-hide");
				D.removeClass(D.get("updateMobileForm2"),"fn-hide");
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};

new AP.widget.Validator(
{
	formId:"updateMobileForm2",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		saveUpdateMobile();
		E.stopEvent(a);
		return false
	},rules:{
            'new_mobile':{type:'mobile',required:true,depends:false,desc:"新手机号码"},
			'mobile_captcha2':{minLength:6,required:true,depends:false,desc:"手机验证码"}
	}
});

var saveUpdateMobile=function()
{
	var a=D.get("updateMobileUrl2").value;
	a+="?new_mobile="+escape(D.get("new_mobile").value);
    a+="&mobile_captcha2="+escape(D.get("mobile_captcha2").value);

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
				alert("手机修改成功");
				self.parent.AP.widget.xBox.hide();
				self.parent.location.reload();
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};

new AP.widget.Validator(
{
	formId:"apiKeyPassForm",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
	    AP.widget.xBox.hide();
		apiKeyPassAjax();
		E.stopEvent(a);
		return false
	},rules:{
	}
});

var apiKeyPassAjax=function()
{
    if(!confirm("密钥修改会影响到您的支付交易，如确认修改，请联系技术人员，以免影响您的交易")){
         return  false;
       }
	var a=D.get("apiKeyUrl").value;
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
				alert("修改安全校验码成功");
				AP.widget.xBox.hide();
                window.location.href="../security/index.gsp?"
			}
		},failure:function(c)
		{
			AP.widget.xBox.hide();
			alert("脚本出现错误,请稍后再试")
		}
	};
	AP.ajax.asyncRequest("POST",a,b)
};

new AP.widget.Validator(
{
	formId:"welcomeMsgForm",
	ruleType:"id",
	onSubmit:true,
	userDefine:function(a)
	{
		welcomeMsgAjax();
		E.stopEvent(a);
		return false
	},rules:{
			'newwelcomemsg':{minLength:0,required:false,depends:false,desc:"个性化信息"}
	}
});

var welcomeMsgAjax=function()
{
//	var a=D.get("welcomeMsgUrl").value;
//	a+="?newwelcomemsg="+encodeURL(D.get("newwelcomemsg").value);
//	var b=
//	{
//		success:function(c)
//		{
//			var d=c.responseText;
//			if(d!="ok")
//			{
//				if(d.indexOf("<title>登录</title>")>0)
//				{
//					alert("您的登录已超时，请重新登录");
//					self.parent.AP.widget.xBox.hide();
//					self.parent.location.reload();
//				}else{
//					alert(d);
//				}
//			}else{
//				alert("修改个性化信息成功");
//				AP.widget.xBox.hide();
//                window.location.href="../security/index.gsp?"
//			}
//		},failure:function(c)
//		{
//			AP.widget.xBox.hide();
//			alert("脚本出现错误,请稍后再试")
//		}
//	};
//	AP.ajax.asyncRequest("POST",a,b)
    document.welcomeMsgForm.action = "../operator/savechangewelcomemsg";
    document.welcomeMsgForm.submit();
};