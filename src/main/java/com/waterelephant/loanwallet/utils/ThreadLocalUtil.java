package com.waterelephant.loanwallet.utils;

public class ThreadLocalUtil {
	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	public static String get() {
		return threadLocal.get();
	}

	public static void set(String value) {
		threadLocal.set(value);
	}
	
	public static void remove(){
		threadLocal.remove();
	}
}