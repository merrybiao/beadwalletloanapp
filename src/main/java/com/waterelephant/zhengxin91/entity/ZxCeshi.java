package com.waterelephant.zhengxin91.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/18 11:40
 */
@Table(name = "bw_zx_ceshi")

public class ZxCeshi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long borrowId;
	private Long orderId;
	private String name;
	private String phone;
	private String cardId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Override public String toString() {
		return "ZxCeshi{" + "id=" + id + ", borrowId=" + borrowId + ", orderId=" + orderId + ", name='" + name + '\''
				+ ", phone='" + phone + '\'' + ", card_id='" + cardId + '\'' + '}';
	}
}
