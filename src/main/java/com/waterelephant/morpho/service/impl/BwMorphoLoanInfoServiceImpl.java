package com.waterelephant.morpho.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.morpho.entity.BwMorphoLoanInfo;
import com.waterelephant.morpho.service.BwMorphoLoanInfoService;
import com.waterelephant.service.BaseService;

/*
 * code:18002
 * 批核&贷后数据
 */
@Service
public class BwMorphoLoanInfoServiceImpl extends BaseService<BwMorphoLoanInfo, Long> implements BwMorphoLoanInfoService {

	@Override
	public void save(BwMorphoLoanInfo d360Info) {
		mapper.insert(d360Info);
		
	}
	
	//code:18002-2
	@Override
	public List<BwMorphoLoanInfo> findRecordsByPid(String pid) {
		String sql = "SELECT veidoo_type,report_type,query_count,loan_count,loan_tenant_count,average_loan_gap_days,average_tenant_gap_days,"
				   + "max_loan_amount,max_loan_period_days,average_loan_amount,max_overdue_days,overdue_loan_count,overdue_tenant_count,"
				   + "overdue_for2_term_tenant_count,days_from_last_loan,months_from_first_loan,months_from_last_overdue,months_for_normal_repay,"
				   + "remaining_amount,id_no FROM bw_morpho_loan_info WHERE id_no = " + pid 
				   + " AND DATEDIFF(create_time,NOW()) <= 0 AND DATEDIFF(create_time,NOW()) > -30";
		return sqlMapper.selectList(sql, BwMorphoLoanInfo.class);
	}

	@Override
	public List<BwMorphoLoanInfo> findRecordsPlusByPid(String pid) {
		String sql = "SELECT * FROM bw_morpho_loan_info WHERE id_no = " + pid 
				+ " AND DATEDIFF(create_time,NOW()) <= -30 LIMIT 10";
	return sqlMapper.selectList(sql, BwMorphoLoanInfo.class);
	}

	@Override
	public void deleteByPid(String pid) {
		String sql = "DELETE FROM bw_morpho_loan_info WHERE id_no = " + pid;
		sqlMapper.delete(sql);
	}

}
