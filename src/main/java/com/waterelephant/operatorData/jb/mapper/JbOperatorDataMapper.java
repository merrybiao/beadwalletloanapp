package com.waterelephant.operatorData.jb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorRechargeDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface JbOperatorDataMapper {

	/**
	 * 基础数据
	 * @param orderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("select"
		  + " t.`user_source` ,"
		  + " t.`id_card` ,"
		  + " t.`addr`,"
		  + " t.`real_name` ,"
		  + " t.`phone_remain`,"
		  + " t.`phone`,"
		  + " DATE_FORMAT(t.`reg_time` ,'%Y-%m-%d %H:%i:%S') as reg_time,"
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
	OperatorTelDataDto getJbCallCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);

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
	List<Map<String,String>> getJbCallRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

	@Select("SELECT  t.number as total_size,"
			+ " t.DATE as month "
		    + " FROM (select date_format(`time`,'%Y-%m') as DATE,"
		    + " COUNT(id) as number "
		    + " from `bw_jb_smses` "
		    + " where order_id=#{orderId} "
		    + " GROUP BY DATE) t "
		    + "  where t.DATE = #{endTime}")
	OperatorMsgDataDto getJbMsgCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);

	@Select("select"
			+ " t.`serivce_name` AS business_name,"
			+ " t.`fee` ,"
			+ " t.`peer_number` AS receiver_phone,"
			+ " DATE_FORMAT(t.`time`,'%Y-%m-%d %H:%i:%S') AS send_time,"
			+ " '' AS special_offer, "
			+ " t.`location` AS trade_addr,"
			+ " t.`msg_type` AS trade_type,"
			+ " CASE UPPER(IFNULL(t.`send_tpye`,'NULL')) WHEN 'SEND' THEN '1' WHEN 'RECEIVE' THEN '2' ELSE '"+""+"' END AS trade_way "
			+ " from `bw_jb_smses` t"
			+ " where t.`order_id` = #{orderId}"
			+ " and t.`time` BETWEEN  #{startTime} AND  #{endTime}"
			+ " ORDER BY t.`time` asc")
	List<Map<String,String>> getJbMsgRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,@Param(value="endTime") String endTime);

	@Select("SELECT  t.number as total_size,"
			+ " t.DATE as month "
			+ " FROM (select date_format(`time`,'%Y-%m') as DATE,"
			+ " COUNT(id) as number "
			+ " from `bw_jb_nets`"    
			+ " where order_id=#{orderId} "
			+ " GROUP BY DATE) t "
			+ " where t.DATE = #{endTime}")
	OperatorNetDataDto getJbNetCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);
	
	@Select("SELECT"
			+ " t.`fee` ,"
			+ " t.`net_type` ,"
			+ " ''as net_way,"
			+ " '' AS preferential_fee,"
			+ " DATE_FORMAT(t.`time`,'%Y-%m-%d %H:%i:%S') AS start_time,"
			+ " t.`duration` AS total_time ,"
			+ " t.`subflow` as total_traffi,"
			+ " t.`location` AS trade_addr"
			+ " FROM `bw_jb_nets` t "
			+ " where t.`order_id` = #{orderId} "
			+ " and t.`time` BETWEEN  #{startTime} AND #{endTime} "
			+ " ORDER BY t.`time` asc")
	List<Map<String,String>> getJdqNetRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,@Param(value="endTime") String endTime);
	
	@Select("select "
			+ " t.`bill_month` AS month,"
			+ " t.`total_fee` AS call_pay,"
			+ " t.`base_fee` AS  package_fee,"
			+ " t.`sms_fee` AS msg_fee,"
			+ " t.`voice_fee` AS tel_fee,"
			+ " t.`web_fee` AS net_fee,"
			+ " t.`extra_service_fee` AS addtional_fee,"
			+ " t.`extra_discount` AS preferential_fee,"
			+ " '' AS generation_fee, "
			+ " t.`extra_fee` AS other_fee,"
			+ " t.`point` AS score,"
			+ " '' AS otherspaid_fee,"
			+ " t.`actualFee` AS pay_fee  "
			+ " FROM bw_jb_bills t "
			+ " where t.`bill_month`"
			+ " BETWEEN #{startTime} and #{endTime} "
			+ " and t.`order_id`  = #{orderId} "
			+ " ORDER BY t.`bill_month` asc")
	List<OperatorBillDataDto> getJbBillRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);
	
	@Select("select "
			+ " t.`amount` AS fee,"
			+ " IFNULL(DATE_FORMAT(t.`recharge_time`,'%Y-%m-%d %H:%i:%S'),'"+""+"') as recharge_time,"
			+ " t.`type` AS recharge_way "
			+ " from `bw_jb_recharges` t"
			+ " where t.`order_id`  = #{orderId} "
			+ " and t.`recharge_time` BETWEEN #{startTime} and #{endTime}"
			+ " order by t.`recharge_time` ASC ")
	List<OperatorRechargeDataDto> rechargeRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

	
}
