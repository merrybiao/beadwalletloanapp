package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwQhzxBlack;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwQhzxBlackService;
@Service
public class BwQhzxBlackServiceImpl extends BaseService<BwQhzxBlack,Long> implements BwQhzxBlackService{

	@Override
	public int save(BwQhzxBlack bwQhzxBlack) {
		return mapper.insert(bwQhzxBlack);
	}

	@Override
	public BwQhzxBlack findByNameAndIdCard(String name, String idCard) {
		BwQhzxBlack bwQhzxBlack = new BwQhzxBlack();
		bwQhzxBlack.setName(name);
		bwQhzxBlack.setIdCard(idCard);
		return mapper.selectOne(bwQhzxBlack);
	}

	@Override
	public int update(BwQhzxBlack bwQhzxBlack) {
		return mapper.updateByPrimaryKey(bwQhzxBlack);
	}

}
