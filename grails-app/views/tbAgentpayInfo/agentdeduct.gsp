<%@ page import="java.text.DecimalFormat; dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批量代收申请</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
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
      <div class="trnmenu">
        <span class="left trnmenutlt">批量代收申请：</span>
      </div>
      <div class="main_box">
      	  <g:form name="searchform" id="searchform" method="post" enctype="multipart/form-data" action="uploaddeductnew">
              <div class="serchlitst">
                 <g:if test="${flash.message}">
                    <div class="message"><font color="red">${flash.message}</font></div>
                 </g:if>

                 <table class="tlb1" >
                     <tr>
                         <td width="150" class="txtRight b">请选择批量代收批次文件:</td>
                         <td class="txtLeft" style="padding-left:10px;">
                             <input type="file" name="agentDeductFile" style="height:24px;width:450px;"/>
                         </td>
                         <td class="txtLeft">
                             <div class="elxdwn blue"><g:link action="getdeductfile" class="download-txt">模板下载TXT</g:link></div>
                             <div class="elxdwn blue"><g:link action="getdeductxlsfile" class="download-txt">模板下载EXCEL</g:link></div>
                         </td>
                     </tr>
                     <tr>
                         <td colspan="3">
                             <input type="submit" class="btn mglf10" value="提交" />
                         </td>
                     </tr>
                 </table>
              </div>
          </g:form>
      </div>
</body>
</html>