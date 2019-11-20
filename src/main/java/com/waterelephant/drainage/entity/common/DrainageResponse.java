package com.waterelephant.drainage.entity.common;

import com.alibaba.fastjson.JSONObject;

public class DrainageResponse {

	private String code;
	private String message;
	private JSONObject jsonObject;

	public static final String CODE_SUCCESS = "0000"; // 成功
	public static final String CODE_FAIL = "0001"; // 失败
	public static final String CODE_EXCEPITON = "1000"; // 系统异常
	public static final String CODE_PARAMETER = "2000"; // 缺少参数

	public static final String CODE_RULE_BALCKLIST = "3001"; // 规则 - 黑名单

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

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

}
