<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-批量转账</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
</head>
<body>
    <g:render template="/layouts/baseInfo"/>
    <div class="main_box">
      <div class="main_left">
        <div class="zxgg">
          <div class="zxggtlt">
            <p>最新公告</p>
          </div>
          <g:render template="/layouts/news"/>
        </div>
        <div class="cpfw">
          <div class="zxggtlt">
            <p>常见问题</p>
          </div>
          <ul class="list12">
          </ul>
        </div>
      </div>
      <div class="txmenu">
        <span class="left trnmenutlt">交易管理：</span>
  	    <div class="rtnms">
            <ul>
              <li class="rtncnt blue">批量转账</li>
              <li>
                  <a href="${request.contextPath}/transfer/index">单笔转账</a>
              </li>
              <g:if test="${session.level3Map != null && session.level3Map['transfer/index'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['transfer/index'].modelPath}">转账记录</a>
                </li>
              </g:if>
              <g:if test="${session.level3Map != null && session.level3Map['transfer/check'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['transfer/check'].modelPath}">转账审核</a>
                </li>
              </g:if>
            </ul>
        </div>
    </div>
    <form action="batchStep2" method="post" name="actionForm" id="actionForm" style="width:960px; margin:0 auto">
  <div class="txbox">
  	<dl>
      <g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">
          <dt>上传转账文件：</dt>
          <dd>
              <input type="file" name="uploadNew" id="uploadNew" contentEditable="false" style="width:400px;height:25px;margin-top:5px;margin-bottom:5px" />
              &nbsp;<a href="${createLink(controller: 'transfer', action: 'getBatchTemplateFile')}" title="Excel格式" style="color:blue;">模板下载</a>
          </dd>
      </g:if>
      <g:else>
          因您的可用余额为0，所以无法进行转账操作
      </g:else>
  	</dl>

    <div class="xybbtn">
        <g:if test="${acAccount_Main!=null&&acAccount_Main.balance>0}">
    	    <input type="submit" class="btn mglf10" value="下一步" onclick="return check();"/>
        </g:if>
    </div>
  </div>
  </form>
  </div>

    <script type="text/javascript">

        function check(){
            var file= document.getElementById('uploadNew')

            if(file.value==""){
               alert("请选择批量转账文件!");
                return false;
            }else if(file.value.substring(file.value.lastIndexOf('.'))!='.xls'
                    && file.value.substring(file.value.lastIndexOf('.'))!='.xlsx'
                    && file.value.substring(file.value.lastIndexOf('.'))!='.XLS'
                    && file.value.substring(file.value.lastIndexOf('.'))!='.XLSX'){
                alert("文件格式不正确!文件必须为EXCEL文件");
                 return false;
            } else {
                return true;
            }

        }
    </script>
</body>
</html>
