package com.waterelephant.sms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间类工具
 * @author dengyan
 *
 */
public class DateUtil {

	/**
	 * 将时间转化为字符串
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sf.format(new Date());
		
		return date;
	}
}
