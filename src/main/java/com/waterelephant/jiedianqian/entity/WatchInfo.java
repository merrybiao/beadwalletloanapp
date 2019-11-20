package com.waterelephant.jiedianqian.entity;

public class WatchInfo {
	private boolean is_matched;

	private String biz_no;

	private boolean success;

	public void setIs_matched(boolean is_matched) {
		this.is_matched = is_matched;
	}

	public boolean getIs_matched() {
		return this.is_matched;
	}

	public void setBiz_no(String biz_no) {
		this.biz_no = biz_no;
	}

	public String getBiz_no() {
		return this.biz_no;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return this.success;
	}
}
