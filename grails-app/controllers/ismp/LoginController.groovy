package ismp

import javax.servlet.http.Cookie
import boss.BoCustomerService
import boss.BoNews
import com.ecard.products.constants.Constants
import boss.BoAgentPayServiceParams
import role.Role
import model.Model
import boss.BoRefundModel
import settle.FtSrvFootSetting
import ocx.AESWithJCE

import javax.servlet.http.HttpSession

class LoginController {
    //static allowedMethods = [authenticate: "POST"]
    def operatorService
    def dataSource_boss
    protected writeInfoPage = {msg, type = 'error' ->
        render view: '/error', model: [type: type, msg: msg]
    }

    def login = {
        def query = {
            eq('sysId', 'RONGPAY')
            eq('msgColumn', '吉高')
        }
        def query1 = {
            eq('sysId', 'RONGPAY')
            eq('msgColumn', '商户接入')
        }
        def query2 = {
            eq('sysId', 'RONGPAY')
            eq('msgColumn', '生活服务')
        }

        params.offset = 0
        params.max = 10
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        def list = BoNews.createCriteria().list(params, query)
        def newsCount = list.size()
        def list1 = BoNews.createCriteria().list(params, query1)
        def list2 = BoNews.createCriteria().list(params, query2)

        render(view: "login", model: [boNews: list, newsCount: newsCount, boNews1: list1, boNews2: list2])
        //log.info "1.session="+session.getId()
//        request.getSession().invalidate();//清空session
//        Cookie[] cookie = request.getCookies();//获取cookie
//        if(cookie)cookie[0].setMaxAge(0);//让cookie过期
    }

    def noaccess = {}

    def index = {redirect(action: "login")}

    def authenticate = {
        if (request.method.toLowerCase() == 'get') { redirect(action: "login"); return}
        def errmsg = ''

//        def mcrypt_key_1=(String)session.getAttribute("mcrypt_key");
//
////        println  "password1:"+  params.loginpwd
////
////        params.loginpwd=AESWithJCE.getResult(mcrypt_key_1,params.loginpwd);
////        session.removeAttribute("mcrypt_key");
////        println  "password2:"+  params.loginpwd

        params.sort = params.sort ? params.sort : "dateCreated"

        params.order = params.order ? params.order : "desc"
        def query = {
            eq('sysId', 'RONGPAY')
            eq('msgColumn', '吉高')
        }
        def query1 = {
            eq('sysId', 'RONGPAY')
            eq('msgColumn', '商户接入')
        }
        def query2 = {
            eq('sysId', 'RONGPAY')
            eq('msgColumn', '生活服务')
        }
        def list = BoNews.createCriteria().list(params, query)
        def list1 = BoNews.createCriteria().list(params, query1)
        def list2 = BoNews.createCriteria().list(params, query2)

        if (grailsApplication.config.verifyCaptcha != 'false') {
            log.info "params.captcha>>>>>>>"+params.captcha+"<<<<<<<<<<<session.captcha:"+session.captcha
            if (!params.captcha || !session.captcha?.isCorrect(params.captcha.toUpperCase())) {
                session.captcha = null
                errmsg = '验证码不正确，请重新输入'
                log.info errmsg
                render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
                return
            }
        session.captcha = null
        //log.info "2.session="+session.getId()
        println "loginname:"+        params.loginname.toLowerCase()
        def loginCertificate = CmLoginCertificate.findByLoginCertificate(params.loginname.toLowerCase())
        if (!loginCertificate) {
            errmsg = "帐户名或登录密码错误"
            render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            return
        }
        def cmCustomerOperator = loginCertificate.customerOperator
        def cmCustomer = cmCustomerOperator.customer

            //检查手机校验码
            def querylogin={
                eq('useType','login')
                eq('parameter',cmCustomerOperator.id as String)
                eq('sendType','mobile')
                eq('isUsed',false)
                ge("timeExpired",new Date())
            }

            def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],querylogin)
            def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
            if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha!=cmDynamicKey.verification))){
                errmsg = '手机验证码错误，请重新输入'
                log.info errmsg
                render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
                return
            }

        def roleId = cmCustomerOperator.roleSet
        def roleInfo = Role.get(roleId.toInteger())
        if (!roleInfo) {
            errmsg = "您没有分配角色，无法登陆。"
            log.info errmsg
            render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            return
        } else if (!roleInfo.roleStatus.equals("normal")) {
            errmsg = "对不起，您的角色已被停用。"
            log.info errmsg
            render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            return
        } else if (cmCustomerOperator.status == 'locked') {
            errmsg = "对不起，您的账号已被锁定。"
            log.info errmsg
            render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            return
        } else if (cmCustomerOperator.status == 'disabled' || cmCustomer.status == 'disabled') {
            errmsg = "对不起，您的账号已被停用。"
            log.info errmsg
            render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            return
        } else if (cmCustomerOperator.status == 'deleted' || cmCustomer.status == 'deleted' || cmCustomer.type != 'C') {
            errmsg = "帐户名或登录密码错误。"
            log.info errmsg
            render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            return
        }
        def str_pwd = cmCustomerOperator.id + params.loginpwd
        if (str_pwd.encodeAsSHA1() == cmCustomerOperator.loginPassword) {
            cmDynamicKey.timeUsed = new Date()
            cmDynamicKey.isUsed = true
            cmDynamicKey.save(flush: true, failOnError: true)

            if(isLogonUser(loginCertificate.id)){
                flash.message = "对不起，该操作员已经登陆，不能重复登陆！"
                log.info flash.message
                render(view: "compulsoryLogin", model: [loginCertificate: loginCertificate])
                return
            }

            request.getSession().setAttribute(LoginSessionListener.SESSION_LOGIN_NAME,loginCertificate.id);


            if (cmCustomerOperator.defaultMobile) {
                session.cmCustomerOperator = cmCustomerOperator
                session.cmCustomer = cmCustomer
                session.cmLoginCertificate = loginCertificate
                session.lastLoginTime = cmCustomerOperator.lastLoginTime

                // Agent Pay
                def collServices = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: Constants.ServiceType.COLLECT_SERVICE, isCurrent: true, enable: true)
                def payServices = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: Constants.ServiceType.PAY_SERVICE, isCurrent: true, enable: true)
                session.collServices = collServices != null
                session.payServices = payServices != null
                //println "session.collServices:${collServices}, session.payServices:${payServices}"
                //判断该客户是否开通分润服务
                def hasRoyaltyService = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: 'royalty', isCurrent: true, enable: true)
                session.hasRoyaltyService = hasRoyaltyService == null ? false : true
                //查找预付费卡充值服务是否开通服务
                def prechargeService = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: 'precharge', isCurrent: true, enable: true)
                session.prechargeService = prechargeService
                //log.info "3.session="+session.getId()
                //写登录日志
                CmLoginLog cmLoginLog = new CmLoginLog()
                cmLoginLog.customer = cmCustomer
                cmLoginLog.customerOperator = cmCustomerOperator
                cmLoginLog.loginCertificate = loginCertificate.loginCertificate
                def ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
                cmLoginLog.loginIp = ip
                cmLoginLog.loginResult = '登录成功'
                cmLoginLog.save(flush: true, failOnError: true)

                // 登录成功后清除登录错误计数为0
                cmCustomerOperator.loginErrorTime = 0
                cmCustomerOperator.loginIp=ip
                cmCustomerOperator.lastLoginTime = new Date()
                if (!cmCustomerOperator.lastPWChangeTime) {
                    cmCustomerOperator.lastPWChangeTime = cmCustomerOperator.lastLoginTime;
                }
                cmCustomerOperator.save(flush: true, failOnError: true)

                //取得登录用户的角色-------------------------
                def role = Role.findAllById(cmCustomerOperator.roleSet.toInteger())
                if (role?.roleStatus[0].equals('normal') && role?.model[0] != null && !role?.model[0].equals("")) {
                    String[] roleModels = role.model[0].split(',')
//                def modelsList = Model.createCriteria().list{order('modelindex','asc')}
                    // 取得权限菜单
                    def querySer = {
                        eq("customerId", Long.valueOf(session.cmCustomer.id))
                        eq("enable", true)
                        eq("isCurrent", true)
                    }
                    def cmServiceList = BoCustomerService.createCriteria().list(querySer)
                    def cmService = ""
                    cmServiceList.each {
                        cmService = cmService + "," + it["serviceCode"]
                        //判断是否具有单笔、批量、接口的代收付业务
                        String serviceCode = it.serviceCode;
                        if (serviceCode.equals("agentpay")) {
                            if (it.singleChannel) {
                                cmService = cmService + ",agentpaysingle";
                            }
                            if (it.batchChannel) {
                                cmService = cmService + ",agentpaybatch";
                            }
                        }
                        if (serviceCode.equals("agentcoll")) {
                            if (it.singleChannel) {
                                cmService = cmService + ",agentcollsingle";
                            }
                            if (it.batchChannel) {
                                cmService = cmService + ",agentcollbatch";
                            }
                        }
                    }
                    // 退款附加
                    def payService = BoCustomerService.findWhere(customerId: session.cmCustomer.id, serviceCode: 'online', isCurrent: true, enable: true)
                    println "-------------------------------" +  session.cmCustomer.id
                    println "-------------------------------" +  payService
                    def refundModels
                    if (payService) {
                        refundModels = BoRefundModel.findByCustomerServerId(payService.id)
                    }
                    cmService = cmService + "," + (refundModels ? refundModels.refundModel : 'recheck')
                    // 转账附加
                    cmService = cmService + "," + (refundModels ? refundModels.transferModel : 'open')
                    // 结算附加
                    def querySettle = {
                        eq("customerNo", session.cmCustomer.customerNo)
//                        ne("footType", 0)
                    }
                    def ft_cycleList = FtSrvFootSetting.createCriteria().list(querySettle)
                    if (ft_cycleList.size() > 0) {
                        cmService = cmService + ",settlecycle"
                    }
                    log.info 'cmService is ' + cmService
                    /*//判断是否具有单笔、批量、接口的代收付业务
                    cmServiceList.each {
                        String serviceCode = it.serviceCode;
                        if (serviceCode.equals("agentpay")) {
                            if (it.singleChannel) {
                                cmService = cmService + ",agentpaysingle";
                            }
                            if (it.batchChannel) {
                                cmService = cmService + ",agentpaybatch";
                            }
                        }
                         if (serviceCode.equals("agentcoll")) {
                            if (it.singleChannel) {
                                cmService = cmService + ",agentcollsingle";
                            }
                            if (it.batchChannel) {
                                cmService = cmService + ",agentcollbatch";
                            }
                        }
                    }
                    */
                    def queryModel = {
                        or {
                            isNull("serviceCode")
                            if (cmService.indexOf("agent") > -1) {
                                eq("serviceCode", "agent")
                            }
                            and {
                                if (cmService.indexOf("precharge") == -1) {
                                    ne("serviceCode", "precharge")
                                }
                                if (cmService.indexOf("agent") == -1) {
                                    ne("serviceCode", "agent")
                                }
                                if (cmService.indexOf("agentcoll") == -1) {
                                    ne("serviceCode", "agentcoll")
                                }
                                if (cmService.indexOf("agentpay") == -1) {
                                    ne("serviceCode", "agentpay")
                                }
                                if (cmService.indexOf("royalty") == -1) {
                                    ne("serviceCode", "royalty")
                                }
                                if (cmService.indexOf("recheck") == -1) {
                                    ne("serviceCode", "recheck")
                                }
                                if (cmService.indexOf("open") == -1) {
                                    ne("serviceCode", "open")
                                }
                                if (cmService.indexOf("settlecycle") == -1) {
                                    ne("serviceCode", "settlecycle")
                                }
                                if (cmService.indexOf("agentpaysingle") == -1) {
                                    ne("serviceCode", "agentpaysingle")
                                }
                                if (cmService.indexOf("agentpaybatch") == -1) {
                                    ne("serviceCode", "agentpaybatch")
                                }
                                if (cmService.indexOf("agentcollsingle") == -1) {
                                    ne("serviceCode", "agentcollsingle")
                                }
                                if (cmService.indexOf("agentcollbatch") == -1) {
                                    ne("serviceCode", "agentcollbatch")
                                }
                                //如果没有批量通道，则屏蔽批量封箱功能
                                if (cmService.indexOf("agentpaybatch") == -1&&cmService.indexOf("agentcollbatch") == -1) {
                                    ne("serviceCode", "agentbox")
                                }
                            }
                        }
			if (cmCustomer.customerCategory == 'travel') {
                            not {'in'('modelPath', ['charge/index', 'transfer/index', 'withdraw/index'])}
                        }
                        order('modelindex', 'asc')
                    }
                    def modelsList = Model.createCriteria().list(queryModel)
                    def levelList1 = new ArrayList()
                    def levelList2 = new ArrayList()
                    def level3Map = new HashMap()

                    def modelUrlMap = new String[roleModels.length]
                    def modelUrlMapIndex = 0

                    def modelMap = new String[modelsList.size()]
                    for (int i = 0; i < modelsList.size(); i++) {
                        modelMap[i] = ((Model) modelsList[i]).modelPath
                    }

                    for (int j = 0; j < modelsList.size(); j++) {
                        for (int i = 0; i < roleModels.length; i++) {
                            if (roleModels[i].toInteger() == ((Model) modelsList[j]).id) {
                                def model = (Model) modelsList[j]
                                if (model.modelLevel != null && model.modelLevel[0].equals("1")) {
                                    levelList1.add(model)
                                    level3Map.put(model.modelPath, model)
                                    modelUrlMap[modelUrlMapIndex] = model.modelPath
                                    modelUrlMapIndex++
                                } else if (model.modelLevel != null && model.modelLevel[0].equals("2")) {
                                    levelList2.add(model)
                                    level3Map.put(model.modelPath, model)
                                    modelUrlMap[modelUrlMapIndex] = model.modelPath
                                    modelUrlMapIndex++
                                } else if (model.modelLevel != null && model.modelLevel[0].equals("3")) {
                                    level3Map.put(model.modelPath, model)
                                    modelUrlMap[modelUrlMapIndex] = model.modelPath
                                    modelUrlMapIndex++
                                }
                            }
                        }
                    }

                    session.modelUrlMap = modelUrlMap
                    session.modelMap = modelMap
                    session.defModelUrlMap = ['index/account', 'captcha/index', 'login/login', 'login/logout', 'login/queryWelcomeMsg', 'login/authenticate']

                    def level2DefaultIndexPage = new HashMap();
                    for (Model model: levelList1) {
                        for (Model model2: levelList2) {
                            if (model.modelindex == Math.round(model2.modelindex / 100L)) {
                                level2DefaultIndexPage.put(model.modelPath, model2);
                                break;
                            }
                        }
                    }
                    session.level2DefaultIndexPage = level2DefaultIndexPage //一级功能中默认显示的页面


                    session.levelList1 = levelList1   //一级功能list
                    session.levelList2 = levelList2  //二级功能list
                    session.level3Map = level3Map   //第三级功能

                    session.role = role   //用户角色

                    redirect(controller: "index", action: "account")
                } else {
                    if (session.cmCustomerOperator) {
                        //写登录日志
                        CmLoginLog loginLog = new CmLoginLog()
                        loginLog.customer = session.cmCustomer
                        loginLog.customerOperator = session.cmCustomerOperator
                        loginLog.loginCertificate = session.cmLoginCertificate?.loginCertificate
                        ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
                        loginLog.loginIp = ip
                        loginLog.loginResult = '账号权限出错，退出系统'
                        loginLog.save(flush: true, failOnError: true)

                        session.cmCustomerOperator = null
                        session.cmCustomer = null
                        session.cmLoginCertificate = null
                        session.lastLoginTime = null

                        session.collServices = null
                        session.payServices = null
                    }
                    errmsg = "您的账号权限有问题，请与管理员联系。"
                    log.info errmsg
                    render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
                }
            } else {
                redirect(action: "historyUserBindMobile", id: cmCustomerOperator.id)
            }
            /////////////权限*************///////////
        } else {
                //写登录日志
                CmLoginLog cmLoginLog = new CmLoginLog()
                cmLoginLog.customer = cmCustomer
                cmLoginLog.customerOperator = cmCustomerOperator
                cmLoginLog.loginCertificate = loginCertificate.loginCertificate
                def ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
                cmLoginLog.loginIp = ip
                cmLoginLog.loginResult = '登录密码错误'
                cmLoginLog.save(flush: true, failOnError: true)
            }
            cmCustomerOperator.loginErrorTime++

            //如果连续登录错误次数达到5次，修改操作员状态为锁定
            if (cmCustomerOperator.loginErrorTime >= 5) {
                cmCustomerOperator.status = 'locked'
                cmCustomerOperator.save(flush: true, failOnError: true)
                errmsg = "对不起，您连续输入错误密码的次数已达到5次，您的账户已被锁定。"
                log.info errmsg
                render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            } else {
                cmCustomerOperator.save(flush: true, failOnError: true)
                errmsg = "帐户名或登录密码错误"
                log.info errmsg
                render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            }
        }
    }
    def historyUserBindMobile = {
        [cmCustomerOperator: params.id]
    }
    def send = {
        def cmCustomerOperatorId = params.id
        if (!cmCustomerOperatorId) {
            log.info "无效操作员ID"
            writeInfoPage null, null
            return
        }
        //发送手机短信
        def mobile = params.mobile.trim() as String
        def cmCustomerOperator = CmCustomerOperator.get(params.id as Long)
        operatorService.sendBindMobileSMS(cmCustomerOperator.customer, cmCustomerOperator, mobile)
        render "ok"
    }
    def step2 = {
        def cmCustomerOperatorId = params.id
        if (!cmCustomerOperatorId) {
            log.info "无效操作员ID"
            writeInfoPage null, null
            return
        }
        [cmCustomerOperator: params.id, mobile: params.mobile.trim()]
    }
    def saveHistoryUserBindMobile = {
        def query = {
            eq('useType', 'bindmobile')
            eq('parameter', params.id)
            eq('sendType', params.method)
            eq('isUsed', false)
            ge("timeExpired", new Date())
        }
        def cmDynamicKeyList = CmDynamicKey.createCriteria().list([sort: "id", order: 'desc'], query)
        def cmDynamicKey = (cmDynamicKeyList && cmDynamicKeyList.size() > 0) ? cmDynamicKeyList.first() : null
        if (!cmDynamicKey) {
            flash.message = '手机校验码失效'
            render(view: 'step2', model: [cmCustomerOperator: params.id, mobile: params.mobile])
            return
        }
        if (!params.captcha) {
            flash.message = '请输入您手机上收到的验证码'
            render(view: 'step2', model: [cmCustomerOperator: params.id, mobile: params.mobile])
            return
        }
        if ((!cmDynamicKey) || (params.captcha != cmDynamicKey.verification)) {
            flash.message = "手机验证码错误，请重新输入"
            render(view: 'step2', model: [cmCustomerOperator: params.id, mobile: params.mobile])
            return
        }
        //更改动态口令表信息
        cmDynamicKey.timeUsed = new Date()
        cmDynamicKey.isUsed = true
        cmDynamicKey.save(flush: true, failOnError: true)
        def cmCustomerOperator = CmCustomerOperator.findById(params.id)
        cmCustomerOperator.defaultMobile = params.mobile
        cmCustomerOperator.save(flush: true, failOnError: true)
        writeInfoPage "商户绑定手机成功", 'ok'
    }
    def logout = {
        flash.message = "${message(code: 'login.goodbye', default: 'Goodbye')}"
        if (session.cmCustomerOperator) {
            //写登录日志
            CmLoginLog cmLoginLog = new CmLoginLog()
            cmLoginLog.customer = session.cmCustomer
            cmLoginLog.customerOperator = session.cmCustomerOperator
            cmLoginLog.loginCertificate = session.cmLoginCertificate?.loginCertificate
            def ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
            cmLoginLog.loginIp = ip
            cmLoginLog.loginResult = '退出系统'
            cmLoginLog.save(flush: true, failOnError: true)

            session.cmCustomerOperator = null
            session.cmCustomer = null
            session.cmLoginCertificate = null
            session.lastLoginTime = null

            session.collServices = null
            session.payServices = null

            session.levelList1 = null
            session.levelList2 = null
            session.level3Map = null
            session.role = null
            session.modelUrlMap = null
            session.modelMap = null
            session.defModelUrlMap = null

            if(session != null){
                request.getSession().removeAttribute(LoginSessionListener.SESSION_LOGIN_NAME);
                request.getSession().invalidate()
            }
        }

        redirect(action: "login")
    }

    def saveresetloginpass = {
        def method = params.method
        def cmCustomerOperatorId = params.id
        if (!method && !cmCustomerOperatorId) {
            log.info "无效操作员ID或Method"
            writeInfoPage null, null
            return
        }

        def query = {
            eq('useType', 'bindmobile')
            eq('parameter', cmCustomerOperatorId as String)
            eq('sendType', method)
            eq('isUsed', false)
            ge("timeExpired", new Date())
        }
        //def cmDynamicKey=CmDynamicKey.findByParameterAndSendType(cmCustomerOperatorId as String,method)
        def cmDynamicKeyList = CmDynamicKey.createCriteria().list([sort: "id", order: 'desc'], query)
        def cmDynamicKey = (cmDynamicKeyList && cmDynamicKeyList.size() > 0) ? cmDynamicKeyList.first() : null
        if (!cmDynamicKey) {
            log.info "动态口令验证无效,手机校验码失效"
            writeInfoPage "手机校验码失效"
            return
        }

        if (!params.mobile_captcha) {
            flash.message = '请输入您手机上收到的验证码'
            render(view: 'resetloginpassbymobile', model: [cmCustomerOperatorId: cmCustomerOperatorId, mobile: params.mobile])
            return
        }
        if ((!cmDynamicKey) || (params.mobile_captcha != cmDynamicKey.verification)) {
            flash.message = "手机验证码错误，请重新输入"
            render(view: 'resetloginpassbymobile', model: [cmCustomerOperatorId: cmCustomerOperatorId, mobile: params.mobile])
            return
        }
        if (!params.password) {
            flash.message = "请输入新登录密码"
            render(view: 'resetloginpassbymobile', model: [cmCustomerOperatorId: cmCustomerOperatorId, mobile: params.mobile])
            return
        }
        if (params.password != params.confirm_password) {
            flash.message = "您输入的新登录密码和确认新登录密码不一致，请重新输入"
            render(view: 'resetloginpassbymobile', model: [cmCustomerOperatorId: cmCustomerOperatorId, mobile: params.mobile])
            return
        }
        //检查密码强度
        if (!KeyUtils.checkPassStrength(params.password)) {
            flash.message = "密码长度必须大于8位，且必须是数字、字母和特殊字符组合而成"
            render(view: 'resetloginpassbymobile', model: [cmCustomerOperatorId: cmCustomerOperatorId, mobile: params.mobile])
            return
        }
        //验证通过
        if (method == 'email') {
            operatorService.updateLoginPass(cmDynamicKey, params.password)
            writeInfoPage "设置登录密码成功", 'ok'
        } else if (method == 'mobile') {
            def mobile_captcha = params.mobile_captcha      //页面上输入的手机验证码
            if (grailsApplication.config.verifyCaptcha != 'false' && (!mobile_captcha || mobile_captcha != cmDynamicKey.verification)) {
                writeInfoPage "手机验证码错误，请重新输入"
                return
            } else {
                //验证码验证通过
                operatorService.updateLoginPass(cmDynamicKey, params.password)
                writeInfoPage "设置登录密码成功", 'ok'
            }
        }
    }

    def forget_login = {

    }

    def resetlogin = {
        def loginCertificate = CmLoginCertificate.findByLoginCertificate(params.loginname.toLowerCase() as String)
        if (!loginCertificate) {
            flash.message = "对不起，无此用户，请重新输入帐户名"
            redirect(action: "forget_login")
            return
        }
        def cmCustomerOperator = loginCertificate.customerOperator
        def cmCustomer = cmCustomerOperator.customer
        if (cmCustomerOperator.status == 'locked') {
            flash.message = "对不起，您的账号已被锁定。"
            redirect(action: "forget_login")
            return
        } else if (cmCustomerOperator.status == 'disabled' || cmCustomer.status == 'disabled') {
            flash.message = "对不起，您的账号已被停用。"
            redirect(action: "forget_login")
            return
        } else if (cmCustomerOperator.status == 'deleted' || cmCustomer.status == 'deleted') {
            flash.message = "${message(code: 'login.nothisuser', args: [params.loginname], default: 'Sorry, no this user {0}. Please try again.')}"
            redirect(action: "forget_login")
            return
        }
        session.cmCustomerOperator = null
        session.cmCustomer = null
        session.cmLoginCertificate = null
        session.lastLoginTime = null

        session.collServices = null
        session.payServices = null

        session.levelList1 = null
        session.levelList2 = null
        session.level3Map = null
        session.role = null
        session.modelUrlMap = null
        session.modelMap = null
        session.defModelUrlMap = null
        if (loginCertificate.certificateType == 'email') {
            operatorService.resetLoginPassByEmail(cmCustomer, cmCustomerOperator, loginCertificate.loginCertificate)
        } else {
            operatorService.resetLoginPassByMobile(cmCustomer, cmCustomerOperator, loginCertificate.loginCertificate)
            render(view: "resetloginpassbymobile", model: [mobile: loginCertificate.loginCertificate, cmCustomerOperatorId: cmCustomerOperator.id, useType: 'reset_login_pass'])
        }
    }

    def queryWelcomeMsg = {
        def loginName = params.loginname
        def loginCertificate = CmLoginCertificate.findByLoginCertificate(loginName?.toLowerCase())
        if (!loginCertificate) {
            render "false"
        } else {
            def cmCustomerOperator = loginCertificate.customerOperator
            def welcomeMsg = cmCustomerOperator.welcomeMsg
            def per = cmCustomerOperator.customer.type
            if (per == 'P') {
                render "false"
            }
            if (welcomeMsg == null) {
                welcomeMsg = "此用户没有设定个性化信息"
            }
            render welcomeMsg
        }
    }

    def resetloginpass = {
        session.cmCustomerOperator = null
        session.cmCustomer = null
        session.cmLoginCertificate = null
        session.lastLoginTime = null

        session.collServices = null
        session.payServices = null

        session.levelList1 = null
        session.levelList2 = null
        session.level3Map = null
        session.role = null
        session.modelUrlMap = null
        session.modelMap = null
        session.defModelUrlMap = null
        if (!queryCmDynamicKey()) {
            writeInfoPage "验证邮件已失效，请返回<a href=\"${grailsApplication.config.grails.serverURL}\" style=\"color:blue;\">首页</a>点击\"找回密码\"，再次发送邮件。"
             [cmCustomerOperatorId: params.id, verification: params.verification]
        } else {
            //验证通过，判断是否绑定过手机。绑定过则显示设置登录密码页面，未绑定则显示绑定手机页面
            def operator = CmCustomerOperator.findById(params.id)
            if (operator.defaultMobile) {
                render(view: "resetloginpassbymobile", model: [cmCustomerOperatorId: params.id, mobile: operator.defaultMobile])
            } else {
                [cmCustomerOperatorId: params.id, verification: params.verification]
            }
        }
    }

    def sendCaptcha = {
        def mobile_captcha = KeyUtils.getRandNumberKey(6)
        def content = new Date().format("MM月dd日") + ',您设置了登录密码。手机校验码：' + mobile_captcha + '。【吉高】'
        def cmCustomerOperator = CmCustomerOperator.get(params.id)
        operatorService.sendMobileCaptcha(cmCustomerOperator.customer, cmCustomerOperator, cmCustomerOperator.defaultMobile, content, mobile_captcha, 'bindmobile')
        render "ok"
    }

    def saveBindMobile = {
        if (!queryCmDynamicKey()) {
            writeInfoPage "动态口令验证无效"
             [cmCustomerOperatorId: params.id, verification: params.verification]
        } else {
            if (!params.mobile.trim()) {
                flash.message = '请输入您要绑定的手机号'
                render view: "bindMobile"
                return
            } else {
                def mobile = params.mobile.trim() as String
                if (!(mobile ==~ /^1[3458]\d{9}$/)) {
                    flash.message = '请输入正确的手机号码'
                    render(view: 'resetloginpass', model: [cmCustomerOperatorId: params.id, verification: params.verification])
                    return
                }
    //            //检查是否该手机号已经被绑定
    //            def cmLoginCertificate=CmLoginCertificate.findByLoginCertificate(mobile)
    //            if(cmLoginCertificate){
    //                flash.message = '该手机号'+mobile+'已经被绑定'
    //                render(view: 'resetloginpass', model: [cmCustomerOperatorId: params.id, verification: params.verification])
    //                return
    //            }
                def cmCustomerOperator = CmCustomerOperator.get(params.id)
    //            operatorService.sendBindMobileSMS(cmCustomerOperator.customer,cmCustomerOperator,mobile)
                cmCustomerOperator.defaultMobile = mobile
                cmCustomerOperator.save(flush: true, failOnError: true)
                render(view: "resetloginpassbymobile", model: [mobile: mobile, cmCustomerOperatorId: cmCustomerOperator.id, useType: 'bindmobile'])
            }
        }
    }
    protected def queryCmDynamicKey = {
        def cmCustomerOperatorId = params.id
        if (!cmCustomerOperatorId) {
            log.info "无效操作员ID"
            writeInfoPage null, null
            return false
        }
        def query = {
            eq('useType', 'reset_login_pass')
            eq('parameter', cmCustomerOperatorId as String)
            eq('sendType', 'email')
            eq('isUsed', false)
            eq('verification', params.verification)
            ge("timeExpired", new Date())
        }
        //def cmDynamicKey=CmDynamicKey.findByParameterAndSendType(cmCustomerOperatorId as String,'email')
        def cmDynamicKeyList = CmDynamicKey.createCriteria().list([sort: "id", order: 'desc'], query)
        def cmDynamicKey = (cmDynamicKeyList && cmDynamicKeyList.size() > 0) ? cmDynamicKeyList.first() : null
        if (!cmDynamicKey) {
            log.info "动态口令验证无效"
            return false
        }
        return true
    }

    def sendLoginMobileCaptcha ={
        def loginCertificate = CmLoginCertificate.findByLoginCertificate(params.loginname.toLowerCase())

        if(!loginCertificate){
            flash.message = "对不起，无此用户，请重新输入帐户名"
            render flash.message
            return
        }

        def cmCustomerOperator = loginCertificate.customerOperator
        def mobile_captcha = KeyUtils.getRandNumberKey(6)
        def content = new Date().format("MM月dd日 HH时mm分ss秒") + '，您本次登陆操作的验证码是' + mobile_captcha + '。【吉高】'
        operatorService.sendMobileCaptcha(cmCustomerOperator.customer, cmCustomerOperator, cmCustomerOperator.defaultMobile, content, mobile_captcha, 'login')
        render "ok"
        return
    }

    def isLogonUser(Long userId){
        Set<HttpSession> keys = LoginSessionListener.loginUser.keySet();
        for (HttpSession key : keys) {
            if (LoginSessionListener.loginUser.get(key).equals(userId)) {
                HttpSession session = (HttpSession)key
                session.invalidate()
                LoginSessionListener.loginUser.remove(key)
                return true;
            }
        }
        return false;
    }

    def compulsoryLogin = {
        def loginCertificate = CmLoginCertificate.findByLoginCertificate(params.id.toLowerCase())
        def cmCustomerOperator = loginCertificate.customerOperator
        def cmCustomer = cmCustomerOperator.customer

        request.getSession().setAttribute(LoginSessionListener.SESSION_LOGIN_NAME,loginCertificate.id);
        if (cmCustomerOperator.defaultMobile) {
            session.cmCustomerOperator = cmCustomerOperator
            session.cmCustomer = cmCustomer
            session.cmLoginCertificate = loginCertificate
            session.lastLoginTime = cmCustomerOperator.lastLoginTime

            // Agent Pay
            def collServices = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: Constants.ServiceType.COLLECT_SERVICE, isCurrent: true, enable: true)
            def payServices = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: Constants.ServiceType.PAY_SERVICE, isCurrent: true, enable: true)
            session.collServices = collServices != null
            session.payServices = payServices != null
            //println "session.collServices:${collServices}, session.payServices:${payServices}"
            //判断该客户是否开通分润服务
            def hasRoyaltyService = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: 'royalty', isCurrent: true, enable: true)
            session.hasRoyaltyService = hasRoyaltyService == null ? false : true
            //查找预付费卡充值服务是否开通服务
            def prechargeService = BoCustomerService.findWhere(customerId: cmCustomer.id, serviceCode: 'precharge', isCurrent: true, enable: true)
            session.prechargeService = prechargeService
            //log.info "3.session="+session.getId()
            //写登录日志
            CmLoginLog cmLoginLog = new CmLoginLog()
            cmLoginLog.customer = cmCustomer
            cmLoginLog.customerOperator = cmCustomerOperator
            cmLoginLog.loginCertificate = loginCertificate.loginCertificate
            def ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
            cmLoginLog.loginIp = ip
            cmLoginLog.loginResult = '登录成功'
            cmLoginLog.save(flush: true, failOnError: true)

            // 登录成功后清除登录错误计数为0
            cmCustomerOperator.loginErrorTime = 0

            cmCustomerOperator.lastLoginTime = new Date()
            if (!cmCustomerOperator.lastPWChangeTime) {
                cmCustomerOperator.lastPWChangeTime = cmCustomerOperator.lastLoginTime;
            }
            cmCustomerOperator.save(flush: true, failOnError: true)

            //取得登录用户的角色-------------------------
            def role = Role.findAllById(cmCustomerOperator.roleSet.toInteger())
            if (role?.roleStatus[0].equals('normal') && role?.model[0] != null && !role?.model[0].equals("")) {
                String[] roleModels = role.model[0].split(',')
//                def modelsList = Model.createCriteria().list{order('modelindex','asc')}
                // 取得权限菜单
                def querySer = {
                    eq("customerId", Long.valueOf(session.cmCustomer.id))
                    eq("enable", true)
                    eq("isCurrent", true)
                }
                def cmServiceList = BoCustomerService.createCriteria().list(querySer)
                def cmService = ""
                cmServiceList.each {
                    cmService = cmService + "," + it["serviceCode"]
                    //判断是否具有单笔、批量、接口的代收付业务
                    String serviceCode = it.serviceCode;
                    if (serviceCode.equals("agentpay")) {
                        if (it.singleChannel) {
                            cmService = cmService + ",agentpaysingle";
                        }
                        if (it.batchChannel) {
                            cmService = cmService + ",agentpaybatch";
                        }
                    }
                    if (serviceCode.equals("agentcoll")) {
                        if (it.singleChannel) {
                            cmService = cmService + ",agentcollsingle";
                        }
                        if (it.batchChannel) {
                            cmService = cmService + ",agentcollbatch";
                        }
                    }
                }
                // 退款附加
                def payService = BoCustomerService.findWhere(customerId: session.cmCustomer.id, serviceCode: 'online', isCurrent: true, enable: true)
                println "-------------------------------" +  session.cmCustomer.id
                println "-------------------------------" +  payService
                def refundModels
                if (payService) {
                    refundModels = BoRefundModel.findByCustomerServerId(payService.id)
                }
                cmService = cmService + "," + (refundModels ? refundModels.refundModel : 'recheck')
                // 转账附加
                cmService = cmService + "," + (refundModels ? refundModels.transferModel : 'open')
                // 结算附加
                def querySettle = {
                    eq("customerNo", session.cmCustomer.customerNo)
//                        ne("footType", 0)
                }
                def ft_cycleList = FtSrvFootSetting.createCriteria().list(querySettle)
                if (ft_cycleList.size() > 0) {
                    cmService = cmService + ",settlecycle"
                }
                log.info 'cmService is ' + cmService

                def queryModel = {
                    or {
                        isNull("serviceCode")
                        if (cmService.indexOf("agent") > -1) {
                            eq("serviceCode", "agent")
                        }
                        and {
                            if (cmService.indexOf("precharge") == -1) {
                                ne("serviceCode", "precharge")
                            }
                            if (cmService.indexOf("agent") == -1) {
                                ne("serviceCode", "agent")
                            }
                            if (cmService.indexOf("agentcoll") == -1) {
                                ne("serviceCode", "agentcoll")
                            }
                            if (cmService.indexOf("agentpay") == -1) {
                                ne("serviceCode", "agentpay")
                            }
                            if (cmService.indexOf("royalty") == -1) {
                                ne("serviceCode", "royalty")
                            }
                            if (cmService.indexOf("recheck") == -1) {
                                ne("serviceCode", "recheck")
                            }
                            if (cmService.indexOf("open") == -1) {
                                ne("serviceCode", "open")
                            }
                            if (cmService.indexOf("settlecycle") == -1) {
                                ne("serviceCode", "settlecycle")
                            }
                            if (cmService.indexOf("agentpaysingle") == -1) {
                                ne("serviceCode", "agentpaysingle")
                            }
                            if (cmService.indexOf("agentpaybatch") == -1) {
                                ne("serviceCode", "agentpaybatch")
                            }
                            if (cmService.indexOf("agentcollsingle") == -1) {
                                ne("serviceCode", "agentcollsingle")
                            }
                            if (cmService.indexOf("agentcollbatch") == -1) {
                                ne("serviceCode", "agentcollbatch")
                            }
                            //如果没有批量通道，则屏蔽批量封箱功能
                            if (cmService.indexOf("agentpaybatch") == -1&&cmService.indexOf("agentcollbatch") == -1) {
                                ne("serviceCode", "agentbox")
                            }
                        }
                    }
                    if (cmCustomer.customerCategory == 'travel') {
                        not {'in'('modelPath', ['charge/index', 'transfer/index', 'withdraw/index'])}
                    }
                    order('modelindex', 'asc')
                }
                def modelsList = Model.createCriteria().list(queryModel)
                def levelList1 = new ArrayList()
                def levelList2 = new ArrayList()
                def level3Map = new HashMap()

                def modelUrlMap = new String[roleModels.length]
                def modelUrlMapIndex = 0

                def modelMap = new String[modelsList.size()]
                for (int i = 0; i < modelsList.size(); i++) {
                    modelMap[i] = ((Model) modelsList[i]).modelPath
                }

                for (int j = 0; j < modelsList.size(); j++) {
                    for (int i = 0; i < roleModels.length; i++) {
                        if (roleModels[i].toInteger() == ((Model) modelsList[j]).id) {
                            def model = (Model) modelsList[j]
                            if (model.modelLevel != null && model.modelLevel[0].equals("1")) {
                                levelList1.add(model)
                                level3Map.put(model.modelPath, model)
                                modelUrlMap[modelUrlMapIndex] = model.modelPath
                                modelUrlMapIndex++
                            } else if (model.modelLevel != null && model.modelLevel[0].equals("2")) {
                                levelList2.add(model)
                                level3Map.put(model.modelPath, model)
                                modelUrlMap[modelUrlMapIndex] = model.modelPath
                                modelUrlMapIndex++
                            } else if (model.modelLevel != null && model.modelLevel[0].equals("3")) {
                                level3Map.put(model.modelPath, model)
                                modelUrlMap[modelUrlMapIndex] = model.modelPath
                                modelUrlMapIndex++
                            }
                        }
                    }
                }

                session.modelUrlMap = modelUrlMap
                session.modelMap = modelMap
                session.defModelUrlMap = ['index/account', 'captcha/index', 'login/login', 'login/logout', 'login/queryWelcomeMsg', 'login/authenticate']

                def level2DefaultIndexPage = new HashMap();
                for (Model model: levelList1) {
                    for (Model model2: levelList2) {
                        if (model.modelindex == Math.round(model2.modelindex / 100L)) {
                            level2DefaultIndexPage.put(model.modelPath, model2);
                            break;
                        }
                    }
                }
                session.level2DefaultIndexPage = level2DefaultIndexPage //一级功能中默认显示的页面


                session.levelList1 = levelList1   //一级功能list
                session.levelList2 = levelList2  //二级功能list
                session.level3Map = level3Map   //第三级功能

                session.role = role   //用户角色

                redirect(controller: "index", action: "account")
            } else {
                if (session.cmCustomerOperator) {
                    //写登录日志
                    CmLoginLog loginLog = new CmLoginLog()
                    loginLog.customer = session.cmCustomer
                    loginLog.customerOperator = session.cmCustomerOperator
                    loginLog.loginCertificate = session.cmLoginCertificate?.loginCertificate
                    ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP")
                    loginLog.loginIp = ip
                    loginLog.loginResult = '账号权限出错，退出系统'
                    loginLog.save(flush: true, failOnError: true)

                    session.cmCustomerOperator = null
                    session.cmCustomer = null
                    session.cmLoginCertificate = null
                    session.lastLoginTime = null

                    session.collServices = null
                    session.payServices = null
                }
                errmsg = "您的账号权限有问题，请与管理员联系。"
                log.info errmsg
                render(view: "login", model: [errmsg: errmsg, boNews: list, boNews1: list1, boNews2: list2])
            }
        } else {
            redirect(action: "historyUserBindMobile", id: cmCustomerOperator.id)
        }
    }
}
