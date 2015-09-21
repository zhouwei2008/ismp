package ismp

import java.text.SimpleDateFormat

class OutAndInController {

      def dataSource_ismp


      def index = {redirect(action: "list")}



    def list = {

        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def listStart = params.offset
        def listTo = listStart + params.max

        def condition = ""

        if ((params.ordernum != null)&&(!params.ordernum.equals(""))) {
            condition = condition + " and a.ordernum = '" + params.ordernum + "'"
        }
         if ((params.tradeno != null)&&(!params.tradeno.equals(""))) {
            condition = condition + " and a.tradeno = '" + params.tradeno + "'"
        }
         if ((params.contractno != null)&&(!params.contractno.equals(""))) {
            condition = condition + " and a.contractno = '" + params.contractno + "'"
        }
        if ((params.name != null)&&(!params.name.equals(""))) {
            condition = condition + " and a.name like  '%" + params.name + "%'"
        }
         if ((params.name != null)&&(!params.name.equals(""))) {
            condition = condition + " and a.name like  '%" + params.name + "%'"
        }
         if ((params.tradetype != null)&&(!params.tradetype.equals(""))) {
            condition = condition + " and a.tradetype = '" + params.tradetype + "'"
        }

         if (!params.startDate) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00")
                        Calendar calendar = Calendar.getInstance()
                        params.startDate = sdf.format(calendar.getTime())
                        calendar.add(Calendar.DATE, 1)
                        params.endDate = sdf.format(calendar.getTime())
                    }
        def channelSql = """
                         select a.tradetime,a.ordernum,a.amount,a.balance,a.no,a.tradetype,a.name,a.contractno
                         from (  select t.closedate tradetime,
                                        t.ordernum ordernum,
                                         '' contractno,
                                         t.amount amount,
                                         t.discountdesc balance,
                                         t.ext_param1 no,
                                         b.member_name name,
                                         'in' tradetype
                                   from  gworders t ,business_member b
                                  where   t.ordersts=3
                                    and   t.partnerid ='"""+session.cmCustomer.customerNo+"""'
                                    and   t.ext_param1 = b.member_id(+)
                              union
                                  select t.batch_chkdate tradetime,
                                       t.batch_filename ordernum,
                                       ''contractno,
                                      t.batch_amount*100 amount,
                                       to_char(to_number(t.batch_remark3)*100) balance,
                                       t.batch_remark2 no,
                                       b.member_name name,
                                       'out' tradetype
                                   from temp_dsf.tb_agentpay_info t ,business_member b
                                   where  t.batch_type = 'F'
                                    and  (t.batch_status = '3' or t.batch_status = '4')
                                    and   t.batch_bizid ='"""+session.cmCustomer.customerNo+"""'
                                    and   t.batch_remark2 = b.member_id(+)
                              union
                                   select to_date(to_char(t.fee_record_date, 'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') tradetime,
                                           t.order_no ordernum,
                                           t.contract_no contractno,
                                           t.fee*100 amount,
                                           to_char(t.balance*100) balance,
                                           t.member_id no,
                                           b.member_name name,
                                           t.fee_type tradetype
                                    from fee_record t,business_member b
                                    where t.member_id = b.member_id(+)
                          ) a
                         where 1=1
                           and    to_char(a.tradetime, 'yyyy-mm-dd hh24:mi:ss') >= '""" + params.startDate + """'
                           and    to_char(a.tradetime, 'yyyy-mm-dd hh24:mi:ss') <= '""" + params.endDate + """'"""+condition+"""

                           order by a.tradetime desc
                           """
          println       channelSql
         if (params?.format && params.format in ["txt", "csv", "excel"]) {
            params.offset = null
            params.max = null
            def outAndInList = dbIsmp.rows(channelSql)
            def count = dbIsmp.rows("select count(*) as count from (" + channelSql + ")")[0][0]
            def outListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='in'")[0][0];
            def inListCount = dbIsmp.rows("select sum(t.amount)  sumamount  from ("+channelSql+") t where t.tradetype='out'")[0][0];
            def feeListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='fee'")[0][0];
             def payListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='pay'")[0][0];
             def getListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='get'")[0][0];
            def filename = 'Excel-' + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + '.xls'
            response.setHeader("Content-disposition", "attachment; filename=" + filename)
            response.setCharacterEncoding("GBK")
            response.contentType = "application/x-rarx-rar-compressed"
            render(template: "tianwu_${params.format}_OutAndIn", model: [outAndInList: outAndInList, tradeListTotal: count,outListCount:outListCount,inListCount:inListCount,feeListCount:feeListCount,payListCount:payListCount,getListCount:getListCount])

         }else{
              def outAndInList = dbIsmp.rows("select t.* from (select t.*, rownum rn from (" + channelSql + ") t) t where t.rn > " + listStart + " and t.rn <= " + listTo)
              def count = dbIsmp.rows("select count(*) as count from (" + channelSql + ")")[0][0]
                 def outListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='in'")[0][0];
                def inListCount = dbIsmp.rows("select sum(t.amount)  sumamount  from ("+channelSql+") t where t.tradetype='out'")[0][0];
                def feeListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='fee'")[0][0];
                 def payListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='pay'")[0][0];
                 def getListCount = dbIsmp.rows("select sum(t.amount)  sumamount from ("+channelSql+") t where t.tradetype='get'")[0][0];
              [outAndInList: outAndInList,outAndInListTotal:count,outListCount:outListCount,inListCount:inListCount,feeListCount:feeListCount,payListCount:payListCount,getListCount:getListCount]

         }

    }
}
