package com.waterelephant.loanwallet.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LoanWalletLogUtil {
	Logger logger = null;
	
	public LoanWalletLogUtil(Class<?> clazz){
		logger = Logger.getLogger(clazz);
	}
	
	public void info(String message){
		logger.info(getTransNo()+message);
	}
	
	public void error(String message, Throwable t){
		logger.error(getTransNo()+message, t);
	}
	
	public void debug(String message){
		logger.debug(message);
	}
	
	public String get() {
		return ThreadLocalUtil.get();
	}

	public void set(String value) {
		ThreadLocalUtil.set(value);
	}
	
	public void remove(){
		ThreadLocalUtil.remove();
	}
	
	public String getTransNo(){
		
		if (StringUtils.isBlank(ThreadLocalUtil.get())) {
			return "";
		}
		
		String tname = "["+Thread.currentThread().getName()+"-"+Thread.currentThread().getId()+"]";
		return tname+" [APP-LOANWALLET-"+ThreadLocalUtil.get()+"]";
	}
}