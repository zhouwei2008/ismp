<%@ page import="ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-结算查询</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" media="all" />
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script charset="utf-8" src="${resource(dir: 'js/My97DatePicker', file: 'WdatePicker.js')}"></script>
    <script type="text/javascript">
        function search(obj) {
            if (obj) {
                if ($("offset"))
                    $("offset").value = 0;
            }
            $("#searchform").submit();
        }

        function validatetime(){
            var s = $('#startDate').val();
            var e = $('#endDate').val();
            if(''!= s && '' != e && s > e){
                alert('日期输入颠倒，请重新输入')
                return false;
            }else{
                $("#searchform").submit();
                return true;
            }
        }

        function clearTime() {
            $('#startDate').val('');
            $('#endDate').val('');
            $('#startDate').focus();
        }
    </script>
</head>
<body>
<!--内容区开始-->
<form action="list" method="post" name="searchform" id="searchform">
<div class="InContent">
    <div class="searchForm">
        <h1>结算查询</h1>
        <div class="searchContent">
            <ul class="formContent clearFloat">
                <li>
                    <label class="txtRight">开始日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="startDate" name="startDate" value="${params.startDate}"  class="formDate"/>
                </li>
                <li>
                    <label class="txtRight">结束日期:</label><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="endDate" name="endDate" value="${params.endDate}"  class="formDate"/>
                </li>
                <li>
                    <label class="txtRight">结算周期:</label><input disabled="disabled" type="text" class="" value="${ft_cycle}"  />
                </li>
                <li>
                    <label class="txtRight">订单金额:</label><span style="color:red;">${(sum.PAY_AMOUNT == 0 || sum.PAY_AMOUNT) ? Math.abs(sum.PAY_AMOUNT).toBigDecimal().divide(100,2,0).toPlainString() : '0.00'}</span>
                </li>
                <li>
                    <label class="txtRight">退款金额:</label><span style="color:red;">${(sum.REF_AMOUNT == 0 || sum.REF_AMOUNT) ? Math.abs(sum.REF_AMOUNT).toBigDecimal().divide(100,2,0).toPlainString() : '0.00'}</span>
                </li>
                <li>
                    <label class="txtRight">订单净额:</label><span style="color:red;">${((sum.PAY_AMOUNT == 0 || sum.PAY_AMOUNT) && (sum.REF_AMOUNT == 0 || sum.REF_AMOUNT)) ? (Math.abs(sum.PAY_AMOUNT) - Math.abs(sum.REF_AMOUNT)).toBigDecimal().divide(100,2,0).toPlainString() : '0.00'}</span>
                </li>
                <li>
                    <label class="txtRight">退扣手续费金额:</label><span style="color:red;">${(0 == sum.PAY_FEE || sum.PAY_FEE) ? sum.PAY_FEE.toBigDecimal().divide(100,2,0).toPlainString() : '0.00'}</span>
                </li>
                <li>
                    <label class="txtRight">结算金额:</label><span style="color:red;">${((sum.PAY_AMOUNT == 0 || sum.PAY_AMOUNT) && (sum.REF_AMOUNT == 0 || sum.REF_AMOUNT) && (0 == sum.PAY_FEE || sum.PAY_FEE)) ? (Math.abs(sum.PAY_AMOUNT) - Math.abs(sum.REF_AMOUNT) - sum.PAY_FEE).toBigDecimal().divide(100,2,0).toPlainString() : '0.00'}</span>
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
                    <td>订单支付日期范围</td>
                    <td>结算日期</td>
                    <td class="shortNum">订单笔数</td>
                    <td class="money">订单金额(元)</td>
                    <td class="money">退款金额(元)</td>
                    <td class="money">订单净额(元)</td>
                    <td>手续费金额(元)</td>
                    <td class="money">结算金额(元)</td>
                    <td class="btnArea">结算明细</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${result}" status="i" var="foot">
                    <tr>
                        <td><g:formatDate format="yyyy.MM.dd" date="${foot.MIN_TRADE_DATE}"/>-<g:formatDate format="yyyy.MM.dd" date="${foot.MAX_TRADE_DATE}"/></td>
                        <td>${foot.FOOT_DATE.format("yyyy-MM-dd")}</td>
                        <td>${foot.TRANS_NUM}</td>
                        <td>${Math.abs(foot.PAY_AMOUNT).toBigDecimal().divide(100,2,0).toPlainString()}</td>
                        <td>${Math.abs(foot.REF_AMOUNT).toBigDecimal().divide(100,2,0).toPlainString()}</td>
                        <td>${(Math.abs(foot.PAY_AMOUNT) - Math.abs(foot.REF_AMOUNT)).toBigDecimal().divide(100,2,0).toPlainString()}</td>
                        <td><strong style="color:green">${foot.PAY_FEE.toBigDecimal().divide(100,2,0).toPlainString()}</strong></td>
                        <td><strong style="color:red">${(Math.abs(foot.PAY_AMOUNT) - Math.abs(foot.REF_AMOUNT) - foot.PAY_FEE).toBigDecimal().divide(100,2,0).toPlainString()}</strong></td>
                        <td>
                            <g:link controller="ftFoot" action="detail" class="gridBtn_normal" params="[pay_no:foot.PAY_NO,ref_no:foot.REF_NO,a1:foot.MIN_TRADE_DATE.format('yyyy-MM-dd'),a2:foot.MAX_TRADE_DATE.format('yyyy-MM-dd'),b:foot.FOOT_DATE.format('yyyy-MM-dd'),c:foot.TRANS_NUM,d:Math.abs(foot.PAY_AMOUNT).toBigDecimal().divide(100,2,0).toPlainString(),e:Math.abs(foot.REF_AMOUNT).toBigDecimal().divide(100,2,0).toPlainString(),f:(Math.abs(foot.PAY_AMOUNT) - Math.abs(foot.REF_AMOUNT)).toBigDecimal().divide(100,2,0).toPlainString(),g:foot.PAY_FEE.toBigDecimal().divide(100,2,0).toPlainString(),h:(Math.abs(foot.PAY_AMOUNT) - Math.abs(foot.REF_AMOUNT) - foot.PAY_FEE).toBigDecimal().divide(100,2,0).toPlainString()]">结算明细</g:link>
                        </td>
                    </tr>
                </g:each>


                </tbody>
            </table>
            <div class="tableFoot clearFloat">
                <div class="pagination">
                    <g:pageNavi total="${total}"/>
                </div>
            </div>
        </div>
    </div>
    <!--搜索结果结束-->
</div>
<!--内容区结束-->
</form>

<script type="text/javascript">

</script>

</body>
</html>
