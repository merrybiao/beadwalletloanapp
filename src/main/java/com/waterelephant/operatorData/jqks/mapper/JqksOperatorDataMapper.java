package com.waterelephant.operatorData.jqks.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.jqks.provider.JqksOperatorDataProvider;

public interface JqksOperatorDataMapper {
	
	@SelectProvider(type=JqksOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long borrowerId);
	
	@SelectProvider(type=JqksOperatorDataProvider.class,method="queryBillData")
	public List<OperatorBillDataDto> queryBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=JqksOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("borrowerId") Long borrowerId,@Param("endTime")String endTime);
	
	@SelectProvider(type=JqksOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,Object>> queryCallData(@Param("borrowerId") Long borrowerId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=JqksOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=JqksOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,Object>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	

}
