package com.waterelephant.sms.dahansantong.service;

import java.util.List;
import java.util.Map;

/**
 * 大汉三通短信发送事物接口
 * @author dengyan
 *
 */
public interface DhstSendMessageService {

	/**
	 * 大汉三通短信发送(相同内容批量发送请用这个接口)
	 * @param msgId
	 * @param phones
	 * @param msg
	 * @param sign
	 * @return
	 */
	public boolean sendMsg(String msgId, String phones, String msg, String sign);
	
	/**
	 * 大汉三通批量短信发送（仅当内容不同的时候批量发送调用）
	 * @param mapList
	 * @return
	 */
	public boolean batchSendMsg(List<Map<String, String>> mapList);
}
