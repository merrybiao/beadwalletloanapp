//package com.waterelephant.sxyDrainage.entity.wacai.table;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/7/5
// * @since JDK 1.8
// */
//@Table(name = "bw_wc_bill")
//public class BwWcBill {
//    @Id
//    private Long id;
//    /** 我方工单号 */
//    private Long orderId;
//    /** 手机号 */
//    private String phone;
//    /** 计费开始时间 */
//    private Date chargeBegin;
//    /** 计费结束时间 */
//    private Date chargeEnd;
//    /** 套餐金额(单位：分) */
//    private Integer planAmount;
//    /** 固定费用(单位：分) */
//    private Integer fixedExpenses;
//    /** 语音通信费用(单位：分) */
//    private Integer voiceExpenses;
//    /** 上网费用(单位：分) */
//    private Integer netExpenses;
//    /** 短彩信费用(单位：分) */
//    private Integer mmsExpenses;
//    /** 增值业务费(单位：分) */
//    private Integer addedExpenses;
//    /** 代收费(单位：分) */
//    private Integer collectionExpenses;
//    /** 其他费用(单位：分) */
//    private Integer otherExpenses;
//    /** 消费总金额(单位：分) */
//    private Integer totalAmount;
//    /** 实缴金额(单位：分) */
//    private Integer payAmount;
//    /** 挖财更新时间 */
//    private Date updatedTime;
//    private Date createTime;
//    private Date updateTime;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Date getChargeBegin() {
//        return chargeBegin;
//    }
//
//    public void setChargeBegin(Date chargeBegin) {
//        this.chargeBegin = chargeBegin;
//    }
//
//    public Date getChargeEnd() {
//        return chargeEnd;
//    }
//
//    public void setChargeEnd(Date chargeEnd) {
//        this.chargeEnd = chargeEnd;
//    }
//
//    public Integer getPlanAmount() {
//        return planAmount;
//    }
//
//    public void setPlanAmount(Integer planAmount) {
//        this.planAmount = planAmount;
//    }
//
//    public Integer getFixedExpenses() {
//        return fixedExpenses;
//    }
//
//    public void setFixedExpenses(Integer fixedExpenses) {
//        this.fixedExpenses = fixedExpenses;
//    }
//
//    public Integer getVoiceExpenses() {
//        return voiceExpenses;
//    }
//
//    public void setVoiceExpenses(Integer voiceExpenses) {
//        this.voiceExpenses = voiceExpenses;
//    }
//
//    public Integer getNetExpenses() {
//        return netExpenses;
//    }
//
//    public void setNetExpenses(Integer netExpenses) {
//        this.netExpenses = netExpenses;
//    }
//
//    public Integer getMmsExpenses() {
//        return mmsExpenses;
//    }
//
//    public void setMmsExpenses(Integer mmsExpenses) {
//        this.mmsExpenses = mmsExpenses;
//    }
//
//    public Integer getAddedExpenses() {
//        return addedExpenses;
//    }
//
//    public void setAddedExpenses(Integer addedExpenses) {
//        this.addedExpenses = addedExpenses;
//    }
//
//    public Integer getCollectionExpenses() {
//        return collectionExpenses;
//    }
//
//    public void setCollectionExpenses(Integer collectionExpenses) {
//        this.collectionExpenses = collectionExpenses;
//    }
//
//    public Integer getOtherExpenses() {
//        return otherExpenses;
//    }
//
//    public void setOtherExpenses(Integer otherExpenses) {
//        this.otherExpenses = otherExpenses;
//    }
//
//    public Integer getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(Integer totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public Integer getPayAmount() {
//        return payAmount;
//    }
//
//    public void setPayAmount(Integer payAmount) {
//        this.payAmount = payAmount;
//    }
//
//    public Date getUpdatedTime() {
//        return updatedTime;
//    }
//
//    public void setUpdatedTime(Date updatedTime) {
//        this.updatedTime = updatedTime;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//}
