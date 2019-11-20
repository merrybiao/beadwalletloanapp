package com.waterelephant.sms.chuanglan.service;

public interface ClSendMessageService {

	/**
	 * 创蓝发送短信验证码(相同内容批量发送请用这个接口)
	 * @param msgId
	 * @param phones
	 * @param msg
	 * @param sign
	 * @return
	 */
	public boolean sendMsgVoice(String msgId, String phones, String msg);
	
	/**
	 * 创蓝文字短信发送
	 * @param seqid
	 * @param phones
	 * @param msg
	 * @return
	 */
	public boolean sendMsg(String seqid, String phones, String msg);
}
