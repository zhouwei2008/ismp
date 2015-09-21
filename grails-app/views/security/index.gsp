<%@ page import="account.AcAccount" %>
<%@ page import="ismp.CmCorporationInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-账户信息</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>
<!--内容区开始-->
<div class="InContent">
    <g:if test="${session.level3Map != null && session.level3Map['security/index'] != null}">
    <div class="boxContent">
        <h1>安全设置</h1>
        <div class="normalContent">
            <g:if test="${session.level3Map != null && session.level3Map['operator/savechangeloginpass'] != null}">
                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">登录密码：</div>
                    <div  class="labelText fl width450"  style="text-align:left"><span style="color:green;">&nbsp;&nbsp;上次登录时间：${session.lastLoginTime?session.lastLoginTime:"本次为首次登录！"}</span></div>
                    <div  class="labelText fl">
                        <input type="button" class="btn-default loginPassBut" value="修改" />
                    </div>
                </div>
            </g:if>

            <g:if test="${session.level3Map != null && session.level3Map['operator/savechangepaypass'] != null}">
                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">支付密码：</div>
                    <div class="labelText fl width450"  style="text-align:left">&nbsp;&nbsp;</div>
                    <div class="labelContent fl">
                        <input type="button" class="btn-default payPassBut" value="修改" />
                    </div>
                </div>
            </g:if>

            <g:if test="${session.level3Map != null && session.level3Map['operator/saveBindMobile'] != null}">
                <div class="Content800 clearFloat">
                    <div class="labelText fl width150">手机绑定：</div>
                    <div class="labelText fl width450"  style="text-align:left"><span style="color:green;">&nbsp;&nbsp;${session.cmCustomerOperator.defaultMobile.encodeAsHTML()}&nbsp;&nbsp;绑定手机后，您可以免费享受手机找回密码等功能</span></div>
                    <div class="labelContent fl">
                        <input type="button" class="btn-default updateBindMobileBut" value="修改" />
                    </div>
                </div>
            </g:if>
        </div>
    </div>
    </g:if>
</div>
<g:render template="/layouts/changePass"/>
<!--内容区结束-->
</body>
</html>
