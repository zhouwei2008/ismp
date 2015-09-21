<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css/flow/css',file:'css.css')}" media="all" />
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-store"/>
    <meta http-equiv="expires" content="0"/>
    <style type="text/css">
        body{background:#666; }
    </style>
    <title>吉高-信息提示</title>
</head>
<body>

    <div class="iframeTrip">
        <div class="oktps">
            <p>申请成功</p>
            <br />
            <br />
            <br />
            <h1>您已成功提交申请信息！</h1><br />
            <br />
            <br />
            <h2>
                您可随时拨打我们客服热线，完成后续审核及签约步骤！<br />

                客服热线<b class="red">xxx-xxx-xxxx</b>
                <br />
                <br />
                %{--联系电话：--}%
                <br />
                <b class="red"></b>
                <br />
                %{--<b class="red">${message(code: 'application.linker')}：${message(code: 'application.phone')}</b>--}%
            </h2>
            <h2><br/>
                <a href="http://www.gicard.net"><img src="${resource(dir: 'css/flow/img', file: 'fanhui.jpg')}" width="136" height="28" /></a>
                <br/>
            </h2>
        </div>
    </div>
</body>
</html>
