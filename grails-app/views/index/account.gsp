<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-首页</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script>
        function doWithdraw() {
            var form = document.getElementById('jumpform');
            form.action = "../withdraw/index";
            form.submit();
        }
    </script>
</head>


<body>
<form action="" method="post" name="jumpform" id="jumpform">
<!--内容区开始-->
<div class="InContent">
    <!--账户信息与账户级别开始-->
    <div class="itemBox clearFloat">
        <div class="myAccount">
            <h1>账户信息</h1>
            <div class="accout">${session.cmCustomer?.registrationName}</div>
            <div class="accout_name"><span>账户名：</span><span class="accout_text">${session.cmLoginCertificate.loginCertificate}</span></div>
            <div class="accout_state clearFloat"><span>账户状态：</span><span class="realName">${ismp.CmCustomer.statusMap[session.cmCustomer.status]}</span></div>
            <div class="accout_security"><span>安全等级：</span><span class="processBar"><span class="process" style="width:80%"></span></span><span class="account_text">&nbsp;中</span></div>
            <a class="securityStorage" href="${createLink(controller:'security',action:'index')}">强化安全等级>></a>
        </div>

        <div class="myAccount">
            <h1>账户级别</h1>
            <div class="accout_name mt30"><span>账户级别：</span><span class="accout_text">${session.role.roleName}</span></div>
            <div class="accout_balance"><span>当前可用余额：</span><span class="balance">
                <g:if test="${acAccount_Main}"><g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/></g:if>
                <g:else>
                    <%def acAccount_Main=account.AcAccount.findByAccountNo(session.cmCustomer.accountNo)%>
                    <g:formatNumber currencyCode="CNY" number="${acAccount_Main==null?0:acAccount_Main.balance/100}" type="currency"/>
                </g:else>
            <i> 元 </i></span><input type="button" class="accout_balance_btn" value="提现" onclick="doWithdraw()"/></div>
        </div>
    </div>
    <!--账户信息与账户级别结束-->

    <!--登录信息与收支明细开始-->
    <div class="itemBox clearFloat">
        <div class="detailInfo">
            <h1>登录信息</h1>
            <div class="accout_name"><span>注册时间：</span><span class="accout_text">${session.cmCustomerOperator.dateCreated}</span></div>
            <div class="accout_name"><span>上次登录：</span><span class="accout_text">${session.lastLoginTime?session.lastLoginTime:"本次为首次登录！"}</span></div>

        </div>

        <div class="detailInfo">
            <h1>收支明细</h1>
            <div class="detailLeft"><span>今日未结算金额：</span><span class="accout_text">
                <i><g:if test="${acAccount_srv}"><g:formatNumber currencyCode="CNY" number="${acAccount_srv.balance/100}" type="currency"/></g:if><g:else>0</g:else></i>元</span></div>
            <div class="detailRight"><span>冻结金额：</span><span class="accout_text">
             <%def acAccount_freeze=account.AcAccount.findByAccountNoAndStatus(session.cmCustomer.accountNo,'freeze')%>
            <i><g:formatNumber currencyCode="CNY" number="${acAccount_freeze==null?0:acAccount_freeze.balance/100}" type="currency"/></i>元</span></div>
        </div>
    </div>
    <!--登录信息与收支明细结束-->


    <!--最近结算信息开始-->
    <div class="itemBox">
        <div class="todayDetail">
            <div class="todayDetail_title"><h1>最近结算信息</h1></div>
            <div class="todayDetail_tabs"></div>

            <div class="todayDetail_grid">
                <table class="gridStyle_normal">
                    <thead>
                    <tr>
                        <td class="longData">结算日期</td>
                        <td class="type">订单笔数</td>
                        <td class="money">交易净额(元)</td>
                        <td class="state">手续费金额（元）</td>
                        <td class="btnArea">结算金额（元）</td>
                    </tr>
                    </thead>
                    <tbody>

                    <g:each in="${result}" status="i" var="foot">
                        <tr>
                            <td>${foot.FOOT_DATE.format("yyyy-MM-dd")}</td>
                            <td>${foot.TRANS_NUM}</td>
                            <td>${(Math.abs(foot.PAY_AMOUNT) - Math.abs(foot.REF_AMOUNT)).toBigDecimal().divide(100,2,0).toPlainString()}</td>
                            <td><strong style="color:green">${foot.PAY_FEE.toBigDecimal().divide(100,2,0).toPlainString()}</strong></td>
                            <td><strong style="color:red">${(Math.abs(foot.PAY_AMOUNT) - Math.abs(foot.REF_AMOUNT) - foot.PAY_FEE).toBigDecimal().divide(100,2,0).toPlainString()}</strong></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="gridMore">

                <g:if test="${result}">
                    <a href="${request.contextPath}/ftFoot/list">查看全部结算记录</a>
                </g:if>

            </div>
        </div>
    </div>
    <!--最近结算信息结束-->

</div>
<!--内容区结束-->
</form>

</body>
</html>
