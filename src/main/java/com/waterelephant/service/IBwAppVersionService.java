package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.entity.BwAppVersion;

public interface IBwAppVersionService {

	BwAppVersion findBwAppVersionById(Long id);

	/**
	 * 
	 * @param versionId
	 * @param storeNameChannel
	 * @return
	 */
	Map<String, Object> findBwAppDetail(String versionId, String storeNameChannel);
}
