package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCityCheck;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwCityCheckService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwCityCheckService extends BaseService<BwCityCheck,Long> implements IBwCityCheckService{

	@Override
	public List<BwCityCheck> findBwCityCheckByExample(Example example) {
	
		return mapper.selectByExample(example);
	}

	
}
