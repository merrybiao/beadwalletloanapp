package com.waterelephant.operatorData.lfqkaBao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.lfqkaBao.provider.LfqKaBaoOperatorDataProvider;

public interface LfqKaBaoOperatorDataMapper {
	
	
	@SelectProvider(type=LfqKaBaoOperatorDataProvider.class,method="queryMsgCount")
	public OperatorMsgDataDto queryMsgCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=LfqKaBaoOperatorDataProvider.class,method="queryMsgData")
	public List<Map<String,Object>> queryMsgData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@SelectProvider(type=LfqKaBaoOperatorDataProvider.class,method="queryNetCount")
	public OperatorNetDataDto queryNetCount(@Param("orderId") Long orderId,@Param("endTime")String endTime);
	
	@SelectProvider(type=LfqKaBaoOperatorDataProvider.class,method="queryNetData")
	public List<Map<String,Object>> queryNetData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	@SelectProvider(type=LfqKaBaoOperatorDataProvider.class,method="queryBillData")
	public List<OperatorBillDataDto> queryBillData(@Param("orderId") Long orderId,@Param("startTime")String startTime,@Param("endTime")String endTime);
}
