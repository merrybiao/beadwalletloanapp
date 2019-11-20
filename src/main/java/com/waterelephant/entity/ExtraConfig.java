package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Table;

@Table(name = "extra_config")
public class ExtraConfig implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    // 主键
    private Integer ecid;
    // 参数编号
    private String code;
    // 值
    private String value;
    // 描述
    private String describeInfo;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    public Integer getEcid() {
        return ecid;
    }

    public void setEcid(Integer ecid) {
        this.ecid = ecid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
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
