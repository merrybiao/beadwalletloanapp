package com.waterelephant.service;

import com.waterelephant.entity.BwCreditCloudReason;

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
public interface BwCreditCloudReasonService extends BaseCommonService<BwCreditCloudReason, Long> {

	void saveBwCreditCloudReason(BwCreditCloudReason bwCreditCloudReason);

	void updateBwCreditCloudReason(BwCreditCloudReason bwCreditCloudReason);

	BwCreditCloudReason findBwCreditCloudReason(String creditPushNo);

}
