package com.waterelephant.service;

import com.waterelephant.entity.BwOrderZhanqiInfo;

public interface IBwOrderZhanqiInfoService {
	
	/**
	 * 保存工单展期记录
	 * @param bwOrderZhanqiInfo 工单展期记录
	 */
	void saveOrderZhanqiInfo(BwOrderZhanqiInfo bwOrderZhanqiInfo);

}
