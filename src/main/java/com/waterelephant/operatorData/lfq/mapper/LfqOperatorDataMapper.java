package com.waterelephant.operatorData.lfq.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface LfqOperatorDataMapper {
	
	@Select("select"
			  + " t.`user_source` ,"
			  + " t.`id_card` ,"
			  + " t.`addr`,"
			  + " t.`real_name` ,"
			  + " t.`phone_remain`,"
			  + " t.`phone`,"
			  + " DATE_FORMAT(t.`reg_time` ,'%Y-%m-%d %T') as reg_time,"
			  + " DATE_FORMAT(IFNULL(t.update_time,t.create_time),'%Y-%m-%d %T') as updateTime,"
			  + " t.`score` ,"
			  + " t.`contact_phone` ,"
			  + " t.`star_level` ,"
			  + " t.`authentication` ,"
			  + " t.`phone_status` ,"
			  + " t.`package_name`  "
			  + " from `bw_third_operate_basic` t "
			  + " where `order_id` = #{orderId}"
			  + " order by `update_time` DESC LIMIT 1;")
		OperatorUserDataDto queryUserData(@Param(value="orderId") Long orderId);
	
	@Select("SELECT  t.number as total_size, "
			+ " t.DATE as month  "
		    + " FROM (select date_format(`call_time`,'%Y-%m') as DATE, "
		    + " COUNT(id) as number  "
			+ " from `bw_third_operate_voice`   "
			+ " where order_id=#{orderId}   "
			+ " GROUP BY DATE) t "
			+ " where t.DATE = #{endTime}")
	OperatorTelDataDto getLfqCallCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);

	@Select("select"
			+ " '' AS business_name,"
			+ " b.`call_type`,"
			+ " DATE_FORMAT(b.`call_time` ,'%Y-%m-%d %H:%i:%S')  as call_time,"
			+ " '' AS fee,"
			+ " '' AS special_offer,"
			+ " b.`trade_addr` ,"
			+ " b.`trade_time` ,"
			+ " b.`trade_type` ,"
			+ " b.`receive_phone` "
			+ " from `bw_third_operate_voice` b"
			+ " where b.`order_id` = #{orderId} "
			+ " and b.call_time between #{startTime} AND #{endTime}"
			+ " ORDER BY b.call_time ASC")
	List<Map<String,String>> getLfqCallRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);


}
