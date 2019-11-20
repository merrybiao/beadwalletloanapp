package com.waterelephant.capital.yiqile;

/**
 * 服务器响应app结果对象
 * 
 * @author lujilong
 *
 * @param <T>
 */
public class ResponseResult {

	/**
	 * 服务器响应编号
	 */
	private Integer rtnCode;
	/**
	 * 服务器响应消息
	 */
	private String rtnMsg;

	public Integer getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(Integer rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}
}
