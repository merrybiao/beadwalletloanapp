package com.waterelephant.constants;

/**
 * 参数常量类，对应数据库表extra_config中的code字段
 * 
 * 
 * Module:
 * 
 * ParameterConstant.java
 * 
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ParameterConstant {

	/**
	 * 白名单免认证天数
	 */
	public static final String FREE_AUTHENTICATION_DAY = "0000";

	/**
	 * 绑定银行卡次数
	 */
	public static final String BANK_CARD_BINDING_TIME = "0001";
	/**
	 * 借款多少天之后才能续贷
	 */
	public static final String XUDAI_AFTER_BORROW_DAY = "0002";
	/**
	 * 逾期多少天之后不能续贷
	 */
	public static final String UN_XUDAI_AFTER_OVERDUE_DAY = "0003";
	/**
	 * 最少分批还款金额
	 */
	public static final String MIN_BATCH_REPAYMENT_AMOUNT = "0004";

	/**
	 * 查询分期期数
	 */
	public static final String INSTALLMENT_NUMBER = "0005";

	/**
	 * 口袋放款工单是否使用口袋代扣
	 */
	public static final String USE_KOUDAI_WITHHOLD = "0104";
}