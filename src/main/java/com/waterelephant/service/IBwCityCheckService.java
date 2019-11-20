package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCityCheck;

import tk.mybatis.mapper.entity.Example;

public interface IBwCityCheckService {

	List<BwCityCheck> findBwCityCheckByExample(Example example);
}
