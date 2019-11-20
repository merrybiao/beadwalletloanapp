package com.waterelephant.operatorData.mf.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class MfOperatorDataProvider {
	
	/**
	 * 用户基础信息.
	 * @param order_id
	 * @return
	 */
	public String queryUserData(Long orderId){
		return "SELECT '' AS user_source, "
			 + " a.`cert_num` AS id_card, "
			 + " a.`cert_addr` AS addr, "
			 + " a.`user_name` AS real_name, "
			 + " b.`account_balance` AS phone_remain, "
			 + " a.`user_number` AS phone, "
			 + " DATE_FORMAT(b.`net_time`,'%Y-%m-%d') AS reg_time, "
			 + " DATE_FORMAT(IFNULL(a.update_time, a.create_time), '%Y-%m-%d %T') as updateTime, "
			 + " b.`credit_point` AS score, "
			 + " '' AS contact_phone, "
			 + " b.`credit_level` AS star_level, "
			 + " case b.`real_info` WHEN '实名' THEN 1 WHEN '未实名' THEN 2 ELSE 0 END AS authentication, "
			 + " case b.`mobile_status` WHEN '正常' then 1 WHEN '欠费' THEN 2 WHEN '停机' THEN  2 WHEN '销户' THEN 5 WHEN '未激活' then 3 WHEN '未知' THEN 0 ELSE 0 END AS phone_status, "
			 + " b.`brand_name` AS package_name "
			 + " FROM `bw_mf_base_info` a "
			 + " LEFT JOIN bw_mf_account_info b on a.`order_id`= b.`order_id` where a.`order_id` = #{orderId} order by a.create_time desc limit 1";
	}

	/**
	 * 统计每月通话的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryCallCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`call_start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_mf_call_info_record` "
				+ " where order_id=#{orderId}   "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	};
	
	/**
	 * 查询每月通话的基本信息.
	 * @param map
	 * @return
	 */
	public String queryCallData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" '' AS business_name, "
					 + " DATE_FORMAT(t.`call_start_time`, '%Y-%m-%d %T') AS call_time, "
					 + " case t.`call_type_name` WHEN '主叫' THEN 1 WHEN '被叫' THEN 2 WHEN '未知' THEN 3 WHEN '呼转' THEN 4 ELSE 3 END  AS call_type, "
				     + " t.`call_cost` AS fee, "
				     + " '' AS special_offer, "
				     + " t.`call_address` AS trade_addr, "
				     + " t.`call_time` AS trade_time, "
				     + " case t.`call_land_type` WHEN '本地通话' THEN 1 WHEN '国内长途' THEN 2 WHEN '国际长途' THEN 3 ELSE 3 END  AS trade_type, "
				     + " t.`call_other_number` AS receive_phone");
				 FROM(" `bw_mf_call_info_record` t");
				WHERE(" t.`order_id` = #{orderId} ");
				WHERE(" t.call_start_time between #{startTime} AND #{endTime}");
				ORDER_BY(" t.call_start_time ASC");
			}
		}.toString();
	};
	
	/**
	 * 统计每月短信的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`msg_start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_mf_sms_info_record` "
				+ " where order_id=#{orderId}   "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	};
	
	/**
	 * 查询每月通话的基本信息.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" IFNULL(t.`msg_biz_name`,'') AS business_name, "
					 + " t.msg_cost AS fee, "
					 + " t.`msg_other_num` AS receiver_phone, "
					 + " DATE_FORMAT(t.`msg_start_time`,'%Y-%m-%d %T') AS send_time, "
					 + " '' AS special_offer, "
					 + " t.`msg_address`, "
					 + " CASE t.`msg_channel` WHEN '短信' THEN 1 WHEN '彩信' THEN 2 ELSE 3 END AS trade_type, "
					 + " CASE t.`msg_type` WHEN '发送' THEN 1 WHEN '接收' THEN 2 ELSE 3 END AS trade_way");
				 FROM(" `bw_mf_sms_info_record` t");
				WHERE(" t.`order_id` = #{orderId} ");
				WHERE(" t.msg_start_time between #{startTime} AND #{endTime}");
				ORDER_BY(" t.msg_start_time ASC");
			}
		}.toString();
	};

	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryRechargeData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT(" t.`pay_fee` AS fee,"
			 	  + " DATE_FORMAT(t.`pay_date`,'%Y-%m-%d %T') AS recharge_time,"
			 	  + " t.`pay_type` AS recharge_way");         
			   FROM(" bw_mf_payment_info t");
              WHERE(" t.order_id=#{orderId}");
              WHERE(" t.`pay_date` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.`pay_date` asc ");
			}
		}.toString();
	}
}
