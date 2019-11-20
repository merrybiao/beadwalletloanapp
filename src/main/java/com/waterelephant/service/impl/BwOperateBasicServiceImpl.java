package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOperateBasicService;

@Service
public class BwOperateBasicServiceImpl extends BaseService<BwOperateBasic, Long> implements BwOperateBasicService {

	@Override
	public int save(BwOperateBasic bwOperateBasic) {

		return mapper.insert(bwOperateBasic);
	}

	@Override
	public BwOperateBasic getOperateBasicById(Long borrowerId) {
		BwOperateBasic record = new BwOperateBasic();
		record.setBorrowerId(borrowerId);
		return mapper.selectOne(record);
	}

	@Override
	public BwOperateBasic getOperateBasicByName(Long borrowerId, String name) {
		name = name.substring(name.length() - 1);
		String sql = "SELECT * FROM bw_operate_basic WHERE borrower_id = " + borrowerId + " AND real_name LIKE '%"
				+ name + "'";
		return sqlMapper.selectOne(sql, BwOperateBasic.class);
	}

	@Override
	public int update(BwOperateBasic bwOperateBasic) {
		return mapper.updateByPrimaryKey(bwOperateBasic);
	}

	@Override
	public BwOperateBasic getBwOperateBasicByBorrowerId(Long borrowerId) {
		String sql = "SELECT a.* FROM bw_operate_basic a WHERE borrower_id = #{borrowerId}";
		return sqlMapper.selectOne(sql, borrowerId, BwOperateBasic.class);
	}

}
