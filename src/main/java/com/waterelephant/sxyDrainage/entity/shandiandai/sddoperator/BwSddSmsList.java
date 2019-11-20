//package com.waterelephant.sxyDrainage.entity.shandiandai.sddoperator;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * 闪电贷短信列表（仅android）
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/11
// * @since JDK 1.8
// */
//@Table(name = "bw_sdd_sms_list")
//public class BwSddSmsList {
//
//    @Id
//    private Long id;
//    /** 我方订单号 */
//    private Long orderId;
//
//    /** 对方手机号 */
//    private String address;
//    /** 时间戳 */
//    private String date;
//    /** 短信内容 */
//    private String body;
//    /** 固定值INBOX */
//    private String type;
//
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
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
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
