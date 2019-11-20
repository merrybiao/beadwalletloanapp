package com.waterelephant.authentication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;

/**
 * ApiCheckSign
 * <p> 自定义API验签注解 该注解用在方法上
 * @author dinglinhao
 * @date 2018年9月7日14:05:17
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Configuration
public @interface ApiCheckSign {}