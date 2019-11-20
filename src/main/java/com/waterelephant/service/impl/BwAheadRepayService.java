package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwAheadRepay;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwAheadRepayService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwAheadRepayService extends BaseService<BwAheadRepay, Long> implements IBwAheadRepayService {

	@Override
	public int addBwAheadRepay(BwAheadRepay bwAheadRepay) {
	
		return mapper.insert(bwAheadRepay);
	}

	@Override
	public List<BwAheadRepay> findBwAheadRepayByExample(Example example) {
	
		return mapper.selectByExample(example);
	}

	@Override
	public BwAheadRepay findBwAheadRepayByAttr(BwAheadRepay bwAheadRepay) {
	
		return mapper.selectOne(bwAheadRepay);
	}





}
