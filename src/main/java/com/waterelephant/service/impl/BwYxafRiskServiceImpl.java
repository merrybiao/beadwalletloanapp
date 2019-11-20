package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwYxafRisk;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwYxafRiskService;
@Service
public class BwYxafRiskServiceImpl extends BaseService<BwYxafRisk, Long> implements BwYxafRiskService{

	@Override
	public int save(BwYxafRisk bwYxafRisk) {
		return mapper.insert(bwYxafRisk);
	}

	@Override
	public BwYxafRisk findByNameAndIdCard(String name, String idCard) {
		BwYxafRisk bwYxafRisk = new BwYxafRisk();
		bwYxafRisk.setName(name);
		bwYxafRisk.setIdCard(idCard);
		return mapper.selectOne(bwYxafRisk);
	}

	@Override
	public int update(BwYxafRisk bwYxafRisk) {
		return mapper.updateByPrimaryKey(bwYxafRisk);
	}

}
