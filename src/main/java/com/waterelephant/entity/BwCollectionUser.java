package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 催单用户——工单关系model
 *
 * @author song
 *
 */
@Table(name = "bw_collection_user")
public class BwCollectionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long repayId;
    private Long userId;
    private Date createTime;
    private Date updateTime;
    private Integer status;

    public BwCollectionUser(Long id, Long orderId, Long userId, Date createTime) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.createTime = createTime;
    }

    public BwCollectionUser() {}

    @Override
    public String toString() {
        return "BwCollectionUser [id=" + id + ", orderId=" + orderId + ", userId=" + userId + ", createTime=" + createTime + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
