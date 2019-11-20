package com.waterelephant.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author hui wang
 *
 */
public class MyDateUtils {
	/** 年-月-日 时:分:秒 显示格式yyyy-MM-dd HH:mm:ss */
	// 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
	public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 年-月-日 显示格式 yyyy-MM-dd */
	public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";
	/** 年-月-日 显示格式yyyy年HH月mm日 */
	public static String DATE_TO_STRING_CHINA_PATTERN = "yyyy年MM月dd日";

	/**
	 * 功能：增加小时。
	 * 
	 * @param date 参照时间
	 * @param days 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addHours(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * Date类型转为指定格式的String类型
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String DateToString(Date source, String pattern) {
		DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(source);
	}

	/**
	 * String 转 Date 2015年3月25日上午11:27:14 auther:shijing
	 * 
	 * @param str 日期字符串
	 * @param format 转换格式
	 * @return Date
	 */
	public static Date stringToDate(String str, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * 功能：增加天数。
	 * 
	 * @param date 参照时间
	 * @param days 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
		return new Date(c.getTimeInMillis());
	}

	
	
	/**
	 * 功能：增加月数。
	 * 
	 * @param date 参照时间
	 * @param months 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public static Date addMonths(Date date, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 两个日期相隔月份
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getMonthSpace(Date beginDate, Date endDate) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(beginDate);
		c2.setTime(endDate);
		if (c2.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) {
			result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		} else if (c2.get(Calendar.YEAR) > c1.get(Calendar.YEAR)) {
			result = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + c2.get(Calendar.MONTH)
					- c1.get(Calendar.MONTH);
		}
		return Math.abs(result);
	}

	/**
	 * 两个日期相隔天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDaySpace(Date beginDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(beginDate);
		c2.setTime(endDate);
		// 设置时间为0时
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		// 得到两个日期相差的天数
		long days = (c2.getTime().getTime() - c1.getTime().getTime()) / (1000 * 60 * 60 * 24);
		return Integer.parseInt(String.valueOf(days));
	}

	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}

	/**
	 * 两个日期相隔小时
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getHourSpace(Date beginDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(beginDate);
		c2.setTime(endDate);
		// 设置时间为0时
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		// 得到两个日期相差的天数
		long days = (c2.getTime().getTime() - c1.getTime().getTime()) / (1000 * 60 * 60);
		return Integer.parseInt(String.valueOf(days));
	}

	public static int monthSpace(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return (day2 - day1) / 30;

	}

	/**
	 * 某月第一天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDay(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(calendar.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
		return str.toString();

	}

	/**
	 * 某月最后一天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDay(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(date));
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String s = df.format(calendar.getTime());
		StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		return str.toString();

	}

	public static int getDateHours(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH");
		String hour = df.format(date);
		return Integer.parseInt(hour);
	}

	/**
	 * 获得某月的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static void main(String[] args) throws ParseException {
		// Date stringToDate = stringToDate("2017-04-30", MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);
		// int i = getDaySpace(new Date(), stringToDate);
		// System.out.println(i);
//		int daysOfMonth = getDaysOfMonth(new Date());
		 Date date = addDays(new Date(), 18);
		
		System.out.println(date);
	}
}
