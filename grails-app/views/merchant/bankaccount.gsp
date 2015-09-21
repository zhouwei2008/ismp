<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-银行账号</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>

    <div class="serchlitst">

        <table class="tlb1">
            <tr>
                <th class="txtLeft" scope="col" colspan="2">
                    <b>银行账号信息</b>
                </th>
            </tr>
            <tr><td class="txtRight" scope="col" width="100">开户银行：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.bankCode}</td></tr>
            <tr><td class="txtRight" scope="col">分行：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.branch}</td></tr>
            <tr><td class="txtRight" scope="col">支行：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.subbranch}</td></tr>
            <tr><td class="txtRight" scope="col">行号：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.bankNo}</td></tr>
            <tr><td class="txtRight" scope="col">账户名：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.bankAccountName}</td></tr>
            <tr><td class="txtRight" scope="col">账号：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.bankAccountNo}</td></tr>
            <tr><td class="txtRight" scope="col">账号所在省份：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.accountProvince}</td></tr>
            <tr><td class="txtRight" scope="col">账号所在地市：</td><td class="txtLeft" scope="col">${cmCustomerBankAccount?.accountCity}</td></tr>
        </table>
    </div>
</body>
</html>
