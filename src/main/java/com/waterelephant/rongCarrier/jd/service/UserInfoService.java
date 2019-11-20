package com.waterelephant.rongCarrier.jd.service;

import com.waterelephant.rongCarrier.jd.entity.UserInfo;

public interface UserInfoService {

	public boolean saveUserInfo(UserInfo userInfo);
	
	public boolean updateUserInfo(UserInfo userInfo);
	
	public UserInfo queryUserInfo(Long borrowerId);
}
