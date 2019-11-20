package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MyJSONUtil;
import com.waterelephant.utils.SqlMapper;
import com.waterelephant.utils.SystemConstant;

import cn.jpush.api.utils.StringUtils;
import net.sf.json.JSONArray;

/**
 * 活动基本信息
 * 
 * 
 * Module:
 * 
 * ActivityInfoServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ActivityInfoServiceImpl extends BaseService<ActivityInfo, Long> implements ActivityInfoService {

	@Autowired
	protected SqlMapper sqlMapper;

	/**
	 * 
	 * @see com.waterelephant.service.ActivityInfoService#queryActivityInfo(java.lang.Integer)
	 */
	@Override
	public ActivityInfo queryActivityInfo(ActivityInfo entity) {
		String sql = "select * from activity_info  ";
		StringBuffer sqlBuffer = new StringBuffer(sql);
		sqlBuffer.append(
				"where status = 1 and activity_type=" + entity.getActivityType() + " ORDER BY create_time DESC"); // 按时间降序查询
		List<ActivityInfo> result = sqlMapper.selectList(sqlBuffer.toString(), ActivityInfo.class);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public ActivityInfo queryLastActivityInfo(ActivityInfo entity) {
		String activityType = entity.getActivityType();
		Integer status = entity.getStatus();
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append("select * from activity_info where");
		sqlSB.append(" end_time<='");
		sqlSB.append(CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));
		sqlSB.append("'");
		if (StringUtils.isNotEmpty(activityType)) {
			sqlSB.append(" and activity_type=");
			sqlSB.append(activityType);
		}
		if (status != null) {
			sqlSB.append(" and status=");
			sqlSB.append(status);
		}
		sqlSB.append(" order by create_time desc limit 0,1");
		return sqlMapper.selectOne(sqlSB.toString(), ActivityInfo.class);
	}

	@Override
	public Date getExpiryTime(Date startDate, ActivityInfo queryActivityInfo) {
		Date expiryTime = startDate;
		Integer validYear = queryActivityInfo.getValidYear();
		Integer validMonth = queryActivityInfo.getValidMonth();
		Integer validDay = queryActivityInfo.getValidDay();
		if (validYear != null && validYear > 0) {
			expiryTime = DateUtils.addYears(expiryTime, validYear);
		}
		if (validMonth != null && validMonth > 0) {
			expiryTime = DateUtils.addMonths(expiryTime, validMonth);
		}
		if (validDay != null && validDay > 0) {
			expiryTime = DateUtils.addDays(expiryTime, validDay);
		}
		expiryTime = CommUtils.convertStringToDate(CommUtils.convertDateToString(expiryTime, SystemConstant.YMD_HMS),
				SystemConstant.YMD_HMS);
		return expiryTime;
	}

	@Override
	public Object findListActivityInfo() {
		List<ActivityInfo> list = new ArrayList<>();
		String sql = "select activity_id,activity_url,activity_title,start_time ,end_time,activity_img,status from activity_info where 1=1 order by start_time desc ";
		list = this.sqlMapper.selectList(sql, ActivityInfo.class);
		String[] excludes = { "participant", "content", "activityRule", "limitedTime", "validYear", "validMonth",
				"validDay", "limitedAmount", "activityType", "createTime" };
		if (list != null && list.size() > 0) {
			JSONArray listToJson = MyJSONUtil.listToJson(list, excludes, true);
			return listToJson;
		}
		return MyJSONUtil.objectToJson(new ActivityInfo(), excludes, true);
	}

	@Override
	public ActivityInfo findActivityInfoByEntry(ActivityInfo activityInfo) {
		return mapper.selectOne(activityInfo);
	}

}
