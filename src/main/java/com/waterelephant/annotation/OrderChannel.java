package com.waterelephant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderChannel {
	/**
	 * 渠道号
	 * @return
	 */
	String channel() ;
	/**
	 * 渠道名称
	 * @return
	 */
	String name() default "";

}
