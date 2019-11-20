package com.waterelephant.operatorData.xjbk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.xjbk.entity.BwXjbkTripInfo;

import tk.mybatis.mapper.common.Mapper;

public interface XjbkOperatorDataMapper extends Mapper<BwXjbkTripInfo> {
	
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

	@Select("SELECT " +
			"  t.cell_mth as month," + 
			"  t.call_cnt as total_size" +
			" FROM bw_xjbk_cell_behavior t" +
			" WHERE t.order_id = #{orderId} " +
			" AND t.cell_mth = #{month}"
			)
	OperatorTelDataDto getCallCount(@Param(value="orderId") Long orderId, @Param(value="month") String month);

	@Select("SELECT " + 
			"	t.trade_type," + 
			"	t.trade_time," + 
			"	t.call_time," + 
			"	t.trade_addr," + 
			"	t.receive_phone," + 
			"	t.call_type," + 
			"	'' as business_name," + 
			"	'' as fee," + 
			"	'' as special_offer" + 
			" FROM `bw_operate_voice` t " +
			" WHERE t.`borrower_id`= #{borrowerId}" + 
			" AND `call_time` BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getCallRecord(@Param(value="borrowerId") Long borrowerId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);
	
	@Select("SELECT " + 
			"	t.trade_type," + 
			"	t.trade_time," + 
			"	t.call_time," + 
			"	t.trade_addr," + 
			"	t.receive_phone," + 
			"	t.call_type," + 
			"	'' as business_name," + 
			"	'' as fee," + 
			"	'' as special_offer" + 
			" FROM `bw_operate_voice_1` t " +
			" WHERE t.`borrower_id`= #{borrowerId}" + 
			" AND `call_time` BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getCallRecord1(@Param(value="borrowerId") Long borrowerId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);
	
	@Select("SELECT " + 
			"	t.trade_type," + 
			"	t.trade_time," + 
			"	t.call_time," + 
			"	t.trade_addr," + 
			"	t.receive_phone," + 
			"	t.call_type," + 
			"	'' as business_name," + 
			"	'' as fee," + 
			"	'' as special_offer" + 
			" FROM `bw_operate_voice_2` t " +
			" WHERE t.`borrower_id`= #{borrowerId}" + 
			" AND `call_time` BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getCallRecord2(@Param(value="borrowerId") Long borrowerId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);
	
	@Select("SELECT " + 
			"	t.trade_type," + 
			"	t.trade_time," + 
			"	t.call_time," + 
			"	t.trade_addr," + 
			"	t.receive_phone," + 
			"	t.call_type," + 
			"	'' as business_name," + 
			"	'' as fee," + 
			"	'' as special_offer" + 
			" FROM `bw_operate_voice_3` t " +
			" WHERE t.`borrower_id`= #{borrowerId}" + 
			" AND `call_time` BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getCallRecord3(@Param(value="borrowerId") Long borrowerId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

}
