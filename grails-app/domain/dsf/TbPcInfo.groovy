package dsf

class TbPcInfo {
       static mapping = {
        table 'TB_PC_INFO'
        version false
        id generator: 'assigned', column:'TB_PC_ID'
    }
    Date tbPcDate //日期
    Integer tbPcItems //笔数
    Double tbPcAmount //金额
    Double tbPcFee //手续费
    Double tbPcAccamount //实付手续费
    Integer tbPcDkChanel //打款渠道（银行ID）
    String tbPcDkStatus //打款状态: 0 等待打款 1 打款完成  2 已发送银行  3 对账完毕
    String tbPcDkChanelname //打款渠道（银行名）
    String tbPcCheckStatus //对账状态 0：不可对账1：未对账2：对账完毕
    String tbSfFlag	//收付标识
    String tbDealstyle	//处理方式


    static constraints = {
        tbPcDate(nullable:true)
        tbPcItems(nullable:true)
        tbPcAmount(nullable:true)
        tbPcFee(nullable:true)
        tbPcAccamount(nullable:true)
        tbPcDkChanel(nullable:true)
        tbPcDkStatus(size:1..1,nullable:true)
        tbPcDkChanelname(size:1..40,nullable:true)
        tbPcCheckStatus(size:1..1,nullable:true)
        tbSfFlag(size:1..1,nullable:true)
        tbDealstyle(size:1..8,nullable:true)
    }
    def static tbPcDkMap = ['0':'等待打款','1': '打款完成','2': '已发送银行','3': '对账完毕']
}
