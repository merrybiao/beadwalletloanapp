package com.waterelephant.base;

import java.util.List;

/**
 * 网络响应数据模型
 * @author 刘自宾
 *
 */
public class WebRespModel {
	
	/**
	 * 成功标记
	 */
	public static final Integer RESULT_CODE_SUCC = 0;
	
	/**
	 * 失败标记
	 */
	public static final Integer RESULT_CODE_FAIL = -1;
	
	
	/**
	 * 结果码
	 */
	private Integer resultCode = 0;
	
	/**
	
	/**
	 * 响应消息
	 */
	private String message = null;
	
	/**
	 * 提示错误信息
	 */
	private String error = null;
	
	/**
	 * 响应数据
	 */
	private Object data = null;
	/**
	 * 响应数据
	 */
	private List list = null;
	/**
	 *总行数
	 */
	private String total = null;
	/**
	 *总行数
	 */
	private String pageCurrent = null;

	/**
	 * 获取结果码
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * 设置结果码
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}



	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(String pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	

}
