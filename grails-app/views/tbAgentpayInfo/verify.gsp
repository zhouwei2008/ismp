<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批次审核</title>
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
<g:form name="actionForm" onkeypress="if(event.keyCode==13){return false;}">
    <input type="hidden" name="id" id="id" value="${trade.id}"/>
<div class="InContent">
    <div class="boxContent">
        <h1>批次审核</h1>
        <div class="normalContent">
            <g:if test="${flash.message}">
                <div class="message"><font color="red">${flash.message}</font></div>
            </g:if>
            <div class="Content800">
                <div class="lineHeight50 clearFloat">

                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">批次交易号：</label><span class="contentTxt fl width250">${trade?.id}</span>
                        <label class="labelTxtR fl width150">交易类型：</label><span class="contentTxt fl width250">${trade?.batchTypeMap[trade?.batchType]}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">交易笔数：</label><span class="contentTxt fl width250"><strong style="color:green;">${trade.batchCount}</strong>&nbsp;笔</span>
                        <label class="labelTxtR fl width150">总金额：</label><span class="contentTxt fl width250"><strong style="color:red;"><g:formatNumber currencyCode="CNY" number="${trade?.batchAmount}" type="currency"/></strong>&nbsp;元</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">创建时间：</label><span class="contentTxt fl width250">${trade.batchDate}</span>
                        <label class="labelTxtR fl width150">状态：</label><span class="contentTxt fl width250">${trade?.statusMap[trade?.batchStatus]}</span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">手续费：</label><span class="contentTxt fl width250"><strong style="color:purple;"><g:formatNumber currencyCode="CNY" number="${trade.batchFee}" type="currency"/>&nbsp;元</strong></span>
                        <label class="labelTxtR fl width150">实付金额：</label><span class="contentTxt fl width250"><strong style="color:red;"><g:formatNumber currencyCode="CNY" number="${trade.batchAccamount}" type="currency"/>&nbsp;元</strong></span>
                    </div>
                    <div class="clearFloat">
                        <label class="labelTxtR fl width150">结算方式：</label><span class="contentTxt fl width250">${trade?.feeTypeMap[trade?.batchFeetype]}</span>
                        <label class="labelTxtR fl width150"></label><span class="contentTxt fl width250"></span>
                    </div>

                    <div class="separateLine"></div>


                    <div class="Content800 clearFloat">
                        <div class="labelText fl w100">审核意见：</div>
                        <div class="labelContent fl">
                            <input name="batchRemark1" id="batchRemark1" class="normalInput" type="text" maxlength="64"/>
                        </div>
                    </div>

                    <div class="Content800 clearFloat">
                        <div class="labelText fl w100">&nbsp;</div>
                        <div class="labelContent fl">
                            <g:actionSubmit action="verifySuccess" class="btn-default" value="审核通过"></g:actionSubmit>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <g:actionSubmit action="verifyRefuse" class="btn-default" value="审核拒绝"></g:actionSubmit>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" id="back" name="back" class="btn-default" value="返回" onClick="javascript:history.go(-1);"/>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</g:form>
<!--内容区结束-->

    <script type="text/javascript">

        $(document).ready(function(){

            $("#actionForm").validate({
                rules: {
                    batchRemark1:{required:true,maxlength:15}
                },
                messages: {
                    batchRemark1:{required:"<font color=red>请输入审核意见。</font>",maxlength:"<font color=red>您输入的长度超过{0}个字符。</font>"}

                }
            });
        });

    </script>
</body>
</html>