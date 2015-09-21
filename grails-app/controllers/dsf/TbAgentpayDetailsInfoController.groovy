package dsf

class TbAgentpayDetailsInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tbAgentpayDetailsInfoInstanceList: TbAgentpayDetailsInfo.list(params), tbAgentpayDetailsInfoInstanceTotal: TbAgentpayDetailsInfo.count()]
    }

    def create = {
        def tbAgentpayDetailsInfoInstance = new TbAgentpayDetailsInfo()
        tbAgentpayDetailsInfoInstance.properties = params
        return [tbAgentpayDetailsInfoInstance: tbAgentpayDetailsInfoInstance]
    }

    def save = {
        def tbAgentpayDetailsInfoInstance = new TbAgentpayDetailsInfo(params)
        if (tbAgentpayDetailsInfoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), tbAgentpayDetailsInfoInstance.id])}"
            redirect(action: "show", id: tbAgentpayDetailsInfoInstance.id)
        }
        else {
            render(view: "create", model: [tbAgentpayDetailsInfoInstance: tbAgentpayDetailsInfoInstance])
        }
    }

    def show = {
        def tbAgentpayDetailsInfoInstance = TbAgentpayDetailsInfo.get(params.id)
        if (!tbAgentpayDetailsInfoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tbAgentpayDetailsInfoInstance: tbAgentpayDetailsInfoInstance]
        }
    }

    def edit = {
        def tbAgentpayDetailsInfoInstance = TbAgentpayDetailsInfo.get(params.id)
        if (!tbAgentpayDetailsInfoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tbAgentpayDetailsInfoInstance: tbAgentpayDetailsInfoInstance]
        }
    }

    def update = {
        def tbAgentpayDetailsInfoInstance = TbAgentpayDetailsInfo.get(params.id)
        if (tbAgentpayDetailsInfoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tbAgentpayDetailsInfoInstance.version > version) {
                    
                    tbAgentpayDetailsInfoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo')] as Object[], "Another user has updated this TbAgentpayDetailsInfo while you were editing")
                    render(view: "edit", model: [tbAgentpayDetailsInfoInstance: tbAgentpayDetailsInfoInstance])
                    return
                }
            }
            tbAgentpayDetailsInfoInstance.properties = params
            if (!tbAgentpayDetailsInfoInstance.hasErrors() && tbAgentpayDetailsInfoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), tbAgentpayDetailsInfoInstance.id])}"
                redirect(action: "show", id: tbAgentpayDetailsInfoInstance.id)
            }
            else {
                render(view: "edit", model: [tbAgentpayDetailsInfoInstance: tbAgentpayDetailsInfoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tbAgentpayDetailsInfoInstance = TbAgentpayDetailsInfo.get(params.id)
        if (tbAgentpayDetailsInfoInstance) {
            try {
                tbAgentpayDetailsInfoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tbAgentpayDetailsInfo.label', default: 'TbAgentpayDetailsInfo'), params.id])}"
            redirect(action: "list")
        }
    }
}
