package com.waterelephant.service;

import com.waterelephant.entity.BwGxbTaxiBaseInfo;
import com.waterelephant.gxb.dto.TaxiDataDto;

public interface BwGxbTaxiBaseInfoService {
	
	BwGxbTaxiBaseInfo save(String sequenceNo, TaxiDataDto dto) throws Exception;

	TaxiDataDto queryTaxiDataBySequenceNo(String sequenceNo) throws Exception;

}
