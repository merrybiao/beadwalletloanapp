package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditVerifySource;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditVerifySourceService;

import tk.mybatis.mapper.entity.Example;

@Service
/**
 * @author dinglinhao
 *
 */
public class BwCreditVerifySourceServiceImpl extends BaseService<BwCreditVerifySource, Long>
		implements BwCreditVerifySourceService {
	
	@Override
	public BwCreditVerifySource queryBwVerifySourceByAdjunctId(Long adjunctId) {
		Example example = new Example(BwCreditVerifySource.class);
		example.createCriteria().andEqualTo("adjunctId", adjunctId);
		
		List<BwCreditVerifySource> list = selectByExample(example);
		return null == list || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public boolean saveOrUpdateBwVerifySource(Long adjunctId, Long borrowerId, Long creditId, Integer verifySource) {
		BwCreditVerifySource creditVerifySource = queryBwVerifySourceByAdjunctId(adjunctId);
		if(null == creditVerifySource) {
			creditVerifySource = new BwCreditVerifySource();
			creditVerifySource.setAdjunctId(adjunctId);
			creditVerifySource.setBorrowerId(borrowerId);
			creditVerifySource.setCreditId(creditId);
			creditVerifySource.setVerifySource(verifySource);
			return insert(creditVerifySource) >0;
		} else {
			creditVerifySource.setAdjunctId(adjunctId);
			creditVerifySource.setBorrowerId(borrowerId);
			creditVerifySource.setCreditId(creditId);
			creditVerifySource.setVerifySource(verifySource);
			return updateByPrimaryKeySelective(creditVerifySource)>0;
		}
	}

}
