package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwAdjunct entity. @author MyEclipse Persistence Tools
 */

@Table(name = "bw_adjunct")
public class BwAdjunct implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer adjunctType;// 类型
    private String adjunctPath;// 路径
    private String adjunctDesc;// 描述
    private Long orderId;// 工单ID
    private Long borrowerId;// 借款人ID
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Integer photoState;// 照片类型0手持，1活体

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAdjunctType() {
        return adjunctType;
    }

    public void setAdjunctType(Integer adjunctType) {
        this.adjunctType = adjunctType;
    }

    public String getAdjunctPath() {
        return adjunctPath;
    }

    public void setAdjunctPath(String adjunctPath) {
        this.adjunctPath = adjunctPath;
    }

    public String getAdjunctDesc() {
        return adjunctDesc;
    }

    public void setAdjunctDesc(String adjunctDesc) {
        this.adjunctDesc = adjunctDesc;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
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
