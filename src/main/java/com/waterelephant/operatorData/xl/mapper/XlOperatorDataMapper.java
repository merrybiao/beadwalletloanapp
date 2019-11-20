package com.waterelephant.operatorData.xl.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.xl.provider.XlOperatorDataProvider;

public interface XlOperatorDataMapper {
	
	@SelectProvider(type=XlOperatorDataProvider.class,method="selectUserData")
	public OperatorUserDataDto queryUserData(Long orderId);
	
	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlCallCount")
	public OperatorTelDataDto getXlCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlCallData")
	public List<Map<String, String>> getXlCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlNetCount")
	public OperatorNetDataDto getXlNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlNetData")
	public List<Map<String, String>> getXlNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlMsgCount")
	public OperatorMsgDataDto getXlMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);

	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlMsgData")
	public List<Map<String, String>> getXlMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=XlOperatorDataProvider.class,method="getXlBillData")
	public List<OperatorBillDataDto> getXlBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);


}
