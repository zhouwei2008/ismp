<%@ page import="dsf.TbAgentpayDetailsInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-批次明细</title>
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

<g:form action="boxlist" method="post" name="searchform">
    <g:hiddenField name="format"/>
    <g:hiddenField name="id" value="${batch.id}"/>
</g:form>
    <!--内容区开始-->
    <div class="InContent">
        <div class="searchForm">
            <h1>批次明细</h1>
            <div class="searchContent">
                <ul class="formContent clearFloat">
                    <li>
                        <label class="txtRight">商户名称:</label>${session.cmCustomer.name}
                    </li>
                    <li>
                        <label class="txtRight">上传文件名:</label>${batch.batchFilename}
                    </li>
                    <li>
                        <label class="txtRight">业务类型:</label><g:if test="${batch.batchType=='S'}">代收业务</g:if><g:if test="${batch.batchType=='F'}">代付业务</g:if>
                    </li>
                    <li>
                        <label class="txtRight">总笔数:</label>${batch.batchCount}笔
                    </li>
                    <li>
                        <label class="txtRight">总金额:<g:formatNumber currencyCode="CNY" number="${batch.batchAmount}" type="currency"/>
                    </li>

                    <li class="formBtnArea">
                        <button class="formBtn" onclick="mysubmit('${batch.id}')">确认提交</button>
                        <button class="formBtn" onclick="history.go(-1)">返回</button>
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
                        <td>序号</td>
                        <td>开户名</td>
                        <td>客户账号</td>
                        <td>公/私</td>
                        <td class="money">金额（元）</td>
                        <td>币种</td>
                        <td>手机号</td>
                        <td>证件类型</td>
                        <td>证件号</td>
                        <td>备注</td>
                        <td>检验结果</td>
                        %{--<td class="btnArea">操作</td>--}%
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tradeList}" status="i" var="trade">
                        <tr>
                            <td>${trade.tradeNum}</td>
                            <td>${trade.tradeCardname}</td>
                            <td>${trade.tradeAccountname}<br>${trade.tradeBranchbank},${trade.tradeSubbranchbank}<br>${trade.tradeCardnum}<br>${trade.tradeProvince},${trade.tradeCity}</td>
                            <td>${TbAgentpayDetailsInfo.accountTypeMap[trade.tradeAccounttype]}</td>
                            <td><strong style="color:red"><g:formatNumber currencyCode="CNY" number="${trade.tradeAmount}" type="currency"/></strong></td>
                            <td>${trade.tradeAmounttype}</td>
                            <td>${trade.tradeMobile}</td>
                            <td>${TbAgentpayDetailsInfo.certificateTypeMap[trade.certificateType]}</td>
                            <td>${trade.certificateNum}</td>
                            <g:if test="${batch.batchType=='S'}">
                                <td>${trade.contractUsercode}</td>
                            </g:if>
                            <td>${trade.tradeRemark}</td>
                            <td>${trade.errMsg}</td>
                            %{--<td>--}%
                                %{--<a href="#" onclick="myconedit('${trade.id}')" class="gridBtn_normal">编辑</a>--}%
                                %{--|--}%
                                %{--<a href="#" onclick="myconfirm('${trade.id}')" class="gridBtn_normal">删除</a>--}%
                            %{--</td>--}%
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="tableFoot clearFloat">
                    <g:if test="${tradeListTotal>0}">
                        <a href="#" class="downCount" id="download-txt">下载TXT</a>
                        <a href="#" class="downCount" id="download-exc">下载CSV</a>
                        <a href="#" class="downCount" id="download-exc">下载Excel</a>
                    </g:if>
                    <g:pageNavi total="${tradeListTotal}"/>
                </div>
            </div>
        </div>
        <!--搜索结果结束-->
    </div>
    <!--内容区结束-->


<script type="text/javascript">

    //----------下载部分处理-------
    E.on(D.get("download-exc"),"click",function(e){
        download("csv");
        E.preventDefault(e);
    });
    E.on(D.get("download-txt"),"click",function(e){
        download("txt");
        E.preventDefault(e);
    });
    E.on(D.get("download-excel"),"click",function(e){
        download("xls");
        E.preventDefault(e);
    });
    function download(type){
        var f = D.get("searchform");
        $("#format").val(type)
        f.submit();
        $("#format").val("")
    };
    function myconfirm(id){
        if(confirm("您确认执行删除操作吗？")){
            window.document.location.href = "${createLink(controller:'tbAgentPayBox',action:'agentinfodel')}?tradeid="+id;
        }
    }
    function myconedit(id){

        window.document.location.href = "${createLink(controller:'tbAgentPayBox',action:'boxupdate')}?tradeid="+id;
    }
    function mysubmit(id){
        if(confirm("您确认执行提交操作吗？")){
            window.document.location.href = "${createLink(controller:'tbAgentPayBox',action:'boxsubmit')}?id="+id;
        }
    }

</script>
</body>
</html>
