package com.waterelephant.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE) 
@Retention(RUNTIME)
public @interface SplitTable {
	
	SplitTableType type() default SplitTableType.HASH;
	
	/**
	 * type为SplitTableType.ADD时，请给key赋值
	 * key值会追加在TableName后面 如table_1
	 * @return
	 */
	String key() default "";
}
