package com.waterelephant.sms.service;

/**
 * 短信发送通用接口
 * @author dengyan
 *
 */
public interface SendMessageCommonService {

	/**
	 * 文字短信
	 * @param seqid
	 * @param phones
	 * @param msg
	 * @return
	 */
	public boolean commonSendMessage(String phones, String msg);
	
	/**
	 * 语音短信
	 * @param seqid
	 * @param phones
	 * @param msg
	 * @return
	 */
	public boolean commonSendMessageVoice(String phones, String msg);
	
	/**
	 * 大汉三通短信调用
	 * @param phones
	 * @param msg
	 * @return
	 */
	public boolean dhstSendMessage(String phones, String msg);
}
