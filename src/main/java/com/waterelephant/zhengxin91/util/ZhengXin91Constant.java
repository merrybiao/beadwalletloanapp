package com.waterelephant.zhengxin91.util;

import java.util.ResourceBundle;

/**
 * 
 * 
 * 
 * Module:
 * 
 * ZhengXin91Constant.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: 91征信 - 常量
 */
public class ZhengXin91Constant {
	public static String ZHENGXIN_CUST_NO = "";
	public static String ZHENGXIN_SIGN = "";
	public static String ZHENGXIN_URL = "";

	static {
		ResourceBundle config_zhengxin = ResourceBundle.getBundle("zhengxin91");
		ZhengXin91Constant.ZHENGXIN_CUST_NO = config_zhengxin.getString("cust_no");
		ZhengXin91Constant.ZHENGXIN_SIGN = config_zhengxin.getString("sign");
		ZhengXin91Constant.ZHENGXIN_URL = config_zhengxin.getString("url");
	}
}
