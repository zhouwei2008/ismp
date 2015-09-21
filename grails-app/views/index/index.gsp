<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="layout" content="main" />
<title>吉高-首页</title>
<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'grxx.css')}" media="all" />
<script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
</head>
<body>

	<div class="grxx_content">
		<g:render template="/layouts/cmBalanceBar"/>
        <div class="index_congtent">
        	<h1>你好，${session.cmCustomerOperator?.name.encodeAsHTML()}。</h1>
        	<div class="index_asd"></div>
        </div>
		<g:render template="/layouts/notice"/>
      </div>

</body>
</html>
