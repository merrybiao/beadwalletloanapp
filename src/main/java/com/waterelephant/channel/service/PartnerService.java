package com.waterelephant.channel.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.channel.entity.BwNoticeRecord;
import com.waterelephant.channel.entity.Partner;
import com.waterelephant.channel.utils.Page;
import com.waterelephant.entity.CmsContent;

public interface PartnerService {

	Page<Partner> list(Partner entity, int[] pageParams);

	void savePartner(Partner partner);

	void updatePartner(Partner partner);

	Partner findPartnerInfo(int partnerId);

	void deletePartner(int partnerId);

	/** 根据ID查询栏目 分页查询 */
	List<Map<String, Object>> getPartnerList(String pageNo);

	Map<String, Object> getContentList(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getHelpContentList(Map<String, Object> map);

	/** 根据ID查询栏目用于首页显示 */
	List<CmsContent> getIndexContentList(String channelId);

	/**
	 * 首页查询未读公告数量
	 * 
	 * @param borrower_id
	 * @param borrower_id2
	 * @return
	 */
	int getUnreadCount(String channel_id, String borrower_id);

	/**
	 * 查询公告列表
	 * 
	 * @param map
	 * @return
	 */
	Map<String, Object> getNoticeList(Map<String, Object> map);

	/** 查询已读公告列表 */
	List<BwNoticeRecord> getReadList(String borrower_id);

	/** 删除用户所有浏览记录 */
	void deleteRecord(String borrower_id);

}
