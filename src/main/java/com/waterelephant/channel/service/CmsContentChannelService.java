/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.service;

import java.util.Map;

import com.waterelephant.channel.entity.BwNoticeRecord;
import com.waterelephant.channel.entity.CmsContent;
import com.waterelephant.channel.utils.Page;

/**
 * 
 * 
 * Module:
 * 
 * 
 * @author 李萌
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface CmsContentChannelService {

	@SuppressWarnings("rawtypes")
	public Page findContentListByCriteria(CmsContent content, Long channelId, int pageNo, int pageSize,
			String beginTime, String endTime);

	public CmsContent findContentByKey(Long id);

	public void deleteContent(Long id);

	public void bulkDeleteContent(Long[] ids);

	public void saveContent(CmsContent content);

	public void updateContent(CmsContent content);

	public Map<String, Object> findContentDetail(Long ld);

	/**
	 * 添加公告浏览记录
	 * 
	 * @param bwNoticeRecord
	 */
	public void addNoticeRecord(BwNoticeRecord bwNoticeRecord);

	/**
	 * 查询是否添加了浏览记录
	 * 
	 * @param bwNoticeRecord
	 * @return
	 */
	public int isReadNotice(BwNoticeRecord bwNoticeRecord);

}