package com.waterelephant.operatorData.lfqkaBao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class LfqKaBaoOperatorDataProvider {
	
	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryBillData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT(" t.`bill_month` AS month, "
				  + " t.`total_fee` AS call_pay, "
				  + " t.`base_fee` AS package_fee, "
				  + " t.`sms_fee` AS msg_fee, "
				  + " t.`voice_fee` AS tel_fee, "
				  + " t.`web_fee` AS net_fee, "
				  + " t.`extra_service_fee` AS addtional_fee, "
				  + " t.`discount` AS preferential_fee, "
				  + " '' AS generation_fee, "
				  + " t.`extra_fee` AS other_fee, "
				  + " t.`point` AS score, "
				  + " '' AS otherspaid_fee, "
				  + " t.`actual_fee`AS pay_fee");
			 	FROM( " bw_kabao_mx_bill t");
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`bill_month` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.`bill_month` asc ");    
			}
		}.toString();
	}

	

	/**
	 * 统计每月流量的记录数和时间.
	 * @param map
	 * @return
	 */
	public String queryNetCount(Map<String,Object> map){
		 return " SELECT bill_month AS MONTH,total_size FROM bw_kabao_mx_net WHERE order_id =#{orderId} AND bill_month =#{endTime} LIMIT 1;";
	};
	
	/**
	 * 用户流量信息.
	 * @param map
	 * @return
	 */
	public String queryNetData(Map<String,Object> map){
		return new SQL(){
			{
			  SELECT(" t.`fee`, "
					+ " t.`net_type`, "
				    + " '' AS net_way, "
				    + " '' AS preferential_fee, "
				    + " DATE_FORMAT(t.`time`,'%Y-%m-%d %T') AS start_time, "
				    + " t.`duration` AS total_time, "
				    + " t.`subflow` AS total_traffic, "
				    + " t.`location` AS trade_addr");
				 FROM(" `bw_kabao_mx_net` t");         
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
		return  "SELECT t.`bill_month` AS month,t.`total_size` FROM  bw_kabao_mx_sms t where t.`order_id` =#{orderId} and t.`bill_month`=#{endTime} limit 1";
	};
	
	/**
	 * 用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  '' AS business_name, "
				 + " t.`fee`, "
				 + " t.`peer_number` AS receiver_phone, "
				 + " DATE_FORMAT(t.`time`,'%Y-%m-%d %T') AS send_time, "
				 + " '' AS special_offer, "
				 + " t.`location` AS trade_addr, "
				 + " CASE t.`msg_type` WHEN 'SMS' THEN 1 WHEN 'MMS' THEN 2 ELSE 3 END AS trade_type, "
				 + " CASE t.`send_type` WHEN 'SEND' THEN 1 WHEN 'RECEIVE' THEN 2 ELSE 3 END AS trade_way");
			 	FROM( " bw_kabao_mx_sms t");
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`time` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.`time` asc ");    
			}
		}.toString();
	}

}
