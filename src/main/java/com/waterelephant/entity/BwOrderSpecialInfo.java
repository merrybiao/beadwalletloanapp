package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_order_special_info")
public class BwOrderSpecialInfo implements Serializable {

	private static final long serialVersionUID = -1934141390173985793L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date ratifiedDate;//核定日志
	private String customerName;//客户姓名
	private String customerPhone;//客户电话
	private String orderNo;//工单编号
	private String seatName;//坐席姓名
	private String teamName;//主管姓名
	private String remark;//原因反馈
	private String checkType;//质检核定类型
	private String checkResult;//质检核实结果
	private String financeResult;//财务合适结果
	private String caseAture;//案件性质
	private Date createTime;//创建时间
	private Long createUserid;//创建人Id
	private Date updateTime;//更新时间
	private Long updateUserid;//更新人id
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getRatifiedDate() {
		return ratifiedDate;
	}
	public void setRatifiedDate(Date ratifiedDate) {
		this.ratifiedDate = ratifiedDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getFinanceResult() {
		return financeResult;
	}
	public void setFinanceResult(String financeResult) {
		this.financeResult = financeResult;
	}
	public String getCaseAture() {
		return caseAture;
	}
	public void setCaseAture(String caseAture) {
		this.caseAture = caseAture;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(Long createUserid) {
		this.createUserid = createUserid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUserid() {
		return updateUserid;
	}
	public void setUpdateUserid(Long updateUserid) {
		this.updateUserid = updateUserid;
	}
}
