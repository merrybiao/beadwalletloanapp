package com.waterelephant.drainage.baidu.util;

import java.util.ResourceBundle;

public class Constant {

	public static String KEY = "";
	public static String BALANCE = "";
	public static String RATE = "";
	public static String REALBALANCE = "";
	public static String MONTHBALANCE = "";
	public static String PASSWORD = "";
	public static String CARDURL = "";
	public static String CHANNELCODE = "";
	public static String CARDTOURL = "";
	public static String FILEURL = "";
	public static String CHANNEL = "";
	public static String MERCHANTCODE = "";
	public static String TPCODE = "";
	// public static String REPAYDATE = "";

	static {
		ResourceBundle baidu_config = ResourceBundle.getBundle("baidu");
		Constant.KEY = baidu_config.getString("key");
		Constant.BALANCE = baidu_config.getString("balance");
		Constant.RATE = baidu_config.getString("rate");
		Constant.REALBALANCE = baidu_config.getString("real_balance");
		Constant.MONTHBALANCE = baidu_config.getString("month_balance");
		Constant.PASSWORD = baidu_config.getString("password");
		Constant.CARDURL = baidu_config.getString("card_url");
		Constant.CHANNELCODE = baidu_config.getString("channel_code");
		Constant.CARDTOURL = baidu_config.getString("card_to_url");
		Constant.FILEURL = baidu_config.getString("file_url");
		Constant.CHANNEL = baidu_config.getString("channel");
		Constant.MERCHANTCODE = baidu_config.getString("merchant_code");
		Constant.TPCODE = baidu_config.getString("tp_code");
		// Constant.REPAYDATE = baidu_config.getString("repay_data");
	}
}
