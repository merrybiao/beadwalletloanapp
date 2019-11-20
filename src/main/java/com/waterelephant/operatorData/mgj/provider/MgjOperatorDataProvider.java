package com.waterelephant.operatorData.mgj.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class MgjOperatorDataProvider {
	
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
	 * 查询账单记录信息.
	 * @param map
	 * @return
	 */
	public String queryCallBillData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" t.`bill_cycle` AS month, "
					 + " t.`total_amt` AS call_pay, "
					 + " t.`plan_amt` AS package_fee, "
					 + " '' AS msg_fee, "
					 + " '' AS tel_fee, "
					 + " '' AS net_fee, "
					 + " '' AS addtional_fee, "
					 + " '' AS preferential_fee, "
					 + " '' AS generation_fee, "
					 + " '' AS other_fee, "
					 + " '' AS score, "
					 + " '' AS otherspaid_fee, "
					 + "  t.`pay_amt` AS pay_fee");
				FROM(" bw_ym_phone_bill t");
			   WHERE(" t.`order_id` = #{orderId} ");
			   WHERE(" t.bill_cycle between #{startTime} AND #{endTime}");
			   ORDER_BY("t.`bill_cycle` ASC");
			}
		}.toString();
	}
	
	/**
	 * 查询短信月份所对应的条数.
	 * @param map
	 * @return
	 */
	public String queryMsgCount(Map<String,Object> map){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month "
			    + " FROM (select date_format(`start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number "
				+ " from `bw_ym_sms_bill` "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	/**
	 * 查询短信每月对应的详细数据.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT(" '' AS business_name, "
					 + "  t.subtotal AS fee, "
					 + "  t.`other_cell_phone` AS receiver_phone, "
					 + "  DATE_FORMAT(t.`start_time`,'%Y-%m-%d %T') AS send_time, "
					 + "  '' AS special_offer, "
					 + "  t.`place` AS trade_addr, "
					 + "  '' AS trade_type, "
					 + "  CASE t.`init_type` WHEN '短信发' THEN 1 WHEN '短信收' THEN 2 ELSE 3 END"
					 + " AS trade_way");
				FROM("`bw_ym_sms_bill` t");
			   WHERE("t.`order_id` = #{orderId} ");
			   WHERE(" t.start_time between #{startTime} AND #{endTime}");
			ORDER_BY("t.`start_time` ASC");
				
			}
		}.toString();
	}
	
	/**
	 * 查询流量月份所对应的条数.
	 * @param map
	 * @return
	 */
	public String queryNetCount(){
		return  "SELECT  t.number as total_size, "
				+ " t.DATE as month "
			    + " FROM (select date_format(`start_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number "
				+ " from `bw_ym_net_bill` "
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	/**
	 * 查询月份对应的流量的详细数据.
	 * @param map
	 * @return
	 */
	public String queryNetData(Map<String,Object> map){
		return new SQL(){
			{
				SELECT("  t.`subtotal` AS fee, "
					 + "  t.`net_type`, "
					 + "  '' AS net_way, "
					 + "  '' AS preferential_fee, "
					 + "  DATE_FORMAT(t.`start_time`,'%Y-%m-%d %T') AS start_time, "
					 + "  t.`use_time` AS total_time, "
					 + "  t.`subflow` AS total_traffic, "
					 + "  t.`place` AS trade_addr ");
				FROM("`bw_ym_net_bill` t");
				WHERE("t.`order_id` = #{orderId} ");
				WHERE("t.start_time between #{startTime} AND #{endTime}");
			 ORDER_BY("t.`start_time` ASC");
			}
		}.toString();
	}
}
