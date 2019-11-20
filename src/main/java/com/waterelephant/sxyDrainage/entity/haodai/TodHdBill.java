///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * TodHdBill.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月18日
// * @Description: <账单信息>
// * 
// */
//@Table(name = "bw_haodai_bill")
//public class TodHdBill {
//
//	/**
//	 * 主键id
//	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	private Long orderId;// 我方订单号
//	private Date createTime;
//	private Date updateTime;
//	private String billMonth;// 账单月，格式：yyyy-MM
//	private Date billStartDate;// 账期起始日期，格式：yyyy-MM-dd
//	private Date billEndDate;// 账期结束日期，格式：yyyy-MM-dd
//	private Integer baseFee;// 本机号码套餐及固定费
//	private Integer extraServiceFee;// 增值业务费
//	private Integer voiceFee;// 语音费
//	private Integer smsFee;// 短彩信费
//	private Integer webFee;// 网络流量费
//	private Integer extraFee;// 其它费用
//	private Integer totalFee;// 本月总费用
//	private Integer discount;// 优惠费
//	private Integer extraDiscount;// 其它优惠
//	private Integer actualFee;// 个人实际费用
//	private Integer paidFee;// 本期已付费用
//	private Integer unpaidFee;// 本期未付费用
//	private Integer point;// 本期可用积分
//	private Integer lastPoint;// 上期可用积分
//	private String relatedMobiles;// 本手机关联号码, 多个手#x673A;号以逗号分隔
//	private String notes;// 备注
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(Long orderId) {
//		this.orderId = orderId;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public Integer getBaseFee() {
//		return baseFee;
//	}
//
//	public void setBaseFee(Integer baseFee) {
//		this.baseFee = baseFee;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	public Date getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getBillMonth() {
//		return billMonth;
//	}
//
//	public void setBillMonth(String billMonth) {
//		this.billMonth = billMonth;
//	}
//
//	public Date getBillStartDate() {
//		return billStartDate;
//	}
//
//	public void setBillStartDate(Date billStartDate) {
//		this.billStartDate = billStartDate;
//	}
//
//	public Date getBillEndDate() {
//		return billEndDate;
//	}
//
//	public void setBillEndDate(Date billEndDate) {
//		this.billEndDate = billEndDate;
//	}
//
//	public Integer getExtraServiceFee() {
//		return extraServiceFee;
//	}
//
//	public void setExtraServiceFee(Integer extraServiceFee) {
//		this.extraServiceFee = extraServiceFee;
//	}
//
//	public Integer getVoiceFee() {
//		return voiceFee;
//	}
//
//	public void setVoiceFee(Integer voiceFee) {
//		this.voiceFee = voiceFee;
//	}
//
//	public Integer getSmsFee() {
//		return smsFee;
//	}
//
//	public void setSmsFee(Integer smsFee) {
//		this.smsFee = smsFee;
//	}
//
//	public Integer getWebFee() {
//		return webFee;
//	}
//
//	public void setWebFee(Integer webFee) {
//		this.webFee = webFee;
//	}
//
//	public Integer getExtraFee() {
//		return extraFee;
//	}
//
//	public void setExtraFee(Integer extraFee) {
//		this.extraFee = extraFee;
//	}
//
//	public Integer getTotalFee() {
//		return totalFee;
//	}
//
//	public void setTotalFee(Integer totalFee) {
//		this.totalFee = totalFee;
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
//	public Integer getExtraDiscount() {
//		return extraDiscount;
//	}
//
//	public void setExtraDiscount(Integer extraDiscount) {
//		this.extraDiscount = extraDiscount;
//	}
//
//	public Integer getActualFee() {
//		return actualFee;
//	}
//
//	public void setActualFee(Integer actualFee) {
//		this.actualFee = actualFee;
//	}
//
//	public Integer getPaidFee() {
//		return paidFee;
//	}
//
//	public void setPaidFee(Integer paidFee) {
//		this.paidFee = paidFee;
//	}
//
//	public Integer getUnpaidFee() {
//		return unpaidFee;
//	}
//
//	public void setUnpaidFee(Integer unpaidFee) {
//		this.unpaidFee = unpaidFee;
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
//	public Integer getLastPoint() {
//		return lastPoint;
//	}
//
//	public void setLastPoint(Integer lastPoint) {
//		this.lastPoint = lastPoint;
//	}
//
//	public String getRelatedMobiles() {
//		return relatedMobiles;
//	}
//
//	public void setRelatedMobiles(String relatedMobiles) {
//		this.relatedMobiles = relatedMobiles;
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
