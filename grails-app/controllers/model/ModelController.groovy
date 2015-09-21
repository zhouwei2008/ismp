package model

import ismp.TradeBase
import role.Role
import boss.BoCustomerService
import boss.BoRefundModel
import settle.FtSrvFootSetting

class ModelController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def modelList = {

        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0

        def list = Model.createCriteria().list {
            maxResults(100)
            order("modelLevel", "desc")
        }
        def count = Model.createCriteria().count {
            maxResults(100)
            order("modelLevel", "desc")
        }
        [modelList: list, modelListTotal: count]
    }


    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
        //[modelInstanceList: Model.list(params), modelInstanceTotal: Model.count()]

        def rid = params.id
        Role role = Role.get(rid)
        String modelStr = role.model.toString() == "0" ? "" : role.model
        [modelInstanceList: modelListAsJSON(role.model), ids: modelStr, rid: rid, rname: role.roleName]

    }

    def checkedAll = {
        String jsonNodes = ""
        String ids = ""
        Role role = Role.get(params.id)
        for (nd in Model.createCriteria().list {order('modelindex', 'asc')}) {
            ids += nd.id + ","
            jsonNodes += "{point:" + nd.point + ",parentPoint:" + nd.parentPoint + ",name:" + '"' + nd.modelName + '"' + ",ename:" + '"' + nd.modelPath + '"' + ",mid:" + nd.id + ",open:true,checked:true},"
        }
        ids = ids.substring(0, ids.length() - 1)
        jsonNodes = jsonNodes.substring(0, jsonNodes.length() - 1)

        render(view: "list", model: [modelInstanceList: jsonNodes, ids: ids, rid: params.id, rname: role.roleName, checkedAll: true])
    }

    def checkedAntiall = {
        Role role = Role.get(params.id)
        render(view: "list", model: [modelInstanceList: modelListAsJSON(), ids: "", rid: params.id, rname: role.roleName, checkedAntiall: true])
    }

    def modelListAsJSON(String ids) {
        String jsonNodes = ""
//        List modelList = Model.createCriteria().list{order('modelindex','asc')}

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

        //退款附加
        def payService = BoCustomerService.findWhere(customerId: session.cmCustomer.id, serviceCode: 'online', isCurrent: true, enable: true)
        def refundModels
        if (payService) {
            refundModels = BoRefundModel.findByCustomerServerId(payService.id)
        }

        cmService = cmService + "," + (refundModels?refundModels.refundModel:'recheck')
        // 转账附加
        cmService = cmService + "," + (refundModels?refundModels.transferModel : 'open')
        // 结算附加
        def querySettle = {
            eq("customerNo", session.cmCustomer.customerNo)
            ne("footType", 0)
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
                    if (cmService.indexOf("agentpaybatch") == -1 && cmService.indexOf("agentcollbatch") == -1) {
                        ne("serviceCode", "agentbox")
                    }
                }
            }
            order('modelindex', 'asc')
        }
        def modelList = Model.createCriteria().list(queryModel)
        if (ids != null && ids.length() > 0 && ids != "0") {
            String[] idsArr = ids.split(",")

            for (nd in modelList) {
                String tmp = "{point:" + nd.point + ",parentPoint:" + nd.parentPoint + ",name:" + '"' + nd.modelName + '"' + ",ename:" + '"' + nd.modelPath + '"' + ",mid:" + nd.id + ",open:true},"

                for (um in idsArr) {

                    if (um == nd.id.toString()) {
                        tmp = "{point:" + nd.point + ",parentPoint:" + nd.parentPoint + ",name:" + '"' + nd.modelName + '"' + ",ename:" + '"' + nd.modelPath + '"' + ",mid:" + nd.id + ",open:true,checked:true},"
                        break
                    }
                }
                jsonNodes += tmp
            }
        } else {
            for (nd in modelList) {
                jsonNodes += "{point:" + nd.point + ",parentPoint:" + nd.parentPoint + ",name:" + '"' + nd.modelName + '"' + ",ename:" + '"' + nd.modelPath + '"' + ",mid:" + nd.id + ",open:true},"
            }
        }

        jsonNodes = jsonNodes.substring(0, jsonNodes.length() - 1)
        println("jsonNodes:"+jsonNodes)
        return jsonNodes
    }


    def create = {
//        def modelInstance = new Model()
//        modelInstance.properties = params
//        return [modelInstance: modelInstance]
    }

    def save = {

//        def modelInstance = new Model(params)
//        if (modelInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'model.label', default: 'Model'), modelInstance.id])}"
//            redirect(action: "show", id: modelInstance.id)
//        }
//        else {
//            render(view: "create", model: [modelInstance: modelInstance])
//        }

    }

    def saveToRole = {
        String result = params.result.replace("[,", "").replace(",]", "");
        Set set = new HashSet(Arrays.asList(result.split(",")))
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String str = iterator.next()
            if (!str.matches("\\d+")) {
                iterator.remove()
            }
        }

        result = set.toString().replace(" ", "").replace("[", "").replace("]", "")

        Role role = Role.get(params.rid)
        role.model = result
        if (role.save(flush: true)) {
            redirect(controller: 'role', action: 'list', ids: result, id: params.rid)
        } else {
            redirect(action: "list", ids: result, id: params.rid)
        }

    }

    def show = {
//        def modelInstance = Model.get(params.id)
//        if (!modelInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), params.id])}"
//            redirect(action: "list")
//        }
//        else {
//            [modelInstance: modelInstance]
//        }
    }

    def edit = {
//        def modelInstance = Model.get(params.id)
//        if (!modelInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), params.id])}"
//            redirect(action: "list")
//        }
//        else {
//            return [modelInstance: modelInstance]
//        }
    }

    def update = {
        def modelInstance = Model.get(params.id)
        if (modelInstance) {
//            if (params.version) {
//                def version = params.version.toLong()
//                if (modelInstance.version > version) {
//
//                    modelInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'model.label', default: 'Model')] as Object[], "Another user has updated this Model while you were editing")
//                    render(view: "edit", model: [modelInstance: modelInstance])
//                    return
//                }
//            }
//            modelInstance.properties = params
//            if (!modelInstance.hasErrors() && modelInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'model.label', default: 'Model'), modelInstance.id])}"
//                redirect(action: "show", id: modelInstance.id)
//            }
//            else {
//                render(view: "edit", model: [modelInstance: modelInstance])
//            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def modelInstance = Model.get(params.id)
        if (modelInstance) {
//            try {
//                modelInstance.delete(flush: true)
//                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'model.label', default: 'Model'), params.id])}"
//                redirect(action: "list")
//            }
//            catch (org.springframework.dao.DataIntegrityViolationException e) {
//                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'model.label', default: 'Model'), params.id])}"
//                redirect(action: "show", id: params.id)
//            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), params.id])}"
            redirect(action: "list")
        }
    }
}
