package com.waterelephant.service;

import com.waterelephant.entity.BwZmxyRecord;

/**
 * 芝麻信用评分记录接口
 * @author song
 *
 */
public interface BwZmxyRecordService {
		
		/**
		 * 添加记录
		 * @param bwZmxyRecord
		 * @return
		 */
		int saveBwZmxyRecord(BwZmxyRecord bwZmxyRecord);
}
