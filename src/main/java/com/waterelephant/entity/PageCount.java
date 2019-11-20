/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象股份有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chengpan
 * @date 2017-07-10 14:55:03
 * @description: <描述>
 * @log 2017-07-10 14:55:03 chengpan 新建
 */

@Table(name = "page_count")
public class PageCount implements java.io.Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** sessionId */
    private String sessionId;
    /** IP地址 */
    private String ip;
    /** 页面名称 */
    private String page;
    /** 访问时间 */
    private Date accessTime;
    /** 停留时间 */
    private Long stayTime;
    /** 统计类型 */
    private Integer pageType;

    // Constructors

    /** default constructor */
    public PageCount() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 获取主键 属性值
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return 设置sessionId 属性值
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return 获取sessionId 属性值
     */
    public String getSessionId() {
        return this.sessionId;
    }

    /**
     * @return 设置IP地址 属性值
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return 获取IP地址 属性值
     */
    public String getIp() {
        return this.ip;
    }

    /**
     * @return 设置页面名称 属性值
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return 获取页面名称 属性值
     */
    public String getPage() {
        return this.page;
    }

    /**
     * @return 设置访问时间 属性值
     */
    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * @return 获取访问时间 属性值
     */
    public Date getAccessTime() {
        return this.accessTime;
    }

    /**
     * @return 设置停留时间 属性值
     */
    public void setStayTime(Long stayTime) {
        this.stayTime = stayTime;
    }

    /**
     * @return 获取停留时间 属性值
     */
    public Long getStayTime() {
        return this.stayTime;
    }

    /**
     * @return 获取 pageType属性值
     */
    public Integer getPageType() {
        return pageType;
    }

    /**
     * @param pageType 设置 pageType 属性值为参数值 pageType
     */
    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PageCount[");
        builder.append(" 主键[id] = ");
        builder.append(id);
        builder.append(" sessionId[sessionId] = ");
        builder.append(sessionId);
        builder.append(" IP地址[ip] = ");
        builder.append(ip);
        builder.append(" 页面名称[page] = ");
        builder.append(page);
        builder.append(" 访问时间[accessTime] = ");
        builder.append(accessTime);
        builder.append(" 停留时间[stayTime] = ");
        builder.append(stayTime);
        builder.append(" 统计类型[pageType] = ");
        builder.append(pageType);
        builder.append("]");
        return builder.toString();
    }
}
