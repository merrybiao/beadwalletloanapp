package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_order_auth")
public class BwOrderAuth implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;// 工单ID
    private Integer auth_type;// 认证类型
    private Integer auth_channel;// 认证渠道
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Integer photoState;// 照片认证状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(Integer auth_type) {
        this.auth_type = auth_type;
    }

    public Integer getAuth_channel() {
        return auth_channel;
    }

    public void setAuth_channel(Integer auth_channel) {
        this.auth_channel = auth_channel;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPhotoState() {
        return photoState;
    }

    public void setPhotoState(Integer photoState) {
        this.photoState = photoState;
    }

}
