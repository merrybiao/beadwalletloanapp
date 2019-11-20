package com.waterelephant.faceID.service;

import com.waterelephant.faceID.entity.IdentityCard;

/**
 * 保存身份信息接口类
 * @author dengyan
 *
 */
public interface IdentityCardService {

	 public boolean saveIdentityCard(IdentityCard identityCard);
	 
	 public boolean updateIdentityCard(IdentityCard identityCard);
	 
	 public IdentityCard queryIdentityCard(int borrowerId) throws Exception;

	public IdentityCard queryIdentityCard(Long borrowerId);
}
