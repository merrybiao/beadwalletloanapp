package com.waterelephant.utils;

import java.util.Set;

/**
 * 权限自定义标签
 * 
 * @author hui wang
 */
public class RuleContains {
	public static boolean contains(Set<String> rules, String ruleCode) {
		return rules.contains(ruleCode);
	}
}
