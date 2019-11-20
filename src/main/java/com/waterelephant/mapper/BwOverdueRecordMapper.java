package com.waterelephant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.entity.BwOverdueRecord;
import tk.mybatis.mapper.common.Mapper;

/**
 * 逾期
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月14日 下午1:57:02
 */
public interface BwOverdueRecordMapper extends Mapper<BwOverdueRecord>{
	
	@Select("<script>"
			+ "SELECT MAX(t.overdue_day) FROM bw_overdue_record t "
			+ " LEFT JOIN bw_order o ON t.order_id = o.id "
			+ " WHERE o.borrower_id = #{borrowerId} "
			+ " AND status_id IN "
			+ " <foreach item='state' index='index' collection='inStatus' open='(' separator=', ' close=')'>" 
		    + " #{state} "
		    + " </foreach> "
		    + "</script>")
	Integer queryMaxOverdueDays(@Param("borrowerId") Long borrowerId,@Param("inStatus") List<Integer> inStatus);

}
