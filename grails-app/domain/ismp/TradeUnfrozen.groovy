package ismp

class TradeUnfrozen extends TradeBase{

    String  unfrozenType
    String  unfrozenParams
    String  unfrozenStatus
    Long    handlePperId
    String  handleOperName
    String  handleBatch
    String  handleStatus
    Date    handleTime

    static constraints = {
        unfrozenType(maxSize: 16)
        unfrozenParams(maxSize: 512)
        unfrozenStatus(maxSize: 16)
        handlePperId(nullable: true)
        handleOperName(maxSize: 16)
        handleBatch(maxSize: 16)
        handleStatus(maxSize: 16,inList: ['waiting','checked','submited','completed'])
        handleTime(nullable: true)
    }

    def static handleStatusMap = ['waiting': '待处理', 'checked': '已审核','submited':'已提交','completed':'完毕']
}
