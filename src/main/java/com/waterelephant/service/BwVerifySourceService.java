package com.waterelephant.service;

import com.waterelephant.entity.BwVerifySource;

/**
 * 身份认证来源事物处理层 code0088
 * @author dengyan
 *
 */
public interface BwVerifySourceService {

	/**
	 * 根据orderId查询认证来源
	 * @param orderId
	 * @return
	 */
	@Deprecated
	BwVerifySource getVerifySource(Long photoId);
	
	/**
	 * 保存认证来源
	 * @param borrowerId
	 * @param orderId
	 * @param verfiySource
	 * @return
	 */
	@Deprecated
	boolean saveOrUpdateVerfiySource(Long photoId, Long borrowerId, Long orderId, Long verfiySource);
	
	boolean saveOrUpdateBwVerifySource(Long adjunctId,Long borrowerId,Long orderId,Long source);
	
	BwVerifySource queryBwVerifySourceByAdjunctId(Long adjunctId);
	
}
