package com.waterelephant.operatorData.jdq.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface JdqOperatorDataMapper {
	
	/**
	 * 基础数据
	 * @param orderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
		@Select(" SELECT "
				+ " '' AS user_source,"
				+ " a.idcard AS id_card,"
				+ " c.`register_addr` AS addr,"
				+ " a.`real_name`,"
				+ " '' AS phone_remain,"
				+ " a.cell_phone AS phone,"
				+ " DATE_FORMAT(a.`reg_time`, '%Y-%m-%d %H:%i:%S') AS reg_time,"
				+ " DATE_FORMAT(IFNULL(a.update_time,a.gmt_create),'%Y-%m-%d %T') as updateTime,"
				+ " '' AS score,"
				+ " '' AS contact_phone,"
				+ " '' AS star_level,"
				+ " '' AS authentication,"
				+ " c.state AS phone_status,"
				+ " '' AS package_name FROM bw_jdq_basic a "
				+ " LEFT JOIN bw_order b ON a.order_id =b.id "
                + " LEFT JOIN bw_borrower c "
                + " ON b.borrower_id = c.id "
                + " WHERE order_id = #{orderId}"
                + " ORDER BY a.update_time DESC LIMIT 1")
		OperatorUserDataDto queryUserData(@Param(value="orderId") Long orderId);
	
		@Select("SELECT "
				+ " t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_jdq_call`  "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t"
				+ " where t.DATE = #{endTime}")
		OperatorTelDataDto getJdqCallCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);
	
		@Select("SELECT "
				+ " t.`use_time` as trade_time,"
				+ " DATE_FORMAT(t.`start_time`, '%Y-%m-%d %H:%i:%S') AS call_time,"
				+ " t.`place` AS trade_addr,"
				+ " t.`other_cell_phone` AS receive_phone,"
				+ " t.`init_type` as call_type,"
				+ " t.`call_type` AS business_name,"
				+ " t.`subtotal` AS fee,"
				+ " '' AS special_offer"
				+ " FROM `bw_jdq_call` t"
				+ " where t.`order_id` = #{orderId} and t.`start_time` BETWEEN  #{startTime} and #{endTime}")
		List<Map<String,String>> getJdqCallRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);
		
		@Select("SELECT "
				+ " t.number as total_size,"
				+ " t.DATE as month"
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number"
				+ " from `bw_jdq_sms`  "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime}")
		OperatorMsgDataDto getJdqMsgCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);
	
		@Select("SELECT DATE_FORMAT(t.`start_time`, '%Y-%m-%d %H:%i:%S') as send_time, "
				+ " case t.`init_type` when '发送' then '1' WHEN '接收' THEN '2' when '未识别状态' THEN '3' when '收取' then 1 else t.`init_type` end as 'trade_way', "
				+ " t.`other_cell_phone` as receiver_phone, "
				+ " '' as business_name, "
				+ " t.`subtotal` as fee, "
				+ " t.`place` as trade_addr, "
				+ " '' as trade_type, "
				+ " '' as special_offer "
				+ " FROM `bw_jdq_sms` t"
				+ " where t.`start_time` BETWEEN #{startTime} and #{endTime} and t.`order_id` = #{orderId}")
		List<Map<String,String>> getJdqMsgRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,@Param(value="endTime") String endTime);
	
		@Select("SELECT "
				+ " t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number"
				+ " from `bw_jdq_net` "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t"
				+ " where t.DATE = #{endTime}")
		OperatorNetDataDto getJdqNetCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);
	
		@Select("select "
				+ " t.`subtotal` as fee,"
				+ " '' as `net_type` ,"
				+ " t.`net_type` as net_way,"
				+ " '' as preferential_fee,"
				+ " DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%S') as start_time,"
				+ " t.`use_time` AS total_time,"
				+ " t.`subflow` as total_traffic,"
				+ " '' as business_name,"
				+ " t.`place` as trade_addr"
				+ " from bw_jdq_net  t "
				+ " where t.`order_id` = #{orderId} "
				+ " and t.`start_time` between #{startTime} and #{endTime}")
		List<Map<String,String>> getJdqNetRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,@Param(value="endTime") String endTime);

		@Select("SELECT"
				+ " t.`pay_amt` AS pay_fee,"
				+ " t.`plan_amt` AS package_fee,"
				+ " '' AS msg_fee, "
				+ " '' AS tel_fee, "
				+ " '' AS net_fee,"
				+ " '' AS addtional_fee,"
				+ " '' AS preferential_fee,"
				+ " '' AS generation_fee,"
				+ " '' AS other_fee,"
				+ " '' AS otherspaid_fee, "
				+ " '' AS score "
				+ " FROM `bw_jdq_transaction` t "
				+ " where t.`bill_cycle` BETWEEN  #{startTime} and #{endTime}"
				+ " and t.`order_id` = #{orderId}")
		List<OperatorBillDataDto> getJdqBillRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

}
