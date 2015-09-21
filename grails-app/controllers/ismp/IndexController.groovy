package ismp

import account.AcAccount
import boss.BoNews
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import groovy.sql.Sql
import ismp.TradeBase
import org.hibernate.Session
import org.hibernate.transform.AliasToEntityMapResultTransformer
import org.springframework.orm.hibernate3.HibernateCallback
import org.springframework.orm.hibernate3.HibernateTemplate

import java.text.SimpleDateFormat
import boss.BoCustomerService

class IndexController {

    def index = {redirect(action: account)}
    def dataSource_settle
    def account={
        //def accountNo=CmCustomerAccountMapping.findByCustomerAndAccountType(session.cmCustomer,'main')?.accountNo
        def accountNo=session.cmCustomer.accountNo
        def boCustomer = BoCustomerService.findWhere(customerId: session.cmCustomer.id, serviceCode: 'online', isCurrent: true, enable: true)
        //println  "accountNo="+accountNo
        def acAccount_Main=AcAccount.findByAccountNo(accountNo)
        def acAccount_Frozen=AcAccount.findByParentIdAndAccountType(acAccount_Main?.id,'freeze')
        def acAccount_srv=AcAccount.findByAccountNo(boCustomer?.srvAccNo)


        def PWDmsg = ""
        def lpwdT = session.cmCustomerOperator.lastPWChangeTime
        def now = new Date();
        if(lpwdT){//1323158635196 //1323160938874
            long days = (now.time - lpwdT.time)/1000/60/60/24
            if(days > 90)
                PWDmsg = "您的登陆密码已经超过90天未修改!请及时更改登陆密码!"
            else if(days > 80)
                PWDmsg = "您的登陆密码已经 "+ days + " 天未修改!请及时更改登陆密码!"
//            else if(days > 80)
//                PWDmsg = "您的登陆密码即将过期，请及时更改登陆密码!"
//            else
//                PWDmsg = "您的登陆密码还有 "+ (90 - days) + " 天过期!请及时更改登陆密码!"
        }

        def queryTrade = {
//            eq('dateCreated', Date.parse("yyyy-MM-dd", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date())))
            or {
                eq('payeeId', session.cmCustomer.id)
                eq('payerId', session.cmCustomer.id)
            }
        }
        params.offset = 0
        params.max = 5
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        def tradeList = TradeBase.createCriteria().list(params, queryTrade)

        def cmCustomer = session.cmCustomer
        def customerNo = cmCustomer.customerNo

        def result = []
        def sql = new Sql(dataSource_settle)
        def queryParam = []

        Calendar calendar = Calendar.getInstance()
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def date = sdf.format(calendar.getTime())
        def startDate = date + " 00:00:00"
        def endDate = date + " 23:59:59"


        // 审核状态为1，手续费类型为净额, 服务类型online，交易类型payment和refund
        def sel_common = "select fo.FOOT_NO,max(fo.FOOT_DATE) FOOT_DATE,max(fo.TRANS_NUM) TRANS_NUM,min(ta.MIN_TRADE_DATE) MIN_TRADE_DATE,max(ta.MAX_TRADE_DATE) MAX_TRADE_DATE"
        def mid_common = """
                        from FT_FOOT fo
                        left join FT_LIQUIDATE li
                        on fo.FOOT_NO = li.FOOT_NO
                        left join (select tr.LIQUIDATE_NO,min(tr.BILL_DATE) MIN_TRADE_DATE,max(tr.BILL_DATE) MAX_TRADE_DATE
                                   from FT_TRADE tr
                                   group by tr.LIQUIDATE_NO
                                  ) ta
                        on li.LIQUIDATE_NO = ta.LIQUIDATE_NO
                      """

        def whe_common = """
                        where fo.CHECK_STATUS=1 and fo.FEE_TYPE=0 and fo.CUSTOMER_NO='${customerNo}'
                              ${startDate ? "and fo.FOOT_DATE >= to_date('" + startDate + "','yyyy-mm-dd hh24:mi:ss')" : ""}
                              ${endDate ? "and fo.FOOT_DATE <= to_date('" + endDate + "','yyyy-mm-dd hh24:mi:ss')" : ""}
                              and fo.SRV_CODE='online'
                      """
        def query =  """
                    select un.FOOT_DATE,max(un.PAY_NO) PAY_NO,max(un.REF_NO) REF_NO,max(un.TRANS_NUM) TRANS_NUM,max(un.MIN_TRADE_DATE) MIN_TRADE_DATE,max(un.MAX_TRADE_DATE) MAX_TRADE_DATE,
                           sum(un.PAY_AMOUNT) PAY_AMOUNT,sum(un.REF_AMOUNT) REF_AMOUNT,sum(un.PRE_FEE) PAY_FEE
                    from (
                            ${sel_common},fo.FOOT_NO PAY_NO,'0' as REF_NO,max(fo.AMOUNT) PAY_AMOUNT,0 as REF_AMOUNT,max(fo.PRE_FEE) PRE_FEE
                            ${mid_common}
                            ${whe_common}
                            and fo.TRADE_CODE='payment'
                            group by fo.foot_no
                            union all
                            ${sel_common},'0' as PAY_NO,fo.FOOT_NO REF_NO,0 as PAY_AMOUNT,max(fo.AMOUNT) REF_AMOUNT,max(fo.PRE_FEE) PRE_FEE
                            ${mid_common}
                            ${whe_common}
                            and fo.TRADE_CODE='refund'
                            group by fo.foot_no
                         ) un
                    group by un.FOOT_DATE
                    order by un.FOOT_DATE desc
                  """

        log.info("#query#${query}")

        HibernateTemplate ht = DatasourcesUtils.newHibernateTemplate('settle')
        result = ht.executeFind({ Session session ->
            def sqlQuery = session.createSQLQuery(query.toString())
            sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
            for (def i = 0; i < queryParam.size(); i++) {
                sqlQuery.setParameter(i, queryParam.get(i))
            }

            return sqlQuery.setFirstResult(params.offset).setMaxResults(params.max).list();
        } as HibernateCallback)

        [acAccount_Main:acAccount_Main,acAccount_Frozen:acAccount_Frozen,acAccount_srv:acAccount_srv,PWDMsg:PWDmsg,tradeList:tradeList,result:result]
    }
}
