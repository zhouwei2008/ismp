package dsf


import com.ecard.products.utils.FileUtils

import com.ecard.products.constants.Constants
import com.ecard.products.utils.DateUtils
import boss.BoAgentPayServiceParams
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import jxl.Workbook
import jxl.Sheet
import jxl.Cell

import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.format.Alignment
import jxl.write.NumberFormat

import packagedata.CombinedData
import java.text.DateFormat
import java.text.SimpleDateFormat;

class TbAgentpayInfoController {

    //代收付服务
    def agentLoaderService
    //账务服务
    def accountClientService
    def entrustClientService
    def dataFileService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
    def boCustomerflag(BoAgentPayServiceParams boCustomerServices){

    }

     // 代收批次文件申请
    def uploaddeductnew = {
            def f = request.getFile('agentDeductFile')
            if(f.empty) {
                flash.message = '批次文件内容为空，请重新输入代收批量文件。'
                render(view:'agentdeduct')
                return
            }

            //BufferedInputStream bi=new BufferedInputStream(f);
            //bi.readLines().size()
            def cmCustomer = session.cmCustomer
            // 写文件
            def filePath
             // 获取服务
            def boCustomerServices
            try{
                println(cmCustomer.id);
                println(Constants.ServiceType.COLLECT_SERVICE);
                boCustomerServices = BoAgentPayServiceParams.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.COLLECT_SERVICE,isCurrent:true,enable:true)
                if(boCustomerServices == null){
                    println '没有开通代收服务，删除批次文件'
                    //FileUtils.removeFile(filePath)
                    flash.message = '您没有开通代收服务，请确认'
                    render(view:'agentdeduct')
                    return
                }
               if(boCustomerServices.pubLimitMoney==null||boCustomerServices.priLimitMoney==null||boCustomerServices.dayLimitMoney==null||boCustomerServices.dayLimitTrans==null||"".equals(boCustomerServices.templateType)||!boCustomerServices.batchChannel){
               //if(!boCustomerServices.dayLimitTrans||!boCustomerServices.dayLimitMoney||!boCustomerServices.pubLimitMoney||!boCustomerServices.priLimitMoney||!boCustomerServices.templateType||!boCustomerServices.batchChannel){
                    println '没有设置代收服务，删除批次文件'
                    //FileUtils.removeFile(filePath)
                    flash.message = '没有设置代收服务参数，请联系管理员！'
                    render(view:'agentdeduct')
                    return
               }
            }catch (NullPointerException e){
                println '用户没有开通任何服务' + e
                //FileUtils.removeFile(filePath)
                flash.message = '您没有开通代收服务，请确认'
                render(view:'agentdeduct')
                return
            }
            def res=dataFileService.processData(f.getOriginalFilename(),f,cmCustomer.customerNo,'S')
            if(res[0].equals("true")){
                 filePath= res[2];
            }
            else if(res[0]=="false"){
                 log.warn res[1];
                 flash.message =res[1];
                 render(view: 'agentdeduct')
                 return
            }
            def fileline=dataFileService.readFileByLines(filePath,f.getOriginalFilename());
            if(fileline>Constants.FileNum.NUMBER){
                FileUtils.removeFile(filePath)
                flash.message = "文件过大，批量代收数据最多为50000条数据,请重新上传!";
                render(view: 'agentdeduct')
                return
            }
            def msg //构造的数据
            def rtr //调用委托接受返回值
             //业务类型 代付agentpay  代收agentcoll
            def combinedData=new CombinedData(); //TEMPLATE_TYPE
              //Integer.getInteger(cmCustomer.templateType)
            def filename=f.getOriginalFilename()
            def dsname=filename.substring(filename.indexOf(".")+1,filename.length()).toUpperCase();

            msg=combinedData.combine(filePath,boCustomerServices.templateType.toInteger(), dsname);//创建对象

             if(msg==null || "".equals(msg)){
                println '文件格式错误，删除错误文件'
                FileUtils.removeFile(filePath)
                flash.message = "文件格式错误，批量代收数据解析为空,请重新上传!";
                render(view: 'agentdeduct')
                return
            }
            println ' After setMsg Path is ' + msg;
            try{
                rtr=entrustClientService.openprocess("agentcoll","batch",cmCustomer.customerNo,msg,filename.substring(0,filename.indexOf(".")));
                if(rtr.result == 'true'){
                    // 修正文件名称
                    String fDone = FileUtils.make2done(filePath)
                    log.info '修改文件为关闭状态文件' + fDone
                    redirect( controller: 'tbAgentPayBox',action:'boxlist',params:[id:rtr.tradeId])
                }else if(rtr.result == 'false'){
                     println '保存失败！'
                     FileUtils.removeFile(filePath)
                     flash.message = rtr.errorMsg
                     log.error rtr.errorMsg
                }

            }catch(Exception ex){
                   ex.printStackTrace()
                   println '系统异常，删除批次文件'
                   //FileUtils.removeFile(filePath + Constants.FILEState.SUFFIX_DONE)
                   FileUtils.removeFile(filePath)
                   flash.message = ex.getMessage();
                   render(view:'agentdeduct')
                   return
            }
            //flash.message = '成功了！'
            render(view:'agentdeduct')
    }

    //  代付批次文件申请
    def uploadpaynew = {
        def f = request.getFile('agentPayFile')
         if(f.empty) {
                flash.message = '批次文件内容为空，请重新输入代付批量文件。'
                render(view:'agentpay')
                return
            }
            def cmCustomer = session.cmCustomer
            def boCustomerServices
            try{
                boCustomerServices = BoAgentPayServiceParams.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
                 if(boCustomerServices == null){
                    println '没有开通代付服务，删除批次文件'
                    //FileUtils.removeFile(filePath)
                    flash.message = '您没有开通代付服务，请确认'
                    render(view:'agentpay')
                    return
                 }
                 if("".equals(boCustomerServices.settWay)||"".equals(boCustomerServices.backFee)||boCustomerServices.pubLimitMoney==null||boCustomerServices.priLimitMoney==null||boCustomerServices.batchPubFee==null||boCustomerServices.batchPriFee==null||boCustomerServices.dayLimitMoney==null||boCustomerServices.dayLimitTrans==null||"".equals(boCustomerServices.templateType)||!boCustomerServices.batchChannel){
                    println '没有设置代付服务参数，删除批次文件'
                    //FileUtils.removeFile(filePath)
                    flash.message = '没有设置代付服务参数，请联系管理员！'
                    render(view:'agentpay')
                    return
                 }
            }catch (NullPointerException e){
                println '用户没有开通任何服务' + e
                //FileUtils.removeFile(filePath)
                flash.message = '您没有开通代付服务，请确认'
                render(view:'agentpay')
                return
            }

            def filePath
            def res=dataFileService.processData(f.getOriginalFilename(),f,cmCustomer.customerNo,'F')
            if(res[0].equals("true")){
                 filePath= res[2];
            }
            else if(res[0]=="false"){
                 log.warn res[1];
                 flash.message =res[1];
                 render(view: 'agentpay')
                 return
            }
            def fileline=dataFileService.readFileByLines(filePath,f.getOriginalFilename());
            if(fileline>Constants.FileNum.NUMBER){
                FileUtils.removeFile(filePath)
                flash.message = "文件过大，批量代付数据最多为50000条数据,请重新上传!";
                render(view: 'agentpay')
                return
            }
            def msg
            def rtr
             //业务类型 代付agentpay  代收agentcoll
             def combinedData=new CombinedData(); //TEMPLATE_TYPE
             def filename=f.getOriginalFilename()
             def dsname=filename.substring(filename.indexOf(".")+1,filename.length()).toUpperCase();

              msg=combinedData.combine(filePath,boCustomerServices.templateType.toInteger(),dsname);//创建对象

            if(msg==null || "".equals(msg)){
                println '文件格式错误，删除错误文件'
                FileUtils.removeFile(filePath)
                flash.message = "文件格式错误，批量代付数据解析为空,请重新上传!";
                render(view: 'agentpay')
                return
            }
            try{
                rtr=entrustClientService.openprocess("agentpay","batch",cmCustomer.customerNo,msg,filename.substring(0,filename.indexOf(".")));
                if(rtr.result == 'true'){
                     // 修正文件名称
                     String fDone = FileUtils.make2done(filePath)
                     log.info '修改文件为关闭状态文件' + fDone
                     redirect( controller: 'tbAgentPayBox',action:'boxlist',params:[id:rtr.tradeId])
                     //flash.message = '保存成功,数据上传到系统！'
                }else if(rtr.result == 'false'){
                     println '保存失败了！'
                     FileUtils.removeFile(filePath)
                     flash.message=rtr.errorMsg
                }
            }catch(Exception ex){
                   ex.printStackTrace()
                   println '系统异常，删除批次文件'
                   FileUtils.removeFile(filePath)
                   flash.message = ex.getMessage();
                   render(view:'agentpay')
                   return
            }
            log.info 'successfull !!!!!'
            //flash.message = '成功了！'
            render(view:'agentpay')

    }
    def agentdeduct = {
    }

    def agentpay = {
    }

    // 代收待审批次列表
    def agentdeductlist = {
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('coll')
        def max=params.max;
        if(params?.format && params.format in ["txt","csv"]){
            params.offset=null
            params.max=null
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            def totalamount=list.sum{it.batchAmount}
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            def filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_BatchDetail'
            response.setCharacterEncoding("GBK")
			response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            render(template: "tpl_${params.format}_trade_coll",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount,max:max])
		}else{
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count,max:max]
        }
    }

    // 代收交易查询
    def agentdeducthis = {
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('collhis');
        def max= params.max;
        if(params?.format && params.format in ["txt","csv"]){
            params.offset=null
            params.max=null
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            def totalamount=list.sum{it.batchAmount}
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            def filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_BatchDetail'
            response.setCharacterEncoding("GBK")
			response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            render(template: "tpl_${params.format}_trade_coll",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount,max:max])
		}else{
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count,max:max]
        }
    }

    // 代付待审批次列表
    def agentpaylist = {
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('pay')
        def max=params.max;
        if(params?.format && params.format in ["txt","csv"]){
            params.offset=null
            params.max=null
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            def totalamount=list.sum{it.batchAmount}
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            def filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_BatchDetail'
            response.setCharacterEncoding("GBK")
			response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            render(template: "tpl_${params.format}_trade_pay",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount,max:max])
		}else{
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count,max:max]
        }
        /*params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tbAgentpayInfoInstanceList: TbAgentpayInfo.list(params), tbAgentpayInfoInstanceTotal: TbAgentpayInfo.count()]*/
    }

    // 代付批次交易
    def agentpayhis = {
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('payhis')
        def max=1;//params.max;
        if(params?.format && params.format in ["txt","csv"]){
            params.offset=null
            params.max=null
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            def totalamount=list.sum{it.batchAmount}
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            def filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_BatchDetail'
            response.setCharacterEncoding("GBK")
			response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            render(template: "tpl_${params.format}_trade_pay",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount,max:max])
		}else{
            def list=TbAgentpayInfo.createCriteria().list(params,query)
            def count=TbAgentpayInfo.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count,max:max]
        }
    }

    protected getQuery(action){
        return {
          switch(action)
          {
              case 'coll':
                    eq('batchBizid',session.cmCustomer.customerNo)
                    eq('batchType','S')
                    eq('batchStatus','1')
                    eq('batchUsrChk','1')

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                    if (!params.startDate) {
                        Calendar calendar = Calendar.getInstance()
                        params.endDate = sdf.format(calendar.getTime())
                        calendar.add(Calendar.MONTH, -1)
                        params.startDate = sdf.format(calendar.getTime())
                    }

                    String startDate = String.valueOf(params.startDate)
                    int year = Integer.valueOf(startDate.substring(0, 4))
                    int month = Integer.valueOf(startDate.substring(5, 7)) - 1
                    int day = Integer.valueOf(startDate.substring(8, 10))
                    Calendar calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    ge('batchDate', sdf.format(calendar.getTime()))

                    String endDate = String.valueOf(params.endDate)
                    year = Integer.valueOf(endDate.substring(0, 4))
                    month = Integer.valueOf(endDate.substring(5, 7)) - 1
                    day = Integer.valueOf(endDate.substring(8, 10))
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    le('batchDate', sdf.format(calendar.getTime()))
//                    if(params.startDate)
//                    {
//                        ge('batchDate', Date.parse("yyyy-MM-dd",params.startDate).format('yyyy-MM-dd'))
//                    }
//                    if(params.endDate)
//                    {
//                        lt('batchDate',(Date.parse("yyyy-MM-dd",params.endDate)+1).format('yyyy-MM-dd'))
//                    }

                   // if(params.amountMin)
                   // {
                   //     ge('batchAmount',new BigDecimal(params.amountMin).toDouble())
                   // }
                  //  if(params.amountMax)
                  //  {
                  //      le('batchAmount', new BigDecimal(params.amountMax).toDouble())
                  //  }
                  break
              case 'pay':
                    eq('batchBizid',session.cmCustomer.customerNo)
                    eq('batchType','F')
                    eq('batchStatus','1')
                    eq('batchUsrChk','1')

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                    if (!params.startDate) {
                        Calendar calendar = Calendar.getInstance()
                        params.endDate = sdf.format(calendar.getTime())
                        calendar.add(Calendar.MONTH, -1)
                        params.startDate = sdf.format(calendar.getTime())
                    }

                    String startDate = String.valueOf(params.startDate)
                    int year = Integer.valueOf(startDate.substring(0, 4))
                    int month = Integer.valueOf(startDate.substring(5, 7)) - 1
                    int day = Integer.valueOf(startDate.substring(8, 10))
                    Calendar calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    ge('batchDate', sdf.format(calendar.getTime()))

                    String endDate = String.valueOf(params.endDate)
                    year = Integer.valueOf(endDate.substring(0, 4))
                    month = Integer.valueOf(endDate.substring(5, 7)) - 1
                    day = Integer.valueOf(endDate.substring(8, 10))
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    le('batchDate', sdf.format(calendar.getTime()))
//                    if(params.startDate)
//                    {
//                        ge('batchDate', Date.parse("yyyy-MM-dd",params.startDate).format('yyyy-MM-dd'))
//                    }
//                    if(params.endDate)
//                    {
//                        lt('batchDate',(Date.parse("yyyy-MM-dd",params.endDate)+1).format('yyyy-MM-dd'))
//                    }
                  //  if(params.amountMin)
                  //  {
                  //      ge('batchAmount',new BigDecimal(params.amountMin).toDouble())
                  //  }
                  //  if(params.amountMax)
                  //  {
                  //      le('batchAmount', new BigDecimal(params.amountMax).toDouble())
                  //  }
                  break
              case 'detail':
                    eq('batchBizid',session.cmCustomer.customerNo)

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                    if (!params.startDate) {
                        Calendar calendar = Calendar.getInstance()
                        params.endDate = sdf.format(calendar.getTime())
                        calendar.add(Calendar.MONTH, -1)
                        params.startDate = sdf.format(calendar.getTime())
                    }

                    String startDate = String.valueOf(params.startDate)
                    int year = Integer.valueOf(startDate.substring(0, 4))
                    int month = Integer.valueOf(startDate.substring(5, 7)) - 1
                    int day = Integer.valueOf(startDate.substring(8, 10))
                    Calendar calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    ge('tradeSubdate', calendar.getTime())

                    String endDate = String.valueOf(params.endDate)
                    year = Integer.valueOf(endDate.substring(0, 4))
                    month = Integer.valueOf(endDate.substring(5, 7)) - 1
                    day = Integer.valueOf(endDate.substring(8, 10))
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    calendar.add(Calendar.DATE, 1)
                    le('tradeSubdate', calendar.getTime())

//                   if(params.startDate)
//                    {
//                        ge('tradeSubdate', Date.parse("yyyy-MM-dd",params.startDate))
//                    }
//                    if(params.endDate)
//                    {
//                        lt('tradeSubdate',(Date.parse("yyyy-MM-dd",params.endDate)+1))
//                    }
                   // if(params.amountMin)
                   // {
                   //     ge('tradeAmount',new BigDecimal(params.amountMin).toDouble())
                   // }
                   // if(params.amountMax)
                  //  {
                  //      le('tradeAmount', new BigDecimal(params.amountMax).toDouble())
                   // }
                    if(params.id){
                        eq('batch', TbAgentpayInfo.load(params.id))
                    }
                    if(params.tradeStatus){
                        eq('tradeStatus',params.tradeStatus)
                    }
                    if(params.tradeFeedbackcode){
                        eq('tradeFeedbackcode',params.tradeFeedbackcode)
                    }
                    if(params.tradeAccountname){
                         like('tradeAccountname',"%"+params.tradeAccountname+"%")
                      //eq('tradeAccountname',params.tradeAccountname)
                    }
                    if(params.tradeCardname){
                         like('tradeCardname',"%"+params.tradeCardname+"%")
                      //eq('tradeCardname',params.tradeCardname)
                    }
                break
              case 'collhis':
                    eq('batchBizid',session.cmCustomer.customerNo)
                    eq('batchType','S')
                    if(params.batchStatus)
                    {
                       eq('batchStatus',params.batchStatus)
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                    if (!params.startDate) {
                        Calendar calendar = Calendar.getInstance()
                        params.endDate = sdf.format(calendar.getTime())
                        calendar.add(Calendar.MONTH, -1)
                        params.startDate = sdf.format(calendar.getTime())
                    }

                    String startDate = String.valueOf(params.startDate)
                    int year = Integer.valueOf(startDate.substring(0, 4))
                    int month = Integer.valueOf(startDate.substring(5, 7)) - 1
                    int day = Integer.valueOf(startDate.substring(8, 10))
                    Calendar calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    ge('batchDate', sdf.format(calendar.getTime()))

                    String endDate = String.valueOf(params.endDate)
                    year = Integer.valueOf(endDate.substring(0, 4))
                    month = Integer.valueOf(endDate.substring(5, 7)) - 1
                    day = Integer.valueOf(endDate.substring(8, 10))
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    le('batchDate', sdf.format(calendar.getTime()))
//                    if(params.startDate)
//                    {
//                        ge('batchDate', Date.parse("yyyy-MM-dd",params.startDate).format('yyyy-MM-dd'))
//                    }
//                    if(params.endDate)
//                    {
//                        lt('batchDate',(Date.parse("yyyy-MM-dd",params.endDate)+1).format('yyyy-MM-dd'))
//                    }
                   // if(params.amountMin)
                   // {
                   //     ge('batchAmount',new BigDecimal(params.amountMin).toDouble())
                   // }
                  //  if(params.amountMax)
                   // {
                   //     le('batchAmount', new BigDecimal(params.amountMax).toDouble())
                   // }
                     if(params.filename)
                    {
                        like('batchFilename', "%"+params.filename+"%")
                    }
                  break
              case 'payhis':
                    eq('batchBizid',session.cmCustomer.customerNo)
                    eq('batchType','F')
                    if(params.batchStatus)
                    {
                       eq('batchStatus',params.batchStatus)
                    }
                    ne('batchStatus','0')//过滤代付待确认记录

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                    if (!params.startDate) {
                        Calendar calendar = Calendar.getInstance()
                        params.endDate = sdf.format(calendar.getTime())
                        calendar.add(Calendar.MONTH, -1)
                        params.startDate = sdf.format(calendar.getTime())
                    }

                    String startDate = String.valueOf(params.startDate)
                    int year = Integer.valueOf(startDate.substring(0, 4))
                    int month = Integer.valueOf(startDate.substring(5, 7)) - 1
                    int day = Integer.valueOf(startDate.substring(8, 10))
                    Calendar calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    ge('batchDate', sdf.format(calendar.getTime()))

                    String endDate = String.valueOf(params.endDate)
                    year = Integer.valueOf(endDate.substring(0, 4))
                    month = Integer.valueOf(endDate.substring(5, 7)) - 1
                    day = Integer.valueOf(endDate.substring(8, 10))
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    le('batchDate', sdf.format(calendar.getTime()))
//                    if(params.startDate)
//                    {
//                        ge('batchDate', Date.parse("yyyy-MM-dd",params.startDate).format('yyyy-MM-dd'))
//                    }
//                    if(params.endDate)
//                    {
//                        lt('batchDate',(Date.parse("yyyy-MM-dd",params.endDate)+1).format('yyyy-MM-dd'))
//                    }
                  //  if(params.amountMin)
                  //  {
                  //      ge('batchAmount',new BigDecimal(params.amountMin).toDouble())
                 //   }
                 //   if(params.amountMax)
                 //   {
                 //       le('batchAmount', new BigDecimal(params.amountMax).toDouble())
                  //  }
                     if(params.filename)
                    {
                        like('batchFilename', "%"+params.filename+"%")
                    }
                  break
          }
           if(params.tradeNo)
            {
                eq('id',params.tradeNo)
            }
      }
    }

     protected writeInfoPage ={msg,type='error'->
        render view:'/error',model: [type:type,msg:msg]
    }

    def verify={
        def trade=TbAgentpayInfo.get(params.id)
        if(!trade||(trade.batchBizid!=session.cmCustomer.customerNo)){
            writeInfoPage "没找到该交易"
            return
        }
        [trade:trade]
    }
    def verifySuccess = {
        def batchPay = TbAgentpayInfo.findById(params.id);
        def batchNotes=params.batchRemark1;
        def rtr
        try{
            rtr=entrustClientService.openverify(batchPay.id,"pass",batchNotes);
            if(rtr.result == 'true'){
                flash.message = '操作已成功！'
                log.info '审核已成功'+batchPay.getBatchBizid();
            }else if(rtr.result == 'false'){
                 flash.message=rtr.errorMsg
                 log.info '审核失败'+batchPay.getBatchBizid();
                 //  throw new RuntimeException(rtr.errorMsg)
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(batchPay.batchType == 'S'){
                redirect(action:'agentdeductlist')
            }else if(batchPay.batchType == 'F'){
                redirect(action:'agentpaylist')
            }
        }
    }

    def verifyRefuse = {
       def batchPay = TbAgentpayInfo.findById(params.id);
        def batchNotes=params.batchRemark1;
        def rtr
        try{
            rtr=entrustClientService.openverify(batchPay.id,"refuse",batchNotes);
            if(rtr.result == 'true'){
                flash.message = '商户拒绝成功！'
                log.info '商户拒绝成功了'+batchPay.getBatchBizid();
            }else if(rtr.result == 'false'){
                 flash.message=rtr.errorMsg
                 log.info '商户拒绝失败了'+batchPay.getBatchBizid();
                 //  throw new RuntimeException(rtr.errorMsg)
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(batchPay.batchType == 'S'){
                redirect(action:'agentdeductlist')
            }else if(batchPay.batchType == 'F'){
                redirect(action:'agentpaylist')
            }
        }

    }

    def detaillist = {
        params.sort = params.sort ? params.sort : "tradeNumOrder"
        params.tradeflag =params.tradeflag ? params.int('tradeflag') : 0
        params.order = params.order ? params.order : ""
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('detail')
        def max= params.max;
        def type = TbAgentpayInfo.load(params.id).batchType
        def tradeflag=params.tradeflag;
        if(params?.format && params.format in ["txt","csv"]){
            params.offset=null
            params.max=null
            def list=TbAgentpayDetailsInfo.createCriteria().list(params,query)
            def count=TbAgentpayDetailsInfo.createCriteria().count(query)
            def totalamount=list.sum{it.tradeAmount}
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            def batchFilename=""
            if(list!=null){
                batchFilename=TbAgentpayInfo.get(params.id).batchFilename//.fin.findAll(list.get(0)).batchFilename
            }
            def filename
            if(batchFilename!=null){
                filename = java.net.URLEncoder.encode('Re'+batchFilename, "UTF-8")
            }
            else{
                 filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_TradeDetail'
            }
            response.setCharacterEncoding("GBK")
			response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            if(session.cmCustomer.customerNo == grailsApplication.config.huaAnBizCode){
               render(template: "tpl_${params.format}_trade_huaandetail",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount,max:max])
             }else{
               render(template: "tpl_${params.format}_trade_detailcoll",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount,max:max])
             }
             return
		}else if(params?.format && params.format in ["xls"]){
            params.offset=null
            params.max=null
            def list=TbAgentpayDetailsInfo.createCriteria().list(params,query)
            def count=TbAgentpayDetailsInfo.createCriteria().count(query)
            def totalamount=list.sum{it.tradeAmount}
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            def batchFilename=""
            if(list!=null){
                batchFilename=TbAgentpayInfo.get(params.id).batchFilename//.fin.findAll(list.get(0)).batchFilename
            }
            def filename
            if(batchFilename!=null){
                filename = java.net.URLEncoder.encode('Re'+batchFilename, "UTF-8")
            }
            else{
                 filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_TradeDetail'
            }
            //def filename=session.cmCustomer.customerNo+'_'+new Date().format('yyyyMMdd')+'_TradeDetail'
            response.setCharacterEncoding("GBK")
			response.setHeader("Content-disposition", "attachment; filename=${filename}.${params.format}")
            response.setContentType("application/msexcel");// 定义输出类型

               //response.contentType = "application/x-rarx-rar-compressed"
               //render(template: "tpl_${params.format}_trade_huaandetail",model:[tradeList: list, tradeListTotal: count,totalamount:totalamount])
               // 更新
               OutputStream os = response.getOutputStream();//response.getOutputStream();//取得输出流
                WritableWorkbook workbook = Workbook.createWorkbook(os);
                WritableSheet sheet = workbook.createSheet(new Date().format('yyyyMMdd'), 0);
                // 组织excel文件的内容
                jxl.write.Label label = null;
                int excelCol = 0;
                int row = 0;
                try {
                    sheet.setRowView(0,300); // 设置行宽,行高,第1行
                    sheet.setColumnView(0,20);// 设置列宽,行高,第1列
                    sheet.setColumnView(1,20);// 设置列宽,行高,第1列
                    sheet.setColumnView(2,15);
                    sheet.setColumnView(3,15);
                    sheet.setColumnView(4,15);
                    sheet.setColumnView(5,15);
                    sheet.setColumnView(6,15);
                    sheet.setColumnView(7,22);
                    sheet.setColumnView(8,20);
                    sheet.setColumnView(9,15);
                    sheet.setColumnView(10,15);
                    sheet.setColumnView(11,15);
                    sheet.setColumnView(12,15);
                    sheet.setColumnView(13,15);
                    sheet.setColumnView(14,20);
                    sheet.setColumnView(15,20);
                    sheet.setColumnView(16,15);
                    sheet.setColumnView(17,15);
                    sheet.setColumnView(18,15);
                    sheet.setColumnView(19,15);
                    sheet.setColumnView(20,15);
                    sheet.setColumnView(21,15);

                    jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.createFont("宋体"),10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
                    jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
                    wcfFC.setBackground(jxl.format.Colour.GREY_50_PERCENT);
                    wcfFC.setAlignment(Alignment.CENTRE);
                    label = new jxl.write.Label(excelCol++, row, "交易号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "创建日期", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "流水号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "类型", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "交易金额（元）", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "状态", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "收/付款人", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "收/付款人账号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "开户行名称", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "开户行(省)", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "开户行(市)", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "分行", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "支行", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "证件类型", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "证件号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "手机号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "用户协议号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "商户订单号", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "完成时间", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "摘要", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "交易状态", wcfFC);
                    sheet.addCell(label);
                    label = new jxl.write.Label(excelCol++, row, "反馈原因", wcfFC);
                    sheet.addCell(label);


                    jxl.write.Number number = null;
                    jxl.write.NumberFormat nf = new NumberFormat("#0.00#");
			        WritableCellFormat wcfN = new WritableCellFormat(nf);
                    //jxl.write.DateTime dateTime;
                    //jxl.write.DateFormat customDateFormat = new jxl.write.DateFormat("yyyy-MM-dd");//时间格式
                    for(int i=0;i<list.size();i++){
                        TbAgentpayDetailsInfo rr = (TbAgentpayDetailsInfo) list.get(i)
                        //println rr.id + " = " + rr.cbusinessType + " = " + rr.cpayCorname

                        excelCol = 0
                        row = i + 1
                        label = new jxl.write.Label(excelCol++, row, rr.id)
                        sheet.addCell(label)
                        //label = new jxl.write.Label(excelCol++, row, formatDate.format(rr.tradeSysdate))
                        try{
                            label = new jxl.write.Label(excelCol++, row, rr.tradeSubdate.format("yyyy-MM-dd HH:mm:ss")) //new jxl.write.Label(excelCol++, row, formatDate(rr.tradeSysdate));
                            sheet.addCell(label);
                        }catch (Exception e){
                            //Maybe somebody fogot to input his birthday ,I need do nothing here!
                        }
                        //sheet.addCell(label)
                        label = new jxl.write.Label(excelCol++, row, rr.tradeNum)
                        sheet.addCell(label)
                        label = new jxl.write.Label(excelCol++, row, rr.typeMap[rr.tradeType])
                        sheet.addCell(label)
                        /*字串格式*/
                        /*try{
                            label = new jxl.write.Label(excelCol++, row, formatDate.format(rr.ctradeCurrdate));
                            sheet.addCell(label);
                        }catch (Exception e){
                            //Maybe somebody fogot to input his birthday ,I need do nothing here!
                        }*/
                        number = new jxl.write.Number(excelCol++, row, rr.tradeAmount,wcfN);
                        sheet.addCell(number);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeStatusMap[rr.tradeStatus]);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeCardname);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeCardnum);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeAccountname);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeProvince);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeCity);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeBranchbank);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeSubbranchbank);
                        sheet.addCell(label);

                        label = new jxl.write.Label(excelCol++, row, rr.certificateTypeMap[rr.certificateType?rr.certificateType:""]);
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.certificateNum?rr.certificateNum.toString():"");
				        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeMobile?rr.tradeMobile.toString():"");
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.contractUsercode?rr.contractUsercode.toString():"");
                        sheet.addCell(label);
                        label = new jxl.write.Label(excelCol++, row, rr.tradeCustorder?rr.tradeCustorder.toString():"");
                        sheet.addCell(label);
                         try{
                            label = new jxl.write.Label(excelCol++, row, rr.tradeDonedate.format("yyyy-MM-dd HH:mm:ss"));
                            sheet.addCell(label);
                        }catch (Exception e){
                            //Maybe somebody fogot to input his birthday ,I need do nothing here!
                        }
                        // label = new jxl.write.Label(excelCol++, row, formatDate.format(rr.tradeDonedate));
                       // sheet.addCell(label);
                         label = new jxl.write.Label(excelCol++, row, rr.tradeRemark);
                        sheet.addCell(label);
                         label = new jxl.write.Label(excelCol++, row, rr.tradeFeedbackcode);
                        sheet.addCell(label);
                         label = new jxl.write.Label(excelCol++, row, rr.tradeReason);
                        sheet.addCell(label);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
        //			生成excel文件
                    workbook.write();
                    workbook.close();
                    os.close();
                }
                return null
           // }else{
           //    render(template: "tpl_${params.format}_trade_detailcoll",model:[tradeList: list, tradeListTotal: count,params:params,type:type,max:max,tradeflag:tradeflag])
          //  }
            //return
        }else{
            def list=TbAgentpayDetailsInfo.createCriteria().list(params,query)
            def count=TbAgentpayDetailsInfo.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count,params:params,type:type,max:max,tradeflag:tradeflag]
        }
    }

     // 模板下载
    def getdeductfile = {
      def filepath = request.getRealPath("/")+"download"+ File.separator +"agentdeduct_model.txt"
      downfile(filepath, '代收TXT模板.TXT')
    }

    def getpayfile = {
      def filepath = request.getRealPath("/")+"download"+ File.separator +"agentpay_model.txt"
      downfile(filepath, '代付TXT模板.TXT')
    }

    def getdeductxlsfile = {
      def filepath = request.getRealPath("/")+"download"+ File.separator +"agentdeduct_model.xls"
      downfile(filepath, '代收Excel模板.XLS')
    }

    def getpayxlsfile = {
      def filepath = request.getRealPath("/")+"download"+ File.separator +"agentpay_model.xls"
      downfile(filepath, '代付Excel模板.XLS')
    }

    def downfile(String filepath, String fn){
       OutputStream out=response.getOutputStream()
       byte [] bs = new byte[500]
       File fileLoad=new File(filepath)
       response.setContentType("application/octet-stream;charset=gbk");
       response.setHeader("content-disposition","attachment; filename=" + java.net.URLEncoder.encode(fn, "UTF-8"));
       long fileLength=fileLoad.length();
       String length1=String.valueOf(fileLength);
       response.setHeader("Content_Length",length1)
       InputStream inputStream = new FileInputStream(fileLoad)
       int len
       while((len = inputStream.read(bs))!=-1){
            out.write(bs,0,len)
        }
       inputStream.close()
       out.flush()
       out.close()
    }


    // 附加
    def simplededuct = {

    }
    def simplepay = {

    }
    def simpledconfirm = {
        log.info '单笔收款确认路过' + params.dump()
    }
    def simplepconfirm = {
        log.info '单笔付款确认路过' + params.dump()
    }

    def RESP_RESULT_SUCC = 'true' //成功
    def RESP_RESULT_FAIL = 'false' //失败

    def buildJSON(String status, String msg){
        return new StringBuffer()
                .append("{")
                .append("status:")
                .append("'" + status + "'")
                .append(",")
                .append("msg:")
                .append("'" + msg + "'")
                .append("}").toString()
    }

    //单笔代收
    def simpledeductnew={
       //log.info params.dump()
        def errMsg
        // 获取服务
        def cmCustomer = session.cmCustomer
        def boCustomerServices
        try{
            boCustomerServices = BoAgentPayServiceParams.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.COLLECT_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                errMsg = '您没有开通代收服务，请确认!'
                String result = buildJSON(RESP_RESULT_FAIL, errMsg);
                log.info 'Result is ' + result
                render(result)
                return
            }

        if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||boCustomerServices.pubLimitMoney==null||boCustomerServices.priLimitMoney==null||!boCustomerServices.singleChannel){
                errMsg = '没有设置代收服务参数，请联系管理员!'
                String result = buildJSON(RESP_RESULT_FAIL, errMsg);
                log.info 'Result is ' + result
                render(result)
                return
           }
        }catch (NullPointerException e){
            println '用户没有开通任何服务' + e
            errMsg = '没有开通任何服务!'
            String result = buildJSON(RESP_RESULT_FAIL, errMsg);
            log.info 'Result is ' + result
            render(result)
            return
        }



        def rtr
        def msg
        msg="00001,"+params.tradeCardnum+","+params.tradeCardname+","+params.tradeAccountname+","+params.tradeBranchbank+","+params.tradeSubbranchbank+","+params.tradeAccounttype+","+params.tradeAmount+","+"CNY," +params.tradeProvince +","+params.tradeCity+","+params.tradeMobile +","+params.certificateType +","+params.certificateNum +","+params.contractUsercode+","+","+params.tradeRemark;
        println("msg====="+msg);
        String result="";
        try{

                rtr=entrustClientService.openprocess("agentcoll","single",cmCustomer.customerNo,msg.replace(',null',','),'');
                println("rtr====="+rtr);
                if(rtr.result == 'true'){
                  println '提交成功！'
                  result = buildJSON(RESP_RESULT_SUCC, "提交成功！");
                    String custId = params.tradeId
                    println 'cid ==================== ' + custId
                    def c
                    if(custId == null||custId.equals("")){
                        c = TbBizCustomer.findByCardNumAndBizType(params.tradeCardnum,'S')
                    }else{
                        c = TbBizCustomer.findById(custId)
                    }
                    if(!c){
                        c = new TbBizCustomer()
                    }
                    c.bizId = session.cmCustomer.customerNo
                    c.cardName = params.tradeCardname
                    c.cardNum = params.tradeCardnum
                    c.bizType = 'S'
                    c.bank = params.tradeAccountname
                    c.branchBank = params.tradeBranchbank
                    c.subbranchBank = params.tradeSubbranchbank
                    c.accountType = params.tradeAccounttype
                    c.contractCode = params.contractUsercode
                    c.province = params.tradeProvince
                    c.city = params.tradeCity
                    c.lastTime = new Date()
                    c.remark = params.tradeRemark
                    c.certificateType= params.certificateType
                    c.certificateNum=params.certificateNum
                    c.tradeMobile=params.tradeMobile
                    def cResult = c.save(flush:true,failOnError:true)
                    log.info 'Save Customer Result is ' + cResult

                    //errMsg ="提交成功！"
                    //String result = buildJSON(RESP_RESULT_SUCC, errMsg);
                    log.info 'Succ Result is ' + result
                    render(result)
                }else if(rtr.result == 'false'){
                  println '提交失败！'
                  result = buildJSON(RESP_RESULT_FAIL,rtr.errorMsg);
                   //throw new RuntimeException(rtr.errorMsg)
                  render(result)
                }
        }catch(Exception ex){
           ex.printStackTrace()
           println '单笔收款系统异常'
           result = buildJSON(RESP_RESULT_FAIL, "单笔收款系统异常");
           log.info 'Result is ' + result
           render(result)
           return
        }

    }

    //单笔代付
    def simplepaynew = {
       //log.info params.dump()
        def errMsg
        // 获取服务
        def cmCustomer = session.cmCustomer
        def boCustomerServices
        try{
            boCustomerServices = BoAgentPayServiceParams.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                errMsg = '您没有开通代付服务，请确认'
                String result = buildJSON(RESP_RESULT_FAIL, errMsg);
                log.info 'Result is ' + result
                render(result)
                return
            }

        if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||"".equals(boCustomerServices.backFee)||"".equals(boCustomerServices.settWay)||boCustomerServices.singlePriFee==null||boCustomerServices.singlePubFee==null||boCustomerServices.pubLimitMoney==null||boCustomerServices.priLimitMoney==null||!boCustomerServices.singleChannel){
                errMsg = '没有设置代付服务参数，请联系管理员！'
                String result = buildJSON(RESP_RESULT_FAIL, errMsg);
                log.info 'Result is ' + result
                render(result)
                return
           }
        }catch (NullPointerException e){
            println '用户没有开通任何服务' + e
            errMsg = '您没有开通代付服务，请确认'
            String result = buildJSON(RESP_RESULT_FAIL, errMsg);
            log.info 'Result is ' + result
            render(result)
            return
        }

        def ta = TbAgentpayInfo.createCriteria()
        def batch = ta.get {
            projections {
                eq('batchBizid',session.cmCustomer.customerNo)
                eq('batchType','F')
                eq('batchDate', new Date().format('yyyyMMdd'))
                max('batchCurrnum')
            }
        }
        log.info 'batch is === ' + batch



        def rtr
        def msg
       msg="00001,"+params.tradeCardnum+","+params.tradeCardname+","+params.tradeAccountname+","+params.tradeBranchbank+","+params.tradeSubbranchbank+","+params.tradeAccounttype+","+params.tradeAmount+","+"CNY,"+params.tradeProvince+","+params.tradeCity+","+params.tradeMobile+","+params.certificateType+","+params.certificateNum+","+params.contractUsercode+","+params.tradeCustorder+","+params.tradeRemark;
        //msg=vauleString(msg)

        println msg
        try{
                rtr=entrustClientService.openprocess("agentpay","single",cmCustomer.customerNo,msg.replace(',null',','),'');
                if(rtr.result == 'true'){
                     println '提交成功！'
                }else if(rtr.result == 'false'){
                     println '失败了！'
                     String result = buildJSON(RESP_RESULT_FAIL, rtr.errorMsg);
                     render(result)
                     return
                }
        }catch(Exception ex){
           ex.printStackTrace()
           println '单笔代付系统异常'
           String result = buildJSON(RESP_RESULT_FAIL, "单笔代付系统异常");
           log.info 'Result is ' + result
           render(result)
           return
        }
        String custId = params.tradeId
        def c
        if(custId == null||custId.equals("")){
            c = TbBizCustomer.findByCardNumAndBizType(params.tradeCardnum,'F')
        }else{
            c = TbBizCustomer.findById(custId)
        }
        if(!c){
            c = new TbBizCustomer()
        }
        c.bizId = session.cmCustomer.customerNo
        c.cardName = params.tradeCardname
        c.cardNum = params.tradeCardnum
        c.bizType = 'F'
        c.bank = params.tradeAccountname
        c.branchBank = params.tradeBranchbank
        c.subbranchBank = params.tradeSubbranchbank
        c.accountType = params.tradeAccounttype
        //c.contractCode = params.contractUsercode
        c.province = params.tradeProvince
        c.city = params.tradeCity
        c.lastTime = new Date()
        c.remark = params.tradeRemark
        c.certificateType= params.certificateType
        c.certificateNum=params.certificateNum
        c.tradeMobile=params.tradeMobile

        def cResult = c.save(flush:true,failOnError:true)
        log.info 'Save Customer Result is ' + cResult

        errMsg = "提交成功！"
        String result = buildJSON(RESP_RESULT_SUCC, errMsg);
        log.info 'Succ Result is ' + result
        render(result)
    }

    def getProvinceUnit(String province){
        // 直辖市
        if(province.equals('北京')|| province.equals('上海') || province.equals('天津')||province.equals('重庆')){
            return '市'
        }else{
            return '省'
        }
    }

    def nextSeqNum(String curr){
        if(!curr.startsWith("9")){
           return '90001'
        }
        String base = "00000";
		String r = base + (Integer.parseInt(curr) + 1);
		//System.out.println(r.substring(r.length()-base.length(), r.length()));
        return r.substring(r.length()-base.length(), r.length())
    }

    def uploadtest = {
        def f = request.getFile('agentDeductFile')
        log.info f.size + " && " + f.getOriginalFilename().toUpperCase()
        log.info f.dump()

        if(!f.empty) {
            def tarFile = new File('D:/root/'+f.getOriginalFilename().toUpperCase())
            // 写文件
            BufferedInputStream bin = new BufferedInputStream(f.getInputStream())
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(tarFile))
            int i = 0;
            byte [] bs = new byte[1024]
            while((i=bin.read(bs))!=-1){
                bout.write(bs,0,i)
                bout.flush()
            }
            bout.close()
            bin.close()
            // f.transferTo(tarFile)
            log.info tarFile.absolutePath + " " + tarFile.length()
            // 解析Excel xls xls
            //1选取Excel文件，2选择工作簿，3选择Cell，4读取信息。
            // 那么现在就可以看看JXL中这四步骤如何体现：
            //通过Workbook的静态方法getWorkbook选取Excel文件
            Workbook wb = null
            BufferedWriter bw = new BufferedWriter(new FileWriter('D:/root/'+(f.getOriginalFilename().toUpperCase().replaceFirst('.XLS','.TXT'))))
            try {
                wb = Workbook.getWorkbook(new FileInputStream(tarFile))
                int size = 13
                //通过Workbook的getSheets方法获取工作簿集（从0开始）
                Sheet rs = wb.getSheet(0)
				for (int j = 0; j < rs.getRows(); j++) {
                    Cell[] cs = rs.getRow(j)
                    StringBuffer line = new StringBuffer()
                    for(int z = 0; z < size && z < cs.length; z++){
                        Cell c = cs[z]
                        if(j == 0 && (null == c.getContents()||''==c.getContents())){
                            break
                        }
                        line.append(c.getContents())
                        line.append(",")

                    }
                    def rlt = line.substring(0, line.length()-1)
                    log.info 'item line is ' + rlt
                    bw.write(rlt)
                    if(j != rs.getRows()-1){
                        bw.newLine()
                    }
                    bw.flush()
                }
                //最后关闭bw以释放资源
                bw.close()
                //最后不要忘记关闭wb以释放资源：
                wb.close()
            } catch (Exception e) {
                e.printStackTrace()
            }
            response.sendError(200,'Done')
        } else {
             flash.message = 'file cannot be empty'
             render(view:'uploadForm')
        }
    }

    // 客户管理
    def customerList = {
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def max=params.max;
        def query=getCustQuery('all')
        def list=TbBizCustomer.createCriteria().list(params,query)
        def count=TbBizCustomer.createCriteria().count(query)
        [tradeList: list, tradeListTotal: count,reqSearch:params.reqSearch,resultSearch:params.resultSearch,max:max]
    }

    def delCustomer = {
        TbBizCustomer.executeUpdate("delete from TbBizCustomer where id in (" + params.ids + ")")
        params.bizType = params.bizType
        redirect(action : customerList,params : params)
    }

    protected getCustQuery(action){
        log.info 'agentpay customer'
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

}
