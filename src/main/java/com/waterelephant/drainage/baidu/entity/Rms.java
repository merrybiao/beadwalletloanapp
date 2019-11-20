package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 风控信息
 * 
 * @author dengyan
 *
 */
public class Rms {

	@JSONField(name = "level")
	private String level; // 黑名单等级

	@JSONField(name = "black_reason")
	private String black_reason; // 黑名单原因

	@JSONField(name = "safe_items")
	private Object safe_items; // 安全因子

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return 获取 black_reason属性值
	 */
	public String getBlack_reason() {
		return black_reason;
	}

	/**
	 * @param black_reason 设置 black_reason 属性值为参数值 black_reason
	 */
	public void setBlack_reason(String black_reason) {
		this.black_reason = black_reason;
	}

	/**
	 * @return 获取 safe_items属性值
	 */
	public Object getSafe_items() {
		return safe_items;
	}

	/**
	 * @param safe_items 设置 safe_items 属性值为参数值 safe_items
	 */
	public void setSafe_items(Object safe_items) {
		this.safe_items = safe_items;
	}

}
