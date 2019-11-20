package com.waterelephant.channel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.channel.entity.BwNoticeRecord;
import com.waterelephant.channel.entity.Partner;
import com.waterelephant.channel.service.PartnerService;
import com.waterelephant.channel.utils.Page;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.entity.CmsContent;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

@Service
public class PartnerServiceImpl extends BaseService<Partner, Long> implements PartnerService {

	private Logger logger = Logger.getLogger(PartnerServiceImpl.class);

	@Override
	public Page<Partner> list(Partner entity, int[] pageParams) {
		Page<Partner> page = new Page<Partner>();
		page.setPageNo(pageParams[0]);
		page.setPageSize(pageParams[1]);
		String sql = "select partner_id,partner_name,img_url,status,sort,create_time,update_time FROM cms_partner b where 1=1 ";
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if (!CommUtils.isNull(entity.getPartnerName())) {
			sqlBuffer.append(" and b.partner_name LIKE '%" + entity.getPartnerName() + "%'");// 根据名字查询
		}
		// 截取字符串用于查询count
		String sqlCount = sqlBuffer.toString().substring(sqlBuffer.toString().indexOf("FROM"));
		Long count = sqlMapper.selectOne(("select count(b.partner_id) " + sqlCount), Long.class);
		PageRequestVo pVo = new PageRequestVo((pageParams[0] - 1) * pageParams[1], pageParams[1]);
		sqlBuffer.append(
				" order by  b.sort asc,b.update_time desc limit " + pVo.getPageNum() + "," + pVo.getPageSize() + "");
		logger.debug("分页查询活动SQL：" + sqlBuffer.toString());
		page.setTotalCount(count);
		page.setPageNo(pVo.getPageNum());
		page.setPageSize(pVo.getPageSize());
		List<Partner> result = sqlMapper.selectList(sqlBuffer.toString(), Partner.class);
		page.setResult(result);
		return page;
	}

	@Override
	public void savePartner(Partner partner) {
		this.mapper.insert(partner);
	}

	@Override
	public void updatePartner(Partner partner) {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append("update cms_partner set partner_name=#{partnerName},img_url=#{imgUrl},status=#{status},");
		sqlSB.append("sort=#{sort},update_time=#{updateTime} where partner_id = #{partnerId}");
		logger.info("updatePartner:sql=" + sqlSB);
		this.sqlMapper.update(sqlSB.toString(), partner);
	}

	@Override
	public Partner findPartnerInfo(int partnerId) {
		// Partner partner = new Partner();
		// String sql = "select * FROM cms_partner where partner_id =" + partnerId;
		// partner = sqlMapper.selectOne(sql, Partner.class);
		// return partner;
		return mapper.selectByPrimaryKey(partnerId);
	}

	@Override
	public void deletePartner(int partnerId) {
		String sql = "delete FROM cms_partner where partner_id =" + partnerId;
		sqlMapper.delete(sql);
	}

	@Override
	public List<Map<String, Object>> getPartnerList(String pageNo) {
		List<Map<String, Object>> partner = new ArrayList<Map<String, Object>>();
		String sql = "select partner_name,img_url,partner_url FROM cms_partner b where b.status = 1 order by  b.sort asc,b.update_time desc";
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if (StringUtils.isNoneEmpty(pageNo)) {
			PageRequestVo pVo = new PageRequestVo((Integer.parseInt(pageNo) - 1) * 10, 10);
			sqlBuffer.append(" limit " + pVo.getPageNum() + "," + pVo.getPageSize() + "");
			logger.debug("分页查询活动SQL：" + sqlBuffer.toString());

		}
		partner = sqlMapper.selectList(sqlBuffer.toString());
		return partner;
	}

	@Override
	public Map<String, Object> getContentList(Map<String, Object> map) {
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sql = "select id,title,title_img,url,summary,date_format(create_time,'%Y-%m-%d') as update_time   FROM cms_content where channel_id = "
				+ map.get("channelId") + " and status = 3 order by update_time desc";
		StringBuffer sqlBuffer = new StringBuffer(sql);
		// 截取字符串用于查询count
		String sqlCount = sqlBuffer.toString().substring(sqlBuffer.toString().indexOf("FROM"));
		Long count = sqlMapper.selectOne(("select count(id) " + sqlCount), Long.class);
		sqlBuffer.append(" limit " + ((int) map.get("pageNo") - 1) * (int) map.get("pageSize") + ","
				+ (int) map.get("pageSize") + "");
		contentList = sqlMapper.selectList(sqlBuffer.toString());
		String channelId = map.get("channelId").toString();
		String nsql = "select channel_name FROM cms_channel WHERE id=" + channelId;
		// Mapper<T>
		String channelName = sqlMapper.selectOne(nsql, String.class);
		resultMap.put("channelName", channelName);// 当前页码
		resultMap.put("pageNo", map.get("pageNo"));// 当前页码
		resultMap.put("pageSize", map.get("pageSize"));// 每页显示条数
		resultMap.put("totalCount", count);// 总条数
		resultMap.put("contentList", contentList);
		return resultMap;
	}

	@Override
	public List<CmsContent> getIndexContentList(String channelId) {
		List<CmsContent> contentList = new ArrayList<CmsContent>();
		String json = RedisUtils.get("cms_" + channelId);
		if (StringUtil.isEmpty(json)) {
			String sql = "select * FROM cms_content where channel_id = " + channelId
					+ " and status = 3 order by create_time desc";
			contentList = sqlMapper.selectList(sql, CmsContent.class);
			RedisUtils.set("cms_" + channelId, JSON.toJSONString(contentList));
		} else {
			contentList = JSON.parseArray(json, CmsContent.class);
		}
		return contentList;
	}

	@Override
	public List<Map<String, Object>> getHelpContentList(Map<String, Object> map) {
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();

		String sql = "select title,detail  FROM cms_content where channel_id = " + map.get("channelId")
				+ " and status = 3 order by create_time desc";

		contentList = sqlMapper.selectList(sql);

		return contentList;
	}

	/**
	 * 首页查询未读公告数量
	 * 
	 */
	@Override
	public int getUnreadCount(String channel_id, String borrower_id) {
		String sql = "SELECT (x.all_count-y.read_count) as un_read_count from"
				+ "(select COUNT(1) as all_count  FROM cms_content where channel_id = " + channel_id
				+ " and status = 3 order by create_time desc) x,"
				+ "(select COUNT(1) as read_count  FROM bw_notice_record where borrower_id = " + borrower_id + ") y";
		return sqlMapper.selectOne(sql, Integer.class);
	}

	@Override
	public Map<String, Object> getNoticeList(Map<String, Object> map) {
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String borrower_id = "0";
		if (!CommUtils.isNull(map.get("borrower_id"))) {
			borrower_id = map.get("borrower_id").toString();
		}
		String sql = "select c.id,c.title,c.title_img,c.url,c.summary,date_format(c.create_time,'%Y-%m-%d') as update_time,IF(n.id IS NOT NULL,1,0) as isRead "
				+ "FROM cms_content c " + "LEFT JOIN bw_notice_record n ON c.id = n.notice_id and n.borrower_id = "
				+ borrower_id + " where channel_id = " + map.get("channelId")
				+ " and status = 3 order by update_time desc";// isRead是否已读：1已读，0未读
		StringBuffer sqlBuffer = new StringBuffer(sql);
		// 截取字符串用于查询count
		String sqlCount = "select count(id) FROM cms_content where channel_id = " + map.get("channelId")
				+ " and status = 3";
		Long count = sqlMapper.selectOne(sqlCount, Long.class);
		sqlBuffer.append(" limit " + ((int) map.get("pageNo") - 1) * (int) map.get("pageSize") + ","
				+ (int) map.get("pageSize") + "");
		contentList = sqlMapper.selectList(sqlBuffer.toString());
		String channelId = map.get("channelId").toString();
		String nsql = "select channel_name FROM cms_channel WHERE id=" + channelId;
		// Mapper<T>
		String channelName = sqlMapper.selectOne(nsql, String.class);
		resultMap.put("channelName", channelName);// 栏目名称
		resultMap.put("pageNo", map.get("pageNo"));// 当前页码
		resultMap.put("pageSize", map.get("pageSize"));// 每页显示条数
		resultMap.put("totalCount", count);// 总条数
		resultMap.put("contentList", contentList);
		return resultMap;
	}

	@Override
	public List<BwNoticeRecord> getReadList(String borrower_id) {
		String sql = "select id,borrower_id,notice_id from bw_notice_record where borrower_id = " + borrower_id;
		List<BwNoticeRecord> bwNoticeRecord = new ArrayList<BwNoticeRecord>();
		bwNoticeRecord = sqlMapper.selectList(sql, BwNoticeRecord.class);
		return bwNoticeRecord;
	}

	@Override
	public void deleteRecord(String borrower_id) {
		String sql = "delete from bw_notice_record where borrower_id = " + borrower_id;
		sqlMapper.delete(sql);
	}

}
