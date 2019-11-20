package com.waterelephant.drainage.util.youyu;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/*****
 * 有鱼常量
 * 
 * 
 * @author FanShenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description:
 */
public class YouyuConstant {
	public static ResourceBundle YOUYU_CONFIG = null; // 配置常量
	public static final Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();// 订单状态映射
	// 订单状态映射
	public static Map<String, String> ORDER_STATUS_MAP_L = new HashMap<String, String>();

	// youyu.properties文件常量
	public static String YOUYUREQUESTYOUYUURL = "";
	public static String SHUIXIANG_PUBLICKEY = "";
	public static String SHUIXIANG_PRIVATEKEY = "";
	public static String YOUYU_PUBLICKEY = "";
	public static String YOUYUAPP_ID = "";
	public static String YOUYU_CHANNEL = "";
	public static String YOUYU_CREDIT_CARD_URL = "";
	public static String YOUYU_CHANNEL_CODE = "";
	public static String YOUYU_SKIPURL = "";
	public static String YOUYU_THIRDPAY_PAYMENT = "";
	public static String YOUYU_CREDIT_SYNTONYURL = "";
	
	

	static {
		YOUYU_CONFIG = ResourceBundle.getBundle("youyu");
		if (YOUYU_CONFIG == null) {
			throw new IllegalArgumentException("[youyu.properties] is not found!");
		}
		// 常量
		YOUYUREQUESTYOUYUURL=YOUYU_CONFIG.getString("youyu.requestYouYuUrl");//测试路径
		SHUIXIANG_PUBLICKEY=YOUYU_CONFIG.getString("shuixiang_publickey");//水象公钥
		SHUIXIANG_PRIVATEKEY=YOUYU_CONFIG.getString("shuixiang_privatekey");//水象私钥
		YOUYU_PUBLICKEY=YOUYU_CONFIG.getString("youyu_publickey");//有鱼公钥
		YOUYUAPP_ID=YOUYU_CONFIG.getString("youyu.app_id");//有鱼提供appid
		YOUYU_CHANNEL=YOUYU_CONFIG.getString("youyu_channel");
		YOUYU_CREDIT_CARD_URL=YOUYU_CONFIG.getString("youyu_credit_card_url");
		YOUYU_CHANNEL_CODE=YOUYU_CONFIG.getString("youyu_channel_code");
		YOUYU_SKIPURL=YOUYU_CONFIG.getString("youyu_skipurl");//跳转绑卡成功或失败页面地址
		YOUYU_THIRDPAY_PAYMENT=YOUYU_CONFIG.getString("youyu_thirdpay_payment");//还款地址
		YOUYU_CREDIT_SYNTONYURL=YOUYU_CONFIG.getString("youyu_credit_syntonyUrl");//跳转我的绑卡页面（自己写的控制器）
		/**
		 * 10 新订单 20 不符合条件 30 用户放弃 40 申请进件 50 待补充材料 60 审批中 70 审批拒绝 80 审批通过 81
		 * 审批通过，待签约 82 已拒绝签约 83 订单状态失效 90 放款中 100 放款成功 110 正常还款中 120 逾期 130
		 * 贷款无逾期结清 140 贷款逾期结清 150 第三方还款进行中
		 */
		ORDER_STATUS_MAP.put("10", "新订单");
		ORDER_STATUS_MAP.put("20", "不符合条件");
		ORDER_STATUS_MAP.put("30", "用户放弃 ");
		ORDER_STATUS_MAP.put("40", "申请进件");
		ORDER_STATUS_MAP.put("50", "待补充材料");
		ORDER_STATUS_MAP.put("60", "审批中");
		ORDER_STATUS_MAP.put("70", "审批拒绝 ");
		ORDER_STATUS_MAP.put("80", " 审批通过");
		ORDER_STATUS_MAP.put("81", "审批通过，待签约 ");
		ORDER_STATUS_MAP.put("82", "已拒绝签约");
		ORDER_STATUS_MAP.put("83", "订单状态失效");
		ORDER_STATUS_MAP.put("90", " 放款中");
		ORDER_STATUS_MAP.put("100", "放款成功");
		ORDER_STATUS_MAP.put("110", "正常还款中");
		ORDER_STATUS_MAP.put("120", "逾期");
		ORDER_STATUS_MAP.put("130", "贷款结清");
		ORDER_STATUS_MAP.put("140", "贷款逾期结清");
		ORDER_STATUS_MAP.put("150", "第三方还款进行中");
		
		
		/**
		 * 1草稿 2初审 3终审 4待签约 5待放款 6结束 7拒绝 8撤回 9还款中 11待生成合同 12待债匹 13逾期 14债匹中
		 */
		ORDER_STATUS_MAP_L.put("1", "10");//草稿
		ORDER_STATUS_MAP_L.put("2", "60");//初审
		ORDER_STATUS_MAP_L.put("3", "60");//终审
		ORDER_STATUS_MAP_L.put("4", "81");//待签约
		ORDER_STATUS_MAP_L.put("6", "130");//结束
		ORDER_STATUS_MAP_L.put("7", "70");//拒绝
		ORDER_STATUS_MAP_L.put("8", "70");//撤回
		ORDER_STATUS_MAP_L.put("9", "110");//还款中
		ORDER_STATUS_MAP_L.put("13", "120");//逾期
		
	}

}
