/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.constants;

/**
 * Redis保存数据的key
 * 
 * Module:
 * 
 * RedisKeyConstant.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RedisKeyConstant {
	/**
	 * 富友银行编码
	 */
	public static final String FUIOU_BANK = "fuiou:bank";
	/**
	 * 支付明细，支付成功将支付明细放入redis，回调时保存记录BwPaymentDetail时用到，然后删除redis对应数据
	 */
	public static final String PAYMENT_DETAIL = "payment_detail:repay_id";

	/**
	 * 不允许续贷借款人，保存在redis(预留)
	 */
	public static final String NOT_CAN_XUDAI = "not_can_xudai:borrower_id";

	/**
	 * 分批还款明细
	 */
	public static final String BATCH_REPAYMENT_DETAIL = "batch_repayment_detail:order_id";

	/**
	 * 语音验证次数，每天清除
	 */
	public static final String VOICE_PHONE_COUNT = "voicePhone:count";

	/**
	 * 语音验证多少时间后能发送
	 */
	public static final String VOICE_PHONE_TIME_PRE = "voicePhone:";

	/**
	 * 语音验证码，10分钟失效
	 */
	public static final String VOICE_CODE_PRE = "voiceCode:";

	/**
	 * redis实现同步锁的key，支付时防止并发提交
	 */
	public static final String LOCK_KEY_PAY_PRE = "lock_key_pay:";
	
	/**
	 * redis实现同步锁的key，提交时防止并发提交
	 */
	public static final String LOCK_KEY_PRE = "lock_key:";

	/**
	 * 口袋处理中
	 */
	public static final String KOUDAI_PROCESS = "koudai_process";

	/**
	 * 还款完成修改用户状态
	 */
	public final static String USER_TYPE_CHANGE_KEY = "userService:userTypeChangeKey:";

	public final static String BIND_BANK_UNIQUE_CODE_PRE = "bind_bank_unique_code:";
	/**
	 * 重新绑卡到redis
	 */
	public static String PAY_SIGN = "PAY_SIGN";
}