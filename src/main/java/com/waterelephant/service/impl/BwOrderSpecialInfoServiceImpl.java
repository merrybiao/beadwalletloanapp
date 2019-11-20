package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrderSpecialInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderSpecialInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwOrderSpecialInfoServiceImpl extends BaseService<BwOrderSpecialInfo, Long> implements BwOrderSpecialInfoService {

	@Override
	public int queryOrderSpecialCountByPhone(String phone) {
		
		Example example = new Example(BwOrderSpecialInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("customerPhone", phone);
		return selectCountByExample(example);
	}

}
