package com.waterelephant.register.entity;

public class RegisterResp<T> {
	private String code;
	private String message;
//	private String msg;
//	private String sign;
	private T result;
	 
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
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	} 
//	public String getSign() {
//		return sign;
//	}
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
//	
//	public String getMsg() {
//		return msg;
//	}
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterResp [");
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
//		if (msg != null) {
//			builder.append("msg=");
//			builder.append(msg);
//			builder.append(", ");
//		}
//		if (sign != null) {
//			builder.append("sign=");
//			builder.append(sign);
//			builder.append(", ");
//		}
		if (result != null) {
			builder.append("result=");
			builder.append(result);
		}
		builder.append("]");
		return builder.toString();
	}
}