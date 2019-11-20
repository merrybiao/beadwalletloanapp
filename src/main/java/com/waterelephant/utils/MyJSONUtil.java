package com.waterelephant.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * @Title: MyJSONUtil.java
 * @Description:
 * @author wangkun
 * @date 2017年4月19日 下午6:10:06
 * @version V1.0
 */
public class MyJSONUtil {
	public static JsonConfig jsonConfig = new JsonConfig();

	/**
	 * 实体对象转json
	 * 
	 * @param object 实体对象
	 * @param excludes 排除字段数组
	 * @param dateToString 是否将date转换成yyyy-MM-dd类型
	 * @return
	 */
	public static JSONObject objectToJson(Object object, String[] excludes, boolean dateToString) {
		jsonConfig.setExcludes(excludes);
		if (dateToString) {
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDataProcessorImpl(MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));
		}
		jsonConfig.registerJsonValueProcessor(java.lang.Double.class, new JsonDoubleProcessorImpl());
		JSONObject fromObject = JSONObject.fromObject(object, jsonConfig);
		return fromObject;
	}

	/**
	 * 集合转JSON
	 * 
	 * @param list 集合
	 * @param excludes 排除字段数组
	 * @param dateToString 是否将date转换成yyyy-MM-dd类型
	 * @return
	 */
	public static JSONArray listToJson(Object list, String[] excludes, boolean dateToString) {
		jsonConfig.setExcludes(excludes);
		if (dateToString) {
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDataProcessorImpl(MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));
		}
		jsonConfig.registerJsonValueProcessor(java.lang.Double.class, new JsonDoubleProcessorImpl());
		JSONArray fromObject = JSONArray.fromObject(list, jsonConfig);
		return fromObject;
	}
}
