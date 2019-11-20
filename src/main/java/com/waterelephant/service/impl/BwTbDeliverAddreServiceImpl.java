package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwTbDeliverAddre;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwTbDeliverAddreService;

@Service
public class BwTbDeliverAddreServiceImpl extends BaseService<BwTbDeliverAddre, Long>
		implements BwTbDeliverAddreService {

	/**
	 * 
	 * @see com.waterelephant.service.BwTbDeliverAddreService#findListByAttr(com.waterelephant.entity.BwTbDeliverAddre)
	 */
	@Override
	public List<BwTbDeliverAddre> findListByAttr(BwTbDeliverAddre bwTbDeliverAddre) {
		return mapper.select(bwTbDeliverAddre);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwTbDeliverAddreService#findByAttr(com.waterelephant.entity.BwTbDeliverAddre)
	 */
	@Override
	public BwTbDeliverAddre findByAttr(BwTbDeliverAddre bwTbDeliverAddre) {
		return mapper.selectOne(bwTbDeliverAddre);
	}

}