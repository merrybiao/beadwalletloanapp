package com.waterelephant.third.utils;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 统一对外接口 - 常量（code0091）
 * 
 * 
 * Module:
 * 
 * ThirdConstant.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ThirdConstant {

	public static ResourceBundle THIRD_CONFIG = null; // 配置常量
	static {
		THIRD_CONFIG = ResourceBundle.getBundle("third");
		if (THIRD_CONFIG == null) {
			throw new IllegalArgumentException("[third.properties] is not found!");
		}
	}

	public static void main(String[] args) {
		Enumeration<String> keys = THIRD_CONFIG.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			System.out.println("key=" + key);
			System.out.println("value=" + THIRD_CONFIG.getString(key));
		}
	}
}
