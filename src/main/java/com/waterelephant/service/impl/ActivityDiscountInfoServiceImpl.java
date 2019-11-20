/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.ActivityDiscountInfo;
import com.waterelephant.service.ActivityDiscountInfoService;
import com.waterelephant.service.BaseService;

/**
 * 活动基本信息
 * 
 * Module:
 * 
 * ActivityDiscountInfoServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ActivityDiscountInfoServiceImpl extends BaseService<ActivityDiscountInfo, Long>
		implements ActivityDiscountInfoService {

	/**
	 * 
	 * @see com.waterelephant.target.service.ActivityDiscountInfoService#addList(java.util.List)
	 */
	@Override
	public void addList(List<ActivityDiscountInfo> list) {
		for (ActivityDiscountInfo entity : list) {
			add(entity);
		}

	}

	/**
	 * 
	 * @see com.waterelephant.target.service.ActivityDiscountInfoService#add(com.waterelephant.common.entity.ActivityDiscountInfo)
	 */
	@Override
	public void add(ActivityDiscountInfo entity) {
		this.mapper.insert(entity);
	}

	@Override
	public List<ActivityDiscountInfo> getActinityDiscountInfo(Integer activityId) {
		 String sql="SELECT a.discount_id,a.activity_id,a.bonus_amount,a.number,a.loan_amount,a.invited_number,a.create_time,a.instructions "
		 		+ " from activity_discount_info a WHERE a.activity_id = "+activityId+"  order by bonus_amount";
		 List<ActivityDiscountInfo> result = sqlMapper.selectList(sql, ActivityDiscountInfo.class);
		 return result;
	}
	
	@Override
	public ActivityDiscountInfo getCanInvitedMaxActinityDiscountInfo(List<ActivityDiscountInfo> discountInfoList, Integer invitedNumber) {
		if (invitedNumber != null && discountInfoList != null && !discountInfoList.isEmpty()) {
			List<ActivityDiscountInfo> newDiscountInfoList = new ArrayList<ActivityDiscountInfo>(discountInfoList);
			Collections.sort(newDiscountInfoList, new Comparator<ActivityDiscountInfo>() {
				@Override
				public int compare(ActivityDiscountInfo o1, ActivityDiscountInfo o2) {
					return o2.getBonusAmount().compareTo(o1.getBonusAmount());
				}
			});
			for (ActivityDiscountInfo newDiscountInfo : newDiscountInfoList) {
				Integer queryInvitedNumber = newDiscountInfo.getInvitedNumber();
				if (invitedNumber.compareTo(queryInvitedNumber) >= 0) {
					return newDiscountInfo;
				}
			}
		}
		return null;
	}
}
