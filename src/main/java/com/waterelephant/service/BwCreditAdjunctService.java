package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.entity.BwCreditAdjunct;

public interface BwCreditAdjunctService {

	Map<Integer, BwCreditAdjunct> queryAdjunctByCreditId(Long creditId);

	int update(BwCreditAdjunct adjunct);

}
