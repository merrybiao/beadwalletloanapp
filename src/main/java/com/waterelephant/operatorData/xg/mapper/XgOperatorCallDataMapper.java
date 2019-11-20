package com.waterelephant.operatorData.xg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.xg.entity.XgOperatorOverall;

import tk.mybatis.mapper.common.Mapper;

public interface XgOperatorCallDataMapper extends Mapper<XgOperatorOverall> {
	
	@Select("SELECT " +
			"  t.month as month," + 
			"  t.call_cnt as total_size" +
			" FROM bw_xg_area_analysis_detail t" +
			" WHERE t.borrower_id = #{borrowerId} " +
			" AND t.month = #{month} ORDER BY id DESC LIMIT 1")
	OperatorTelDataDto getCallCount(@Param(value="borrowerId") Long borrowerId, @Param(value="month") String month);
	
	@Select("SELECT " +
			"  t.month as month," + 
			"  t.call_cnt as total_size" +
			" FROM bw_xg_area_analysis_detail_new t" +
			" WHERE t.borrower_id = #{borrowerId} " +
			" AND t.month = #{month} ORDER BY id DESC LIMIT 1")
	OperatorTelDataDto getCallCountV2(@Param(value="borrowerId") Long borrowerId, @Param(value="month") String month);

	@Select("SELECT " + 
			" t.trade_type," + 
			" t.trade_time," + 
			" t.call_time," + 
			" t.trade_addr," + 
			" t.receive_phone," + 
			" t.call_type," + 
			" '' as business_name," + 
			" '' as fee," + 
			" '' as special_offer" + 
			" FROM `bw_operate_voice` t " +
			" WHERE t.`borrower_id`= #{borrowerId}" + 
			" AND `call_time` BETWEEN #{startTime} AND #{endTime}")
	List<Map<String,String>> getCallRecord(@Param(value="borrowerId") Long borrowerId, @Param(value="startTime") String startTime,  @Param(value="endTime") String endTime);

}
