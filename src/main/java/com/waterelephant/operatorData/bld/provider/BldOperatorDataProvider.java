package com.waterelephant.operatorData.bld.provider;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BldOperatorDataProvider {
	
	/**
	 * 获取基本数据
	 * @param orderId
	 * @return
	 */
	public String queryUserData(@Param("borrowerId") Long borrowerId){
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
					"	t.package_name as packageName" );
                FROM("bw_operate_basic t ");         
               WHERE(" t.borrower_id=#{borrowerId}");
            ORDER_BY(" t.update_time desc limit 1");      
			}
		}.toString();
	};
	
	
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
}
