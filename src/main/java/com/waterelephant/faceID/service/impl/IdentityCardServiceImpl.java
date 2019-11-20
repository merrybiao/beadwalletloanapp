package com.waterelephant.faceID.service.impl;

import com.waterelephant.faceID.entity.IdentityCard;
import com.waterelephant.faceID.service.IdentityCardService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * 身份信息事物处理层
 * @author dengyan
 *
 */
@Service
public class IdentityCardServiceImpl extends BaseService<IdentityCard, Long> implements IdentityCardService {

	@Override
	public boolean saveIdentityCard(IdentityCard identityCard) {
		return mapper.insert(identityCard) > 0;
	}

	@Override
	public boolean updateIdentityCard(IdentityCard identityCard) {
		return mapper.updateByPrimaryKey(identityCard) > 0;
	}

	@Override
	public IdentityCard queryIdentityCard(int borrowerId) throws Exception {
		String sql = "select * from bw_identity_card a where a.borrower_id = " + borrowerId;
		IdentityCard identityCard = sqlMapper.selectOne(sql, IdentityCard.class);
		return identityCard;
	}

	@Override
	public IdentityCard queryIdentityCard(Long borrowerId) {
		String sql = "select a.* from bw_identity_card a where a.borrower_id = " + borrowerId
				+ " order by a.update_time desc limit 1";
		return sqlMapper.selectOne(sql, IdentityCard.class);
	}

}
