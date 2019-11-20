package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: ZmVerify.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ZmVerify {
	private int zmxy_score;// 芝麻信用分
	// private List<ZmxyWatchInfo> zmxy_watch_info;// 行业关注名单
	private String zmxy_openid;// 芝麻信用openID
	private String watch_matched;// 是否在关注名单 1. 未命中 2. 命中

	public int getZmxy_score() {
		return zmxy_score;
	}

	public void setZmxy_score(int zmxy_score) {
		this.zmxy_score = zmxy_score;
	}

	// public List<ZmxyWatchInfo> getZmxy_watch_info() {
	// return zmxy_watch_info;
	// }
	//
	// public void setZmxy_watch_info(List<ZmxyWatchInfo> zmxy_watch_info) {
	// this.zmxy_watch_info = zmxy_watch_info;
	// }

	public String getZmxy_openid() {
		return zmxy_openid;
	}

	public void setZmxy_openid(String zmxy_openid) {
		this.zmxy_openid = zmxy_openid;
	}

	public String getWatch_matched() {
		return watch_matched;
	}

	public void setWatch_matched(String watch_matched) {
		this.watch_matched = watch_matched;
	}

}