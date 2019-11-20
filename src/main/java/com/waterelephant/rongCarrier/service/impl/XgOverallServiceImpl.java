package com.waterelephant.rongCarrier.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgOverall;
import com.waterelephant.rongCarrier.service.XgOverallService;
import com.waterelephant.service.BaseService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 征信91
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 13:35
 */
@Service
public class XgOverallServiceImpl extends BaseService<XgOverall, Long> implements XgOverallService {

	@Override
	public boolean save(XgOverall xgOverall) {
		return mapper.insert(xgOverall) > 0;
	}

	@Override
	public boolean deleteZ(XgOverall xgOverall) {
		return mapper.delete(xgOverall) > 0;
	}

	@Override
	public void updateXgOverall(XgOverall xgOverall) {
		mapper.updateByPrimaryKeySelective(xgOverall);
	}

//	@Override
//	public XgOverall findXgOverall(Long borrowerId) {
//		StringBuilder sql = new StringBuilder("select a.* from bw_xg_overall a where 1=1 ");
//		sql.append(" and a.borrower_Id = ").append(borrowerId);
//		return sqlMapper.selectOne(sql.toString(), XgOverall.class);
//	}
	
	@Override
	public XgOverall findXgOverall(Long borrowerId) {
		Example example = new Example(XgOverall.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("borrowerId", borrowerId);
		
		List<XgOverall> list = selectByExample(example);
		
		return null == list || list.isEmpty() ? null : list.get(0);
	}

}
