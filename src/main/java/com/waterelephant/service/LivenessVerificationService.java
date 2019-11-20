package com.waterelephant.service;

/**
 * 活体检测
 * @author dinglinhao
 * @date 2018年11月29日10:57:39
 * @version 1.0
 * @since 1.8
 *
 */
public interface LivenessVerificationService {
	
	/**
	 * 根据工单做活体检测
	 * @param orderNo
	 */
	boolean savelivenessVerificationByOrder(String orderNo);
	/**
	 *  <p>授信单活体检测
	 * @param creditNo 授信单
	 * @return
	 */
	boolean savelivenessVerificationBycreditNo(String creditNo);
}
