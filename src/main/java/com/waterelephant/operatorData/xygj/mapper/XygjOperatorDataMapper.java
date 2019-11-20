package com.waterelephant.operatorData.xygj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.xygj.provider.XygjOperatorDataProvider;

public interface XygjOperatorDataMapper {
	
	@SelectProvider(type=XygjOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long orderId);
		
	@SelectProvider(type=XygjOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=XygjOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,Object>> queryCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=XygjOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=XygjOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,Object>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/*@SelectProvider(type=XygjOperatorDataProvider.class,method="queryNetCount")
	public OperatorNetDataDto queryNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=XygjOperatorDataProvider.class,method="queryNetData")
	public List<Map<String,Object>> queryNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);*/


}
