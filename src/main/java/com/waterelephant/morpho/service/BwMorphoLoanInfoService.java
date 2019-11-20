package com.waterelephant.morpho.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoLoanInfo;


/*
 * code:18002 闪蝶批核&贷后共享数据
 */
@Service
public interface BwMorphoLoanInfoService {
	/*
	 * 保存
	 */
	void save(BwMorphoLoanInfo d360Info);
	
	// code:18002-2
	List<BwMorphoLoanInfo> findRecordsByPid(String pid);
	// code:18002-2
	List<BwMorphoLoanInfo> findRecordsPlusByPid(String pid);

	void deleteByPid(String Pid);

}
