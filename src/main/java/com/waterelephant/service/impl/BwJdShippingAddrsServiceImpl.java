package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwJdShippingAddrs;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwJdShippingAddrsService;

@Service
public class BwJdShippingAddrsServiceImpl extends BaseService<BwJdShippingAddrs, Long>
		implements BwJdShippingAddrsService {

	/**
	 * 
	 * @see com.waterelephant.service.BwJdShippingAddrsService#findListByAttr(com.waterelephant.entity.BwJdShippingAddrs)
	 */
	@Override
	public List<BwJdShippingAddrs> findListByAttr(BwJdShippingAddrs bwJdShippingAddrs) {
		return mapper.select(bwJdShippingAddrs);
	}

}