package com.waterelephant.rongCarrier.jd.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.jd.entity.Bankcards;
import com.waterelephant.rongCarrier.jd.entity.UserInfo;
import com.waterelephant.rongCarrier.jd.service.BankcardsService;
import com.waterelephant.service.BaseService;

@Service
public class BankcardsServiceImpl extends BaseService<Bankcards, Long>
		implements BankcardsService {

	@Override
	public boolean saveBankcards(Bankcards bankcards) {
		return mapper.insert(bankcards) > 0;
	}

	@Override
	public boolean updateBankcards(Bankcards bankcards) {
		return mapper.updateByPrimaryKey(bankcards) > 0;
	}

	@Override
	public Bankcards queryBankcards(Long borrowerId, String cardId) {
		String sql = "select * from bw_jd_bankcards a where a.borrower_id = " + borrowerId + " and a.card_id = '" + cardId + "'";
		Bankcards bankcards = sqlMapper.selectOne(sql, Bankcards.class);
		return bankcards;
	}

}
