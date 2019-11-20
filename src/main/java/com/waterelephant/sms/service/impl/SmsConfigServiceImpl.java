package com.waterelephant.sms.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.sms.entity.SmsConfig;
import com.waterelephant.sms.service.SmsConfigService;

/**
 * 短信配置文件事物处理
 * @author dengyan
 *
 */
@Service
public class SmsConfigServiceImpl extends BaseService<SmsConfig, Long> implements SmsConfigService {

	@Override
	public SmsConfig querySmsConfig(int id) throws Exception {
		String sql = "select * from sms_config a where a.id=" + id;
		SmsConfig smsConfig = sqlMapper.selectOne(sql, SmsConfig.class);
		return smsConfig;
	}

}
