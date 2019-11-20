/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwIdentityCard2;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwIdentityCardService;

import tk.mybatis.mapper.entity.Example;

/**
 * 身份信息实现类
 * 
 * Module:
 * 
 * BwIdentityCardServiceImpl.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwIdentityCardServiceImpl extends BaseService<BwIdentityCard2, Long> implements IBwIdentityCardService {

	/**
	 * 
	 * @see com.waterelephant.service.IBwIdentityCardService#findBwIdentityCardByAttr(com.waterelephant.entity.BwIdentityCard2)
	 */
	@Override
	public BwIdentityCard2 findBwIdentityCardByAttr(BwIdentityCard2 bwIdentityCard) {

		return mapper.selectOne(bwIdentityCard);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwIdentityCardService#saveBwIdentityCard(com.waterelephant.entity.BwIdentityCard2)
	 */
	@Override
	public int saveBwIdentityCard(BwIdentityCard2 bwIdentityCard) {

		return mapper.insert(bwIdentityCard);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwIdentityCardService#updateBwIdentityCard(com.waterelephant.entity.BwIdentityCard2)
	 */
	@Override
	public int updateBwIdentityCard(BwIdentityCard2 bwIdentityCard) {

		return mapper.updateByPrimaryKey(bwIdentityCard);
	}
	
	@Override
	public BwIdentityCard2 queryIdentityCardByIdcardNo(String idcardNumber) {
		/*String sql = "SELECT a.* FROM bw_identity_card a"
				+ " WHERE a.id_card_number ='" + idcardNumber +"'"
				+ " ORDER BY a.update_time DESC"
				+ " LIMIT 1";
		return sqlMapper.selectOne(sql, BwIdentityCard2.class);*/
		Example example = new Example(BwIdentityCard2.class);
		example.createCriteria().andEqualTo("idCardNumber", idcardNumber);
		example.setOrderByClause("update_time DESC");
		List<BwIdentityCard2> list = selectByExample(example);
		
		return null == list || list.isEmpty() ? null : list.get(0);
	}

}
