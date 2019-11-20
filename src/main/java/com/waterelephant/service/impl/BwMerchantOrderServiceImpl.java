/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwMerchantOrder;
import com.waterelephant.service.IBwMerchantOrderService;

/**
 * 
 * 
 * Module:
 * 
 * BwMerchantOrderServiceImpl.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwMerchantOrderServiceImpl extends BaseCommonServiceImpl<BwMerchantOrder, Long>
		implements IBwMerchantOrderService {

	/**
	 * 
	 * @see com.waterelephant.service.IBwMerchantOrderService#selectByAtt(com.waterelephant.entity.BwMerchantOrder)
	 */
	@Override
	public BwMerchantOrder selectByAtt(BwMerchantOrder bwMerchantOrder) {

		String sql = "select * from bw_merchant_order where order_id = " + bwMerchantOrder.getOrderId() + " limit 1";

		return sqlMapper.selectOne(sql, BwMerchantOrder.class);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwMerchantOrderService#insertByAtt(com.waterelephant.entity.BwMerchantOrder)
	 */
	@Override
	public Integer insertByAtt(BwMerchantOrder bwMerchantOrder) {
		return mapper.insert(bwMerchantOrder);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwMerchantOrderService#updateByAtt(com.waterelephant.entity.BwMerchantOrder)
	 */
	@Override
	public Integer updateByAtt(BwMerchantOrder bwMerchantOrder) {
		return mapper.updateByPrimaryKey(bwMerchantOrder);
	}

}
