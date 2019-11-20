/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.waterelephant.service;

import com.waterelephant.entity.BwAuthCodeRecord;

/**
 * Module:
 *
 * @author chengpan
 * @date 2017-05-22 06:15:09
 * @description: <描述>
 * @log: 2017-05-22 06:15:09 chengpan 新建
 */
public interface BwAuthCodeRecordService {

	void addDataToBwAuthCodeRecord(BwAuthCodeRecord bwAuthCodeRecord);

}
