package com.waterelephant.sms.service;

import com.waterelephant.sms.entity.SmsConfig;

/**
 * 短信配置事物处理层接口
 * @author dengyan
 *
 */
public interface SmsConfigService {

	public SmsConfig querySmsConfig(int id) throws Exception;
}
