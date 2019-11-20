package com.waterelephant.drainage.entity.qihu360;

import java.util.List;

/**
 * 
 * 
 * 
 * Module:用户短信信息
 * 
 * AddInfoMobileMsg.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfoMobileMsg {
	private List<AddInfoMobileMsgMsgdata> msgdata; // 短信信息

	public List<AddInfoMobileMsgMsgdata> getMsgdata() {
		return msgdata;
	}

	public void setMsgdata(List<AddInfoMobileMsgMsgdata> msgdata) {
		this.msgdata = msgdata;
	}

}
