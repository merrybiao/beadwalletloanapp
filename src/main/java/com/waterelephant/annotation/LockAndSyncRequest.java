/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.waterelephant.constants.RedisKeyConstant;

/**
 * 
 * Module:
 * 
 * LockAndSyncRequest.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 防表单重复提交锁定注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LockAndSyncRequest {
	/**
	 * 默认锁定
	 * 
	 * @return
	 */
	boolean locked() default true;

	/**
	 * redis存入键前缀
	 * 
	 * @return
	 */
	String redisKeyPre() default RedisKeyConstant.LOCK_KEY_PRE;
	/**
	 * redis存入键后缀，从请求中获取value，若没有，则默认取session的hashcode
	 * 
	 * @return
	 */
	String redisKeyAfterByRequestName() default "";

	/**
	 * 存入redis时间，单位：秒
	 * 
	 * @return
	 */
	int seconds() default 20;
}