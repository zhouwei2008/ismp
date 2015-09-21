import groovy.sql.Sql
import grails.util.GrailsUtil
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import ismp.CmCorporationInfo
import ismp.CmCustomerOperator
import ismp.CmLoginCertificate
import ismp.CmCustomerBankAccount
import ismp.KeyUtils

class BootStrap {
    def dataSource

    def init = { servletContext ->
//        createSeq(name: 'seq_trade_no', max: 9999999)
        switch ( GrailsUtil.environment ) {
            case 'development':
//                def ci = new CmCorporationInfo()
//                ci.registrationName='测试客户001科技有限公司'
//                ci.name='测试客户001'
//                ci.apiKey=KeyUtils.getRandKey(32)
//                ci.type='C'
//                ci.customerNo='100000000000001'
//                ci.status='normal'
//                ci.accountNo='AC001'
//                ci.businessLicenseCode='BM101001'
//                ci.organizationCode='ORG101001'
//                ci.taxRegistrationNo='TA101001'
//                ci.businessScope='软件开发，软件销售，计算机技术服务'
//                ci.registrationDate=new Date()
//                ci.licenseExpires=ci.registrationDate+365
//                ci.save flush: true, failOnError: true
//
//                def ci2=new CmCorporationInfo(registrationName: '测试客户002科技有限公司',name:'测试客户002',apiKey:KeyUtils.getRandKey(32),type: 'C',customerNo: '100000000000002',
//                status: 'normal',accountNo: 'AC002',businessLicenseCode: 'BM101002',organizationCode: 'ORG101002',taxRegistrationNo: 'TA101002',businessScope: '软件销售',
//                registrationDate: new Date(),licenseExpires: new Date()+365)
//                ci2.save flush: true, failOnError: true
//
//                def cmCustomerOperator=new CmCustomerOperator()
//                cmCustomerOperator.customer=ci
//                cmCustomerOperator.status='normal'
//                cmCustomerOperator.name='管理员'
//                cmCustomerOperator.defaultEmail='admin@testpay.org'
//                cmCustomerOperator.roleSet='admin'
//                cmCustomerOperator.save flush: true, failOnError: true
//                //设置登录密码
//                cmCustomerOperator.loginPassword=(cmCustomerOperator.id+'123456').encodeAsSHA1()
//                cmCustomerOperator.save flush: true, failOnError: true
//
//                def cmLoginCertificate=new CmLoginCertificate()
//                cmLoginCertificate.customerOperator=cmCustomerOperator
//                cmLoginCertificate.certificateType='email'
//                cmLoginCertificate.loginCertificate=cmCustomerOperator.defaultEmail
//                cmLoginCertificate.isVerify=false
//                cmLoginCertificate.save flush: true, failOnError: true
//
//                def cmCustomerOperator2=new CmCustomerOperator()
//                cmCustomerOperator2.customer=ci
//                cmCustomerOperator2.status='normal'
//                cmCustomerOperator2.name='销售经理'
//                cmCustomerOperator2.defaultEmail='sale001@testpay.org'
//                cmCustomerOperator2.roleSet='user'
//                cmCustomerOperator2.save flush: true, failOnError: true
//                //设置登录密码
//                cmCustomerOperator2.loginPassword=(cmCustomerOperator2.id+'123456').encodeAsSHA1()
//                cmCustomerOperator2.save flush: true, failOnError: true
//
//                def cmLoginCertificate2=new CmLoginCertificate()
//                cmLoginCertificate2.customerOperator=cmCustomerOperator2
//                cmLoginCertificate2.certificateType='email'
//                cmLoginCertificate2.loginCertificate=cmCustomerOperator2.defaultEmail
//                cmLoginCertificate2.isVerify=false
//                cmLoginCertificate2.save flush: true, failOnError: true
//
//                def cmCustomerOperator3=new CmCustomerOperator(customer: ci,name: '财务经理',status: 'normal',defaultEmail: 'finance01@testpay.org',defaultMobile: '13910111234', roleSet: 'finance')
//                cmCustomerOperator3.save flush: true, failOnError: true
//                cmCustomerOperator3.loginPassword=(cmCustomerOperator3.id+'123456').encodeAsSHA1()
//                cmCustomerOperator3.payPassword=(cmCustomerOperator3.id+'p'+'123456').encodeAsSHA1()
//                cmCustomerOperator3.save flush: true, failOnError: true
//                def cmLoginCertificate3=new CmLoginCertificate(customerOperator: cmCustomerOperator3,certificateType: 'email',loginCertificate: cmCustomerOperator3.defaultEmail,isVerify: false)
//                cmLoginCertificate3.save flush: true, failOnError: true
//
//                def cmCustomerOperator4=new CmCustomerOperator(customer: ci2,name: '对方销售员',status: 'normal',defaultEmail: 'sale01001@testpay.org',roleSet: 'admin')
//                cmCustomerOperator4.save flush: true, failOnError: true
//                cmCustomerOperator4.loginPassword=(cmCustomerOperator4.id+'123456').encodeAsSHA1()
//                cmCustomerOperator4.save flush: true, failOnError: true
//                def cmLoginCertificate4=new CmLoginCertificate(customerOperator: cmCustomerOperator4,certificateType: 'email',loginCertificate: cmCustomerOperator4.defaultEmail,isVerify: false)
//                cmLoginCertificate4.save flush: true, failOnError: true
//
//                def cmCustomerBankAccount=new CmCustomerBankAccount()
//                cmCustomerBankAccount.customer=ci
//                cmCustomerBankAccount.bankCode='BOC'
//                cmCustomerBankAccount.branch='北京分行'
//                cmCustomerBankAccount.subbranch='朝阳支行'
//                cmCustomerBankAccount.bankNo='101010789'
//                cmCustomerBankAccount.bankAccountName=ci.name
//                cmCustomerBankAccount.bankAccountNo='6225880188888888'
//                cmCustomerBankAccount.status='normal'
//                cmCustomerBankAccount.isCorporate=true
//                cmCustomerBankAccount.isDefault=true
//                cmCustomerBankAccount.isVerify=false
//                cmCustomerBankAccount.save flush: true, failOnError: true
                break
            case 'test': break
            case 'production': break
        }

    }

    def destroy = {
    }

//    def createSeq( attrs ){
//        def ds=DatasourcesUtils.getDataSource('ismp')
//        println ds
//        def sql = new Sql(ds)
//        def name = attrs.name
//        if (!name) {
//            println 'error createSeq name is null'
//            return
//        }
//        try {
//            sql.firstRow("select ${name}.nextval seq from dual" as String)
//        } catch (e) {
//            println "not found seq $name"
//            def min = (attrs.min) ? attrs.min : 1
//            def max = (attrs.max) ? attrs.max : 999999999999999999999999999
//            def start = (attrs.start) ? attrs.start : min
//            sql.execute("""
//                    create sequence $name
//                    minvalue $min
//                    maxvalue $max
//                    start with $start
//                    increment by 1
//                    cache 20
//                    """ as String)
//            println "create sequence $name"
//        }
//    }
}
