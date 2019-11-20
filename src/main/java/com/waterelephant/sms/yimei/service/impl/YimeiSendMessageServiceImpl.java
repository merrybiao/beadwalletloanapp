package com.waterelephant.sms.yimei.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.sms.yimei.SendMsgSDK;
import com.waterelephant.sms.yimei.service.YimeiSendMessageService;
import com.waterelephant.sms.yimei.util.XMLUtil;

/**
 * 短信发送接口事物处理
 * @author dengyan
 *
 */
@Service
public class YimeiSendMessageServiceImpl implements YimeiSendMessageService {


	private Logger logger = LoggerFactory.getLogger(YimeiSendMessageServiceImpl.class);

	/**
	 * 短信发送
	 */
	@Override
	public boolean sendMsg(String seqid, String phones, String msg) {
		int error = -1;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seqid", seqid);
		paramMap.put("phones", phones);
		paramMap.put("msg", msg);
		try {
			Response<Object> response = SendMsgSDK.sendMsg(paramMap);
			if ("200".equals(response.getRequestCode())) { 
				String responseResult = response.getObj().toString();
				String jsonResult = XMLUtil.xmlToJson(responseResult);
				JSONObject json = JSONObject.parseObject(jsonResult);
				JSONObject responseJson = JSONObject.parseObject(json.getString("response"));
				error = responseJson.getInteger("error");
				if (error == 0) {
					logger.info("短信发送成功！");
					return true;
				}
			}
		}catch (Exception e) {
			logger.error("短信发送出现异常，异常信息：" + e.getMessage());
		}
		
		logger.info("短信发送失败！错误码：" + error);
		return false;
	}

	/**
	 * 发送语音验证码
	 */
	@Override
	public boolean sendMsgVoice(String seqid, String phones, String msg) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seqid", seqid);
		paramMap.put("phones", phones);
		paramMap.put("msg", msg);
		try {
			Response<Object> response = SendMsgSDK.sendMsgVoice(paramMap);
			if ("200".equals(response.getRequestCode())) { 
				logger.info("短信发送成功！");
				return true;
			}
		}catch (Exception e) {
			logger.error("短信发送出现异常，异常信息：" + e.getMessage());
		}
		
		logger.info("短信发送失败!" );
		return false;
	}
}
