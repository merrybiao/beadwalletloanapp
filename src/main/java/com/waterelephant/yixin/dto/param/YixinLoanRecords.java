/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.dto.param;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Table(name = "yixin_loan_records")
public class YixinLoanRecords {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long mainid;// 关联主键id
	private String orgName;// 提供数据的机构代号
	private String name;// 被查询借款人姓名
	private String certNo;// 被查询借款人身份证号
	private String loanDate;// 借款时间
	private String periods;// 期数，范围（1－120）：借款的期数。通过的，取合同期数；未通过的或审核中的，取申请期数。
	private String loanAmount;// 借款金额（范围）：通过的，取合同金额；未通过的或审核中的，取申请金额
	private String approvalStatusCode;// 审批结果码（201、202、203、204分别表示审核中201，批贷已放款202，拒贷203，客户放弃204.）
	private String loanStatusCode;// 还款状态码（301、302、303分别表示正常301，逾期302，结清303）
	private String loanTypeCode;// 借款类型码（21、22、23分别表示信用21，抵押22，担保23）
	private String overdueAmount;// 逾期金额
	private String overdueStatus;// 逾期情况
	private String overdueTotal;// 历史逾期总次数
	private String overdueM3;// 历史逾期M3+次数
	private String overdueM6;// 历史逾期M6+次数
	private Date createTime;//
	private Date updateTime;//

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainid() {
		return mainid;
	}

	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

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

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
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

}
