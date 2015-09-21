<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="layout" content="main" />
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="cache-control" content="no-store"/>
        <meta http-equiv="expires" content="0"/>
		<title>吉高-信息提示</title>
		<g:if test="${session.cmCustomerOperator}">
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'grxx.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
		</g:if>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'hetong.css')}" media="all" />
</head>
<body>

      <div class="content">
        	<h1>错误</h1>
            <div class="content_t">
                <img src="${resource(dir:'images',file:'error.gif')}" width="89" height="89" />
				<h1></h1>
                <h2>无效访问方式</h2>
            </div>
      </div>

</body>
</html>
