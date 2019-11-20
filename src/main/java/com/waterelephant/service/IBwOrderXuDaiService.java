package com.waterelephant.service;

/**
 * 续贷
 * 
 * @author wrh
 *
 */
public interface IBwOrderXuDaiService {

	int updOrderXudai(Long orderId);

	/**
	 * 当前续贷次数
	 * 
	 * @param id
	 * @return
	 */
	int queryXudaiTerm(Long id);

	/**
	 * 当前续贷次数
	 * 
	 * @param orderId
	 * @return
	 */
	int queryCurrentXudaiTermByOrderId(Long orderId);
}
