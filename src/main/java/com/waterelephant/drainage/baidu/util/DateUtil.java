package com.waterelephant.drainage.baidu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间类工具
 * @author dengyan
 *
 */
public class DateUtil {

	public static String getDateTimeHH(Date date) {
		String dateTime = String.valueOf(date.getTime());
		return dateTime;
	}
	
	
	/**
	 * 功能：增加小时。
	 * 
	 * @param date 参照时间
	 * @param days 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public static Date addHours(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
		return new Date(c.getTimeInMillis());
	}
	
	public static void main(String[] args) {
		Date date = addHours(new Date(), 2);
		if (System.currentTimeMillis() < Long.parseLong(getDateTimeHH(date))) {
			System.out.println("true");
		}else {
			System.out.println("false");
		}
		System.out.println(getDateTimeHH(date));
	}
}
