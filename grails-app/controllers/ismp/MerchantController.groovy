package ismp

import boss.BoCustomerService

class MerchantController extends BaseController{

    def info = {
        def cmCorporationInfo=(CmCorporationInfo)session.cmCustomer
        [cmCorporationInfo:cmCorporationInfo]
    }

    def service={
        def boCustomerServiceList=BoCustomerService.findAllByCustomerIdAndIsCurrent(session.cmCustomer.id,true)
        [boCustomerServiceList:boCustomerServiceList]
    }

    def bankaccount={
        def cmCustomerBankAccount=CmCustomerBankAccount.findByCustomerAndIsDefault(session.cmCustomer,true)
        [cmCustomerBankAccount:cmCustomerBankAccount]
    }

    def servicedetail={
        def service=BoCustomerService.get(params.id)
        if(!service||service.customerId!=session.cmCustomer.id)
        {
            writeInfoPage "没找到该服务信息"
            return
        }
        [service:service]
    }
}
