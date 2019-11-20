package com.waterelephant.operatorData.kuainiu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.kuainiu.provider.KuaiNiuOperatorDataProvider;

public interface KuaiNiuOperatorDataMapper {
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectUserData")
	public OperatorUserDataDto selectUserData(Long order_id);
		
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectBillData")
	public List<OperatorBillDataDto> selectBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectCallCount")
	public OperatorTelDataDto selectCallCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectCallData")
	public List<Map<String,Object>> selectCallData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectMsgCount")
	public OperatorMsgDataDto selectMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectMsgData")
	public List<Map<String,Object>> selectMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectNetCount")
	public OperatorNetDataDto selectNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=KuaiNiuOperatorDataProvider.class,method="selectNetData")
	public List<Map<String,Object>> selectNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

}
