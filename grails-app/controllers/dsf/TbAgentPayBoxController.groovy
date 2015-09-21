package dsf

import boss.BoAgentPayServiceParams
import com.ecard.products.constants.Constants
import com.ecard.products.utils.DateUtils
import jxl.Cell
import jxl.Sheet
import jxl.Workbook
import jxl.format.Alignment
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import entrust.EntrustClientService;
import jxl.write.*

class TbAgentPayBoxController {

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
    protected getQuery(action){
        return {
          switch(action)
          {
              case 'coll'://代收付封箱列表
                    eq('batchBizid',session.cmCustomer.customerNo)
                    eq('batchStatus','0')
                    if(params.startDate)
                    {
                        ge('batchDate', Date.parse("yyyy-MM-dd",params.startDate).format('yyyy-MM-dd'))
                    }
                    if(params.endDate)
                    {
                        lt('batchDate',(Date.parse("yyyy-MM-dd",params.endDate)+1).format('yyyy-MM-dd'))
                    }
                    if(params.filename)
                    {
                        like('batchFilename', params.filename+"%")
                    }
                  break
              case 'detail':
                   eq('batchBizid',session.cmCustomer.customerNo)
                    if(params.id){
                        eq('batch', TbAgentpayInfo.load(params.id))
                    }
                break
          }
           if(params.tradeNo)
            {
                eq('id',params.tradeNo)
            }
      }
    }
    //代收付封箱列表
    def boxinfo={
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def max= Math.min(params.max ? params.int('max') : 10, 100);

        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('coll')
        if(params?.format && params.format in ["txt","csv"]){
            params.offset=null
            params.max=10
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
    //代收付明细表
    def boxlist={
        params.sort = params.sort ? params.sort : "tradeNumOrder"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query=getQuery('detail')
        def max=params.max;

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
            //def count=TbAgentpayDetailsInfo.createCriteria().count(query)
            //def totalamount=list.sum{it.tradeAmount}
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
                    label = new jxl.write.Label(excelCol++, row, "摘要", wcfFC);
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
                        // label = new jxl.write.Label(excelCol++, row, formatDate.format(rr.tradeDonedate));
                       // sheet.addCell(label);
                         label = new jxl.write.Label(excelCol++, row, rr.tradeRemark);
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
        }else{
            def list=TbAgentpayDetailsInfo.createCriteria().list(params,query)
            def count=TbAgentpayDetailsInfo.createCriteria().count(query)
            def batch = TbAgentpayInfo.findById(params.id)

            [tradeList: list, tradeListTotal: count,params:params,batch:batch,max:max]
        }
    }
    def boxupdate={
       def batchPay = TbAgentpayDetailsInfo.get(params.tradeid as Long)
       def batch= batchPay.getBatch()
          [trade:batchPay,batchId:batch.id]
    }

    // 附加
    def boxupdateindex = {

    }

    //代收付修改
    def boxupdateinfo={
        def batchPay = TbAgentpayDetailsInfo.findById(params.tradeId)
        def tbAgentpayInfo=batchPay.batch;
        def rtr
        def Msg;

        try{
        Msg=params.tradeNum+","+params.tradeCardnum+","+params.tradeCardname+","+params.tradeAccountname+","+params.tradeBranchbank+","+params.tradeSubbranchbank+","+params.tradeAccounttype+","+params.tradeAmount+","+"CNY,"+params.tradeProvince+","+params.tradeCity+","+params.tradeMobile+","+params.certificateType+","+params.certificateNum+","+params.contractUsercode+","+params.tradeCustorder+","+params.tradeRemark;
            rtr=entrustClientService.openrenew(params.tradeId,session.cmCustomer.customerNo,Msg);
            if(rtr.result == 'true'){
                flash.message = '修改成功了！'
                log.info '修改成功'+batchPay.getBatchBizid();
            }else if(rtr.result == 'false'){
                 flash.message=rtr.errorMsg
                 log.info '修改失败'+batchPay.getBatchBizid();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
           redirect(action:boxlist,id:tbAgentpayInfo.id)
        }
    }
    //代收付明细删除
    def agentinfodel={
        println("params.id====") + params.tradeid
        def batchPay = TbAgentpayDetailsInfo.findById(params.tradeid)
        def tbAgentpayInfo=batchPay.batch;
        def rtr
        def errMsg;
        try{
            rtr=entrustClientService.openremove(params.tradeid.toString(),session.cmCustomer.customerNo);
            if(rtr.result == 'true'){
                flash.message = '单笔删除成功！'
                log.info '单笔删除成功'+batchPay.getBatchBizid();
            }else if(rtr.result == 'false'){
                 flash.message=rtr.errorMsg
                 log.info '单笔删除失败'+batchPay.getBatchBizid();
            }


        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        redirect(action:boxlist,id:tbAgentpayInfo.id)
    }

    //代收付删除
    def boxdel={
        def errMsg;
        def batchPay = TbAgentpayInfo.findById(params.id)
        batchPay.delete(flush: true);
        errMsg = '批次删除成功！'
        flash.message = '批次删除成功！'
         //删除附件
        def cmCustomer = session.cmCustomer
        String dirPathnew = Constants.Config.DATA_ROOT + File.separator + batchPay.batchType + File.separator + cmCustomer.customerNo; //保存全路径
        dataFileService.deletefile(batchPay.batchFilename,dirPathnew);
        String result = buildJSON(RESP_RESULT_SUCC, errMsg);


        log.info 'Succ Result is ' + result
        redirect(action : boxinfo,params : params)
    }
    //代收付封箱审核
    def boxsubmit={
        def rtr
        try{
            rtr=entrustClientService.openpackages(params.id);
            TbAgentpayInfo tb=TbAgentpayInfo.findById(params.id);
            if(rtr.result == 'true'){
                //flash.message = '提交成功了！'
                log.info '提交成功'+params.id;
                redirect(action:'redirect',params:[type:"ok",succcount:tb.batchCount])
            }else if(rtr.result == 'false'){
                // flash.message=rtr.errorMsg
                 log.info '提交失败'+params.id;
                 writeInfoPage '提交失败,'+rtr.errorMsg
            }
        }
        catch(Exception ex){
            writeInfoPage '系统异常,请稍候重试！'
        }


    }
     protected writeInfoPage ={msg,type='error'->
        render view:'boxsucc',model: [type:type,msg:msg]
    }
     def redirect={
        render view:'boxsucc',model: [type:params.type,succcount:params.succcount]
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

    def procsimplededuct ={
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

        if(!boCustomerServices.limitMoney||!boCustomerServices.dayLimitTrans||!boCustomerServices.dayLimitMoney){
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

        // 验证单笔超限
        //def errMsg
        if(Double.parseDouble(params.tradeAmount).doubleValue() > boCustomerServices.limitMoney.doubleValue()){
            errMsg = '单笔代收金额超限额, 请联系管理员!'
            String result = buildJSON(RESP_RESULT_FAIL, errMsg);
            log.info '超限 Result is ' + result
            render(result)
            return
        }
        // 验证当日超限
        def dayLimit = TbAgentpayInfo.createCriteria().get {
            projections {
                sum "batchCount"
                sum "batchAmount"
            }
            eq('batchBizid',cmCustomer.customerNo)
            eq('batchType', 'S')
            eq('batchDate', DateUtils.getDefaultDateBySDF8())
        }
        println "batchLimit ${dayLimit[0]} ${dayLimit[1]}"
        def dayCountLimit = dayLimit[0] == null ? 0 : dayLimit[0].longValue()
        def dayMoneyLimit = dayLimit[1] == null ? 0 : dayLimit[1].doubleValue()
        if(dayCountLimit + 1 > boCustomerServices.dayLimitTrans.longValue()){
            errMsg = Constants.Verify.DETAIL_DAYCOUNTOVER_ERRMSG;
        }
        if(dayMoneyLimit + Double.parseDouble(params.tradeAmount).doubleValue() > boCustomerServices.dayLimitMoney.doubleValue()){
            errMsg = Constants.Verify.DETAIL_DAYAMOUNTOVER_ERRMSG;
        }
        log.info 'check error is ' + errMsg
        if(errMsg){
            String result = buildJSON(RESP_RESULT_FAIL, errMsg);
            log.info 'Result is ' + result
            render(result)
            return
        }

        def ta = TbAgentpayInfo.createCriteria()
        def batch = ta.get {
            projections {
                eq('batchBizid',session.cmCustomer.customerNo)
                eq('batchType','S')
                eq('batchDate', new Date().format('yyyyMMdd'))
                max('batchCurrnum')
            }
        }
        log.info 'batch is === ' + batch

        def aPay = new TbAgentpayInfo()
        aPay.batchBizid = session.cmCustomer.customerNo
        aPay.batchType = 'S'
        aPay.batchVersion = '00'
        aPay.batchDate =  new Date().format('yyyyMMdd')
        aPay.batchCurrnum = batch == null ? '90001':nextSeqNum(batch) //五位自动补0
        aPay.batchBiztype = '00000'
        aPay.batchCount = 1
        aPay.batchAmount = Double.parseDouble(params.tradeAmount)

        def detail = new TbAgentpayDetailsInfo()
        // 代收手续费均不计算
        detail.tradeNum = '000001'
        detail.tradeAmounttype = 'CNY'
        detail.tradeStatus = Constants.Status.ORDER_INIT	// 初始化/待审批
		detail.tradeSubdate = new Date()
        detail.tradeCardname = params.tradeCardname
        detail.tradeCardnum = params.tradeCardnum
        detail.tradeAccountname = params.tradeAccountname
        //detail.tradeRemark = params.tradeProvince + getProvinceUnit(params.tradeProvince) + ';' + params.tradeCity // + '市'
        detail.tradeBranchbank = params.tradeBranchbank
        detail.tradeSubbranchbank = params.tradeSubbranchbank
        detail.tradeAmount = Double.parseDouble(params.tradeAmount)
        detail.tradeAccounttype = params.tradeAccounttype
        detail.contractUsercode = params.contractUsercode
        detail.tradeRemark = params.tradeRemark
        aPay.addToTbAgentpayDetailsInfos(detail)
        def resultBatch
        try{
           resultBatch = agentLoaderService.saveAgentBatch(aPay, boCustomerServices, cmCustomer)
        }catch(Exception ex){
           ex.printStackTrace()
           println '单笔收款系统异常'
           errMsg = ex.getMessage();
           String result = buildJSON(RESP_RESULT_FAIL, errMsg);
           log.info 'Result is ' + result
           render(result)
           return
        }
         log.info 'Save Coll Object Succ.' + resultBatch

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
        def cResult = c.save(flush:true,failOnError:true)
        log.info 'Save Customer Result is ' + cResult

        errMsg = '提交成功！'
        String result = buildJSON(RESP_RESULT_SUCC, errMsg);
        log.info 'Succ Result is ' + result
        render(result)
    }

    def procsimplepay = {
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

        if(!boCustomerServices.limitMoney||!boCustomerServices.dayLimitTrans||!boCustomerServices.dayLimitMoney||!boCustomerServices.backFee||!boCustomerServices.settWay){
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

        // 验证单笔超限
        //def errMsg
        if(Double.parseDouble(params.tradeAmount).doubleValue() > boCustomerServices.limitMoney.doubleValue()){
            errMsg = '单笔代付金额超限额, 请联系管理员！'
            String result = buildJSON(RESP_RESULT_FAIL, errMsg);
            log.info 'Result is ' + result
            render(result)
            return
        }
        // 验证当日超限
        def dayLimit = TbAgentpayInfo.createCriteria().get {
            projections {
                sum "batchCount"
                sum "batchAmount"
            }
            eq('batchBizid',cmCustomer.customerNo)
            eq('batchType', 'F')
            eq('batchDate', DateUtils.getDefaultDateBySDF8())
        }
        println "batchLimit ${dayLimit[0]} ${dayLimit[1]}"
        def dayCountLimit = dayLimit[0] == null ? 0 : dayLimit[0].longValue()
        def dayMoneyLimit = dayLimit[1] == null ? 0 : dayLimit[1].doubleValue()
        if(dayCountLimit + 1 > boCustomerServices.dayLimitTrans.longValue()){
            errMsg = Constants.Verify.DETAIL_DAYCOUNTOVER_ERRMSG;
        }
        if(dayMoneyLimit + Double.parseDouble(params.tradeAmount).doubleValue() > boCustomerServices.dayLimitMoney.doubleValue()){
            errMsg = Constants.Verify.DETAIL_DAYAMOUNTOVER_ERRMSG;
        }

        if(errMsg){
            log.warn 'fount error is ' + errMsg
            String result = buildJSON(RESP_RESULT_FAIL, errMsg);
            log.info 'Result is ' + result
            render(result)
            return
        }

        def aPay = new TbAgentpayInfo()
        aPay.batchBizid = session.cmCustomer.customerNo
        aPay.batchType = 'F'
        aPay.batchVersion = '00'
        aPay.batchDate =  new Date().format('yyyyMMdd')
        aPay.batchCurrnum = batch == null ? '90001':nextSeqNum(batch) //五位自动补0
        aPay.batchBiztype = '00000'
        aPay.batchCount = 1
        println 'boCustomerServices.settWay is ' + boCustomerServices.settWay
        aPay.batchFeetype = boCustomerServices.settWay
        aPay.batchAmount = Double.parseDouble(params.tradeAmount)

        def detail = new TbAgentpayDetailsInfo()
        // 代收手续费均不计算
        detail.tradeNum = '000001'
        detail.tradeAmounttype = 'CNY'
        detail.tradeStatus = Constants.Status.ORDER_INIT	// 初始化/待审批
		detail.tradeSubdate = new Date()

        detail.tradeCardname = params.tradeCardname
        detail.tradeCardnum = params.tradeCardnum
        detail.tradeAccountname = params.tradeAccountname
        //detail.tradeRemark = params.tradeProvince + getProvinceUnit(params.tradeProvince) + ';' + params.tradeCity // + '市'
        detail.tradeBranchbank =params.tradeBranchbank
        detail.tradeSubbranchbank = params.tradeSubbranchbank
        detail.tradeAmount = Double.parseDouble(params.tradeAmount)
        detail.tradeAccounttype = params.tradeAccounttype
        //detail.contractUsercode = params.contractUsercode
        detail.tradeRemark = params.tradeRemark
        // fujia
        // 是否退手续费、结算方式、手续费、实际金额
        // 批次手续费
        detail.tradeFeestyle = boCustomerServices.backFee == '1' ? 'T': null;
        detail.tradeFeetype = boCustomerServices.settWay
        if(detail.tradeAccounttype.equals("0")){
            detail.tradeFee = (boCustomerServices.perprocedureFee * 1).toDouble()
        }else if(detail.tradeAccounttype.equals("1")){
            detail.tradeFee =  (boCustomerServices.procedureFee * 1).toDouble()
        }
        detail.tradeAccamount = new BigDecimal(detail.tradeAmount).add(new BigDecimal(detail.tradeFee)).toDouble()

        aPay.batchFee = BigDecimal.valueOf(aPay.batchFee == null ? 0 : aPay.batchFee).add(BigDecimal.valueOf(detail.tradeFee)).toDouble()
        aPay.addToTbAgentpayDetailsInfos(detail)
        def resultBatch
        try{
           resultBatch = agentLoaderService.saveAgentBatch(aPay, boCustomerServices, cmCustomer)
        }catch(Exception ex){
           ex.printStackTrace()
           println '单笔代付系统异常'
           errMsg = ex.getMessage();
           String result = buildJSON(RESP_RESULT_FAIL, errMsg);
           log.info 'Result is ' + result
           render(result)
           return
        }
         log.info 'Save Pay Object Succ.' + resultBatch

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
        def cResult = c.save(flush:true,failOnError:true)
        log.info 'Save Customer Result is ' + cResult

        errMsg = '提交成功！'
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
            BufferedWriter bw = new BufferedWriter(new FileWriter('D:/root/'+(f.getOriginalFilename().toUpperCase().replaceFirst('.','.TXT'))))
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
                        //log.info c.getContents()
                    }
                    /*for (Cell cell : cs) {
                        line.append(cell.getContents())
                        line.append(",")
                        if(j == 0 && (null == cell.getContents())){
                            break
                        }
                    }*/
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

            /*def br = new BufferedReader(new FileReader(tarFile))
            def line = null;
            def ai = new AtomicInteger(0)
            while((line = br.readLine()) != null){
                log ai.incrementAndGet()
                log line
            }*/
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
        def query=getCustQuery('all')
        def list=TbBizCustomer.createCriteria().list(params,query)
        def count=TbBizCustomer.createCriteria().count(query)
        [tradeList: list, tradeListTotal: count,reqSearch:params.reqSearch,resultSearch:params.resultSearch]
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
