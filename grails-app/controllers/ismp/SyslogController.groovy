package ismp

class SyslogController {

    def index = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        [cmLoginLogList: CmLoginLog.findAllByCustomer(session.cmCustomer,params), cmLoginLogListTotal: CmLoginLog.countByCustomer(session.cmCustomer)]
    }

}
