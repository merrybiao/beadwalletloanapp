package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwFundRecord;

public interface BwFundRecordService {

	List<BwFundRecord> findListByAttr(BwFundRecord bwFundRecord);

	BwFundRecord findByAttr(BwFundRecord bwFundRecord);

}