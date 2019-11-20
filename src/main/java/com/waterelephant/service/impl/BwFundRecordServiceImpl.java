package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFundRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwFundRecordService;

@Service
public class BwFundRecordServiceImpl extends BaseService<BwFundRecord, Long> implements BwFundRecordService {

	/**
	 * 
	 * @see com.waterelephant.service.BwFundRecordService#findListByAttr(com.waterelephant.entity.BwFundRecord)
	 */
	@Override
	public List<BwFundRecord> findListByAttr(BwFundRecord bwFundRecord) {
		return mapper.select(bwFundRecord);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwFundRecordService#findByAttr(com.waterelephant.entity.BwFundRecord)
	 */
	@Override
	public BwFundRecord findByAttr(BwFundRecord bwFundRecord) {
		return mapper.selectOne(bwFundRecord);
	}

}