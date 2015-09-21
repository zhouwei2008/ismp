package com.ecard.products.pay.web.servlet.env;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import boss.BoAgentPayServiceParams;
import ismp.CmCustomer;
import org.apache.log4j.Logger;



public class SessionUserBean implements Serializable {
	
	private static final Logger logger = Logger.getLogger(SessionUserBean.class);

	private CmCustomer cmCustomer = null;
	private BoAgentPayServiceParams agentServices = null;
    private Long dayCount = null;
    private Double dayAmount = null;

	
	public SessionUserBean(CmCustomer cmCustomer, BoAgentPayServiceParams agentServices, Long dayCount, Double dayAmount){
		this.cmCustomer = cmCustomer;
        this.agentServices = agentServices;
        this.dayCount = dayCount;
        this.dayAmount = dayAmount;
	}

	public BoAgentPayServiceParams getBoAgentPayServiceParams() {
		return this.agentServices;
	}

	public CmCustomer getCmCustomer() {
		return this.cmCustomer;
	}
    public Long getDayCount(){
        return this.dayCount;
    }
    public Double getDayAmount(){
        return this.dayAmount;
    }

}
