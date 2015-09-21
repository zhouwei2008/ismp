import model.Model
import ismp.CmOpLog
import ismp.CmOpRelation

class SecurityFilters {
  def filters = {
    loginCheck(controller: '*', action: '*') {
      before = {
        //log.error "controllerName"+controllerName
        if (!controllerName) return true
        def curUrl = controllerName + "/" + (actionName == null ? "" : actionName)
        if (curUrl in ['agentpay/pay', 'agentpay/coll', 'agentpay/payquery', 'agentpay/collquery', 'agentpay/paysinglequery', 'agentpay/collsinglequery', 'application/index', 'application/confirm', 'application/checkEmail', 'agentpay/billset']) {
          return true
        }
        if (!session.cmCustomer && !"login".equals(controllerName) && !"captcha".equals(controllerName)) {
          redirect(controller: "login", action: "login")
          return false
        }else {
          //检查权限
          def hasPerm = true;
          /*if(curUrl in ['operator/create','operator/save','operator/edit','operator/update','operator/updateStatus','operator/resetLoginPass'])
         {
             if(session.cmCustomerOperator.roleSet!='admin') hasPerm=false
         }else if((curUrl in ['operator/changepaypass','operator/sendDynamicKey','operator/savechangepaypass'])||(controllerName in ['transfer','withdraw'])){
              if(session.cmCustomerOperator.roleSet!='finance') hasPerm=false
         } */

//                   println "***********************************   "  + session.modelUrlMap
          if (!((curUrl in session.modelUrlMap || curUrl in session.defModelUrlMap) || !(curUrl in session.modelMap))) {
//                   if(!(curUrl in Model.modelUrlMap || curUrl in Model.defModelUrlMap)){
//                       println "==================curUrl =      "  + curUrl
//                       println "==================session.modelUrlMap =      "  + session.modelUrlMap
            hasPerm = false
          }
  
		if (session.cmCustomer?.customerCategory == 'travel') {
                        if (curUrl == 'charge/index' || curUrl == 'transfer/index' || curUrl == 'withdraw/index') {
                            hasPerm = false
                        }
            }       


		 if (!hasPerm) {
            //log.info "noaccess"
            render(view: "/error", model: [type: 'error', msg: "No access to this operation."])
            return false
          }
        }
      }
    }

    //操作日志记录
    opLog(controller: '*', action: '*') {
      after = {
        def curUrl = controllerName + "/" + (actionName == null ? "" : actionName)
        if (curUrl in ['agentpay/pay', 'agentpay/coll', 'agentpay/payquery', 'agentpay/collquery', 'agentpay/paysinglequery', 'agentpay/collsinglequery', 'agentpay/billset']) {
          return true
        }

        //println "in log,controllerName:${controllerName},actionName:${actionName}"
        if (session.cmLoginCertificate && !['captcha'].contains(controllerName)) {
          //println "create log"
          //params.remove('controller')
          //params.remove('action')
          CmOpLog log = new CmOpLog()
          log.account = session.cmLoginCertificate.loginCertificate
          if(!session.cmCustomer){
              def cmCustomerOperator = session.cmLoginCertificate.customerOperator
              def cmCustomer = cmCustomerOperator.customer
              session.cmCustomer = cmCustomer
          }
          log.customerNo = session.cmCustomer.customerNo
          log.controller = controllerName
          log.action = actionName
          log.ip = request.getHeader('X-Real-IP') ? request.getHeader('X-Real-IP') : request.getRemoteAddr();
          if (params.containsKey('loginpwd')) {
            params.loginpwd = '******'
          }
          if (params.containsKey('password')) {
            params.password = '******'
          }
          if (params.containsKey('newpassword')) {
            params.newpassword = '******'
          }
          if (params.containsKey('confirm_newpassword')) {
            params.confirm_newpassword = '******'
          }
          log.params = params.toString()
            CmOpRelation  opRelation = new CmOpRelation()
            opRelation.controllers=controllerName
                   opRelation.actions=actionName
                   opRelation.names= controllerName+'.'+ actionName
            opRelation.save()
          log.save()
        }
        return true
      }
    }

  }
}
