///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai;
//
///**
// * 
// * HaoDaiResponse.java
// * 
// * @author 王亚楠
// * @since JDK 1.8
// * @version 1.0
// * @description: <好贷网响应实体类>
// */
//public class HaoDaiResponse {
//
//	public static final String CODE_SUCCESS = "200";
//	public static final String CODE_AUTH_FAIL = "401";
//	public static final String CODE_FAIL = "500";// 异常
//	public static final String CODE_FAIL_CONFIRM = "400";// 签约接口异常
//
//	private String code;// 状态码
//	private String msg;// 错误信息
//	private Object data;// 银行卡信息
//
//	private Integer userstatus;// 用户状态
//	// 1表示新用户可申请或老用户复贷时走全流程，2表示老用户复贷时走简捷流程，3表示老用户不可复待，4表示黑名单用户，5表示其他
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//
//	public Object getData() {
//		return data;
//	}
//
//	public void setData(Object data) {
//		this.data = data;
//	}
//
//	public Integer getUserstatus() {
//		return userstatus;
//	}
//
//	public void setUserstatus(Integer userstatus) {
//		this.userstatus = userstatus;
//	}
//
//}
