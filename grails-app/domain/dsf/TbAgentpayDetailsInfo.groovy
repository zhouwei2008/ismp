package dsf

import ismp.CmCustomer

/**
 * Created by IntelliJ IDEA.
 * User: wz
 * Date: 11-6-14
 * Time: 上午11:53
 * To change this template use File | Settings | File Templates.
 */
class TbAgentpayDetailsInfo {
    static mapping = {
        //table 'TB_AGENTPAY_DETAILS_INFO'
        version false
        id generator: 'assigned', column:'DETAIL_ID'
        tablePerHierarchy false
  }
               String id //批量明细ID
                //String batchId  //批量ID
               String tradeNum   //记录序号，同一个文件内必须唯一。建议从1开始递增
               String tradeBankcode  //暂不用 银行代码
               String tradeCardtype  //暂不用 00银行卡，01存折
               String tradeCardnum   //银行卡或存折号码
               String tradeCardname  //银行卡或存折上的所有人姓名
               String tradeBranchbank   //开户行分行
               String tradeSubbranchbank  //开户行支行
               String tradeAccountname  //开户行名称
               String tradeAccounttype  //账户类型：0私，1公
               Double tradeAmount       //整数，单位元 例如：1000.00
               String tradeAmounttype   //人民币：CNY 元。
               String contractCode      //暂不用 尽量提供
               String contractUsercode  //用户协议号 (代收必须)
               String certificateType   //证件类型
               String certificateNum    //证件号
               String tradeMobile       //联系电话
               String tradeRemark       //用途
               String tradeFeedbackcode //反馈状态  null :处理中 succ:成功 fail :失败
               String tradeReason       //反馈原因
               String tradeStatus       //交易状态:['0':'待确认','1':'待审核','2':'待处理','3':'商户拒绝','4':'吉高拒绝','5':'吉高审核通过','6':'处理中','7':'处理完毕']
               Double tradeFee          //手续费
               Date tradeSysdate          //业管审核时间 _old


               String batchBizid          //商户编号
               String tradeFeestyle       //T：退手续费
               String tradeFeetype         //0：即扣；1：后返
               Double tradeAccamount       //实际交易金额
               String tradeType            //交易类型 S，收；F，付
               String tradeCustorder   //商户订单号
               dsf.TbAgentpayInfo batch
               static belongsTo = dsf.TbAgentpayInfo
               String tradeProvince  //开户所在省
               String tradeCity      //开户所在市
               String errMsg         //错误信息
               String tradeCommtype  //商户审核方式 0 手工 : 1 自动
               String tradeSystype   //业管审核方式 0 手工 : 1 自动
               Date tradeSubdate     //商户提交时间
               Date tradeCommdate    //商户审核时间
               String tradeCommsuggest //商户审核意见
               String tradeSyschkname   //业管审核人
               Date tradeSyschkdate    //业管审核时间
               String refundFirstname   //退款初核人
               String refundLastname    //退款终审人
               Date tradeRefusedate   //退款时间
               Date tradeDonedate      //交易完毕时间
               String tradeRefued      //退款状态 0：待退款 （对应）  1:退款已初审  2：已退款
               String isEntrust        //委托代扣关联订单批次号
               String entrustEmail     //Email 委托代扣失败发Email通知
               String isSms     //短信服务    null:无此服务  0：具有短信通知的服务
               String tradeRemark1     //预留字段1
               String tradeRemark2
               String tradeRemark3
               String tradeRemark4
               String tradeRemark5
               String payStatus
               String dkPcNo
               String fcheckName// 初审人
               String tcheckName// 终审人
               Long tradeNumOrder       //排序号
               String messageNotify    //是否已经发送短信通知     0,未发送 1,已发送
               String sendToSet     //发送清结算系统　０未发送，１已发送
               String dataStatus   //新旧数据区分(old 旧数据,为空或是为new新数据)
           // String tkFcheckName//退款初审人
            //String tkTcheckName//退款终审人
           // String checkfailReson
     public String toString(){
        return String.format("[序号：%s;银行账户:%s;开户名:%s;开户行:%s;分行:%s;支行:%s;\n " +
            			"类型:%s;金额:%1.2f;币种:%s;省:%s;市:%s;手机号:%s;证件类型:%s;证件号:%s;\n " +
            			"协议号:%s;商户订单号:%s;用途:%s;错误信息:%s]",
            			this.tradeNum, this.tradeCardnum, this.tradeCardname, this.tradeAccountname, this.tradeBranchbank, this.tradeSubbranchbank, this.tradeAccounttype,
            			this.tradeAmount, this.tradeAmounttype, this.tradeProvince, this.tradeCity, this.tradeMobile, this.certificateType, this.certificateNum,
                        this.contractUsercode, this.tradeCustorder, this.tradeRemark, this.errMsg);
    }
  static constraints = {
	  tradeNum(size:1..6,nullable:false)
	  tradeBankcode(size:1..3,nullable:true)
	  tradeCardtype(size:1..2,nullable:true)
	  tradeCardnum(size:1..32,nullable:true)
	  tradeCardname(size:1..50,nullable:true)
	  tradeBranchbank(size:1..50,nullable:true)
	  tradeSubbranchbank(size:1..50,nullable:true)
	  tradeAccountname(size:1..50,nullable:true)//SYW 农村信用社
	  tradeAccounttype(size:1..1,nullable:true)
	  tradeAmount(nullable:true)
	  tradeAmounttype(size:1..3,nullable:true)
	  contractCode(size:1..60,nullable:true)
	  contractUsercode(size:1..30,nullable:true)
	  certificateType(size:1..1,nullable:true)
	  certificateNum(size:1..22,nullable:true)
	  tradeMobile(size:1..13,nullable:true)
	  tradeRemark(size:1..50,nullable:true)
	  tradeFeedbackcode(size:1..10,nullable:true)
	  tradeReason(size:1..30,nullable:true)
	  tradeRemark1(size:1..30,nullable:true)
	  tradeRemark2(size:1..30,nullable:true)
      tradeRemark3(size:1..30,nullable:true)
      tradeRemark4(size:1..30,nullable:true)
      tradeRemark5(size:1..30,nullable:true)
	  tradeStatus(size:1..1,nullable:true)
	  tradeSysdate(nullable:true)

      batchBizid(size:1..15,nullable:true)
      tradeFee(nullable:true)
      tradeFeestyle(size:1..1,nullable:true)
     tradeFeetype(size:1..1,nullable:true)
     tradeAccamount(nullable:true)
     payStatus(size:1..1,nullable:true)
     dkPcNo(nullable:true)
     tradeType(nullable: true)
     tradeCustorder(size:1..50,nullable:true)
     tradeProvince(size:1..50,nullable:true)
      tradeCity(size:1..50,nullable:true)
      errMsg(size:1..200,nullable:true)
      tradeCommtype(size:1..1,nullable:true)
      tradeSystype(size:1..1,nullable:true)
      tradeSubdate(nullable:true)
      tradeCommdate(nullable:true)
      tradeCommsuggest(size:1..200,nullable:true)
      tradeSyschkname(size:1..50,nullable:true)
      tradeSyschkdate(nullable:true)
      refundFirstname(size:1..50,nullable:true)
      refundLastname(size:1..50,nullable:true)
      tradeRefusedate(nullable:true);
      tradeDonedate(nullable:true)
      tradeRefued(size:1..1,nullable:true)
      isEntrust(size:1..20,nullable:true)
      entrustEmail(size:1..100,nullable:true)
      isSms(size:1..1,nullable:true)
      //checkfailReson(nullable:true)
      fcheckName(size:1..50,nullable:true)
      tcheckName(size:1..50,nullable:true)
      tradeNumOrder(size:1..50,nullable:true)
       messageNotify(nullable:true)
      sendToSet(nullable:true)
      dataStatus(nullable:true)
      //tkFcheckName(size:1..50,nullable:true)
      //tkTcheckName(size:1..50,nullable:true)
  }



//    def static tradeStatusMap = ['0':'待确认','1':'待审核','2':'待处理','3':'商户拒绝','4':'吉高拒绝','5':'吉高审核通过','6':'处理中','7':'处理完毕']
    def static tradeStatusMap = ['0':'待确认','1':'待审核','2':'待处理','3':'商户拒绝','4':'吉高拒绝','5':'吉高审核通过','6':'清算成功','7':'处理完毕']
    //def static certificateTypeMap = ['身份证':'身份证','户口簿':'户口簿','护照':'护照','军官证':'军官证','士兵证':'士兵证','台胞证':'台胞证']
    def static certificateTypeMap = ['0':'身份证','1':'户口簿','2':'护照','3':'军官证','4':'士兵证','5':'台胞证']
    def static accountTypeMap = ['0': '个人', '1': '企业']
    def static merStatusMap = ['0':'待处理','1':'处理中','2':'处理完毕']
    def static dkStatusMap = ['0':'待处理','1':'初审通过','2': '初审拒绝', '3': '终审通过','4':'终审拒绝','5':'已处理','6':'交易成功','7':'待退款' ,'8':'退款初审通过','9':'已退款','10':'待打款']
    def static dkTypeMap = ['05':'代收','06': '代付']
    def static typeMap=['F':'代付','S':'代收']
    def static skYwTypeMap=['S':'代收']
    def static fkYwTypeMap=['F':'代付']
    def static dkStyleMap=['F':'付款','S':'收款']
    def static tradeFeeMap=['T':'退手续费','':'不退手续费']
    def static feeTypeMap=['0':'即扣','1':'后返']
    def static cardTypeMap=['16':'贷记卡','19':'借记卡']
    def static serviceMap = ['agentcoll':'代收','agentpay': '代付']
    def static tradeFeedbackcodeMap = ['成功':'成功','失败':'失败']
}
