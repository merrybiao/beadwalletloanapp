//package com.waterelephant.sxyDrainage.entity.shandiandai;
//
//import java.util.List;
//
///**
// * 订单信息查询响应实体类
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/1
// * @since JDK 1.8
// */
//public class SddRepayDetailResp {
//
//    /** 订单状态：1. 审批中 2. 审批失败 3. 审批成功（放款中） 4. 放款失败 5. 还款中(已到帐) 6. 已结清 7. 逾期 8. 坏账 */
//    private Integer status;
//    /** 还款计划，第一期，第二期，……，json格式 用户状态为 5、6、7、8时该字段不能为空 */
//    private List<SddRefundPlan> refundPlan;
//    /** 剩余还款额度 */
//    private String remainAmount;
//    /** 剩余还款期数 */
//    private String remainMaturity;
//    /** 审批失败原因，或放款失败原因，或还款失败原因 */
//    private String reason;
//    /** 本金 */
//    private String principal;
//    /** 利息 */
//    private String interest;
//    /** 贷款时间(格式：yyyy-MM-dd HH:ss:ss) */
//    private String loanTime;
//    /** 当前第几期 */
//    private Integer currentPeriod;
//    /** 日利率 */
//    private String dayRate;
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public List<SddRefundPlan> getRefundPlan() {
//        return refundPlan;
//    }
//
//    public void setRefundPlan(List<SddRefundPlan> refundPlan) {
//        this.refundPlan = refundPlan;
//    }
//
//    public String getRemainAmount() {
//        return remainAmount;
//    }
//
//    public void setRemainAmount(String remainAmount) {
//        this.remainAmount = remainAmount;
//    }
//
//    public String getRemainMaturity() {
//        return remainMaturity;
//    }
//
//    public void setRemainMaturity(String remainMaturity) {
//        this.remainMaturity = remainMaturity;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//
//    public String getPrincipal() {
//        return principal;
//    }
//
//    public void setPrincipal(String principal) {
//        this.principal = principal;
//    }
//
//    public String getInterest() {
//        return interest;
//    }
//
//    public void setInterest(String interest) {
//        this.interest = interest;
//    }
//
//    public String getLoanTime() {
//        return loanTime;
//    }
//
//    public void setLoanTime(String loanTime) {
//        this.loanTime = loanTime;
//    }
//
//    public Integer getCurrentPeriod() {
//        return currentPeriod;
//    }
//
//    public void setCurrentPeriod(Integer currentPeriod) {
//        this.currentPeriod = currentPeriod;
//    }
//
//    public String getDayRate() {
//        return dayRate;
//    }
//
//    public void setDayRate(String dayRate) {
//        this.dayRate = dayRate;
//    }
//}
