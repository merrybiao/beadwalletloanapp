package com.waterelephant.third.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具类
 * @author dengyan
 *
 */
public class DateUtils {

	/**
	 * 将时间转换成字符串最小至毫秒
	 * @return
	 */
	public static String getDateHMToString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String sessionId = sdf.format(new Date());
		return sessionId;
	}
	
	/**
	 * 日期转化为字符串
	 * @return
	 */
	public static String getDate_ToString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		return date;
	}
	
	/**
	 * 日期转化为字符串
	 * @return
	 */
	public static String getDate_ToString2(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}
}
