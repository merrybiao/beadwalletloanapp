package com.waterelephant.linkface.entity;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * linkface - 记录活体检测次数的实体 code0088
 * 
 * 
 * Module:
 * 
 * LinkfaceVerify.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "bw_linkface_verify")
public class LinkfaceVerify {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private long borrowerId;
	private long orderId;
	private Date createTime;

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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "FaceidVerify [id=" + id + ", borrowerId=" + borrowerId + ", orderId=" + orderId + ", createTime="
				+ createTime + "]";
	}
}
