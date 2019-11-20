package com.waterelephant.rongCarrier.jd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间类工具
 * @author dengyan
 *
 */
public class DateUtil {

	/**
	 * 将yyyy-MM-dd格式的字符串时间转换成时间类型
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getStringToDate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(dateStr);
	}
}
