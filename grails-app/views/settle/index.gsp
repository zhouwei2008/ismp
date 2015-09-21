<!doctype html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main"/>
    <title>吉高-账户查询</title>
	<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'sjfw.css')}" media="all" />
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'cwmx.css')}" media="all"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css', file: 'jygl.css')}" media="all"/>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir:'js',file:'pa.js')}"></script>
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
        vertical-align: top;
        text-align: center;
        float: right;
    }

    .btn-input:hover {
        background: url("${resource(dir:'images',file:'grxxanniu1.gif')}") no-repeat transparent;
    }
    </style>
</head>
<body>

<div class="cwmx_content">
    <div class="cwmx_mxsm">
        <span class="left"><strong>账户查询</strong></span>
        <span class="left" style="margin-left:10px;">
            <a href="index?direction=in"> 收入</a> | <a href="index?direction=out">支出</a>
            <g:if test="${session.level3Map != null && session.level3Map['ftFoot/list'] != null}">|
                <a href="${request.contextPath}/${session.level3Map['ftFoot/list'].modelPath}">结算查询</a>
            </g:if>
        </span>
    </div>
    <form action="index" method="post" name="searchform" id="searchform">
        <div class="mcjy_serchtj01">
            <table align="center" class="cwmx_list_table">
                %{--<tr>--}%
				%{--<td>服务类型&nbsp;&nbsp;&nbsp;：--}%
					%{--<g:select name="serviceType" value="${params.serviceType?:'online'}" from="${boss.BoCustomerService.serviceMap}" optionKey="key" optionValue="value" class="right_top_h2_input"/>--}%
				%{--</td>--}%
                %{--</tr>--}%
                <tr>
                    <td scope="col">资金流向：
                            <input type="radio" name="direction" value="in" id="account-in" <g:if test="${params.direction=='in'}">checked</g:if>/>收入
                            <input type="radio" name="direction" value="out" id="account-out" <g:if test="${params.direction=='out'}">checked</g:if>/>支出
                            <input type="radio" name="direction" value="all" id="account-all" <g:if test="${(params.direction==null||params.direction=='all')&&actionName=='index'}">checked</g:if>/>所有
                    </td>
                    <td rowspan="2" scope="col">
                        <input type="submit" class="btn-input" value="搜索">
                    </td>
                </tr>
                <tr>
                    <td scope="col">条件：
                    开始日期：<g:textField name="startDate" value="${params.startDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                    结束日期：<g:textField name="endDate" value="${params.endDate}" readonly="readonly" size="10" class="right_top_h2_input"/>
                    </td>

                </tr>
                <tr>
                    <td scope="col" colspan="2">类型：
                            <input type="checkbox" name="tradeType" value="typeAll" id="typeAll" <g:if test="${params.tradeType=='typeAll'||params.tradeType==null}">checked</g:if>/>所有
                            %{--<input type="checkbox" name="tradeType" value="charge" id="charge" <g:if test="${'charge' in params.tradeType}">checked</g:if>/>充值--}%
                            %{--<input type="checkbox" name="tradeType" value="withdrawn" id="withdrawn" <g:if test="${'withdrawn' in params.tradeType}">checked</g:if>/>提现--}%
                            <input type="checkbox" name="tradeType" value="payment" id="payment" <g:if test="${'payment' in params.tradeType}">checked</g:if>/>支付
                            <input type="checkbox" name="tradeType" value="fee" id="fee" <g:if test="${'fee' in params.tradeType}">checked</g:if>/>手续费
                            <input type="checkbox" name="tradeType" value="fee_rfd" id="fee_rfd" <g:if test="${'fee_rfd' in params.tradeType}">checked</g:if>/>退手续费
                            <input type="checkbox" name="tradeType" value="transfer" id="transfer" <g:if test="${'transfer' in params.tradeType}">checked</g:if>/>转账
                            %{--<input type="checkbox" name="tradeType" value="royalty" id="royalty" <g:if test="${'royalty' in params.tradeType}">checked</g:if>/>分润--}%
                            %{--<input type="checkbox" name="tradeType" value="royalty_rfd" id="royalty_rfd" <g:if test="${'royalty_rfd' in params.tradeType}">checked</g:if>/>退分润--}%
                            <input type="checkbox" name="tradeType" value="refund" id="refund" <g:if test="${'refund' in params.tradeType}">checked</g:if>/>退款
                            <input type="checkbox" name="tradeType" value="frozen" id="frozen" <g:if test="${'frozen' in params.tradeType}">checked</g:if>/>冻结
                            <input type="checkbox" name="tradeType" value="unfrozen" id="unfrozen" <g:if test="${'unfrozen' in params.tradeType}">checked</g:if>/>解冻结
                            %{--<input type="checkbox" name="tradeType" value="agentpay" id="agentpay" <g:if test="${'agentpay' in params.tradeType}">checked</g:if>/>代付--}%
                            %{--<input type="checkbox" name="tradeType" value="agentcoll" id="agentcoll" <g:if test="${'agentcoll' in params.tradeType}">checked</g:if>/>代收--}%
                            %{--<input type="checkbox" name="tradeType" value="settle" id="settle" <g:if test="${'settle' in params.tradeType}">checked</g:if>/>结算--}%
                    </td>
                </tr>
            </table>
        </div>
            <div class="w936">
                <div id="tb_" class="tb_">
                    <ul style="padding-left:10px;">
                        <li id="tb_1" class="hovertab">结算查询</li>
                    </ul>
                </div>
                <div class="ctt">
                    <div class="dis" id="tbc_01">
                        <table width="100%" class="right_list_table" id="test">
                            <tr>
                                <th scope="col">创建时间</th>
                                <th scope="col">流水号</th>
                                <th scope="col">类型</th>
                                <th scope="col">收入（元）</th>
                                <th scope="col">支出（元）</th>
                                <th scope="col">账户余额（元）</th>
                                <th scope="col">摘要</th>
                                <th scope="col">详细</th>
                            </tr>
                            <g:each in="${acSeqList}" status="i" var="acSeq">
                                <tr>
                                    <td>${acSeq.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</td>
                                    <td>${acSeq.transaction.tradeNo}</td>
                                    <td>${acSeq.transaction.transTypeMap[acSeq.transaction.transferType]}</td>
                                    <td style="text-align: right;"><strong class="lvsfong"><g:if test="${acSeq.debitAmount>0}">+<g:formatNumber currencyCode="CNY" number="${acSeq.debitAmount/100}" type="currency"/></g:if></strong></td>
                                    <td style="text-align: right;"><strong class="hsfong"><g:if test="${acSeq.creditAmount>0}">-<g:formatNumber currencyCode="CNY" number="${acSeq.creditAmount/100}" type="currency"/></g:if></strong></td>
                                    <td style="text-align: right;"><strong><g:formatNumber currencyCode="CNY" number="${acSeq.balance/100}" type="currency"/></strong></td>
                                    <td>${acSeq.transaction.subjict == 'null' ? '' : acSeq.transaction.subjict?.encodeAsHTML()}</td>
                                    <td>
                                        <g:if test="${session.level3Map != null && session.level3Map['settle/accdetail'] != null}">
                                            <a href="${request.contextPath}/${session.level3Map['settle/accdetail'].modelPath}/${acSeq.id}?direction=${params.direction}">查看</a>
                                        </g:if>
                                        %{--<a href="${createLink(controller: 'trade', action: 'accdetail')}/${acSeq.id}">查看</a>--}%
                                    </td>
                                </tr>
                            </g:each>
                        </table>
                        %{--<div style="margin-left:-50px;background-color:'red';color:red">--}%
                               <g:pageNavi total="${acSeqListTotal}"/>
                        %{--</div>--}%

                        <g:if test="${acSeqListTotal>0}">
                            <div>
                                <span><a href="#" title="TXT格式" class="download-txt">&nbsp;&nbsp;&nbsp;&nbsp;下载TXT</a></span>
                                <span><a href="#" title="CSV格式" class="download-exc">&nbsp;&nbsp;&nbsp;&nbsp;下载CSV</a></span>
                                <span><a href="#" title="Excel格式" class="download-excel">&nbsp;&nbsp;下载Excel</a></span>
                            </div>
                        </g:if>
                    </div>
                </div>
            </div>
    </form>
</div>
<script type="text/javascript">
    $(function() {
        $("#startDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
        $("#endDate").datepicker({ dateFormat: 'yy-mm-dd', changeYear: true, changeMonth: true,minDate: -30, maxDate: "+0D" });
    });

    $(document).ready(function() {
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
    			startDate:{dateISO:true},
    			endDate:{dateISO:true,compareDate:"#startDate"}
    		},
    		messages: {
    			startDate:{dateISO:"无效时间格式"},
    			endDate:{dateISO:"无效时间格式",compareDate:"结束日期必须大于开始日期"}
    		}
    	});

        /*
    	* 不可选与可选
    	* disabl
    	* Id：    DOMID
    	* check： 1."checked"为选中 2."unchecked"为不选中
    	* disabl： 1."disabl"不可操作 2."undisabl"可操作
    	*/
    	function disabl(Id, check, disabl) {
    		if (check == "checked") {
    			D.get(Id).checked = true;
    		} else if (check == "unchecked") {
    			D.get(Id).checked = false;
    		}
    		if (disabl == "disabl") {
    			D.get(Id).disabled = true;
    			D.get(Id).readOnly = true;
    		} else if (disabl == "undisabl") {
    			D.get(Id).disabled = false;
    			D.get(Id).readOnly = false;
    		}
    	}

        function checke() {
//            if (D.get("account-in").checked) {
//                disabl("charge", "null", "undisabl");
//                disabl("withdrawn", "unchecked", "disabl");
//            }
//            if (D.get("account-out").checked) {
//                disabl("charge", "unchecked", "disabl");
//                disabl("withdrawn", "null", "undisabl");
//            }
//            if (D.get("account-all").checked) {
//                disabl("charge", "null", "undisabl");
//                disabl("withdrawn", "null", "undisabl");
//            }
        }

        E.on(D.get("account-in"), "click", function(e) {
            checke();
        });

        E.on(D.get("account-out"), "click", function(e) {
            checke();
        });

        E.on(D.get("account-all"), "click", function(e) {
            checke();
        });
        checke();

        E.on(D.get("typeAll"), "click", function(e) {
            if (D.get("typeAll").checked) {
//                disabl("charge", "unchecked", "null");//充值
//                disabl("withdrawn", "unchecked", "null");//提现
                disabl("transfer", "unchecked", "null");//转账
                disabl("payment", "unchecked", "null");//支付
                disabl("fee", "unchecked", "null");//手续费
                disabl("fee_rfd", "unchecked", "null");//退手续费
//                disabl("royalty", "unchecked", "null");//分润
//                disabl("royalty_rfd", "unchecked", "null");//退分润
                disabl("refund", "unchecked", "null");//退款
                disabl("frozen", "unchecked", "null");//冻结
                disabl("unfrozen", "unchecked", "null");//解冻
//                disabl("agentpay", "unchecked", "null");//代付
//                disabl("agentcoll", "unchecked", "null");//代收
            }
            checke();
        });
        E.on([D.get("payment"), D.get("charge"), D.get("transfer"), D.get("withdrawn"), D.get("fee"), D.get("refund"), D.get("refund_rfd"), D.get("fee_rfd"), D.get("frozen"), D.get("unfrozen"), D.get("royalty"),D.get("royalty_rfd"),D.get("agentpay"),D.get("agentcoll")], "click", function(e) {
            disabl("typeAll", "unchecked", "null");//所有
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
        E.on(D.query(".download-excel"),"click",function(e){
    		download("excel");
    		E.preventDefault(e);
    	});
    	function download(type){
    		var f = D.get("searchform");
    		f.action=f.action+"?format="+type;
    		f.submit();
    		f.action = "index";
    		f.method = "post";
    	};

    });
</script>

</body>
</html>
