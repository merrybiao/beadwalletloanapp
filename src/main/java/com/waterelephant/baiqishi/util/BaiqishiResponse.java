package com.waterelephant.baiqishi.util;

import java.io.Serializable;
import java.util.Map;

/**
 * BaiqishiResponse.java
 * @author dinglinhao
 * @since JDK 1.8
 * @desc 白骑士响应类
 * @date 2018年5月17日15:14:38
 *
 */
public class BaiqishiResponse implements Serializable {
	
	private static final long serialVersionUID = 3784884075892585548L;
	//结果码
	private String resultCode;
	//结果描述
	private String resultDesc;
	//具体结果
	private Map<String,Object> data;
	
	public BaiqishiResponse() {}
	
	public BaiqishiResponse(String code,String msg) {
		
		this.resultCode = code;
		
		this.resultDesc = msg;
	}
	
	public BaiqishiResponse(String code,String msg,Map<String,Object> data) {
		
		this.resultCode =  code;
		
		this.resultDesc = msg;
		
		this.data = data;
	}
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	

}
