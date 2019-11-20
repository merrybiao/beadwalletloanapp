package com.waterelephant.channel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.channel.entity.ActivityDrawRecord;
import com.waterelephant.channel.service.ActivityDrawRecordService;
import com.waterelephant.service.BaseService;

/**
 * 抽奖活动记录表
 * 
 * 
 * Module:
 * 
 * ActivityDrawRecordServiceImpl.java
 * 
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ActivityDrawRecordServiceImpl extends BaseService<ActivityDrawRecord, Long>
		implements ActivityDrawRecordService {

	/**
	 * 新增抽奖活动记录
	 * 
	 * @see com.waterelephant.channel.service.ActivityDrawRecordService#saveActivityDrawRecord(com.waterelephant.channel.entity.ActivityDrawRecord)
	 */
	@Override
	public int saveActivityDrawRecord(ActivityDrawRecord activityDrawRecord) {
		return mapper.insert(activityDrawRecord);
	}

	/**
	 * 更新抽奖活动记录表
	 * 
	 * @see com.waterelephant.channel.service.ActivityDrawRecordService#updateActivityDrawRecord(com.waterelephant.channel.entity.ActivityDrawRecord)
	 */
	@Override
	public int updateActivityDrawRecord(ActivityDrawRecord activityDrawRecord) {
		// return mapper.updateByPrimaryKey(activityDrawRecord);
		return mapper.updateByPrimaryKeySelective(activityDrawRecord);
	}

	/**
	 * 查询我的中奖记录
	 * 
	 * @see com.waterelephant.channel.service.ActivityDrawRecordService#getMyWinningRecord(com.waterelephant.channel.entity.ActivityDrawRecord)
	 */
	@Override
	public List<Map<String, Object>> getMyWinningRecord(Map<String, Object> params) {
		// return mapper.select(activityDrawRecord);
		String sql = "select r.id,r.borrower_id,r.activity_id,r.prize_id,r.grant_status,r.address,r.create_time,a.img,a.prize_name,a.type "
				+ "from activity_draw_record r left join activity_discount_info a on r.prize_id = a.discount_id "
				+ "where r.is_winning = 1 and r.borrower_id = " + params.get("borrower_id")
				+ " order by r.create_time desc limit " + params.get("row") + "," + params.get("show_count");
		return sqlMapper.selectList(sql);
	}

}
