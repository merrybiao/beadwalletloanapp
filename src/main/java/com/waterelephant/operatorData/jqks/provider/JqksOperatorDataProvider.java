package com.waterelephant.operatorData.jqks.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class JqksOperatorDataProvider {
	
	/**
	 * 用户基础信息.
	 * @param order_id
	 * @return
	 */
	public String queryUserData(Long orderId){
		return new SQL(){
			{
			  SELECT(" DATE_FORMAT(IFNULL(t.update_time,t.create_time),'%Y-%m-%d %T') as updateTime," + 
					 " t.user_source as userSource," + 
					 " t.id_card as idCard," + 
					 " t.addr," + 
					 " t.real_name as realName," + 
					 " t.phone_remain as phoneRemain," + 
					 " t.phone," + 
					 " DATE_FORMAT(t.reg_time,'%Y-%m-%d %T') as regTime," + 
					 " t.score as score," + 
					 " t.contact_phone as contactPhone," + 
					 " t.star_level as starLevel," + 
					 " t.authentication," + 
					 " t.phone_status as phoneStatus," + 
					 " t.package_name as packageName");
				FROM("bw_operate_basic t");		
			   WHERE("t.borrower_id= #{borrowerId}");
		    ORDER_BY("t.update_time DESC  LIMIT 1");
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
				+ " t.DATE as month "
			    + " FROM (select date_format(`call_time`,'%Y-%m') as DATE, "
			    + " COUNT(id) as number  "
				+ " from `bw_operate_voice` "
				+ " where borrower_id=#{borrowerId} "
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
			 SELECT(" t.trade_type," + 
					" t.trade_time," + 
					" DATE_FORMAT(t.call_time,'%Y-%m-%d %T') AS call_time," + 
					" t.trade_addr," + 
					" t.receive_phone," + 
					" t.call_type," + 
					" '' as business_name," + 
					" '' as fee," + 
					" '' as special_offer");
			   FROM(" `bw_operate_voice` t");
		      WHERE(" t.`borrower_id` = #{borrowerId} ");
		      WHERE(" t.call_time between #{startTime} AND #{endTime}");
		   ORDER_BY(" t.call_time ASC");
			}
		}.toString();
	};
	
	/**
	 * 查询每月账单信息.
	 * @param map
	 * @return
	 */
	public String queryBillData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  t.`bill_cycle` AS MONTH," 
				  + "  t.`bill_total` AS call_pay," 
				  + " '' AS package_fee," 
				  + " '' AS msg_fee," 
				  + " '' AS tel_fee," 
				  + " '' AS net_fee," 
				  + " '' AS addtional_fee," 
				  + " t.`bill_discount` AS preferential_fee ," 
				  + " '' AS generation_fee," 
				  + " '' AS other_fee," 
				  + " '' AS score," 
				  + " '' AS otherspaid_fee," 
				  + " t.`paid_amount` AS pay_fee");
			   FROM(" `bw_jqks_bill_info` t");
		      WHERE(" t.`order_id` = #{orderId} ");
		      WHERE(" t.bill_cycle between #{startTime} AND #{endTime}");
		   ORDER_BY(" t.bill_cycle ASC");
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
				+ " from `bw_jqks_sms_info`   "
				+ " where order_id=#{orderId}   "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	};


	/**
	 * 查询每月短信信息.
	 * @param map
	 * @return
	 */
	public String queryMsgData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT(" t.`msg_biz_name` AS business_name, "
				  + " t.`msg_fee` AS fee, "
				  + " t.`msg_other_num` AS receiver_phone, "
				  + " DATE_FORMAT(t.`msg_start_time`,'%Y-%m-%d %T') AS send_time, "
				  + " '' AS special_offer, "
				  + " t.`msg_address` AS trade_addr, "
				  + " case t.`msg_channel` when '短信' then 1 when '彩信' then 2 else 3 end as trade_type, "
				  + " case t.`msg_type` when '发送' then 1 when '接收' then 2 else 3 end as trade_way");
			   FROM(" `bw_jqks_sms_info` t");
		      WHERE(" t.`order_id` = #{orderId} ");
		      WHERE(" t.msg_start_time between #{startTime} AND #{endTime}");
		   ORDER_BY(" t.msg_start_time ASC");
			}
		}.toString();
	};

}
