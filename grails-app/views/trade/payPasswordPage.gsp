<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批量退款批次确认</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'sjfw.css')}" media="all" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'jygl.css')}" media="all"/>

     <script type="text/javascript">

         function trefuse() {
            window.location.href="${createLink(controller:'trade',action:'sale')}";
          }
          function confirmRefund(){

            document.forms[0].action="confirmRefund"
                document.forms[0].submit();
          }
            function download(){
                document.forms[0].action="uploadFail"
                document.forms[0].submit();
            }
            function ret(){

               document.forms[0].action="sale"
                document.forms[0].submit();
            }


     </script>
    <style>
    .btn-input {
        background: url("${resource(dir:'images',file:'grxxanniu.gif')}") no-repeat transparent;
        border: 0 none;
        cursor: pointer;
        width: 71px;
        height: 27px;
        color: #fff;
        vertical-align: middle;
        text-align: center;
        float: left;
    }

    .btn-input:hover {
        background: url("${resource(dir:'images',file:'grxxanniu1.gif')}") no-repeat transparent;
    }
    </style>
</head>
<body>

<div class="cwmx_content">

    <g:form action="confirm" method="post" name="searchform" useToken="true">
        <g:hiddenField name="ids" id="ids" value="${refundAuth.id}" />
        <g:hiddenField name="batch" id="batch" value="${refundBatch.BatchNo}" />
        <div class="w936">
  <div id="tb_" class="tb_">
      <ul style="padding-left:10px;">
              <li id="tb_1" class="hovertab">上传文件内容</li>
      </ul>
  </div>
  <div class="ctt">
    <div class="dis" id="tbc_01" >
        <div class="xtgl_content">
            <table width="100%" class="right_list_tablebx" id="dataTbl">
                <tr class="c1">
                    <td colspan="5" scope="col" >
                        <div class="xtgl_h1">
                            <b>退款批次信息</b>
                        </div>
                    </td>
                </tr>
		        <tr class="c2"><td width="17%" class="right_fnt">批次号：</td><td width="83%" class="left_fnt" colspan="4">${refundBatch.BatchNo}</td></tr>

                <tr class="c2">
                    <td width="17%" class="right_fnt">正确笔数：</td> <td>${sitems} </td>
                    <td width="17%" class="right_fnt">正确总金额：</td><td width="27%"><g:formatNumber number="${stotalMoney}" format="########################.##" /></td><td align="center"><a href="#" onclick="disdata();" >显示正确数据记录</a></td>
                </tr>
                <tr class="c2">
                    <td width="17%" class="right_fnt">错误笔数：</td><td>${fitems}</td>
                    <td width="17%" class="right_fnt">错误总金额：</td><td width="27%"><g:formatNumber number="${ftotalMoney}" format="########################.##" /></td>
                    <td align="center"><g:link action="uploadFail" id="${refundBatch.BatchNo}" >错误数据下载</g:link></td>
                </tr>

        </table>
        </div>
        <div class=" xtgl_content" id="data" name="data" style="display:none">
      <table width="100%" class="right_list_table" id="test">
       <tr><th colspan="4"><a href="#" onclick=" document.getElementById('data').style.display='none'" >关闭</a></th></tr>
        <tr>
          <th scope="col">商户订单号</th>
          <th scope="col">平台交易号</th>
          <th scope="col" style="text-align: right;">退款金额</th>
          <th scope="col">退款备注</th>
        </tr>
        <g:each in="${refundAuth}" status="i" var="ra">
            <tr>
                <td>${ra.outTradeNo}</td>
                <td>${ra.tradeNo}</td>
                <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${ra.amount/100}" type="currency"/></strong></td>
                <td>${ra.note}</td>
            </tr>
        </g:each>
        </table>
        </div>

         <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

            <table align="center">
                <tr>

                    <td align="left">
                        请输入支付密码:<input type="password" name="pw">
                    </td>
                    <td>
                        <g:hiddenField name="sitems" id="sitems" value="${sitems}" />
                        <g:hiddenField name="stotalMoney" id="stotalMoney" value="${stotalMoney}" />
                        <g:hiddenField name="fitems" id="fitems" value="${fitems}" />
                        <g:hiddenField name="ftotalMoney" id="sitems" value="${ftotalMoney}" />
                        <input type="button" id="qrtk" class="anniu_2" value="确认退款" onclick="confirmRefund();">
                        <input type="button" id="re" class="anniu_2" value="返回" onclick="ret();">
                    </td>
                </tr>
            </table>

    </g:form>
</div>
</div>
</div>
</div>
 <script type="text/javascript">


             var  sitems="${sitems}"

                 if(sitems<=0){

                    document.getElementById('confir').style.display="none";
                 }
               function disdata(){
                document.getElementById('data').style.display="block";
             }
</script>
</body>
</html>
