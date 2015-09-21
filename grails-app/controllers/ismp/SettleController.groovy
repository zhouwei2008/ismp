package ismp

import account.AcSequential
import boss.BoCustomerService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SettleController extends BaseController {

    def index = {
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
//        def accountNo = session.cmCustomer.accountNo
        def bcservice
        def query = {
           if(params.serviceType){
                 bcservice = BoCustomerService.findByCustomerIdAndServiceCode(session.cmCustomer.id,params.serviceType)
            }else{
                 bcservice = BoCustomerService.findByCustomerIdAndServiceCode(session.cmCustomer.id,'online')
            }
            if(bcservice){
                eq('accountNo', bcservice.srvAccNo)
            }else {
                isNull('accountNo')
            }

            if (params.startDate) {
                ge('dateCreated', Date.parse("yyyy-MM-dd", params.startDate))
            }
            if (params.endDate) {
                le('dateCreated', Date.parse("yyyy-MM-dd", params.endDate) + 1)
            }
            if (params.tradeType && params.tradeType != 'typeAll') {
                def tradeTypeList = params.list('tradeType')
                transaction {
                    or {
                        for (tradeType in tradeTypeList) {
                            eq('transferType', tradeType)
                        }
                    }
                }
            }
            if (params.direction && params.direction != 'all') {
                if (params.direction == 'in')
                    eq('creditAmount', 0 as Long)
                else if (params.direction == 'out')
                    eq('debitAmount', 0 as Long)
            }
        }
        if (params?.format && params.format in ["txt", "csv", "excel"]) {
            params.offset = null
            params.max = null
            def list = AcSequential.createCriteria().list(params, query)
            def count = AcSequential.createCriteria().count(query)

            def in_count = 0
            def in_amount = 0
            def out_count = 0
            def out_amount = 0
            list.each {
                if (it.creditAmount == 0) {
                    in_amount += it.debitAmount
                    in_count++
                }
                if (it.debitAmount == 0) {
                    out_amount += it.creditAmount
                    out_count++
                }
            }
            if (params.format in ["txt", "csv"]) {
                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                def filename = new Date().format('yyyyMMddHHmmss') + '_SettleDetail'
                response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            } else {
                def filename = 'Excel-' + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + '.xls'
                response.setHeader("Content-disposition", "attachment; filename=" + filename)
                response.contentType = "application/x-rarx-rar-compressed"
            }
            response.setCharacterEncoding("GBK")
            render(template: "tpl_${params.format}_settle", model: [acSeqList: list, acSeqListTotal: count, in_amount: in_amount, in_count: in_count, out_amount: out_amount, out_count: out_count])
        } else {
            def list = AcSequential.createCriteria().list(params, query)
            def count = AcSequential.createCriteria().count(query)
            [acSeqList: list, acSeqListTotal: count]
        }
    }

    def accdetail = {
        def boCustomer = BoCustomerService.findWhere(customerId: session.cmCustomer.id, serviceCode: 'online', isCurrent: true, enable: true)
        def accountNo = boCustomer?.srvAccNo
        def acSeq = AcSequential.findByIdAndAccountNo(params.id, accountNo)
        if (!acSeq ) {
            writeInfoPage "没找到该交易"
            return
        }
        [acSeq: acSeq]
    }
}
