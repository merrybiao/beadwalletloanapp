/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.constants;

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
public class ActivityConstant {

	/**
	 * 活动类型
	 * 
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
	public static interface ACTIVITY_TYPE {

		/**
		 * 新用户注册
		 */
		String NEW_REGISTER = "1";

		/**
		 * 邀请好友
		 */
		String INVITE_FRIENDS = "2";

	}

	/**
	 * 返回编码
	 * 
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
	public static interface ErrorCode {

		/**
		 * 成功
		 */
		String SUCCESS = "000";

		/**
		 * 失败
		 */
		String FAIL = "111";

		/**
		 * 服务器异常
		 */
		String CATCH = "101";

	}

	/**
	 * 错误信息
	 * 
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
	public static interface ErrorMsg {

		/**
		 * 操作成功
		 */
		String SUCCESS = "操作成功!";

		/**
		 * 活动不存在
		 */
		String ACTIVITY_NOT_EXIST = "活动不存在";

		/**
		 * 系统错误
		 */
		String SYSTEM_ERROR = "系统错误";

		/**
		 * 参数错误
		 */
		String PARAM_ERROR = "参数错误";

		/**
		 * 服务器异常
		 */
		String CATCH_ERROR = "服务器异常";
	}

	public static String MSG_BEFORE = "=======================";

	/**
	 * 
	 * 认证类型，1：运营商 2：个人信息 3：拍照 4：芝麻信用
	 * 
	 * Module:
	 * 
	 * ActivityConstant.java
	 * 
	 * @author 胡林浩
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public static interface ORDER_AUTH_TYPE {

		/**
		 * 运营商认证
		 */
		String AUTH_TYPE_1 = "1";

		/**
		 * 联系信息认证
		 */
		String AUTH_TYPE_2 = "2";
		/**
		 * 拍照认证
		 */
		String AUTH_TYPE_3 = "3";
		/**
		 * 芝麻信用认证
		 */
		String AUTH_TYPE_4 = "4";

	}

	/** 黑名单状态 **/
	public static interface BLACK_SORT {
		Integer SORT_0 = 0; // 普通用户
		Integer SORT_1 = 1; // 黑名单
		Integer SORT_2 = 2; // 灰名单
		Integer SORT_3 = 3; // 白名单
	}

	/** 查询结果显示的条数 */
	public static int RRSULT_TOP = 10;

	/** 添加记录是否有效 */
	public static interface BWORDERSTATUSRECORD_EFFECTIVE {
		Integer EFFECTIVE_0 = 0; // 无效记录
		Integer EFFECTIVE_1 = 1; // 有效记录
	}

	/** 弹窗显示消息 */
	public static interface BWORDERSTATUSRECORD_MSG {
		String MSG_SUCCESS = "恭喜您钞票已经成功到账";// 放款成功弹窗消息
		String MSG_ERROR = "可能因为卡内余额不足或者未开通网银，建议您使用支付宝还款"; // 还款失败弹窗消息
		String MSG_BACK = "您的申请审核未通过";// 撤回弹窗消息
	}

	/** 弹框样式 */
	public static interface BWORDERSTATUSRECORD_DIALOGSTYLE {
		String DIALOGSTYLE_LOANSUCCESS = "1"; // 放款成功弹窗样式
		String DIALOGSTYLE_REPAYSUCCESS = "2";// 还款成功弹窗样式
		String DIALOGSTYLE_REPAYFAIL = "3";// 还款失败弹窗样式
		String DIALOGSTYLE_BACK = "4";// 撤回弹窗样式
		String DIALOGSTYLE_BATCHREPAYSUCCESS = "5";// 分批还款成功弹窗样式
	}

	public final static Integer BLACKTIME = 7;

}
