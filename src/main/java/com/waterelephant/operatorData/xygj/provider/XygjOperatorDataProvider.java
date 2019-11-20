package com.waterelephant.operatorData.xygj.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class XygjOperatorDataProvider {
	
	/**
	 * 查询用户基础信息.
	 * @param order_id
	 * @return
	 */
	public String queryUserData(Long orderId){
		return new SQL(){
			{
			  SELECT(" t.`type` AS user_source, "
			       + " t.`idcard` AS id_card, "
			       + " IFNULL(t.`address`, '') AS addr, "
			       + " t.`truename` AS real_name, "
			       + " t.`balance` AS phone_remain, "
			       + " t.`mobile` AS phone, "
			       + " DATE_FORMAT(t.`openTime`,'%Y-%m-%d %T')  AS reg_time, "
			       + " DATE_FORMAT(t.`createTime`,'%Y-%m-%d %T')  AS update_time, "
			       + " '' AS score, "
			       + " '' AS contact_phone, "
			       + " '' AS star_level, "
			       + " t.`certify` AS authentication, "
			       + " '' AS phone_status, "
			       + " '' AS package_name"); 
			   FROM(" `bw_xygj_base` t");
			  WHERE(" t.order_id= #{orderId}");
		   ORDER_BY(" t.`createTime` DESC  limit 1");        
			}
		}.toString();
	}

	
	/**
	 * 统计短信月份对应的条数.
	 * @param map
	 * @return
	 */
	public String queryCallCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month "
			    + " FROM (select date_format(`calltime`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number "
				+ " from `bw_xygj_call` "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	/**
	 * 查询订单号对应每月的通话记录详情.
	 * @param map
	 * @return
	 */
	public String queryCallData(Map<String,Object> map){
		return new SQL(){
			{
			SELECT(" t.`thtypename` AS business_name, "
			     + " date_format(t.`calltime`, '%Y-%m-%d %H:%i:%S') AS call_time, "
				 + " case t.`calltype` WHEN '主叫' THEN 1 WHEN '被叫' THEN 2 ELSE 3 END AS call_type, "
		         + " '' AS fee, "
		         + " '' AS special_offer, "
		         + " t.`homearea` AS trade_addr, "
		         + " t.`calllong` AS trade_time, "
		         + " CASE t.`landtype` WHEN '国内异地主叫' THEN 1 WHEN '国内被叫' THEN 2 ELSE 3 END AS trade_type, "
		         + " t.callphone AS receive_phone");
			 FROM( " bw_xygj_call t");
			WHERE("t.order_id = #{orderId}");
			WHERE("t.`calltime` BETWEEN #{startTime} AND #{endTime}");
		 ORDER_BY("t.`calltime` DESC");
			}
		}.toString();
	}
	
	/**
	 * 统计每月短信的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`smstime`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_xygj_sms`   "
				+ " where order_id=#{orderId} "
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
				SELECT(" '' AS `business_name`, "
					 + " t.`smsfee` AS fee, "
			         + " t.`smsphone` AS receiver_phone, "
			         + " date_format(t.`smstime`, '%Y-%m-%d %H:%i:%S') AS send_time, "
			         + " '' AS special_offer, "
			         + " t.`homearea` AS trade_addr, "
			         + " CASE  t.`businesstype` WHEN '短信' THEN 1 WHEN '彩信' THEN 2 ELSE 3 END AS trade_type, "
			         + " CASE t.smstype WHEN '发送' THEN 1 WHEN '彩信' THEN  2 ELSE 3 END AS trade_way");
				  FROM(" `bw_xygj_sms` t");
				 WHERE(" t.`order_id` = #{orderId} ");
				 WHERE(" t.smstime between #{startTime} AND #{endTime}");
			  ORDER_BY(" t.smstime ASC");
			}
		}.toString();
	};
	
	/**
	 * 统计每月流量的记录数和时间.
	 * @param map
	 * @return
	 *//*
	public String queryNetCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_xygj_sms`   "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	};

	*//**
	 * 查询每月流量数据.
	 * @return
	 *//*
	public String queryNetData(){
		return new SQL(){
			{
				SELECT("'' AS fee, "
					 + " '' AS net_type, "
			         + " '' AS net_way, "
			         + " '' AS preferential_fee, "
			         + " '' AS start_time, "
			         + " '' AS total_time, "
			         + " t.`totalFlow` AS total_traffic, "
			         + " '' AS trade_addr");
				FROM(" `bw_xygj_gprs` t");
				 WHERE(" t.`order_id` = #{orderId} ");
				 WHERE(" t.start_time between #{startTime} AND #{endTime}");
			  ORDER_BY(" t.start_time ASC");
			}
		}.toString();
	}*/
	
	
}
