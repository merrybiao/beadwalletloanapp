//package com.waterelephant.sxyDrainage.entity.lakala;
//
//public class LklCheckUserInfo {
//	
//	private int isStock; // 是否存量用户 （0：否，1：是）
//	private int isCanLoan; // 是否可以借款（0：否，1：是）
//	private int isBlackList; // 是否命中黑名单（0：否，1：是）
//	private int periodUnit; // 周期单位（1：天，2：月）
//	private int rejectReason; // 拒绝原因（0-不拒绝;1-黑名单;2-在贷;3-短时拒绝;4-其他）
//    private String remark;	//	备注	其他拒绝原因等
//    private String amountLimit;//金额额度 jsonObject
//    private String amountOption;//放款金额周期jsonArray(step为0时表示可选任意金额)
//    private String bankName;	//已绑储蓄银行名称	对于已绑卡用户需回传
//    private String bankCardNum;	//	已绑储蓄银行卡号	对于已绑卡用户需回传
//    private String phone;		//储蓄银行预留手机号	对于已绑卡用户需回传
//    private String creditBankName;	//	已绑信用卡名称	对于已绑卡用户需回传
//    private String creditCardNum;	//	已绑信用卡号	对于已绑卡用户需回传
//    private String creditPhone;	//	信用卡预留手机号	对于已绑卡用户需回传
//	public int getIsStock() {
//		return isStock;
//	}
//	public void setIsStock(int isStock) {
//		this.isStock = isStock;
//	}
//	public int getIsCanLoan() {
//		return isCanLoan;
//	}
//	public void setIsCanLoan(int isCanLoan) {
//		this.isCanLoan = isCanLoan;
//	}
//	public int getIsBlackList() {
//		return isBlackList;
//	}
//	public void setIsBlackList(int isBlackList) {
//		this.isBlackList = isBlackList;
//	}
//	public int getPeriodUnit() {
//		return periodUnit;
//	}
//	public void setPeriodUnit(int periodUnit) {
//		this.periodUnit = periodUnit;
//	}
//	public int getRejectReason() {
//		return rejectReason;
//	}
//	public void setRejectReason(int rejectReason) {
//		this.rejectReason = rejectReason;
//	}
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//	public String getAmountLimit() {
//		return amountLimit;
//	}
//	public void setAmountLimit(String amountLimit) {
//		this.amountLimit = amountLimit;
//	}
//	public String getAmountOption() {
//		return amountOption;
//	}
//	public void setAmountOption(String amountOption) {
//		this.amountOption = amountOption;
//	}
//	public String getBankName() {
//		return bankName;
//	}
//	public void setBankName(String bankName) {
//		this.bankName = bankName;
//	}
//	public String getBankCardNum() {
//		return bankCardNum;
//	}
//	public void setBankCardNum(String bankCardNum) {
//		this.bankCardNum = bankCardNum;
//	}
//	public String getPhone() {
//		return phone;
//	}
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	public String getCreditBankName() {
//		return creditBankName;
//	}
//	public void setCreditBankName(String creditBankName) {
//		this.creditBankName = creditBankName;
//	}
//	public String getCreditCardNum() {
//		return creditCardNum;
//	}
//	public void setCreditCardNum(String creditCardNum) {
//		this.creditCardNum = creditCardNum;
//	}
//	public String getCreditPhone() {
//		return creditPhone;
//	}
//	public void setCreditPhone(String creditPhone) {
//		this.creditPhone = creditPhone;
//	}
//    
//    
//}
