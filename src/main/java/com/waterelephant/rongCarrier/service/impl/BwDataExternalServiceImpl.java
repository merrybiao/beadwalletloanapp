package com.waterelephant.rongCarrier.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.waterelephant.rongCarrier.entity.BwDataExternal;
import com.waterelephant.rongCarrier.service.BwDataExternalService;
import com.waterelephant.service.BaseService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwDataExternalServiceImpl extends BaseService<BwDataExternal, Long> 
	implements BwDataExternalService {

	private final Logger logger = LoggerFactory.getLogger(BwDataExternalServiceImpl.class);
	
	@Override
	public boolean save(Map<String,String> params,String platform) throws Exception {
		
		String text = JSON.toJSONString(params);
		
		BwDataExternal record = JSON.parseObject(text,BwDataExternal.class);
		
		Assert.notNull(record, "类型转化异常");
		
		record.setPlatform(platform);
		BwDataExternal entity = new BwDataExternal();
		entity.setOutUniqueId(record.getOutUniqueId());
		int count = selectCount(entity);
		if(count >0) {
			throw new SQLException("重复的业务流水号~");
		}
		
		return save(record);
	}
	
	@Override
	public boolean save(BwDataExternal record) throws Exception {
		
		if(record == null) return false;
		
		record.setCreatedTime(new Date());
		record.setLastModifyTime(new Date());
		return insert(record)==1;
	}

	/**
	 * 
	 * @param userId 用户Id
	 * @param outUniqueId 流水号
	 * @param state 状态 ‘login’,crawl’, ‘crawl_fail’, ‘report’, ‘report_fail’
	 * @return boolean
	 */
	@Override
	public BwDataExternal updateState(String userId,String outUniqueId,String state) throws Exception {
		
		Assert.hasText(userId);
		Assert.hasText(outUniqueId);
		Assert.hasText(state);
		BwDataExternal entity = new BwDataExternal();
		entity.setUserId(userId);
		entity.setOutUniqueId(outUniqueId);
		
		BwDataExternal record = selectOne(entity);
		
		if(record != null && record.getId() != null && !state.equals(record.getState())) {
			record.setState(state);
			record.setLastModifyTime(new Date());
			updateByPrimaryKey(record);
			
			return record;
		}
		return record;
	}
	
	@Override
	public BwDataExternal updateSearchInfo(String userId,String outUniqueId,String searchId,String state,String account,String accountType,String errorReasonDetail) throws Exception {
		
		BwDataExternal entity = new BwDataExternal();
		entity.setUserId(userId);
		entity.setOutUniqueId(outUniqueId);
		
		BwDataExternal record = selectOne(entity);
		if(record != null && record.getId() != null) {
			record.setSearchId(searchId);
			record.setState(state);
			record.setAccount(account);
			record.setAccountType(accountType);
			record.setErrorReasonDetail(errorReasonDetail);
			record.setLastModifyTime(new Date());
			int result = updateByPrimaryKey(record);
			
			return result == 1 ? record : null;
		}
		logger.debug("bw_data_external表查无此记录~,userId:{},outUniqueId:{}",userId,outUniqueId);
		return null;
	}
	
	@Override
	public BwDataExternal selectRecord(String userId,String outUniqueId) {
		
		BwDataExternal record = new BwDataExternal();
		
		record.setUserId(userId);
		
		record.setOutUniqueId(outUniqueId);
		
		return selectOne(record);
	}
	
	@Override
	public BwDataExternal selectOneBySearchId(String searchId) {
		
		Example example = new Example(BwDataExternal.class);
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("searchId", searchId);
		
		example.setOrderByClause("id desc");
		
		List<BwDataExternal> list = selectByExample(example);
	
		return list == null || list.isEmpty() ? null : list.get(0);
	}

}
