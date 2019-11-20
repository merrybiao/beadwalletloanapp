package com.waterelephant.operatorData.Fqgj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorRechargeDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface FqgjOperatorDataMapper {
	
	/**
	 * 基础数据
	 * @param orderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("SELECT " + 
			"	DATE_FORMAT(IFNULL(t.update_time,t.create_time),'%Y-%m-%d %T') as updateTime," + 
			"	t.user_source as userSource," + 
			"	t.id_card as idCard," + 
			"	t.addr," + 
			"	t.real_name as realName," + 
			"	t.phone_remain as phoneRemain," + 
			"	t.phone," + 
			"	DATE_FORMAT(t.reg_time,'%Y-%m-%d %T') as regTime," + 
			"	t.score as score," + 
			"	t.contact_phone as contactPhone," + 
			"	t.star_level as starLevel," + 
			"	t.authentication," + 
			"	t.phone_status as phoneStatus," + 
			"	t.package_name as packageName" + 
			" FROM bw_operate_basic t " + 
			" WHERE t.borrower_id= #{borrowerId} "+
			" ORDER BY t.update_time DESC "+
			" LIMIT 1")
		OperatorUserDataDto queryUserData(@Param(value="borrowerId") Long borrowerId);
	
	/**
	 * 通话记录条数和月份
	 * @param orderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("SELECT "
			+ " t.number as total_size,"
			+ " t.DATE as month "
			+ " FROM (select date_format(`call_time`,'%Y-%m') as DATE,"
			+ " COUNT(id) as number from `bw_fqgj_call`"
			+ " where order_id=#{orderId} "
			+ " GROUP BY DATE) t "
			+ " where t.DATE = #{endTime}")
	OperatorTelDataDto getFqgjCallCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);

	/**
	 * 获取分期管家通话记录详情
	 * @param borrowerId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("SELECT "
			+ " t.trade_type, "
			+ "	t.trade_time, "
			+ "	DATE_FORMAT(t.call_time, '%Y-%m-%d %H:%i:%S') as call_time,"
			+ "	t.trade_addr, "
			+ "	t.receive_phone, "
			+ "	t.call_type, "
			+ "	'' as business_name, "
			+ "	'' as fee, "
			+ "	'' as special_offer "
			+ " FROM `bw_fqgj_call` t  "
			+ " WHERE t.`order_id` = #{orderId} "
			+ " AND t.`call_time`  BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getFqgjCallRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);
	
	/**
	 * 获取分期管家月账单记录
	 * @param orderId
	 * @param endTime
	 * @return
	 */
	@Select("select t.`month` , "
			+ " t.`call_pay`, "
			+ " '' as pay_fee, "
			+ " t.`package_fee`, "
			+ " t.`msg_fee` , "
			+ " t.`tel_fee` , "
			+ " t.`net_fee` , "
			+ " t.`addtional_fee` , "
			+ " t.`preferential_fee` , "
			+ " t.`generation_fee` , "
			+ " t.`other_fee` , "
			+ " '' as otherspaid_fee, "
			+ " t.`score` "
			+ " from bw_fqgj_phone_bill t where t.`order_id`  = #{orderId} and t.`month` =#{endTime} limit 1")
	OperatorBillDataDto getFqgjBillRecord(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);
	
	/**
	 * 获取每月短信条数
	 * @param orderId
	 * @param endTime
	 * @return
	 */
	@Select("SELECT "
			+ " t.number as total_size,"
			+ " t.DATE as month"
			+ " FROM (select date_format(`send_time`,'%Y-%m') as DATE,"
			+ " COUNT(id) as number"
			+ " from `bw_fqgj_msg_bill` "
			+ " where order_id=#{orderId}"
			+ " GROUP BY DATE) t"
			+ " where t.DATE = #{endTime}")
	OperatorMsgDataDto getFqgjMsgCount(@Param(value="orderId") Long orderId, @Param(value="endTime") String endTime);
	
	
	/**
	 * 获取每月短信记录信息
	 * @param orderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("select "
			+ " DATE_FORMAT(b.`send_time`, '%Y-%m-%d %H:%i:%S') as send_time,"
			+ " b.`trade_way`, "
			+ " b.`receiver_phone` , "
			+ " b.`business_name` , "
			+ " b.`fee` , "
			+ " b.`trade_addr` , "
			+ " b.`trade_type` , "
			+ " '' as special_offer "
			+ " from bw_fqgj_msg_bill b "
			+ " where b.`order_id` = #{orderId} "
			+ " and b.`send_time` "
			+ " BETWEEN #{startTime} and #{endTime}")
	List<Map<String,String>> getFqgjMsgRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,@Param(value="endTime") String endTime);

	/**
	 * 获取充值记录
	 * @param orderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("select "
			+ " c.`fee` ,"
			+ " DATE_FORMAT(c.`recharge_time` ,'%Y-%m-%d %H:%i:%S') as recharge_time,"
			+ " c.`recharge_way`  "
			+ " FROM `bw_fqgj_recharge` c "
			+ " where c.`order_id`  = #{orderId}"
			+ " and c.`recharge_time` BETWEEN  #{startTime} and #{endTime}")
	List<OperatorRechargeDataDto> getFqgChargeRecord(@Param(value="orderId") Long orderId, @Param(value="startTime") String startTime,@Param(value="endTime") String endTime);

}
