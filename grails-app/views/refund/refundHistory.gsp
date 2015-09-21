<%@ page import="ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批量退款上传历史</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'jygl.css')}" media="all"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
    <script type="text/javascript">
        function search(obj) {
            if (obj) {
                if ($("offset"))
                    $("offset").value = 0;
            }
            $("#searchform").submit();
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
    <div class="cwmx_mxsm">
        <span class="left">
            <strong><a style="font-size:13px;" href="${createLink(controller: 'trade', action: 'batchRefund')}">批量退款</a>&nbsp;|&nbsp;</strong>
        </span>
        <span class="left">
            <strong>批量退款上传历史</strong>
        </span>
    </div>
        <form action="refundHistory" method="post" name="searchform" id="searchform">
          <div class="mcjy_serchtj">
            <table align="center" class="cwmx_list_table">
                <tr>
                    <td>
                       退款批次号：<input name="batchNo" type="text" class="i-text i-text-s" id="batchNo" value="${params.batchNo}" maxlength="80"/>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td>
                        开始日期：<g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                        结束日期：<g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                    </td>
                    <td><input type="submit" class="btn-input" value="搜索"></td>
                </tr>
            </table>
          </div>
    <div class="w936">
        <div id="tb_" class="tb_">
            <ul style="padding-left:10px;">
                <li id="tb_1" class="hovertab">批量退款历史</li>
            </ul>
        </div>
        <div class="ctt">
            <div class="dis" id="tbc_01">
                <table width="100%" class="right_list_table" id="test">
                    <tr>
                        <th scope="col">退款批次号</th>
                        <th scope="col">退款操作时间</th>
                        <th scope="col">正确退款笔数</th>
                        <th scope="col" align="right">退款总金额</th>
                        <th scope="col">错误退款笔数</th>
                        <th scope="col" align="right">错误总金额</th>
                        <th scope="col">错误记录下载</th>
                    </tr>
                    <g:each in="${refundHisList}" status="i" var="refundHis">
                        <tr>
                            <td>${refundHis.batchNo}</td>
                            <td>${refundHis.batchDate.format("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${refundHis.succItems}</td>
                            <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${refundHis.succAmt}" type="currency"/></strong></td>
                            <td>${refundHis.failItems}</td>
                            <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${refundHis.failAmt}" type="currency"/></strong></td>
                            %{--<td>
                               <g:if test="${session.level3Map != null && session.level3Map['refund/export'] != null}">
                                    <a href="${request.contextPath}/${session.level3Map['refund/export'].modelPath}/${refundHis.id}">下载</a>
                               </g:if>
                            </td>--}%
                            <td>
                                <g:if test="${refundHis.exportPath}">
                                    <a href="${createLink(controller: 'refund', action: 'export')}/${refundHis.id}">下载</a>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                </table>
                <g:pageNavi total="${refundHisTotal}"/>
                %{--<g:if test="${tradeListTotal>0}">
                    <div>
                        --}%%{--<span><a href="#" title="TXT格式" class="download-txt">&nbsp;&nbsp;&nbsp;&nbsp;下载TXT</a></span>--}%%{--
                        --}%%{--<span><a href="#" title="Excel格式" class="download-exc">&nbsp;&nbsp;&nbsp;&nbsp;下载CSV</a></span>--}%%{--
                        <span><a href="#" title="Excel格式" class="download-excel">&nbsp;&nbsp;下载Excel</a></span>
                    </div>
                </g:if>--}%
            </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
        $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
    });

    $(document).ready(function() {

        jQuery.validator.addMethod("compareDate", function(value, element, param) {
            var startDate = jQuery(param).val();
            if (startDate == "" || value == "") {
                return true;
            } else {
                var date1 = new Date(Date.parse(startDate.replaceAll("-", "/")));
                var date2 = new Date(Date.parse(value.replaceAll("-", "/")));
                return date1 <= date2;
            }
        }, "Please enter a valid value.");
        $("#searchform").validate({
            rules: {
                startDate:{dateISO:true},
                endDate:{dateISO:true,compareDate:"#startDate"}
            },
            messages: {
                startDate:{dateISO:"无效时间格式"},
                endDate:{dateISO:"无效时间格式",compareDate:"结束日期不能小于开始日期"}
            }
        });
    });

    //----------下载部分处理-------
    E.on(D.query(".download-exc"), "click", function(e) {
        download("csv");
        E.preventDefault(e);
    });
    E.on(D.query(".download-txt"), "click", function(e) {
        download("txt");
        E.preventDefault(e);
    });
    E.on(D.query(".download-excel"), "click", function(e) {
        download("excel");
        E.preventDefault(e);
    });
    function download(type) {
        var f = D.get("searchform");
        f.action = f.action + "?format=" + type;
        f.submit();
        f.action = "buy";
        f.method = "post";
    }
    ;

</script>
</body>
</html>
