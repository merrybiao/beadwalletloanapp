/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.entity.response;

import java.util.Map;

/**
 * 
 * 
 * Module:
 * 
 * ResponseContract.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @param <E>
 * @description: <描述>
 */
public class ResponseContract {
	private Map<String, String> contractUrls;// 合同地址集合

	public Map<String, String> getContractUrls() {
		return contractUrls;
	}

	public void setContractUrls(Map<String, String> contractUrls) {
		this.contractUrls = contractUrls;
	}

}
