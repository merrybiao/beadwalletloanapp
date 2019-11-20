package com.waterelephant.third.jsonentity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 电话信息
 * @author dengyan
 *
 */
public class Tel {

	@JSONField(name = "telData")
	private List<TelData> telDataList;

	public List<TelData> getTelDataList() {
		return telDataList;
	}

	public void setTelDataList(List<TelData> telDataList) {
		this.telDataList = telDataList;
	}
}
