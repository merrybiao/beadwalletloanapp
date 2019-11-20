/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service;

import java.util.List;

import com.waterelephant.yixin.dto.param.YixinLoanRecords;

/**
 * @author Administrator
 *
 */
public interface YixinLoanRecordService {

	public int deleteYixinLoanRecord(Long mainid);
	
	public int insertYixinLoanRecord(YixinLoanRecords loanRecords);
	
	public List<YixinLoanRecords> getYixinLoanRecord(Long mainid);
}
