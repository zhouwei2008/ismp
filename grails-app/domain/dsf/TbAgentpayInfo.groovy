package dsf

class TbAgentpayInfo {

    static mapping = {
        table 'TB_AGENTPAY_INFO'
        version false
        id generator: 'assigned', column:'BATCH_ID'
    }
    String id           //批量交易ID
    String batchBizid    //商户编号
    String batchType     //类型F付S收
    String batchVersion//暂时不用 批量版本号
    String batchDate     //录入日期
    String batchCurrnum//暂时不用 当日批次号
    Long batchCount    //总记录数
    Double batchAmount //总金额
    String batchStatus //状态 0 待确认  1 待审核  2 商户审核拒绝  3 处理中 4 交易完毕
    Long transSucnum   //暂时不用 交易成功数
    Long transFailnum  //暂时不用  交易失败数
    Double transSucamount  //暂时不用 成交金额
    Double transFailamount //暂时不用 待交易金额
    String batchRemark    //说明
    String batchRemark1   //说明1
    String batchRemark2   //说明2
    String batchRemark3   //说明3
    Date batchSysdate     //提交时间
    String batchBiztype   //暂时不用 业务类型
    Date batchFinishdate  //交易完毕时间
    Double batchFee       //手续费金额
    String batchFeetype   //0,即扣 1,后返
    Double batchAccamount //实付金额
    String batchFilename  //文件名
    String batchSrc              //batch 批量 single 单笔  interface 接口     varchar2
    Date batchChkdate          //商户审核时间  DATE
    String batchSend             //是否已发送  0未发送 1 已发送   varchar2(1)
    String batchUsrChk           //商户审批方式  0自动 1 手动     varchar2(1)
    String batchSysChk   // 是否系统自动审核 0 自动 1 手动
    Date  chargeDate    //委托代扣单生成时间
    String dataStatus   //新旧数据区分(old 旧数据,为空或是为new新数据)
     static constraints = {
        batchStatus(nullable: true)
        transSucnum(nullable: true)
        transFailnum(nullable: true)
        transSucamount(nullable: true)
        transFailamount(nullable: true)
        batchRemark(nullable: true)
        batchRemark1(nullable: true)
        batchRemark2(nullable: true)
        batchRemark3(nullable: true)
        batchSysdate(nullable: true)
        batchFinishdate(nullable: true)
        batchFee(nullable: true)
        batchFeetype(nullable: true)
        batchAccamount(nullable: true)
        batchFilename(nullable:true)
        batchSrc(nullable:true)
        batchChkdate(nullable:true)
        batchSend(nullable:true)
        batchUsrChk(nullable:true)
        batchSysChk(nullable:true)
        batchBiztype(nullable:true)
        batchCurrnum(nullable:true)
        chargeDate(nullable:true)
        dataStatus(nullable:true)
      }
     List details = new ArrayList();

    public void addDetail(TbAgentpayDetailsInfo detail){
        this.details.add(detail);
    }
    static hasMany = [tbAgentpayDetailsInfos : TbAgentpayDetailsInfo ]
    //TbAgentpayDetailsInfo tbAgentpayDetailsInfo

    def static batchTypeMap = ['S':'代收','F': '代付']
    def static  feeTypeMap = ['0':'即扣','1':'后返']
//    def static statusMap = ['0':'待确认','1':'待审核','2':'商户审核拒绝','3':'处理中','4':'交易完毕']
    def static statusMap = ['1':'待审核','2':'商户审核拒绝','3':'商户审核通过','4':'出款成功']
    def static srcMap=['batch':'批量','single':'单笔','interface':'接口']
}
