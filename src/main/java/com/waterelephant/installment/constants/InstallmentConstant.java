/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.constants;

/**
 * 活动常量
 * 
 * Module:
 * 
 * ActivityConstant.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class InstallmentConstant {

	/**
	 * 产品类型
	 * 
	 * 
	 * Module:
	 * 
	 * InstallmentConstant.java
	 * 
	 * @author 程盼
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface PRODUCT_TYPE {

		/**
		 * 单期
		 */
		Integer SINGLE = 1;

		/**
		 * 多期
		 */
		Integer MULTI = 2;

	}

	/**
	 * 单期借款提示信息
	 * 
	 * 
	 * Module:
	 * 
	 * InstallmentConstant.java
	 * 
	 * @author 程盼
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface SINGLE_TEXT_MSG {

		/**
		 * 单期默认金额 500 - 5000
		 */
		String SINGLE_AUTO_MONEY = "500元-5000元";

		/**
		 * 多期 默认金额 2000元-10000
		 */
		String MULTI_AUTO_MONEY = "2000元-10000元";

		/**
		 * 临时拒绝
		 */
		String TEMP_REJECT = "信用评估完成";

		/**
		 * 永久拒绝
		 */
		String PERM_REJECT = "信用评估完成";

		/**
		 * 审核中
		 */
		String IN_AUDIT = "您的申请正在审核，请等待";

		/**
		 * 逾期中
		 */
		String OVERDUE = "逾期中";

		/**
		 * 结束
		 */
		String END = "500元-5000元";

		/**
		 * 单期前缀
		 */
		String SINGLE_LEFT = "待还款金额：";
		/**
		 * 多期前缀
		 */
		String MULTI_LEFT = "待还款：";
		/**
		 * 多期前缀
		 */
		String MULTI_LEFT2 = "最后还款日：";
		/**
		 * 撤回
		 */
		String BACK = "您的申请审核未通过";
		/**
		 * 放款中
		 */
		String CREDIT = "借款金额：";
		/**
		 * 待签约
		 */
		String GETMONEY = "借款金额：";
	}

	/**
	 * 单期借款操作提示信息
	 * 
	 * 
	 * Module:
	 * 
	 * InstallmentConstant.java
	 * 
	 * @author 程盼
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface SINGLE_OPERATION_MSG {

		/**
		 * 临时拒绝
		 */
		String TEMP_REJECT = "因系统评分不足，您的申请未通过";

		/**
		 * 永久拒绝
		 */
		String PERM_REJECT = "";
		/**
		 * 审核额中
		 */
		String IN_AUDIT = "您已有订单在审核，请稍后再试";

		/**
		 * 正在处理中
		 */
		String IN_PROCESSING = "您已有订单在处理中，请结束订单后再申请";

	}

	/**
	 * 分期借款提示信息
	 * 
	 * 
	 * Module:
	 * 
	 * InstallmentConstant.java
	 * 
	 * @author 程盼
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface MULTI_TEXT_MSG {

		/**
		 * 无工单
		 */
		String NO_ORDER = "2000元-10000元";

		/**
		 * 草稿
		 */
		String IN_DRAFT = "2000元-10000元";

		/**
		 * 临时拒绝
		 */
		String TEMP_REJECT = "因系统评分不足，您的申请暂时未通过";

		/**
		 * 永久拒绝
		 */
		String PERM_REJECT = "因系统评分不足，您的申请未通过";

		/**
		 * 审核中
		 */
		String IN_AUDIT = "您的申请正在审核，请等待";

		/**
		 * 逾期中
		 */
		String OVERDUE = "逾期中";

		/**
		 * 结束
		 */
		String END = "2000元-10000元";

		/**
		 * 前缀
		 */
		String LEFT = "借款金额：";

	}

	/**
	 * 分期借款操作提示信息
	 * 
	 * 
	 * Module:
	 * 
	 * InstallmentConstant.java
	 * 
	 * @author 程盼
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface MULTI_OPERATION_MSG {

		/**
		 * 临时拒绝
		 */
		String TEMP_REJECT = "因系统评分不足，您的申请未通过，请1个月后重新申请";

		/**
		 * 审核额中
		 */
		String IN_AUDIT = "您已有订单在审核，请稍后再试";

	}

	/**
	 * 不能操作提示信息
	 * 
	 * 
	 * Module:
	 * 
	 * InstallmentConstant.java
	 * 
	 * @author 程盼
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface OPERATION_MSG {

	}

	public static interface BORROWER_LIMIT {
		String DINGLE_MIN = "500"; // 单期最低
		String DINGLE_MAX = "5000"; // 单期最高
		String MULTI_MIN = "2000"; // 分期最低
		String MULTI_MAX = "10000"; // 分期最高
	}

}
