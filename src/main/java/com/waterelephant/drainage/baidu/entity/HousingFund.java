package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 公积金信息
 * 
 * @author dengyan
 *
 */
public class HousingFund {

	@JSONField(name = "Info")
	private String info; // 公积金详情

	@JSONField(name = "get_time")
	private String get_time; // 获取时间

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return 获取 get_time属性值
	 */
	public String getGet_time() {
		return get_time;
	}

	/**
	 * @param get_time 设置 get_time 属性值为参数值 get_time
	 */
	public void setGet_time(String get_time) {
		this.get_time = get_time;
	}

}
