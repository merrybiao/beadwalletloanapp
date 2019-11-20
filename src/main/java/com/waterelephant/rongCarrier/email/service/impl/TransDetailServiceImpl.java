package com.waterelephant.rongCarrier.email.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.email.entity.TransDetail;
import com.waterelephant.rongCarrier.email.service.TransDetailService;
import com.waterelephant.service.BaseService;

@Service
public class TransDetailServiceImpl extends BaseService<TransDetail, Long> implements TransDetailService {

	@Override
	public boolean saveTransDetail(TransDetail transDetail) {
		return mapper.insert(transDetail) > 0;
	}

	@Override
	public boolean updateTransDetail(TransDetail transDetail) {
		return mapper.updateByPrimaryKey(transDetail) > 0;
	}

	@Override
	public TransDetail queryTransDetail(Long borrowerId, long transDate) {
		String sql = "select * from bw_email_trans_detail a where a.borrower_id = " + borrowerId + " and a.trans_date = " + transDate;
		TransDetail transDetail = sqlMapper.selectOne(sql, TransDetail.class);
		return transDetail;
	}

}
