package ismp

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import account.AcAccount
import boss.BoBankDic
import boss.BoAcquirerAccount
import boss.BoMerchant
import java.text.SimpleDateFormat

class ChargeController extends BaseController {

    def dataSource_ismp

    def index = {
        def cmCustomer=CmCustomer.get(session.cmCustomer.id)
        if(cmCustomer.status=='init'){
            writeInfoPage "用户状态错误，不能做充值操作，请联系吉高支付客服。",'warn'
            return
        }
        if(cmCustomer.status in ['disabled','deleted']){
            writeInfoPage "用户状态错误，不能做充值操作，请联系吉高支付客服。",'warn'
            session.cmCustomer=null
            return
        }

        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        def acAccount_Frozen = AcAccount.findByParentIdAndAccountType(acAccount_Main?.id, 'freeze')

        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)
        def channelSql = """select t.id,
                                t.acquire_code,
                                t.acquire_indexc,
                                t.bankid,
                                t.channel_type
                     from gwchannel t
                    where t.acquire_indexc not like '%-%'
                     and t.bank_type = '1'
                      and t.channel_sts = '0'"""
        def channelList = dbIsmp.rows(channelSql)

        [acAccount_Main: acAccount_Main, acAccount_Frozen: acAccount_Frozen, channelList: channelList]
    }
    def confirm = {
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        def acAccount_Frozen = AcAccount.findByParentIdAndAccountType(acAccount_Main?.id, 'freeze')

        // 渠道 直连B2C、银联B2C、直连B2B、银联B2B
        def channelMap = new HashMap<String, String>()
        // 更新0927关于加入B2B充值
        def payChannel = params.bankname.toLowerCase()
        def bankDic = BoBankDic.findByCode(payChannel)
        log.info 'normal BoBankDic is ' + bankDic
        if (bankDic) {
            def accountList = BoAcquirerAccount.findAllByBankAndStatus(bankDic, 'normal')
            log.info 'normal BoAcquirerAccount is ' + accountList
            if (accountList) {
                def list = BoMerchant.findAllByChannelStsAndAcquirerAccountInList('0', accountList)
                log.info 'normal Merchant is ' + list
                if (list) {
                    for (BoMerchant bm: list) {
                        String key = bm.channelType
                        if (bm.bankType == '1' && !channelMap.get(key)) {
                            log.info 'add Normal Channel ' + bm.acquireIndexc
                            channelMap.put(key, bm.acquireIndexc)
                        }
                    }
                }
            }
        }

        def unionDic = BoBankDic.findByCode('unionpay')
        log.info 'unp BoBankDic is ' + unionDic
        if (unionDic) {
            def accountList = BoAcquirerAccount.findAllByBank(unionDic, 'normal')
            log.info 'unp BoAcquirerAccount is ' + accountList
            if (accountList) {

                //UNP-PSBC
                def unpChannel = ('UNP-' + payChannel).toUpperCase()
                log.info 'unp B2C channel ' + unpChannel
                //UNP-PSBC_B2B
                def unpB2BChannel = ('UNP-' + payChannel + '_B2B').toUpperCase()
                log.info 'unp B2B channel ' + unpB2BChannel

                def unpList = BoMerchant.createCriteria().list {
                    eq('channelSts', '0')
                    or {
                        eq('acquireIndexc', unpChannel)
                        eq('acquireIndexc', unpB2BChannel)
                    }
                    //eq('acquireIndexc',unpChannel)
                    'in'('acquirerAccount', accountList)
                }
                if (unpList) {
                    for (BoMerchant bm: unpList) {
                        String key = bm.channelType
                        if (bm.bankType == '1' && !channelMap.get(key)) {
                            log.info 'add UNP Channel ' + bm.acquireIndexc
                            channelMap.put(key, bm.acquireIndexc)
                        }
                    }
                }
/*
                //UNP-PSBC_B2B
                def unpB2BChannel = ('UNP-'+ payChannel + '_B2B').toUpperCase()
                log.info 'unp B2B channel ' + unpB2BChannel
                def unpB2BList = BoMerchant.createCriteria().list {
                   eq('channelSts','0')
                   eq('acquireIndexc',unpB2BChannel)
                   'in'('acquirerAccount',accountList)
                }
                if(unpB2BList){
                   for(BoMerchant bm : unpList){
                       String key = bm.channelType
                       if(!channelMap.get(key)){
                           channelMap.put(key, bm.acquireIndexc)
                       }
                   }
                }*/
            }
        }

        log.info 'Map size is ' + channelMap.size()
        if (!channelMap) {
            writeInfoPage "没有银行渠道"
            return
        }
        [acAccount_Main: acAccount_Main, acAccount_Frozen: acAccount_Frozen, channelMap: channelMap, "bankname": params.bankname]
    }

    def reconfirm = {

    }

    def mul(def v1){
        BigDecimal b1 = new BigDecimal(v1);
        return  b1.multiply(Long.valueOf(100)).longValue()
    }

    def addBd(def v1,def v2){
        BigDecimal bignum1 = new BigDecimal(v1);
        BigDecimal bignum2 = new BigDecimal(v2);
        return bignum1.add(bignum2).longValue()
    }

    def create = {
        def cmCustomer=CmCustomer.get(session.cmCustomer.id)
        if(cmCustomer.status=='init'){
            writeInfoPage "用户状态错误，不能做充值操作，请联系吉高支付客服。",'warn'
            return
        }
        if(cmCustomer.status in ['disabled','deleted']){
            writeInfoPage "用户状态错误，不能做充值操作，请联系吉高支付客服。",'warn'
            session.cmCustomer=null
            return
        }
        /*if(!withForm{true}.invalidToken {false}){
           writeInfoPage  "请勿重复提交数据"
           return;
        }*/
        params.service = "create_charge";
        params.currency = "CNY";
        params._input_charset = grailsApplication.config.grails.views.gsp.encoding;
        println 'this is test----------------- '
        def preference
        if(params.preference) {
           preference= params.preference.replaceFirst('UNP-', '')
        }
        def maps
        if (params.payCusNo) {
            maps = ["service": params.service,
                    "currency": params.currency,
                    "charset": params._input_charset,
                    "amount": params.amount,
                    "buyer_name": params.buyer_name,
                    "buyer_id": params.buyer_id,
                    "preference": preference,
                    "create_date":  new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),
                    "pay_cus_no": params.payCusNo
            ];
        } else {
            maps = ["service": params.service,
                    "currency": params.currency,
                    "charset": params._input_charset,
                    "amount": params.amount,
                    "buyer_name": params.buyer_name,
                    "buyer_id": params.buyer_id,
                    "preference": preference,
                    "create_date":  new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
            ];
        }
        def msg = "";
        if (!params.amount) {
            msg = "金额不能空";
        } else {
            if (!(params.amount ==~ /^(\d{0,8}+)(\.\d{1,2})?$/)) {
                msg = "无效金额格式"
            } else {
                def xamount = (params.amount as double)
                if (xamount < 0.01) {
                    msg = "金额不能小于0.01元"
                }
            }
        }

        CmCorporationInfo cmCorporationInfo = CmCorporationInfo.get(cmCustomer?.id)
        if(cmCorporationInfo && mul(params.amount).compareTo(mul(cmCorporationInfo?.chargeSingleLimit))>0){
            msg = "充值金额大于充值单笔限额["+cmCorporationInfo?.chargeSingleLimit+"]！"
            writeInfoPage msg
            return
        }
        def maxAccount = 20000000
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        BigDecimal balanceMain =  new BigDecimal(Long.valueOf(acAccount_Main?.balance))
        balanceMain = addBd(balanceMain,mul(params.amount))
        BigDecimal maxBalance = mul(maxAccount)
        if(balanceMain.compareTo(maxBalance)>0){
            msg = "账户可用余额大于["+maxAccount+"]，请尽快提现！"
            writeInfoPage msg
            return
        }

        if (!msg) {
            log.info 'ok le '
            def sign = FormFunction.createMD5Sign(maps, ConfigurationHolder.config.cashier.MD5_KEY, params._input_charset)
            println maps;
            maps.sign = sign;
            println url: grailsApplication.config.charge.targetUrl + "?" + FormFunction.params2string(maps)
            redirect(url: grailsApplication.config.charge.targetUrl + "?" + FormFunction.params2string(maps));
        } else {

            def acAccount_Frozen = AcAccount.findByParentIdAndAccountType(acAccount_Main.id, 'freeze')
            // 更新0927关于加入B2B充值
            def payChannel = params.bankname.toLowerCase()
            def bankDic = BoBankDic.findByCode(payChannel)
            def list
            if (bankDic) {
                def accountList = BoAcquirerAccount.findAllByBankAndStatus(bankDic, 'normal')
                list = BoMerchant.findAllByChannelStsAndAcquirerAccountInList('0', accountList)
            } else {
                def unionDic = BoBankDic.findByCode('unionpay')
                if (!unionDic) {
                    writeInfoPage "没有银行渠道"
                    return
                }
                def accountList = BoAcquirerAccount.findAllByBank(unionDic, 'normal')
                if (!accountList) {
                    writeInfoPage "没有银行渠道"
                    return
                }
                //UNP-PSBC
                def unpChannel = ('UNP-' + payChannel).toUpperCase()
                list = BoMerchant.createCriteria().list {
                    eq('channelSts', '0')
                    eq('acquireIndexc', unpChannel)
                    'in'('acquirerAccount', accountList)
                }
                log.info 'UNP BoMerchant list is ' + list
            }
            if (!list) {
                writeInfoPage "没有银行渠道"
                return
            }
            def channelMap = new HashMap<String, String>()
            list.each {
                String key = it.channelType
                if (!channelMap.get(key)) {
                    channelMap.put(key, it.acquireIndexc)
                }
            }
            //params.pay_channel=params.preference
            flash.message = msg;
            render(view: "confirm", model: [acAccount_Main: acAccount_Main, acAccount_Frozen: acAccount_Frozen, channelMap: channelMap, "bankname": params.bankname]);
        }


    }

    def list = {
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query = {
            eq('payeeId', session.cmCustomer.id)
            eq('tradeType', 'charge')

            if (!params.startDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                Calendar calendar = Calendar.getInstance()
                params.endDate = sdf.format(calendar.getTime())
                calendar.add(Calendar.MONTH, -3)
                params.startDate = sdf.format(calendar.getTime())
            }

            String startDate = String.valueOf(params.startDate)
            int year = Integer.valueOf(startDate.substring(0, 4))
            int month = Integer.valueOf(startDate.substring(5, 7)) - 1
            int day = Integer.valueOf(startDate.substring(8, 10))
            Calendar calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            ge('dateCreated', calendar.getTime())

            String endDate = String.valueOf(params.endDate)
            year = Integer.valueOf(endDate.substring(0, 4))
            month = Integer.valueOf(endDate.substring(5, 7)) - 1
            day = Integer.valueOf(endDate.substring(8, 10))
            calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            le('dateCreated', calendar.getTime())
//            if (params.startDate) {
//                ge('dateCreated', Date.parse("yyyy-MM-dd", params.startDate))
//                if (!params.endDate) {
//                    le('dateCreated',Date.parse("yyyy-MM-dd", params.startDate).updated(month:Date.parse("yyyy-MM-dd", params.startDate).month+3)+1)
//                }
//            }
//            if (params.endDate) {
//                if (!params.startDate) {
//                    ge('dateCreated',Date.parse("yyyy-MM-dd", params.endDate).updated(month:Date.parse("yyyy-MM-dd", params.endDate).month-3));
//                }
//                le('dateCreated', Date.parse("yyyy-MM-dd", params.endDate) + 1)
//            }
        }
        def list = TradeBase.createCriteria().list(params, query)
        def count = TradeBase.createCriteria().count(query)
        [tradeList: list, tradeListTotal: count]
    }
}