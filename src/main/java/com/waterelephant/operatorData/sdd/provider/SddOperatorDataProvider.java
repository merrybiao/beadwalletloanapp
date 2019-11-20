package com.waterelephant.operatorData.sdd.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class SddOperatorDataProvider {
	
	/**
	 * 用户基础信息.
	 * @param order_id
	 * @return
	 */
	public String queryUserData(Long orderId){
		return new SQL(){
			{
			 SELECT(" t.`user_source` ,"
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
				  + " t.`package_name` ");
               FROM(" `bw_third_operate_basic` t");         
              WHERE(" t.order_id= #{orderId}");
           ORDER_BY(" t.update_time desc limit 1");         
			}
		}.toString();
	}

	/**
	 * 查询月份账单记录信息
	 * @param map
	 * @return
	 */
	public String queryBillData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" t.`bill_cycle` AS month,"
				     + " t.`total_amt` AS call_pay,"
			         + " t.`plan_amt` AS package_fee,"
			         + " '' AS msg_fee,"
			         + " '' AS tel_fee,"
			         + " '' AS net_fee,"
			         + " '' AS addtional_fee,"
			         + " '' AS preferential_fee,"
			         + " '' AS generation_fee,"
			         + " '' AS other_fee,"
			         + " '' AS score,"
			         + " '' AS otherspaid_fee,"
			         + " t.`pay_amt` AS pay_fee");
				  FROM(" bw_sdd_bill t");
			     WHERE("t.order_id = #{orderId}");
			     WHERE("t.bill_cycle between #{startTime} and #{endTime}");
			}
		}.toString();
	}

	/**
	 * 统计每月通话的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryCallCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`call_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_third_operate_voice`   "
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
				SELECT(" '' AS business_name,"
						+ " b.`call_type`,"
						+ " DATE_FORMAT(b.`call_time` ,'%Y-%m-%d %H:%i:%S')  as call_time,"
						+ " '' AS fee,"
						+ " '' AS special_offer,"
						+ " b.`trade_addr` ,"
						+ " b.`trade_time` ,"
						+ " b.`trade_type` ,"
						+ " b.`receive_phone` ");
				 FROM(" `bw_third_operate_voice` b");
				WHERE(" b.`order_id` = #{orderId} ");
				WHERE(" b.call_time between #{startTime} AND #{endTime}");
				ORDER_BY(" b.call_time ASC");
			}
		}.toString();
	};
	
	/**
	 * 统计短信月份对应的条数.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_sdd_sms`   "
				+ " where order_id=#{orderId}   "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	/**
	 * 查询每月通话的基本信息.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" '' AS business_name, "
					 + " '' AS fee, "
			         + " t.`other_call_phone` AS receiver_phone, "
			         + " date_format(t.`start_time`,'%Y-%m-%d %H:%i:%S') AS send_time, "
			         + " '' AS special_offer, "
			         + " t.`place` AS trade_addr, "
			         + " '' AS trade_type, "
			         + " case t.`init_type` WHEN '发送' THEN 1 WHEN '接收' THEN 2 ELSE 3 END  AS trade_way ");
				  FROM(" `bw_sdd_sms` t");
				 WHERE(" t.`order_id` = #{orderId} ");
				 WHERE(" t.start_time between #{startTime} AND #{endTime}");
			  ORDER_BY(" t.start_time ASC");
			}
		}.toString();
	};
	
	/**
	 * 统计流量月份对应的条数.
	 * @param map
	 * @return
	 */
	public String queryNetCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month  "
			    + " FROM (select date_format(`start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_sdd_net`   "
				+ " where order_id=#{orderId}   "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	/**
	 * 查询每月流量的基本信息.
	 * @param map
	 * @return
	 */
	public String queryNetData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" '' AS fee, "
				     + " t.`net_type` AS net_type, "
					 + " '' AS net_way, "
			         + " '' AS preferential_fee, "
			         + " date_format(t.`start_time`,'%Y-%m-%d %H:%i:%S') AS start_time, "
			         + " t.`use_time` AS total_time, "
			         + " t.`subflow` AS total_traffic, "
			         + " t.`place` AS trade_addr, "
			         + " '' AS business_name");
				  FROM(" `bw_sdd_net` t");
				 WHERE(" t.`order_id` = #{orderId} ");
				 WHERE(" t.start_time between #{startTime} AND #{endTime}");
			  ORDER_BY(" t.start_time ASC");
			}
		}.toString();
	};
}
