package boss

class BoAgentPayServiceParams extends boss.BoCustomerService
{
      //收费方式（0：按笔收费   1：按批次收费    2：按流量收费 ）
    String gatherWay
    //手续费（对公每笔、每批次、费率）
    Double procedureFee
    //手续费（对私每笔）
    Double perprocedureFee
    //单笔限额
    Double limitMoney
    //日交易限额（笔数）
    Long dayLimitTrans
    //日交易限额（金额）
    Double dayLimitMoney
    //月交易限额（笔数）
    Long monthLimitTrans
    //月交易限额（金额）
    Double monthLimitMoney
    //年费
    Double yearlyPayment
    //风险保证金
    Double dangerMoney
    //结算方式（0：即扣  1：后返）
    String settWay
    //是否退还手续费  0：是  1：否
    String backFee
    //结算周期  0：T+?天结算  1：按通知结算
    String settCycle
    //T+结算天数
    String settCycleDay
    //批次版本号
    String remark
    String batchVersion  = "00"
    String messageNotify// 短信通知
    String templateType  //模板类型
    String ismpCheck     //商户后台审核
    String bossCheck     //BOSS后台审核
    String batchChannel  //批量代付
    String singleChannel     //单笔代付
    String interfaceChannel   //接口代付
    Double batchPubFee         //批量对公手续费
    Double batchPriFee         //批量对私手续费
    Double singlePubFee        //单笔对公手续费
    Double singlePriFee       //单笔对私手续费
    Double interfacePubFee   //接口对公手续费
    Double interfacePriFee   //接口对私手续费
    Double pubLimitMoney    //对公单笔限额
    Double priLimitMoney //对私单笔限额
    String isDq//是否代扣 0 否 1:是
    String userCompactno//用户协议号
    Integer dqAccountId //代扣账号ID
    //窗口时间
    Long windowTime
    String isMailMessage //是否邮件通知 0 否 1是
    String entrustEmail   //通知的邮件

    // 附加 证书信息
    String certName
    String certPath
    Date certDate


    //ISENTRUST	CHAR(1)	Y	委托代扣
    //ENTRUST_ACCOUNT	VARCHAR2(33)	Y	委托代扣银行ID
    //ENTRUST_USERCODE	VARCHAR2(15)	Y	委托代扣商户协议号
    //ENTRUST_EMAIL	VARCHAR2(50)	Y	委托代扣Email通知 为NULL表示无此服务
    //WINDOW_TIME	Long	Y	窗口时间 秒

    //String isentrust
   // String entrustAccount
   // String entrustUsercode

    static constraints = {
        gatherWay(nullable:true)
         procedureFee(nullable:true)
         perprocedureFee(nullable:true)
         limitMoney(nullable:true)
         dayLimitTrans(nullable:true)
         dayLimitMoney(nullable:true)
         monthLimitTrans(nullable:true)
         monthLimitMoney(nullable:true)
         yearlyPayment(nullable:true)
         dangerMoney(nullable:true)
         settWay(nullable:true)
         backFee(nullable:true)
         settCycle(nullable:true)
         settCycleDay(nullable:true)
         remark(nullable:true)
         batchVersion(nullable:true)
          messageNotify(nullable:true)// 短信通知
         templateType(nullable:true)  //模板类型
         ismpCheck(nullable:true)     //商户后台审核
         bossCheck(nullable:true)     //BOSS后台审核
         batchChannel(nullable:true)  //批量代付
         singleChannel(nullable:true)     //单笔代付
         interfaceChannel(nullable:true)   //接口代付
         batchPubFee(nullable:true)         //批量对公手续费
         batchPriFee(nullable:true)         //批量对私手续费
         singlePubFee(nullable:true)        //单笔对公手续费
         singlePriFee(nullable:true)       //单笔对私手续费
         interfacePubFee(nullable:true)   //接口对公手续费
         interfacePriFee(nullable:true)   //接口对私手续费
          isDq(nullable:true)//是否代扣 0 否 1:是
         userCompactno(nullable:true)//用户协议号
         dqAccountId(nullable:true) //代扣账号ID
          pubLimitMoney(nullable:true)    //对公单笔限额
         priLimitMoney(nullable:true) //对私单笔限额
        windowTime(nullable: true)    //窗口时间
        isMailMessage(nullable:true)//是否邮件通知
        entrustEmail(nullable:true) //邮件通知的Email

        // 附加 证书信息
        certName(nullable: true)
        certPath(nullable: true)
        certDate(nullable: true)

    }
    def static serviceParamsMap = ['0': '是', '1': '否']
}
