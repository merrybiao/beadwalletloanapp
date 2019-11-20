package com.waterelephant.dto;

/**
 * 一起好放款数据推送DTO 20161215
 * @author duxiaoyong
 *
 */
public class YiQiHaoLoanPushDto {
	
	private String appid;// 应用ID
	private String sign;// 签名
	private String loanSid;// 工单号
	private String repayDate;// 还款日期
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getLoanSid() {
		return loanSid;
	}
	public void setLoanSid(String loanSid) {
		this.loanSid = loanSid;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	

}
