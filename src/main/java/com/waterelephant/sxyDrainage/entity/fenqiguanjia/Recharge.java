//
//package com.waterelephant.sxyDrainage.entity.fenqiguanjia;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//public class Recharge {
//
//	private String fee;
//	private String recharge_time;
//	private String recharge_way;
//
//	public void setFee(String fee) {
//		this.fee = fee;
//	}
//
//	public String getFee() {
//		return fee;
//	}
//
//	public String getRecharge_time() {
//		return recharge_time;
//	}
//
//	public void setRecharge_time(String recharge_time) {
//		this.recharge_time = recharge_time;
//	}
//
//	public void setRecharge_way(String recharge_way) {
//		this.recharge_way = recharge_way;
//	}
//
//	public String getRecharge_way() {
//		return recharge_way;
//	}
//
//	public static void main(String[] args) {
//		String data = "{\"recharge_time\":\"2018-02-14 23:59:59\"}";
//		Recharge parseObject = JSONObject.parseObject(data, Recharge.class);
//		System.out.println(JSON.toJSONString(parseObject));
//
//	}
//
//}
