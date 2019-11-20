package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwDssjToken;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwDssjTokenService;
@Service
public class BwDssjTokenServiceImpl extends BaseService<BwDssjToken, Long> implements BwDssjTokenService {

	/**
	 * 存储token
	 * @param expire
	 * @param token
	 * @param insert_time
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long save(String expire, String token) throws Exception {
		BwDssjToken bwdssjtoken = new BwDssjToken();
		bwdssjtoken.setInsertTime(new Date());
		bwdssjtoken.setUpdateTime(new Date());
		bwdssjtoken.setToken(token);
		bwdssjtoken.setExpire(expire);
		return insert(bwdssjtoken) == 1 ? bwdssjtoken.getId() : 0;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public BwDssjToken queryBwDssjTokenbyToken(String token) throws Exception {
		String sql = "SELECT * FROM bw_dssj_updtoken WHERE TOKEN = '"+token+"'";
		return sqlMapper.selectOne(sql, BwDssjToken.class);
	}
}
