package com.waterelephant.morpho.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoBlacklist;


/**
 * code:18002 闪蝶黑名单
 */
@Service
public interface BwMorphoBlacklistService {
	/*
	 * 保存
	 */
	void save(BwMorphoBlacklist bwMorphoBlacklist);
	
	//code:18002-2
	List<BwMorphoBlacklist> findRecordsByPid(String pid);
	// code:18002-2
	List<BwMorphoBlacklist> findRecordsPlusByPid(String pid);

	void deleteByPid(String pid);

}
