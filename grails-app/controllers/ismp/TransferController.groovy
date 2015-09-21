package ismp

import account.AcAccount

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFCell
import grails.converters.JSON
import ocx.AESWithJCE
import trade.AccountCommandPackage
import account.AcSequential
import account.AcTransaction
import java.text.SimpleDateFormat

class TransferController extends BaseController {
    def beforeInterceptor = [action: this.&checkMobile]
    def transferService
    def operatorService
    def accountClientService

    protected checkMobile = {
        def cmCustomer = CmCustomer.get(session.cmCustomer.id)
        if (cmCustomer.status == 'init') {
            writeInfoPage "用户状态错误，不能做转账操作，请联系吉高客服。"
            return
        }
        if (cmCustomer.status in ['disabled', 'deleted']) {
            writeInfoPage "对不起，您不能做转账操作，请联系吉高客服。", 'warn'
            session.cmCustomer = null
            return
        }
        if (!session.cmCustomerOperator.defaultMobile) {
            writeInfoPage "您还没有绑定手机，不能进行转账操作。"
            return
        }
    }

    /**
     * date：2012-07-10
     * 判断转入账户是否是企业账户
     * @param email 转入账户邮箱
     */
    def getIsComp = {
        def email
        def result = "fail"
        try {
            if (params.toEmail) {
                email = params.toEmail
                //根据转入账户Email查询是否是企业账户
                def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(email)
                if (cmLoginCertificate) {
                    def cmCustomerOperator = cmLoginCertificate?.customerOperator
                    if (cmCustomerOperator) {
                        if (cmCustomerOperator?.customer?.type == 'C') {
                            result = 'success'
                        }
                    }
                }
                render result
            }
        } catch (Exception e) {
            e.printStackTrace()
            writeInfoPage '系统错误，请联系管理员！'
        }
    }

    protected checkPayee(cmLoginCertificate) {
        def res = [result: true, msg: 'ok']
        if (!cmLoginCertificate) {
            log.info "没找到转账对方账户"
            res.result = false
            res.msg = "无效对方用户，请重新输入"
            return res
        }
        def customerOperator_payee = cmLoginCertificate.customerOperator
        //判断对方账户跟自己是否是同一个账户
        def cmCustomer_payee = customerOperator_payee.customer
        if (cmCustomer_payee.id == session.cmCustomer.id) {
            log.info "转账对方账户和己方账户相同"
            res.result = false
            res.msg = "无效对方用户，请重新输入"
            return res
        }
        return res
    }

    protected checkParams(type) {
        def res = [result: true, msg: 'ok']
        if (!params.subject) {
            res.result = false
            res.msg = "请输入付款理由"
            return res
        }
        def amount = 0
        try {
            BigDecimal strAmount = new BigDecimal(params.amount)
            strAmount = strAmount.multiply(new BigDecimal(100))
            log.info "strAmount=" + strAmount
            amount = strAmount as Long
            params._amount = amount
        } catch (Exception e) {
            res.result = false
            res.msg = "无效金额，请重新输入"
            return res
        }
        log.info "amount=" + amount
        /* date:2012-07-10
        * 描述：不同类型的商户，转账金额不同，个人商户最多允许2000元，企业商户最多允许1000000元
        * params:type p:表示个人商户，C表示企业商户
        */
        if (type == "P") {
            if (amount < 1 || amount > 200000) {
                res.result = false
                res.msg = "转账金额错误，请重新输入"
                return res
            }
        } else if (type == "C") {
            if (amount < 1 || amount > 100000000) {
                res.result = false
                res.msg = "转账金额错误，请重新输入"
                return res
            }
        }
        return res
    }

//    def index = {
//        def accountNo=session.cmCustomer.accountNo
//        def acAccount_Main=AcAccount.findByAccountNo(accountNo)
//        [acAccount_Main:acAccount_Main]
//    }





    def getBatchTemplateFile = {
        def filepath = request.getRealPath("/") + "file" + File.separator + "transfer" + File.separator + "multiTransferTemplate.xls"
//        println filepath
        // 下载本地文件
        String fileName = "multiTransferTemplate.xls".toString(); // 文件的默认保存名
        // 读到流中
        InputStream is = new FileInputStream(filepath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        while ((len = is.read(b)) > 0)
            response.getOutputStream().write(b, 0, len);
        is.close();
    }

    def getBatchTemplateReportFile = {
        def reportPath = request.getRealPath("/") + "upload" + File.separator + "batchTransfer" + File.separator + "failReport" + File.separator + "re_" + params.file;
//        println filepath
        // 下载本地文件
        String fileName = "report.xls".toString(); // 文件的默认保存名
        // 读到流中
        InputStream is = new FileInputStream(reportPath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        while ((len = is.read(b)) > 0)
            response.getOutputStream().write(b, 0, len);
        is.close();
    }

    def getBatchTemplateErrorFile = {
        def reportPath = request.getRealPath("/") + "upload" + File.separator + "batchTransfer" + File.separator + "errorReport" + File.separator + "fail_" + params.file;
//        println filepath
        // 下载本地文件
        String fileName = "errorReport.xls".toString(); // 文件的默认保存名
        // 读到流中
        InputStream is = new FileInputStream(reportPath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        while ((len = is.read(b)) > 0)
            response.getOutputStream().write(b, 0, len);
        is.close();
    }


    def getPayeeInfo = {
        def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(params.to)
        def res = checkPayee(cmLoginCertificate)
        if (res.result == true)
            render cmLoginCertificate.customerOperator.name
        else
            render res.msg
    }

    def list = {
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query = {
            eq('payerId', session.cmCustomer.id)
//            eq('tradeType', 'transfer')

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
//            }
//            if (params.endDate) {
//                le('dateCreated', Date.parse("yyyy-MM-dd", params.endDate) + 1)
//            }
//            amt_s" class="right_top_h2_input" size="10"/>
//                  小于：<g:textField name="amt_s" class="right_top_h2_input" size="10"/>
//            </h2>
//            <h2>
//                流水号：<g:textField name="flow_No" class="right_top_h2_input" size="10"/>
//                转入账户：<g:textField name="to_Account
            if (params.amt_s) {
                def amount = 0
                try {
                    BigDecimal strAmount = new BigDecimal(params.amt_s)
                    strAmount = strAmount.multiply(new BigDecimal(100))
                    log.info "amountMin=" + strAmount
                    amount = strAmount as Long
                } catch (Exception e) {
                    writeInfoPage "无效金额，请重新输入"
                    return
                }
                ge('amount', amount)

            }
            if (params.amt_b) {
                def amount = 0
                try {
                    BigDecimal strAmount = new BigDecimal(params.amt_b)
                    strAmount = strAmount.multiply(new BigDecimal(100))
                    log.info "amountMax=" + strAmount
                    amount = strAmount as Long
                } catch (Exception e) {
                    writeInfoPage "无效金额，请重新输入"
                    return
                }
                le('amount', amount)
            }
            if (params.flag == "1") {
                if (params.flow_No) {
                    eq('tradeNo', params.flow_No)
                }
            } else if (params.flag == "2") {
                if (params.outTrade_No) {
                    eq('outTradeNo', params.outTrade_No)
                }
            }


            if (params.to_Account) {

                eq('payeeCode', params.to_Account)
            }
        }
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)

        if (params?.format && params.format in ["excel"]) {
            params.offset = null
            params.max = null
            def list = TradeTransfer.createCriteria().list(params, query)
            def count = TradeTransfer.createCriteria().count(query)
            def totalamount = list.sum {it.amount}
            def filename = 'Excel-' + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + '.xls'
            response.setHeader("Content-disposition", "attachment; filename=" + filename)
            response.setCharacterEncoding("GBK")
            response.contentType = "application/x-rarx-rar-compressed"
            render(template: "tpl_${params.format}_search", model: [tradeList: list, tradeListTotal: count, totalamount: totalamount, acAccount_Main: acAccount_Main])
        } else {
            def list = TradeTransfer.createCriteria().list(params, query)
            def count = TradeTransfer.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count, acAccount_Main: acAccount_Main]
        }

//        def list = TradeBase.createCriteria().list(params, query)
//        def count = TradeBase.createCriteria().count(query)
//
//        [tradeList: list, tradeListTotal: count,acAccount_Main:acAccount_Main]
    }
    def check = {
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query = {
            eq('payerId', session.cmCustomer.id)
//            eq('tradeType', 'transfer')

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
//            }
//            if (params.endDate) {
//                le('dateCreated', Date.parse("yyyy-MM-dd", params.endDate) + 1)
//            }
//            amt_s" class="right_top_h2_input" size="10"/>
//                  小于：<g:textField name="amt_s" class="right_top_h2_input" size="10"/>
//            </h2>
//            <h2>
//                流水号：<g:textField name="flow_No" class="right_top_h2_input" size="10"/>
//                转入账户：<g:textField name="to_Account
            if (params.amt_s) {
                def amount = 0
                try {
                    BigDecimal strAmount = new BigDecimal(params.amt_s)
                    strAmount = strAmount.multiply(new BigDecimal(100))
                    log.info "amountMin=" + strAmount
                    amount = strAmount as Long
                } catch (Exception e) {
                    writeInfoPage "无效金额，请重新输入"
                    return
                }
                ge('amount', amount)

            }
            if (params.amt_b) {
                def amount = 0
                try {
                    BigDecimal strAmount = new BigDecimal(params.amt_b)
                    strAmount = strAmount.multiply(new BigDecimal(100))
                    log.info "amountMax=" + strAmount
                    amount = strAmount as Long
                } catch (Exception e) {
                    writeInfoPage "无效金额，请重新输入"
                    return
                }
                le('amount', amount)
            }
            if (params.flag == "1") {
                if (params.flow_No) {
                    eq('tradeNo', params.flow_No)
                }
            } else if (params.flag == "2") {
                if (params.outTrade_No) {
                    eq('outTradeNo', params.outTrade_No)
                }
            }


            if (params.to_Account) {

                eq('payeeCode', params.to_Account)
            }
            eq('transferModel', 'open')
            eq('status', 'starting')
        }
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)

        if (params?.format && params.format in ["excel"]) {
            params.offset = null
            params.max = null
            def list = TradeTransfer.createCriteria().list(params, query)
            def count = TradeTransfer.createCriteria().count(query)
            def totalamount = list.sum {it.amount}
            def filename = 'Excel-' + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + '.xls'
            response.setHeader("Content-disposition", "attachment; filename=" + filename)
            response.setCharacterEncoding("GBK")
            response.contentType = "application/x-rarx-rar-compressed"
            render(template: "tpl_${params.format}_search", model: [tradeList: list, tradeListTotal: count, totalamount: totalamount, acAccount_Main: acAccount_Main])
        } else {
            def list = TradeTransfer.createCriteria().list(params, query)
            def count = TradeTransfer.createCriteria().count(query)
            [tradeList: list, tradeListTotal: count, acAccount_Main: acAccount_Main]
        }

//        def list = TradeBase.createCriteria().list(params, query)
//        def count = TradeBase.createCriteria().count(query)
//
//        [tradeList: list, tradeListTotal: count,acAccount_Main:acAccount_Main]
    }
    //转账详细 调用买入交易中的转账详细页面
    def detail = {
        redirect(controller: "trade", action: "detail", params: [id: params.id])
    }
    //转账审批详细
    def checkDetail = {
        if (!params.id) {
            writeInfoPage "该交易不存在"
            return
        }
        def trade = TradeBase.get(params.id)
        if (!trade || (trade.payerId != session.cmCustomer.id && (trade.payeeId != session.cmCustomer.id))) {
            writeInfoPage "没找到该交易"
            return
        }
        [trade: trade]
    }
    /**
     * date：2012-07-05
     * 转账审批通过
     * @param id 转账交易id
     */
    def checkPass = {
        def id = params.id
        def list = new ArrayList()
        list = id.split(',')
        //成功条数
        def succCount = 0
        //失败条数
        def failCount = 0
        //失败提示
        def failReson = ''
        if (list.size() > 0) {       //获取审批通过每一个需要审批的id
            list.each {
                def tradeAccountCommandSaf = TradeAccountCommandSaf.findByTradeId(it)
                if (tradeAccountCommandSaf) {
                    def tradeBase = TradeBase.get(it)
                    if (!tradeBase) {
                        failCount++
                        failReson = failReson + failCount + '、转账交易不存在;'
                    } else {
                        //查询账务流水，如果存在改变转账交易状态为完成，
                        def acAccount = AcTransaction.findAllByTradeNo(tradeBase?.tradeNo)
                        if (acAccount.size() > 0) {
                            tradeBase.status = 'completed'
                            tradeBase.save flash: true, flush: true
                            succCount++
                        } else {
                            def commandNo = tradeAccountCommandSaf.commandNo
                            def acPackage = AccountCommandPackage.findByCommandNo(commandNo)
                            log.info "account command package: $acPackage.commandList"
                            if (!acPackage) {
                                failCount++
                                failReson = failReson + failCount + '、转账交易不存在;'
                            } else {
                                try {
                                    def resp = accountClientService.executeCommands(acPackage)
                                    if (!resp) {
                                        failCount++
                                        failReson = failReson + failCount + '、网络连接错误;'
                                    } else {
                                        if (resp.result) {  //转账成功
                                            tradeBase.status = 'completed'
                                            tradeBase.save flash: true, flush: true
                                            succCount++
                                        } else {  //转账失败
                                            log.info resp
                                            tradeBase.status = 'colsed'
                                            tradeBase.save flash: true, flush: true
                                            failCount++
                                            failReson = failReson + failCount + '、转账交易流水号为：' + tradeBase.tradeNo + '审批失败;'
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace()
                                    failCount++
                                    failReson = failReson + failCount + '、转账审批失败;'
                                }
                            }
                        }
                    }
                } else {
                    failCount++
                    failReson = failReson + failCount + '、转账交易不存在;'
                }
            }
            //转账审批笔数
            def transferCount = succCount + failCount
            render view: '/error', model: [type: 'ok', succTransferCount: succCount, failTransferCount: failCount, failReson: failReson, totalCount: transferCount]
        } else {
            writeInfoPage "转账交易不存在！"
        }
    }
    /**
     * date：2012-07-05
     * 转账审批拒绝
     * @param id 转账交易id
     */
    def checkRefuse = {
        try {
            if (params.id) {
                def id = params.id
                //取得交易，并修改状态
                def tradeBase = TradeBase.get(id)
                if (tradeBase) {
                    tradeBase.status = 'closed'
                    tradeBase.save flash: true, flush: true
                    writeInfoPage "审核拒绝成功", "ok"
                } else {
                    writeInfoPage "转账交易不存在，审核失败！"
                }
            } else {
                writeInfoPage "转账交易不存在，审核失败！"
            }
        } catch (Exception e) {
            e.printStackTrace()
            writeInfoPage "转账审核异常，请稍后再试！"
        }
    }

    def index = {
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        [acAccount_Main: acAccount_Main]
    }

    def step2 = {

        params.commentText = params.comment
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        //if (!withForm {true}.invalidToken {false}) {writeInfoPage "请不要重复提交数据" ;return}
        //检查页面验证码
        if (grailsApplication.config.verifyCaptcha != 'false' && !session.captcha?.isCorrect(params.captcha.toUpperCase())) {
            session.captcha = null
//            writeInfoPage '验证码不正确，请重新输入'
            flash.message = '验证码不正确，请重新输入'
            println '--------------' + params.to
            println '--------------' + params.comment
            render(view: 'index', model: [acAccount_Main: acAccount_Main])
            return
        }
        session.captcha = null
        def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(params.to)
        def res = checkPayee(cmLoginCertificate)
        if (res.result == false) {
//            writeInfoPage res.msg
            flash.message = res.msg
            render(view: 'index', model: [acAccount_Main: acAccount_Main])
            return
        }
        def customerOperator_payee = cmLoginCertificate.customerOperator
        if (!customerOperator_payee) {
//            writeInfoPage "无效对方用户，请重新输入！"
            flash.message = '无效对方用户，请重新输入'
            render(view: 'index', model: [acAccount_Main: acAccount_Main])
            return
        }
        /*  date:2012-07-10
        * 描述：是否是企业用户
        * params：type 商户类型，C：表示公司 P：表示个人
        */
        def type = customerOperator_payee?.customer?.type
        if(!type){
//            writeInfoPage "无效对方用户，请重新输入！"
            flash.message = '无效对方用户，请重新输入'
            render(view: 'index', model: [acAccount_Main: acAccount_Main])
            return
        }
        res = checkParams(type)
        if (res.result == false) {
//            writeInfoPage res.msg
            flash.message = res.msg
            render(view: 'index', model: [acAccount_Main: acAccount_Main])
            return
        }
        log.info params._amount
        //发送手机验证码
//        def mobile_captcha = KeyUtils.getRandNumberKey(6)
//        def content = '转账确认：您本次转账操作的验证码是' + mobile_captcha + '。'
//        def cmCustomer_payer = session.cmCustomer
//        def cmCustomerOperator_payer = session.cmCustomerOperator
//       operatorService.sendMobileCaptcha(cmCustomer_payer,cmCustomerOperator_payer,cmCustomerOperator_payer.defaultMobile,content,mobile_captcha,'transfer')

//        def accountNo = cmCustomer_payer.accountNo
//        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        [acAccount_Main: acAccount_Main, customerOperator_payee: customerOperator_payee]
    }

    def sendMobileCaptcha = {
        def mobile_captcha = KeyUtils.getRandNumberKey(6)
        def content = '转账确认：您本次转账操作的验证码是' + mobile_captcha + '。【吉高】'
        def cmCustomer_payer = session.cmCustomer
        def cmCustomerOperator_payer = session.cmCustomerOperator
        operatorService.sendMobileCaptcha(cmCustomer_payer, cmCustomerOperator_payer, cmCustomerOperator_payer.defaultMobile, content, mobile_captcha, 'transfer')
        render "ok"
    }

    def checkMobileCaptcha = {
        def customerOperator_payer = session.cmCustomerOperator;
        def res = [result: true, msg: "ok!"];
        def query = {
            eq('useType', 'transfer')
            eq('parameter', customerOperator_payer.id as String)
            eq('sendType', 'mobile')
            eq('isUsed', false)
            ge("timeExpired", new Date())
        }
        def cmDynamicKeyList = CmDynamicKey.createCriteria().list([sort: "id", order: 'desc'], query)
        def cmDynamicKey = (cmDynamicKeyList && cmDynamicKeyList.size() > 0) ? cmDynamicKeyList.first() : null
        if (grailsApplication.config.verifyCaptcha != 'false' && ((!cmDynamicKey) || (params.mobile_captcha != cmDynamicKey.verification))) {
            res.result = false;
            res.msg = "手机校验码错误，请重新获得手机校验码！"
            render res as JSON
        } else {
            render res as JSON
        }
    }

    def checkPayPassCaptcha = {
        def customerOperator_payer = session.cmCustomerOperator;
        def res = [result: true, msg: "ok!"];
        //解密加密的支付密码
//        def mcrypt_key_1 = (String) session.getAttribute("mcrypt_key");
//        params.paypass = AESWithJCE.getResult(mcrypt_key_1, params.paypass);
//        session.removeAttribute("mcrypt_key");
        def str_pass = (customerOperator_payer.id + 'p' + params.paypass).encodeAsSHA1()
        if (customerOperator_payer.payPassword != str_pass) {
            res.result = false;
            res.msg = "支付密码错误，请输入正确的支付密码！"
            render res as JSON
        } else {
            render res as JSON
        }
    }

    def save = {
        if (!withForm {true}.invalidToken {false}) {writeInfoPage "请不要重复提交数据"; return}
        def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(params.to)
        def res = checkPayee(cmLoginCertificate)
        if (!res.result) {
            writeInfoPage res.msg
            return
        }
        def customerOperator_payer = session.cmCustomerOperator
        if(!cmLoginCertificate){
            writeInfoPage "无效对方用户，请重新输入！"
            return
        }
        /*  date:2012-07-10
        * 描述：是否是企业用户
        * params：type 商户类型，C：表示公司 P：表示个人
        */
        def type = cmLoginCertificate?.customerOperator?.customer?.type
        if(!type){
            writeInfoPage "无效对方用户，请重新输入！"
            return
        }
        res = checkParams(type)
        if (!res.result) {
            writeInfoPage res.msg
            return
        }
        def amount = params._amount
        //解密加密的支付密码
//        def mcrypt_key_1 = (String) session.getAttribute("mcrypt_key");
//        params.paypass = AESWithJCE.getResult(mcrypt_key_1, params.paypass);
//        session.removeAttribute("mcrypt_key");
        //检查支付密码
        if (!params.paypass) {
            writeInfoPage "请输入支付密码"
            return
        }
        def str_pass = (customerOperator_payer.id + 'p' + params.paypass).encodeAsSHA1()
        if (customerOperator_payer.payPassword != str_pass) {
            writeInfoPage "支付密码错误"
            return
        }
        //检查手机校验码
        def query = {
            eq('useType', 'transfer')
            eq('parameter', customerOperator_payer.id as String)
            eq('sendType', 'mobile')
            eq('isUsed', false)
            ge("timeExpired", new Date())
        }
        def cmDynamicKeyList = CmDynamicKey.createCriteria().list([sort: "id", order: 'desc'], query)
        def cmDynamicKey = (cmDynamicKeyList && cmDynamicKeyList.size() > 0) ? cmDynamicKeyList.first() : null
        if (grailsApplication.config.verifyCaptcha != 'false' && ((!cmDynamicKey) || (params.mobile_captcha != cmDynamicKey.verification))) {
            writeInfoPage "手机验证码错误，请重新输入"
            return
        }
        try {
            def ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
            //返回值状态有open、true、false，open表示需要审批，true表示不需要审批，且转账成功，false表示转账失败
            def msgreturn = transferService.transfer(cmDynamicKey, session.cmLoginCertificate, customerOperator_payer, cmLoginCertificate, amount, 'transfer', params.subject, params.comment, ip)
            if (msgreturn == 'open') {
                writeInfoPage "转账申请成功，请等待审核", 'ok'
            } else if (msgreturn) {
                writeInfoPage "转账成功", 'ok'
            } else {
                log.info msgreturn
                writeInfoPage "转账失败"
            }
        } catch (Exception e) {
            e.printStackTrace()
            writeInfoPage "转账失败"
        }
    }

    def batchStep1 = {
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        [acAccount_Main: acAccount_Main]
    }

    def batchStep2 = {
        try {
            def scount = 0//成功笔数
            double stotalMoney = 0//成功总金额
            def fcount = 0//失败笔数
            double ftotalMoney = 0//失败总金额
            List sList = new ArrayList()//成功记录,记到数据库
            List fList = new ArrayList() //失败记录，供下载用

            LinkedHashMap res = [t_result: true, t_msg: 'ok', dt_result: true, file: "", suc_count: 0, suc_total: 0, suc_tLeft: 0, cellList: new ArrayList()]

            def cmCustomer_payer = session.cmCustomer;
            def accountNo = cmCustomer_payer.accountNo;
            def acAccount_Main = AcAccount.findByAccountNo(accountNo);
            uploadFile(acAccount_Main.balance, res);

            if (!res.t_result) {
                if (!res.dt_result) {
                    makeReport(res);
                }
            }
//            render res as JSON;
            res.suc_tLeft = (acAccount_Main.balance - res.suc_total);
            [acAccount_Main: acAccount_Main, transferCheck: res]
//            println "here!"
//            writeInfoPage "转账成功",'ok'
        } catch (Exception e) {
            e.printStackTrace()
            // writeInfoPage "数据错误，请检查数据正确性"
            writeInfoPage "上传失败，请确认上传文件中格式跟模板一样，并且有相应数据！"
            return
        }
    }



    def batchStep3 = {
        def res = [result: true, msg: 'ok']
        //检查页面验证码
        if (grailsApplication.config.verifyCaptcha != 'false' && !session.captcha?.isCorrect(params.code.toUpperCase())) {
            session.captcha = null;
            res.result = false;
            res.msg = "校验码错误，请重新输入！";
            render res as JSON;
        } else {
            session.captcha = null
            log.info params._amount
            //发送手机验证码
//            def mobile_captcha=KeyUtils.getRandNumberKey(6)
//            def content='转账确认：您本次转账操作的验证码是'+mobile_captcha+'。'
//            def cmCustomer_payer=session.cmCustomer
//            def cmCustomerOperator_payer=session.cmCustomerOperator
//            operatorService.sendMobileCaptcha(cmCustomer_payer,cmCustomerOperator_payer,cmCustomerOperator_payer.defaultMobile,content,mobile_captcha,'transfer')

            render res as JSON;
        }
    }

    def batchStep4 = {
//        def scount = 0//成功笔数
//        double  stotalMoney = 0//成功总金额
//        def  fcount = 0//失败笔数
//        double  ftotalMoney = 0//失败总金额
//        List sList = new ArrayList()//成功记录,记到数据库
//        List  fList = new ArrayList() //失败记录，供下载用
        //解密加密的支付密码
        def filename = request.getRealPath("/") + "upload" + File.separator + "batchTransfer" + File.separator + params.upname
        if (!params.paypass) {
            writeInfoPage "请输入支付密码"
            File temp = new File(filename);
            if (temp.exists()) {
                deleteFile(filename);
            }
            return
        }
        def failFile = params.upname;
        def ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
        def cmLoginCertificate_payer = session.cmLoginCertificate;
        def customerOperator_payer = session.cmCustomerOperator;
        def res = [file: filename, IP: ip, payer: cmLoginCertificate_payer, payer_op: customerOperator_payer, result: true, msg: 'ok', sucNum: 0, sucMoney: 0, sucList: new ArrayList(), failNum: 0, failMoney: 0, failList: new ArrayList(), failFile: failFile]

//        def str_pass=(customerOperator_payer.id+'p'+params.paypass).encodeAsSHA1()
//        if(customerOperator_payer.payPassword!=str_pass){
//            writeInfoPage "支付密码错误"
//            File temp = new File(filename);
//            if(temp.exists()){
//                deleteFile(filename);
//            }
//            return
//        }

        //检查手机验证码
//        def query={
//            eq('useType','transfer')
//            eq('parameter',customerOperator_payer.id as String)
//            eq('sendType','mobile')
//            eq('isUsed',false)
//            ge("timeExpired",new Date())
//        }
//        def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
//        def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
//        if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha!=cmDynamicKey.verification))){
//            writeInfoPage "手机验证码错误，请重新输入"
//            File temp = new File(filename);
//            if(temp.exists()){
//                deleteFile(filename);
//            }
//            return
//        }
        dealFile(res);
        if (res.result == 'open') {
            writeInfoPage "转账申请成功,请等待审核！", 'ok'
        } else if (res.result) {
            writeInfoPage "转账成功！", 'ok'
        }
        def accountNo = session.cmCustomer.accountNo
        def acAccount_Main = AcAccount.findByAccountNo(accountNo)
        [acAccount_Main: acAccount_Main, transferResult: res]
    }



    protected uploadFile(balance, res) {

        if (request instanceof MultipartHttpServletRequest) {

            InputStream is = null
            def maxFileSize = 10485760
            def resultmsg
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
            CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("uploadNew")
            // 判断是否上传文件
            if (!orginalFile.isEmpty()) {
//                     println "orginalFile.getSize():"+orginalFile.getSize()
                if (orginalFile.getSize() < maxFileSize) {
                    is = orginalFile.getInputStream()
                    //判断当前文件的版本xls,xlxs
                    String originalFilename = orginalFile.getOriginalFilename()
                    // 获取上传文件扩展名
                    def extension = originalFilename.substring(originalFilename.indexOf(".") + 1)
                    //上传文件
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmssssss")
                    def name = sdf.format(new Date()) + "." + extension
                    res.file = name
                    def filepath = request.getRealPath("/") + "upload" + File.separator + "batchTransfer" + File.separator
                    def filename = filepath + name
                    if (!new File(filepath).exists()) {
                        new File(filepath).mkdir()
                    }
//                    orginalFile.transferTo(new File(filename))

                    writeFile(is, new File(filename))

                    //格式校验
                    if (extension.toUpperCase().equals("XLS")) {
                        this.getXls(new FileInputStream(new File(filename)), res)
                    } else {
////                                println filename
                        this.getXlsx(new FileInputStream(new File(filename)), res)
//
                    }

                    if (res.t_result) {
                        if (balance < res.suc_total) {
                            res.t_result = false;
                            res.t_msg = "批量转账的总金额超过账户余额！";
                        }
                    }
                    //处理校验结果
                    if (!res.t_result) {
//                        res.file = "";
                        deleteFile(filename);
                    }
//                    return list
                } else {
                    res.t_result = false;
                    res.t_msg = "上传文件不能大于10M！"
                }
            } else {
                res.t_result = false;
                res.t_msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
            }
        }
    }

    def makeReport(res) {
        def filepath = request.getRealPath("/") + "file" + File.separator + "transfer" + File.separator + "multiTransferTemplate.xls"
        // 下载本地文件
        // 读到流中
        InputStream is = new FileInputStream(filepath);// 文件的存放路径
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(is)
            HSSFSheet xSheet = workbook.getSheetAt(0)
//            List li = new ArrayList();
            HSSFRow xRow = xSheet.getRow(8)
            HSSFCell ptrCell = xRow.createCell(6);
            ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
            ptrCell.setCellValue("错误原因");

            //循环行Row
            for (int i = 0; i < res.cellList.size(); i++) {
                LineInfo ptrLInfo = res.cellList.get(i);
                def rowNum = i + 9;
                xRow = xSheet.createRow(rowNum);

                ptrCell = xRow.createCell(0);//序号
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue(String.valueOf(i + 1));

                ptrCell = xRow.createCell(1);//商户订单号
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue(ptrLInfo.out_T_No);

                ptrCell = xRow.createCell(2);//转入账户
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue(ptrLInfo.account);

                ptrCell = xRow.createCell(3);//转账金额
                ptrCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                ptrCell.setCellValue(ptrLInfo.money / 100);

                ptrCell = xRow.createCell(4);//转账原因
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue(ptrLInfo.title);

                ptrCell = xRow.createCell(5);//客户备注
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue(ptrLInfo.note);

                ptrCell = xRow.createCell(6);//错误原因
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue(ptrLInfo.msg);

            }
            def reportPath = request.getRealPath("/") + "upload" + File.separator + "batchTransfer" + File.separator + "failReport" + File.separator;
            if (!new File(reportPath).exists()) {
                new File(reportPath).mkdir()
            }
            workbook.write(new FileOutputStream(new File(reportPath + "re_" + res.file)));
        } catch (Exception e) {
            e.printStackTrace()
//            res.result=false
//            res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
            return null;
        }

    }

    def writeFile(is, file) {
        FileOutputStream output = new FileOutputStream(file);
        byte[] b = new byte[1024 * 4];
        int len;
        while ((len = is.read(b)) != -1) {
            output.write(b, 0, len);
        }
        output.flush();
        output.close();
        is.close();
    }

    def deleteFile(String name) {
        File fs = new File(name);
        if (fs.exists()) {
            fs.delete()
        }
    }

    class LineInfo {

        String out_T_No = ''
        String account = ''
        long money = -0
        String title = ''
        String note = ''

        Boolean isRight = true
        Boolean isBlank = false
        String msg = ''

        def lineCheck(cmap) {
            if (!out_T_No && !account && !money && !title && !note) {
                isBlank = true
                isRight = false
            } else {
                if (out_T_No) {
                    if (out_T_No.length() > 32) {
                        isRight = false
                        msg = msg + '商户订单号不能超过32个字符！'
                    } else {
                        for (def i = 0; i < out_T_No.length(); i++) {
                            def t = out_T_No[i]
                            if (!(t in cmap)) {
                                isRight = false
                                msg = msg + '商户订单号只能由数字、字母、下划线、中划线组成！'
                                break
                            }
                        }
                    }
                }

                if (account) {
                    def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(account)
                    def res = checkPayee(cmLoginCertificate)
                    if (!res.result) {
                        isRight = false;
                        msg = msg + res.msg + "!";
                    }
                } else {
                    isRight = false
                    msg = msg + '转入账户不能为空！'
                }

                if (money) {
                    if (money > 200000) {
                        isRight = false
                        msg = msg + '金额单笔不能超过￥2,000.00元！'
                    }
                } else {
                    isRight = false
                    msg = msg + '金额不能为空，不能为0或负数，且最小为0.01元！'
                }

                if (title) {
                    if (title.length() > 128) {
                        isRight = false
                        msg = msg + '转账原因不能超过128个字符！'
                    }
                } else {
                    isRight = false
                    msg = msg + '转账原因不能为空！'
                }

                if (note) {
                    if (note.length() > 128) {
                        isRight = false
                        msg = msg + '客户备注不能超过128个字符！'
                    }
                }
            }

        }
    }



    def getLong(num) {
        BigDecimal strAmount = new BigDecimal(String.valueOf(num))
        strAmount = strAmount.multiply(new BigDecimal("100"))
        return strAmount as Long
    }

    def getXls(InputStream is, res) {
        def cmap = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                '-', '_']

        try {
            HSSFWorkbook workbook = new HSSFWorkbook(is)
            HSSFSheet xSheet = workbook.getSheetAt(0)
            if (xSheet == null) {
                res.t_result = false
                res.t_msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
                return null;
            }

            //            List li = new ArrayList();
            //数据金额
            Long noInTemp = 0
            HSSFRow noRow = xSheet.getRow(6);
            if (noRow) {
                HSSFCell noCell = noRow.getCell(2);
                if (noCell) {
                    if (noCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                        res.t_result = false
                        res.t_msg = "上传文件中，转账总金额不得是公式！"
                        return null;
                    } else if (noCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        noInTemp = getLong(noCell.getNumericCellValue())
                    } else {
                        res.t_result = false
                        res.t_msg = "上传文件中，转账总金额必须是数字！"
                        return null;
                    }
                } else {
                    res.t_result = false
                    res.t_msg = "请确认上传文件中转账总金额为空！"
                    return null;
                }
            } else {
                res.t_result = false
                res.t_msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
                return null;
            }
            //数据条数
            if (xSheet.getLastRowNum() > 3009) {
                res.t_result = false
                res.t_msg = "文件数据超过3000条！请把数据拆分成几个文件，分次上传！"
                return null;
            }

            def cmLoginCertificate
            def rst
            //循环行Row
            for (int rowNum = 9; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                HSSFRow xRow = xSheet.getRow(rowNum)
                if (xRow) {
                    //                    循环列Cell
                    //                    int CELL_TYPE_NUMERIC = 0;
                    //                    int CELL_TYPE_STRING = 1;
                    //                    int CELL_TYPE_FORMULA = 2;
                    //                    int CELL_TYPE_BLANK = 3;
                    //                    int CELL_TYPE_BOOLEAN = 4;
                    //                    int CELL_TYPE_ERROR = 5;
                    LineInfo cell = new LineInfo();
                    HSSFCell ptrXCell = xRow.getCell(1);
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            cell.out_T_No = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.out_T_No = String.valueOf(ptrn).trim();
                        }
                    }

                    ptrXCell = xRow.getCell(2); //转入账户
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            cell.account = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.account = String.valueOf(ptrn).trim();
                        }
                    }

                    ptrXCell = xRow.getCell(3);//转账金额
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            cell.money = getLong(ptrXCell.getNumericCellValue())
                        }
                    }


                    ptrXCell = xRow.getCell(4);
                    if (ptrXCell) {//转账原因
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            cell.title = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.title = String.valueOf(ptrn).trim();
                        }
                    }

                    ptrXCell = xRow.getCell(5);
                    if (ptrXCell) {//客户备注
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            cell.note = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.note = String.valueOf(ptrn).trim();
                        }
                    }
                    cell.lineCheck(cmap);
                    if (!cell.isBlank) {
                        if (cell.isRight) {
                            res.suc_count = res.suc_count + 1
                            res.suc_total = res.suc_total + cell.money
                        } else {
                            res.t_result = false
                            res.dt_result = false
                        }
                        res.cellList.add(cell);
                    } else {
                        break;
                    }
                }
            }
            if (res.t_result) {
                if (noInTemp != res.suc_total) {
                    res.t_result = false
                    res.t_msg = "上传文件中标明转账总金额是：" + noInTemp / 100 + "元，实际总金额是：" + res.suc_total / 100 + "元，请核对文件中的转账总金额。注意：文件中不能有空行！"
                    return null;
                }
            } else {
                res.t_msg = "部分数据出错，请下载详细的数据校验报告！";
            }
        } catch (Exception e) {
            e.printStackTrace()
            res.result = false
            res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
            return null;
        }
    }

    def getXlsx(InputStream is, res) {
        def cmap = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                '-', '_']
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(is)
            XSSFSheet xSheet = xwb.getSheetAt(0)
            if (xSheet == null) {
                res.result = false
                res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
                return null;
            }

            //数据金额
            Long noInTemp = 0
            XSSFRow noRow = xSheet.getRow(6);
            if (noRow) {
                XSSFCell noCell = noRow.getCell(2);
                if (noCell) {
                    if (noCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
                        res.t_result = false
                        res.t_msg = "上传文件中，转账总金额不得是公式！"
                        return null;
                    } else if (noCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        noInTemp = getLong(noCell.getNumericCellValue())
                    } else {
                        res.t_result = false
                        res.t_msg = "上传文件中，转账总金额必须是数字！"
                        return null;
                    }
                } else {
                    res.t_result = false
                    res.t_msg = "请确认上传文件中转账总金额为空！"
                    return null;
                }
            } else {
                res.t_result = false
                res.t_msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
                return null;
            }
            //数据条数
            if (xSheet.getLastRowNum() > 3009) {
                res.t_result = false
                res.t_msg = "文件数据超过3000条！请把数据拆分成几个文件，分次上传！"
                return null;
            }

            def cmLoginCertificate
            def rst
            //循环行Row
            for (int rowNum = 9; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                XSSFRow xRow = xSheet.getRow(rowNum)
                if (xRow) {
                    //                    循环列Cell
                    //                    int CELL_TYPE_NUMERIC = 0;
                    //                    int CELL_TYPE_STRING = 1;
                    //                    int CELL_TYPE_FORMULA = 2;
                    //                    int CELL_TYPE_BLANK = 3;
                    //                    int CELL_TYPE_BOOLEAN = 4;
                    //                    int CELL_TYPE_ERROR = 5;
                    LineInfo cell = new LineInfo();
                    XSSFCell ptrXCell = xRow.getCell(1);
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            cell.out_T_No = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.out_T_No = String.valueOf(ptrn).trim();
                        }
                    }

                    ptrXCell = xRow.getCell(2); //转入账户
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            cell.account = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.account = String.valueOf(ptrn).trim();
                        }
                    }

                    ptrXCell = xRow.getCell(3);//转账金额
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            cell.money = getLong(ptrXCell.getNumericCellValue())
                        }
                    }


                    ptrXCell = xRow.getCell(4);
                    if (ptrXCell) {//转账原因
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            cell.title = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.title = String.valueOf(ptrn).trim();
                        }
                    }

                    ptrXCell = xRow.getCell(5);
                    if (ptrXCell) {//客户备注
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            cell.note = ptrXCell.getStringCellValue().trim();
                        } else if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            def ptrn = getLong(ptrXCell.getNumericCellValue()) / 100;
                            cell.note = String.valueOf(ptrn).trim();
                        }
                    }
                    cell.lineCheck(cmap);
                    if (!cell.isBlank) {
                        if (cell.isRight) {
                            res.suc_count = res.suc_count + 1
                            res.suc_total = res.suc_total + cell.money
                        } else {
                            res.t_result = false
                            res.dt_result = false
                        }
                        res.cellList.add(cell);
                    } else {
                        break;
                    }
                }
            }
            if (res.t_result) {
                if (noInTemp != res.suc_total) {
                    res.t_result = false
                    res.t_msg = "上传文件中标明转账总金额是：" + noInTemp / 100 + "元，实际总金额是：" + res.suc_total / 100 + "元，请核对文件中的转账总金额。注意：文件中不能有空行！"
                    return null;
                }
            } else {
                res.t_msg = "部分数据出错，请下载出错数据文件！";
            }
        } catch (Exception e) {
            e.printStackTrace()
            res.result = false
            res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
            return null;
        }
    }

    protected dealFile(res) {

        File file = new File(res.file);
//         println res
        InputStream is = new FileInputStream(file);
        def extension = res.file.substring(res.file.indexOf(".") + 1)
        if (extension.toUpperCase().equals("XLS")) {
            this.dealXls(is, res);
        } else {
////                                println filename
            this.dealXlsx(is, res);
//
        }
        if (res.failList.size() > 0) {
            makeErrorReport(res);
        }

        if (file.exists()) {
            file.delete();
        }

    }

    def dealXls(is, res) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet xSheet = workbook.getSheetAt(0)
            if (xSheet == null) {
                res.result = false
                res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
                return null;
            }
            //循环行Row
//            List li = new ArrayList();
            def cmLoginCertificate
            def rst
            for (int rowNum = 9; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                HSSFRow xRow = xSheet.getRow(rowNum)
                if (xRow) {
//                    循环列Cell
//                    int CELL_TYPE_NUMERIC = 0;
//                    int CELL_TYPE_STRING = 1;
//                    int CELL_TYPE_FORMULA = 2;
//                    int CELL_TYPE_BLANK = 3;
//                    int CELL_TYPE_BOOLEAN = 4;
//                    int CELL_TYPE_ERROR = 5;
                    LineInfo cell = new LineInfo();
                    def li = new ArrayList();
                    HSSFCell ptrXCell = xRow.getCell(1);//商户订单号
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            li.add(ptrXCell.getStringCellValue().trim());
                        } else if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            li.add(String.valueOf((Long) ptrXCell.getNumericCellValue()).trim());
                        } else {
                            li.add("");
                        }
                    } else {
                        li.add("");
                    }

                    ptrXCell = xRow.getCell(2); //转入账户
                    li.add(ptrXCell.getStringCellValue().trim());

                    ptrXCell = xRow.getCell(3);//转账金额
                    li.add(ptrXCell.getNumericCellValue());

                    ptrXCell = xRow.getCell(4); //转账原因
                    li.add(ptrXCell.getStringCellValue().trim());

                    ptrXCell = xRow.getCell(5);//转账备注
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            li.add(ptrXCell.getStringCellValue().trim());
                        } else if (ptrXCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            li.add(String.valueOf(ptrXCell.getNumericCellValue()).trim());
                        } else {
                            li.add("");
                        }
                    } else {
                        li.add("");
                    }

                    if (!(li.get(0) && li.get(1) && li.get(2) && li.get(3) && li.get(4))) {
                        break;
                    } else {
                        def saveRes = save2(li.get(1), li.get(2), li.get(0), li.get(3), li.get(4), res);
                        if (saveRes.result == 'open') {
                            res.sucNum = res.sucNum + 1;
                            res.sucMoney = res.sucMoney + getLong(li.get(2));
                            res.sucList.add(li);
                            res.result = 'open'
                        } else if (saveRes.result) {
                            res.sucNum = res.sucNum + 1;
                            res.sucMoney = res.sucMoney + getLong(li.get(2));
                            res.sucList.add(li);
                        } else {
                            res.failNum = res.failNum + 1;
                            res.failMoney = res.failMoney + getLong(li.get(2));
                            li.add(saveRes.msg);
                            res.failList.add(li);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            res.result = false
            res.msg = "数据存储时发生未知错误！"
            return null;
        }
    }

    def dealXlsx(is, res) {
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(is)
            XSSFSheet xSheet = xwb.getSheetAt(0)
            if (xSheet == null) {
                res.result = false
                res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
                return null;
            }
            //循环行Row
//            List li = new ArrayList();
            def cmLoginCertificate
            def rst
            for (int rowNum = 9; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                XSSFRow xRow = xSheet.getRow(rowNum)
                if (xRow) {
//                    循环列Cell
//                    int CELL_TYPE_NUMERIC = 0;
//                    int CELL_TYPE_STRING = 1;
//                    int CELL_TYPE_FORMULA = 2;
//                    int CELL_TYPE_BLANK = 3;
//                    int CELL_TYPE_BOOLEAN = 4;
//                    int CELL_TYPE_ERROR = 5;
                    def li = new ArrayList();
                    XSSFCell ptrXCell = xRow.getCell(1);//商户订单号
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            li.add(ptrXCell.getStringCellValue().trim());
                        } else if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            li.add(String.valueOf((Long) ptrXCell.getNumericCellValue()).trim());
                        } else {
                            li.add("");
                        }
                    } else {
                        li.add("");
                    }

                    ptrXCell = xRow.getCell(2); //转入账户
                    li.add(ptrXCell.getStringCellValue().trim());

                    ptrXCell = xRow.getCell(3);//转账金额
                    li.add(ptrXCell.getNumericCellValue());

                    ptrXCell = xRow.getCell(4); //转账原因
                    li.add(ptrXCell.getStringCellValue().trim());

                    ptrXCell = xRow.getCell(5);//转账备注
                    if (ptrXCell) {
                        if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            li.add(ptrXCell.getStringCellValue().trim());
                        } else if (ptrXCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            li.add(String.valueOf(ptrXCell.getNumericCellValue()).trim());
                        } else {
                            li.add("");
                        }
                    } else {
                        li.add("");
                    }

                    if (!(li.get(0) && li.get(1) && li.get(2) && li.get(3) && li.get(4))) {
                        break;
                    } else {
                        def saveRes = save2(li.get(1), li.get(2), li.get(0), li.get(3), li.get(4), res);
                        if (saveRes.result == 'open') {
                            res.sucNum = res.sucNum + 1;
                            res.sucMoney = res.sucMoney + getLong(li.get(2));
                            res.sucList.add(li);
                            res.result = 'open'
                        } else if (saveRes.result) {
                            res.sucNum = res.sucNum + 1;
                            res.sucMoney = res.sucMoney + getLong(li.get(2));
                            res.sucList.add(li);
                        } else {
                            res.failNum = res.failNum + 1;
                            res.failMoney = res.failMoney + getLong(li.get(2));
                            li.add(saveRes.msg);
                            res.failList.add(li);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            res.result = false
            res.msg = "数据存储时发生未知错误！"
            return null;
        }
    }

    def save2(String toAccount, Double amt, String otNo, String sbj, String note, res) {
        /*def res=[file:filename,
        IP:ip,
        payer:cmLoginCertificate_payer,
        payer_op:customerOperator_payer,
        result:true,msg:'ok',
        sucNum:0,
        sucMoney:0,
        sucList:new ArrayList(),
        failNum:0,
        failMoney:0,
        failList:new ArrayList(),
        failFile:failFile]*/
        def saveRes = [result: true, msg: 'ok'];
        def amount = 0

        try {

            amount = getLong(amt);
            log.info "strAmount=" + amount
            def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(toAccount);
            /*
           ismp.CmLoginCertificate,   ismp.CmLoginCertificate
           ismp.CmCustomerOperator,
           ismp.CmLoginCertificate,   ismp.CmLoginCertificate
           java.lang.Double,
           java.lang.String,          transfer
           java.lang.String,          ss
           java.lang.String,          ss
           java.lang.String,          dd
           java.lang.String           0:0:0:0:0:0:0:1
            */
            def a = res.payer
            def b = res.payer_op
            def c = res.IP
            //返回值状态有open、true、false，open表示需要审批，true表示不需要审批，且转账成功，false表示转账失败
            def msgreturn = transferService.transfer2(a, b, cmLoginCertificate, amount, "transfer", otNo, sbj, note, c)
            if (msgreturn == 'open') {
                saveRes.result = msgreturn;
                saveRes.msg = 'ok';
                log.info msgreturn
            } else if (msgreturn) {
                saveRes.result = true;
                saveRes.msg = 'ok';
                log.info msgreturn
            } else {
                saveRes.result = false;
                saveRes.msg = msgreturn;
                log.info msgreturn
            }
            return saveRes;
        } catch (Exception e) {
            e.printStackTrace()
            saveRes.result = false;
            saveRes.msg = "数据库执行失败，请联系技术人员！";
            return saveRes;
        }
    }

    def makeErrorReport(res) {
        def filepath = request.getRealPath("/") + "file" + File.separator + "transfer" + File.separator + "multiTransferTemplate.xls"
        // 下载本地文件
        // 读到流中
        InputStream is = new FileInputStream(filepath);// 文件的存放路径
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(is)
            HSSFSheet xSheet = workbook.getSheetAt(0)
//            List li = new ArrayList();
            HSSFRow xRow = xSheet.getRow(8)
            HSSFCell ptrCell = xRow.createCell(6);
            ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
            ptrCell.setCellValue("失败原因");

            //循环行Row
            for (int i = 0; i < res.failList.size(); i++) {
                List ptrLInfo = res.failList.get(i);
                def rowNum = i + 9;
                xRow = xSheet.createRow(rowNum);

                ptrCell = xRow.createCell(1);//商户订单号
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue((String) ptrLInfo.get(0));

                ptrCell = xRow.createCell(2);//转入账户
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue((String) ptrLInfo.get(1));

                ptrCell = xRow.createCell(3);//转账金额
                ptrCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                ptrCell.setCellValue(getLong(ptrLInfo.get(2)) / 100);

                ptrCell = xRow.createCell(4);//转账原因
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue((String) ptrLInfo.get(3));

                ptrCell = xRow.createCell(5);//客户备注
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue((String) ptrLInfo.get(4));

                ptrCell = xRow.createCell(6);//错误原因
                ptrCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                ptrCell.setCellValue((String) ptrLInfo.get(5));

            }
            def reportPath = request.getRealPath("/") + "upload" + File.separator + "batchTransfer" + File.separator + "errorReport" + File.separator;
            if (!new File(reportPath).exists()) {
                new File(reportPath).mkdir()
            }
            workbook.write(new FileOutputStream(new File(reportPath + "fail_" + params.upname)));
        } catch (Exception e) {
            e.printStackTrace()
//            res.result=false
//            res.msg = "请确认上传文件中格式跟模板一样，并且有相应数据！"
            return null;
        }

    }
}
