package com.waterelephant.faceID.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.faceID.entity.FaceidVerify;
import com.waterelephant.faceID.service.FaceidVerifyService;
import com.waterelephant.service.BaseService;

/**
 * faceID - 活体检测记录事物处理
 * @author dengyan
 *
 */
@Service
public class FaceidVerifyServiceImpl extends BaseService<FaceidVerify, Long> implements FaceidVerifyService {

	@Override
	public void saveFaceidVerify(FaceidVerify faceidVerfiy) {
		mapper.insert(faceidVerfiy);
	}

}
