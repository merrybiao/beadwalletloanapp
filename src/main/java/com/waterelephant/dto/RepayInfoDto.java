package com.waterelephant.dto;

import java.util.Date;

/**
 * 逾期信息DTO 20170111
 * @author duxiaoyong
 *
 */
public class RepayInfoDto {
	
	private Long id;// 还款计划ID
	private Date repayTime;// 还款时间
	private Double realityRepayMoney;// 实际还款金额
	private Integer repayType;// 还款类型
	private Long orderId;// 工单ID
	private String orderNo;// 工单号
	private Date orderTime;// 工单时间
	private Long borrowerId;// 借款人ID
	private String name;// 借款人姓名
	private Integer sex;// 借款人性别。1：男，0：女，其他：未知
	private String phone;// 借款人手机号
	private String idCard;// 借款人身份证号
	private Date regTime;// 用户注册时间
	private String cardNo;// 借款人银行卡号
	private String bankCode;// 借款人开户行标识
	private Integer signStatus;// 银行卡签约状态
	private Double alreadyRepayMoney;//已还金额
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}
	public Double getRealityRepayMoney() {
		return realityRepayMoney;
	}
	public void setRealityRepayMoney(Double realityRepayMoney) {
		this.realityRepayMoney = realityRepayMoney;
	}
	public Integer getRepayType() {
		return repayType;
	}
	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Long getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

	public void setAlreadyRepayMoney(Double alreadyRepayMoney) {
		this.alreadyRepayMoney = alreadyRepayMoney;
	}

	public Double getAlreadyRepayMoney() {
		return alreadyRepayMoney;
	}
}
