package dsf

import boss.BoAgentPayServiceParams
import com.ecard.products.constants.Constants

/**
 * Created by IntelliJ IDEA.
 * User: test
 * Date: 12-2-27
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
    public  class BAPSParams {
        def byBAParams(String customerId){
            def baps=BoAgentPayServiceParams.findWhere(customerId:customerId,serviceCode:"agentcoll",isCurrent:true,enable:true);
            return baps;
        }
    }
