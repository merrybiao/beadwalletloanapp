package com.waterelephant.baiqishi.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.baiqishi.entity.BqsDecision;
import com.waterelephant.baiqishi.service.BqsDecisionService;
import com.waterelephant.service.BaseService;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/27 10:58
 */
@Service
public class BqsDecisionServiceImpl extends BaseService<BqsDecision, Long> implements BqsDecisionService {

	@Override
	public BqsDecision getBqsDecision(String phone, String idCard) {
		String sql = "select a.* from `bw_bqs_decision` a LEFT JOIN `bw_borrower` b on a.`borrower_id` =b.id where b.phone='"
				+ phone + "' and b.id_card='" + idCard + "' order by a.id desc ";
		return sqlMapper.selectOne(sql, BqsDecision.class);
	}

	@Override
	public BqsDecision getBqsDecisionExternal(String phone, String idCard) {
		String sql = "select a.* from `bw_bqs_decision` a LEFT JOIN `bw_data_log` b on a.`id` =b.sys_data_id where b.phone='"
				+ phone + "' and b.id_card='" + idCard + "' and b.data_type='102' order by a.id desc ";
		return sqlMapper.selectOne(sql, BqsDecision.class);
	}

	@Override
	public boolean saveBqsDecision(BqsDecision bqsDecision) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(bqsDecision) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean deleteBqsDecision(BqsDecision bqsDecision) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(bqsDecision) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}
}
