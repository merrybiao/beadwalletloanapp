//package com.waterelephant.sxyDrainage.entity.wacai;
//
///**
// * @author wangfei
// * @version 1.0
// * @date 2018/6/29
// * @Description <TODO>
// * @since JDK 1.8
// */
//public class WaCaiApproval {
//
//    /** 进件成功还未进行审核 */
//    public static final Integer ORDER_SUCCESS = 12000;
//    /** 缺少资料 */
//    public static final Integer ORDER_LACK = 13200;
//    /** 订单直接挂掉 */
//    public static final Integer ORDER_DIE = 12100;
//
//
//    /**
//     * 申请订单号
//     */
//    private String orderId;
//    /**
//     * 用户标识
//     */
//    private String openId;
//    /**
//     * 产品标识
//     */
//    private String productId;
//    /**
//     * 审核结果状态
//     */
//    private Integer approvalResults;
//    /**
//     * 审核结果附注
//     */
//    private String approvalMessage;
//    /**
//     * 审核时间(YYYY-MM-dd HH:mm:ss)
//     */
//    private String approvalTime;
//    /**
//     * 授信额度
//     */
//    private WaCaiApprovalAmount approvalAmount;
//    /**
//     * 已使用的金额 , 单位分
//     */
//    private Long appliedAmount;
//    /**
//     * 剩余可申请的金额 , 单位分
//     */
//    private Long remainAmount;
//    /**
//     * 合作机构支持的期数
//     */
//    private WaCaiTerm term;
//    /**
//     * 固定利率，单位 万分之一
//     */
//    private Integer rate;
//    /**
//     * 利率类型
//     */
//    private Integer rateType;
//
//
//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getOpenId() {
//        return openId;
//    }
//
//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }
//
//    public String getProductId() {
//        return productId;
//    }
//
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//    public Integer getApprovalResults() {
//        return approvalResults;
//    }
//
//    public void setApprovalResults(Integer approvalResults) {
//        this.approvalResults = approvalResults;
//    }
//
//    public String getApprovalMessage() {
//        return approvalMessage;
//    }
//
//    public void setApprovalMessage(String approvalMessage) {
//        this.approvalMessage = approvalMessage;
//    }
//
//    public String getApprovalTime() {
//        return approvalTime;
//    }
//
//    public void setApprovalTime(String approvalTime) {
//        this.approvalTime = approvalTime;
//    }
//
//    public WaCaiApprovalAmount getApprovalAmount() {
//        return approvalAmount;
//    }
//
//    public void setApprovalAmount(WaCaiApprovalAmount approvalAmount) {
//        this.approvalAmount = approvalAmount;
//    }
//
//    public Long getAppliedAmount() {
//        return appliedAmount;
//    }
//
//    public void setAppliedAmount(Long appliedAmount) {
//        this.appliedAmount = appliedAmount;
//    }
//
//    public Long getRemainAmount() {
//        return remainAmount;
//    }
//
//    public void setRemainAmount(Long remainAmount) {
//        this.remainAmount = remainAmount;
//    }
//
//    public WaCaiTerm getTerm() {
//        return term;
//    }
//
//    public void setTerm(WaCaiTerm term) {
//        this.term = term;
//    }
//
//    public Integer getRate() {
//        return rate;
//    }
//
//    public void setRate(Integer rate) {
//        this.rate = rate;
//    }
//
//    public Integer getRateType() {
//        return rateType;
//    }
//
//    public void setRateType(Integer rateType) {
//        this.rateType = rateType;
//    }
//}
