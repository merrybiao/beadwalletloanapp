/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象股份有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.util.Date;

/**
 * @author chengpan
 * @date 2017-04-17 10:01:43
 * @description: <描述>
 * @log 2017-04-17 10:01:43 chengpan 新建
 */
public class BwBorrowerWealth {

    private Integer id;
    /** 借款人ID */
    private Integer borrowerId;
    /** 财富值 */
    private Double wealth;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    // Constructors

    /** default constructor */
    public BwBorrowerWealth() {}

    // Property accessors

    /**
     * @return 设置 属性值
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 获取 属性值
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @return 设置借款人ID 属性值
     */
    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    /**
     * @return 获取借款人ID 属性值
     */
    public Integer getBorrowerId() {
        return this.borrowerId;
    }

    /**
     * @return 设置财富值 属性值
     */
    public void setWealth(Double wealth) {
        this.wealth = wealth;
    }

    /**
     * @return 获取财富值 属性值
     */
    public Double getWealth() {
        return this.wealth;
    }

    /**
     * @return 设置创建时间 属性值
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 获取创建时间 属性值
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @return 设置更新时间 属性值
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 获取更新时间 属性值
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwBorrowerWealth[");
        builder.append(" [id] = ");
        builder.append(id);
        builder.append(" 借款人ID[borrowerId] = ");
        builder.append(borrowerId);
        builder.append(" 财富值[wealth] = ");
        builder.append(wealth);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append(" 更新时间[updateTime] = ");
        builder.append(updateTime);
        builder.append("]");
        return builder.toString();
    }
}
