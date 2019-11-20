package com.waterelephant.operatorData.dkgj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dkgj.provider.DkgjOperatorDataProvider;
import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface DkgjOperatorDataMapper {
	
	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long order_id);
		
	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,String>> queryCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryBillData")
	public List<OperatorBillDataDto> queryBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,String>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryNetCount")
	public OperatorNetDataDto queryNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=DkgjOperatorDataProvider.class,method="queryNetData")
	public List<Map<String,String>> queryNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);


}
