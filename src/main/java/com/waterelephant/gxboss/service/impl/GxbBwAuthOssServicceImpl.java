package com.waterelephant.gxboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBorrowerCredit;
import com.waterelephant.entity.BwCreditRecord;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.gxboss.service.GxbOssServicce;
import com.waterelephant.mapper.BwBorrowerCreditMapper;
import com.waterelephant.mapper.BwCreditRecordMapper;
import com.waterelephant.mapper.BwOrderAuthMapper;
import com.waterelephant.mapper.BwOrderMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class GxbBwAuthOssServicceImpl implements GxbOssServicce{
	
	@Autowired
	private BwCreditRecordMapper bwCreditRecordMapper;
	
	@Autowired
	private BwBorrowerCreditMapper bwBorrowerCreditMapper;
	
	@Autowired
	private BwOrderAuthMapper bwOrderAuthMapper;
	
	@Autowired
	private BwOrderMapper bwOrderMapper;
	
	@Override
	public Long queryBwBorrowerCreditByCreditNo(String creditNo) {
		Example example = new Example(BwBorrowerCredit.class);		
		Criteria criteria = example.createCriteria(); 
		criteria.andEqualTo("creditNo", creditNo);
		example.setOrderByClause("create_time desc");
		List<BwBorrowerCredit> list =bwBorrowerCreditMapper.selectByExample(example);
		return null == list || list.isEmpty() ? null : list.get(0).getId();
	}
	
	@Override
	public Long queryBwOrderIdByCreditNo(String creditNo) {
		Example example = new Example(BwOrder.class);		
		Criteria criteria = example.createCriteria(); 
		criteria.andEqualTo("orderNo",creditNo); 
		example.setOrderByClause("create_time desc");
		List<BwOrder> list =bwOrderMapper.selectByExample(example);
		return null == list || list.isEmpty() ? null : list.get(0).getId();
	}
	
	
	@Override
	public String queryBwCreditURLByRelationId(Long relationId,Integer relationType,Integer creditType) {
		Example example = new Example(BwCreditRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("relationType", relationType);
		criteria.andEqualTo("creditType", creditType);
		criteria.andEqualTo("relationId", relationId);
		example.setOrderByClause("create_time desc");
		List<BwCreditRecord> list = bwCreditRecordMapper.selectByExample(example);
		return null == list || list.isEmpty() ? null : list.get(0).getOssFileUrl();
	}
}
