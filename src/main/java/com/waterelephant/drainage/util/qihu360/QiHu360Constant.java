/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.util.qihu360;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * 
 * Module:奇虎360（code360）
 * 
 * QiHi360Constant.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class QiHu360Constant {
	public static ResourceBundle QIHU360_CONFIG = null; // 配置常量

	public static Map<String, String> KEY_MAP = new HashMap<String, String>();

	static {
		QIHU360_CONFIG = ResourceBundle.getBundle("qihu360");
		if (QIHU360_CONFIG == null) {
			throw new IllegalArgumentException("[qihu360.properties] is not found!");
		}
	}

	public static String get(String key) {
		Enumeration<String> keys = QIHU360_CONFIG.getKeys(); // 获取配置文件中所有的key

		while (keys.hasMoreElements()) {
			String key1 = keys.nextElement();
			KEY_MAP.put(key1, QIHU360_CONFIG.getString(key1)); // 把键和值封装到map中
		}

		return KEY_MAP.get(key);
	}

	public static void main(String[] args) {
		System.out.println(get("channelId"));
	}
}
