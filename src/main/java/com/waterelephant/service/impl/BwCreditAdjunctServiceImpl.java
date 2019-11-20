package com.waterelephant.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditAdjunct;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditAdjunctService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

@Service
public class BwCreditAdjunctServiceImpl extends BaseService<BwCreditAdjunct, Long> implements BwCreditAdjunctService {

	@Override
	public Map<Integer, BwCreditAdjunct> queryAdjunctByCreditId(Long creditId) {
		Example example = Example.builder(BwCreditAdjunct.class)
				.andWhere(Sqls.custom().andEqualTo("relationId", creditId).andEqualTo("relationType", 1))
				.build();
		
		List<BwCreditAdjunct> list =  mapper.selectByExample(example);
		
		if(null == list || list.isEmpty()) return new HashMap<>();
		
		Map<Integer,BwCreditAdjunct> result = new HashMap<>();
	
		for(BwCreditAdjunct entity : list) {
			result.put(entity.getAdjunctType(), entity);
		}

		return result;
	}

	@Override
	public int update(BwCreditAdjunct adjunct) {
		return mapper.updateByPrimaryKey(adjunct);
	}

}
