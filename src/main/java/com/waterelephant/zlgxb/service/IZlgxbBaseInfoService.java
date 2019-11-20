package com.waterelephant.zlgxb.service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwZlgxbAuthData;


public interface IZlgxbBaseInfoService {
	
	public JSONObject saveAuthData(String name, String phone, String idCard, String authItem, String sequenceNo, String returnUrl, String notifyUrl, String timestamp) throws Exception;
	
	public BwZlgxbAuthData queryAuthData(String sequenceno) throws Exception;
	
	public boolean updateAuthData(String score, String status, String authStatus,String authTime,String sequenceNo) throws Exception;
	
	public JSONObject updateAuthDatByQuery(String token,String sequenceNo) throws Exception;
	
}
