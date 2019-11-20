package com.waterelephant.service;

import com.waterelephant.entity.BwCreditVerifySource;

public interface BwCreditVerifySourceService {
	
	BwCreditVerifySource queryBwVerifySourceByAdjunctId(Long adjunctId);

	boolean saveOrUpdateBwVerifySource(Long adjunctId, Long borrowerId, Long creditId, Integer verifySource);

}
