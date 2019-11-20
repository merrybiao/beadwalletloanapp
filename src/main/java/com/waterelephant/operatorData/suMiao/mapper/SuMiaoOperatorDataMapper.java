package com.waterelephant.operatorData.suMiao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.suMiao.provider.SuMiaoOperatorDataProvider;

public abstract interface SuMiaoOperatorDataMapper {
	
	@SelectProvider(type=SuMiaoOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long order_id);
			
	@SelectProvider(type=SuMiaoOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=SuMiaoOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,Object>> queryCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

}
