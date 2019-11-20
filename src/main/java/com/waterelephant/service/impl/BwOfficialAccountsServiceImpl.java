package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOfficialAccounts;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwOfficialAccountsService;

@Service
public class BwOfficialAccountsServiceImpl extends BaseService<BwOfficialAccounts, Long> implements IBwOfficialAccountsService {

	@Override
	public Boolean addOfficialAccounts(BwOfficialAccounts bwOfficialAccounts) {
		return mapper.insert(bwOfficialAccounts) > 0;
	}
	
	@Override
	public BwOfficialAccounts findBwOfficialAccountsById(Object obj) {
		return mapper.selectByPrimaryKey(obj);
	}
	
	@Override
	public BwOfficialAccounts findBwOfficialAccountsByAttr(BwOfficialAccounts bwOfficialAccounts) {
		return mapper.selectOne(bwOfficialAccounts);
	}
	
	@Override
	public int updateOfficialAccounts(BwOfficialAccounts bwOfficialAccounts) {
		return mapper.updateByPrimaryKey(bwOfficialAccounts);
	}
	
}
