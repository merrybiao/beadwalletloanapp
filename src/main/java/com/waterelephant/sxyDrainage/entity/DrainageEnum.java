///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity;
//
///**
// * Module: DrainageEnum.java
// *
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public enum DrainageEnum {
//	/** 成功 */
//	CODE_SUCCESS("0000", "成功"),
//	/** 失败 */
//	CODE_FAIL("0001", "失败"),
//	/** 失败，失败原因 */
//	CODE_FAIL_MSG("0001", "失败，%s"),
//	/** 系统异常 */
//	CODE_EXCEPTION("1000", "系统异常"),
//	/** 参数为空 */
//	CODE_PARAMSETER("1001", "参数为空"),
//	/** 参数为空:%s */
//	CODE_PARAMETER("1002", "参数为空:%s"),
//	/** 本地订单不存在 */
//	CODE_ORDER_NOT_FOUND("1003", "本地订单不存在"),
//	/** 三方订单不存在 */
//	CODE_THIRD_ORDER_NOT_FOUND("1004", "三方订单不存在"),
//	/** 借款人不存在 */
//	CODE_USER_NOT_FOUND("1005", "借款人不存在"),
//	/** 还款计划不存在 */
//	CODE_REPAYPLAN_NOT_FOUND("1006", "还款计划不存在"),
//	/** 逾期记录不存在 */
//	CODE_OVERDUE_NOT_FOUND("1007", "逾期记录不存在"),
//
//	/** 命中黑名单规则 */
//	CODE_RULE_BLACKLIST("2001", "命中黑名单规则 "),
//	/** 存在进行中的订单 */
//	CODE_RULE_ISPROCESSING("2002", "存在进行中的订单 "),
//	/** 命中被拒规则 */
//	CODE_RULE_ISREJECT("2003", "命中被拒规则 "),
//	/** 用户年龄超限 */
//	CODE_RULE_AGE_UNMATCH("2004", "用户年龄超限"),
//	/** 手机号码为170号段 */
//	CODE_PHONE_NOT_EXCEPTION("2005", "手机为虚拟运营商号段"),
//
//	/** 该用户已经绑定其他银行卡 */
//	CODE_BIND_CARD("3001", "该用户已经绑定银行卡:%s"),
//
//	/** 订单状态错误 */
//	CODE_ORDERSTATUS_ERR("4001", "订单状态错误"),
//	/** 该用户未绑定银行卡 */
//	CODE_NO_BINDCARD("4002", "该用户还未绑卡，请先绑卡"),
//
//	/** 本次借款金额%s大于最大借款金额%s */
//	CODE_LOANAMOUNT_GT("5001", "本次借款金额%s大于最大借款金额%s"),
//	/** 本次借款金额%s小于最小借款金额%s */
//	CODE_LOANAMOUNT_LT("5002", "本次借款金额%s小于最小借款金额%s"),
//
//	;
//	private String code;
//	private String msg;
//
//	private DrainageEnum(String code, String msg) {
//		this.code = code;
//		this.msg = msg;
//	}
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
//}
