package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @ClassName: BwOrder
 * @Description: TODO(工单实体类)
 * @author SongYajun
 * @date 2016年8月26日 下午2:02:44
 *
 */
@Table(name = "bw_order")
public class BwOrder implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号
    private String orderNo; // 工单号
    private String mark; // 打分
    private Long creditLimit; // 额度
    private Double borrowAmount; // 借款金额
    private String borrowUse; // 借款用途
    private Integer repayTerm; // 还款期限
    private Double borrowRate; // 借款月利率
    private Double contractRate; // 合同年利率
    private Double contractMonthRate; // 合同月利率
    private Double contractAmount; // 合同金额
    private Long sysUserId; // 工单录入员Id
    private Long orgId; // 机构Id
    private Long deptId; // 部门id
    private Long statusId; // 当前工单状态 1草稿 2初审 3终审 4待签约 5待放款 6结束 7拒绝 8撤回 9还款中 11待生成合同 12待债匹 13逾期 14债匹中
    // private Long auditStatus; //审核状态:针对初审做区分 0：系统审核 1：人工审核
    private Long borrowerId; // 借款人id
    private Integer repayType; // 还款方式 1:先息后本 2:等额本息
    private Integer avoidFineDate; // 免罚息期
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Integer applyPayStatus; // 申请还款状态 0:未申请 1:申请
    private Integer flag = 1; // 删除标志 0:已删除 1:未删除
    private Integer channel; // 删除标志 0:已删除 1:未删除
    private Integer rejectType; // 拒绝类型 0:系统拒绝 1:人工拒绝
    private Integer productId;
    private Integer rank;
    private Date submitTime;// 提交申请时间
    private Double expectMoney;// 用户预期借款金额
    private Integer borrowNumber;// 借款期数
    private Integer productType;// 产品类型(1、单期，2、分期)
    private Integer expectNumber;// 预借期数
    /**
     * 产品期限
     */
    @Transient
    private String term;
    /**
     * 产品期限类型（1：月；2：天）
     */
    @Transient
    private String termType;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public BwOrder() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(Double borrowRate) {
        this.borrowRate = borrowRate;
    }

    public Double getContractRate() {
        return contractRate;
    }

    public void setContractRate(Double contractRate) {
        this.contractRate = contractRate;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    /*
     * public Long getAuditStatus() { return auditStatus; }
     *
     * public void setAuditStatus(Long auditStatus) { this.auditStatus = auditStatus; }
     */

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public Integer getAvoidFineDate() {
        return avoidFineDate;
    }

    public void setAvoidFineDate(Integer avoidFineDate) {
        this.avoidFineDate = avoidFineDate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getApplyPayStatus() {
        return applyPayStatus;
    }

    public void setApplyPayStatus(Integer applyPayStatus) {
        this.applyPayStatus = applyPayStatus;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Double getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(Double borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public Integer getRepayTerm() {
        return repayTerm;
    }

    public void setRepayTerm(Integer repayTerm) {
        this.repayTerm = repayTerm;
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

    public Double getContractMonthRate() {
        return contractMonthRate;
    }

    public void setContractMonthRate(Double contractMonthRate) {
        this.contractMonthRate = contractMonthRate;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getRejectType() {
        return rejectType;
    }

    public void setRejectType(Integer rejectType) {
        this.rejectType = rejectType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Double getExpectMoney() {
        return expectMoney;
    }

    public void setExpectMoney(Double expectMoney) {
        this.expectMoney = expectMoney;
    }

    public Integer getBorrowNumber() {
        return borrowNumber;
    }

    public void setBorrowNumber(Integer borrowNumber) {
        this.borrowNumber = borrowNumber;
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

    /**
     * @return 获取 productType属性值
     */
    public Integer getProductType() {
        return productType;
    }

    /**
     * @param productType 设置 productType 属性值为参数值 productType
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    /**
     * @return 获取 expectNumber属性值
     */
    public Integer getExpectNumber() {
        return expectNumber;
    }

    /**
     * @param expectNumber 设置 expectNumber 属性值为参数值 expectNumber
     */
    public void setExpectNumber(Integer expectNumber) {
        this.expectNumber = expectNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwOrder[");
        builder.append(" [id] = ");
        builder.append(id);
        builder.append(" [orderNo] = ");
        builder.append(orderNo);
        builder.append(" 打分[mark] = ");
        builder.append(mark);
        builder.append(" 额度[creditLimit] = ");
        builder.append(creditLimit);
        builder.append(" 借款金额[borrowAmount] = ");
        builder.append(borrowAmount);
        builder.append(" 借款用途[borrowUse] = ");
        builder.append(borrowUse);
        builder.append(" 借款期限 1:一个月 2:2个月 3:3个月[repayTerm] = ");
        builder.append(repayTerm);
        builder.append(" 还款方式 1:先息后本 2:等额本息[repayType] = ");
        builder.append(repayType);
        builder.append(" 借款月利率[borrowRate] = ");
        builder.append(borrowRate);
        builder.append(" 合同年利率[contractRate] = ");
        builder.append(contractRate);
        builder.append(" 合同月利率[contractMonthRate] = ");
        builder.append(contractMonthRate);
        builder.append(" 合同金额[contractAmount] = ");
        builder.append(contractAmount);
        builder.append(" 工单收件人id[sysUserId] = ");
        builder.append(sysUserId);
        builder.append(" 机构id[orgId] = ");
        builder.append(orgId);
        builder.append(" 部门id[deptId] = ");
        builder.append(deptId);
        builder.append(" 工单状态id[statusId] = ");
        builder.append(statusId);
        builder.append(" 借款人id[borrowerId] = ");
        builder.append(borrowerId);
        builder.append(" 免罚息天数[avoidFineDate] = ");
        builder.append(avoidFineDate);
        builder.append(" [createTime] = ");
        builder.append(createTime);
        builder.append(" [updateTime] = ");
        builder.append(updateTime);
        builder.append(" 删除标志 0:已删除 1:未删除[flag] = ");
        builder.append(flag);
        builder.append(" 申请还款状态 0:未申请 1:申请还款本金 2:申请还款罚息[applyPayStatus] = ");
        builder.append(applyPayStatus);
        builder.append(" 来源渠道 1:安卓 2:ios 3:后台[channel] = ");
        builder.append(channel);
        builder.append(" 拒绝类型 0.系统拒绝 1. 审核拒绝[rejectType] = ");
        builder.append(rejectType);
        builder.append(" 产品类型[productId] = ");
        builder.append(productId);
        builder.append(" 信用等级，1A 2B 3C４D ５Ｅ[rank] = ");
        builder.append(rank);
        builder.append(" 提交申请时间[submitTime] = ");
        builder.append(submitTime);
        builder.append(" 用户预期借款金额[expectMoney] = ");
        builder.append(expectMoney);
        builder.append(" 预借期数[expectNumber] = ");
        builder.append(expectNumber);
        builder.append(" 借款期数[borrowNumber] = ");
        builder.append(borrowNumber);
        builder.append(" 产品类型(1、单期，2、分期)[productType] = ");
        builder.append(productType);
        builder.append("]");
        return builder.toString();
    }

}
