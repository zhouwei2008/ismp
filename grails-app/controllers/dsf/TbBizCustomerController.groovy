package dsf

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class TbBizCustomerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tbBizCustomerInstanceList: TbBizCustomer.list(params), tbBizCustomerInstanceTotal: TbBizCustomer.count()]
    }

    protected getQuery(action){
        return {
          switch(action)
          {
              case 'all':
                    eq('bizId',session.cmCustomer.customerNo)
                    eq('bizType',params.bizType)
                    if(params.reqSearch && params.resultSearch){
                        println '进入LIKE了.....................'
                        ilike(params.reqSearch, '%'+params.resultSearch+'%')
                    }
                  break
          }
      }
    }

    def customerList = {
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('all')
        def list=TbBizCustomer.createCriteria().list(params,query)
        def count=TbBizCustomer.createCriteria().count(query)
        [tradeList: list, tradeListTotal: count,reqSearch:params.reqSearch,resultSearch:params.resultSearch]
    }

    def getCollData = {

        log.info 'get required is ' + params.q
        def datas = TbBizCustomer.createCriteria().list {
             eq('bizId',session.cmCustomer.customerNo)
             eq('bizType','S')
            if(params.q){
                ilike('cardName', params.q+'%')
            }
        }

        log.info 'Datas is ' + datas.size()
        String result;
		StringBuffer buffer = new StringBuffer();
		for(TbBizCustomer bc : datas){
            buffer.append("{")
            .append("cardName:")
            .append("'" + bc.cardName + "'")
            .append(",")
            .append("cardNum:")
            .append("'" + bc.cardNum + "'")
            .append(",")
            .append("id:")
            .append("'" + bc.id + "'")
            .append(",")
            .append("bank:")
            .append("'" + bc.bank + "'")
            .append(",")
            .append("branchBank:")
            .append("'" + bc.branchBank + "'")
            .append(",")
            .append("subbranchBank:")
            .append("'" + bc.subbranchBank + "'")
            .append(",")
            .append("province:")
            .append("'" + bc.province + "'")
            .append(",")
            .append("city:")
            .append("'" + bc.city + "'")
            .append(",")
            .append("accountType:")
            .append("'" + bc.accountType + "'")
            .append(",")
            .append("contractCode:")
            .append("'" + bc.contractCode + "'")
            .append(",")
            .append("remark:")
            .append("'" + bc.remark + "'")
            .append("}")
            .append("\n");
		}
		result = buffer.toString();
        log.info 'Get Coll Result is ' + result
        render result
    }

    def getPayData = {

        log.info 'get required is ' + params.q
        def datas = TbBizCustomer.createCriteria().list {
             eq('bizId',session.cmCustomer.customerNo)
             eq('bizType','F')
            if(params.q){
                ilike('cardName', params.q+'%')
            }
        }

        log.info 'Datas is ' + datas.size()
        String result;
		StringBuffer buffer = new StringBuffer();
		for(TbBizCustomer bc : datas){
            buffer.append("{")
            .append("cardName:")
            .append("'" + bc.cardName + "'")
            .append(",")
            .append("cardNum:")
            .append("'" + bc.cardNum + "'")
            .append(",")
            .append("id:")
            .append("'" + bc.id + "'")
            .append(",")
            .append("bank:")
            .append("'" + bc.bank + "'")
            .append(",")
            .append("branchBank:")
            .append("'" + bc.branchBank + "'")
            .append(",")
            .append("subbranchBank:")
            .append("'" + bc.subbranchBank + "'")
            .append(",")
            .append("province:")
            .append("'" + bc.province + "'")
            .append(",")
            .append("city:")
            .append("'" + bc.city + "'")
            .append(",")
            .append("accountType:")
            .append("'" + bc.accountType + "'")
            .append(",")
            .append("remark:")
            .append("'" + bc.remark + "'")
            .append("}")
            .append("\n");
		}
		result = buffer.toString();
        log.info 'Get Pay Result is ' + result
        render result
    }

    def chkCustomer = {
        log.info 'check id is ' + params.id + ' ' + params.dump()
    }

    def delCustomer = {
        TbBizCustomer.executeUpdate("delete from TbBizCustomer where id in (" + params.ids + ")")
        params.bizType = params.bizType
        redirect(action : customerList,params : params)
    }

}
