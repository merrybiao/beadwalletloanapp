package com.waterelephant.operatorData.mgj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.mgj.provider.MgjOperatorDataProvider;

public interface MgjOperatorDataMapper {
	
	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long order_id);
		
	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,String>> queryCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryCallBillData")
	public List<OperatorBillDataDto> queryBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,String>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryNetCount")
	public OperatorNetDataDto queryNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=MgjOperatorDataProvider.class,method="queryNetData")
	public List<Map<String,String>> queryNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

}
