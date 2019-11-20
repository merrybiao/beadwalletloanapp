package com.waterelephant.service.impl;

import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwProductDictionaryService;

import org.springframework.stereotype.Service;

/**
 * @since 2018-07-18
 */
@Service
public class BwProductDictionaryServiceImpl extends BaseService<BwProductDictionary, Long> implements BwProductDictionaryService{

	@Override
	public BwProductDictionary findById(Long id) {
		return selectByPrimaryKey(id);
	}

	@Override
	public BwProductDictionary findBwProductDictionaryById(Integer productId) {
		return selectByPrimaryKey(productId.longValue());
	}

	
	
	@Override
	public BwProductDictionary queryByOrderId(Long orderId) {
		return sqlMapper.selectOne("SELECT t1.* FROM bw_product_dictionary t1 WHERE t1.id = (SELECT t2.product_id FROM bw_order t2 WHERE t2.id ="+ orderId +")",BwProductDictionary.class);
	}

}
