<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-单笔代付申请</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'js/ext/css',file:'ext-all.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir:'js/ext/js',file:'common.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js/ext/js',file:'ext-base.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js/ext/js',file:'ext-all.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'decode.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js/jquery',file:'jquery.autocomplete.js')}"></script>
    <script type="text/javascript">
        function search(obj){
            if(obj){
                if($("offset"))
                    $("offset").value=0;
            }
            $("#searchform").submit();
        }
    </script>
</head>
<body>

<g:form name="searchform" id="searchform" action="simplepay">
        <g:hiddenField name="tradeId" id="tradeId" value="${params.tradeId}" />
        <g:hiddenField name="contractUsercode" id="contractUsercode" value="${params.contractUsercode}" />
    <!--内容区开始-->
    <div class="InContent">
        <div class="boxContent">
            <h1>单笔代付申请</h1>
            <div class="normalContent">

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>客户名称：</div>
                    <div class="labelContent fl"><g:textField name="tradeCardname" id="tradeCardname" value="${params.tradeCardname}" class="normalInput w300"/><input type="button" name="searchCustomer" id="searchCustomer" class="findCustBtn" value="客户查询"><span class="rightTips">客户名称必须输入</span></div>
                </div>
                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>客户开户行：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeAccountname" id="tradeAccountname"  class="normalInput" value="${params.tradeAccountname}"/>
                        <span class="rightTips">例如：中国工商银行，或中国建设银行</span>
                    </div>
                </div>
                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>客户银行账号：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeCardnum" id="tradeCardnum"  class="normalInput" value="${params.tradeCardnum}" />
                    </div>
                </div>
                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>开户行所在省：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeProvince" id="tradeProvince" class="normalInput"  value="${params.tradeProvince}"  />
                        <span class="rightTips">例如：天津</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>开户行所在市：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeCity" id="tradeCity" class="normalInput" value="${params.tradeCity}"  />
                        <span class="rightTips">例如：天津</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>开户行分行：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeBranchbank" id="tradeBranchbank" class="normalInput" value="${params.tradeBranchbank}"/>
                        <span class="rightTips">例如：天津分行</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>开户行支行：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeSubbranchbank" id="tradeSubbranchbank" class="normalInput" value="${params.tradeSubbranchbank}"/>
                        <span class="rightTips">例如：和平区支行</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100 "><font color="red">* </font>金额：</div>
                    <div class="labelContent fl"><g:textField name="tradeAmount" id="tradeAmount" class="normalInput" value="${params.tradeAmount}"/>
                         <span class="rightTips">最高限额以协议为准</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100"><font color="red">* </font>账户类型：</div>
                    <div class="labelContent fl">
                        <g:select  name="tradeAccounttype" id="tradeAccounttype" value="${params.tradeAccounttype}" from="${dsf.TbAgentpayDetailsInfo.accountTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="selectStyle"/>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">证件类型：</div>
                    <div class="labelContent fl">
                        <g:select name="certificateType" id="certificateType" value="${params.certificateType}" from="${dsf.TbBizCustomer.certificateTypeMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}" class="selectStyle"/>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">证件号：</div>
                    <div class="labelContent fl">
                        <g:textField name="certificateNum" id="certificateNum" class="normalInput" value="${params.certificateNum}"  />
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">手机号：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeMobile" id="tradeMobile" class="normalInput" value="${params.tradeMobile}"  />
                        <span class="rightTips">11位数字</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">备注：</div>
                    <div class="labelContent fl">
                        <g:textField name="tradeRemark" id="tradeRemark" class="normalInput" value="${params.tradeRemark}"  />
                        <span class="rightTips">最多20个字符</span>
                    </div>
                </div>

                <div class="Content800 clearFloat">
                    <div class="labelText fl w100">&nbsp;</div>
                    <div class="labelContent fl"><button class="btn-default">确认提交</button></div>
                    <div class="labelContent fl"><button class="btn-default" id="btnClear" name="btnClear">清除</button></div>
                </div>

            </div>
        </div>
    </div>
    <!--内容区结束-->
</g:form>
</div>

<script type="text/javascript">
    $(document).ready(function(){

        $("#searchCustomer").removeAttr('onclick');
        $("#searchCustomer").click(function(){
            var url = "${createLink(controller:'tbAgentpayInfo',action:'customerList',params:[bizType:'F'])}";      //,params:[bizType:'F']
            win1=new Ext.Window({
                id:'win1',
                title:"客户信息查询",
                width:900,
                modal:true,
                height:500,
                html: '<iframe src='+url +' height="100%" width="100%" name="customer" scrolling="auto" frameBorder="0" onLoad="Ext.MessageBox.hide();">',
                maximizable:true
            });
            win1.show();
        });
    });

    $(document).ready(function() {
        if($("#tradeCardname").val() && $("#tradeAmount").val()){
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
            //var contractUsercode = $("#contractUsercode").val();

            var tradeRemark = encodeURL($("#tradeRemark").val());
            var certificateType=encodeURL($("#certificateType").val());//新加字段证件类型
            var certificateNum=encodeURL($("#certificateNum").val()); //新加字段证件号
            var tradeMobile=encodeURL($("#tradeMobile").val());   //新加字段手机号
            //var tradeCustorder=$("#tradeCustorder").val();   //商户订单号
            if(certificateType==""){
                if(certificateNum!=""){
                    alert("您输入证件号码,请选择证件类型!");
                    return
                }
            }
            else {
                if(certificateNum==""){
                    alert("您选择了证件类型,请输入证件号码!");
                    return
                }
            }
            var regex = /^1[3|4|5|8][0-9]\d{8}$/;

            if(regex.test(tradeMobile) || tradeMobile.length==0 ){}else{
                alert("请输入正确的手机号码!");
                return
            }
            var url = "${createLink(controller:'tbAgentpayInfo',action:'simplepconfirm')}"+
                    "?tradeId="+tradeId+"&tradeCardname="+tradeCardname+"&tradeCardnum="+tradeCardnum+"&tradeAccountname="+tradeAccountname+"&tradeBranchbank="+tradeBranchbank+
                    "&tradeSubbranchbank="+tradeSubbranchbank+"&tradeProvince="+tradeProvince+"&tradeCity="+tradeCity+"&tradeAmount="+tradeAmount+
                    "&tradeAccounttype="+tradeAccounttype+"&tradeRemark="+tradeRemark+"&certificateType="+certificateType+"&certificateNum="+
                    certificateNum+"&tradeMobile="+tradeMobile;    //&contractUsercode="+contractUsercode+"
            win3=new Ext.Window({
                id:'win3',
                title:"请确认付款",
                width:600,
                modal:true,
                height:720,
                html: '<iframe src='+url +' height="100%" width="100%" name="popSett" scrolling="auto" frameBorder="0" onLoad="Ext.MessageBox.hide();">',
                maximizable:true
            });
            win3.show();
        };
    });
    $(document).ready(function() {
        $("#tradeCardname").autocomplete("${createLink(controller:'tbBizCustomer',action:'getPayData')}", {
            minChars: 0, //双击空白文本框时显示全部提示数据
            max: 500,
            width: 300,
            matchContains: true,
            formatItem: function(data, i, total) {
                var row = eval("(" + data + ")");
                //alert("item is " + row);
                return "<I>" + row.cardName + " - " + row.bank + "</I>"; //改变匹配数据显示的格式
                //return "<I>" + row.cardName + " - " + row.bank + " - " + row.desc2 + "</I>"; //改变匹配数据显示的格式
            },
            formatMatch: function(data, i, total) {
                var row = eval("(" + data + ")");
                return row.cardName;
            },
            formatResult: function(data) {
                var row = eval("(" + data + ")");
                return row.cardName;
            }
        }).result(SearchCallback); //选中匹配数据中的某项数据时，调用插件的result()方法
        //自定义返回匹配结果函数
        function SearchCallback(event, data, formatted) {
            var row = eval("(" + data + ")");
            $("#tradeId").val(row.id);
            document.getElementById("tradeCardname").focus();
            $("#tradeCardnum").val(row.cardNum);
            document.getElementById("tradeCardnum").focus();
            $("#tradeAccountname").val(row.bank);
            document.getElementById("tradeAccountname").focus();
            $("#tradeBranchbank").val(row.branchBank);
            document.getElementById("tradeBranchbank").focus();
            $("#tradeSubbranchbank").val(row.subbranchBank);
            document.getElementById("tradeSubbranchbank").focus();
            $("#tradeProvince").val(row.province);
            document.getElementById("tradeProvince").focus();
            $("#tradeCity").val(row.city);
            document.getElementById("tradeCity").focus();
            $("#tradeAccounttype").val(row.accountType);
            document.getElementById("tradeAccounttype").focus();
            //$("#contractUsercode").val(row.contractCode);
            $("#tradeRemark").val(row.remark);
            document.getElementById("tradeRemark").focus();
            $("#certificateType").val(row.certificateType);
            document.getElementById("certificateType").focus();

            $("#certificateNum").val(row.certificateNum);
            document.getElementById("certificateNum").focus();

            $("#tradeMobile").val(row.tradeMobile);
            document.getElementById("tradeMobile").focus();

            //$("#tradeCustorder").val(row.tradeCustorder);
            //document.getElementById("tradeCustorder").focus();

            //$("#branchBank").html("您的选择是：" + (!data ? "空" : formatted));
        };

        /*$("#searchCustomer").removeAttr('onclick');
         $("#searchCustomer").click(function(){
         var url = "${createLink(controller:'tbAgentpayInfo',action:'customerList',params:[bizType:'F'])}";      //,params:[bizType:'F']
         win1=new Ext.Window({
         id:'win1',
         title:"客户信息查询",
         width:900,
         modal:true,
         height:500,
         html: '<iframe src='+url +' height="100%" width="100%" name="customer" scrolling="auto" frameBorder="0" onLoad="Ext.MessageBox.hide();">',
         maximizable:true
         });
         win1.show();
         });*/

        $("#btnClear").removeAttr('onclick');
        $("#btnClear").click(function(){
            $("#tradeCardname").val("");
            $("#tradeCardnum").val("");
            $("#tradeAccountname").val("");
            $("#tradeProvince").val("");
            $("#tradeCity").val("");
            $("#tradeBranchbank").val("");
            $("#tradeSubbranchbank").val("");
            $("#tradeAmount").val("");
            $("#tradeAccounttype").val("");
            $("#tradeRemark").val("");
            $("#certificateType").val("");
            $("#certificateNum").val("");
            $("#tradeMobile").val("");
            // $("#tradeCustorder").val("");
        });
        jQuery.validator.addMethod("stringCheck", function(value, element) {
            return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
        }, "<br><font color=red size=1>只能包括中英文、数字和下划线。</font>");
        jQuery.validator.addMethod("money", function(a, b) {
            return this.optional(b) || /^\d+(\.\d{0,2})?$/i.test(a)
        }, "Please enter a valid amount.");
        jQuery.validator.addMethod("accountCheck", function(value, element) {
            return this.optional(element) || /^([0-9]|-){9,32}$/.test(value);
        }, "<br><font color=red size=1>只能包括数字或横杠。</font>");
        $("#searchform").validate({
            rules: {
                tradeCardname:{required:true,stringCheck:true,maxlength:25},
                tradeAccountname:{required:true,stringCheck:true,maxlength:25},
                tradeCardnum:{required:true, accountCheck:true},
                tradeBranchbank:{ required:true,stringCheck:true,maxlength:25},
                tradeSubbranchbank :{required:true,stringCheck:true,maxlength:25},
                tradeProvince :{required:true,stringCheck:true,maxlength:8},
                tradeCity :{required:true,stringCheck:true,maxlength:15},
                tradeAmount :{required:true, money:true, range:[0.01,999999999]},
                tradeAccounttype :{required:true},
                //contractUsercode:{required:true,rangelength:[15,15]},

                certificateNum :{stringCheck:true, maxlength:33,minlength:8},
                tradeMobile :{stringCheck:true, maxlength:11},
                // tradeCustorder :{required:true,stringCheck:true, maxlength:30},
                tradeRemark:{stringCheck:true,maxlength:20}
            },
            messages: {
                tradeCardname:{required:"<br><font color=red size=1>请输入客户名称。</font>",maxlength:"<br><font color=red size=1>您输入的客户名称长度超过{0}个字符。</font>"},
                tradeAccountname:{required:"<br><font color=red size=1>请选择客户开户行。</font>",maxlength:"<br><font color=red size=1>您输入的银行名称长度超过{0}个字符。</font>"},
                tradeCardnum:{required:"<br><font color=red size=1>请输入客户账号。</font>", accountCheck:"<br><font color=red size=1>账户号只能输入数字或横杠，并且长度在9-32之间。</font>"},
                tradeBranchbank:{required:"<br><font color=red size=1>请输入分行。</font>",maxlength:"<br><font color=red size=1>您输入的分行长度超过{0}个字符。</font>"},
                tradeSubbranchbank:{required:"<br><font color=red size=1>请输入支行。</font>",maxlength:"<br><font color=red size=1>您输入的支行长度超过{0}个字符。</font>"},
                tradeProvince :{required:"<br><font color=red size=1>请输入省份。</font>",maxlength:"<br><font color=red size=1>您输入的省份长度超过{0}个字符。</font>"},
                tradeCity :{required:"<br><font color=red size=1>请输入市。</font>",maxlength:"<br><font color=red size=1>您输入的市长度超过{0}个字符。</font>"},
                tradeAmount :{required:"<br><font color=red size=1>请输入金额。</font>", money:"<br><font color=red size=1>无效金额。</font>",range:"<br><font color=red size=1>输入的金额不在 {0} 和 {1} 之间。</font>"},
                tradeAccounttype :{required:"<br><font color=red size=1>请选择账户类型。</font>"},
                //contractUsercode:{required:"<font color=red>请输入用户协议号。</font>",rangelength:"<font color=red>您输入的协议号长度不是{0}。</font>"},
                certificateNum:{maxlength:"<br><font color=red size=1>您输入的证件号长度超过{0}个字符。</font>",minlength:"<br><font color=red size=1>您输入的证件号长度至少为{0}个字符。</font>"},
                tradeMobile:{maxlength:"<br><font color=red size=1>您输入的手机号长度超过{0}个数字。</font>"},
                //  tradeCustorder:{required:"<br><font color=red size=1>请输入商户订单号。</font>",maxlength:"<br><font color=red size=1>您输入的商户订单号长度超过{0}个字符。</font>"},
                tradeRemark:{maxlength:"<br><font color=red size=1>您输入的用途长度超过{0}个字符。</font>"}
            }
        });
    });

</script>
</body>
</html>