/******************************************************************************
* Copyright (C)  2016 Wuhan Water Elephant  Co.Ltd All Rights Reserved. 
* 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
* 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.zhengxin91.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 征信共享数据
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 10:59
 */
@Table(name = "bw_zx_loan_info")
public class ZxLoanInfo implements Serializable {
	private static final long serialVersionUID = 8987699327832151175L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long borrowId;
	private Long orderId;
	private Integer borrowType;
	private Integer borrowState;
	private Integer borrowAmount;
	private Date contractDate;
	private Integer loanPeriod;
	private Integer repayState;
	private Long arrearsAmount;
	private String companyCode;
	private Date createTime;

	public ZxLoanInfo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}

	public Integer getBorrowState() {
		return borrowState;
	}

	public void setBorrowState(Integer borrowState) {
		this.borrowState = borrowState;
	}

	public Integer getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Integer borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Integer getRepayState() {
		return repayState;
	}

	public void setRepayState(Integer repayState) {
		this.repayState = repayState;
	}

	public Long getArrearsAmount() {
		return arrearsAmount;
	}

	public void setArrearsAmount(Long arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}