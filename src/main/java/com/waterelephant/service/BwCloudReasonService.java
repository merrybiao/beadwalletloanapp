package com.waterelephant.service;

import com.waterelephant.entity.BwCloudReason;

/**
 * Module:
 * 
 * BwCloudReasonService.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2018年8月27日
 */
public interface BwCloudReasonService extends BaseCommonService<BwCloudReason, Long> {

	void saveBwCloudReason(BwCloudReason bwCloudReason);

	void updateBwCloudReason(BwCloudReason bwCloudReason);

	BwCloudReason findBwCloudReason(String orderPushNo);

}
