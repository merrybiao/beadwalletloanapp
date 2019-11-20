package com.waterelephant.metlife.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwMetlifeInsuranceDetail;
import com.waterelephant.metlife.service.BwMetlifeInsuranceDetailService;
import com.waterelephant.service.BaseService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwMetlifeInsuranceDetailServiceImpl extends BaseService<BwMetlifeInsuranceDetail, Long>
		implements BwMetlifeInsuranceDetailService {

	@Override
	public List<BwMetlifeInsuranceDetail> queryInsuranceDetailListByBatchNo(String batchNo) {
		
		Example example = new Example(BwMetlifeInsuranceDetail.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchNo", batchNo);
		return selectByExample(example);
	}

	@Override
	public boolean updatePolicyNo(Long id, String policyNo) {
		BwMetlifeInsuranceDetail record = new BwMetlifeInsuranceDetail();
		record.setId(id);
		record.setPolicyNumber(policyNo);
		record.setUpdateTime(new Date());
		return updateByPrimaryKeySelective(record)>0;
	}
	
}
