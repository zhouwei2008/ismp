<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-充值</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>

    <style type="text/css">
<!--

.rolinList{ width:402px; height:auto; margin:20px auto 0 auto; text-align:left}
.rolinList li{margin-bottom:1px;border:1px solid #DADADA}
.rolinList li h2{ width:380px; height:40px;  background:#fff; font-size:14px; line-height:40px; padding-left:20px; color:#333; cursor:pointer}
.content{ height:150px;width:400px;  background:#fff;  background:#FAFAFA}
.content p{ margin:12px}
-->
</style>
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
              <li class="rtncnt blue">充值</li>
              <g:if test="${session.level3Map != null && session.level3Map['charge/list'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['charge/list'].modelPath}">充值记录</a>
                </li>
              </g:if>
            </ul>
        </div>
    </div>
    <form action="confirm" method="post" name="actionForm" id="actionForm">
   <div class="txbox">

        <input type="text" id="bankname" name="bankname" value="ICBC"/>
        <input type="text" id="payChannel" name="payChannel" value="ICBC_B2B"/>
        <div class="grxx_right">


      <div class="grxx_tabel">
                <ol class="SelectBank clearfix" id="linebank1">

                      <div id="divobj1" >

                            <li>
								<input type="radio" name="pay_channel" id="B2B-ICBC" value="ICBC-icbc1025" checked="" onclick="setBankName(&quot;ICBC_B2B&quot;)"/>
                        		<label for="B2B-ICBC">
									<a href="#">
                                        <img src="../images/bank/ICBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;ICBC_B2B&quot;)"/>
                                    </a>
                        		</label>
                        	</li>

							<li>
                                <input type="radio" name="pay_channel" id="B2B-CCB" value="CCB-ccb102" onclick="setBankName(&quot;CCB_B2B&quot;)"/>
                        		<label for="B2B-CCB">
									<a href="#">
                                        <img src="../images/bank/CCB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CCB_B2B&quot;)"/>
                                    </a>
                        		</label>
                        	</li>

                            <li>
							    <input type="radio" name="pay_channel" id="B2B-ABC" value="ABC-abc101" onclick="setBankName(&quot;ABC_B2B&quot;)"/>
                        		<label for="B2B-ABC">
									<a href="#"><img src="../images/bank/ABC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;ABC_B2B&quot;)"/></a>
                        		</label>
                        	</li>

							<li>
									<input type="radio" name="pay_channel" id="B2B-SDB" value="SDB-sdb101" onclick="setBankName(&quot;SDB_B2B&quot;)"/>
	                        		<label for="B2B-SDB">
										<a href="#">
                                            <img src="../images/bank/SDB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SDB_B2B&quot;)"/>
                                        </a>
	                        		</label>
                        	</li>

							<li>
								<input type="radio" name="pay_channel" id="B2B-CMBC" value="CMBC-cmbc101" onclick="setBankName(&quot;CMBC_B2B&quot;)"/>
                        		<label for="B2B-CMBC">
									<a href="#">
                                        <img src="../images/bank/CMBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CMBC_B2B&quot;)"/>
                                    </a>
                        		</label>
                        	</li>
                      </div>
                </ol>
          </div>


       	  <div class="grxx_tabel">

                <ol class="SelectBank clearfix" id="linebank2">

                      <div id="divobj2" >

                            <li>
								<input type="radio" name="pay_channel" id="B2C-ICBC" value="ICBC-icbc1025" onclick="setBankName(&quot;ICBC&quot;)"/>
                        		<label for="B2C-ICBC">
									<a href="#">
                                        <img src="../images/bank/ICBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;ICBC&quot;)"/>
                                    </a>
                        		</label>
                        	</li>

							<li>
                                <input type="radio" name="pay_channel" id="B2C-CCB" value="CCB-ccb102" onclick="setBankName(&quot;CCB&quot;)"/>
                        		<label for="B2C-CCB">
									<a href="#">
                                        <img src="../images/bank/CCB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CCB&quot;)"/>
                                    </a>
                        		</label>
                        	</li>

                            <li>
							    <input type="radio" name="pay_channel" id="B2C-ABC" value="ABC-abc101" onclick="setBankName(&quot;ABC&quot;)"/>
                        		<label for="B2C-ABC">
									<a href="#"><img src="../images/bank/ABC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;ABC&quot;)"/></a>
                        		</label>
                        	</li>

							<li>
									<input type="radio" name="pay_channel" id="B2C-SDB" value="SDB-sdb101" onclick="setBankName(&quot;SDB&quot;)"/>
	                        		<label for="B2C-SDB">
										<a href="#">
                                            <img src="../images/bank/SDB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SDB&quot;)"/>
                                        </a>
	                        		</label>
                        	</li>

							<li>
								<input type="radio" name="pay_channel" id="B2C-CMBC" value="CMBC-cmbc101" onclick="setBankName(&quot;CMBC&quot;)"/>
                        		<label for="B2C-CMBC">
									<a href="#">
                                        <img src="../images/bank/CMBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CMBC&quot;)"/>
                                    </a>
                        		</label>
                        	</li>
                      </div>
          </ol>
        </div>

<ul class="rolinList" id="rolin">
  <li>
    <h2>企业网银</h2>
    <div class="content">
    </div>
  </li>

  <li>
    <h2>个人网银</h2>

    <div class="content">
    </div>
  </li>
</ul>




    <div class="xybbtn">
    <input type="submit" class="btn mglf10" value="下一步" />
    </div>
  </div>
  </div>
  </form>
  </div>

<script>
    $(document).ready(function() {
        jQuery.validator.addMethod("money",function(a,b){return this.optional(b)||/^\d+(\.\d{0,2})?$/i.test(a)},"Please enter a valid amount.");
        $("#actionForm").validate({
            rules: {
                amount:{required:true,money:true,min:0.01,max:${acAccount_Main==null?0:acAccount_Main.balance/100}},
                captcha:{required:true,minlength:4}
            },
            messages: {
                amount:{required:"请输入提现金额",money:"无效金额",min:'金额值必须大于{0}',max:'金额值必须小于{0}'},
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

    function  setBankName(names){
        document.getElementById('payChannel').value = names;
        document.getElementById('bankname').value = names.replace('_B2B', '');
    }


window.onload = function() {
rolinTab("rolin")
}
function rolinTab(obj) {
var list = $(obj).getElementsByTagName("LI");
var state = {show:false,hidden:false,showObj:false};

for (var i=0; i<list.length; i++) {
var tmp = new rolinItem(list[i],state);
if (i == 0) tmp.pShow();
}
}

function rolinItem(obj,state) {
var speed = 0.0666;
var range = 1;
var interval;
var tarH;
var tar = this;
var head = getFirstChild(obj);
var content = getNextChild(head);
var isOpen = false;
this.pHidden = function() {
if (isOpen) hidden();
}
this.pShow = show;

var baseH = content.offsetHeight;
content.style.display = "none";
var isOpen = false;

head.onmouseover = function() {
this.style.background = "#EFEFEF";
}

head.onmouseout = mouseout;

head.onclick = function() {
this.style.background = "#EFEFEF";
if (!state.show && !state.hidden) {
if (!isOpen) {
head.onmouseout = null;
show();
} else {
hidden();
}

}
}

function mouseout() {
this.style.background = "#FFF"
}
function show() {
head.style.borderBottom = "1px solid #DADADA";
state.show = true;
if (state.openObj && state.openObj != tar ) {
state.openObj.pHidden();
}
content.style.height = "0px";
content.style.display = "block";
content.style.overflow = "hidden";
state.openObj = tar;
tarH = baseH;

interval = setInterval(move,10);
}
function showS() {
isOpen = true;
state.show = false;
}

function hidden() {
state.hidden = true;
tarH = 0;
interval = setInterval(move,10);
}

function hiddenS() {
head.style.borderBottom = "none";
head.onmouseout = mouseout;
head.onmouseout();
content.style.display = "none";
isOpen = false;
state.hidden = false;
}

function move() {
var dist = (tarH - content.style.height.pxToNum())*speed;
if (Math.abs(dist) < 1) dist = dist > 0 ? 1: -1;
content.style.height = (content.style.height.pxToNum() + dist) + "px";
if (Math.abs(content.style.height.pxToNum() - tarH) <= range ) {
clearInterval(interval);
content.style.height = tarH + "px";
if (tarH != 0) {
showS()
} else {
hiddenS();
}
}
}

}
var $ = function($) {return document.getElementById($)};
String.prototype.pxToNum = function() {return Number(this.replace("px",""))}
function getFirstChild(obj) {
var result = obj.firstChild;
while (!result.tagName) {
result = result.nextSibling;
}
return result;
}

function getNextChild(obj) {
var result = obj.nextSibling;
while (!result.tagName) {
result = result.nextSibling;
}
return result;
}

</script>
</body>
</html>
