package com.waterelephant.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.entity.BwOrderAuth;

import tk.mybatis.mapper.common.Mapper;

public interface BwOrderAuthMapper extends Mapper<BwOrderAuth> {
	@Select("SELECT "
			+ " id,"
			+ " order_id as orderId,"
			+ " auth_type,"
			+ " auth_channel,"
			+ " create_time as createTime,"
			+ " update_time as updateTime,"
			+ " photo_state as photoState"
			+ " FROM bw_order_auth "
			+ " WHERE order_id = #{orderId} "
			+ " AND auth_type =#{authType} "
			+ " ORDER BY create_time DESC")
	List<Map<String, Object>> selectBwOrderAuth(@Param("orderId")Long orderId, @Param("authType")Integer authType);

}
