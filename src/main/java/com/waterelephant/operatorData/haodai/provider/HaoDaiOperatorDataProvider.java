package com.waterelephant.operatorData.haodai.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class HaoDaiOperatorDataProvider {
	
	/**
	 * 查询用户基础信息.
	 * @param order_id
	 * @return
	 */
	public String queryUserData(Long orderId){
		return new SQL(){
			{
			 SELECT("t.`carrier` AS user_source,"
				   + " t.`idcard` AS id_card,"
			       + " CONCAT_WS('', t.province, t.city) AS addr,"
			       + " t.`NAME` AS real_name,"
			       + " t.`available_balance` AS phone_remain,"
			       + " t.mobile AS phone,"
			       + " date_format(t.`open_time`,'%Y-%m-%d %H:%i:%S') AS reg_time,"
			       + " date_format(ifnull(t.`last_modify_time`,t.create_time),'%Y-%m-%d %H:%i:%S') AS update_time,"
			       + " '' AS score,"
			       + " '' AS contact_phone,"
			       + " t.level AS star_level,"
			       + " t.`CODE` AS authentication,"
			       + " case t.`state` when -1 then '未知' when 0 then '正常' when 1 then '单向停机' when 2 then '停机' when 3 then '预销户' when 4 then '销户' when 5 then '过户' when 6 then '改号' else '号码不存在' END AS phone_status,"
			       + " t.`package_name`"); 
			   FROM("bw_haodai_mobile t ");
              WHERE(" t.order_id= #{orderId}");
           ORDER_BY(" t.update_time DESC  limit 1");        
			}
		}.toString();
	}
	
	/**
	 * 查询用户账单信息.
	 * @param map
	 * @return
	 */
	public String queryBillData(Map<String,String> map){
		return new SQL(){
			{
			 SELECT(" t.`bill_month` AS month,"
				   + " t.`total_fee` AS call_pay,"
			       + " t.`base_fee` AS package_fee,"
			       + " t.`sms_fee` AS msg_fee,"
			       + " t.`voice_fee` AS tel_fee,"
			       + " t.web_fee AS net_fee,"
			       + " t.`extra_service_fee` AS addtional_fee,"
			       + " t.`discount` AS preferential_fee,"
			       + " '' AS generation_fee,"
			       + " t.`extra_fee` AS other_fee,"
			       + " t.`point` AS score,"
			       + " '' AS otherspaid_fee,"
			       + " t.`actual_fee` AS pay_fee"); 
			   FROM( " bw_haodai_bill t ");
              WHERE( " t.order_id=#{orderId}");
              WHERE( " t.bill_month between #{startTime} and #{endTime}");
           ORDER_BY( " t.bill_month ASC ");        
			}
		}.toString();
	}
	
	/**
	 * 统计订单对应月份的通话总条数.
	 * @param map
	 * @return
	 */
	public String queryCallCount(Map<String,String> map){
		return new SQL(){
			{
				SELECT("`bill_month` AS month,"
					 + "`total_size`");
				FROM(" bw_haodai_call");
				WHERE(" order_id =#{orderId}");
				WHERE(" bill_month =#{endTime}");
				ORDER_BY("bill_month ASC limit 1");
			}
		}.toString();
	};
	
	/**
	 * 查询订单号和月份所对应的通话详情.
	 * @param map
	 * @return
	 */
	public String queryCallData(Map<String,String> map){
		return new SQL(){
			{
			  SELECT("'' AS business_name,"
				   + " date_format(t.`time`,'%Y-%m-%d %H:%i:%S') AS call_time,"
				   + " case t.`dial_type` WHEN 'DIAL' THEN  1 WHEN 'DIALED' THEN 2 ELSE 3 END  AS  call_type,"
			       + " t.`fee`,"
			       + " '' AS special_offer,"
			       + " t.`location` AS trade_addr,"
			       + " t.`duration` AS trade_time,"
			       + " case t.`location_type` WHEN '本地主叫本地' THEN 1 WHEN '国内被叫' THEN 2 ELSE  3 END  AS trade_type,"
			       + " t.`peer_number` AS receive_phone");
				FROM(" `bw_haodai_call` t");
			   WHERE(" t.order_id =#{orderId}");
			   WHERE(" t.time between #{startTime} and #{endTime} ");
		    ORDER_BY(" t.bill_month desc");
			}
		}.toString();
	};
	
	/**
	 * 统计订单对应月份的短信总条数.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,String> map){
		return new SQL(){
			{
				SELECT("`bill_month` AS month,"
					 + "`total_size`");
				FROM(" bw_haodai_sms ");
				WHERE(" order_id =#{orderId}");
				WHERE(" bill_month =#{endTime}");
				ORDER_BY("bill_month asc limit 1");
			}
		}.toString();
	};
	
	/**
	 * 查询订单号和月份所对应的短信详情.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,String> map){
		return new SQL(){
			{
			  SELECT("'' AS business_name, "
				   + " t.fee, "
			       + " t.`peer_number` AS receiver_phone, "
			       + " date_format(t.time,'%Y-%m-%d %H:%i:%S') AS send_time, "
			       + " '' AS special_offer, "
			       + " t.`location` AS trade_addr, "
			       + " CASE t.msg_type WHEN 'SMS' THEN 1 WHEN 'MMS' THEN 2 ELSE 3 END AS trade_type, "
			       + " case t.send_type WHEN 'SEND' THEN 1 WHEN 'RECEIVE' THEN 2 ELSE 3 END AS trade_way");
				FROM(" `bw_haodai_sms` t");
			   WHERE(" t.order_id =#{orderId}");
			   WHERE(" t.time between #{startTime} and #{endTime} ");
		    ORDER_BY(" t.bill_month desc");
			}
		}.toString();
	};
	
	/**
	 * 统计订单对应月份的流量总条数.
	 * @param map
	 * @return
	 */
	public String queryNetCount(Map<String,String> map){
		return new SQL(){
			{
			   SELECT("`bill_month` AS month, "
					+ "`total_size` ");
				 FROM(" bw_haodai_net ");
				WHERE(" order_id = #{orderId} ");
				WHERE(" bill_month = #{endTime} ");
			 ORDER_BY("bill_month asc limit 1");
			}
		}.toString();
	};
	
	/**
	 * 查询订单号和月份所对应的短信详情.
	 * @param map
	 * @return
	 */
	public String queryNetData(Map<String,String> map){
		return new SQL(){
			{
			  SELECT(" t.`fee`,"
				   + " t.`net_type`,"
	               + " '' AS net_way,"
			       + " '' AS preferential_fee,"
			       + " date_format(t.time,'%Y-%m-%d %H:%i:%S') AS start_time,"
			       + " t.duration AS total_time,"
			       + " t.`subflow` AS total_traffic,"
			       + " t.`location` AS trade_addr");
				FROM(" `bw_haodai_net` t");
			   WHERE(" t.order_id = #{orderId}");
			   WHERE(" t.time between #{startTime} and #{endTime} ");
		    ORDER_BY(" t.time desc");
			}
		}.toString();
	};
}
