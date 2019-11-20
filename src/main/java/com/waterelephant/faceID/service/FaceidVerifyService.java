package com.waterelephant.faceID.service;

import com.waterelephant.faceID.entity.FaceidVerify;

/**
 * faceID - 活体检测记录事物处理接口
 * @author dengyan
 *
 */
public interface FaceidVerifyService {

	/**
	 * faceID - 保存活体检测记录
	 * @param faceidVerfiy
	 */
	public void saveFaceidVerify(FaceidVerify faceidVerfiy);
}
