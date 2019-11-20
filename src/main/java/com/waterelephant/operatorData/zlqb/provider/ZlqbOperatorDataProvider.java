package com.waterelephant.operatorData.zlqb.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class ZlqbOperatorDataProvider {
	
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
	 * 统计流量月份对应的条数.
	 * @param map
	 * @return
	 */
	public String queryNetCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month "
			    + " FROM (select date_format(`time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number "
				+ " from `bw_zl_new_net` "
				+ " where order_id=#{orderId} "
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
				SELECT(" t.`fee`,"
					 + " t.`net_type`,"
					 + " t.`service_name` AS net_way,"
					 + " '' AS preferential_fee,"
					 + " DATE_FORMAT(t.time,'%Y-%m-%d %H:%i:%S') AS start_time,"
					 + " t.`duration`  AS total_time,"
					 + " t.`subflow` AS total_traffic,"
					 + " t.`location` AS trade_addr");
				  FROM(" `bw_zl_new_net` t");
				 WHERE(" t.`order_id` = #{orderId} ");
				 WHERE(" t.time between #{startTime} AND #{endTime}");
			  ORDER_BY(" t.time ASC");
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
			    + " FROM (select date_format(`time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number "
				+ " from `bw_zl_new_sms` "
				+ " where order_id=#{orderId} "
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
				SELECT(" '' AS business_name,"
					 + " t.`fee`,"
					 + " t.`peer_number` AS receiver_phone,"
					 + " DATE_FORMAT(t.`time`,'%Y-%m-%d %H:%i:%S') AS send_time,"
					 + " '' AS special_offer,"
					 + " t.`location` AS trade_addr,"
					 + " CASE t.`msg_type` WHEN 'SMS' THEN 1 WHEN 'MMS' THEN 2 ELSE 3 END AS trade_type,"
					 + " CASE t.send_type WHEN 'SEND' THEN 1 WHEN 'RECEIVE' THEN 2 ELSE 3 END AS trade_way ");
				  FROM(" `bw_zl_new_sms` t");
				 WHERE(" t.`order_id` = #{orderId} ");
				 WHERE(" t.time between #{startTime} AND #{endTime}");
			  ORDER_BY(" t.time ASC");
			}
		}.toString();
	};

	/**
	 * 查询月份账单记录信息
	 * @param map
	 * @return
	 */
	public String queryBillData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" t.`bill_month` AS MONTH,"
					 + " t.`total_fee` AS call_pay,"
					 + " t.`base_fee` AS package_fee,"
					 + " t.`sms_fee` AS msg_fee,"
					 + " t.`voice_fee` AS tel_fee,"
					 + " t.`web_fee` AS net_fee,"
					 + " t.`extra_service_fee` AS addtional_fee,"
					 + " t.`discount` AS preferential_fee,"
					 + " '' AS generation_fee,"
					 + " t.`extra_fee` AS other_fee,"
					 + " t.`point` AS score,"
					 + " '' AS otherspaid_fee,"
					 + " t.`actual_fee` AS pay_fee");
				  FROM(" `bw_zl_new_bills` t");
			     WHERE("t.`order_id` = #{orderId}");
			     WHERE("t.`bill_month` between #{startTime} and #{endTime}");
			}
		}.toString();
	}
}
