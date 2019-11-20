package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwExternalIdentityCard;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwExternalIdentityCardService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwExternalIdentityCardServiceImpl extends BaseService<BwExternalIdentityCard, Long> implements BwExternalIdentityCardService {

	@Override
	public BwExternalIdentityCard queryIdentityCardByIdcardNo(String idcardNumber) {
		Example example = new Example(BwExternalIdentityCard.class);
		example.createCriteria().andEqualTo("idCardNumber", idcardNumber);
		example.setOrderByClause("update_time DESC");
		List<BwExternalIdentityCard> list =  selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int saveBwIdentityCard(BwExternalIdentityCard identityCard) {
		return insert(identityCard);
	}

	@Override
	public int updateBwIdentityCard(BwExternalIdentityCard identityCard) {
		return updateByPrimaryKeySelective(identityCard);
	}

}
