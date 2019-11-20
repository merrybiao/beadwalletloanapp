package com.waterelephant.operatorData.xl.provider;

import java.util.Map;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class XlOperatorDataProvider {
	
	/**
	 * 获取基本数据
	 * @param orderId
	 * @return
	 */
	public String selectUserData(@Param("orderId") Long order_id){
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
				  + " t.`package_name`  ");
                FROM("bw_third_operate_basic t ");         
                if(StringUtils.hasText(order_id+"")){
            WHERE(" t.order_id=#{orderId}");
         ORDER_BY(" t.update_time desc limit 1");
                }           
			}
		}.toString();
	};
	
	/**
	 * 获取通话总数和时间
	 * @param orderId
	 * @return
	 */
	public String getXlCallCount(Map<String,String> param){
		return new SQL(){
			{
				SELECT("t.cell_mth as month,"  
					  +"t.call_cnt as total_size");
				FROM(" `bw_xl_cell_behavior` t ");         
				if(param.containsKey("orderId")){
					WHERE(" t.`order_id`=#{orderId}");
				}  
				if(param.containsKey("endTime")){
					WHERE(" t.`cell_mth`= #{endTime}");
				}
			}
		}.toString();
	};
	
	/**
	 * 查询通话记录详情
	 * @param param
	 * @return
	 */
	public String getXlCallData(Map<String,String> param){
		return new SQL(){
			{
				SELECT( " '' AS business_name,"
						+ " DATE_FORMAT(a.`start_time`,'%Y-%m-%d %H:%i:%S') AS call_time,"
						+ " case a.`init_type` when '主叫' then 1 when '被叫' then 2 else 3 end AS call_type,"
						+ " a.`subtotal` AS fee,"
						+ " '' AS special_offer,"
						+ " a.`place` AS trade_addr,"
						+ " a.`use_time` AS trade_time,"
						+ " case a.`call_type` when '本地主叫本地' then 1 when '国内被叫' then 2 else 3 end AS trade_type,"
						+ " a.`other_cell_phone` AS receive_phone");
				FROM(" `bw_xl_calls` a ");
				WHERE(" a.`order_id` = #{orderId}");
				WHERE(" a.`start_time` BETWEEN #{startTime} AND #{endTime} ");
				ORDER_BY(" a.`start_time` asc");
			}
		}.toString();
	}
	
	/**
	 * 获取流量总数和时间
	 * @param orderId
	 * @return
	 */
	public String getXlNetCount(Map<String,String> param){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_xl_nets` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	};
	

	/**
	 * 获取流量记录数据
	 * @param orderId
	 * @return
	 */
	public String getXlNetData(Map<String,String> param){
	  return  " select "
			+ " t.`subtotal` as fee,"
			+ " t.`net_type` as `net_type` ,"
			+ " '' as net_way,"
			+ " '' as preferential_fee,"
			+ " DATE_FORMAT(t.`start_time`, '%Y-%m-%d %H:%i:%S') as start_time,"
			+ " t.`use_time` AS total_time,"
			+ " t.`subflow` as total_traffic,"
			+ " t.`place` as trade_addr"
			+ " from bw_xl_nets  t "
			+ " where t.`order_id` = #{orderId} "
			+ " and t.`start_time` between #{startTime} and #{endTime} "
			+ " order by t.`start_time` asc";
	}
	
	/**
	 * 获取短信总数和时间
	 * @param orderId
	 * @return
	 */
	public String getXlMsgCount(Map<String,String> param){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_xl_smses` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	};
	
	/**
	 * 获取短信记录数据
	 * @param orderId
	 * @return
	 */
	public String getXlMsgData(Map<String,String> param){
		return  " SELECT '' as business_name,"
				+ " t.`subtotal` as fee, "
				+ " t.`other_cell_phone` as receiver_phone, "
				+ " DATE_FORMAT(t.`start_time`, '%Y-%m-%d %H:%i:%S') as send_time,'' as special_offer, "
				+ " t.`place` as trade_addr, "
				+ " '' as trade_type, "
				+ " '' as trade_way "
				+ " FROM `bw_xl_smses` t"
				+ " where t.`start_time` BETWEEN #{startTime} and #{endTime} "
				+ " and t.`order_id` = #{orderId} "
				+ " order by t.`start_time` asc";
	};
	
	/**
	 * 获取短信总数和时间
	 * @param orderId
	 * @return
	 */
	public String getXlBillCount(Map<String,String> param){
		return  " SELECT  t.number as total_size, "
			  + " t.DATE as month "
			  + " FROM (select date_format(`bill_cycle`,'%Y-%m') as DATE,"
			  + " COUNT(id) as number "
			  + " from `bw_xl_transactions` "
			  + " where order_id=#{orderId} "
			  + " GROUP BY DATE) t "
			  + " where t.DATE = #{endTime} ";
	};
	
	/**
	 * 获取短信记录数据
	 * @param orderId
	 * @return
	 */
	public String getXlBillData(Map<String,String> param){
		return  "SELECT DATE_FORMAT(t.`bill_cycle`,'%Y-%c') AS MONTH,"
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
				+ " '' AS pay_fee "
				+ " FROM bw_xl_transactions t "
				+ " WHERE t.`bill_cycle` BETWEEN  #{startTime} and #{endTime} "
				+ " and  t.`order_id` = #{orderId}";
	};
}
