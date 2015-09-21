<%@ page import="ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />


        <meta name="layout" content="main" />
		<title>吉高-批量退款</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'sjfw.css')}" media="all" />
	    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'mystyle.css')}" media="all" />
        <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
        <script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'Paging.js')}"></script>
		<g:javascript library="jquery" />
		<script charset="utf-8" src="${resource(dir:'js',file:'jquery-ui-1.8.7.custom.min.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'application.js')}"></script>
		<script src="${resource(dir:'js',file:'jquery.validate.min.js')}" type="text/javascript"></script>
		<script type="text/javascript">

		    function check(){
                var file= document.getElementById('upload')
                var uploadtype = document.getElementById('uploadtype')
                if(uploadtype.value==""){
                   alert("请选择上传模板格式!");
                    return false;
                }
                 if(file.value==""){
                   alert("请选择批量退款文件!");
                    return false;
                }
                else if(file.value.substring(file.value.lastIndexOf('.'))!='.xls'
                        && file.value.substring(file.value.lastIndexOf('.'))!='.xlsx'
                        && file.value.substring(file.value.lastIndexOf('.'))!='.XLS'
                        && file.value.substring(file.value.lastIndexOf('.'))!='.XLSX'){
                    alert("文件格式不正确!");
                     return false;
                } else {
                    return true;
                }

            }
            function makedefault(){
                var modeltype=document.getElementById("uploadtype").value;
                document.getElementById("default").style.display="none";
                if(modeltype==""){
                  alert("请选择上传模板格式");
                  return  false
                }
                else{
                    document.forms[0].action="makedefault";
                    document.forms[0].submit();
                }

            }
            function doS(){
                var uptype = document.getElementById("uptype").value;
               var uploadtype=document.getElementById("uploadtype").value;
                if(uploadtype!=""){
                 document.getElementById("default").style.display="block";
                }
                if(uploadtype==uptype){
                     document.getElementById("default").style.display="none";
                }
                 if(uploadtype==""){
                 document.getElementById("default").style.display="none";
                }
            }


//function isExists()
//{
//  var fso,f;
//  var files=document.all.upload.value;
//  fso=new ActiveXObject("Scripting.FileSystemObject");
//  try{
//    f=fso.GetFile(files);
//    alert(f.size+" Bytes");
//  }catch(e){
//    alert("文件不存在");
//  }
//}
//
//$(document).ready(function() {
//        E.on(D.query(".download-excel"),"click",function(e){
//		download("excel");
//		E.preventDefault(e);
//	});
//	function download(type){
//		var f = D.get("myUpload");
//		f.action=f.action+"?format="+type;
//		f.submit();
//		f.action = "upload";
//		f.method = "post";
//	};
//});

        </script>
</head>
<body >
<div class="xtgl_content">
    <div class="cwmx_mxsm">
        <span class="left">
            <strong>批量退款&nbsp;|&nbsp;</strong>
        </span>
        <span class="left">
            <strong><a href="${createLink(controller: 'refund', action: 'refundHistory')}" >批量退款上传历史</a></strong>
        </span>
    </div>
    <br>
    <g:uploadForm name="myUpload"  method="post" action="upload" enctype="multipart/form-data">
	<table width="100%" class="right_list_tablebx_as" id="dataTbl">

                <div id="msg" class="fm-error" style="color:red">${flash.message?.encodeAsHTML()}</div>

        <tr >
            <td  width="120"height="20%" class="right_fnt">请选择上传模板格式：</td>
            <td  width="200">
                <g:hiddenField name="uptype" id="uptype" value="${uploadtype}"/>
                <g:select style="margin-top:8px;margin-bottom:10px; float:left;" name="uploadtype" from="${typeList}" optionKey="id" optionValue="name" value="${uploadtype}" noSelection="${['':'--请选择--']}" onchange="doS();"/>
            <div id="default" style="display:none" >
                %{--<input type="button" onclick="return makedefault();" class="anniu_5" value="设为默认模式">--}%
               &nbsp;&nbsp;&nbsp; <a href="#" onclick="return makedefault();">设为默认模式</a>
            </div>
            </td>

        </tr>
		<tr class="c2">
            <td height="20%" class="right_fnt" >导入批量退款文件：</td>
            <td class="left_fnt">
                <input type="hidden" name="handle" value="upload">
                <input type="file" name="upload" id="upload" contentEditable="false" style="width:400px;height:25px;margin-top:5px;margin-bottom:5px" />
            </td>
        </tr>
        <tr class="c2">
            <td class="right_fnt"></td>
            <td class="left_fnt">

                <g:actionSubmit style="margin-top:8px;margin-left:20px" class="btn-input" action="dispatch" value="确认" onclick="return check();"/>
            </td>
        </tr>
        <tr class="c2">
            <td height="20%" class="right_fnt"></td>
            <td class="left_fnt">
                <a href="${createLink(controller: 'trade', action: 'getcardfile')}" title="Excel格式">含核对模板下载</a>
                 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<a href="${createLink(controller: 'trade', action: 'uncheckcardfile')}" title="Excel格式">非核对模板下载</a>
            </td>

        </tr>
	</table>
  </g:uploadForm>
</div>
<script>
E.on('upload','focus',function(){
	if(D.get("msg").innerHTML!="")
		D.get("msg").innerHTML="";
});
</script>
</body>
</html>
