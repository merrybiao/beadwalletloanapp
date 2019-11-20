//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import java.math.BigDecimal;
//
///**
// * 审批
// *
// * @author xanthuim
// */
//
//public class ApprovalResult {
//    /**
//     * 订单编号 string 否 订单编号
//     */
//    private String orderNo;
//    /**
//     * 审批结果 int 否 1：通过，2：未通过
//     */
//    private Integer conclusion;
//    /**
//     * 结果说明 string 否 成功或者失败都要返回，失败需要返回详细的原因说明。
//     */
//    private String instructions;
//    /**
//     * 审批时间 Long 否 成功或者失败都要返回 毫秒时间戳
//     */
//    private Long approvalTime;
//    /**
//     * 最大申请金额 BigDecimal 是 最大申请金额
//     */
//    private BigDecimal maxAmount;
//    /**
//     * 最小申请金额 BigDecimal 是 最小申请金额
//     */
//    private BigDecimal minAmount;
//    /**
//     * 颗粒度 int 是 一般来说是100
//     */
//    private String rangeAmount;
//    /**
//     * 借款期限单位 int 是 1：日、2：月
//     */
//    private Integer loanUnit;
//    /**
//     * 借款可选期限 string 是 返回可选期限的字符串，比如：3,6,9,12，如果是月的话，表示可选3，6、9、12月四种
//     */
//    private String loanTerm;
//    /**
//     * 费率 string 是 费率，同期数返回，每期对应
//     */
//    private String loanRate;
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public Integer getConclusion() {
//        return conclusion;
//    }
//
//    public void setConclusion(Integer conclusion) {
//        this.conclusion = conclusion;
//    }
//
//    public String getInstructions() {
//        return instructions;
//    }
//
//    public void setInstructions(String instructions) {
//        this.instructions = instructions;
//    }
//
//    public Long getApprovalTime() {
//        return approvalTime;
//    }
//
//    public void setApprovalTime(Long approvalTime) {
//        this.approvalTime = approvalTime;
//    }
//
//    public BigDecimal getMaxAmount() {
//        return maxAmount;
//    }
//
//    public void setMaxAmount(BigDecimal maxAmount) {
//        this.maxAmount = maxAmount;
//    }
//
//    public BigDecimal getMinAmount() {
//        return minAmount;
//    }
//
//    public void setMinAmount(BigDecimal minAmount) {
//        this.minAmount = minAmount;
//    }
//
//    public String getRangeAmount() {
//        return rangeAmount;
//    }
//
//    public void setRangeAmount(String rangeAmount) {
//        this.rangeAmount = rangeAmount;
//    }
//
//    public Integer getLoanUnit() {
//        return loanUnit;
//    }
//
//    public void setLoanUnit(Integer loanUnit) {
//        this.loanUnit = loanUnit;
//    }
//
//    public String getLoanTerm() {
//        return loanTerm;
//    }
//
//    public void setLoanTerm(String loanTerm) {
//        this.loanTerm = loanTerm;
//    }
//
//    public String getLoanRate() {
//        return loanRate;
//    }
//
//    public void setLoanRate(String loanRate) {
//        this.loanRate = loanRate;
//    }
//}
