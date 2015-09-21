package ismp

class TradeFrozen extends TradeBase{

    String  frozenType
    String  frozenParams
    String  frozenStatus
    Long    unfrozenAmount=0
    Long    handleOperId
    String  handleOperName
    String  handleBatch
    String  handleStatus
    Date    handleTime


    static constraints = {
        frozenType(maxSize:16)
        frozenParams(maxSize:512)
        frozenStatus(maxSize:16)
        handleOperId(nullable: true)
        handleOperName(maxSize:16,nullable: true)
        handleBatch(maxSize:16,nullable: true)
        handleStatus(maxSize:16,nullable: true)
        handleTime(nullable: true)
    }
    def static handleStatusMap = ['waiting': '待处理', 'checked': '已审核','submited':'已提交','completed':'完毕']
}
