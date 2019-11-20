package com.waterelephant.operatorData.rongshu.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class RongsShuOperatorDataProvider {
	
	/**
	 * 查询用户基础信息.
	 * @param order_id
	 * @return
	 */
	public String queryUserData(Long borrowerId){
		return new SQL(){
			{
			 SELECT(" DATE_FORMAT(IFNULL(t.update_time,t.create_time),'%Y-%m-%d %T') as updateTime," + 
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
				"	t.package_name as packageName"); 
			   FROM("bw_operate_basic t ");
              WHERE(" t.borrower_id= #{borrowerId}");
           ORDER_BY(" t.update_time DESC  limit 1");        
			}
		}.toString();
	}

	/**
	 * 查询账单信息.
	 * @return SQL
	 */
	public String queryBillData(Map<String,String> map){
		return new SQL(){
			{
				SELECT(" t.`bill_Date` AS month,"
			         + " t.`voice_Call_Fee` AS call_pay,"
			         + " '' AS package_fee,"
			         + " t.`sms_Fee` AS tel_fee,"
			         + " t.`data_Fee` AS net_fee,"
			         + " t.`vas_Fee` AS addtional_fee,"
			         + " t.`discount` AS preferential_fee,"
			         + " '' AS generation_fee,"
			         + " '' AS other_fee,"
			         + " '' AS score,"
			         + " '' AS otherspaid_fee,"
			         + " t.`actual_Fee` AS pay_fee");
				FROM(" `bw_rongshu_billsummarylist` t ");
			   WHERE(" t.`bill_Date` BETWEEN #{startTime} and #{endTime}");
			   WHERE(" t.`order_id` = #{orderId}");
			ORDER_BY(" t.`bill_Date` DESC");
			}
		}.toString();
	}

	/**
	 * 查询每月对应的通话记录数.
	 * @param map
	 * @return
	 */
	public String queryCallCount(Map<String,Object> map){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_rongshu_calldetaillist` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	/**
	 * 查询每月的通话记录详情.
	 * @param map
	 * @return
	 */
	public String queryCallData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT("'' AS business_name,"
					 + " date_format(t.`time`,'%Y-%m-%d %H:%i:%S') AS call_time,"
			         + " CASE t.`dial_type` WHEN '主叫' THEN 1 WHEN '被叫' THEN 2 ELSE 3 END  AS call_type,"
			         + " t.`fee`,"
			         + " '' AS special_offer,"
			         + " t.`location` AS trade_addr,"
			         + " t.`duration_sec` AS trade_time,"
			         + " '' AS trade_type,"
			         + " t.`peer_number` AS receive_phone");
				  FROM(" `bw_rongshu_calldetaillist` t ");
				 WHERE(" t.`order_id` = #{orderId}");
				 WHERE(" t.`time` BETWEEN #{startTime} and #{endTime}");
			  ORDER_BY(" t.`time` DESC");
			}
		}.toString();
	}
	
	/**
	 * 查询每月对应的短信记录数.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,Object> map){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_rongshu_smsdetaillist` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}

	/**
	 * 查询每月的短信记录详情.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT("'' AS business_name, "
					 + " t.`fee`, "
			         + " '' AS receiver_phone, "
			         + " date_format(t.`time`,'%Y-%m-%d %H:%i:%S') AS send_time, "
			         + " '' AS special_offer, "
			         + " '' AS trade_addr, "
			         + " '' AS trade_type, "
			         + " '' AS trade_way");
				  FROM(" `bw_rongshu_smsdetaillist` t ");
				 WHERE(" t.`order_id` = #{orderId}");
				 WHERE(" t.`time` BETWEEN #{startTime} and #{endTime}");
			  ORDER_BY(" t.`time` DESC");
			}
		}.toString();
	}
	
	/**
	 * 获取订单号对应付每月充值记录.
	 * @param map
	 * @return
	 */
	public String queruyRechargeData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT("t.`money` AS fee, "
					 + " date_format(t.`time`,'%Y-%m-%d %H:%i:%S') AS recharge_time, "
					 + " t.`type` AS recharge_way");
					  FROM(" `bw_rongshu_paymentinfolist` t ");
					 WHERE(" t.`order_id` = #{orderId}");
					 WHERE(" t.`time` BETWEEN #{startTime} and #{endTime}");
				  ORDER_BY(" t.`time` DESC");
			}
		}.toString();
	}
}
