package com.waterelephant.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBorrowerContract;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBorrowerContractService;

/**
 * 关联查询合同对应的还款日期
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月10日 下午3:16:38
 */
@Service
public class BwBorrowerContractServiceImpl extends BaseService<BwBorrowerContract, Long> implements BwBorrowerContractService {
	
	//合同列表
	public List<BwBorrowerContract> queryBwBorrowerContractList(BwBorrowerContract bwBorrowerContract){
		return mapper.select(bwBorrowerContract);
	}
	
	//合同详情
	public BwBorrowerContract queryBwBorrowerContractInfo(BwBorrowerContract bwBorrowerContract){
		return mapper.selectOne(bwBorrowerContract);
	}
		
}
