package trade

import ismp.TradeBase
import ismp.TradeAccountCommandSaf
import org.apache.commons.logging.LogFactory

/**
 * 帐务指令包装器
 * 注意: 非线程安全
 */
class AccountCommandPackage {
    static log = LogFactory.getLog(AccountCommandPackage.class)

    String  commandNo   = UUID.randomUUID().toString().replace('-', '')
    List    commandList = []
    Date    dateCreated = new Date()
    Boolean updateOnly  = false
    Boolean redoMode    = false

    def append(TradeBase trade) {
        append(
                commandType     : (trade.tradeType in ['frozen', 'unfrozen']) ? trade.tradeType : 'transfer',
                tradeId         : trade.id,
                tradeNo         : trade.tradeNo,
                outTradeNo      : trade.outTradeNo,
                fromAccountNo   : trade.payerAccountNo,
                toAccountNo     : trade.payeeAccountNo,
                amount          : trade.amount,
                currency        : trade.currency,
                transferType    : trade.tradeType,
                subject         : trade.subject              //new add
        )
    }

    def append(Map attr) {
        if (updateOnly) return this
        attr.commandNo  = commandNo
        attr.subSeqno   = commandList.size()
        attr.syncTime   = dateCreated
        attr.syncFlag   = 'F'
        if (redoMode) {
            attr.redoFlag  = 'T'
            attr.redoCount = 0
        }
        if (commandList) {
            def last = commandList[-1]
            attr.commandType    = (attr.commandType) ? attr.commandType : last.commandType
            attr.tradeId        = (attr.tradeId) ? attr.tradeId : last.tradeId
            attr.tradeNo        = (attr.tradeNo) ? attr.tradeNo : last.tradeNo
            attr.outTradeNo     = (attr.outTradeNo) ? attr.outTradeNo : last.outTradeNo
            attr.currency       = (attr.currency) ? attr.currency : last.currency
            attr.transferType   = (attr.transferType) ? attr.transferType : last.transferType
            attr.subjict        = (attr.subjict) ? attr.subjict : ""
        } else {
            attr.outTradeNo     = (attr.outTradeNo) ? attr.outTradeNo : ''
            attr.subjict        = (attr.subjict) ? attr.subjict : ""
        }
        commandList << new TradeAccountCommandSaf(attr)
        this
    }

    def save() {
        commandList.each { it.save failOnError: true }
    }

    def update( resp ) {
        log.info "update $resp"
        log.info "in update"
        if ( !resp ) return
        commandList.each { acs ->
            acs.syncFlag = 'S'
            acs.respCode = resp.errorCode
            if ( resp.result == 'true' ) {
                acs.transCode = resp.transCode
                acs.transId   = resp.transIds.remove(0)
                if (redoMode) {
                    acs.redoFlag = 'F'
                }
            }
            acs.save failOnError: true
        }
    }

    String toString() {
        "AccountCommandPackage($commandNo) $commandList"
    }

    static findByCommandNo( commandNo ) {
        def list = TradeAccountCommandSaf.findAll(
                "from TradeAccountCommandSaf where commandNo=? order by subSeqno",
                [commandNo]
        )
        if (list) {
            new AccountCommandPackage(commandNo: commandNo, commandList: list, updateOnly: true)
        } else {
            null
        }
    }

    static createRedoByCommandNo( commandNo ) {
        def list = TradeAccountCommandSaf.findAll(
                "from TradeAccountCommandSaf where commandNo=? and redoFlag='T' and syncFlag='S' order by subSeqno",
                [commandNo]
        )
        if (list) {
            def acPackage = new AccountCommandPackage(updateOnly: true, redoMode: true)
            acPackage.commandList = list.collect {
                it.redoFlag = 'F'
                it.save failOnError: true
                it.clone(
                        commandNo: acPackage.commandNo, redoFlag: 'T', syncTime: acPackage.dateCreated
                ).save failOnError: true
            }
            acPackage
        } else {
            null
        }
    }
    static  String getOrgTransno(Long id){
        def returnstr
        def tradebase = TradeBase.get(id)
        if(tradebase){
            returnstr = tradebase.tradeNo
        }
       return  returnstr
    }
    static  boolean  getLargeBank(String code){
          boolean flag =false
          def bobankdic =  boss.BoBankDic.findByCode(code)
         if(bobankdic){
             List list =   boss.BoAcquirerAccount.findAllByBank(bobankdic)
             if(list!=null&&list.size()>0){
                   a:for(int i=0;i<list.size();i++){
                           def boaccount =(boss.BoAcquirerAccount) list.get(i)
                         if(boaccount){
                               List merchantlist =   boss.BoMerchant.findAllByAcquirerAccount(boaccount)
                               if(merchantlist!=null&&merchantlist.size()>0){
                                  for(int j=0;j<merchantlist.size();j++){
                                        def merchant =  (boss.BoMerchant)   merchantlist.get(j)
                                          if(merchant){
                                                if("2".equals(merchant.bankType)&&"0".equals(merchant.channelSts)){
                                                     flag = true
                                                    println "dddddddddddddddddd"+flag
                                                     break  a
                                                }
                                          }
                                  }
                               }
                         }
                    }
             }
         }

          return flag

    }

     static String getContentStr(String content){
        def str = ""
        if(content&&!"".equals(content)){
             if(content.length()>10){
                str = content.substring(0,10)+"..."
             }else{
                 str = content
             }
        }
        return str
    }

     static String getContentStrForLogin(String content){
        def str = ""
        if(content&&!"".equals(content)){
             if(content.length()>16){
                str = content.substring(0,16)+"..."
             }else{
                 str = content
             }
        }
        return str
    }

     static String getLastNo(String content){
        def str = ""
        if(content&&!"".equals(content)&&content.length()>4){
            str="**** **** ****"+content.substring(content.length()-4,content.length())
        }
        return str
    }

}
