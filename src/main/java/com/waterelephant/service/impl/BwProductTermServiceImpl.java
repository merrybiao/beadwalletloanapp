package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwProductTerm;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwProductTermService;

/**
 * 
 * Module:续期 服务（产品关联表（维护每期服务费））
 * 
 * BwProductTermServiceImpl.java
 * 
 * @author wangkun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwProductTermServiceImpl extends BaseService<BwProductTerm, Long> implements BwProductTermService {

	@Override
	public List<BwProductTerm> findListProductTermByEntry(Long productId) {
		String sql = "select id,p_id,term_num,rate from bw_product_term where p_id=#{p_id}";
		return sqlMapper.selectList(sql, productId, BwProductTerm.class);
	}

}
