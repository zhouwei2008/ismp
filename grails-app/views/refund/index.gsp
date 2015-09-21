<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-退款确认</title>
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
<g:form action="create" useToken="true" name="actionForm">
    <input type="hidden" name="buyer_name" value="${session.cmLoginCertificate.loginCertificate}"/>
    <input type="hidden" name="buyer_id" value="${session.cmCustomer.id}"/>
    <input type="hidden" name="id" value="${trade.id}"/>
    <input type="hidden" name="model" id="model" value="${model}">
<div class="InContent">
    <div class="boxContent">
        <h1>退款交易</h1>
        <div class="normalContent">

            <div class="Content800">
                <div class="lineHeight50 clearFloat">

                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">商户订单号：</label><span class="contentTxt fl width250">${trade.outTradeNo}</span>
                        <label class="labelTxtR fl width150">平台交易号：</label><span class="contentTxt fl width250">${trade.tradeNo}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">交易类型：</label><span class="contentTxt fl width250">${trade.tradeTypeMap[trade.tradeType]}</span>
                        <label class="labelTxtR fl width150">交易状态：</label><span class="contentTxt fl width250">${ismp.TradeBase.statusMap[trade.status]}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">付款方：</label><span class="contentTxt fl width250">${trade.payerName}</span>
                        <label class="labelTxtR fl width150">收款方：</label><span class="contentTxt fl width250">${trade.payeeName}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">交易金额：</label><span class="contentTxt fl width250"><g:formatNumber currencyCode="CNY" number="${trade.amount/100}" type="currency"/>元</span>
                        <label class="labelTxtR fl width150">已退金额：</label><span class="contentTxt fl width250"><g:if test="${trade.refundAmount>0}"><g:formatNumber currencyCode="CNY" number="${(trade.refundAmount)/100}" type="currency"/></g:if><g:else>0</g:else>元</span>
                    </div>

                    <g:if test="${!model.equals('payPassword')}">
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">待审核金额：</label>
                            <span class="contentTxt fl width250">
                                <g:if test="${wamount>0}"><g:formatNumber currencyCode="CNY" number="${(wamount)/100}" type="currency"/></g:if><g:else>0</g:else>元
                            </span>
                            <label class="labelTxtR fl width150">可退金额：</label>
                            <span class="contentTxt fl width250"><g:if test="${(trade.amount-(trade.refundAmount+wamount))>0}"><g:formatNumber currencyCode="CNY" number="${(trade.amount-(trade.refundAmount+wamount))/100}" type="currency"/></g:if><g:else>0</g:else>元
                            </span>
                        </div>
                    </g:if>
                    <g:else>
                        <div class="clearFloat">
                           <label class="labelTxtR fl width150">可退金额：</label>
                           <span class="contentTxt fl width250"><g:if test="${(trade.amount-(trade.refundAmount+wamount))>0}"><g:formatNumber currencyCode="CNY" number="${(trade.amount-(trade.refundAmount+wamount))/100}" type="currency"/></g:if><g:else>0</g:else>元
                            </span>
                            <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                        </div>
                    </g:else>


                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">退款金额：</label>
                        <span class="contentTxt fl width250">
                            <input name="amount" type="text" class="normalInput" id="amount" maxlength=12 value="<g:if test="${amount!=null}">${amount}</g:if><g:else><g:if test="${(trade.amount-(trade.refundAmount+wamount))>0}">${(trade.amount-(trade.refundAmount+wamount))/100}</g:if><g:else>0</g:else></g:else>" /> 元
                        </span>
                        <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                    </div>

                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">退款备注：</label>
                        <span class="contentTxt fl width250">
                            <input name="description" type="text" class="normalInput" id="description" maxlength="64" value="${description}"/>
                        </span>
                        <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                    </div>

                    <g:if test="${terminal.equals('999999') && channelType.equals('2')}">
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">退款账户名称：</label>
                            <span class="contentTxt fl width250"><input name="refundAccName" type="text" class="normalInput" id="refundAccName" onkeyup="value=value.replace(/[^\一-\龥^a-z^A-Z^0-9]/g, '')" maxlength="50" value="" /></span>
                            <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                        </div>

                      <div class="clearFloat">
                          <label class="labelTxtR fl width150">退款账号：</label>
                          <span class="contentTxt fl width250"><input name="refundAccNo" type="text" class="normalInput" id="refundAccNo" maxlength="30" value=""/></span>
                          <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                      </div>

                        <div class="clearFloat">
                            <label class="labelTxtR fl width150">开户行名称：</label>
                            <span class="contentTxt fl width250"><input name="bankName" type="text" class="normalInput" id="bankName" onkeyup="value=value.replace(/[^\一-\龥^a-z^A-Z^0-9]/g, '')" maxlength="50" value=""/>
                            </span>
                            <label class="labelTxtR fl width150"></label><span class="contentTxt fl width450">注：开户行必须填写到支行，例如：建设银行北京分行三元桥支行</span>
                        </div>
                        <div class="clearFloat">
                            <label class="labelTxtR fl width150"></label><span class="contentTxt fl widthAll">
                            注：请确保您预留的退款账户开户名、账号以及开户行等银行账户信息与您成功支付的银行账户信息的一致性，否则将无法成功退款，由此导致的退款不成功和给您带来的不利影响，我公司不承担任何责任。
                            </span>
                            <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                        </div>
                    </g:if>

                    <div class="separateLine"></div>

                    <div class="Content800 clearFloat">
                        <div class="labelText fl w100">&nbsp;</div>
                        <div class="labelContent fl">
                            <input type="submit" class="btn-default" value="申请退款" />
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" id="back" name="back" class="btn-default" value="返回" />
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</g:form>
<!--内容区结束-->

        <script>
            $(document).ready(function() {
                jQuery.validator.addMethod("money",function(a,b){return this.optional(b)||/^\d+(\.\d{0,2})?$/i.test(a)},"Please enter a valid amount.");
                jQuery.validator.addMethod("accName",function(a,b){return this.optional(b)||AntiSqlValid(a)},"Please enter a valid accName.");
                jQuery.validator.addMethod("accNo",function(a,b){return this.optional(b)||(/^[0-9]+$/i.test(a)&&AntiSqlValid(a))},"Please enter a valid accName.");
                jQuery.validator.addMethod("bankName",function(a,b){return this.optional(b)||AntiSqlValid(a)},"Please enter a valid bankName.");
                $("#actionForm").validate({
                    rules: {
                        amount:{required:true,money:true,min:0.01,max:${(trade.amount-(trade.refundAmount+wamount))/100}},
                        refundAccName:{required:true,accName:true},
                        refundAccNo:{required:true,accNo:true},
                        bankName:{required:true,bankName:true}
                    },
                    messages: {
                        amount:{required:"<font color=red>请输入退款金额</font>",money:"<font color=red>无效金额</font>",min:'<font color=red>金额值必须大于{0}</font>',max:'<font color=red>金额值必须小于{0}</font>'},
                        refundAccName:{required:"<font color=red>请输入退款账户名称</font>",accName:"<font color=red>文本框中不能输入特殊字符</font>"},
                        refundAccNo:{required:"<font color=red>请输入退款账号</font>",accNo:"<font color=red>只能输入数字且不能包含特殊字符</font>"},
                        bankName:{required:"<font color=red>请输入开户行名称</font>",bankName:"<font color=red>文本框中不能输入特殊字符</font>"}
                    }
                });

                $("#back").removeAttr('onclick');
                $("#back").click(function(){
                    var url = "${createLink(controller:'trade',action:'sale')}";
                    window.document.location.href = url;
                });

                //防止SQL注入
                function AntiSqlValid(oField)
                {
                    var re= /select|update|delete|exec|count|drop|create|'|"|=|;|>|<|%/i;
                    if(!re.test(oField.toLowerCase())) return true;
                    if(re.test(oField.toLowerCase()))
                    {
                        return false;
                    }
                }
            });
        </script>
</body>
</html>