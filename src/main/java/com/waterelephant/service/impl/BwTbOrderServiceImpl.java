package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwTbOrder;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwTbOrderService;

@Service
public class BwTbOrderServiceImpl extends BaseService<BwTbOrder, Long> implements BwTbOrderService {

	/**
	 * 
	 * @see com.waterelephant.service.BwTbOrderService#findListByAttr(com.waterelephant.entity.BwTbOrder)
	 */
	@Override
	public List<BwTbOrder> findListByAttr(BwTbOrder bwTbOrder) {
		return mapper.select(bwTbOrder);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwTbOrderService#findByAttr(com.waterelephant.entity.BwTbOrder)
	 */
	@Override
	public BwTbOrder findByAttr(BwTbOrder bwTbOrder) {
		return mapper.selectOne(bwTbOrder);
	}

}