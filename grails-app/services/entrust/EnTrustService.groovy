package entrust

import dsf.TbAgentpayInfo
import org.apache.log4j.Logger
import groovy.sql.Sql
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils

class EnTrustService {

    static transactional = true
    private Logger logger = Logger.getLogger(EnTrustService.class)
    def entrustClientService
    /**
     * 批量入库
     * @param batch
     * @return
     * @throws RuntimeException
     */
    def saveBatch(TbAgentpayInfo batch) throws RuntimeException{
        try{
            // 保存代收付对象
            String suffix = batch.getBatchType()=='F'?'06':'05' //Constants.Services.sf.get(batch.getBatchType());
            String batchId = createBatchNo()
            batch.id = batchId + suffix
            logger.info("批次Id = " + batch.id)
            batch.details.each {
                it.id =  createDetailsNo() + suffix
                logger.info("批次明细Id = " + it.id)
                it.batch = batch
                batch.addToTbAgentpayDetailsInfos(it)
            }
            batch.save(failOnError:true)
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
    * 生成批次交易号，15位
     * SQL: SELECT '10'||TO_CHAR(sysdate,'yymmdd')||LPAD(SEQ_AGENTPAY.nextval,5,'0') AS id FROM DUAL
    * @return 交易号
    */
    def createBatchNo() {
       def ds=DatasourcesUtils.getDataSource('dsf')
       def sql = new Sql(ds)
       def seq = sql.firstRow('SELECT \'10\'||TO_CHAR(sysdate,\'yymmdd\')||LPAD(SEQ_AGENTPAY.nextval,5,\'0\') AS ID FROM DUAL')['ID']
       return seq
    }

    /**
    * 生成明细交易号，17位
     * SQL：SELECT '10'||TO_CHAR(sysdate,'yymmdd')||LPAD(SEQ_AGENTPAY.nextval,5,'0') AS id FROM DUAL
    * @return 交易号
    */
     def createDetailsNo() {
       def ds=DatasourcesUtils.getDataSource('dsf')
       def sql = new Sql(ds)
       def seq = sql.firstRow('SELECT \'10\'||TO_CHAR(sysdate,\'yymmdd\')||LPAD(SEQ_AGENTPAYDETAILS.nextval,7,\'0\') AS ID FROM DUAL')['ID']
       return seq
    }



}
