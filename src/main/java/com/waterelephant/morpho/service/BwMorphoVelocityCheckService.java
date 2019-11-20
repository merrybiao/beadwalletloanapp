package com.waterelephant.morpho.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoVelocityCheck;


/*
 * code:18002 闪蝶流量查询
 */
@Service
public interface BwMorphoVelocityCheckService {
	/**
	 * 保存
	 */
	void save(BwMorphoVelocityCheck bwMorphoVelocityCheck);
	
	// code:18002-2
	List<BwMorphoVelocityCheck> findRecordsByPid(String pid);
	// code:18002-2
	List<BwMorphoVelocityCheck> findRecordsPlusByPid(String pid);

	void deleteByPid(String pid);
}
