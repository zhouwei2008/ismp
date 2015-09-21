package ismp

class ApplicationController {

    def index = {

    }

    def confirm = {
        def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(String.valueOf(params.bizEmail).trim())
        if (cmLoginCertificate) {
            render view: '/error', model: [type: 'error', msg: '邮箱已被注册。']
        } else {
            def dateTime = new Date()
            CmApplicationInfo cmApplicationInfo = new CmApplicationInfo()
            cmApplicationInfo.companyWebsite = params.companyWebsite
            cmApplicationInfo.registrationName = params.registrationName
            cmApplicationInfo.registrationType = params.registrationType
            cmApplicationInfo.belongToArea = params.belongToArea
            cmApplicationInfo.belongToBusiness = params.belongToBusiness
            cmApplicationInfo.bizMan = params.bizMan
            cmApplicationInfo.bizMPhone = params.bizMPhone
            cmApplicationInfo.bizPhone = params.bizPhone
            cmApplicationInfo.bizEmail = params.bizEmail
            cmApplicationInfo.officeLocation = params.officeLocation
            cmApplicationInfo.zipCode = params.zipCode
            cmApplicationInfo.status = '0'
            cmApplicationInfo.dateCreated = dateTime
            cmApplicationInfo.lastUpdated = dateTime
            cmApplicationInfo.save(flush: true, failOnError: true)
        }
    }

    def checkEmail = {
        def cmLoginCertificate = CmLoginCertificate.findByLoginCertificate(params.bizEmail)
        if (!cmLoginCertificate) {
            render(contentType: "text/json", encoding: "utf-8") {
                msg = "success"
            }
        }
    }
}
