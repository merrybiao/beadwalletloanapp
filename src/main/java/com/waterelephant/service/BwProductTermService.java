package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwProductTerm;

/**
 * 
 * Module:续期 服务（产品关联表（维护每期服务费））
 * 
 * BwProductTermService.java
 * 
 * @author wangkun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BwProductTermService {
	/**
	 * 通过产品id查询出所有的续期费用配置信息
	 * 
	 * @param productId
	 * @return
	 */
	List<BwProductTerm> findListProductTermByEntry(Long productId);
}
