///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai.operator;
//
//import java.util.Date;
//
///**
// * HdBills.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月10日
// * @Description: <账单信息>
// * 
// */
//public class HdBill {
//	private String bill_month;// 账单月，格式：yyyy-MM
//	private Date bill_start_date;// 账期起始日期，格式：yyyy-MM-dd
//	private Date bill_end_date;// 账期结束日期，格式：yyyy-MM-dd
//	private Integer base_fee;// 本机号码套餐及固定费
//	private Integer extra_service_fee;// 增值业务费
//	private Integer voice_fee;// 语音费
//	private Integer sms_fee;// 短彩信费
//	private Integer web_fee;// 网络流量费
//	private Integer extra_fee;// 其它费用
//	private Integer total_fee;// 本月总费用
//	private Integer discount;// 优惠费
//	private Integer extra_discount;// 其它优惠
//	private Integer actual_fee;// 个人实际费用
//	private Integer paid_fee;// 本期已付费用
//	private Integer unpaid_fee;// 本期未付费用
//	private Integer point;// 本期可用积分
//	private Integer last_point;// 上期可用积分
//	private String related_mobiles;// 本手机关联号码, 多个手#x673A;号以逗号分隔
//	private String notes;// 备注
//
//	public String getBill_month() {
//		return bill_month;
//	}
//
//	public void setBill_month(String bill_month) {
//		this.bill_month = bill_month;
//	}
//
//	public Integer getBase_fee() {
//		return base_fee;
//	}
//
//	public void setBase_fee(Integer base_fee) {
//		this.base_fee = base_fee;
//	}
//
//	public Date getBill_start_date() {
//		return bill_start_date;
//	}
//
//	public void setBill_start_date(Date bill_start_date) {
//		this.bill_start_date = bill_start_date;
//	}
//
//	public Date getBill_end_date() {
//		return bill_end_date;
//	}
//
//	public void setBill_end_date(Date bill_end_date) {
//		this.bill_end_date = bill_end_date;
//	}
//
//	public Integer getExtra_service_fee() {
//		return extra_service_fee;
//	}
//
//	public void setExtra_service_fee(Integer extra_service_fee) {
//		this.extra_service_fee = extra_service_fee;
//	}
//
//	public Integer getVoice_fee() {
//		return voice_fee;
//	}
//
//	public void setVoice_fee(Integer voice_fee) {
//		this.voice_fee = voice_fee;
//	}
//
//	public Integer getSms_fee() {
//		return sms_fee;
//	}
//
//	public void setSms_fee(Integer sms_fee) {
//		this.sms_fee = sms_fee;
//	}
//
//	public Integer getWeb_fee() {
//		return web_fee;
//	}
//
//	public void setWeb_fee(Integer web_fee) {
//		this.web_fee = web_fee;
//	}
//
//	public Integer getExtra_fee() {
//		return extra_fee;
//	}
//
//	public void setExtra_fee(Integer extra_fee) {
//		this.extra_fee = extra_fee;
//	}
//
//	public Integer getTotal_fee() {
//		return total_fee;
//	}
//
//	public void setTotal_fee(Integer total_fee) {
//		this.total_fee = total_fee;
//	}
//
//	public Integer getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(Integer discount) {
//		this.discount = discount;
//	}
//
//	public Integer getExtra_discount() {
//		return extra_discount;
//	}
//
//	public void setExtra_discount(Integer extra_discount) {
//		this.extra_discount = extra_discount;
//	}
//
//	public Integer getActual_fee() {
//		return actual_fee;
//	}
//
//	public void setActual_fee(Integer actual_fee) {
//		this.actual_fee = actual_fee;
//	}
//
//	public Integer getPaid_fee() {
//		return paid_fee;
//	}
//
//	public void setPaid_fee(Integer paid_fee) {
//		this.paid_fee = paid_fee;
//	}
//
//	public Integer getUnpaid_fee() {
//		return unpaid_fee;
//	}
//
//	public void setUnpaid_fee(Integer unpaid_fee) {
//		this.unpaid_fee = unpaid_fee;
//	}
//
//	public Integer getPoint() {
//		return point;
//	}
//
//	public void setPoint(Integer point) {
//		this.point = point;
//	}
//
//	public Integer getLast_point() {
//		return last_point;
//	}
//
//	public void setLast_point(Integer last_point) {
//		this.last_point = last_point;
//	}
//
//	public String getRelated_mobiles() {
//		return related_mobiles;
//	}
//
//	public void setRelated_mobiles(String related_mobiles) {
//		this.related_mobiles = related_mobiles;
//	}
//
//	public String getNotes() {
//		return notes;
//	}
//
//	public void setNotes(String notes) {
//		this.notes = notes;
//	}
//
//}
