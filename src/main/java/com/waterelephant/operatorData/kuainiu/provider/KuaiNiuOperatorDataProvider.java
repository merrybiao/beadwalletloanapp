package com.waterelephant.operatorData.kuainiu.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class KuaiNiuOperatorDataProvider {
	
	public String selectUserData(Long order_id){
		return new SQL(){
			{
			 SELECT(" '' AS user_source,"
			 		+ " t.`id_card`,"
			 		+ " '' AS addr,"
			 		+ " t.`real_name` AS real_name,"
			 		+ " ''AS phone_remain,"
			 		+ " t.`cell_phone` AS phone,"
			 		+ " date_format(t.`reg_time`,'%Y-%m-%d %H:%i:%S') AS reg_time,"
			 		+ " date_format(IFNULL(t.`update_time`,t.`create_time`),'%Y-%m-%d %H:%i:%S') AS update_time,"
			 		+ " '' AS score,'' AS contact_phone,"
			 		+ " '' AS star_level,"
			 		+ " '' AS authentication,"
			 		+ " '' AS phone_status,"
			 		+ " '' AS package_name ");
                FROM(" `bw_kuainiu_basicinfo` t");         
                if(StringUtils.hasText(order_id+"")){
                    WHERE(" t.order_id= #{order_id}");
                    ORDER_BY(" t.update_time desc limit 1");
                }           
			}
		}.toString();
	}
	
	public String selectBillData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT(" date_format(t.`bill_cycle`, '%Y-%m') AS month, "
			       + " t.`total_amt` AS call_pay, "
			       + " t.`plan_amt` AS package_fee, "
			       + " '' AS msg_fee, "
			       + " '' AS tel_fee, "
			       + " '' AS net_fee, "
			       + " '' AS addtional_fee, "
			       + " '' AS preferential_fee, "
			       + " '' as generation_fee, "
			       + " '' AS other_fee, "
			       + " '' AS score, "
			       + " '' AS otherspaid_fee, "
			       + " t.`pay_amt` AS pay_fee");
                FROM(" `bw_kuainiu_transactions` t");         
                WHERE(" t.order_id=#{orderId}");
                WHERE(" t.`bill_cycle` BETWEEN #{startTime} AND #{endTime} ");
             ORDER_BY(" t.bill_cycle asc ");    
			}
		}.toString();
	}
	
	public String selectCallCount(Map<String,Object> map){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_kuainiu_calls` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	public String selectCallData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT(" '' AS business_name, "
				   + " date_format(t.`start_time`,'%Y-%m-%d %H:%i:%S') AS call_time, "
			       + " case t.`init_type` when '主叫' then 1 when '被叫' then 2 else 3 end AS call_type, "
			       + " '0' AS fee, "
			       + " '' AS special_offer, "
			       + " t.`place` AS trade_addr, "
			       + " t.`user_time` AS trade_time, "
			       + " case t.`call_type` when '国内被叫' THEN 2 when '国内异地主叫' then 2 when '本地主叫本地' THEN 1 when '本地主叫异地' THEN 1 else 3 end AS trade_type, "
			       + " t.`other_cell_phone` AS receive_phone");
	          FROM(" `bw_kuainiu_calls` t");         
              WHERE(" t.order_id=#{orderId}");
              WHERE(" t.`start_time` BETWEEN #{startTime} AND #{endTime} ");
           ORDER_BY(" t.start_time asc ");    
			}
		}.toString();
	}

	public String selectMsgCount(Map<String,Object> map){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_kuainiu_smses` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	public String selectMsgData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT(" '' AS business_name, "
				  + " t.`sub_total` AS fee, "
			      + " t.`other_cell_phone` AS receiver_phone, "
			      + " date_format(t.`start_time`,'%Y-%m-%d %H:%i:%S') AS send_time, "
			      + " '' AS special_offer, "
			      + " t.`place` AS trade_addr, "
			      + " '' AS trade_type, "
			      + " case t.`init_type` WHEN '发送' THEN 1 WHEN '接收' THEN 2 ELSE 3 END AS trade_way");
	          FROM(" `bw_kuainiu_smses` t");         
              WHERE(" t.order_id=#{orderId}");
              WHERE(" t.`start_time` BETWEEN #{startTime} AND #{endTime} ");
           ORDER_BY(" t.start_time asc ");    
			}
		}.toString();
	}
	
	public String selectNetCount(Map<String,Object> map){
		return  " SELECT  t.number as total_size,"
				+ " t.DATE as month "
				+ " FROM (select date_format(`start_time`,'%Y-%m') as DATE,"
				+ " COUNT(id) as number "
				+ " from `bw_kuainiu_nets` " 
				+ " where order_id=#{orderId} "
				+ " GROUP BY DATE) t "
				+ " where t.DATE = #{endTime} ";
	}
	
	public String selectNetData(Map<String,Object> map){
		return new SQL(){
			{
			 SELECT("  t.`sub_total` AS fee, "
				   + " t.net_type, "
			       + " '' AS net_way, "
			       + " '' AS preferential_fee, "
			       + " date_format(t.`start_time`,'%Y-%m-%d %H:%i:%S') AS start_time,"
			       + " t.`use_time` AS total_time, "
			       + " t.subflow AS total_traffic, "
			       + " t.`place` AS trade_addr");
	          FROM(" `bw_kuainiu_nets` t");         
              WHERE(" t.order_id=#{orderId}");
              WHERE(" t.`start_time` BETWEEN #{startTime} AND #{endTime} ");
           ORDER_BY(" t.start_time asc ");    
			}
		}.toString();
	}
}
