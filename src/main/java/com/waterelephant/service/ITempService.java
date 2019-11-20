package com.waterelephant.service;

import com.waterelephant.utils.AppResponseResult;

import java.util.List;
import java.util.Map;

/**
 * 借款工单
 * 
 * @author lujilong
 *
 */
public interface ITempService {

	/**
	 * 查询当天的总单量
	 *
	 * @return
	 */
	public Long findTodayOrderCount();

	/**
	 * 查询当天的总放款额
	 *
	 * @return
	 */
	public Double findTodayOrderAmount();

	/**
	 * 查询初审单量
	 *
	 * @return
	 */
	public Long findCheckOrderCount();

	/**
	 * 查询未签约单量
	 *
	 * @return
	 */
	public Long findSignOrderCount();

	/**
	 * 查询还款单量
	 *
	 * @return
	 */
	public Long findRepayOrderCount();

	/**
	 * 查询未推送成功及未满标返回的单量
	 *
	 * @return
	 */
	public Long findNotPushOrderCount();

	/**
	 * 查询最近一周的总单量
	 *
	 * @return
	 */
	public List<Map<String, Object>> findWeekOrderCount();

}