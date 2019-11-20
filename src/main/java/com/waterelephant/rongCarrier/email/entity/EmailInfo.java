package com.waterelephant.rongCarrier.email.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_email_info")
public class EmailInfo {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;
	private long borrowerId;
	private String mail;
	private String bankName;
	private String cardNo;
	private String name;
	private int lastBalance;
	private int lastPayment;
	private long statementStartDate;
	private long statementEndDate;
	private long paymentCurDate;
	private long paymentDueDate;
	private int creditLimit;
	private int totalPoints;
	private int newBalance;
	private int minPayment;
	private int newCharges;
	private int adjustment;
	private int interest;
	private int lastPoints;
	private int earnedPoints;
	private int adjustedPoints;
	private int availableBalanceUsd;
	private int availableBalance;
	private int cashAdvanceLimitUsd;
	private int creditLimitUsd;
	private int cashAdvanceLimit;
	private int minPaymentUsd;
	private int newBalanceUsd;
	private int redeemedPoints;
	private String senderEmail;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLastBalance() {
		return lastBalance;
	}
	public void setLastBalance(int lastBalance) {
		this.lastBalance = lastBalance;
	}
	public int getLastPayment() {
		return lastPayment;
	}
	public void setLastPayment(int lastPayment) {
		this.lastPayment = lastPayment;
	}
	public long getStatementStartDate() {
		return statementStartDate;
	}
	public void setStatementStartDate(long statementStartDate) {
		this.statementStartDate = statementStartDate;
	}
	public long getStatementEndDate() {
		return statementEndDate;
	}
	public void setStatementEndDate(long statementEndDate) {
		this.statementEndDate = statementEndDate;
	}
	public long getPaymentCurDate() {
		return paymentCurDate;
	}
	public void setPaymentCurDate(long paymentCurDate) {
		this.paymentCurDate = paymentCurDate;
	}
	public long getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(long paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public int getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	public int getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(int newBalance) {
		this.newBalance = newBalance;
	}
	public int getMinPayment() {
		return minPayment;
	}
	public void setMinPayment(int minPayment) {
		this.minPayment = minPayment;
	}
	public int getNewCharges() {
		return newCharges;
	}
	public void setNewCharges(int newCharges) {
		this.newCharges = newCharges;
	}
	public int getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(int adjustment) {
		this.adjustment = adjustment;
	}
	public int getInterest() {
		return interest;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	public int getLastPoints() {
		return lastPoints;
	}
	public void setLastPoints(int lastPoints) {
		this.lastPoints = lastPoints;
	}
	public int getEarnedPoints() {
		return earnedPoints;
	}
	public void setEarnedPoints(int earnedPoints) {
		this.earnedPoints = earnedPoints;
	}
	public int getAdjustedPoints() {
		return adjustedPoints;
	}
	public void setAdjustedPoints(int adjustedPoints) {
		this.adjustedPoints = adjustedPoints;
	}
	public int getAvailableBalanceUsd() {
		return availableBalanceUsd;
	}
	public void setAvailableBalanceUsd(int availableBalanceUsd) {
		this.availableBalanceUsd = availableBalanceUsd;
	}
	public int getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(int availableBalance) {
		this.availableBalance = availableBalance;
	}
	public int getCashAdvanceLimitUsd() {
		return cashAdvanceLimitUsd;
	}
	public void setCashAdvanceLimitUsd(int cashAdvanceLimitUsd) {
		this.cashAdvanceLimitUsd = cashAdvanceLimitUsd;
	}
	public int getCreditLimitUsd() {
		return creditLimitUsd;
	}
	public void setCreditLimitUsd(int creditLimitUsd) {
		this.creditLimitUsd = creditLimitUsd;
	}
	public int getCashAdvanceLimit() {
		return cashAdvanceLimit;
	}
	public void setCashAdvanceLimit(int cashAdvanceLimit) {
		this.cashAdvanceLimit = cashAdvanceLimit;
	}
	public int getMinPaymentUsd() {
		return minPaymentUsd;
	}
	public void setMinPaymentUsd(int minPaymentUsd) {
		this.minPaymentUsd = minPaymentUsd;
	}
	public int getNewBalanceUsd() {
		return newBalanceUsd;
	}
	public void setNewBalanceUsd(int newBalanceUsd) {
		this.newBalanceUsd = newBalanceUsd;
	}
	public int getRedeemedPoints() {
		return redeemedPoints;
	}
	public void setRedeemedPoints(int redeemedPoints) {
		this.redeemedPoints = redeemedPoints;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
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
		return "EmailInfo [id=" + id + ", borrowerId=" + borrowerId + ", mail="
				+ mail + ", bankName=" + bankName + ", cardNo=" + cardNo
				+ ", name=" + name + ", lastBalance=" + lastBalance
				+ ", lastPayment=" + lastPayment + ", statementStartDate="
				+ statementStartDate + ", statementEndDate=" + statementEndDate
				+ ", paymentCurDate=" + paymentCurDate + ", paymentDueDate="
				+ paymentDueDate + ", creditLimit=" + creditLimit
				+ ", totalPoints=" + totalPoints + ", newBalance=" + newBalance
				+ ", minPayment=" + minPayment + ", newCharges=" + newCharges
				+ ", adjustment=" + adjustment + ", interest=" + interest
				+ ", lastPoints=" + lastPoints + ", earnedPoints="
				+ earnedPoints + ", adjustedPoints=" + adjustedPoints
				+ ", availableBalanceUsd=" + availableBalanceUsd
				+ ", availableBalance=" + availableBalance
				+ ", cashAdvanceLimitUsd=" + cashAdvanceLimitUsd
				+ ", creditLimitUsd=" + creditLimitUsd + ", cashAdvanceLimit="
				+ cashAdvanceLimit + ", minPaymentUsd=" + minPaymentUsd
				+ ", newBalanceUsd=" + newBalanceUsd + ", redeemedPoints="
				+ redeemedPoints + ", senderEmail=" + senderEmail
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
}
