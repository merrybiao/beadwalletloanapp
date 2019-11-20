package com.waterelephant.dto;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付签约DTO
 * 
 * @author maoenqi
 */
public class PaySignDto implements Serializable {
	private static final long serialVersionUID = -1453782010066242406L;
	private Long borrowerId;// 借款人id
	private String phone;// 手机号
	private String name;// 真实姓名
	private String idCard;// 身份证
	private String cardNo;// 银行卡号
	private String bankCode;// 银行编码
	private String bankName;// 开户行名称
	private Boolean bankCardChange;// 是否银行卡重绑卡
	private Boolean sameCardNoValidate = true;// 是否验证重新绑卡卡号相同
	private Integer monthBindCountLimit;// 每月绑卡次数限制
	private String verifyCode;// 验证码
	private String paySignTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Boolean getBankCardChange() {
		return bankCardChange;
	}

	public void setBankCardChange(Boolean bankCardChange) {
		this.bankCardChange = bankCardChange;
	}

	public Boolean getSameCardNoValidate() {
		return sameCardNoValidate;
	}

	public void setSameCardNoValidate(Boolean sameCardNoValidate) {
		this.sameCardNoValidate = sameCardNoValidate;
	}

	public Integer getMonthBindCountLimit() {
		return monthBindCountLimit;
	}

	public void setMonthBindCountLimit(Integer monthBindCountLimit) {
		this.monthBindCountLimit = monthBindCountLimit;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getPaySignTime() {
		return paySignTime;
	}

	public void setPaySignTime(String paySignTime) {
		this.paySignTime = paySignTime;
	}
}
