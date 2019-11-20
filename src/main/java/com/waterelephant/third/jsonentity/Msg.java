package com.waterelephant.third.jsonentity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 短信信息
 * @author dengyan
 *
 */
public class Msg {

	@JSONField(name = "msgdata")
	private List<MsgData> msgDataList;

	public List<MsgData> getMsgDataList() {
		return msgDataList;
	}

	public void setMsgDataList(List<MsgData> msgDataList) {
		this.msgDataList = msgDataList;
	}

}
