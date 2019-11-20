package com.waterelephant.operatorData.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class OperatorNetDataDto {
	
	@JSONField(ordinal=1)
	private String month;
	
	@JSONField(name="total_size",ordinal=2)
	private String totalSize;
	
	@JSONField(ordinal=3)
	private List<?> items;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	
}
