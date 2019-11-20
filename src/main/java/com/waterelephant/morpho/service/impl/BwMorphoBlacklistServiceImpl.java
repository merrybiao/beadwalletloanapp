package com.waterelephant.morpho.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoBlacklist;
import com.waterelephant.morpho.service.BwMorphoBlacklistService;
import com.waterelephant.service.BaseService;

/*
 * code:18002
 * 闪蝶黑名单数据
 */
@Service
public class BwMorphoBlacklistServiceImpl extends BaseService<BwMorphoBlacklist, Long>
		implements BwMorphoBlacklistService {

	@Override
	public void save(BwMorphoBlacklist bwMorphoBlacklist) {
		mapper.insert(bwMorphoBlacklist);
	}

	// code:18002-2
	@Override
	public List<BwMorphoBlacklist> findRecordsByPid(String pid) {
		String sql = "SELECT veidoo_type,black_level,last6_m_tenant_count,last6_m_query_count,last_confirm_at_days,last_confirm_status,last12_m_max_confirm_status,id_no"
				   + " FROM bw_morpho_blacklist WHERE id_no = " + pid
				   + " AND DATEDIFF(create_time,NOW()) <= 0 AND DATEDIFF(create_time,NOW()) > -30";
		return sqlMapper.selectList(sql, BwMorphoBlacklist.class);
	}

	@Override
	public List<BwMorphoBlacklist> findRecordsPlusByPid(String pid) {
		String sql = "SELECT * FROM bw_morpho_blacklist WHERE id_no = " + pid
					+ " AND DATEDIFF(create_time,NOW()) <= -30 LIMIT 10";
		return sqlMapper.selectList(sql, BwMorphoBlacklist.class);
	}

	@Override
	public void deleteByPid(String pid) {
		String sql = "DELETE FROM bw_morpho_blacklist WHERE id_no = " + pid;
		sqlMapper.delete(sql);
	}
}
