package com.waterelephant.operatorData.kabao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorRechargeDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.kabao.provider.KaBaoOperatorDataProvider;

public abstract interface KaBaoOperatorDataMapper {
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long order_id);

	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,Object>> queryCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

/*	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryBillData")
	public List<OperatorBillDataDto> queryBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryNewBillData")
	public List<OperatorBillDataDto> queryNewBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
*/	
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,Object>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryNetCount")
	public OperatorNetDataDto queryNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryNetData")
	public List<Map<String,Object>> queryNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryBillData")
	public List<OperatorBillDataDto> queryBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=KaBaoOperatorDataProvider.class,method="queryRechargeData")
	public List<OperatorRechargeDataDto> queryRechargeData(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

}
