package com.ecard.products.pay.data.analyse;

import com.ecard.products.pay.web.servlet.env.SessionUserBean;

/**
 * 
 * @author tkZhu
 * 
 * 商户号为201103000001的某商户于2011年3月30日提交的代收文件的文件名为：
 * 
 * 文件名：201103000001_S0220110330
 *
 * 文件头：S, 201103000001, 20110330,2,300,
 * 
 * 文件明细：000001,,105,00,62270000***********,李四,北京,北京,中国建设银行,0,100,CNY,,,,,,,,,
 */
public abstract class ResponsibilityChain {
	
	private ResponsibilityChain next = null;
	public ResponsibilityChain(ResponsibilityChain next) {
		this.next = next;
	}
	
	public String process(String r){
		
		String msg = processSelf(r);
		if(msg == null){
			if(next!=null){
				next.setUserBean(this.getUserBean());
				return next.process(r);
			}else{
				return null;
			}
		}else{
			return msg;
		}
	}
	
	public abstract String processSelf(String r);
	
	private SessionUserBean userBean = null;
	public void setUserBean(SessionUserBean userBean) {
		this.userBean = userBean;
	}
	public SessionUserBean getUserBean() {
		return this.userBean;
	}
	
	
}
