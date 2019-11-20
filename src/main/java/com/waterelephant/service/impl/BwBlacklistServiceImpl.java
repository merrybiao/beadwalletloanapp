package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwBlacklist;

import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBlacklistService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwBlacklistServiceImpl extends BaseService<BwBlacklist, Long> implements BwBlacklistService {

	@Override
	public List<BwBlacklist> findBwBlacklistByExample(Example example) {
		return mapper.selectByExample(example);
	}
	
	@Override
	public BwBlacklist findAdoptByCard(String card) {
		if (CommUtils.isNull(card)) {
			return null;
		}
		BwBlacklist paramBlacklist = new BwBlacklist();
		paramBlacklist.setCard(card);
		paramBlacklist.setStatus(1);
		return mapper.selectOne(paramBlacklist);
	}
	
	@Override
	public int findAdoptTypeByCard(String card) {
		int type = 0;
		BwBlacklist bwBlacklist = findAdoptByCard(card);
		if (bwBlacklist != null) {
			Integer sort = bwBlacklist.getSort();
			if (sort != null) {
				type = sort;
			}
		}
		return type;
	}
	
	@Override
	public int findOutBlacklist(String phone, String idCard) {
		String sql = "select count(1) from out_black_list o where o.phone = '" + phone + "' or o.id_card = '" + idCard
				+ "'";
		Integer count = sqlMapper.selectOne(sql, Integer.class);
		return count != null ? count : 0;
	}

	@Override
	public int findBwBlacklistBy(String card, int sort, int status) {
		String sql = "SELECT COUNT(0) FROM bw_blacklist WHERE card = '"+card+"' AND sort ="+sort+" AND status ="+status;
		Integer count = sqlMapper.selectOne(sql, Integer.class);
		return count != null ? count : 0;
	}
}
