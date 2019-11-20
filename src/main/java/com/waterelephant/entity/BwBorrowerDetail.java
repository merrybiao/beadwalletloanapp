/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 借款人详情
 *
 * Module:
 *
 * BwBorrowerDetail.java
 *
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "bw_borrower_detail")
public class BwBorrowerDetail implements Serializable {
    private static final long serialVersionUID = 3470521685038505391L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 借款人ID
     */
    private Long borrowerId;
    /**
     * 应用市场编码
     */
    private String appStoreCode;
    private Date createTime;
    private Date updateTime;

    // code0091 新增
    private Integer userType;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getAppStoreCode() {
        return appStoreCode;
    }

    public void setAppStoreCode(String appStoreCode) {
        this.appStoreCode = appStoreCode;
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

    @Override
    public String toString() {
        return "BwBorrowerDetail [id=" + id + ", borrowerId=" + borrowerId + ", appStoreCode=" + appStoreCode + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}
