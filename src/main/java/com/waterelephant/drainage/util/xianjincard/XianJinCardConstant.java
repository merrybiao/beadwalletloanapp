package com.waterelephant.drainage.util.xianjincard;

import java.util.ResourceBundle;

/**
 * 
 * Module:
 * 
 * XianJinCardConstant.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class XianJinCardConstant {

	public static String UA_REQUEST = "";
	public static String SIGNKEY_REQUEST = "";
	public static String UA_RESPONSE = "";
	public static String SIGNKEY_RESPONSE = "";
	public static String CHANNELID = "";
	public static String BINDCARD_URL = "";

	static {
		ResourceBundle xianJinCard_Bundle = ResourceBundle.getBundle("xianjincard");
		if (xianJinCard_Bundle == null) {
			throw new IllegalArgumentException("[xianjincard.properties] is not found!");
		}
		XianJinCardConstant.UA_REQUEST = xianJinCard_Bundle.getString("UA_REQUEST");
		XianJinCardConstant.SIGNKEY_REQUEST = xianJinCard_Bundle.getString("SIGNKEY_REQUEST");
		XianJinCardConstant.UA_RESPONSE = xianJinCard_Bundle.getString("UA_RESPONSE");
		XianJinCardConstant.SIGNKEY_RESPONSE = xianJinCard_Bundle.getString("SIGNKEY_RESPONSE");
		XianJinCardConstant.CHANNELID = xianJinCard_Bundle.getString("CHANNELID");
		XianJinCardConstant.BINDCARD_URL = xianJinCard_Bundle.getString("BINDCARD_URL");
	}

}
