package com.waterelephant.drainage.util;

import java.text.MessageFormat;
import java.util.Calendar;

/**
 * 引流 - 公共方法
 * 
 * 
 * Module:
 * 
 * CommonUtils.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <引流 - 公共方法>
 */
public class DrainageUtils {

	/**
	 * 公共方法 - 通过身份证号获取年龄
	 * 
	 * @param idCard
	 * @return
	 */
	public static int getAgeByIdCard(String idCard) {
		Calendar c = Calendar.getInstance();
		int age = 0;
		int year = c.get(Calendar.YEAR);
		String ageNum = idCard.substring(6, 10);
		age = year - Integer.parseInt(ageNum);
		return age;
	}

	/**
	 * 公共方法 - 通过身份证获取性别
	 * 
	 * @param idCard
	 * @return
	 */
	public static int getSexByIdCard(String idCard) {
		int sex = 0;
		String sexNum = idCard.substring(idCard.length() - 2, idCard.length() - 1);
		if ((Integer.parseInt(sexNum)) % 2 == 0) {
			sex = 0;
		} else {
			sex = 1;
		}
		return sex;
	}

	/**
	 * 公共方法 - 获取短信发送内容
	 * 
	 * @param pwd
	 * @return
	 */
	public static String getMsg(String pwd) {
		String pattern = "尊敬的用户您好，恭喜您成功注册，您的登录密码为：{0}，您还可以登录 t.cn/R6rhkzU 查看最新的借款进度哦！";
		String msg = MessageFormat.format(pattern, new Object[] { pwd });
		return msg;
	}

	/**
	 * 公共方法 - 判断是否int
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
