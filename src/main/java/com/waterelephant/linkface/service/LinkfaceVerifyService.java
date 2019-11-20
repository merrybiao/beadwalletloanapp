package com.waterelephant.linkface.service;

import com.waterelephant.linkface.entity.LinkfaceVerify;

/**
 * linkface - 活体检测记录事物处理接口 code0088
 * 
 * 
 * Module: 
 * 
 * LinkfaceVerifyService.java 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface LinkfaceVerifyService {

	/**
	 * linkface - 保存活体检测记录
	 * @param faceidVerfiy
	 */
	public void saveLinkfaceVerify(LinkfaceVerify linkfaceVerify);
}
