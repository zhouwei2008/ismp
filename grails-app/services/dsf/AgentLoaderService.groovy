package dsf

import com.ecard.products.utils.FileUtils
import com.ecard.products.constants.Constants
import com.ecard.products.utils.Tools
import com.ecard.products.utils.StringUtils
import boss.BoAgentPayServiceParams
import boss.BoInnerAccount
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import groovy.sql.Sql
import ismp.CmCustomer
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.log4j.Logger
import jxl.Workbook
import java.util.concurrent.atomic.AtomicInteger
import jxl.Sheet
import java.text.DecimalFormat
import jxl.Cell

class AgentLoaderService {

    static transactional = true

    def accountClientService

    private Logger logger = Logger.getLogger(AgentLoaderService.class)

	def  loadData(String filePath, BoAgentPayServiceParams boServerParams, CmCustomer customer) {
		// TODO Auto-generated method stub
		String doneFilePath = FileUtils.make2done(filePath)
		def source = new File(doneFilePath)
		def agentpay = new TbAgentpayInfo()
        agentpay.batchFeetype = boServerParams.settWay
        agentpay.batchBizid = customer.customerNo
        log.info 'batchType ************ ' + (boServerParams.serviceCode=='agentpay'?'F':'S')
        agentpay.batchType = boServerParams.serviceCode=='agentpay'?'F':'S'
        agentpay.batchVersion = '00'
        agentpay.batchDate = new Date().format("yyyyMMdd")// date
        agentpay.batchCurrnum = '0'
        def batchFn = source.getName().replaceFirst('(.txt|.xml|.xsl|.TXT|.XML|.XSL)(.done|.DONE)$', "")
        log.info 'batchFn ************ ' + batchFn
        agentpay.batchFilename = batchFn
		//analyseBatch(source.getName(), agentpay)
		BufferedReader br = null
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(source), 'GBK'))
			boolean top = true
			String line = null
			while((line = br.readLine())!=null){
				logger.info("ReadLine: " + line )
				if(top){
					analyseTop(line, agentpay)
					top = false
					continue
				}
                if(line == null || line.equals("")){
                    break
                }
				analyseDetail(line, agentpay, boServerParams)
			}
		} catch (IOException e) {
			logger.error("加载文件" + doneFilePath + "时，出现异常:", e)
            return null
		}finally{
			try {
				br.close()
			} catch (IOException e) {
				logger.error("IO关闭异常", e)
                return null
			}
		}
		//agentpay.setTbAgentpayDetails(details);
		return agentpay;
	}

    //华安修正
    private void analyseDetailExcel(String line, int c, TbAgentpayInfo agentpay, BoAgentPayServiceParams boServerParams) {
		// TODO Auto-generated method stub
		String [] st = line.split(',');
        String businessType = st[0]
        String payCorName = st[1]
        String budgetProName = st[2]
        String tradeCurrDate = st[3]
        String payAccountCode = st[4]

		String num = String.valueOf(1000000+c).substring(1,7);
		String bankCode = st[5]
		String name = st[6]
        String bankOldInfo = st[7]
        //解析 XX行XX分行XX支行
         String bankName
         String branchBank
         String subBank
        String splid = '行'
		StringTokenizer sts = new StringTokenizer(bankOldInfo, splid);
        if(sts.countTokens() == 3){
            bankName = sts.nextToken() + splid
            branchBank = sts.nextToken() + splid
            subBank = sts.nextToken() + splid
        }else{//若是农村信用社,则分行支行为空 2011-11-07 SYW
            bankName = bankOldInfo
        }
        String selfOrCom = name.length()<=4 ? "0" : "1"
        String remark = st[8] + ";" + st[9]
        String orgCode = st[10]
        String interCode = st[11]
        String peopleCode = st[12]
        String payType = st[13]
        String amount = st[14]
        String remark2 = st[15]

		/*String unit = st[8]
		String remark = st[9]
        // 更新 协议号、用途
        String contractUsercode = st[10]
        String remark2 = st[11]*/

		// 赋值
        //def ad = new TbAgentpayDetailsInfo()
        def ad = new TbAgentpayDetailsChildInfo()

		ad.tradeNum = num
		ad.tradeCardnum =bankCode
		ad.tradeCardname = name
		ad.tradeBranchbank = branchBank
		ad.tradeSubbranchbank = subBank
		ad.tradeAccountname = bankName
		ad.tradeAccounttype = selfOrCom
		ad.tradeAmount = new BigDecimal(amount).toDouble()
		ad.tradeAmounttype = 'CNY'
		ad.tradeRemark = remark
		ad.tradeStatus = Constants.Status.ORDER_INIT	// 初始化/待审批
		ad.tradeSubdate = new Date()

        // 更新
        //ad.contractUsercode = contractUsercode
        ad.tradeRemark2 = remark2

        ad.tradeFeestyle = boServerParams.backFee == '1' ? 'T': null;
        ad.tradeFeetype = boServerParams.settWay
        //手续费
        if(selfOrCom.equals("0")){
            ad.tradeFee = (boServerParams.perprocedureFee * 1).toDouble()
        }else if(selfOrCom.equals("1")){
            ad.tradeFee =  (boServerParams.procedureFee * 1).toDouble()
        }
        // 实际金额
        if(boServerParams.serviceCode == 'agentcoll'){
            ad.tradeAccamount = new BigDecimal(Double.toString(ad.tradeAmount)).subtract(new BigDecimal(Double.toString(ad.tradeFee))).toDouble()
        }else if(boServerParams.serviceCode == 'agentpay'){
            ad.tradeAccamount = new BigDecimal(Double.toString(ad.tradeAmount)).add(new BigDecimal(Double.toString(ad.tradeFee))).toDouble()
        }

        //华安明细
        ad.cbusinessType = businessType
        ad.cpayCorname = payCorName
        ad.cbudgetProname = budgetProName
        ad.ctradeCurrdate = tradeCurrDate
        ad.cpayAccountcode = payAccountCode
        ad.corgCode = orgCode
        ad.cinterbankCode = interCode
        ad.cpeoplebankCode = peopleCode
        ad.cpayType = payType

        agentpay.batchFee = new BigDecimal(agentpay.batchFee == null ? '0' : Double.toString(agentpay.batchFee)).add(new BigDecimal(Double.toString(ad.tradeFee))).toDouble()
        agentpay.addToTbAgentpayDetailsInfos(ad)
	}

    //华安修正
    // 100000000001_S0020110607_00001
    private void analyseBatchExcel(String fName, TbAgentpayInfo ap) {
        // TODO Auto-generated method stub

        fName = fName.replaceFirst('(.txt|.xml|.xls|.TXT|.XML|.XLS)$', "")
        StringTokenizer st = new StringTokenizer(fName, "_")
        String bizCode = st.nextToken()
        String svd = st.nextToken()
        String batchNum = st.nextToken()
        String service = null
        String batchVer = null
        String date = null

        String secRex = '(S|F)'
        String dateRex = '\\d{8}$'

        Pattern p = Pattern.compile(secRex)
        Matcher m = p.matcher(svd);
        if (m.find()) {
            service = m.group()
        }
        p = Pattern.compile(dateRex);
        m = p.matcher(svd)
        if (m.find()) {
            date = m.group()
        }
        batchVer = svd.replace(service, "").replace(date, "")
        log.info String.format("华安保险 %s %s %s %s %s", bizCode, service, batchVer, date, batchNum)

        // 赋值
        ap.batchBizid =bizCode
        ap.batchType = service
        ap.batchVersion = batchVer
        ap.batchDate = new Date().format("yyyyMMdd")// date
        ap.batchCurrnum = batchNum
        ap.batchFilename=fName
    }

    //华安修正
    def  loadDataExcel(String filePath, BoAgentPayServiceParams boServerParams, CmCustomer customer) {
		// TODO Auto-generated method stub
		//String doneFilePath = FileUtils.make2done(filePath)
		def source = new File(filePath)
		def agentpay = new TbAgentpayInfo()
        agentpay.batchFeetype = boServerParams.settWay
        agentpay.batchBizid = customer.customerNo
        log.info 'batchType ************ ' + (boServerParams.serviceCode=='agentpay'?'F':'S')
        agentpay.batchType = boServerParams.serviceCode=='agentpay'?'F':'S'
        agentpay.batchVersion = '00'
        agentpay.batchDate = new Date().format("yyyyMMdd")// date
        agentpay.batchCurrnum = '0'
        def batchFn = source.getName().replaceFirst('(.txt|.xml|.xsl|.TXT|.XML|.XSL)(.done|.DONE)$', "")
        log.info 'batchFn ************ ' + batchFn
        agentpay.batchFilename = batchFn
		//analyseBatchExcel(source.getName(), agentpay)

        Workbook wb = null;
		try {
            log.info '文件名字 ： ' + filePath
			wb = Workbook.getWorkbook(new FileInputStream(filePath));
			int size = 16;
			//通过Workbook的getSheets方法获取工作簿集（从0开始）
			Sheet rs = wb.getSheet(0);
			AtomicInteger atomic = new AtomicInteger(0);
			DecimalFormat df = new DecimalFormat("#.##");
			double totalAmount = 0.00;
            log.info 'ROWS is ' + rs.getRows()
			par:for (int j = 0; j < rs.getRows(); j++) {
				// 跳过第一行
				if(j == 0){
					continue par;
				}

                Cell[] cs = rs.getRow(j)
                if(null == cs[0].getContents() || "".equals(cs[0].getContents())){
                        log.info '解析该批次文件结束了！'
                        break par
                }
                StringBuffer line = new StringBuffer();
				// Item
				atomic.getAndIncrement()
				for(int z = 0; z < size && z < cs.length; z++){
					Cell c = cs[z]
                    /*if(null == c.getContents() || "".equals(c.getContents())){
                        log.info '解析该批次文件结束了！'
                        break par
                    }*/
					line.append(c.getContents())
					line.append(",")
					if(z == 14){
						totalAmount = new BigDecimal(Double.toString(totalAmount)).add(new BigDecimal(c.getContents())).doubleValue()
					}
				}
				String rlt = line.length()==0 ? "0" : line.substring(0, line.length()-1)
				log.info("item " + j + " line is " + rlt)
                analyseDetailExcel(rlt, atomic.get(), agentpay, boServerParams)
			}
			//最后不要忘记关闭wb以释放资源：
			wb.close();
			/*logger.info(String.format("Batch[商户号:%s, 类型：%s, 版本：%s, 日期：%s, 批次号：%s, 总数：%d, 总金额：%7$1.2f, 状态：%8$s]",
					bizCode, type, batchVer, batchDate, batchNum, atomic.get(), totalAmount, batchStatus));*/
            agentpay.batchCount = atomic.get()
            agentpay.batchAmount = totalAmount
            agentpay.batchBiztype = '00000' //待定

            // 修正文件名称
            String fDone = FileUtils.make2done(filePath)
            log.info '修改文件为关闭状态文件' + fDone
		} catch (Exception e) {
			logger.error("解析文件" + filePath + "到时出现异常。");
			//改名字
		}
		return agentpay;
	}

    /**
    * 生成批次交易号，17位
    * @return 交易号
    */
    def createBatchNo() {
        def middle = new java.text.SimpleDateFormat('yyMMdd').format(new Date()) // yymmdd
        def ds=DatasourcesUtils.getDataSource('dsf')
        def sql = new Sql(ds)
        //String sql = "SELECT '10'||TO_CHAR(sysdate,'yymmdd')||LPAD(SEQ_AGENTPAY.nextval,5,'0') AS id FROM DUAL";
        def seq = sql.firstRow('SELECT \'10\'||TO_CHAR(sysdate,\'yymmdd\')||LPAD(SEQ_AGENTPAY.nextval,5,\'0\') AS ID FROM DUAL')['ID']
       return seq
    }

    // String sql = "SELECT '10'||TO_CHAR(sysdate,'yymmdd')||LPAD(SEQ_AGENTPAYDETAILS.nextval,7,'0') AS id FROM DUAL";
     def createDetailsNo() {
        def middle = new java.text.SimpleDateFormat('yyMMdd').format(new Date()) // yymmdd
        def ds=DatasourcesUtils.getDataSource('dsf')
        def sql = new Sql(ds)
        //String sql = "SELECT '10'||TO_CHAR(sysdate,'yymmdd')||LPAD(SEQ_AGENTPAY.nextval,5,'0') AS id FROM DUAL";
        def seq = sql.firstRow('SELECT \'10\'||TO_CHAR(sysdate,\'yymmdd\')||LPAD(SEQ_AGENTPAYDETAILS.nextval,7,\'0\') AS ID FROM DUAL')['ID']
       return seq
    }

    def saveAgentBatch(TbAgentpayInfo ap, BoAgentPayServiceParams boCustomerServices, CmCustomer cmCustomer) throws Exception {
        try{
        // 保存代收付对象
        String suffix = Constants.Services.sf.get(ap.getBatchType());
		String batchId = createBatchNo()
		logger.info("批次Id = " + batchId + suffix)
		ap.id = batchId + suffix
		ap.batchStatus = Constants.Status.ORDER_INIT
        ap.tbAgentpayDetailsInfos.each {
            it.id =  createDetailsNo() + suffix
            logger.info("批次明细Id = " + it.id)
            it.batch = ap
            it.tradeStatus = Constants.Status.ORDER_INIT
            it.batchBizid = ap.batchBizid
            it.payStatus = Constants.Status.ORDER_INIT
            it.tradeType = ap.batchType
        }
        // 实际金额
        if(boCustomerServices.serviceCode == Constants.ServiceType.COLLECT_SERVICE){
            //修正
            //ap.batchAccamount = ap.batchFeetype == '0' ? (new BigDecimal(ap.batchAmount).subtract(new BigDecimal(ap.batchFee))).toDouble() : ap.batchAmount
        }else if(boCustomerServices.serviceCode == Constants.ServiceType.PAY_SERVICE){
            ap.batchAccamount = ap.batchFeetype == '0' ? (new BigDecimal(Double.toString(ap.batchAmount)).add(new BigDecimal(Double.toString(ap.batchFee)))).toDouble() : ap.batchAmount
        }
        def aPay = ap.save(failOnError:true)

        // 记账
        // 商户现金账户
        def fAccountNo = cmCustomer.accountNo
        // 商户服务账户
        def tAccountNo = boCustomerServices.srvAccNo
        // 商户手续费账户
        def sAccountNo = boCustomerServices.feeAccNo
        // 平台应收手续费账户
        def sysAccountNo =BoInnerAccount.findByKey('feeInAdvance').accountNo

        def resultList
        def transferResult
        if(boCustomerServices.serviceCode == Constants.ServiceType.COLLECT_SERVICE){
            // 代收记账
            //修正
            /*if(boCustomerServices.settWay.equals("0")){
               println '准备记录即扣手续费得账了'
               resultList = accountClientService.buildTransfer(null, fAccountNo, tAccountNo, aPay.batchFee * 100 as Integer, Constants.ServiceType.FEE_SERVICE,aPay.id, aPay.batchCurrnum, '代收即扣手续费')
               transferResult = accountClientService.batchCommand(UUID.randomUUID().toString().replaceAll("-", ""), resultList)
               println '记完了，检查一下吧'
            }else if(boCustomerServices.settWay.equals("1")){
                println '准备记录后返手续费得账了'
                resultList = accountClientService.buildTransfer(null, sAccountNo, sysAccountNo, aPay.batchFee * 100 as Integer, Constants.ServiceType.FEE_SERVICE,aPay.id, aPay.batchCurrnum, '代收后缴手续费')
                transferResult = accountClientService.batchCommand(UUID.randomUUID().toString().replaceAll("-", ""), resultList);
                println '记完了，检测一下了'
            }
            println 'Result == ' + transferResult
            if(transferResult.result == 'true'){
                println '代收记账成功了'
            }else if(transferResult.result == 'false'){
                println '代收记账失败了'
                throw new RuntimeException('代收记账失败了')
            }*/
         }else if(boCustomerServices.serviceCode == Constants.ServiceType.PAY_SERVICE){
            //代付记账
             if(boCustomerServices.settWay.equals("0")){
               println '准备记录即扣手续费的账了'
               resultList = accountClientService.buildTransfer(null, fAccountNo, tAccountNo, BigDecimal.valueOf(aPay.batchAmount).multiply(100).toBigInteger(), Constants.ServiceType.PAY_SERVICE,aPay.id, aPay.batchCurrnum, '代付')
               BigInteger biFee = BigDecimal.valueOf(aPay.batchFee).multiply(100).toBigInteger()
               if(biFee > 0){
                    resultList = accountClientService.buildTransfer(resultList, fAccountNo, tAccountNo, biFee, Constants.ServiceType.FEE_SERVICE,aPay.id, aPay.batchCurrnum, '代付即扣手续费')
               }
               transferResult = accountClientService.batchCommand(UUID.randomUUID().toString().replaceAll("-", ""), resultList)
               println '记完了，检查一下吧'
            }else if(boCustomerServices.settWay.equals("1")){
                println '准备记录后返手续费的账了'
                resultList = accountClientService.buildTransfer(null, fAccountNo, tAccountNo, BigDecimal.valueOf(aPay.batchAmount).multiply(100).toBigInteger(), Constants.ServiceType.PAY_SERVICE,aPay.id, aPay.batchCurrnum, '代付')
                BigInteger biFee = BigDecimal.valueOf(aPay.batchFee).multiply(100).toBigInteger()
                if(biFee > 0){
                    resultList = accountClientService.buildTransfer(resultList, sAccountNo, sysAccountNo, biFee, Constants.ServiceType.FEE_SERVICE,aPay.id, aPay.batchCurrnum, '代付后缴手续费')
                }
                transferResult = accountClientService.batchCommand(UUID.randomUUID().toString().replaceAll("-", ""), resultList);
                println '记完了，检测一下了'
            }
            println 'Result == ' + transferResult
            if(transferResult.result == 'true'){
                println '代付记账已成功'
            }else if(transferResult.result == 'false'){
                println '代付记账失败'
               throw new RuntimeException(transferResult.errorMsg)
            }
        }
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException('系统异常')
        }
        return true
	}

	// 2,20000,12345
	private void analyseTop(String line, TbAgentpayInfo ap) {
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(line, ",")
		String count = st.nextToken()
		String amount = st.nextToken()
		String bizService = st.nextToken()

		// 赋值
		ap.batchCount = Tools.String2Long(count)
		ap.batchAmount = new BigDecimal(amount).toDouble()
		ap.batchBiztype = bizService
	}

	/**
	 * 000001,11111111111111111111111111111111,祝树民,北京朝阳分行,三元桥支行,中国建设银行,0,5000,CNY,说明
	 */
	public void analyseDetail(String line, TbAgentpayInfo agentpay, BoAgentPayServiceParams boServerParams) {
		// TODO Auto-generated method stub
		String [] st = line.split(',');
//		StringTokenizer st = new StringTokenizer(line,",")
		String num = st[0]
		String bankCode = st[1]
		String name = st[2]
		String branchBank = st[3]
		String subBank = st[4]
		String bankName = st[5]
		String selfOrCom = st[6]
		String amount = st[7]
		String unit = st[8]
		String remark = st[9]

        // 更新 协议号、用途
        String contractUsercode = st[10]
        String remark2 = st[11]

		// 赋值
		def ad = new TbAgentpayDetailsInfo()
		ad.tradeNum = num
		ad.tradeCardnum =bankCode
		ad.tradeCardname = name
		ad.tradeBranchbank = branchBank
		ad.tradeSubbranchbank = subBank
		ad.tradeAccountname = bankName
		ad.tradeAccounttype = selfOrCom
		ad.tradeAmount = new BigDecimal(amount).toDouble()
		ad.tradeAmounttype = StringUtils.isEmpty(unit) ? 'CNY' : unit;
		ad.tradeRemark = remark
		ad.tradeStatus = Constants.Status.ORDER_INIT	// 初始化/待审批
		ad.tradeSubdate = new Date()

        // 更新
        ad.contractUsercode = contractUsercode
        ad.tradeRemark2 = remark2

        //修正
        /*ad.tradeFeestyle = boServerParams.backFee == '1' ? 'T': null;
        ad.tradeFeetype = boServerParams.settWay*/

        //修正
        /*if(selfOrCom.equals("0")){
            ad.tradeFee = (boServerParams.perprocedureFee * 1).toDouble()
        }else if(selfOrCom.equals("1")){
            ad.tradeFee =  (boServerParams.procedureFee * 1).toDouble()
        }*/
        // 实际金额
        if(boServerParams.serviceCode == 'agentcoll'){
            //修正
            //ad.tradeAccamount = new BigDecimal(ad.tradeAmount).subtract(new BigDecimal(ad.tradeFee)).toDouble()
        }else if(boServerParams.serviceCode == 'agentpay'){
            //修正
            ad.tradeFeestyle = boServerParams.backFee == '1' ? 'T': null;
            ad.tradeFeetype = boServerParams.settWay

            //修正
            if(selfOrCom.equals("0")){
                ad.tradeFee = (boServerParams.perprocedureFee * 1).toDouble()
            }else if(selfOrCom.equals("1")){
                ad.tradeFee =  (boServerParams.procedureFee * 1).toDouble()
            }

            ad.tradeAccamount = new BigDecimal(ad.tradeAmount).add(new BigDecimal(ad.tradeFee)).toDouble()

            //修正
            agentpay.batchFee = new BigDecimal(agentpay.batchFee == null ? 0 : agentpay.batchFee).add(new BigDecimal(ad.tradeFee)).toDouble()
        }
        //修正
        // agentpay.batchFee = new BigDecimal(agentpay.batchFee == null ? 0 : agentpay.batchFee).add(new BigDecimal(ad.tradeFee)).toDouble()
        agentpay.addToTbAgentpayDetailsInfos(ad)
	}

    private void parserBatch(String fn, String customer, String service, TbAgentpayInfo ap){
        // 赋值
        ap.batchBizid =customer
        ap.batchType = service
        ap.batchVersion = '00'
        ap.batchDate = new Date().format("yyyyMMdd")// date
        //ap.batchCurrnum = batchNum
        ap.batchFilename = fn
    }
	// 100000000001_S0020110607_00001
    private void analyseBatch(String fName, TbAgentpayInfo ap) {
        // TODO Auto-generated method stub

        fName = fName.replaceFirst('(.txt|.xml|.xsl|.TXT|.XML|.XSL)(.done|.DONE)$', "")
        StringTokenizer st = new StringTokenizer(fName, "_")
        String bizCode = st.nextToken()
        String svd = st.nextToken()
        String batchNum = st.nextToken()
        String service = null
        String batchVer = null
        String date = null

        String secRex = '(S|F)'
        String dateRex = '\\d{8}$'

        Pattern p = Pattern.compile(secRex)
        Matcher m = p.matcher(svd);
        if (m.find()) {
            service = m.group()
        }
        p = Pattern.compile(dateRex);
        m = p.matcher(svd)
        if (m.find()) {
            date = m.group()
        }
        batchVer = svd.replace(service, "").replace(date, "")
        System.out.println(String.format("%s %s %s %s %s", bizCode, service, batchVer, date, batchNum))

        // 赋值
        ap.batchBizid =bizCode
        ap.batchType = service
        ap.batchVersion = batchVer
        ap.batchDate = new Date().format("yyyyMMdd")// date
        ap.batchCurrnum = batchNum
        ap.batchFilename = fName
    }

    // 商户审批拒绝
    def verRefuse(String id, String batchRemark1,CmCustomer cmCustomer) throws Exception{
        def aPay = TbAgentpayInfo.findById(id)
        aPay.batchStatus = Constants.Status.ORDER_REFUSE
        aPay.batchRemark1 = batchRemark1
        def details = TbAgentpayDetailsInfo.findAllByBatch(aPay)
        details.each {
            it.tradeStatus = Constants.Status.ORDER_REFUSE
            it.tradeFeedbackcode = '失败'
            it.tradeReason = batchRemark1
            it.save()
        }
        aPay.save(flash:true)

        if(aPay.batchType == 'F'){
            // 商户现金账户
            def fAccountNo = cmCustomer.accountNo
            // 平台应收手续费账户
            def sysAccountNo =BoInnerAccount.findByKey('feeInAdvance').accountNo

            def boCustomerServices = BoAgentPayServiceParams.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
             // 商户服务账户
            def tAccountNo = boCustomerServices.srvAccNo
            // 商户手续费账户
            def sAccountNo = boCustomerServices.feeAccNo
            def resultList
            def transferResult
            resultList = accountClientService.buildTransfer(null, tAccountNo, fAccountNo, BigDecimal.valueOf(aPay.batchAmount).multiply(100).toBigInteger(), Constants.ServiceType.REFUND_SERVICE,aPay.id, aPay.batchCurrnum, '退代付款')

            BigInteger biFee = BigDecimal.valueOf(aPay.batchFee).multiply(100).toBigInteger()
            if(biFee > 0){
                if(boCustomerServices.settWay.equals("0")){
                    resultList = accountClientService.buildTransfer(resultList, tAccountNo, fAccountNo, biFee, Constants.ServiceType.FEERFD_SERVICE,aPay.id, aPay.batchCurrnum, '代付退即扣手续费')
                }else if(boCustomerServices.settWay.equals("1")){
                   resultList = accountClientService.buildTransfer(resultList, sysAccountNo, sAccountNo, biFee, Constants.ServiceType.FEERFD_SERVICE,aPay.id, aPay.batchCurrnum, '代付退后缴手续费')
                }
            }
            transferResult = accountClientService.batchCommand(UUID.randomUUID().toString().replaceAll("-", ""), resultList);
            println 'Result == ' + transferResult

            if(transferResult.result == 'true'){
                println '代付记账成功了！'
            }else if(transferResult.result == 'false'){
                println '代付记账失败了！'
                throw new RuntimeException('代付记账失败了')
            }
        }

    }
}
