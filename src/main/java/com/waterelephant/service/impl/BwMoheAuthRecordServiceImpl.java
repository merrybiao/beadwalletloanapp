package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.waterelephant.entity.BwMoheAuthRecord;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMoheAuthRecordService;
import com.waterelephant.utils.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwMoheAuthRecordServiceImpl extends BaseService<BwMoheAuthRecord, Long> implements BwMoheAuthRecordService {

	@Override
	public boolean saveRecord(String productNo, String authType, String realName, String idcardNum, String userMobile,
			String reqId, String returnUrl, String notifyUrl) {
		
		try {
			BwMoheAuthRecord record = new BwMoheAuthRecord();
			record.setProductNo(productNo);
			record.setAuthType(authType);
			record.setRealName(realName);
			record.setIdcardNum(idcardNum);
			record.setUserMobile(userMobile);
			record.setAuthNotifyUrl(notifyUrl);
			record.setAuthReturnUrl(returnUrl);
			record.setReqId(reqId);
			record.setCreateTime(new Date());
			return mapper.insert(record)>0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public BwMoheAuthRecord updateRecord(String reqId, String taskId, String notifyEvent, String notifyType, String notifyTime) {
		BwMoheAuthRecord record = queryMoheAuthRecordByReqId(reqId);
		if(null == record) throw new BusinessException("该授权记录不存在~");
		try {
			if(!StringUtils.isEmpty(taskId))record.setTaskId(taskId);
			if(!StringUtils.isEmpty(notifyEvent))record.setNotifyEvent(notifyEvent);
			if(!StringUtils.isEmpty(notifyType))record.setNotifyType(notifyType);
			if(!StringUtils.isEmpty(notifyTime))record.setNotifyTime(DateUtil.stringToDate(notifyTime, DateUtil.yyyy_MM_dd_HHmmss));
			record.setUpdateTime(new Date());
			mapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("更新授权记录失败~");
		}
		return record;
	}
	
	public BwMoheAuthRecord queryMoheAuthRecordByReqId(String reqId) {
		Example example = new Example(BwMoheAuthRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("reqId", reqId);
		example.setOrderByClause("create_time desc");
		List<BwMoheAuthRecord> list = mapper.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public BwMoheAuthRecord queryAuthRecordByTaskId(String taskId) {
		Example example = new Example(BwMoheAuthRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("taskId", taskId);
		example.setOrderByClause("create_time desc");
		List<BwMoheAuthRecord> list = mapper.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}
	
	

}
