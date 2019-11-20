//package com.waterelephant.sxyDrainage.entity.shandiandai.sddoperator;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * 闪电贷运营商：套餐信息
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/3
// * @since JDK 1.8
// */
//@Table(name = "bw_sdd_bill")
//public class BwSddBill {
//
//
//    @Id
//    private Long id;
//    /** 我方订单号 */
//    private Long orderId;
//
//    /** 套餐及固定费用 */
//    private String planAmt;
//    /** 账单月份 */
//    private String billCycle;
//    /** 总费用 */
//    private String totalAmt;
//    /** 实际缴费金额 */
//    private String payAmt;
//
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
//    public String getPlanAmt() {
//        return planAmt;
//    }
//
//    public void setPlanAmt(String planAmt) {
//        this.planAmt = planAmt;
//    }
//
//    public String getBillCycle() {
//        return billCycle;
//    }
//
//    public void setBillCycle(String billCycle) {
//        this.billCycle = billCycle;
//    }
//
//    public String getTotalAmt() {
//        return totalAmt;
//    }
//
//    public void setTotalAmt(String totalAmt) {
//        this.totalAmt = totalAmt;
//    }
//
//    public String getPayAmt() {
//        return payAmt;
//    }
//
//    public void setPayAmt(String payAmt) {
//        this.payAmt = payAmt;
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
