<%@ page import="java.text.DecimalFormat; dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批量代付申请</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
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

<g:form name="searchform" id="searchform" method="post" enctype="multipart/form-data" action="uploadpaynew">
<!--内容区开始-->
<div class="InContent">
    <div class="boxContent">
        <h1>批量代付申请</h1>
        <div class="normalContent">
            <g:if test="${flash.message}">
                <div class="message"><font color="red">${flash.message}</font></div>
            </g:if>
            <div class="Content800">
                <div style="font-size: 14px;">请选择批量代付批次文件：</div>
                <div><input type="file" name="agentPayFile"/></div>
            </div>

            <div class="stencilPlate">
                <g:link action="getpayfile" class="plateTxt">txt格式模版下载</g:link>
                <g:link action="getpayxlsfile"  class="plateExcel">模板下载EXCEL</g:link>
            </div>
            <div class="Content800 clearFloat">
                <div class="labelText fl width250">&nbsp;</div>
                <div class="labelContent fl"><input type="button" class="btn-default" id="btnSubmit" value="提交" onclick="doSubmit();"/></div>
            </div>
        </div>
    </div>
</div>
</g:form>
<!--内容区结束-->

<script>
    function doSubmit() {
        document.getElementById("btnSubmit").disabled=true;
        document.getElementById("searchform").submit();
    }

    function goSecondStep(){
        document.getElementById("flowStepsCurrent").className="current_2";
        document.getElementById("div1").style.display ="none";
        document.getElementById("div2").style.display ="block";
        document.getElementById("div3").style.display ="none";
    }
    function goThirdStep(){
        document.getElementById("flowStepsCurrent").className="current_3";
        document.getElementById("div1").style.display ="none";
        document.getElementById("div2").style.display ="none";
        document.getElementById("div3").style.display ="block";
    }
</script>
</body>
</html>