package com.waterelephant.constants;

/**
 * 工单常量
 * 
 * @Title: OrderStatusConstant.java
 * @Description:
 * @author wangkun
 * @date 2017年4月13日 下午1:36:58
 * @version V1.0
 */
public class OrderStatusConstant {
	// ===================================工单状态===================================================

	/** 工单产品类型(1、单期，2、分期) */
	public static interface ORDER_PRODUCT_TYPE {
		/** 单期 productType=1 */
		String SINGLE = "1";
		/** 分期 productType=2 */
		String MORE = "2";
	}

	// ===================================消息提示===================================================
	/** 订单审核中 */
	public static final String ORDER_CHECK_BEFORE_MSG = "完成资料认证，即可获取额度 !";
	/** 订单审核中 */
	public static final String ORDER_CHECK_LODING_MSG = "额度审核中，请耐心等待！";
	/** 订单拒绝 */
	public static final String ORDER_CHECK_FAILURE_MSG = "因系统评分不足，您的审核未通过！";
	/** 订单撤回 */
	public static final String ORDER_CHECK_BACK_MSG = "您的认证暂未通过，请重新认证！";
	/** 订单成功 */
	public static final String ORDER_CHECK_SUCCEED_MSG = "您的信用良好，更多提额认证，敬请期待！";
	/** 订单完成 */
	public static final String ORDER_CHECK_OVER_MSG = "验证就可以获取额度，更多提额认证，敬请期待！";
	// ===================================工单状态===================================================

}
