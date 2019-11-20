package com.waterelephant.service;

import com.waterelephant.entity.BwCloudExternal;

/**
 * Module:
 * 
 * BwCloudExternalService.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2018年7月20日
 */
public interface BwCloudExternalService extends BaseCommonService<BwCloudExternal, Long> {

	void saveBwCloudExternal(BwCloudExternal bwCloudExternal);

	void updateBwCloudExternal(BwCloudExternal bwCloudExternal);

	BwCloudExternal findBwCloudExternalByOrderId(String orderId, int source);

	BwCloudExternal findBwCloudExternalByExternalNo(String externalNo, int source);

}
