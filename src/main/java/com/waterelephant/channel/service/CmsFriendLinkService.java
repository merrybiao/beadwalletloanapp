/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.channel.entity.CmsFriendLink;
import com.waterelephant.channel.utils.Page;

/**
 * 
 * 
 * Module:
 * 
 * LinkService.java
 * 
 * @author 周诚享
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface CmsFriendLinkService {

	Page<CmsFriendLink> list(CmsFriendLink entity, int[] pageParams);

	void delete(int linkId);

	CmsFriendLink selectById(Integer linkId);

	boolean updateCmsFriendLink(CmsFriendLink entity);

	void update(CmsFriendLink entity);

	void saveFriendLink(CmsFriendLink entity);

	List<Map<String, Object>> getFriendLinkList(String pageNo);

}
