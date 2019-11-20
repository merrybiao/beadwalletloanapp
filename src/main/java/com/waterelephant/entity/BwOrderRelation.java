package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bwOrderRelation 实体类 工单关系数据表 2016/12/19 15:00 wrh
 */

@Table(name = "bw_order_relation")
public class BwOrderRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//
    private Long orderId;// 工单id
    private Long orderStatus;// 工单状态，1：初审，2 终审
    private Long allotStatus;// 关联状态：1待分配，2 审核中,3 审核完成，4 撤回
    private Integer rank;// 信用等级：1:A,2:B,3:C,4:D
    private Long userId;//
    private String label;// 风险标签：1A,2B,3C,4D,5E,6F,7G,8H
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间

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

    public Long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getAllotStatus() {
        return allotStatus;
    }

    public void setAllotStatus(Long allotStatus) {
        this.allotStatus = allotStatus;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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


}
