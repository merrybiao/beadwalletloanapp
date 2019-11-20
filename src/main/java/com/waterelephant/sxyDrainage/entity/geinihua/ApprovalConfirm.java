//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import javax.validation.constraints.NotNull;
//
///**
// * 审批
// * @author xanthuim
// */
//
//public class ApprovalConfirm {
//    /**
//     * 订单编号 string 否 订单编号
//     */
//    private String orderNo;
//    /**
//     * 申请金额 string 否 申请的借款金额，单位元
//     */
//    @NotNull(message = "loanAmount不能为空")
//    private Double loanAmount;
//    /**
//     * 申请期限 int 否 借款期限
//     */
//    @NotNull(message = "loanTerm不能为空")
//    private Integer loanTerm;
//    /**
//     * 申请单位 int 否 期限的单位：1天 2月，默认为1天
//     */
//    private String loanUnit;
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public Double getLoanAmount() {
//        return loanAmount;
//    }
//
//    public void setLoanAmount(Double loanAmount) {
//        this.loanAmount = loanAmount;
//    }
//
//    public Integer getLoanTerm() {
//        return loanTerm;
//    }
//
//    public void setLoanTerm(Integer loanTerm) {
//        this.loanTerm = loanTerm;
//    }
//
//    public String getLoanUnit() {
//        return loanUnit;
//    }
//
//    public void setLoanUnit(String loanUnit) {
//        this.loanUnit = loanUnit;
//    }
//}
