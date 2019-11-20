package com.waterelephant.register.entity;

public class JdqResp {
	private String code;
	private String msg;
	private String is_new_user;
	private String apply_url;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getIs_new_user() {
		return is_new_user;
	}
	public void setIs_new_user(String is_new_user) {
		this.is_new_user = is_new_user;
	}
	public String getApply_url() {
		return apply_url;
	}
	public void setApply_url(String apply_url) {
		this.apply_url = apply_url;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JdqResp [");
		if (code != null) {
			builder.append("code=");
			builder.append(code);
			builder.append(", ");
		}
		if (msg != null) {
			builder.append("msg=");
			builder.append(msg);
			builder.append(", ");
		}
		if (is_new_user != null) {
			builder.append("is_new_user=");
			builder.append(is_new_user);
			builder.append(", ");
		}
		if (apply_url != null) {
			builder.append("apply_url=");
			builder.append(apply_url);
		}
		builder.append("]");
		return builder.toString();
	}
}
