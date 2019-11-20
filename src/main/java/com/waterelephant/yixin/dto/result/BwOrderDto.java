package com.waterelephant.yixin.dto.result;

/**
 * 订单和逾期记录的dto
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月15日 下午4:52:36
 */
public class BwOrderDto {
	
	//被查询的借款人的姓名
	private String name;
	
	//被查询借款人的身份证号码
	private String certNo;
	
	//借款时间  具体为YYYYMM 申请借款时间，通过的取合同时间，其他取申请时间
	private String loanDate;
	
	//期数1-120  借款的期数
	private Integer periods;
	
	//借款金额
	private String loanAmount;
	
	
	/**审批结果码 
	 * 201 	审核中
	 * 202 	批贷已放款
	 * 203	拒贷
	 * 204	 客户放弃
	 */
	private String approvalStatusCode;
	
	/**
	 * 还款状态码 
	 * 301 正常
	 * 302 逾期
	 * 303 结清
	 * 
	 */
	private String loanStatusCode;
	
	/**
	 * 借款类型码
	 * 21 信用
	 * 22 抵押
	 * 23 担保
	 * 
	 */
	private String loanTypeCode;
	
	
	/**
	 * 逾期金额
	 * (0,1000]
	 * (1000,5000]
	 * (5000,10000]
	 * (10000,20000]
	 * (20000,50000]
	 * (50000,100000]
	 * (100000,+)
	 * 
	 */
	private String overdueAmount;
	
	/**
	 * 逾期情况
	 * 
	 */
	private String overdueStatus;
	
	/**
	 * 历史逾期总次数 
	 * 无逾期可以不填写
	 * 
	 * 
	 */
	private String overdueTotal;
	
	/**
	 * 历史逾期 出现过的M3的次数之和
	 */
	private String overdueM3;
	
	/**
	 * 历史逾期 出现过的M6的次数之和
	 */
	private String overdueM6;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getApprovalStatusCode() {
		return approvalStatusCode;
	}

	public void setApprovalStatusCode(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
	}

	public String getLoanStatusCode() {
		return loanStatusCode;
	}

	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}

	public String getLoanTypeCode() {
		return loanTypeCode;
	}

	public void setLoanTypeCode(String loanTypeCode) {
		this.loanTypeCode = loanTypeCode;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
	}

	public String getOverdueTotal() {
		return overdueTotal;
	}

	public void setOverdueTotal(String overdueTotal) {
		this.overdueTotal = overdueTotal;
	}

	public String getOverdueM3() {
		return overdueM3;
	}

	public void setOverdueM3(String overdueM3) {
		this.overdueM3 = overdueM3;
	}

	public String getOverdueM6() {
		return overdueM6;
	}

	public void setOverdueM6(String overdueM6) {
		this.overdueM6 = overdueM6;
	}

	/**
	 * @param name
	 * @param certNo
	 * @param loanDate
	 * @param periods
	 * @param loanAmount
	 * @param approvalStatusCode
	 * @param loanStatusCode
	 * @param loanTypeCode
	 * @param overdueAmount
	 * @param overdueStatus
	 * @param overdueTotal
	 * @param overdueM3
	 * @param overdueM6
	 */
	public BwOrderDto(String name, String certNo, String loanDate, Integer periods, String loanAmount,
			String approvalStatusCode, String loanStatusCode, String loanTypeCode, String overdueAmount,
			String overdueStatus, String overdueTotal, String overdueM3, String overdueM6) {
		super();
		this.name = name;
		this.certNo = certNo;
		this.loanDate = loanDate;
		this.periods = periods;
		this.loanAmount = loanAmount;
		this.approvalStatusCode = approvalStatusCode;
		this.loanStatusCode = loanStatusCode;
		this.loanTypeCode = loanTypeCode;
		this.overdueAmount = overdueAmount;
		this.overdueStatus = overdueStatus;
		this.overdueTotal = overdueTotal;
		this.overdueM3 = overdueM3;
		this.overdueM6 = overdueM6;
	}

	/**
	 * 
	 */
	public BwOrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BwOrderDto [name=" + name + ", certNo=" + certNo + ", loanDate=" + loanDate + ", periods=" + periods
				+ ", loanAmount=" + loanAmount + ", approvalStatusCode=" + approvalStatusCode + ", loanStatusCode="
				+ loanStatusCode + ", loanTypeCode=" + loanTypeCode + ", overdueAmount=" + overdueAmount
				+ ", overdueStatus=" + overdueStatus + ", overdueTotal=" + overdueTotal + ", overdueM3=" + overdueM3
				+ ", overdueM6=" + overdueM6 + ", getName()=" + getName() + ", getCertNo()=" + getCertNo()
				+ ", getLoanDate()=" + getLoanDate() + ", getPeriods()=" + getPeriods() + ", getLoanAmount()="
				+ getLoanAmount() + ", getApprovalStatusCode()=" + getApprovalStatusCode() + ", getLoanStatusCode()="
				+ getLoanStatusCode() + ", getLoanTypeCode()=" + getLoanTypeCode() + ", getOverdueAmount()="
				+ getOverdueAmount() + ", getOverdueStatus()=" + getOverdueStatus() + ", getOverdueTotal()="
				+ getOverdueTotal() + ", getOverdueM3()=" + getOverdueM3() + ", getOverdueM6()=" + getOverdueM6()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	

}
