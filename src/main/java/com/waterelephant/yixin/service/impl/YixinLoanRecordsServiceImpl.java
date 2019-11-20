/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.yixin.dto.param.YixinLoanRecords;
import com.waterelephant.yixin.service.YixinLoanRecordService;

/**
 * @author Administrator
 *
 */
@Service
public class YixinLoanRecordsServiceImpl extends BaseService<YixinLoanRecords, Long> implements YixinLoanRecordService {

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public int deleteYixinLoanRecord(Long mainid) {
		YixinLoanRecords loanRecords=new YixinLoanRecords();
		loanRecords.setMainid(mainid);
		return mapper.delete(loanRecords);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public int insertYixinLoanRecord(YixinLoanRecords loanRecords) {
		
		return mapper.insert(loanRecords);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public List<YixinLoanRecords> getYixinLoanRecord(Long mainid) {
		YixinLoanRecords loanRecords=new YixinLoanRecords();
		loanRecords.setMainid(mainid);
		return mapper.select(loanRecords);
	}

}
