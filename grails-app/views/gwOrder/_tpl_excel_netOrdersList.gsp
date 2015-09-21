<%@ page import="boss.BoCustomerService" %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<style id="template_26326_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font57821
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl733956
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"0_ ";
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl637821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl647821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl657821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl667821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:white;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	background:#0070C0;
	mso-pattern:black none;
	white-space:nowrap;}
.xl677821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl687821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:right;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl697821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:000000;
	text-align:right;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl707821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:right;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl717821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl727821
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"0\.00_ ";
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;}
-->
</style>
</head>

<body>
<!--[if !excel]>　　<![endif]-->
<!--下列信息由 Microsoft Office Excel 的“发布为网页”向导生成。-->
<!--如果同一条目从 Excel 中重新发布，则所有位于 DIV 标记之间的信息均将被替换。-->
<!----------------------------->
<!--“从 EXCEL 发布网页”向导开始-->
<!----------------------------->

<div id="template_26326" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=1524 class=xl637821
 style='border-collapse:collapse;table-layout:fixed;width:1143pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5760;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <col class=xl637821 width=147 style='mso-width-source:userset;mso-width-alt:
 5704;width:110pt'>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl637821 width=202 style='height:13.5pt;width:151pt'>吉高订单查询<span style='mso-spacerun:yes'>&nbsp;</span></td>
  <td class=xl637821 width=80 style='width:60pt'>订单类型：网上支付</td>
  <td class=xl637821 width=96 style='width:130pt'>商户号：[${session.cmCustomer.customerNo}]</td>
  <td class=xl637821 width=173 style='width:72pt'></td>
  <td class=xl637821 colspan=3 width=169 style='width:137pt'>
      <g:if test="${params.startDate != ''}">   起始日期：[${params.startDate}]</g:if>
      <g:if test="${params.endDate != ''}">   终止日期：[${params.endDate}]</g:if>
 </td>
  <td class=xl637821  width=136 style='width:10pt'></td>
  <td class=xl637821 width=136 style='width:10pt'></td>
  <td class=xl637821 width=80 style='width:60pt'></td>
 </tr>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl637821 style='height:13.5pt'></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
  <td class=xl637821></td>
 </tr>
 <tr class=xl637821 height=18 style='height:13.5pt'>
  <td class=xl667821 style='border-left:none'>平台交易号</td>
  <td class=xl667821 style='border-left:none'>创建时间</td>
  <td class=xl667821 style='border-left:none'>支付时间</td>
  <td class=xl667821 style='border-left:none'>商户订单号</td>
  <td class=xl667821 style='border-left:none'>商品名称</td>
  <td class=xl667821 style='border-left:none'>订单说明</td>
  <td class=xl667821 style='border-left:none'>交易对方</td>
  <td class=xl667821 style='border-left:none'>总金额（元）</td>
  <td class=xl667821 style='border-left:none'>退款金额（元）</td>
  <td class=xl667821 style='border-left:none'>状态</td>
  <td class=xl667821 style='border-left:none'>银行交易号</td>
  <td class=xl667821 style='border-left:none'>收单银行</td>
  <td class=xl667821 style='border-left:none'>合作商家客户号</td>
  <td class=xl667821 style='border-left:none'>卖家名称</td>
  <td class=xl667821 style='border-left:none'>卖家(收款)ID</td>
  <td class=xl667821 style='border-left:none'>买家(付款)ID</td>
  <td class=xl667821 style='border-left:none'>商品单价</td>
  <td class=xl667821 style='border-left:none'>商品数量</td>
  <td class=xl667821 style='border-left:none'>折扣</td>
  <td class=xl667821 style='border-left:none'>折扣方式</td>
  <td class=xl667821 style='border-left:none'>折扣说明</td>
  <td class=xl667821 style='border-left:none'>币种</td>
  <td class=xl667821 style='border-left:none'>商家订单日期</td>
  <td class=xl667821 style='border-left:none'>买家实名</td>
  <td class=xl667821 style='border-left:none'>买家联系方式</td>
  <td class=xl667821 style='border-left:none'>卖家(收款)备注</td>
  <td class=xl667821 style='border-left:none'>买家(付款)备注</td>
 </tr>
<g:each in="${ordersList}" status="i" var="orders">
 <tr height=18 style='height:13.5pt'>
  <td class=xl677821 style='border-left:none'>${orders.id}</td>
  <td  class=xl677821 style='border-left:none'>${orders.createdate.format("yyyy-MM-dd HH:mm:ss")}</td>
  <td class=xl677821 style='border-left:none'>${orders.closedate.format("yyyy-MM-dd HH:mm:ss")}</td>
  <td class=xl677821 style='border-left:none'>${orders.ordernum}</td>
  <td class=xl677821 style='border-left:none'>${orders.subject}</td>
  <td class=xl677821 style='border-left:none'>${orders.bodys}</td>
  <td class=xl677821 style='border-left:none'>${orders.buyer_name}</td>
  <td class=xl657821  align=right style='border-left:none'> +<g:formatNumber number="${orders.amount/100}" format="#0.00#"/></td>
  <td class=xl657821  align=right style='border-left:none'> +<g:formatNumber number="${orders.refund_amount/100}" format="#0.00#"/></td>
  <td class=xl677821 style='border-left:none'>${gateway.GwOrder.statusMap[orders.ordersts]}</td>
  <td class=xl677821 style='border-left:none'>${orders.trxnum}</td>
  <td class=xl677821 style='border-left:none'><g:if test="${orders.trxnum == null && orders.ordersts != '0'}">余额支付</g:if><g:else>${orders.acquirer_name}</g:else></td>
  <td class=xl677821 style='border-left:none'>${orders.partnerid}</td>
  <td class=xl677821 style='border-left:none'>${orders.seller_name}</td>
  <td class=xl677821 style='border-left:none'>${orders.seller_id}</td>
  <td class=xl677821 style='border-left:none'>${orders.buyer_id}</td>
  <td class=xl677821 style='border-left:none'><g:formatNumber number="${orders.price/100}" format="#0.00#"/></td>
  <td class=xl677821 style='border-left:none'>${orders.quantity}</td>
  <td class=xl677821 style='border-left:none'>${orders.discount}</td>
  <td class=xl677821 style='border-left:none'>${orders.discount_mode}</td>
  <td class=xl677821 style='border-left:none'>${orders.discountdesc}</td>
  <td class=xl677821 style='border-left:none'>${orders.currency}</td>
  <td class=xl677821 style='border-left:none'>${orders.orderdate}</td>
  <td class=xl677821 style='border-left:none'>${orders.buyer_realname}</td>
  <td class=xl677821 style='border-left:none'>${orders.buyer_contact}</td>
  <td class=xl677821 style='border-left:none'>${orders.seller_remarks}</td>
  <td class=xl677821 style='border-left:none'>${orders.buyer_remarks}</td>
 </tr>
 </g:each>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=95 style='width:91pt'></td>
  <td width=147 style='width:110pt'></td>
  <td width=173 style='width:130pt'></td>
  <td width=75 style='width:56pt'></td>
  <td width=83 style='width:62pt'></td>
  <td width=86 style='width:65pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=96 style='width:72pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
