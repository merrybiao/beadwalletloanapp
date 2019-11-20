package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditImformation;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditInformationService;
@Service
public class BwCreditInformationServiceImpl extends BaseService<BwCreditImformation, Long> implements BwCreditInformationService{

	@Override
	public int save(BwCreditImformation bwBlackList) {	
		return mapper.insert(bwBlackList);
	}
	
	@Override
	public int update(BwCreditImformation bwCreditImformation) {
		return mapper.updateByPrimaryKey(bwCreditImformation);
	}
	
	@Override
	public BwCreditImformation findByNameAndIdCard(String name, String idCard) {
		String sql = "SELECT * FROM bw_credit_information WHERE `name` = '"+name+"' and id_card = '"+idCard+"' limit 1";
		return sqlMapper.selectOne(sql, BwCreditImformation.class);
	}

	@Override
	public BwCreditImformation findByIdCard(String idCard) {
		String sql = "SELECT * FROM bw_credit_information WHERE id_card = '"+idCard+"' limit 1";
		return sqlMapper.selectOne(sql, BwCreditImformation.class);
	}


}
