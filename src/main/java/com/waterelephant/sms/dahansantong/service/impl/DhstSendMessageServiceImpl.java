package com.waterelephant.sms.dahansantong.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.beadwallet.service.sms.dahansantong.DhstSendMsgSDK;
import com.beadwallet.service.entity.response.Response;
import com.waterelephant.sms.dahansantong.service.DhstSendMessageService;

/**
 * 大汉三通短信事物处理
 * @author dengyan
 *
 */
@Service
public class DhstSendMessageServiceImpl implements DhstSendMessageService {

	private Logger logger = LoggerFactory.getLogger(DhstSendMessageServiceImpl.class);
	/**
	 * 短信发送
	 */
	@Override
	public boolean sendMsg(String msgId, String phones, String msg, String sign) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("msgId", msgId);
		paramMap.put("phones", phones);
		paramMap.put("msg", msg);
		paramMap.put("sign", sign);
		try {
			Response<Object> response = DhstSendMsgSDK.sendMsg(paramMap);
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
	 * 批量短信发送（仅当内容不同的时候调用）
	 */
	@Override
	public boolean batchSendMsg(List<Map<String, String>> mapList) {
		try {
			Response<Object> response = DhstSendMsgSDK.batchSendMsg(mapList);
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
