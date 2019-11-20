package com.waterelephant.operatorData.kaniu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public interface KaNiuOperatorDataMapper {
	
	@Select("SELECT " + 
			"	DATE_FORMAT(IFNULL(t.update_time,t.create_time),'%Y-%m-%d %T') as updateTime," + 
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
			"	t.package_name as packageName" + 
			" FROM bw_operate_basic t " + 
			" WHERE t.borrower_id= #{borrowerId} "+
			" ORDER BY t.update_time DESC "+
			" LIMIT 1")
	OperatorUserDataDto queryUserData(@Param(value="borrowerId") Long borrowerId);
	
	
	@Select("SELECT "
			+ " t.number as total_size,"
			+ " t.DATE as month "
			+ " FROM (select date_format(`call_time`,'%Y-%m') as DATE,"
			+ " COUNT(id) as number "
			+ " from `bw_operate_voice`  "
			+ " where borrower_id=#{borrowerId} "
			+ " GROUP BY DATE) t"
			+ " where t.DATE = #{endTime}")
	OperatorTelDataDto getKaNiuCallCount(@Param(value="borrowerId") Long borrowerId, @Param(value="endTime") String endTime);

	@Select("SELECT " + 
			"	t.trade_type," + 
			"	t.trade_time," + 
			"	DATE_FORMAT(t.call_time,'%Y-%m-%d %T') AS call_time," + 
			"	t.trade_addr," + 
			"	t.receive_phone," + 
			"	t.call_type," + 
			"	'' as business_name," + 
			"	'' as fee," + 
			"	'' as special_offer" + 
			" FROM `bw_operate_voice` t " +
			" WHERE t.`borrower_id`= #{borrowerId}" + 
			" AND `call_time` BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getKaNiuCallRecord(@Param(value="borrowerId") Long borrowerId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);


}
