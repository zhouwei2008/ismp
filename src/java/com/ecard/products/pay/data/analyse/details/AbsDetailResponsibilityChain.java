package com.ecard.products.pay.data.analyse.details;

import java.util.HashSet;
import java.util.Set;


public abstract class AbsDetailResponsibilityChain{

	private AbsDetailResponsibilityChain next = null;
	
	public AbsDetailResponsibilityChain(AbsDetailResponsibilityChain next) {
		this.next = next;
	}

	public abstract String processSelf(String r, int count);
	
	public String process(String r, int count) {
		String msg = processSelf(r, count);
		if(msg == null){
			if(next!=null){
				next.setNumSet(numSet);
				return next.process(r, count);
			}else{
				return null;
			}
		}else{
			return msg;
		}
	}
	
	private Set numSet = new HashSet();

	public Set getNumSet() {
		return numSet;
	}

	public void setNumSet(Set numSet) {
		this.numSet = numSet;
	}
	
}
