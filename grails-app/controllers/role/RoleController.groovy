package role

import ismp.KeyUtils;

class RoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = {
            or {
                eq("customerId", String.valueOf(session.cmCustomer.id))
                eq("customerId", String.valueOf(0))
            }
            order("createTime", "desc")
        }
        def list = Role.createCriteria().list(params, query)
        def count = Role.findAllByCustomerId(String.valueOf(session.cmCustomer.id)).size()
        [roleInstanceList: list, roleInstanceTotal: list.size()]
    }

    def create = {
        def roleInstance = new Role()
        roleInstance.properties = params
        return [roleInstance: roleInstance]
    }

    def save = {
        def roleInstance = new Role(params)
        if ((params.roleName == null)||(params.roleName == "")) {
            flash.message = "角色名称不可为空"
            render(view: "create", model: [roleInstance: roleInstance])
        } else if (!(params.roleName =~/^[a-zA-Z0-9\u4E00-\u9FA5]+$/)) {
            flash.message = "角色名称只能输入数字、字母与汉字"
            render(view: "create", model: [roleInstance: roleInstance])
        } else {
            if(params.roleName.equals("管理员") || params.roleName.equals("财务") || params.roleName.equals("普通用户")){
                println 'error'
                flash.message = "角色名称已存在"
                render(view: "create", model: [roleInstance: roleInstance])
            } else {
                def list = Role.findAllByRoleNameAndCustomerId(params.roleName, String.valueOf(session.cmCustomer.id))
                //判断此名称是否已经存在
                if(list != null && list.size() > 0){
                    println 'error'
                    flash.message = "角色名称已存在"
                    render(view: "create", model: [roleInstance: roleInstance])
                } else {
                    roleInstance.customerId = session.cmCustomer.id
                    if (roleInstance.save(flush: true)) {
                        flash.message = "新增角色成功"
                        redirect(action: "list")
                    }
                    else {
                        flash.message = "新增角色失败"
                        render(view: "create", model: [roleInstance: roleInstance])
                    }
                }
            }
        }
    }

    def show = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {

            redirect(action: "list")
        }
        else {
            [roleInstance: roleInstance]
        }
    }

    def edit = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {
            flash.message = "此角色不存在"
            redirect(action: "list")
        }
        else {
            return [roleInstance: roleInstance]
        }
    }

    def update = {
        def roleInstance = Role.get(params.id)
        if ((params.roleName == null)||(params.roleName == "")) {
            flash.message = "角色名称不可为空"
            render(view: "edit", model: [roleInstance: roleInstance])
        } else if (!(params.roleName =~/^[a-zA-Z0-9\u4E00-\u9FA5]+$/)) {
            flash.message = "角色名称只能输入数字、字母与汉字"
            render(view: "edit", model: [roleInstance: roleInstance])
        } else {
            if (roleInstance) {
                if (params.version) {
                    def version = params.version.toLong()
                    if (roleInstance.version > version) {
                        roleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'role.label', default: 'Role')] as Object[], "Another user has updated this Role while you were editing")
                        render(view: "edit", model: [roleInstance: roleInstance])
                        return
                    }
                }

                if(params.roleName.equals("管理员") || params.roleName.equals("财务") || params.roleName.equals("普通用户")){
                    println 'error'
                    flash.message = "角色名称已存在"
                    render(view: "edit", model: [roleInstance: roleInstance])
                } else {
                   def list = Role.findAllByRoleNameAndCustomerId(params.roleName, String.valueOf(session.cmCustomer.id))
                     //判断此名称是否已经存在
                    if(list != null && list.size() > 0 && !String.valueOf(list[0]["id"]).equals(String.valueOf(params.id))){
                        flash.message = "角色名称已存在"
                        render(view: "edit", model: [roleInstance: roleInstance])
                    }else{
                        roleInstance.properties = params
                        if (!roleInstance.hasErrors() && roleInstance.save(flush: true)) {
                            flash.message = "修改角色名称成功"
                            redirect(action: "list")
                        }
                        else {
                            flash.message = "修改角色名称失败"
                            render(view: "edit", model: [roleInstance: roleInstance])
                        }
                    }
                }
            }
            else {
                flash.message = "此角色不存在"
                redirect(action: "list")
            }
        }
    }

    def updateStatus = {
        def roleInstance = Role.get(params.id)
        if (roleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (roleInstance.version > version) {
                    roleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'role.label', default: 'Role')] as Object[], "Another user has updated this Role while you were editing")
                    redirect(action:"list")
                    return
                }
            }

            if(roleInstance.roleStatus.equals('normal')){
                roleInstance.roleStatus = 'close'
            }else if(roleInstance.roleStatus.equals('close')) {
                roleInstance.roleStatus = 'normal'
            }

            if (!roleInstance.hasErrors() && roleInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
                redirect(action: "list")
            }
            else {
                flash.message = "修改角色状态失败"
                redirect(action:"list")
            }
        }
        else {
           flash.message = "此角色不存在"
            redirect(action: "list")
        }
    }

    def remove = {
        def roleInstance = Role.get(params.id)
        if (roleInstance) {
            try {
                roleInstance.delete(flush: true)
                flash.message = "删除角色成功"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "删除角色失败"
                redirect(action: "list")
            }
        }
        else {
            flash.message = "没有找到此角色"
            redirect(action: "list")
        }
    }
}
