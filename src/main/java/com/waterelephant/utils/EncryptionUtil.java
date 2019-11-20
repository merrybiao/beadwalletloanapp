/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.utils;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * Module:
 * 
 * EncryptionUtil.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class EncryptionUtil {

	private static Logger logger = Logger.getLogger(EncryptionUtil.class);

	private static String key = "meiyouzy";

	private static String keyForH5 = "zyemeiyou";

	public static String getPhone(String phone) {
		try {
			phone = DesHelper.decrypt(phone, key);
			return phone;
		} catch (Exception e) {
			logger.error("电话号码解密错误,原始电话号码：" + phone, e);
			return null;
		}

	}

	public static String getPhoneForH5(String phone) {
		try {
			phone = DESUtilForH5.decryption(phone, keyForH5);
			return phone;
		} catch (Exception e) {
			logger.error("电话号码解密错误,原始电话号码：" + phone, e);
			return null;
		}

	}

}
