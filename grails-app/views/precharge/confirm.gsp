<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-预付费卡充值确认</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
		<script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
        <g:javascript library="jquery" />
	   <script src="${resource(dir:'js',file:'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>
      <div class="grxx_content">
      	 <g:render template="/layouts/cmBalanceBar"/>
        <g:form action="recharge" useToken="true" name="actionForm">
         <g:hiddenField name="holderName" value="${params.holderName}"></g:hiddenField>
         <g:hiddenField name="cardNo" value="${params.cardNo}"></g:hiddenField>
         <g:hiddenField name="amount" value="${params.amount}"></g:hiddenField>
         <g:hiddenField name="note" value="${params.note}"></g:hiddenField>
        <div class="grxx_right">
       	  <h1>预付费卡充值确认</h1>
       	  <div class="grxx_tabel">
       	    <h1><span class="left" style=" font-size:12px; font-weight:normal;"><a href="${createLink(controller:'precharge',action:'index')}">预付费卡充值</a></span></h1>
              <div id="divobj1" >
                  <h2 style="margin-top:20px;"><strong class="left">持卡人姓名：${params.holderName}</strong></h2>
                  <h2 style="margin-top:20px;"><strong class="left">持卡人卡号：${params.cardNo}</strong></h2>
                  <h2 style="margin-top:20px;"><strong class="left">充值金额：${params.amount}元</strong></h2>
                  <h2 style="margin-top:20px;"><strong class="left">备注：${params.note}</strong></h2>
                  <h2 style="margin-top:20px;"><strong class="left">支付密码：</strong><input type="password" name="paypass" style="margin-left:14px;"  id="paypass" value="${params.pwd}" ></h2>
                </div>
                 <g:if test="${session.level3Map != null && session.level3Map['precharge/recharge'] != null}">
                        <h2 style="margin-top:20px;"> <input  type="submit" id="btn_reload"  style="color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" value="提交"
                               onMouseOver="""javascript:this.style.backgroundImage='url(${resource(dir: 'images', file: 'grxxtxan1.gif')})';"""
                               onMouseOut="""javascript:this.style.backgroundImage='url(${resource(dir: 'images', file: 'grxxtxan.gif')})';"/>
                        </h2>
                  </g:if>
          </div>
        </div>
          </g:form>
      </div>
</body>
</html>
