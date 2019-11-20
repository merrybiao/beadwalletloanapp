package com.waterelephant.rongCarrier.email.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_email_trans_detail")
public class TransDetail {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;
	private long borrowerId;
	private long transDate;
	private long postDate;
	private String description;
	private int rmbAmount;
	private int rmbOrgAmount;
	private int currency;
	private String transArea;
	private Date createTime;
	private Date updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(long borrowerId) {
		this.borrowerId = borrowerId;
	}
	public long getTransDate() {
		return transDate;
	}
	public void setTransDate(long transDate) {
		this.transDate = transDate;
	}
	public long getPostDate() {
		return postDate;
	}
	public void setPostDate(long postDate) {
		this.postDate = postDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRmbAmount() {
		return rmbAmount;
	}
	public void setRmbAmount(int rmbAmount) {
		this.rmbAmount = rmbAmount;
	}
	public int getRmbOrgAmount() {
		return rmbOrgAmount;
	}
	public void setRmbOrgAmount(int rmbOrgAmount) {
		this.rmbOrgAmount = rmbOrgAmount;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public String getTransArea() {
		return transArea;
	}
	public void setTransArea(String transArea) {
		this.transArea = transArea;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "TransDetail [id=" + id + ", borrowerId=" + borrowerId
				+ ", transDate=" + transDate + ", postDate=" + postDate
				+ ", description=" + description + ", rmbAmount=" + rmbAmount
				+ ", rmbOrgAmount=" + rmbOrgAmount + ", currency=" + currency
				+ ", transArea=" + transArea + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
}
