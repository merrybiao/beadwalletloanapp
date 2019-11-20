package com.waterelephant.operatorData.kabao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class KaBaoOperatorDataProvider {
	
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
	 * 统计每月流量的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryNetCount(Map<String,Object> map){
		return  "SELECT t.`bill_month` AS month,t.`total_size` FROM bw_mx_net t where t.`order_id` =#{orderId} and t.`bill_month`= #{endTime} limit 1";
	};
	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryNetData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("   t.`fee`,"
			 		+ " t.`net_type`,"
			 		+ " '' AS net_way,"
			 		+ " '' AS preferential_fee,"
			 		+ " DATE_FORMAT(t.`time` ,'%Y-%m-%d %H:%i:%S') AS start_time,"
			 		+ " t.`duration` AS total_time,"
			 		+ " t.`subflow` AS total_traffic,"
			 		+ " t.`location` AS trade_addr ");
                FROM(" `bw_mx_net` t");         
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`time` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.`time` asc ");    
			}
		}.toString();
	}
	
	
	
	/**
	 * 统计每月短信的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,Object> map){
		return  "SELECT t.`bill_month` AS month,t.`total_size` FROM bw_mx_sms t where t.`order_id` =#{orderId} and t.`bill_month`= #{endTime} limit 1";
	};
	
	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  '' AS business_name,"
			 		+ " t.`fee`,"
			 		+ " t.`peer_number` AS receiver_phone,"
			 		+ " DATE_FORMAT(t.`time`,'%Y-%m-%d %T')  AS send_time,"
			 		+ " '' AS special_offer,"
			 		+ " t.`location` AS trade_addr,"
			 		+ " CASE t.`msg_type` WHEN 'SMS' THEN 1 WHEN 'MMS' THEN 2 ELSE 3 END AS trade_type,"
			 		+ " CASE t.`send_type` WHEN 'SEND' THEN 1 WHEN 'RECEIVE' THEN 2 ELSE 3 END  AS trade_way ");
			 	FROM( " bw_mx_sms t");
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`time` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.`time` asc ");    
			}
		}.toString();
	}

	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryBillData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  t.`bill_month` AS month,"
			 		+ " t.`total_fee`   AS call_pay,"
			 		+ " t.`base_fee` AS package_fee,"
			 		+ " t.`sms_fee` AS msg_fee,"
			 		+ " t.`voice_fee` AS tel_fee,"
			 		+ " t.`web_fee` AS net_fee,"
			 		+ " t.`extra_service_fee` AS addtional_fee,"
			 		+ " t.`discount` AS preferential_fee,"
			 		+ " '' AS generation_fee,"
			 		+ " t.`extra_fee` AS other_fee,"
			 		+ " t.`point` as score,"
			 		+ " '' AS otherspaid_fee,"
			 		+ " t.`actual_fee` AS pay_fee");
			 	FROM( " bw_mx_bills t");
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`bill_month` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.`bill_month` asc ");    
			}
		}.toString();
	}
	
	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryRechargeData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  t.`amount` AS fee,"
			 		+ " DATE_FORMAT(t.`recharge_time`,'%Y-%m-%d %T') AS recharge_time,"
			 		+ " t.`type` AS recharge_way");         
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`recharge_time` BETWEEN #{startTime} AND #{endTime} ");
                FROM(" bw_mx_recharge t");
             ORDER_BY(" t.`recharge_time` asc ");    
			}
		}.toString();
	}


	
	
	/**
	 * 用户账单信息第一次修改.
	 * @param map
	 * @return
	 *//*
	public String queryBillData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  t.`bill_month` AS month,"
				   + " t.`bill_fee` AS call_pay,"
			       + " t.`item_value` AS package_fee,"
			       + " '' AS msg_fee,"
			       + " '' AS tel_fee,"
			       + " '' AS net_fee,"
			       + " '' AS addtional_fee,"
			       + " '' AS preferential_fee,"
			       + " '' AS generation_fee,"
			       + " '' AS other_fee,"
			       + " '' AS score,"
			       + " '' AS otherspaid_fee,"
			       + " '' AS pay_fee");
                FROM(" `bw_kabao_report_bill_info` t");         
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`bill_month` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.bill_month asc ");    
			}
		}.toString();
	}
	
	*//**
	 * 用户账单信息第二次修改.
	 * @param map
	 * @return
	 *//*
	public String queryNewBillData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  a.`bill_cycle` AS month, "
				  + "  ifnull(a.`bill_total`, a.`bill_fee`) AS call_pay, "
		          + "  '' AS package_fee, "
		          + "  '' AS msg_fee, "
		          + "  '' AS tel_fee, "
		          + "  '' AS net_fee, "
		          + "  '' AS addtional_fee, "
		          + "  '' AS preferential_fee, "
		          + "  '' AS generation_fee, "
		          + "  '' AS other_fee, "
		          + "  '' AS score, "
		          + "  '' AS otherspaid_fee, "
                  + "  '' AS pay_fee");
                FROM(" `bw_mf_bill_info` a");         
                WHERE(" a.order_id=#{orderId}");
                WHERE(" a.`bill_cycle` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" a.`bill_cycle` asc ");    
			}
		}.toString();
	}*/
	


	
	
	

	
}
