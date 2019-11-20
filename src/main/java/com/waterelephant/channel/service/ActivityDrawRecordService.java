package com.waterelephant.channel.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.channel.entity.ActivityDrawRecord;

public interface ActivityDrawRecordService {

	/**
	 * 新增抽奖活动记录
	 * 
	 * @param activityDrawRecord
	 * @return
	 */
	int saveActivityDrawRecord(ActivityDrawRecord activityDrawRecord);

	/**
	 * 更新抽奖活动记录表
	 * 
	 * @param activityDrawRecord
	 * @return
	 */
	int updateActivityDrawRecord(ActivityDrawRecord activityDrawRecord);

	/**
	 * 查询我的中奖记录
	 * 
	 * @param activityDrawRecord
	 * @return
	 */
	List<Map<String, Object>> getMyWinningRecord(Map<String, Object> params);

}
