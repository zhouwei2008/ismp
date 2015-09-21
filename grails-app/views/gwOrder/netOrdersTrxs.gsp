<%@ page import=" ismp.TradeRefund; ismp.TradePayment; ismp.TradeBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="layout" content="main" />
		<title>吉高-订单管理-网上支付-付款历史</title>
		<link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'jygl.css')}" media="all" />
		<script charset="utf-8" src="${resource(dir:'js',file:'jquery-ui-1.8.7.custom.min.js')}"></script>
		<script charset="utf-8" src="${resource(dir:'js',file:'application.js')}"></script>
        <style type="text/css">
		.btn-input{
				background:url("${resource(dir:'images',file:'grxxanniu.gif')}") no-repeat transparent;
				border:0 none;
				cursor:pointer;
				width:71px;height:27px;
				color:#fff;
				vertical-align:middle;
				text-align:center;
				float:left;
		}
		.btn-input:hover {	background:url("${resource(dir:'images',file:'grxxanniu1.gif')}") no-repeat transparent;}
		</style>
</head>
<body>
<div class="cwmx_content">
                <div class="w936">
                        <div id="tb_" class="tb_">
							<ul style="padding-left:10px;">
									<li id="tb_1" class="hovertab">付款历史</li>
							</ul>
                        </div>
                        <div class="ctt">
                          <div class="dis" id="tbc_01" >
                            <table width="100%" class="right_list_table" id="test">
                              <tr>
                                <th scope="col">订单流水</th>
                                <th scope="col">创建时间</th>
                                <th scope="col">交易号</th>
                                %{--<th scope="col">交易类型</th>--}%
                                <th scope="col">交易通道</th>
                                %{--<th scope="col">划款类型</th>--}%
                                %{--<th scope="col">付款方式</th>--}%
                                <th scope="col">交易金额(元)</th>
                                <th scope="col">币种</th>
                                <th scope="col">收单银行</th>
                                %{--<th scope="col">收单商户号</th>--}%
                                %{--<th scope="col">付款账户</th>--}%
                                <th scope="col">交易状态</th>
                              </tr>
							  <g:each in="${trxsList}" status="i" var="trxs">
                              <tr>
                                <td>${trxs.gworders_id}</td>
                                <td>${trxs.createdate.format("yyyy-MM-dd HH:mm:ss")}</td>
                                <td>${trxs.trxnum}</td>
                                %{--<td>${trxs.trxtype}</td>--}%
                                <td>${gateway.GwTrxs.channelMap[trxs.channel]}</td>
                                %{--<td>${gateway.GwTrxs.paymentTypeMap[trxs.payment_type]}</td>--}%
                                %{--<td>${gateway.GwTrxs.payModeMap[trxs.paymode]}</td>--}%
                                <td style="text-align: right;"><strong class="hsfong"><g:formatNumber currencyCode="CNY" number="${trxs.amount/100}" type="currency"/></strong></td>
                                <td>${trxs.currency}</td>
                                <td>${trxs.acquirer_name}</td>
                                %{--<td>${trxs.acquirer_merchant}</td>--}%
                                %{--<td>${trxs.fromacctnum}</td>--}%
                                <td>${gateway.GwTrxs.trxStsMap[trxs.trxsts]}</td>
                                <td></td>
                              </tr>
							  </g:each>
                            </table>
                              <table align="center">
                                  <tr>
                                      <td>&nbsp;</td>
                                  </tr>
                                  <tr>
                                      <td><input type="button" class="btn-input" value="返回" onclick="javascript:history.back()"/></td>
                                  </tr>
                              </table>

                  </div>
                 </div>
                </div>

      </div>
</body>
</html>
