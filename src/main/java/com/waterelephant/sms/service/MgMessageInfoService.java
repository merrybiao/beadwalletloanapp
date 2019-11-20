package com.waterelephant.sms.service;

import com.mongodb.DBCollection;

public interface MgMessageInfoService {

	/**
	 * 保存短信信息
	 * @param mgMessageInfo
	 * @return
	 */
	public void save(DBCollection dbCollection, String phone, String msg, String seqid, int chenal, int type);
	
	/**
	 * 更新短信信息
	 * @param mgMessageInfo
	 */
	public void update(DBCollection dbCollection,String seqid, int state, String stateValue, String phone);
}
