package com.waterelephant.rongCarrier.jd.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.jd.entity.UserInfo;
import com.waterelephant.rongCarrier.jd.service.UserInfoService;
import com.waterelephant.service.BaseService;

@Service
public class UserInfoServiceImpl extends BaseService<UserInfo, Long> implements UserInfoService {

	@Override
	public boolean saveUserInfo(UserInfo userInfo) {
		return mapper.insert(userInfo) > 0;
	}

	@Override
	public boolean updateUserInfo(UserInfo userInfo) {
		return mapper.updateByPrimaryKey(userInfo) > 0;
	}
	
	@Override
	public UserInfo queryUserInfo(Long borrowerId) {
		String sql = "select * from bw_jd_user_info a where a.borrower_id = " + borrowerId;
		UserInfo userInfo = sqlMapper.selectOne(sql, UserInfo.class);
		return userInfo;
	}

}
