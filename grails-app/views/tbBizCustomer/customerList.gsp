<%@ page import="dsf.TbBizCustomer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <META   HTTP-EQUIV="Pragma"   CONTENT="public">
        <META   HTTP-EQUIV="Cache-Control"   CONTENT="must-revalidate, post-check=0, pre-check=0,public">
        <META   HTTP-EQUIV="Expires"  CONTENT="0">

        %{--<meta name="layout" content="main" />--}%
		<title>吉高-客户列表</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css/flick',file:'jquery-ui-1.8.7.custom.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'Paging.js')}"></script>
		<g:javascript library="jquery" />
		<script charset="utf-8" src="${resource(dir:'js',file:'jquery-ui-1.8.7.custom.min.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'application.js')}"></script>
		<script src="${resource(dir:'js',file:'jquery.validate.min.js')}" type="text/javascript"></script>
        <base target="_self">
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
                window.parent.document.location.href = "http://www.baidu.com";
                //window.closeStatus();
                window.close();
                //window.self.close();
            }

            function clickOn(){
                var aa = "";
                var c = 0;
                var list = document.getElementsByName("tradeId");
                for(var i=0;i<list.length;i++){
                    if(list[i].checked){
                        c = c + 1;
                        aa = list[i].value;
                    }
                }
                 if(c!=1){
                     alert("只能选中一条记录！");
                     return false;
                 }
                var objs = aa.split(",");

                /*alert(window.parent.document.getElementById("tradeCardname").value);
                window.parent.document.getElementById("tradeCardname").value = objs[1];
                alert("aaa " + window.parent.document.getElementById("tradeCardname").value);*/
                var tradeId = objs[0];
                var tradeCardname = objs[1];
                 var tradeCardnum = objs[7];
                var tradeAccountname = objs[4];
                var tradeProvince =  objs[2];
                var tradeCity = objs[3];
                var tradeBranchbank = objs[5];
                var tradeSubbranchbank = objs[6];
                var tradeAccounttype = objs[8];
                var contractUsercode = objs[9];
                var tradeRemark2 = objs[10];
                             /* var tradeAccountname = encodeURL($("#tradeAccountname").val());

                              var tradeProvince = encodeURL($("#tradeProvince").val());
                              var tradeCity = encodeURL($("#tradeCity").val());
                              var tradeBranchbank = encodeURL($("#tradeBranchbank").val());
                              var tradeSubbranchbank = encodeURL($("#tradeSubbranchbank").val());
                              var tradeAmount = encodeURL($("#tradeAmount").val());
                              var tradeAccounttype = encodeURL($("#tradeAccounttype").val());
                              //var contractUsercode = encodeURL($("#contractUsercode").val());
                              var tradeRemark2 = encodeURL($("#tradeRemark2").val());*/
                var sss = document.getElementById("bizType").value;

                var url;
                if(sss=="S"){
                    url ="${createLink(controller:'tbAgentpayInfo',action:'simplededuct')}";
                }else if(sss=="F"){
                    url = "${createLink(controller:'tbAgentpayInfo',action:'simplepay')}";
                }


                var ll = url + "?tradeId="+tradeId+"&tradeCardname="+tradeCardname+"&tradeCardnum="+tradeCardnum +
                        "&tradeAccountname="+tradeAccountname+"&tradeProvince=" + tradeProvince +"&tradeCity="+tradeCity+"&tradeBranchbank="+tradeBranchbank +
                                "&tradeSubbranchbank="+tradeSubbranchbank+"&tradeAccounttype="+tradeAccounttype+"&contractUsercode="+contractUsercode+"&tradeRemark="+tradeRemark;


              window.parent.document.location.href = ll;
                window.close();
                /*window.parent.document.forms(0).tradeId.value = objs[0];
                window.parent.document.forms(0).tradeCardname.value = objs[1];

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
                window.parent.document.getElementById("tradeRemark2").value = objs[10];
                window.parent.document.getElementById("tradeRemark2").focus();*/
                //alert("aaaaaaaaaaaaaaaaaa");
                //window.parent.win1.close();
                //alert("bbb:");
                //window.close();

            }
		</script>
		<style>
		.btn-input{
				background:url("${resource(dir:'images',file:'grxxanniu.gif')}") no-repeat transparent;
				border:0 none;
				cursor:pointer;
				width:71px;height:27px;
				color:#fff;
				vertical-align:middle;
				text-align:center;
				float:left;
		}
		.btn-input:hover {	background:url("${resource(dir:'images',file:'grxxanniu1.gif')}") no-repeat transparent;}
		</style>

</head>
<body>

      <div class="cwmx_content">
          <form action="customerList" method="post" name="searchform" id="searchform">
              <div class="mcjy_serchtj">
				<table align="center" class="cwmx_list_table">
					<tr>
						<td scope="col">请选择查询条件：
                            <g:select name="reqSearch" value="${params?.reqSearch}" from="${TbBizCustomer.requiredMap}" optionKey="key" optionValue="value" class="right_top_h2_input" />
							<input name="resultSearch" type="text" class="i-text i-text-s" id="resultSearch" value="${params?.resultSearch}" maxlength="40" />
                            <g:hiddenField name="bizType" id="bizType" value="${params?.bizType}" />
						</td>
						<td scope="col"><input type="submit" class="btn-input" value="搜索"></td>
					</tr>

				</table>
          </div>
          <div>
                <div class="w936">
                        <div id="tb_" class="tb_">
                                        <ul style="padding-left:10px;">                                                
                                                <li id="tb_1" class="hovertab">客户信息</li>
                                        </ul>
                        </div>
                        <div class="ctt">
                          <div class="dis" id="tbc_01" >
                            <table width="100%" class="right_list_table" id="test">
                              <tr>
                                <th scope="col">选择</th>
                                <th scope="col">客户名称</th>
                                <th scope="col">省份</th>
                                <th scope="col">地市</th>
                                <th scope="col">开户行</th>
                                <th scope="col">分行</th>
                                <th scope="col">支行</th>
                                <th scope="col">银行账号</th>
                                <th scope="col">备注</th>
                              </tr>
							  <g:each in="${tradeList}" status="i" var="trade">
                              <tr>
                                <td>
                                        <input type="checkbox" name="tradeId" id="tradeId" value="${trade.id},${trade.cardName},${trade.province},${trade.city},${trade.bank},${trade.branchBank},${trade.subbranchBank},${trade.cardNum},${trade.accountType},${trade.contractCode},${trade.remark}">
                                         </td>
                                    <td>${trade.cardName}</td>
                                    <td>${trade.province}</td>
                                    <td>${trade.city}</td>
                                    <td>${trade.bank}</td>
                                    <td>${trade.branchBank}</td>
                                    <td>${trade.subbranchBank}</td>
                                    <td>${trade.cardNum}</td>
                                    <td>${trade.remark}</td>
                              </tr>
							  </g:each>
                            </table>
							<g:pageNavi total="${tradeListTotal}"/>
                            <div>
                              <input type="button" name="btnChk" id="btnChk" style="margin-left:25px;color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" onclick="clickOn()" value="选中">
                              <input type="button" name="btnDel" id="btnDel" style="margin-left:10px;color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" value="删除">
                              %{--<input type="button" name="btnExt" id="btnExt" style="margin-left:10px;color:#fff;width:71px; height:27px; border:0px;background-image:url(${resource(dir: 'images', file: 'grxxtxan.gif')})" onclick="winClose()" value="退出">--}%
                            </div>
                  </div>
                </div>
             </div>
          </div>
    </form>
</div>

<script type="text/javascript">
    $(document).ready(function(){
            /*$("#btnChk").removeAttr('onclick');
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
                *//*window.parent.document.forms(0).tradeId.value = objs[0];
                window.parent.document.forms(0).tradeCardname.value = objs[1];
                //window.opener.document.getElementById("tradeId").value = objs[0];
                //window.opener.document.getElementById("tradeCardname").value = objs[1];
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
                window.parent.document.getElementById("tradeRemark2").value = objs[10];
                window.parent.document.getElementById("tradeRemark2").focus();*//*
                //alert("aaaaaaaaaaaaaaaaaa");
                //window.parent.win1.close();
                //alert("bbb:");
                //window.close();
            });*/

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
                    window.document.location.href = "${createLink(controller:'tbBizCustomer',action:'delCustomer')}?bizType=" + type + "&ids=" + ids;
                }
            });

           /* $("#btnExt").removeAttr('onclick');
            $("#btnExt").click(function(){
                alert("exit");
                var w = parent.win1;
                alert(w);
                var ws = window.parent.win1;
                alert(ws)
                window.parent.win1.close();
            });*/

        });

</script>
</body>
</html>
