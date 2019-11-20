package com.waterelephant.service;

import com.waterelephant.entity.BwExternalIdentityCard;

public interface BwExternalIdentityCardService {

	BwExternalIdentityCard queryIdentityCardByIdcardNo(String idcardNumber);

	int saveBwIdentityCard(BwExternalIdentityCard identityCard);

	int updateBwIdentityCard(BwExternalIdentityCard identityCard);

}
