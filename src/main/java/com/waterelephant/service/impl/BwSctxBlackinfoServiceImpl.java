package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwSctxBlackinfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwSctxBlackinfoService;
import com.waterelephant.utils.CommUtils;
@Service
public class BwSctxBlackinfoServiceImpl  extends BaseService<BwSctxBlackinfo, Long> implements  IBwSctxBlackinfoService{

	@Override
	public boolean saveSctxBlack(Long blackId, String overdue_amt,
			String plat_code, String loan_type) {
		BwSctxBlackinfo black = new BwSctxBlackinfo();
		if(!CommUtils.isNull(overdue_amt)){
			black.setOverdueAmt(overdue_amt);
		}
		if(!CommUtils.isNull(plat_code)){
			black.setPlatCode(plat_code);
		 }
		if(!CommUtils.isNull(loan_type)){
			black.setLoanType(loan_type);
		}
		black.setBlackId(blackId);
		black.setCreateTime(new Date());
		black.setUpdateTime(new Date());
		return insert(black) > 0;
	}
	
	
	
}
