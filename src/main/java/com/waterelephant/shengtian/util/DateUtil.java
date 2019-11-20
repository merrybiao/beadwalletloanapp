/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.shengtian.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.StringUtil;

/**
 * 
 * 
 * Module:
 * 
 * DateUtil.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class DateUtil {

	private static final Logger log = Logger.getLogger(DateUtil.class);

	public static final String yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMdd_HHmmss = "yyyyMMdd hh:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String HH_mm_ss = "HHmmss";
	public static final String yyMMdd_HHmmss = "yy/MM/dd HH:mm:ss";
	public static final String yyyy_MM_dd_HHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String YMD = "yyyy年MM月dd日";

	/**
	 * 获取指定格式的当前时间字符串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 获取指定时间,指定格式的日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 获取指定时间,指定格式的日期字符串
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getDateString(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.format(sdf.parse(dateStr));
		} catch (ParseException e) {
			log.error("日期解析错误,日期:" + dateStr);
			return "";
		}
	}

	/**
	 * 获取当前时间的TimeStamp
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取指定时间,指定格式的日期字符串
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			log.error("日期解析错误,日期:" + dateStr);
		}
		return date;
	}

	/**
	 * 获取两个日期之间的所有日期
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> getBetweenDates(String start, String end) {
		List<String> result = new ArrayList<String>();
		Calendar tempStart = Calendar.getInstance();
		Calendar tempEnd = Calendar.getInstance();
		if (StringUtil.isEmpty(start) && StringUtil.isEmpty(end)) {
			tempEnd.setTime(new Date());
			tempStart.setTime(new Date());
			tempStart.add(Calendar.MONTH, -1);
		} else if (!StringUtil.isEmpty(start) && StringUtil.isEmpty(end)) {
			tempStart.setTime(stringToDate(start, yyyy_MM_dd));
			tempEnd.setTime(new Date());
			tempEnd.add(Calendar.DAY_OF_YEAR, -1);
		} else if (StringUtil.isEmpty(start) && !StringUtil.isEmpty(end)) {
			tempEnd.setTime(stringToDate(end, yyyy_MM_dd));
			tempStart.setTime(stringToDate(end, yyyy_MM_dd));
			tempStart.add(Calendar.MONTH, -1);
		} else {
			tempStart.setTime(stringToDate(start, yyyy_MM_dd));
			tempEnd.setTime(stringToDate(end, yyyy_MM_dd));
		}
		tempEnd.add(Calendar.DAY_OF_YEAR, 1);
		while (tempStart.before(tempEnd)) {
			result.add(getDateString(tempStart.getTime(), yyyy_MM_dd));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	public static void main(String[] args) {

		System.out.println(
				intervalDay(CommUtils.convertStringToDate("2017-06-24 12:12:12", "yyyy-MM-dd HH:mm:ss"), new Date()));
	}

	public static boolean isBeforeTime(Date before, Date afrer) {
		int compareToBefore = before.compareTo(afrer);
		if (compareToBefore < 0) {
			return true;
		}
		return false;
	}

	public static int intervalMinute(Date start, Date end) {
		Long l = end.getTime() - start.getTime();
		Long m = l / 1000 / 60;
		return m.intValue();
	}

	public static int intervalDay(Date start, Date end) {
		Long l = end.getTime() - start.getTime();
		Long m = l / 1000 / 60 / 60 / 24;
		return m.intValue();
	}

	/**
	 * 获取回话ID - 用户写日志
	 * 
	 * @author liuDaodao
	 * @return
	 */
	public static String getSessionId() {
		String sessionId = "";
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmsss");
			sessionId = simpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionId;
	}

}
