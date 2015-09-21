package settle

import groovy.sql.Sql
import org.hibernate.Session
import org.hibernate.transform.AliasToEntityMapResultTransformer
import org.springframework.orm.hibernate3.HibernateCallback
import org.springframework.orm.hibernate3.HibernateTemplate
import ismp.CmCustomer
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import org.apache.commons.lang.StringUtils
import java.text.SimpleDateFormat

class FtFootController {
    def dataSource_settle

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0

        def errmsg = ""

        def cmCustomer = session.cmCustomer
        if(!cmCustomer){
            errmsg = "无法获取商户信息"
            log.info errmsg
            render(view: "login", model: [errmsg: errmsg])
            return
        }

        def customerNo = cmCustomer.customerNo
        def startDate = params.startDate
        def endDate = params.endDate

        def ft_cycle = FtSrvFootSetting.findWhere([customerNo:customerNo,srv:FtSrvType.findBySrvCode('online'),tradeType:FtSrvTradeType.findByTradeCode('payment')])
        log.info("#ft_cycle#${ft_cycle}")

        def result = []
        def sum = [PAY_AMOUNT:null, REF_AMOUNT:null, PAY_FEE:null]
        def count = [total:0]

        if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
            Calendar calendar = Calendar.getInstance()
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            params.endDate = sdf.format(calendar.getTime())
            calendar.add(Calendar.DATE, -7)
            params.startDate = sdf.format(calendar.getTime())
            startDate = params.startDate
            endDate = params.endDate
        }

        def sql = new Sql(dataSource_settle)
        def queryParam = []

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

        if(startDate){
            startDate = startDate + " 00:00:00"
        }

        if(endDate){
            endDate = endDate + " 23:59:59"
        }

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

        count = sql.firstRow("select count(*) total from (${query})",queryParam)
        HibernateTemplate ht = DatasourcesUtils.newHibernateTemplate('settle')
        result = ht.executeFind({ Session session ->
            def sqlQuery = session.createSQLQuery(query.toString())
            sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
            for (def i = 0; i < queryParam.size(); i++) {
                sqlQuery.setParameter(i, queryParam.get(i))
            }

            return sqlQuery.setFirstResult(params.offset).setMaxResults(params.max).list();
        } as HibernateCallback)

        log.info("#result#${result}")

        // 合计
        sum = sql.firstRow("select sum(su.PAY_AMOUNT) PAY_AMOUNT,sum(su.REF_AMOUNT) REF_AMOUNT ,sum(su.PAY_FEE) PAY_FEE from (${query}) su",queryParam)
        log.info("#sum#${sum}")

        /*
        def liqQuery = """
                        select li.FOOT_NO,li.LIQUIDATE_NO
                        from FT_LIQUIDATE li
                        left join (select fo.FOOT_NO
                                   from FT_FOOT fo
                                   ${whe_common}
                                  ) f
                        on li.FOOT_NO = f.FOOT_NO
                        where f.FOOT_NO is not null
                    """
        log.info("#liqQuery#${liqQuery}")

        def liqNoList = sql.rows(liqQuery.toString())
        log.info("#liqNoList#${liqNoList}")
        */

        [result: result, total: count.total, ft_cycle:ft_cycle ? FtSrvFootSetting.tradeTypeMap[ft_cycle?.footType?.toString()] : '', sum:sum]
    }

    def detail = {
        params.sort = params.sort ? params.sort : "billDate"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0

        def pay_no = params.pay_no
        def ref_no = params.ref_no

        def sql = new Sql(dataSource_settle)
        def queryParam = []

        def query = """
                    select tr.SEQ_NO,tr.BILL_DATE,tr.TRADE_CODE,tr.AMOUNT,tr.PRE_FEE,tr.POST_FEE
                    from FT_TRADE tr
                    left join (select li.LIQUIDATE_NO
                               from FT_LIQUIDATE li
                               where li.FOOT_NO='${pay_no}'
                              ) l
                    on tr.LIQUIDATE_NO = l.LIQUIDATE_NO
                    where l.LIQUIDATE_NO is not null

                    union all

                    select tr.SEQ_NO,tr.BILL_DATE,tr.TRADE_CODE,tr.AMOUNT,tr.PRE_FEE,tr.POST_FEE
                    from FT_TRADE tr
                    left join (select li.LIQUIDATE_NO
                               from FT_LIQUIDATE li
                               where li.FOOT_NO='${ref_no}'
                              ) l
                    on tr.LIQUIDATE_NO = l.LIQUIDATE_NO
                    where l.LIQUIDATE_NO is not null
                  """

        def ttl = sql.firstRow("select count(*) total from (${query})",queryParam)
        HibernateTemplate ht = DatasourcesUtils.newHibernateTemplate('settle')
        def rst = ht.executeFind({ Session session ->
                def sqlQuery = session.createSQLQuery(query.toString())
                sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                for (def i = 0; i < queryParam.size(); i++) {
                    sqlQuery.setParameter(i, queryParam.get(i))
                }

                return sqlQuery.setFirstResult(params.offset).setMaxResults(params.max).list();
            } as HibernateCallback)

        log.info("#rst#${rst}#ttl#${ttl}#params.pay_no#${params.pay_no}#params.ref_no#${params.ref_no}")
        [result: rst, total: ttl.total, pay_no:params.pay_no,ref_no:params.ref_no,a1:params.a1,a2:params.a2,b:params.b,c:params.c,d:params.d,e:params.e,f:params.f,g:params.g,h:params.h]
    }

    /*def create = {
        def ftFootInstance = new FtFoot()
        ftFootInstance.properties = params
        return [ftFootInstance: ftFootInstance]
    }

    def save = {
        def ftFootInstance = new FtFoot(params)
        if (ftFootInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), ftFootInstance.id])}"
            redirect(action: "show", id: ftFootInstance.id)
        }
        else {
            render(view: "create", model: [ftFootInstance: ftFootInstance])
        }
    }

    def show = {
        def ftFootInstance = FtFoot.get(params.id)
        if (!ftFootInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), params.id])}"
            redirect(action: "list")
        }
        else {
            [ftFootInstance: ftFootInstance]
        }
    }

    def edit = {
        def ftFootInstance = FtFoot.get(params.id)
        if (!ftFootInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [ftFootInstance: ftFootInstance]
        }
    }

    def update = {
        def ftFootInstance = FtFoot.get(params.id)
        if (ftFootInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (ftFootInstance.version > version) {
                    
                    ftFootInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'ftFoot.label', default: 'FtFoot')] as Object[], "Another user has updated this FtFoot while you were editing")
                    render(view: "edit", model: [ftFootInstance: ftFootInstance])
                    return
                }
            }
            ftFootInstance.properties = params
            if (!ftFootInstance.hasErrors() && ftFootInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), ftFootInstance.id])}"
                redirect(action: "show", id: ftFootInstance.id)
            }
            else {
                render(view: "edit", model: [ftFootInstance: ftFootInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def ftFootInstance = FtFoot.get(params.id)
        if (ftFootInstance) {
            try {
                ftFootInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ftFoot.label', default: 'FtFoot'), params.id])}"
            redirect(action: "list")
        }
    }*/
}
