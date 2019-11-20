package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwBorrowerContract;

public interface BwBorrowerContractService {
	
	//合同列表
	List<BwBorrowerContract> queryBwBorrowerContractList(BwBorrowerContract bwBorrowerContract);
	
	
	//合同详情
	BwBorrowerContract queryBwBorrowerContractInfo(BwBorrowerContract bwBorrowerContract);
	
	
}
