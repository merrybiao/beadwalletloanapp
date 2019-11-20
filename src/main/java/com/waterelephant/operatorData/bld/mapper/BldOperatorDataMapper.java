package com.waterelephant.operatorData.bld.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.operatorData.bld.provider.BldOperatorDataProvider;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface BldOperatorDataMapper {
	
	
	@SelectProvider(type=BldOperatorDataProvider.class,method="queryUserData")
	public OperatorUserDataDto queryUserData(Long borrowerId);
		
	@SelectProvider(type=BldOperatorDataProvider.class,method="queryCallCount")
	public OperatorTelDataDto queryCallCount(@Param("borrowerId") Long borrowerId,@Param("endTime")String endTime);

	@SelectProvider(type=BldOperatorDataProvider.class,method="queryCallData")
	public List<Map<String,Object>> queryCallData(@Param("borrowerId") Long borrowerId,@Param("startTime")String startTime,@Param("endTime")String endTime);


}
