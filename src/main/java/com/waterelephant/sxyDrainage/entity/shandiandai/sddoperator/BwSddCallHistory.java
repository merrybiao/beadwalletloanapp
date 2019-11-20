//package com.waterelephant.sxyDrainage.entity.shandiandai.sddoperator;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * 手机信息通话记录
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/11
// * @since JDK 1.8
// */
//@Table(name = "bw_sdd_call_history")
//public class BwSddCallHistory {
//
//    @Id
//    private Long id;
//    /** 我方订单号 */
//    private Long orderId;
//
//    /** 姓名 */
//    private String name;
//    /** 手机号 */
//    private String phone;
//    /** 时间戳 */
//    private String callTime;
//    /** 呼叫时间，秒 */
//    private String duration;
//    /** 呼叫类型：callout：呼出 callin：呼入 */
//    private String callType;
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
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
//    public String getCallTime() {
//        return callTime;
//    }
//
//    public void setCallTime(String callTime) {
//        this.callTime = callTime;
//    }
//
//    public String getDuration() {
//        return duration;
//    }
//
//    public void setDuration(String duration) {
//        this.duration = duration;
//    }
//
//    public String getCallType() {
//        return callType;
//    }
//
//    public void setCallType(String callType) {
//        this.callType = callType;
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
