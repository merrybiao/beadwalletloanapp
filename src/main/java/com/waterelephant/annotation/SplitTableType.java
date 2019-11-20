package com.waterelephant.annotation;

public enum SplitTableType {
	/**
	 * <p>哈希算法分割
	 * <p>根据传入值的hashCode分割表
	 */
	HASH,
	/**
	 * <p>求余数算法分割
	 * <p>根据传入值除以一个常量求余数
	 */
	MOD,
	/**
	 * <p>添加关键字
	 * <p>
	 */
	ADD

}
