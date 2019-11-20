package com.waterelephant.dto;

import java.io.Serializable;

/**
 * 微信审核界面显示
 * 
 * @author wrh
 *
 */
@SuppressWarnings("serial")
public class QueryRepayInfo implements Serializable {
	private String borrowAmount;// 审批金额
	private String receivedAmount;// 到账金额
	private String cardNo;// 卡尾号4位数
	private Double loanAmount;// 借款费用（工本费，放款时收取）
	private String checkAmount;// 快速信审费
	private String platformAmount;// 平台使用费
	private String guanliAmount;// 账号管理费
	private String tongdaoAmount;// 代收通道费
	private Double costRate;// 总费率（工本费）
	private Double interestAmount;// 利息，还款时加上
	private Double interestRate;// 利息费率，分期用，7%

	private String useAmount;// 资金使用费
	private Integer repayTerm;// 借款期限(废弃，用term和termType)
	/**
	 * 借款期限，单位由termType决定
	 */
	private String term;
	/**
	 * 借款期限类型，1.月 2.天
	 */
	private String termType;

	public String getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(String checkAmount) {
		this.checkAmount = checkAmount;
	}

	public String getPlatformAmount() {
		return platformAmount;
	}

	public void setPlatformAmount(String platformAmount) {
		this.platformAmount = platformAmount;
	}

	public String getGuanliAmount() {
		return guanliAmount;
	}

	public void setGuanliAmount(String guanliAmount) {
		this.guanliAmount = guanliAmount;
	}

	public String getTongdaoAmount() {
		return tongdaoAmount;
	}

	public void setTongdaoAmount(String tongdaoAmount) {
		this.tongdaoAmount = tongdaoAmount;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Double getCostRate() {
		return costRate;
	}

	public void setCostRate(Double costRate) {
		this.costRate = costRate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public String getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(String useAmount) {
		this.useAmount = useAmount;
	}

	public Integer getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Integer repayTerm) {
		this.repayTerm = repayTerm;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Override
	public String toString() {
		return "QueryRepayInfo [borrowAmount=" + borrowAmount + ", receivedAmount=" + receivedAmount + ", cardNo="
				+ cardNo + ", loanAmount=" + loanAmount + ", checkAmount=" + checkAmount + ", platformAmount="
				+ platformAmount + ", guanliAmount=" + guanliAmount + ", tongdaoAmount=" + tongdaoAmount
				+ ", useAmount=" + useAmount + ", repayTerm=" + repayTerm + ",term=" + term + ",termType=" + termType
				+ "]";
	}
}