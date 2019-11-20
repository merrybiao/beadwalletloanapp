package com.waterelephant.service;

import com.waterelephant.entity.BwProductDictionary;

public interface BwProductDictionaryService {

	//根据产品id查询合同利率
	BwProductDictionary findById(Long id);
	/**
	 * 根据id查询相应产品信息
	 */
	BwProductDictionary findBwProductDictionaryById(Integer productId);
	
	
	/**
	 * 根据工单ID查对应产品
	 * 
	 * @param orderId
	 * @return
	 */
	BwProductDictionary queryByOrderId(Long orderId);
	
}
