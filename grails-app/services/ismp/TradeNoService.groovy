package ismp

import groovy.sql.Sql
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils

class TradeNoService {

    static transactional = false

    def dataSource

    def tradeTypeMap = [
            payment		: '10',
            charge			: '11',
            transfer		: '12',
            refund			: '13',
            withdrawn   : '14',
            frozen			: '15',
            unfrozen		: '16',
            royalty			: '20',
            royalty_rfd	: '21'
    ]

    /**
    * 生成交易号，15位，'12'+6位年月日+ 0.... + 序号
    * @return 交易号
    */
    def createTradeNo(tradeType, time = new Date()) {
        def prefix = tradeTypeMap[tradeType]
        def middle = new java.text.SimpleDateFormat('yyMMdd').format(time) // yymmdd
        def ds=DatasourcesUtils.getDataSource('ismp')
        def sql = new Sql(ds)
        def seq = sql.firstRow('select seq_trade_no.NEXTVAL from DUAL')['NEXTVAL']
        prefix + middle + seq.toString().padLeft(7, '0')
    }
}
