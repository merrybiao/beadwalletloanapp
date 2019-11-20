package com.waterelephant.operatorData.mf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorRechargeDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.mf.provider.MfOperatorDataProvider;

public interface MfOperatorDataMapper {
	
	@SelectProvider(type=MfOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long orderId);
			
	@SelectProvider(type=MfOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=MfOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,Object>> queryCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=MfOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=MfOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,Object>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=MfOperatorDataProvider.class,method="queryRechargeData")
	public List<OperatorRechargeDataDto> queryRechargeData(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

	
}
