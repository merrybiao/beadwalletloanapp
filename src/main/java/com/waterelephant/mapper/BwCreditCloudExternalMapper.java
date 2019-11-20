/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.entity.BwCreditCloudExternal;

import tk.mybatis.mapper.common.Mapper;

/**
 * Module:
 * 
 * BwCloudExternalMapper.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2018年7月20日
 */
public interface BwCreditCloudExternalMapper extends Mapper<BwCreditCloudExternal> {

	@Select("select * from bw_credit_cloud_external a where  a.credit_id = #{creditId}  and a.type =0 and a.source=#{source} order by a.id desc limit 1")
	BwCreditCloudExternal findBwCreditCloudExternalByCreditId(@Param("creditId") Long creditId,
			@Param("source") Integer source);

	@Select("select * from bw_credi_cloud_external where external_no = #{externalNo}  and source = #{source} order by id desc limit 1")
	BwCreditCloudExternal findBwCreditCloudExternalByExternalNo(@Param("externalNo") String externalNo,
			@Param("source") Integer source);
}
