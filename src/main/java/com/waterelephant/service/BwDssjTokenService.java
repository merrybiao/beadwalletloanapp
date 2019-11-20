package com.waterelephant.service;

import com.waterelephant.entity.BwDssjToken;

public interface BwDssjTokenService {
	
	/**
	 * 存储token
	 * @param expire
	 * @param token
	 * @param insert_time
	 * @return
	 * @throws Exception
	 */
	public Long save(String expire,String token) throws Exception;
	
	/**
	 * 查询token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public BwDssjToken queryBwDssjTokenbyToken(String token) throws Exception;

}
