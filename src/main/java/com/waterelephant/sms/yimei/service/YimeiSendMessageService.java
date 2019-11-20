package com.waterelephant.sms.yimei.service;
/**
 * 短信发送事物接口层
 * @author dengyan
 *
 */
public interface YimeiSendMessageService {
	
	/**
	 * 发送短信
	 * @param seqid
	 * @param phones
	 * @param msg
	 */
	public boolean sendMsg(String seqid, String phones, String msg);
	
	/**
	 * 发送语音短信
	 * @param seqid
	 * @param phones
	 * @param msg
	 * @return
	 */
	public boolean sendMsgVoice(String seqid, String phones, String msg);
}
