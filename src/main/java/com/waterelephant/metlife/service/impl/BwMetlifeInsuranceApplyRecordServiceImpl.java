package com.waterelephant.metlife.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.waterelephant.entity.BwMetlifeInsuranceApplyRecord;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.metlife.service.BwMetlifeInsuranceApplyRecordService;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;
import com.waterelephant.service.BaseService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwMetlifeInsuranceApplyRecordServiceImpl 
	extends BaseService<BwMetlifeInsuranceApplyRecord, Long> 
	implements BwMetlifeInsuranceApplyRecordService {
	
	private Logger logger = LoggerFactory.getLogger(BwMetlifeInsuranceApplyRecordServiceImpl.class);
	
	@Override
	public Long save(MetLifeInsuredVo vo,Integer applyState)  throws Exception {
		try {
			BwMetlifeInsuranceApplyRecord record = new BwMetlifeInsuranceApplyRecord();
			BeanUtils.copyProperties(record, vo);
			record.setApplyState(applyState);
			record.setCreateTime(new Date());
			if(mapper.insert(record)>0)
			return record.getId();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存保险申请记录异常：异常信息：{}",e.getMessage());
			throw new BusinessException("保存保险申请记录失败~");
		}
		return null;
	}
	
	
	
	@Override
	public int queryApplyRecordCount(String orderNo, List<Object> applyState) {
		
		Example example = new Example(BwMetlifeInsuranceApplyRecord.class);
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("orderNo", orderNo);
		
		if(null != applyState && applyState.isEmpty()) {
			criteria.andIn("applyState", applyState);
		}
		return selectCountByExample(example);
	}

	

	@Override
	public boolean updateApplyRecord(Long id, int state, String message) {

		BwMetlifeInsuranceApplyRecord record = new BwMetlifeInsuranceApplyRecord();
		record.setId(id);
		record.setApplyState(state);
		record.setMessage(message);
		record.setUpdateTime(new Date());
		
		return mapper.updateByPrimaryKeySelective(record)>0;
		
	}



	@Override
	public List<BwMetlifeInsuranceApplyRecord> queryApplyRecord(String productNo,String trimDate,String orderNo,String insuredIdNo,String insuredName,String insuredMobile) {
		Example example = new Example(BwMetlifeInsuranceApplyRecord.class);
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("productNo", productNo);
		
		if(!StringUtils.isEmpty(orderNo))
			criteria.andEqualTo("orderNo", orderNo);
		if(!StringUtils.isEmpty(insuredIdNo))
			criteria.andEqualTo("insuredIdNo", insuredIdNo);
		if(!StringUtils.isEmpty(insuredName))
			criteria.andEqualTo("insuredName", insuredName);
		if(!StringUtils.isEmpty(insuredMobile))
			criteria.andEqualTo("insuredMobile", insuredMobile);
		
		example.setOrderByClause("create_time ASC");
		
		return mapper.selectByExample(example);
	}



	@Override
	public BwMetlifeInsuranceApplyRecord queryApplyRecordByOrderNo(String orderNo,String productNo) {
		
		Example example = new Example(BwMetlifeInsuranceApplyRecord.class);
		
		Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(productNo))
			criteria.andEqualTo("productNo", productNo);
		
		criteria.andEqualTo("orderNo", orderNo);
		
		example.setOrderByClause("create_time DESC");
		
		List<BwMetlifeInsuranceApplyRecord> list =  selectByExample(example);
		
		return (null == list || list.isEmpty()) ? null : list.get(0);
	}

//	@Override
//	public BwMetlifeInsuranceApplyRecord queryApplyState(String orderNo,String productNo) {
//		
//		Example example = new Example(BwMetlifeInsuranceApplyRecord.class);
//		
//		Criteria criteria = example.createCriteria();
//		
//		if(!StringUtils.isEmpty(productNo))
//			criteria.andEqualTo("productNo", productNo);
//		
//		criteria.andEqualTo("orderNo", orderNo);
//		
//		example.setOrderByClause("create_time desc");
//		
//		List<BwMetlifeInsuranceApplyRecord> list = selectByExample(example);
//		
//		return (null == list || list.isEmpty()) ? null : list.get(0);
//	}
}
