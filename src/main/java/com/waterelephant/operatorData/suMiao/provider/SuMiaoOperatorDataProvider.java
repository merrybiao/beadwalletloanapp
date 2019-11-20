package com.waterelephant.operatorData.suMiao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class SuMiaoOperatorDataProvider {
	
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

}
