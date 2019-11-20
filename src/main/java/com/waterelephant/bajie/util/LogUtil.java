package com.waterelephant.bajie.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LogUtil {
	Logger logger = null;
	
	public LogUtil(Class<?> clazz){
		logger = Logger.getLogger(clazz);
	}
	
	public void debug(String message){
		logger.debug(getTransNo()+message);
	}
	
	public void info(String message){
		logger.info(getTransNo()+message);
	}
	
	public void error(String message, Throwable t){
		logger.error(getTransNo()+message, t);
	}
	
	public void set(String transNo){
		ThreadLocalUtil.set(transNo);
	}
	
	public void remove(){
		ThreadLocalUtil.remove();
	}
	
	public String getTransNo(){
		String tname = "["+Thread.currentThread().getName()+"-"+Thread.currentThread().getId()+"]";
		if (StringUtils.isBlank(ThreadLocalUtil.get())) {
			return tname;
		}
		
		return tname+" [APP-"+ThreadLocalUtil.get()+"]";
	}
}