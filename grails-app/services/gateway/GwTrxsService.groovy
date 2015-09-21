package gateway

class GwTrxsService {

    static transactional = true
    def dataSource_ismp

    def serviceMethod() {

    }

    def searchGwtrxs(String id)
    {
        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)

        def trxsSql = """select t.id,
                            t.gworders_id,
                            t.trxnum,
                            t.trxtype,
                            t.channel,
                            t.payment_type,
                            t.paymode,
                            decode(t.amount, null, 0, t.amount) as amount,
                            t.currency,
                            t.servicecode,
                            t.acquirer_id,
                            t.acquirer_code,
                            t.acquirer_name,
                            t.acquirer_merchant,
                            t.acquirer_seq,
                            t.acquirer_date,
                            t.acquirer_msg,
                            t.submitdates,
                            t.payer_ip,
                            t.refnum,
                            t.authcode,
                            t.fromacctid,
                            t.fromacctnum,
                            t.buyer_id,
                            t.buyer_name,
                            t.payinfo,
                            t.createdate,
                            t.closedate,
                            t.trxsts,
                            t.opers,
                            t.operdate,
                            t.version,
                            t.trxdesc
                       from gwtrxs t
                       where t.gworders_id = '""" + id + """'
                       and t.trxsts = 1 """

        def trxsList = dbIsmp.rows(trxsSql)
        return trxsList
    }

    def searchGwChannel(String id)
    {
        def dbIsmp =  new groovy.sql.Sql(dataSource_ismp)

        def channelSql = """select t.channel_type, t.terminal
                       from gwchannel t
                       where t.id = '""" + id + """'"""

        def channelList = dbIsmp.rows(channelSql)
        return channelList
    }
}
