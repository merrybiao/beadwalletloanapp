package com.waterelephant.operatorData.xg.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.xg.entity.XgOperatorUser;

import tk.mybatis.mapper.common.Mapper;

public interface XgOperatorUserMapper extends Mapper<XgOperatorUser> {
	
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

}
