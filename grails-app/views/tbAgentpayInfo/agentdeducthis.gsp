<%@ page import="dsf.TbAgentpayInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-代收交易</title>
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
  <span class="left trnmenutlt">代收付：</span>
  <div class="rtnms">
    <ul>
      <li class="rtncnt blue">代收交易</li>
    </ul>
  </div>
</div>
<form action="agentdeducthis" method="post" name="searchform" id="searchform" style="width:960px; margin:0 auto">
    <div class="serch">
      <p>搜索</p>
      <table class="serchtlb">
          <tr>
            <td scope="col" width="400">&nbsp;&nbsp;开始日期:
                <g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="8"/>
                &nbsp;&nbsp;结束日期:
                <g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="8"/>
            </td>
            <td width="252" scope="col">批次交易号:
              <input name="tradeNo" type="text" id="tradeNo" value="${params.tradeNo}" maxlength="40"/>
            </td>
            <td scope="col">
                <input type="submit" class="serchbtn" value="搜索" />
            </td>
            <td scope="col" class="blue">
                <a href="#" id="serch">更多条件</a>
            </td>
          </tr>
      </table>
      <table class="serchtlb" style="display:none" id="serchmore">
          <tr>
            <td scope="col"  width="400"> 上传文件名:
                <input name="filename" type="text" id="filename" value="${params.filename}" maxlength="80" style="width:220px;"/>
            </td>
            <td scope="col" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态:
              <g:select name="batchStatus" value="${params.batchStatus}" from="${TbAgentpayInfo.statusMap}" optionKey="key" optionValue="value" noSelection="${['':'-请选择-']}"/>
            </td>
          </tr>
      </table>
    </div>

    <div class="serchlitst">

        <table class="tlb1">
          <tr>
            <th class="txtCenter" scope="col">类型</th>
            <th class="txtCenter" scope="col">创建时间</th>
            <th class="txtCenter" scope="col">批次交易号</th>
            <th class="txtCenter" scope="col">总数</th>
            <th class="txtCenter" scope="col">总金额（元）</th>
            <th class="txtCenter" scope="col">状态</th>
            <th class="txtCenter" scope="col">备注</th>
            <th class="txtCenter" scope="col">上传文件名</th>
            <th class="txtCenter" scope="col">操作</th>
          </tr>
            <g:each in="${tradeList}" status="i" var="trade">
                <tr>
                    <td class="txtCenter" scope="col">${trade.batchTypeMap[trade.batchType]}</td>
                    <td scope="col" class="blue">${trade.batchDate}</td>
                    <td class="txtCenter" scope="col">${trade.id}</td>
                    <td class="txtCenter" scope="col"><strong style="color:green;">${trade.batchCount}</strong></td>
                    <td class="txtCenter" scope="col"><strong style="color:red;"><g:formatNumber currencyCode="CNY" number="${trade.batchAmount}" type="currency"/></strong></td>
                    <td class="txtCenter" scope="col">${trade.statusMap[trade.batchStatus]}</td>
                    <td class="txtCenter" scope="col">${trade.batchRemark1}</td>
                    <td class="txtCenter" scope="col">${trade.batchFilename}</td>
                    <td class="txtCenter" scope="col">
                        <g:if test="${session.level3Map != null && session.level3Map['tbAgentpayInfo/detaillist'] != null}">
                          <a href="${request.contextPath}/${session.level3Map['tbAgentpayInfo/detaillist'].modelPath}?id=${trade.id}&tradeflag=1" style="color:blue;">查看明细</a>
                        </g:if>
                    </td>
                </tr>
            </g:each>
        </table>
        <div class="page">
            <g:if test="${tradeListTotal>0}">
                <div class="elxdwn blue"><a href="#" class="download-txt">下载TXT</a></div>
                <div class="elxdwn blue"><a href="#" class="download-exc">下载CSV</a></div>
            </g:if>
          <g:pageNavi total="${tradeListTotal}"/>
          </div>
    </div>
</form>

<script type="text/javascript">
  $(function() {
    $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
    $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
  });

$(document).ready(function() {
	jQuery.validator.addMethod("money",function(a,b){return this.optional(b)||/^\d+(\.\d{0,2})?$/i.test(a)},"Please enter a valid amount.");
	jQuery.validator.addMethod("ge", function(value, element, param) {
		var target = $(param).unbind(".validate-equalTo").bind("blur.validate-equalTo", function() {
				$(element).valid();
			});
			if(target.val()==""||value=="") return true;
			else return parseFloat(value) >= parseFloat(target.val());
	}, "Please enter a valid value.");
	jQuery.validator.addMethod("compareDate", function(value, element, param) {
				var startDate = jQuery(param).val();
				if(startDate==""||value=="")
				{
					return true;
				}else{
					var date1 = new Date(Date.parse(startDate.replaceAll("-", "/")));
					var date2 = new Date(Date.parse(value.replaceAll("-", "/")));
					return date1 <= date2;
				}
	}, "Please enter a valid value.");
	$("#searchform").validate({
		rules: {
			amountMin:{money:true,range:[0.01,100000000]},
			amountMax:{money:true,range:[0.01,100000000],ge:"#amountMin"},
			startDate:{dateISO:true},
			endDate:{dateISO:true,compareDate:"#startDate"}
		},
		messages: {
			amountMin:{money:"无效金额",range:"无效金额范围"},
			amountMax:{money:"无效金额",range:"无效金额范围",ge:"无效金额范围"},
			startDate:{dateISO:"无效时间格式"},
			endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期"}
		}
	});
});

//----------下载部分处理-------
E.on(D.query(".download-exc"),"click",function(e){
	download("csv");
	E.preventDefault(e);
});
E.on(D.query(".download-txt"),"click",function(e){
	download("txt");
	E.preventDefault(e);
});
function download(type){
	var f = D.get("searchform");
	f.action=f.action+"?format="+type;
	f.submit();
	f.action = "agentdeducthis";
	f.method = "post";
};

</script>
</body>
</html>
