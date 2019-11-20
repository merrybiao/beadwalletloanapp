package com.waterelephant.dssj.service;

import java.util.Map;

import com.waterelephant.dssj.entity.DssjAuthpulldata;

public interface DssjBaseService {
	
	/**
	 * 获取授权token
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> saveToken() throws Exception;
	
	/**
	 * 
	 * @param name
	 * @param idCard
	 * @param phone
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> saveAuthData(String name, String idCard, String phone,String notify_url,String redit_url,
			String sequenceno) throws Exception;
	
	/**
	 * 保存推送的数据信息
	 * @param dssjpulldata
	 * @return
	 */
	public boolean savePulldata(DssjAuthpulldata bwdssjauthdata) throws Exception ;
	
	/**
	 * 保存查询的信用分
	 * @return
	 */
	public String saveScoreData(String name,String idCard,String phone,String token,String sequenceno)  throws Exception;
}
