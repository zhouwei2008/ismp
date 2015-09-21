<%@ page import="dsf.TbBizCustomer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>吉高-客户信息列表</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-1.8.2.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-1.9.0.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js/jquery', file: 'jquery-ui-timepicker-addon-chn.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}?t=${new Date().getTime()}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'common.js')}"></script>
    <script type="text/javascript">

        function search(obj){
            if(obj){
                if($("offset"))
                    $("offset").value=0;
            }
            $("#searchform").submit();
        }

        function winClose()
        {
            window.parent.win1.close();
        }
    </script>
</head>
<body>

<form action="customerList" method="post" name="searchform" id="searchform">
    <g:hiddenField name="bizType" id="bizType" value="${params?.bizType}" />
<!--内容区开始-->
<div style="padding-bottom: 30px">
    <div class="searchForm">
        <div class="searchContent">
            <ul class="formContent clearFloat">
                <li class="w500">
                    <label class="txtRight">客户名称:</label>
                    <g:select name="reqSearch" value="${params?.reqSearch}" from="${TbBizCustomer.requiredMap}" optionKey="key" optionValue="value" class="right_top_h2_input" />
                    <input name="resultSearch" type="text" class="normalInput" id="resultSearch" value="${params?.resultSearch}" maxlength="40" />
                </li>
                <li class="formBtnArea">
                    <button class="formBtn">搜索</button>
                </li>
            </ul>
        </div>
    </div>


    <!--搜索结果开始-->
    <div class="searchResult">
        <div class="trdeDetail_grid">
            <table class="gridStyle_normal">
                <thead>
                <tr>
                    <td>选择</td>
                    <td>客户名称</td>
                    <td>省份</td>
                    <td>地市</td>
                    <td>开户行</td>
                    <td>分行</td>
                    <td>支行</td>
                    <td class="longData">银行账号</td>
                    <td>证件类型</td>
                    <td class="longData">证件号</td>
                    <td>手机号</td>
                    <td>备注</td>

                </tr>
                </thead>
                <tbody>
                <g:each in="${tradeList}" status="i" var="trade">
                    <tr>
                        <td>
                            <input type="checkbox" name="tradeId" id="tradeId" value="${trade.id},${trade.cardName},${trade.province},${trade.city},${trade.bank},${trade.branchBank},${trade.subbranchBank},${trade.cardNum},${trade.accountType},${trade.contractCode},${trade.remark},${trade.certificateType},${trade.certificateNum},${trade.tradeMobile}"/>
                        </td>
                        <td>${trade.cardName}</td>
                        <td>${trade.province}</td>
                        <td>${trade.city}</td>
                        <td>${trade.bank}</td>
                        <td>${trade.branchBank}</td>
                        <td>${trade.subbranchBank}</td>
                        <td>${trade.cardNum}</td>
                        <td>${trade.certificateTypeMap[trade.certificateType]}</td>
                        <td>${trade.certificateNum}</td>
                        <td>${trade.tradeMobile}</td>
                        <td>${trade.remark}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                <g:pageNavi total="${tradeListTotal}"/>
            </div>
        </div>
    </div>
    <!--搜索结果结束-->
    <div class="btnArea">
        <button name="btnChk" id="btnChk">选中</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button id="btnDel" name="btnClear">清除</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button id="btnExt" name="btnClear">退出</button>
    </div>
</div>
</form>

<script type="text/javascript">
    $(document).ready(function(){
        $("#btnChk").removeAttr('onclick');
        $("#btnChk").click(function(){
            var aa = "";
            var c = 0;
            $("input[type='checkbox']:checkbox:checked").each(function(){
                c = c + 1;
                aa=$(this).val();
            });
            if(c!=1){
                alert("只能选中一条记录！");
                return false;
            }
            var objs = aa.split(",");
            //window.parent.document.forms(0).tradeId.value = objs[0];
            //window.parent.document.forms(0).tradeCardname.value = objs[1];
            window.parent.document.getElementById("tradeId").value = objs[0];
            window.parent.document.getElementById("tradeCardname").value = objs[1];
            window.parent.document.getElementById("tradeCardname").focus();
            window.parent.document.getElementById("tradeCardnum").value = objs[7];
            window.parent.document.getElementById("tradeCardnum").focus();
            window.parent.document.getElementById("tradeAccountname").value = objs[4];
            window.parent.document.getElementById("tradeAccountname").focus();
            window.parent.document.getElementById("tradeProvince").value = objs[2];
            window.parent.document.getElementById("tradeProvince").focus();
            window.parent.document.getElementById("tradeCity").value =objs[3];
            window.parent.document.getElementById("tradeCity").focus();
            window.parent.document.getElementById("tradeBranchbank").value = objs[5];
            window.parent.document.getElementById("tradeBranchbank").focus();
            window.parent.document.getElementById("tradeSubbranchbank").value = objs[6];
            window.parent.document.getElementById("tradeSubbranchbank").focus();
            window.parent.document.getElementById("tradeAccounttype").value = objs[8];
            window.parent.document.getElementById("tradeAccounttype").focus();
            window.parent.document.getElementById("contractUsercode").value = objs[9];
            if(window.parent.document.getElementById("contractUsercode").type=='text'){
                window.parent.document.getElementById("contractUsercode").focus();
            }
            window.parent.document.getElementById("certificateType").value = objs[11];
            window.parent.document.getElementById("certificateType").focus();
            window.parent.document.getElementById("certificateNum").value = objs[12];
            window.parent.document.getElementById("certificateNum").focus();
            window.parent.document.getElementById("tradeMobile").value = objs[13];
            window.parent.document.getElementById("tradeMobile").focus();
            window.parent.document.getElementById("tradeRemark").value = objs[10];
            window.parent.document.getElementById("tradeRemark").focus();
            //alert("aaaaaaaaaaaaaaaaaa");
            window.parent.win1.close();
            //alert("bbb:");
            //window.close();
        });

        $("#btnDel").removeAttr('onclick');
        $("#btnDel").click(function(){
            var ids = "";
            var c = 0;
            $("input[type='checkbox']:checkbox:checked").each(function(){
                c = c + 1;
                ids = ids + "," + $(this).val().split(",")[0];
            });
            if(c==0){
                alert("至少选中一条记录！");
                return false;
            }
            var type = $("#bizType").val();
            var ids = ids.substr(1);
            if(confirm("您确认执行删除操作吗？")){
                window.document.location.href = "${createLink(controller:'tbAgentpayInfo',action:'delCustomer')}?bizType=" + type + "&ids=" + ids;
            }
        });
    });

</script>
</body>
</html>
