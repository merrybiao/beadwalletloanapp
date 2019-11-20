package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwBlacklist;

import tk.mybatis.mapper.entity.Example;

public interface BwBlacklistService {

	List<BwBlacklist> findBwBlacklistByExample(Example example);

	/**
	 * 根据身份证查询已通过的名单
	 * 
	 * @param card
	 * @return
	 */
	BwBlacklist findAdoptByCard(String card);

	/**
	 * 根据身份证查询已通过的名单类型：0.不在名单内或未审核或被拒绝 1：黑名单，2：灰名单拒，3：白名单
	 * 
	 * @param card
	 * @return
	 */
	int findAdoptTypeByCard(String card);

	int findOutBlacklist(String phone, String idCard);

	int findBwBlacklistBy(String card, int sort, int status);
}