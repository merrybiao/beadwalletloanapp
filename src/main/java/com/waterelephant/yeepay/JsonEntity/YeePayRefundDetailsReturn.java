package com.waterelephant.yeepay.JsonEntity;

/**
 * 退款对账返回结果
 * @author Administrator
 *
 */
public class YeePayRefundDetailsReturn {
	private String state;//SUCCESS：系统处理正常，业务处理有正常返回结果;FAILURE：系统处理异常
    private String ts;//时间戳
    private String sign;//签名
    private String error;//错误
    private YeePayRefundDetailsResult result;//具体返回结果
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public YeePayRefundDetailsResult getResult() {
		return result;
	}
	public void setResult(YeePayRefundDetailsResult result) {
		this.result = result;
	}
    
}
