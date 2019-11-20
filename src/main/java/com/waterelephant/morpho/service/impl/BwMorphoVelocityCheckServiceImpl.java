package com.waterelephant.morpho.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoVelocityCheck;
import com.waterelephant.morpho.service.BwMorphoVelocityCheckService;
import com.waterelephant.service.BaseService;


/*
 * code:18002
 * 流量查询
 */
@Service
public class BwMorphoVelocityCheckServiceImpl extends BaseService<BwMorphoVelocityCheck, Long>
		implements BwMorphoVelocityCheckService {

	@Override
	public void save(BwMorphoVelocityCheck bwMorphoVelocityCheck) {
		mapper.insert(bwMorphoVelocityCheck);
	}

	// code:18002-2
	@Override
	public List<BwMorphoVelocityCheck> findRecordsByPid(String pid) {
		String sql = "SELECT veidoo_type,sub_window_size,cross_tenant_apps_number,mobile_count,pid_count,"
				   + "id_no FROM bw_morpho_velocity_check WHERE id_no = " + pid
				   + " AND DATEDIFF(create_time,NOW()) <= 0 AND DATEDIFF(create_time,NOW()) > -30";
		return sqlMapper.selectList(sql, BwMorphoVelocityCheck.class);
	}

	@Override
	public List<BwMorphoVelocityCheck> findRecordsPlusByPid(String pid) {
		String sql = "SELECT * FROM bw_morpho_velocity_check WHERE id_no = " + pid
					+ " AND DATEDIFF(create_time,NOW()) <= -30 LIMIT 10";
		return sqlMapper.selectList(sql, BwMorphoVelocityCheck.class);
	}

	@Override
	public void deleteByPid(String pid) {
		String sql = "DELETE FROM bw_morpho_velocity_check WHERE id_no = " + pid;
		sqlMapper.delete(sql);
	}

}
