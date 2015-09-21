package dsf

import dsf.TbBankcardInfo
import dsf.TbAdjustBankCard
/**
 * Created by IntelliJ IDEA.
 * User: test
 * Date: 12-2-27
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
   public  class BCard {
   public  List tabcList(){
       def tabc=TbAdjustBankCard.executeQuery("select bankNames,bankOrgCode from TbAdjustBankCard");
       return tabc;
   }
   public  List tbiList(){
       def tbi=TbBankcardInfo.executeQuery("select substr(bankOrgCode,0,4) bankOrgCode,bankCardLen,bankCardVal,bankCardType from TbBankcardInfo ");
       return tbi;
     }
    }
