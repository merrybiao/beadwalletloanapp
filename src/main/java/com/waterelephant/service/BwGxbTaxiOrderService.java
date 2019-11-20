package com.waterelephant.service;

import java.util.List;

import com.waterelephant.gxb.dto.TaxiOrderDto;

public interface BwGxbTaxiOrderService {
	
	int save(Long uid, List<TaxiOrderDto> orders) throws Exception;
	
	List<TaxiOrderDto> queryTaxiOrderBySequenceNo(String sequenceNo) throws Exception;
	
	

}
