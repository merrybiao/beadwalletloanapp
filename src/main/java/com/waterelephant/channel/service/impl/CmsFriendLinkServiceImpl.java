/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.channel.entity.CmsFriendLink;
import com.waterelephant.channel.service.CmsFriendLinkService;
import com.waterelephant.channel.utils.Page;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.CommUtils;

/**
 * 
 * Module:
 * 
 * LinkServiceImpl.java
 * 
 * @author 周诚享
 * @since JDK 1.8
 * @version 1.0
 */
@Service
public class CmsFriendLinkServiceImpl extends BaseService<CmsFriendLink, Integer> implements CmsFriendLinkService {

	private Logger logger = Logger.getLogger(CmsFriendLinkServiceImpl.class);

	@Override
	public Page<CmsFriendLink> list(CmsFriendLink entity, int[] pageParams) {
		Page<CmsFriendLink> page = new Page<CmsFriendLink>();
		page.setPageNo(pageParams[0]);
		page.setPageSize(pageParams[1]);
		String sql = "select link_id,link_title,link_url,link_status,sort,create_time,update_time FROM cms_friend_link b where 1=1 ";
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if (!CommUtils.isNull(entity.getLinkTitle())) {
			sqlBuffer.append(" and b.link_title LIKE '%" + entity.getLinkTitle() + "%'");// 根据名字查询
		}
		// 截取字符串用于查询count
		String sqlCount = sqlBuffer.toString().substring(sqlBuffer.toString().indexOf("FROM"));
		Long count = sqlMapper.selectOne(("select count(b.link_id) " + sqlCount), Long.class);
		PageRequestVo pVo = new PageRequestVo((pageParams[0] - 1) * pageParams[1], pageParams[1]);
		sqlBuffer.append(
				" order by  b.sort asc,b.update_time desc limit " + pVo.getPageNum() + "," + pVo.getPageSize() + "");
		logger.debug("分页查询活动SQL：" + sqlBuffer.toString());
		page.setTotalCount(count);
		page.setPageNo(pVo.getPageNum());
		page.setPageSize(pVo.getPageSize());
		List<CmsFriendLink> result = sqlMapper.selectList(sqlBuffer.toString(), CmsFriendLink.class);
		page.setResult(result);
		return page;
	}

	@Override
	public void delete(int linkId) {
		String sql = "delete FROM cms_friend_link where link_id =" + linkId;
		sqlMapper.delete(sql);
	}

	@Override
	public CmsFriendLink selectById(Integer linkId) {

		return mapper.selectByPrimaryKey(linkId);
	}

	@Override
	public boolean updateCmsFriendLink(CmsFriendLink entity) {

		Integer result = mapper.updateByPrimaryKeySelective(entity);
		return result > 0 ? true : false;
	}

	@Override
	public void update(CmsFriendLink entity) {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(
				"update cms_friend_link set link_title=#{linkTitle},link_url=#{linkUrl},link_status=#{linkStatus},");
		sqlSB.append("sort=#{sort},update_time=#{updateTime} where link_id = #{linkId}");
		logger.info("updateFriendLink:sql=" + sqlSB);
		this.sqlMapper.update(sqlSB.toString(), entity);
	}

	@Override
	public void saveFriendLink(CmsFriendLink entity) {

		this.mapper.insert(entity);
	}

	@Override
	public List<Map<String, Object>> getFriendLinkList(String pageNo) {
		List<Map<String, Object>> friendLink = new ArrayList<Map<String, Object>>();
		String sql = "select link_title,link_url FROM cms_friend_link b where b.link_status = 1 order by  b.sort asc,b.update_time desc";
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if (StringUtils.isNoneEmpty(pageNo)) {
			PageRequestVo pVo = new PageRequestVo((Integer.parseInt(pageNo) - 1) * 10, 10);
			sqlBuffer.append(" limit " + pVo.getPageNum() + "," + pVo.getPageSize() + "");
			logger.debug("分页查询活动SQL：" + sqlBuffer.toString());

		}
		friendLink = sqlMapper.selectList(sqlBuffer.toString());
		return friendLink;
	}
}
