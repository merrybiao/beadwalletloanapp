package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwZmxyGradeService;

/**
 * 芝麻评分业务实现
 * 
 * @author song
 *
 */
@Service
public class BwZmxyGradeServiceImpl extends BaseService<BwZmxyGrade, Long> implements BwZmxyGradeService {

	@Override
	public BwZmxyGrade findZmxyGradeByBorrowerId(Long borrowerId) {
		String sql = "select z.* from bw_zmxy_grade z where z.borrower_id=#{borrowerId} LIMIT 1";
		return sqlMapper.selectOne(sql, borrowerId, BwZmxyGrade.class);
	}

	@Override
	public int saveBwZmxyGrade(BwZmxyGrade bwZmxyGrade) {
		return mapper.insert(bwZmxyGrade);
	}

	@Override
	public int updateBwZmxyGrade(BwZmxyGrade bwZmxyGrade) {
		return mapper.updateByPrimaryKey(bwZmxyGrade);
	}
	
	
}
