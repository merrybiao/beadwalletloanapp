//package com.waterelephant.sxyDrainage.entity.wacai;
//
//import java.util.List;
//
///**
// * @author wangfei
// * @version 1.0
// * @date 2018/6/29
// * @Description <挖财订单状态返回实体类封装>
// * @since JDK 1.8
// */
//public class WaCaiOrderInfo {
//
//    /** 成功 */
//    public static final String CODE_SUCCESS = "0";
//    /** 失败 */
//    public static final String CODE_FAIL = "1";
//    /**
//     * 返回code
//     */
//    private String code;
//    /**
//     * 返回信息
//     */
//    private String message;
//
//    /**
//     * 申请订单号
//     */
//    private String  orderId;
//    /**
//     * 用户标识
//     */
//    private String  openId	;
//    /**
//     * 标准订单状态, 见订单状态
//     */
//    private Integer  orderStatus;
//    /**
//     * 状态发生时间(YYYY-MM-dd hh:mm:ss)
//     */
//    private String  updateTime	;
//    /**
//     * 放款金额，非必填
//     */
//    private Long  amount	;
//    /**
//     * 合作方返回本次审核的备注，如：审核未通过原因
//     */
//    private String  remark	;
//    /**
//     * 当前订单的还款计划
//     */
//    private List<WaCaiRepaymentPlan> repaymentPlans	;
//
//    public WaCaiOrderInfo() {
//    }
//
//    public WaCaiOrderInfo(String code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
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
//    public Integer getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(Integer orderStatus) {
//        this.orderStatus = orderStatus;
//    }
//
//    public String getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(String updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public Long getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Long amount) {
//        this.amount = amount;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public List<WaCaiRepaymentPlan> getRepaymentPlans() {
//        return repaymentPlans;
//    }
//
//    public void setRepaymentPlans(List<WaCaiRepaymentPlan> repaymentPlans) {
//        this.repaymentPlans = repaymentPlans;
//    }
//}
