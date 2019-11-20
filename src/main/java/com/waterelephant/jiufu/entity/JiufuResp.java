package com.waterelephant.jiufu.entity;

/**
 * @author 张诚
 * @since JDK 1.8
 * @version 1.0
 * @description: <联合注册响应信息>
 * @param <JiufuRespData>
 */
public class JiufuResp<T> {
	private String code;		//响应状态码 200：成功 ，101：失败
	private String message;		//200情况下返回成功，101返回失败原因
	private String status;		//1代表成功，0代表失败
	private T data;				//请求的详细参数体
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("JiufuResp [");
		if (code != null) {
			builder.append("code=");
			builder.append(code);
			builder.append(", ");
		}
		if (message != null) {
			builder.append("message=");
			builder.append(message);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (data != null) {
			builder.append("data=");
			builder.append(data);
		}
		builder.append("]");
		
		return builder.toString();
		
	}
	
}
