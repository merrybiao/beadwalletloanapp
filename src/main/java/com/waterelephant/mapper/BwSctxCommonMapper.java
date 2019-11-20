package com.waterelephant.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BwSctxCommonMapper {
	
	@Select("SELECT IFNULL(b.`name`,'"+""+"') AS name,"
			+ " IFNULL(b.`phone`,'"+""+"') AS phone,"
			+ " IFNULL(b.`idCard`,'"+""+"') AS idCard,"
			+ " a.`overdue_amt`,"
			+ " a.`plat_code`,a.`loan_type`"
			+ " FROM bw_sctx_blackInfo a"
			+ " LEFT JOIN bw_sctx_baseInfo b"
			+ " ON a.`black_id` = b.`id`"
			+ " WHERE b.`only_id` = #{onlyid} "
			+ " order by a.`create_time` desc")
	List<Map<String,String>> queryAllDataByOnlyId(@Param("onlyid")String onlyid);

}
