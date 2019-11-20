package com.waterelephant.rongCarrier.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.rongCarrier.entity.BwDataExternal;

import tk.mybatis.mapper.common.Mapper;

public interface BwDataExternalMapper extends Mapper<BwDataExternal> {

	@Select("select * from bw_data_external e where e.out_unique_id = #{orderId} and e.type in (${type})")
	List<BwDataExternal> queryBwDataExternalByOrderId(@Param("orderId") String orderId, @Param("type") String type);
}
