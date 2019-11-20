package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwDssjPushdata;
import com.waterelephant.entity.BwDssjToken;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwDssjPushdataService;
@Service
public class BwDssjPushdataServiceImpl extends BaseService<BwDssjPushdata, Long> implements BwDssjPushdataService {

	/**
	 * 查询BwDssjPushdata
	 */
	@Override
	public BwDssjPushdata queryBwDssjPushdatabyTransId(String transId) {

		String sql = "SELECT *  FROM bw_dssj_pushdata WHERE transId = '"+transId+"'";
		
		return sqlMapper.selectOne(sql, BwDssjPushdata.class);
	}

	/**
	 * 保存 BwDssjPushdata
	 */
	@Override
	public long saveBwDssjPushdata(BwDssjPushdata bwdssjpushdata) {
		return mapper.insert(bwdssjpushdata);
	}
}
