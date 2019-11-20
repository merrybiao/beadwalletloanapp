package com.waterelephant.sms.chuanglan.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.sms.chuanglan.ClSendMsgSDK;
import com.waterelephant.sms.chuanglan.service.ClSendMessageService;

/**
 * 创蓝发送短信验证码事物处理
 * @author dengyan
 *
 */
@Service
public class ClSendMessageServiceImpl implements ClSendMessageService {

	private Logger logger = LoggerFactory.getLogger(ClSendMessageServiceImpl.class);
	/**
	 * 创蓝发送短信验证码
	 */
	@Override
	public boolean sendMsgVoice(String msgId, String phones, String msg) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seqid", msgId);
		paramMap.put("phones", phones);
		paramMap.put("msg", msg);
		try {
			Response<Object> response = ClSendMsgSDK.sendMsgVoice(paramMap);
			if("200".equals(response.getRequestCode())) {
				logger.info("短信发送成功！");
				return true;
			}else {
				logger.info("短信发送失败,失败原因:" + response.getRequestMsg());
				return false;
			}
		}catch (Exception e) {
			logger.error("短信发送异常：", e);
		}
		return false;
	}
	
	/**
	 * 创蓝文字短信发送
	 */
	@Override
	public boolean sendMsg(String seqid, String phones, String msg) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seqid", seqid);
		paramMap.put("phones", phones);
		paramMap.put("msg", msg);
		try {
			Response<Object> response = ClSendMsgSDK.sendMsg(paramMap);
			if("200".equals(response.getRequestCode())) {
				logger.info("短信发送成功！");
				return true;
			}else {
				logger.info("短信发送失败,失败原因:" + response.getRequestMsg());
				return false;
			}
		}catch (Exception e) {
			logger.error("短信发送异常：", e);
		}
		return false;
	}
	
}
