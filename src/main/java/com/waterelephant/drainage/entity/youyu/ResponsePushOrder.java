package com.waterelephant.drainage.entity.youyu;
/****
 * 
 * 
 * 
 * Module: 返回的订单推送的实体类
 * 
 * ResponsePushOrder.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponsePushOrder {
	
	public static final int CODE_SUCCESS = 200; // 接口调用成功
	
	 private String order_no;//订单id
	 private int order_status;//	订单状态
	 private String order_detail;//订单描述
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public String getOrder_detail() {
		return order_detail;
	}
	public void setOrder_detail(String order_detail) {
		this.order_detail = order_detail;
	}
	 
	
	 
	 
}
