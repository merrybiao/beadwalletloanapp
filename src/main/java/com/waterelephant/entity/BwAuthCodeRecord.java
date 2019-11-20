/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象股份有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.util.Date;

/**
 * @author chengpan
 * @date 2017-05-22 18:15:10
 * @description: <描述>
 * @log 2017-05-22 18:15:10 chengpan 新建
 */
public class BwAuthCodeRecord {

    // Fields
    private Integer id;
    /** 被请求次数 */
    private Integer requestNumber;
    /** 解密成功的次数 */
    private Integer requestSucceedNumber;
    /** 解密失败的次数 */
    private Integer requestFailNumber;
    /** 请求来源 0：APP 1：H5 */
    private Integer type;
    /**  */
    private Date createTime;

    // Constructors

    /** default constructor */
    public BwAuthCodeRecord(Integer requestNumber, Integer requestSucceedNumber, Integer requestFailNumber, Integer type) {
        this.requestNumber = requestNumber;
        this.requestSucceedNumber = requestSucceedNumber;
        this.requestFailNumber = requestFailNumber;
        this.type = type;
    }

    public BwAuthCodeRecord() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 获取主键 属性值
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @return 设置被请求次数 属性值
     */
    public void setRequestNumber(Integer requestNumber) {
        this.requestNumber = requestNumber;
    }

    /**
     * @return 获取被请求次数 属性值
     */
    public Integer getRequestNumber() {
        return this.requestNumber;
    }

    /**
     * @return 设置解密成功的次数 属性值
     */
    public void setRequestSucceedNumber(Integer requestSucceedNumber) {
        this.requestSucceedNumber = requestSucceedNumber;
    }

    /**
     * @return 获取解密成功的次数 属性值
     */
    public Integer getRequestSucceedNumber() {
        return this.requestSucceedNumber;
    }

    /**
     * @return 设置解密失败的次数 属性值
     */
    public void setRequestFailNumber(Integer requestFailNumber) {
        this.requestFailNumber = requestFailNumber;
    }

    /**
     * @return 获取解密失败的次数 属性值
     */
    public Integer getRequestFailNumber() {
        return this.requestFailNumber;
    }

    /**
     * @return 设置请求来源 0：APP 1：H5 属性值
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return 获取请求来源 0：APP 1：H5 属性值
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * @return 设置 属性值
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 获取 属性值
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwAuthCodeRecord[");
        builder.append(" 主键[id] = ");
        builder.append(id);
        builder.append(" 被请求次数[requestNumber] = ");
        builder.append(requestNumber);
        builder.append(" 解密成功的次数[requestSucceedNumber] = ");
        builder.append(requestSucceedNumber);
        builder.append(" 解密失败的次数[requestFailNumber] = ");
        builder.append(requestFailNumber);
        builder.append(" 请求来源 0：APP 1：H5[type] = ");
        builder.append(type);
        builder.append(" [createTime] = ");
        builder.append(createTime);
        builder.append("]");
        return builder.toString();
    }
}
