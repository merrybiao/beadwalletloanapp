/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.PageCount;
import com.waterelephant.service.PageCountService;

/**
 * 
 * 
 * Module:
 * 
 * PageCountServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */ 
public class PageCountServiceImpl extends BaseCommonServiceImpl<PageCount, Long> implements PageCountService {

	/**
	 * 获取最后一次访问记录
	 * 
	 * @see com.waterelephant.service.PageCountService#getLatestPageCount(java.lang.String, java.lang.String)
	 */
	@Override
	public PageCount getLatestPageCount(String sessionId, String ip) {
		String sql = "select * from page_count where session_id = '" + sessionId + "' and ip = '" + ip
				+ "' order by access_time desc limit 0, 1 ";
		PageCount pageCount = sqlMapper.selectOne(sql, PageCount.class);
		return pageCount;
	}

	/**
	 * 统计PV
	 * 
	 * @see com.waterelephant.service.PageCountService#getPV(java.lang.String, java.lang.String)
	 */
	@Override
	public int getPV(String startTime, String endTime) {
		String sql = "select count(*) from page_count where access_time >= '" + startTime + "' and access_time < '"
				+ endTime + "'";
		int pv = sqlMapper.selectOne(sql, Integer.class);
		return pv;
	}

	/**
	 * 统计UV
	 * 
	 * @see com.waterelephant.service.PageCountService#getUV(java.lang.String, java.lang.String)
	 */
	@Override
	public int getUV(String startTime, String endTime) {
		String sql = "select count(ip) from( " + "select distinct ip from page_count where access_time >= '" + startTime
				+ "' and access_time < '" + startTime + "') temp ";
		int uv = sqlMapper.selectOne(sql, Integer.class);
		return uv;
	}

}
