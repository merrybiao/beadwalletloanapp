/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.channel.entity.BwNoticeRecord;
import com.waterelephant.channel.entity.CmsContent;
import com.waterelephant.channel.service.CmsContentChannelService;
import com.waterelephant.channel.utils.Page;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.CommUtils;

/**
 * 
 * 
 * Module:
 * 
 * CmsContentServiceImpl.java
 * 
 * @author 李萌
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class CmsContentServiceChannelImpl extends BaseService<CmsContent, Long> implements CmsContentChannelService {

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#findContentListByCriteria(com.waterelephant.common.entity.CmsContent,
	 *      java.lang.Long, int, int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Page findContentListByCriteria(CmsContent content, Long channelId, int pageNo, int pageSize,
			String beginTime, String endTime) {
		Page<CmsContent> page = new Page<CmsContent>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		StringBuffer sql = new StringBuffer(
				"select a.id,a.channel_id,a.title,a.title_img,a.url,a.author,a.origin,a.status,a.summary,a.detail,a.create_time,a.update_time,b.channel_name  from cms_content a left join cms_channel b on a.channel_id=b.id");
		if (!CommUtils.isNull(channelId)) {
			sql.append(" where channel_id=" + channelId);
		} else {
			sql.append(" where channel_id LIKE '%%'");
		}
		if (!CommUtils.isNull(content.getTitle())) {
			sql.append(" and title like '%" + content.getTitle() + "%'");
		}
		if (!CommUtils.isNull(content.getStatus())) {
			sql.append(" and `status`=" + content.getStatus());
		}
		if (!CommUtils.isNull(content.getBeginTime())) {
			sql.append(" and left(update_time,10) >='" + beginTime + "'");
		}
		if (!CommUtils.isNull(content.getEndTime())) {
			sql.append(" and left(update_time,10) <='" + endTime + "'");
		}
		sql.append(" order by update_time desc 	limit #{pageNum},#{pageSize}");
		page.setTotalCount(TotalCount(content, channelId, beginTime, endTime));
		PageRequestVo pVo = new PageRequestVo((pageNo - 1) * pageSize, pageSize);
		page.setPageNo((pageNo - 1) * pageSize);
		page.setPageSize(pageSize);
		List<CmsContent> rows = sqlMapper.selectList(sql.toString(), pVo, CmsContent.class);
		page.setResult(rows);
		return page;
	}

	public Integer TotalCount(CmsContent content, Long channelId, String beginTime, String endTime) {
		StringBuffer sql = new StringBuffer("select count(id) from cms_content");
		if (!CommUtils.isNull(channelId)) {
			sql.append(" where channel_id=" + channelId);
		} else {
			sql.append(" where channel_id LIKE '%%'");
		}
		if (!CommUtils.isNull(content.getTitle())) {
			sql.append(" and title like '%" + content.getTitle() + "%'");
		}
		if (!CommUtils.isNull(content.getStatus())) {
			sql.append(" and `status`=" + content.getStatus());
		}
		if (!CommUtils.isNull(content.getBeginTime())) {
			sql.append(" and left(update_time,10) >='" + beginTime + "'");
		}
		if (!CommUtils.isNull(content.getEndTime())) {
			sql.append(" and left(update_time,10) <='" + endTime + "'");
		}
		return sqlMapper.selectOne(sql.toString(), Integer.class);
	}

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#findContentByKey(java.lang.Long)
	 */
	@Override
	public CmsContent findContentByKey(Long id) {
		String sql = "select a.id,a.channel_id,a.title,a.title_img,a.url,a.author,a.origin,a.status,a.summary,a.detail,a.create_time,a.update_time,b.channel_name from cms_content a left join cms_channel b on a.channel_id=b.id  where a.id =#{id}";
		return sqlMapper.selectOne(sql, id, CmsContent.class);
	}

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#deleteContent(java.lang.Long)
	 */
	@Override
	public void deleteContent(Long id) {
		mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#bulkDeleteContent(java.lang.Long[])
	 */
	@Override
	public void bulkDeleteContent(Long[] ids) {
		if (!CommUtils.isNull(ids)) {
			for (int i = 0; i < ids.length; i++) {
				mapper.deleteByPrimaryKey(ids[i]);
			}
		}

	}

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#saveContent(com.waterelephant.common.entity.CmsContent)
	 */
	@Override
	public void saveContent(CmsContent content) {
		String sql = "insert into cms_content(channel_id,title,title_img,url,author,origin,status,summary,detail,create_time,update_time)"
				+ "values(#{channelId},#{title},#{titleImg},#{url},#{author},#{origin},#{status},#{summary},#{detail},#{createTime},#{updateTime})";
		sqlMapper.insert(sql, content);
	}

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#updateContent(com.waterelephant.common.entity.CmsContent)
	 */
	@Override
	public void updateContent(CmsContent content) {
		String sql = "update cms_content set channel_id=#{channelId},title=#{title},title_img=#{titleImg},url=#{url},author=#{author},origin=#{origin},status=#{status},"
				+ "summary=#{summary},detail=#{detail},create_time=#{createTime},update_time=#{updateTime} where id =#{id}";
		sqlMapper.update(sql, content);
	}

	/**
	 * 
	 * @see com.waterelephant.CmsContentChannelService.service.cms.CmsContentService#findContentInfo(java.lang.Long)
	 */

	@Override
	public Map<String, Object> findContentDetail(Long ld) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append(
					"SELECT date_format(a.create_time,'%Y-%m-%d') as create_time,date_format(a.update_time,'%Y-%m-%d') as update_time,a.*");
			sBuilder.append(" FROM cms_content a,cms_content b");
			sBuilder.append(" WHERE b.id = " + ld);
			sBuilder.append(" AND a.channel_id = b.channel_id");
			sBuilder.append(" AND a.status=3");
			sBuilder.append(" ORDER BY update_time DESC");

			list = sqlMapper.selectList(sBuilder.toString());
			String nsql = "SELECT channel_name from cms_channel WHERE id =(SELECT channel_id from cms_content WHERE id="
					+ ld + ")";
			// Mapper<T>
			String channelName = sqlMapper.selectOne(nsql, String.class);
			// 处理数据

			for (int i = 0; list != null && i < list.size(); i++) {
				Map<String, Object> contentMap = list.get(i);
				if (contentMap.get("id").equals(ld)) {
					// 当天条
					resultMap = list.get(i);

					// 上一条
					int preIndex = i - 1;
					if (preIndex >= 0) {
						Map<String, Object> preMap = list.get(preIndex);
						resultMap.put("preId", preMap.get("id"));
						resultMap.put("preTitle", preMap.get("title"));
					} else {
						resultMap.put("preId", null);
						resultMap.put("preTitle", null);
					}

					// 下一条
					int nextIndex = i + 1;
					if (nextIndex < list.size()) {
						Map<String, Object> nextMap = list.get(nextIndex);
						resultMap.put("nextId", nextMap.get("id"));
						resultMap.put("nextTitle", nextMap.get("title"));
					} else {
						resultMap.put("nextId", null);
						resultMap.put("nextTitle", null);
					}
					resultMap.put("channelName", channelName);
					break;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public void addNoticeRecord(BwNoticeRecord bwNoticeRecord) {
		SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into bw_notice_record (borrower_id,notice_id,create_time) values ("
				+ bwNoticeRecord.getBorrowerId() + "," + bwNoticeRecord.getNoticeId() + ",'"
				+ SimpleDateFormat.format(bwNoticeRecord.getCreateTime()) + "')";
		sqlMapper.insert(sql);
	}

	/**
	 * 查询是否添加了浏览记录
	 * 
	 */
	@Override
	public int isReadNotice(BwNoticeRecord bwNoticeRecord) {
		String sql = "select count(1) from bw_notice_record where borrower_id = " + bwNoticeRecord.getBorrowerId()
				+ " and notice_id = " + bwNoticeRecord.getNoticeId();
		return sqlMapper.selectOne(sql, Integer.class);
	}

}
