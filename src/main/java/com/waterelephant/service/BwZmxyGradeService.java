package com.waterelephant.service;

import com.waterelephant.entity.BwZmxyGrade;

/**
 * 芝麻信用业务接口
 * 
 * @author song
 *
 */
public interface BwZmxyGradeService {

	/**
	 * 根据借款人id查询芝麻评分
	 * 
	 * @param borrowerId
	 *            借款人id
	 * @return
	 */
	BwZmxyGrade findZmxyGradeByBorrowerId(Long borrowerId);

	/**
	 * 添加芝麻评分
	 * 
	 * @param bwZmxyGrade
	 * @return
	 */
	int saveBwZmxyGrade(BwZmxyGrade bwZmxyGrade);

	/**
	 * 根据主键更新芝麻评分
	 * 
	 * @param bwZmxyGrade
	 * @return
	 */
	int updateBwZmxyGrade(BwZmxyGrade bwZmxyGrade);

}
