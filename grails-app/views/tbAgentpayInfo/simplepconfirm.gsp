<%@ page import="java.text.DecimalFormat; dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>吉高-单笔代付申请确认</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}?t=${new Date().getTime()}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'common.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'decode.js')}"></script>
    <script type="text/javascript">
        function winClose()
        {
            window.parent.win3.close();
        }
    </script>
    <style type="text/css">
      .InContent{
          background:#F2F2F2;padding-bottom: 30px;
          width:500px; border:solid 1px #d2d2d2; height:auto; overflow:hidden; margin:0 auto;
          margin-top:10px;
      }
    </style>

</head>
<body>
<g:form name="actionForm">
    <g:hiddenField name="tradeId" id="tradeId" value="${params.tradeId}" />
    <g:hiddenField name="tradeCardname" id="tradeCardname" value="${params.tradeCardname}" />
    <g:hiddenField name="tradeAccountname" id="tradeAccountname" value="${params.tradeAccountname}" />
    <g:hiddenField name="tradeCardnum" id="tradeCardnum" value="${params.tradeCardnum}" />
    <g:hiddenField name="tradeBranchbank" id="tradeBranchbank" value="${params.tradeBranchbank}" />
    <g:hiddenField name="tradeSubbranchbank" id="tradeSubbranchbank" value="${params.tradeSubbranchbank}" />
    <g:hiddenField name="tradeProvince" id="tradeProvince" value="${params.tradeProvince}" />
    <g:hiddenField name="tradeCity" id="tradeCity" value="${params.tradeCity}" />
    <g:hiddenField name="tradeAmount" id="tradeAmount" value="${params.tradeAmount}" />
    <g:hiddenField name="tradeAccounttype" id="tradeAccounttype" value="${params.tradeAccounttype}" />
    <g:hiddenField name="certificateType" id="certificateType" value="${params.certificateType}" />
    <g:hiddenField name="certificateNum" id="certificateNum" value="${params.certificateNum}" />
    <g:hiddenField name="tradeMobile" id="tradeMobile" value="${params.tradeMobile}" />
    <g:hiddenField name="tradeRemark" id="tradeRemark" value="${params.tradeRemark}" />
    <div class="InContent">
        <div class="boxContent">
            <h1>单笔代付申请确认</h1>
            <div class="normalContent">

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">客户名称：</div>
                    <div class="labelContent fl">${params.tradeCardname}</div>
                </div>
                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">客户开户行：</div>
                    <div class="labelContent fl">${params.tradeAccountname}</div>
                </div>
                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">银行账户：</div>
                    <div class="labelContent fl">${params.tradeCardnum}</div>
                </div>
                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">开户行所在省：</div>
                    <div class="labelContent fl"> ${params.tradeProvince}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">开户行所在市：</div>
                    <div class="labelContent fl">${params.tradeCity}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">开户行分行：</div>
                    <div class="labelContent fl">${params.tradeBranchbank}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">开户行支行：</div>
                    <div class="labelContent fl"> ${params.tradeSubbranchbank}</div>
                </div>

                <div class="Content800 clearFloat c_red">
                    <div class="labelText fl w100 ">代付金额：</div>
                    <div class="labelContent fl">${params.tradeAmount}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">账户类型：</div>
                    <div class="labelContent fl">
                        <g:select name="tradeAccounttype" id="tradeAccounttype" disabled="true" value="${params.tradeAccounttype}" from="${dsf.TbAgentpayDetailsInfo.accountTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="selectStyle"/>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">证件类型：</div>
                    <div class="labelContent fl">
                        <g:select name="certificateType" id="certificateType" disabled="true" value="${params.certificateType}" from="${dsf.TbBizCustomer.certificateTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="selectStyle"/>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">证件号：</div>
                    <div class="labelContent fl"> ${params.certificateNum}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">手机号：</div>
                    <div class="labelContent fl">${params.tradeMobile}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">备注：</div>
                    <div class="labelContent fl">${params.tradeRemark}</div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"></div>
                    <div class="labelContent fl">
                        <input type="button" name="btnConfirm" id="btnConfirm" class="btn-default" value="确认提交" />
                        %{--<button class="btn-default" name="btnConfirm" id="btnConfirm">确认提交</button></div>--}%
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" class="btn-default" onclick="winClose()" value="返回" />
                        %{--<button class="btn-default" onclick="winClose()">返回</button>--}%
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--内容区结束-->
</g:form>


<script type="text/javascript">
    $(document).ready(function() {

        //ajax get 请求
        $('#btnConfirm').click(function(){
            var tradeId = encodeURL($("#tradeId").val());
            var tradeCardname = encodeURL($("#tradeCardname").val());
            var tradeCardnum = encodeURL($("#tradeCardnum").val());
            var tradeAccountname = encodeURL($("#tradeAccountname").val());
            var tradeProvince = encodeURL($("#tradeProvince").val());
            var tradeCity = encodeURL($("#tradeCity").val());
            var tradeBranchbank = encodeURL($("#tradeBranchbank").val());
            var tradeSubbranchbank = encodeURL($("#tradeSubbranchbank").val());
            var tradeAmount = encodeURL($("#tradeAmount").val());
            var tradeAccounttype = encodeURL($("#tradeAccounttype").val());
            //var contractUsercode = encodeURL($("#contractUsercode").val());
            var tradeRemark = encodeURL($("#tradeRemark").val());
            var certificateType = encodeURL($("#certificateType").val());
            var certificateNum = encodeURL($("#certificateNum").val());
            var tradeMobile = encodeURL($("#tradeMobile").val());
            //var tradeCustorder = encodeURL($("#tradeCustorder").val());
            var url = "${createLink(controller:'tbAgentpayInfo',action:'simplepaynew')}"+
                    "?tradeId="+tradeId+"&tradeCardname="+tradeCardname+"&tradeCardnum="+tradeCardnum+"&tradeAccountname="+tradeAccountname+"&tradeBranchbank="+tradeBranchbank+
                    "&tradeSubbranchbank="+tradeSubbranchbank+"&tradeProvince="+tradeProvince+"&tradeCity="+tradeCity+"&tradeAmount="+tradeAmount+
                    "&tradeAccounttype="+tradeAccounttype+"&tradeRemark="+tradeRemark+"&certificateType="+certificateType+
                    "&certificateNum="+certificateNum+"&tradeMobile="+tradeMobile; //&contractUsercode="+contractUsercode+"
            var b =
            {
                success:function(c) {
                    var d = eval("(" + c.responseText + ")");
                    if (d.status == "true") {
                        alert(d.msg);
                    } else {
                        alert(d.msg);
                    }
                    window.parent.document.location.href = "${createLink(controller:'tbAgentpayInfo',action:'simplepay')}";
                    window.parent.win2.close();
                },failure:function(c) {
                AP.widget.xBox.hide();
                alert("脚本出现错误,请稍后再试")
            }
            };
            AP.ajax.asyncRequest("POST", url, b)
        });
    });
</script>
</body>
</html>