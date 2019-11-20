/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.entity.BwCloudReason;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * 
 * 
 * Module:
 * 
 * BwCloudReasonMapper.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2018年8月27日
 */
public interface BwCloudReasonMapper extends Mapper<BwCloudReason> {

	@Select("select a.* from bw_cloud_reason a where a.order_push_no =#{orderPushNo}  order by a.id desc limit 1")
	public BwCloudReason findBwCloudReason(@Param("orderPushNo") String orderPushNo);
}
