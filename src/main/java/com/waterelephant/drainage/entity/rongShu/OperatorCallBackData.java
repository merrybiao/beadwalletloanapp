package com.waterelephant.drainage.entity.rongShu;

/**
 * @author xiaoXingWu
 * @time 2017年8月24日
 * @since JDK1.8
 * @description 运营商返回数据实体类
 */
public class OperatorCallBackData {
	private String code;
	private String message;
	private String failed;
	private String operatorType;
	private String operatorData;
	private String status;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFailed() {
		return failed;
	}
	public void setFailed(String failed) {
		this.failed = failed;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public String getOperatorData() {
		return operatorData;
	}
	public void setOperatorData(String operatorData) {
		this.operatorData = operatorData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
