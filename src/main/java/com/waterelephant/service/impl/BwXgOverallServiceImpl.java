package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgOverall;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgOverallService;

@Service
public class BwXgOverallServiceImpl extends BaseService<BwXgOverall, Long> implements BwXgOverallService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgOverallService#findByAttr(com.waterelephant.entity.BwXgOverall)
	 */
	@Override
	public BwXgOverall findByAttr(BwXgOverall bwXgOverall) {
		return mapper.selectOne(bwXgOverall);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwXgOverallService#findListByAttr(com.waterelephant.entity.BwXgOverall)
	 */
	@Override
	public List<BwXgOverall> findListByAttr(BwXgOverall bwXgOverall) {
		return mapper.select(bwXgOverall);
	}

}
