package com.waterelephant.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 一起好交互工具类
 * @author duxiaoyong
 *
 */
public class YiQiHaoUtil {
	
	/**
	 * 将富友银行编码转化为一起好银行编码
	 * @param bankcode 富友银行编码
	 * @return 一起好银行编码，若富友银行编码错误，返回null
	 */
	public static String convertFuiouBankcodeToYiqihao(String bankcode){
		String res = null;
		switch (bankcode) {
			case "0102":
				res = "icbc";
				break;
			case "0103":
				res = "abc";
				break;
			case "0104":
				res = "boc";
				break;
			case "0105":
				res = "ccb";
				break;
			case "0301":
				res = "comm";
				break;
			case "0302":
				res = "citic";
				break;
			case "0303":
				res = "ceb";
				break;
			case "0304":
				res = "hxb";
				break;
			case "0305":
				res = "cmbc";
				break;
			case "0306":
				res = "gdb";
				break;
			case "0307":
				res = "pab";
				break;
			case "0308":
				res = "cmb";
				break;
			case "0309":
				res = "cib";
				break;
			case "0310":
				res = "spdb";
				break;
			case "0403":
				res = "postgc";
				break;
		}
		return res;
	}

	/**
	 * 对象转为String表达形式
	 * @param obj
	 * @return
	 */
	public static String convertObjToStr(Object obj) {
		String res = null;
		if (obj != null){
			res = obj.toString();
		}
		return res;
	}
	
	/**
	 * 检测一个对象是否有空的属性值
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static boolean hasNullProperty(Object obj) throws Exception {
		boolean res = false;
		
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor property : propertyDescriptors) {
			String propertyName = property.getName();
			if (!"class".equals(propertyName)) {
				Method me = property.getReadMethod();
				Object value = me.invoke(obj);
				if (value == null) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

}
