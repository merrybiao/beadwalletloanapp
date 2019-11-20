package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwGxbAuthRecord;
import com.waterelephant.gxb.dto.AuthInfoDto;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwGxbAuthRecordService;
import com.waterelephant.utils.DateUtil;
import com.yeepay.g3.sdk.yop.utils.Assert;
/**
 * GxbAuthRecordServiceImpl.java
 * 公信宝-授权记录
 * @author dinglinhao
 * @date  2018年7月2日12:59:36
 *
 */
@Service
public class BwGxbAuthRecordServiceImpl extends BaseService<BwGxbAuthRecord, Long> implements BwGxbAuthRecordService {

	@Override
	public Long save(String name,String idCard,String phone,String authItem,String sequenceNo,String returnUrl,String notifyUrl,String timestamp) throws Exception {
		BwGxbAuthRecord record = new BwGxbAuthRecord();
		record.setAuthItem(authItem);
		record.setCreateTime(new Date());
		record.setName(name);
		record.setIdcard(idCard);
		record.setPhone(phone);
		record.setSequenceNo(sequenceNo);
		record.setAuthStatus("-1");
		record.setReturnUrl(returnUrl);
		record.setNotifyUrl(notifyUrl);
		record.setTimestamp(timestamp);
		return insert(record) == 1 ? record.getId() : 0;
	}

	@Override
	public boolean update(Long id,String appId,String authStatus,Date authTime) throws Exception {
		BwGxbAuthRecord record = new BwGxbAuthRecord();
		record.setId(id);
		record.setAppId(appId);
		record.setAuthStatus(authStatus);
		record.setAuthTime(authTime);
		return updateByPrimaryKey(record)==1;
	}

	@Override
	public BwGxbAuthRecord queryBySequenceNo(String sequenceNo) throws Exception {
		String sql = "SELECT * FROM bw_gxb_auth_record WHERE sequence_no = '" + sequenceNo + "' limit 1";
		return sqlMapper.selectOne(sql, BwGxbAuthRecord.class);
	}
	

	@Override
	public boolean update(BwGxbAuthRecord record) throws Exception {
		Assert.notNull(record,"参数bwGxbAuthRecord为空~");
		return updateByPrimaryKeySelective(record)>0;
	}

	@Override
	public boolean updateStatus(AuthInfoDto authInfoDto) {
		String sql = "UPDATE bw_gxb_auth_record SET "
				+ " token = '"+authInfoDto.getToken()+"',"
				+ " app_id = '" + authInfoDto.getAppId() +"',"
				+ " auth_status = '1',"
				+ " auth_time = '" + DateUtil.getDateString(authInfoDto.getAuthTime(),DateUtil.yyyy_MM_dd_HHmmss) + "'"
				+ " WHERE sequence_no = '"+ authInfoDto.getSequenceNo()+"'";
		
		return sqlMapper.update(sql)>0;
	}

}
