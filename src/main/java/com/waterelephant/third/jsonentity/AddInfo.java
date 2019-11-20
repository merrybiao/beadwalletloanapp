package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 运营商信息
 * Created by dengyan on 2017/7/20.
 */
public class AddInfo {


	@JSONField(name = "mobile")
	private Mobile mobile;
	
	@JSONField(name = "zhima")
	private Zhima zhima;

	public Mobile getMobile() {
		return mobile;
	}

	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}

	public Zhima getZhima() {
		return zhima;
	}

	public void setZhima(Zhima zhima) {
		this.zhima = zhima;
	}
}
