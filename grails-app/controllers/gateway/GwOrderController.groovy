package gateway

import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import groovy.sql.Sql
import jxl.Workbook
import jxl.write.*

class GwOrderController {

    def dataSource_ismp

    /*
    *  网上支付
    * */
    def netOrdersList = {

        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)

        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def listStart = params.offset
        def listTo = listStart + params.max
        def condition = ""

        if ((params.orderId != null)&&(!params.orderId.equals(""))) {
            condition = condition + " and t.id = '" + params.orderId + "'"
        }
        if ((params.status != null)&&(!params.status.equals(""))) {
            condition = condition + " and t.ordersts = '" + params.status + "'"
        }
        if ((params.orderType != null)&&(!params.orderType.equals(""))) {
            condition = condition + " and t.order_type = '" + params.orderType + "'"
        }
        if ((params.buyer != null)&&(!params.buyer.equals(""))) {
            condition = condition + " and t.buyer_name like '%" + params.buyer + "%'"
        }
        if ((params.subject != null)&&(!params.subject.equals(""))) {
            condition = condition + " and t.subject like '%" + params.subject + "%'"
        }
        if ((params.orderNum != null)&&(!params.orderNum.equals(""))) {
            condition = condition + " and t.ordernum = '" + params.orderNum + "'"
        }
        if ((params.startDate != null)&&(!params.startDate.equals(""))) {
            condition = condition + " and to_char(t.createdate, 'yyyy-mm-dd') >= '" + params.startDate + "'"
        }
        if ((params.endDate != null)&&(!params.endDate.equals(""))) {
            condition = condition + " and to_char(t.createdate, 'yyyy-mm-dd') <= '" + params.endDate + "'"
        }
        if ((params.amountMin != null)&&(!params.amountMin.equals(""))) {
            condition = condition + " and t.amount >= " + params.amountMin + "*100"
        }
        if ((params.amountMax != null)&&(!params.amountMax.equals(""))) {
            condition = condition + " and t.amount <= " + params.amountMax + "*100"
        }

        def ordersSql = """select t.id,
                               t.service,
                               t.ordernum,
                               t.partnerid,
                               t.seller_name,
                               t.seller_id,
                               t.buyer_name as buyer_name_hidden,
                               t.buyer_id,
                               t.price,
                               t.quantity,
                               t.sign_type,
                               t.discount,
                               t.discount_mode,
                               t.discountdesc,
                               t.currency,
                               t.orderdate,
                               t.createdate,
                               t.query_key,
                               t.exp_dates,
                               t.ips,
                               t.ordersts,
                               t.seller_remarks,
                               t.buyer_remarks,
                               t.return_url,
                               t.notify_url,
                               t.subject,
                               t.bodys,
                               t.royalty_type,
                               t.royalty_parameters,
                               decode(t.amount, null, 0, t.amount) as amount,
                               t.gwlgoptions_id,
                               t.pricechanged,
                               t.apiversion,
                               t.locale,
                               t.preference,
                               decode(t.refund_amount, null, 0, t.refund_amount) as refund_amount_hidden,
                               t.refund_sts,
                               t.order_type,
                               t.version,
                               t.gwl_update,
                               t.charsets,
                               t.paymethod,
                               t.service_fee,
                               t.agentid,
                               t.closedate,
                               t.partner_id,
                               t.show_url,
                               t.directpayamt,
                               t.buyer_realname,
                               t.buyer_contact,
                               g1.id as id_g1,
                               g2.id as id_g2,
                               x.trxnum,
                               x.acquirer_name,
                               x.payment_type,
                               c.name as buyer_name,
                               decode(t1.refund_amount, null, decode(t2.refund_amount, null, 0, t2.refund_amount), t1.refund_amount) as refund_amount
                          from gworders t
                     left join cm_customer c on c.customer_no = t.buyer_id
                     left join gwpayments g1 on t.id = g1.paynum and g1.paysts = 1
                     left join gwpayments g2 on g1.id = g2.prid
                     left join gwtrxs x on g2.paynum = x.id
                     left join (select sum(decode(p.refund_amount, null, 0, p.refund_amount)) as refund_amount,
                                       s.gwordersid
                                  from trade_base t
                            inner join trade_payment p on t.id = p.id
                            inner join gwsuborders s on s.id = t.trade_no
                              group by gwordersid ) t1 on t1.gwordersid = t.id
                    left join (select decode(p.refund_amount, null, 0, p.refund_amount) as refund_amount,
                                      t.trade_no
                                 from trade_base t
                           inner join trade_payment p on t.id = p.id) t2 on t2.trade_no = t.id
                         where t.seller_id = '""" + session.cmCustomer.customerNo + """' """+ condition + """
                         order by t.createdate desc"""

        if (params?.format && params.format in ["txt", "csv", "excel"]) {

            def ordersList = dbIsmp.rows(ordersSql)
            def count = dbIsmp.rows("select count(*) as count from (" + ordersSql + ")")[0][0]

            def filename = 'Excel-' + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + '.xls'
            response.setHeader("Content-disposition", "attachment; filename=" + filename)
            response.setCharacterEncoding("GBK")
            response.contentType = "application/x-rarx-rar-compressed"

            render(template: "tpl_${params.format}_netOrdersList", model: [ordersList: ordersList, count: count])
        } else {

            def listSql = "select t.* from (select t.*, rownum rn from (" + ordersSql + ") t) t where t.rn > " + listStart + " and t.rn <= " + listTo
            def ordersList = dbIsmp.rows(listSql)
            def count = dbIsmp.rows("select count(*) as count from (" + ordersSql + ")")[0][0]

            [ordersList: ordersList, count: count]
        }
    }

    /*
    *  网上支付 支付流程
    * */
    def netOrdersTrxs = {

        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)

        def trxsSql = """select t.id,
                            t.gworders_id,
                            t.trxnum,
                            t.trxtype,
                            t.channel,
                            t.payment_type,
                            t.paymode,
                            decode(t.amount, null, 0, t.amount) as amount,
                            t.currency,
                            t.servicecode,
                            t.acquirer_code,
                            t.acquirer_name,
                            t.acquirer_merchant,
                            t.acquirer_seq,
                            t.acquirer_date,
                            t.acquirer_msg,
                            t.submitdates,
                            t.payer_ip,
                            t.refnum,
                            t.authcode,
                            t.fromacctid,
                            t.fromacctnum,
                            t.buyer_id,
                            t.buyer_name,
                            t.payinfo,
                            t.createdate,
                            t.closedate,
                            t.trxsts,
                            t.opers,
                            t.operdate,
                            t.version,
                            t.trxdesc
                       from gwtrxs t
                       where t.gworders_id = '""" + params.id + """'
                       order by t.createdate asc"""

        def trxsList = dbIsmp.rows(trxsSql)
        [trxsList: trxsList]
    }

    /*
    *  网上支付 合单支付子订单
    * */
    def netOrdersSubList = {

        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)

        def subOrdersSql = """select t.id,
                                t.gwordersid as gworders_id,
                                t.outtradeno,
                                t.seller_name,
                                t.seller_custno,
                                t.seller_code,
                                t.seller_ext,
                                decode(t.amount, null, 0, t.amount) as amount,
                                tp.refund_amount,
                                t.createdate
                           from gwsuborders t
                     inner join ( select t.trade_no,
                                         decode(p.refund_amount, null, 0, p.refund_amount) as refund_amount
                                    from trade_base t
                              inner join trade_payment p on t.id = p.id
                           ) tp on tp.trade_no = t.id
                           where t.gwordersid = '""" + params.id + """'
                           order by t.createdate asc"""

        def subOrdersList = dbIsmp.rows(subOrdersSql)
        [subOrdersList: subOrdersList]
    }
}
