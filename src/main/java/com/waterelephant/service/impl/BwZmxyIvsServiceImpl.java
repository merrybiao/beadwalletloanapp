package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwZmxyIvs;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwZmxyIvsService;
@Service
public class BwZmxyIvsServiceImpl extends BaseService<BwZmxyIvs, Long> implements BwZmxyIvsService{

	@Override
	public int save(BwZmxyIvs bwZmxyIvs) {
		return mapper.insert(bwZmxyIvs);
	}

	@Override
	public BwZmxyIvs findByNameAndIdCard(String name, String idCard) {
		BwZmxyIvs bwZmxyIvs = new BwZmxyIvs();
		bwZmxyIvs.setIdCard(idCard);
		bwZmxyIvs.setName(name);
		return mapper.selectOne(bwZmxyIvs);
	}

	@Override
	public int update(BwZmxyIvs bwZmxyIvs) {
		return mapper.updateByPrimaryKey(bwZmxyIvs);
	}

}
