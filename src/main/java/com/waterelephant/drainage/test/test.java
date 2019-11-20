package com.waterelephant.drainage.test;

/**
 * @author xiaoXingWu
 * @time 2017年11月17日
 * @since JDK1.8
 * @description
 */
public class test {

	public static void main(String[] args) {
		String phone = "158****6395";
		phone = phone.substring(7, phone.length());
		System.out.println(phone);

		// loan + loan * zjw
		double loan = 50;
		Double zjw = 2.0;
		double a = loan + loan * zjw;
		System.out.println(a);
	}

}
