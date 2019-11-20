package com.waterelephant.morpho.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * code:18002 闪蝶征信
 * 批核&贷后共享数据
 * @author Lion
 */
@Table(name="bw_morpho_loan_info")
public class BwMorphoLoanInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;// 主键
	private Long orderId;// 工单ID
	private String veidooType;// 维度类型
	private String reportType;// 报告类型
	private Integer queryCount;// 报告查询次数
	private Integer loanCount;// 贷款
	private Integer loanTenantCount;// 贷款机构数
	private Integer averageLoanGapDays;// 平均申请间隔时长
	private Integer averageTenantGapDays;// 平均换新机构间隔时长
	private Integer maxLoanAmount;// 最大贷款金额
	private Integer maxLoanPeriodDays;// 最大贷款天数
	private Integer averageLoanAmount;// 平均贷款额度
	private Integer maxOverdueDays;// 最大逾期天数
	private Integer overdueLoanCount;// 逾期的贷款笔数
	private Integer overdueTenantCount;// 逾期的贷款机构数
	private Integer overdueFor2TermTenantCount;// 逾期2周期以上的机构数（除小额拖欠）
	private Integer daysFromLastLoan = 0;// 最后一次贷款距今天数 （备注：无记录，则为：-1)
	private Integer monthsFromFirstLoan = 0;// 最早贷款距今月数 (备注：无记录，则为：-1)
	private Integer monthsFromLastOverdue = 0;// 最后一次逾期距今月数  (备注：无记录，则为：-1)
	private Integer monthsForNormalRepay = 0;// 连续正常还款月数 (备注：无记录，则为：-1)
	private Integer remainingAmount = 0; // 未还款总余额 (备注：无记录，则为：-1)
	private Date createTime;// 添加时间
	private String idNo;// 身份证
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getVeidooType() {
		return veidooType;
	}
	public void setVeidooType(String veidooType) {
		this.veidooType = veidooType;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Integer getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}
	public Integer getLoanCount() {
		return loanCount;
	}
	public void setLoanCount(Integer loanCount) {
		this.loanCount = loanCount;
	}
	public Integer getLoanTenantCount() {
		return loanTenantCount;
	}
	public void setLoanTenantCount(Integer loanTenantCount) {
		this.loanTenantCount = loanTenantCount;
	}
	public Integer getAverageLoanGapDays() {
		return averageLoanGapDays;
	}
	public void setAverageLoanGapDays(Integer averageLoanGapDays) {
		this.averageLoanGapDays = averageLoanGapDays;
	}
	public Integer getAverageTenantGapDays() {
		return averageTenantGapDays;
	}
	public void setAverageTenantGapDays(Integer averageTenantGapDays) {
		this.averageTenantGapDays = averageTenantGapDays;
	}
	public Integer getMaxLoanAmount() {
		return maxLoanAmount;
	}
	public void setMaxLoanAmount(Integer maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}
	public Integer getMaxLoanPeriodDays() {
		return maxLoanPeriodDays;
	}
	public void setMaxLoanPeriodDays(Integer maxLoanPeriodDays) {
		this.maxLoanPeriodDays = maxLoanPeriodDays;
	}
	public Integer getAverageLoanAmount() {
		return averageLoanAmount;
	}
	public void setAverageLoanAmount(Integer averageLoanAmount) {
		this.averageLoanAmount = averageLoanAmount;
	}
	public Integer getMaxOverdueDays() {
		return maxOverdueDays;
	}
	public void setMaxOverdueDays(Integer maxOverdueDays) {
		this.maxOverdueDays = maxOverdueDays;
	}
	public Integer getOverdueLoanCount() {
		return overdueLoanCount;
	}
	public void setOverdueLoanCount(Integer overdueLoanCount) {
		this.overdueLoanCount = overdueLoanCount;
	}
	public Integer getOverdueTenantCount() {
		return overdueTenantCount;
	}
	public void setOverdueTenantCount(Integer overdueTenantCount) {
		this.overdueTenantCount = overdueTenantCount;
	}
	public Integer getOverdueFor2TermTenantCount() {
		return overdueFor2TermTenantCount;
	}
	public void setOverdueFor2TermTenantCount(Integer overdueFor2TermTenantCount) {
		this.overdueFor2TermTenantCount = overdueFor2TermTenantCount;
	}
	public Integer getDaysFromLastLoan() {
		return daysFromLastLoan;
	}
	public void setDaysFromLastLoan(Integer daysFromLastLoan) {
		this.daysFromLastLoan = daysFromLastLoan;
	}
	public Integer getMonthsFromFirstLoan() {
		return monthsFromFirstLoan;
	}
	public void setMonthsFromFirstLoan(Integer monthsFromFirstLoan) {
		this.monthsFromFirstLoan = monthsFromFirstLoan;
	}
	public Integer getMonthsFromLastOverdue() {
		return monthsFromLastOverdue;
	}
	public void setMonthsFromLastOverdue(Integer monthsFromLastOverdue) {
		this.monthsFromLastOverdue = monthsFromLastOverdue;
	}
	public Integer getMonthsForNormalRepay() {
		return monthsForNormalRepay;
	}
	public void setMonthsForNormalRepay(Integer monthsForNormalRepay) {
		this.monthsForNormalRepay = monthsForNormalRepay;
	}
	public Integer getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(Integer remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
