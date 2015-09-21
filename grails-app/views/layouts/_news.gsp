<%@ page import="boss.BoNews" %>
<%@ page import="trade.AccountCommandPackage;"%>
<%
    def query = {
        eq('sysId', 'RONGPAY')
        eq('msgColumn', '吉高')
    }
    def query1 = {
        eq('sysId', 'RONGPAY')
        eq('msgColumn', '商户接入')
    }
    def query2 = {
        eq('sysId', 'RONGPAY')
        eq('msgColumn', '生活服务')
    }

    params.offset = 0
    params.max = 10
    params.sort = params.sort ? params.sort : "dateCreated"
    params.order = params.order ? params.order : "desc"
    def boNews = BoNews.createCriteria().list(params, query)
    def newsCount = boNews.size()
    def boNews1 = BoNews.createCriteria().list(params, query1)
    def boNews2 = BoNews.createCriteria().list(params, query2)
  %>
<g:if test="${newsCount > 6}">
    <div id="news" style="overflow:hidden;height:160px;">
        <ul id="news1" class="list12">
            <g:each in="${boNews}" var="x" status="i">
                <g:if test="${i==0}">
                    <li>&nbsp;</li>
                </g:if>
                <g:if test="${i<10 && x.msgColumn=='吉高'}">
                    <li>${x.dateCreated.format("yyyy-MM-dd")}&nbsp;&nbsp;<a title="${x.content}">${AccountCommandPackage.getContentStr(x.content)}</a></li>
                </g:if>
            </g:each>
        </ul>
        <ul id="news2" class="list12"></ul>
    </div>
</g:if>
<g:else>
    <ul class="list12" style="height:160px;">
        <g:each in="${boNews}" var="x" status="i">
            <g:if test="${i<10 && x.msgColumn=='吉高'}">
                <li>${x.dateCreated.format("yyyy-MM-dd")}&nbsp;&nbsp;<a title="${x.content}">${AccountCommandPackage.getContentStr(x.content)}</a></li>
            </g:if>
        </g:each>
    </ul>
</g:else>