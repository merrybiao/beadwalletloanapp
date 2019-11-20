package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwAdjunct entity. @author MyEclipse Persistence Tools
 */

@Table(name = "bw_data_log")
public class BwDataLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;// 电话号码
    private String idCard;// 身份证
    private Long externalOrder;// 外部工单ID
    private Long sysDataId;// 数据ID
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String dataType;// 数据类型
    private Integer requestType;// 数据请求来源

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getExternalOrder() {
        return externalOrder;
    }

    public void setExternalOrder(Long externalOrder) {
        this.externalOrder = externalOrder;
    }

    public Long getSysDataId() {
        return sysDataId;
    }

    public void setSysDataId(Long sysDataId) {
        this.sysDataId = sysDataId;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

}
