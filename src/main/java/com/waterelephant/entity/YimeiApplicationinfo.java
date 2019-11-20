/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 在亿美平台中查询到 用户在金融平台里的申请贷款信息
 *
 * @author heqiwen
 *
 */
@Table(name = "yimei_applicationinfo")
public class YimeiApplicationinfo implements Serializable {

    /**
     * @author heqiwen
     */
    private static final long serialVersionUID = 3503988022051147342L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;//
    private String pType;// 平台类型
    private String platformCode;// 平台代码
    private String applicationTime;// 申请时间
    private String applicationMount;// 申请金额区间
    private String applicationResult;// 申请结果
    private Date createTime;
    private Long mainid;

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

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApplicationMount() {
        return applicationMount;
    }

    public void setApplicationMount(String applicationMount) {
        this.applicationMount = applicationMount;
    }

    public String getApplicationResult() {
        return applicationResult;
    }

    public void setApplicationResult(String applicationResult) {
        this.applicationResult = applicationResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMainid() {
        return mainid;
    }

    public void setMainid(Long mainid) {
        this.mainid = mainid;
    }



}
