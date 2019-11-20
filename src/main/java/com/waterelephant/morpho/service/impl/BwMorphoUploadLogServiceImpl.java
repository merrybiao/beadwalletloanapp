/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.morpho.service.impl;



import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoUploadLog;
import com.waterelephant.morpho.service.BwMorphoUploadLogService;
import com.waterelephant.service.BaseService;

/**
 * Module: 
 * BwMorphoUploadLogServiceImpl.java 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwMorphoUploadLogServiceImpl extends BaseService<BwMorphoUploadLog, Long>
		implements BwMorphoUploadLogService {

	/**
	 * 保存
	 */
	@Override
	public void save(BwMorphoUploadLog bwMorphoUploadLog) {
		mapper.insert(bwMorphoUploadLog);
	}


}
