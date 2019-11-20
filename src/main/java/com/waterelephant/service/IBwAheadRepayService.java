package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwAheadRepay;

import tk.mybatis.mapper.entity.Example;

public interface IBwAheadRepayService {

	int addBwAheadRepay(BwAheadRepay bwAheadRepay);
	
	List<BwAheadRepay> findBwAheadRepayByExample(Example example);
	
	BwAheadRepay findBwAheadRepayByAttr(BwAheadRepay bwAheadRepay);
	
	
}
