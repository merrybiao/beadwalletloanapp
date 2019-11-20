package com.waterelephant.service;

import com.waterelephant.entity.BwZlgxbAuthData;

public interface IBwZlgxbAuthDataService {
	
	/**
	 * 保存请求的基本数据
	 * @param bwzlgxbauthdata
	 * @return
	 */
	public Long saveAutuData(String name, String phone, String idCard,
			String authItem, String sequenceNo, String returnUrl,
			String notifyUrl, String timestamp) throws Exception;
	
	/**
	 * 增加授权后获得的数据
	 * @param bwzlgxbauthdata
	 * @return
	 */
	public boolean updateAutuData(String score, String status, String authStatus,String authTime,String sequenceno) throws Exception;
	
	/**
	 * 修改token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public boolean updateToken(String token,Long id) throws Exception;
	
	/**
	 * 查询授权数据
	 * @param sequenceno
	 * @return
	 */
	public BwZlgxbAuthData queryAutuData(String sequenceno) throws Exception;
	
	/**
	 * 增加手动查询的数据
	 * @param score
	 * @param status
	 * @param sequenceno
	 * @return
	 */
	public boolean updateAutuDatabyQuery(String score, String status,String sequenceno); 

}
