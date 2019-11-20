package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwJdOrderList;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwJdOrderListService;

@Service
public class BwJdOrderListServiceImpl extends BaseService<BwJdOrderList, Long> implements BwJdOrderListService {

	/**
	 * 
	 * @see com.waterelephant.service.BwJdOrderListService#findListByAttr(com.waterelephant.entity.BwJdOrderList)
	 */
	@Override
	public List<BwJdOrderList> findListByAttr(BwJdOrderList bwJdOrderList) {
		return mapper.select(bwJdOrderList);
	}

}