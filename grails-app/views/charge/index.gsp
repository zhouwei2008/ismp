<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="main" />
    <title>吉高-充值</title>
    <link charset="utf-8" rel="stylesheet" href="${resource(dir: 'css/flick', file: 'jquery-ui-1.8.7.custom.css')}" media="all"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'arale.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'pa.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'Paging.js')}"></script>
    <g:javascript library="jquery"/>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'jquery-ui-1.8.7.custom.min.js')}"></script>
    <script charset="utf-8" src="${resource(dir: 'js', file: 'application.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}" type="text/javascript"></script>

    <script language="javascript">
        function  setBankName(names){
            document.getElementById('bankname').value = names.replace('_B2B', '');
            document.getElementById('channel').value = names;
            if (names.indexOf('_') > -1) {
                document.getElementById('B2B-' + names.replace('_B2B', '')).checked = true;
            } else {
                document.getElementById('B2C-' + names).checked = true;
            }
        }
    </script>
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
              <li class="rtncnt blue">充值</li>
              <g:if test="${session.level3Map != null && session.level3Map['charge/list'] != null}">
                <li>
                    <a href="${request.contextPath}/${session.level3Map['charge/list'].modelPath}">充值记录</a>
                </li>
              </g:if>
            </ul>
        </div>
    </div>
    <form action="confirm" method="post" name="actionForm" id="actionForm">
        <div class="txbox">
            <input type="hidden" id="bankname" name="bankname" value="ICBC"/>
            <input type="hidden" id="channel" name="channel" value="ICBC_B2B"/>

            <ul class="rolinList" id="rolin">
                <ol>
                    <h2>企业网银</h2>
                    <div class="contents">
                        <div id="divobj1" class="SelectBank clearfix">

                            <li>
                                <input type="radio" name="pay_channel" id="B2B-ICBC" value="ICBC-icbc1025" checked="" onclick="setBankName(&quot;ICBC_B2B&quot;)"/>
                                <label for="B2B-ICBC">
                                    <a href="#"><img src="../images/bank/ICBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;ICBC_B2B&quot;)"/></a>
                                </label>
                            </li>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'CCB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-CCB" value="CCB-ccb102" onclick="setBankName(&quot;CCB_B2B&quot;)"/>
                                        <label for="B2B-CCB">
                                            <a href="#"><img src="../images/bank/CCB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CCB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'ABC_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-ABC" value="ABC-abc101" onclick="setBankName(&quot;ABC_B2B&quot;)"/>
                                        <label for="B2B-ABC">
                                            <a href="#"><img src="../images/bank/ABC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;ABC_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'BOC_B2B'}">
                                 <li>
                                    <input type="radio" name="pay_channel" id="B2B-BOC" value="BOC" onclick="setBankName(&quot;BOC_B2B&quot;)"/>
                                    <label for="B2B-BOC">
                                        <a href="#"><img src="../images/bank/BOC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;BOC_B2B&quot;)"/></a>
                                    </label>
                                </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'CMB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-CMB" value="CMB-cmb308" onclick="setBankName(&quot;CMB_B2B&quot;)"/>
                                        <label for="B2B-CMB">
                                            <a href="#"><img  src="../images/bank/CMB_OUT.gif"width="100" height="20" border="0" onclick="setBankName(&quot;CMB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'BOCM_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-BOCM" value="BOCM" onclick="setBankName(&quot;BOCM_B2B&quot;)"/>
                                        <label for="B2B-BOCM">
                                            <a href="#"><img src="../images/bank/BOCM_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;BOCM_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'SPDB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-SPDB" value="SPDB" onclick="setBankName(&quot;SPDB_B2B&quot;)"/>
                                        <label for="B2B-SPDB">
                                            <a href="#"><img src="../images/bank/SPDB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SPDB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'GDB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-GDB" value="GDB-gdb101" onclick="setBankName(&quot;GDB_B2B&quot;)"/>
                                        <label for="B2B-GDB">
                                            <a href="#"><img src="../images/bank/GDB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;GDB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'CITIC_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-CITIC" value="CITIC" onclick="setBankName(&quot;CITIC_B2B&quot;)"/>
                                        <label for="B2B-CITIC">
                                            <a href="#"><img src="../images/bank/CITIC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CITIC_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'CEB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-CEB" value="CEB" onclick="setBankName(&quot;CEB_B2B&quot;)"/>
                                        <label for="B2B-CEB">
                                            <a href="#"><img src="../images/bank/CEB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CEB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'CIB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-CIB" value="CIB-cib101" onclick="setBankName(&quot;CIB_B2B&quot;)"/>
                                        <label for="B2B-CIB">
                                            <a href="#"><img src="../images/bank/CIB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CIB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'SDB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-SDB" value="SDB-sdb101" onclick="setBankName(&quot;SDB_B2B&quot;)"/>
                                        <label for="B2B-SDB">
                                            <a href="#"><img src="../images/bank/SDB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SDB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'CMBC_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-CMBC" value="CMBC-cmbc101" onclick="setBankName(&quot;CMBC_B2B&quot;)"/>
                                        <label for="B2B-CMBC">
                                            <a href="#"><img src="../images/bank/CMBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;CMBC_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'HXB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-HXB" value="HXB" onclick="setBankName(&quot;HXB_B2B&quot;)"/>
                                        <label for="B2B-HXB">
                                            <a href="#"><img src="../images/bank/HXB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;HXB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'SPA_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-SPA" value="SPA-spa101" onclick="setBankName(&quot;SPA_B2B&quot;)"/>
                                        <label for="B2B-SPA">
                                            <a href="#"><img src="../images/bank/SPA_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SPA_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'PSBC_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-PSBC" value="PSBC-psbc102" onclick="setBankName(&quot;PSBC_B2B&quot;)"/>
                                        <label for="B2B-PSBC">
                                            <a href="#"><img src="../images/bank/PSBC_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;PSBC_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'BHBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-BHBK" value="BHBK-hscb101" onclick="setBankName(&quot;BHBK_B2B&quot;)"/>
                                        <label for="B2B-BHBK">
                                            <a href="#"><img src="../images/bank/BHBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;BHBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'BEA_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-BEA" value="BEA-hscb101" onclick="setBankName(&quot;BEA_B2B&quot;)"/>
                                        <label for="B2B-BEA">
                                            <a href="#"><img src="../images/bank/BEA_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;BEA_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'NBBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-NBBK" value="NBBK-nbbk101" onclick="setBankName(&quot;NBBK_B2B&quot;)"/>
                                        <label for="B2B-NBBK">
                                            <a href="#"><img src="../images/bank/NBBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;NBBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'HSBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-HSBK" value="HSBK-hscb101" onclick="setBankName(&quot;HSBK_B2B&quot;)"/>
                                        <label for="B2B-HSBK">
                                            <a href="#"><img src="../images/bank/HSBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;HSBK_B2B&quot;)"/></a>
                                        </label>
                                     </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'FDBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-FDBK" value="FDBK-fdbk101" onclick="setBankName(&quot;FDBK_B2B&quot;)"/>
                                        <label for="B2B-FDBK">
                                            <a href="#"><img src="../images/bank/FDBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;FDBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'GZCBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-GZCBK" value="GZCBK-gzcbk101" onclick="setBankName(&quot;GZCBK_B2B&quot;)"/>
                                        <label for="B2B-GZCBK">
                                            <a href="#"><img src="../images/bank/GZCBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;GZCBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'SHRCB_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-SHRCB" value="SHRCB-shrcb101" onclick="setBankName(&quot;SHRCB_B2B&quot;)"/>
                                        <label for="B2B-SHRCB">
                                            <a href="#"><img src="../images/bank/SHRCB_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SHRCB_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'DLCBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-DLCBK" value="DLCBK-dlcbk101" onclick="setBankName(&quot;DLCBK_B2B&quot;)"/>
                                        <label for="B2B-DLCBK">
                                            <a href="#"><img src="../images/bank/DLCBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;DLCBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'DGCBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-DGCBK" value="DGCBK-dgcbk101" onclick="setBankName(&quot;DGCBK_B2B&quot;)"/>
                                        <label for="B2B-DGCBK">
                                            <a href="#"><img src="../images/bank/DGCBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;DGCBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'HBBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-HBBK" value="HBBK-hbbk101" onclick="setBankName(&quot;HBBK_B2B&quot;)"/>
                                        <label for="B2B-HBBK">
                                            <a href="#"><img src="../images/bank/HBBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;HBBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'JSBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-JSBK" value="JSBK-jsbk101" onclick="setBankName(&quot;JSBK_B2B&quot;)"/>
                                        <label for="B2B-JSBK">
                                            <a href="#"><img src="../images/bank/JSBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;JSBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'NXBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-NXBK" value="NXBK-nxbk101" onclick="setBankName(&quot;NXBK_B2B&quot;)"/>
                                        <label for="B2B-NXBK">
                                            <a href="#"><img src="../images/bank/NXBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;NXBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'QLBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-QLBK" value="JSBK-jsbk101" onclick="setBankName(&quot;QLBK_B2B&quot;)"/>
                                        <label for="B2B-QLBK">
                                            <a href="#"><img src="../images/bank/QLBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;QLBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'XMCBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-XMCBK" value="XMCBK-xmcbk101" onclick="setBankName(&quot;XMCBK_B2B&quot;)"/>
                                        <label for="B2B-XMCBK">
                                            <a href="#"><img src="../images/bank/XMCBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;XMCBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'SZCBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-SZCBK" value="SZCBK-szcbk101" onclick="setBankName(&quot;SZCBK_B2B&quot;)"/>
                                        <label for="B2B-SZCBK">
                                            <a href="#"><img src="../images/bank/SZCBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;SZCBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                            <g:each in="${channelList}" status="i" var="channelInfo">
                                <g:if test="${channelInfo.acquire_indexc == 'WZMBK_B2B'}">
                                    <li>
                                        <input type="radio" name="pay_channel" id="B2B-WZMBK" value="WZMBK-wzmbk101" onclick="setBankName(&quot;WZMBK_B2B&quot;)"/>
                                        <label for="B2B-WZMBK">
                                            <a href="#"><img src="../images/bank/WZMBK_OUT.gif" width="100" height="20" border="0" onclick="setBankName(&quot;WZMBK_B2B&quot;)"/></a>
                                        </label>
                                    </li>
                                </g:if>
                            </g:each>

                        </div>
                    </div>
                </ol>
            </ul>


            <div class="xybbtn">
                <input type="submit" class="btn mglf10" value="下一步" />
            </div>
        </div>
    </form>
</div>

<script>
    $(document).ready(function() {
        jQuery.validator.addMethod("money",function(a,b){return this.optional(b)||/^\d+(\.\d{0,2})?$/i.test(a)},"Please enter a valid amount.");
        $("#actionForm").validate({
            rules: {
                amount:{required:true,money:true,min:0.01,max:${acAccount_Main==null?0:acAccount_Main.balance/100}},
                captcha:{required:true,minlength:4}
            },
            messages: {
                amount:{required:"请输入提现金额",money:"无效金额",min:'金额值必须大于{0}',max:'金额值必须小于{0}'},
                captcha:{required:"请输入验证码",minlength:"验证码位数不对"}
            }
        });
    });

     E.on('reload-checkcode','click',function(){
		reloadAuthImg(D.get('authCodeImg'));
	});
    E.on('authCodeImg','click',function(){
		reloadAuthImg(D.get('authCodeImg'));
	});

	function reloadAuthImg(img) {
		var url = img.src.split('?')[0];
		var param = img.src.toQueryParams();
		param.r = new Date().getTime();

		var params = [];
		for(var i in param){
			params.push(i+'='+param[i]);
		}
		img.src = url + '?' + params.join('&');
	}
</script>
</body>
</html>
