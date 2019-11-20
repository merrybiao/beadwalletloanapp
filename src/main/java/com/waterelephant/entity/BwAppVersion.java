package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * app版本
 *
 * @author lujilong
 *
 */
@Table(name = "bw_app_version")
public class BwAppVersion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer appType;
    private String appVersion;
    private Date updateTime;
    private Integer needUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 获取 needUpdate属性值
     */
    public Integer getNeedUpdate() {
        return needUpdate;
    }

    /**
     * @param needUpdate 设置 needUpdate 属性值为参数值 needUpdate
     */
    public void setNeedUpdate(Integer needUpdate) {
        this.needUpdate = needUpdate;
    }

}
