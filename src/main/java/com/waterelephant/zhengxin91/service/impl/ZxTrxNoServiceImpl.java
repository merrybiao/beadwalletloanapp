package com.waterelephant.zhengxin91.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.ZxTrxNo;
import com.waterelephant.zhengxin91.service.ZxTrxNoService;

/**
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/4/1 17:30
 */
@Service
public class ZxTrxNoServiceImpl extends BaseService<ZxTrxNo, Long> implements ZxTrxNoService {

	@Override
	public int save(ZxTrxNo zxTrxNo) {
		return mapper.insert(zxTrxNo);
	}

	@Override
	public boolean updateZxTrxNo(ZxTrxNo zxTrxNo) {
		return mapper.updateByPrimaryKey(zxTrxNo) > 0;
	}

	@Override
	public boolean deleteByBorrowerId(String borrowerId) {
		String sql = "delete from bw_zx_trx_no where borrower_id = '" + borrowerId + "' ";
		return sqlMapper.delete(sql) > 0;
	}

	@Override
	public ZxTrxNo findByBorrowId(Long borrowId) {
		String sql = "select * from bw_zx_trx_no where borrower_id = '" + borrowId
				+ "' ORDER BY create_time desc LIMIT 1 ";
		return sqlMapper.selectOne(sql, ZxTrxNo.class);
	}

	@Override
	public ZxTrxNo findByTrxNo(String trxNo) {
		String sql = "select * from bw_zx_trx_no where trx_no = '" + trxNo + "' ORDER BY create_time desc LIMIT 1 ";
		return sqlMapper.selectOne(sql, ZxTrxNo.class);
	}
}
