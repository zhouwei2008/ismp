<%--
  Created by IntelliJ IDEA.
  User: tkZhu
  Date: 11-6-20
  Time: 下午1:44
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>吉高-充值确认</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css/flick',file:'jquery-ui-1.8.7.custom.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}?t=${new Date().getTime()}"></script>
        <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
        <script charset="utf-8" src="${resource(dir: 'js', file: 'xbox.js')}"></script>
        <script charset="utf-8" src="${resource(dir: 'js', file: 'decode.js')}"></script>
		<g:javascript library="jquery" />
		<script charset="utf-8" src="${resource(dir:'js',file:'jquery-ui-1.8.7.custom.min.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'application.js')}"></script>

        <script type="text/javascript">
            function winClose()
            {
                window.parent.win1.close();
            }
        </script>
</head>
    <body>

                    <g:form action="create" id="actionForm" name="actionForm" target="_blank">

                        <input type="hidden" name="buyer_name" id="buyer_name" value="${session.cmLoginCertificate.loginCertificate}"/>
                        <input type="hidden" name="buyer_id" id="buyer_id" value="${session.cmCustomer.customerNo}"/>
                        <input type="hidden" name="amount" id="amount" value="${params?.amount}"/>
                        <input type="hidden" name="preference" id="preference" value="${params.preference}"/>
                        <input type="hidden" name="bankname" id="bankname" value="${params.bankname}"/>

            <div >
                        <div style="margin-top:20px; float:left; width:180px;"><p><strong class="left">&nbsp;&nbsp;&nbsp;充值银行：</strong>
                            <img src="${resource(dir:'images/bank',file:params.bankname?.encodeAsHTML() + "_OUT.gif")}" width="100" class="left" height="20" /></p></div>

                        <div style="margin-top:20px; float:left; width:170px;"><p><strong class="lvsfong">&nbsp;&nbsp;&nbsp;充值金额：</strong>
                                <strong>${params?.amount}</strong>元</p></div>
                        <div style="margin:20px 0 20px 65px; float:left; width:170px;" class="grxx_topxx">
                            <p style="padding-left:1px; padding-top:5px"><input type="button" name="btnConfirm" id="btnConfirm" class="anniu_3" value="提交">

                            <input type="button" onclick="winClose()" value="返回" style="margin-left:5px"  class="anniu_3"> </p>
                         </div>
                        </div>

                        </g:form>

                <script type="text/javascript">
                      $(document).ready(function() {
                          $('#btnConfirm').removeAttr('onclick');
                          $('#btnConfirm').click(function(){
                              document.forms[0].submit();
                              winClose();
                          });
                    });
                </script>
    </body>
</html>
