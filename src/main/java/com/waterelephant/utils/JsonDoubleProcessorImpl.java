/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.utils;

import java.text.DecimalFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json转换double保留小数点2位
 * 
 * @description: JsonDoubleProcessorImpl.java
 * @author wangkun
 * @since JDK 1.8
 */
public class JsonDoubleProcessorImpl implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof java.lang.Double[]) {
			DecimalFormat df = new DecimalFormat("#0.00");
			Double[] dates = (Double[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = df.format(dates[i]);
			}
		}
		return obj;
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if (value instanceof java.lang.Double) {
			DecimalFormat df = new DecimalFormat("#0.00");
			Double d = (Double) value;
			String format = df.format(d);

			return format;
		}
		return null == value ? "" : value.toString();
	}

}
