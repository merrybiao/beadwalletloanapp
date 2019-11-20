package com.waterelephant.service;

import com.waterelephant.entity.BwCreditCloudExternal;

/**
 * 
 * Module:
 * 
 * BwCreditCloudExternalService.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2019年4月19日
 */
public interface BwCreditCloudExternalService extends BaseCommonService<BwCreditCloudExternal, Long> {

	void saveBwCreditCloudExternal(BwCreditCloudExternal bwCreditCloudExternal);

	void updateBwCreditCloudExternal(BwCreditCloudExternal bwCreditCloudExternal);

	BwCreditCloudExternal findBwCreditCloudExternalByCreditId(Long creditId, int source);

	BwCreditCloudExternal findBwCreditCloudExternalByExternalNo(String externalNo, int source);

}
