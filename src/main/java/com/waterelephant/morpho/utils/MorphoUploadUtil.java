/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.morpho.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 * Module: 
 * MorphoUploadUtil.java 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class MorphoUploadUtil {
	/**
	 * 日期前推或后推天数,其中day表示天数
	 * 
	 * @param date yyyy-MM-dd
	 * @param day 向前推为负数，向后推为正数
	 * @return
	 */
	public static Date getPreDay(Date dateDate, int day) {
		Calendar gc = Calendar.getInstance();
		gc.setTime(dateDate);
		gc.add(Calendar.DATE, day);
		return gc.getTime();
	}

	/**
	 * 
	 * dateToISO8601
	 * 
	 * @param date
	 * @param pattern 默认为yyyy-MM-dd'T'HH:mm:ss
	 * @return
	 */
	public static String dateToISO8601(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd'T'HH:mm:ss";
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 
	 * 
	 * @param date
	 * @param pattern 默认为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String formatToStr(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 
	 * DX 逾期X天 D5表示逾期5天 M1 逾期1－30天 M2 逾期31-60天 M3 逾期61-90天 M4 逾期91-120天 M5 逾期121-150天 M6 逾期151-180天 MN 逾期181天以上（含）
	 */
	public static String getOverdueStatu(Integer days) {
		if (days == null) {
			return "";
		}
		if (1 <= days && days <= 30) {
			return "M1";
		} else if (31 <= days && days <= 60) {
			return "M2";
		} else if (61 <= days && days <= 90) {
			return "M3";
		} else if (91 <= days && days <= 120) {
			return "M4";
		} else if (121 <= days && days <= 150) {
			return "M5";
		} else if (181 <= days) {
			return "MN";
		}else{
			return "D" + days;
		}

	}

	/**
	 * 得到二个日期间的间隔天数 同一个日期返回0
	 * 
	 * @param date1 较大的日期 格式是 yyyy-MM-dd
	 * @param date2 较小的日期 格式是 yyyy-MM-dd
	 * @return 间隔的天数 date1 - date2
	 */
	public static int getTwoDay(Date date1, Date date2) {
		try {
			Long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
			return day.intValue();
		} catch (Exception e) {
			return 0;
		}
	}


	public static void main(String[] args) {
		System.out.println(dateToISO8601(new Date(), ""));
		System.out.println(formatToStr(new Date(), ""));
		System.out.println(getOverdueStatu(190));
		System.out.println(MorphoUploadUtil.dateToISO8601(MorphoUploadUtil.getPreDay(new Date(), -1),
				""));
		
	}

}
