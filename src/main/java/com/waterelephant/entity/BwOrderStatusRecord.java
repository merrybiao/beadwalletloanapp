package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_order_status_record")
public class BwOrderStatusRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Integer id;
    /** 工单ID */
    private String orderId;
    /** 工单状态 */
    private long orderStatus;
    /** 创建时间 */
    private Date createTime;
    /** 是否有效 ：0 否 1是 */
    private Integer effective;
    /** 弹窗消息 */
    private String msg;
    /** 弹框样式 */
    private String dialogStyle;

    public BwOrderStatusRecord() {

    }

    public BwOrderStatusRecord(String orderId, Integer orderStatus, Date createTime, Integer effective, String msg) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.effective = effective;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public String getMsg() {
        return msg;
    }

    public String getDialogStyle() {
        return dialogStyle;
    }

    public void setDialogStyle(String dialogStyle) {
        this.dialogStyle = dialogStyle;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
