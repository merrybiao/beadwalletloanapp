package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_info_match
 * @author 
 */
@Table(name = "bw_mf_info_match")
public class BwMfInfoMatch implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 姓名是否与运营商数据匹配
     */
    private String realNameCheckYys;

    /**
     * 身份证号码是否与运营商数据匹配
     */
    private String identityCodeCheckYys;

    /**
     * 邮箱是否与运营商数据匹配
     */
    private String emailCheckYys;

    /**
     * 家庭地址是否与运营商数据匹配
     */
    private String homeAddrCheckYys;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRealNameCheckYys() {
        return realNameCheckYys;
    }

    public void setRealNameCheckYys(String realNameCheckYys) {
        this.realNameCheckYys = realNameCheckYys;
    }

    public String getIdentityCodeCheckYys() {
        return identityCodeCheckYys;
    }

    public void setIdentityCodeCheckYys(String identityCodeCheckYys) {
        this.identityCodeCheckYys = identityCodeCheckYys;
    }

    public String getEmailCheckYys() {
        return emailCheckYys;
    }

    public void setEmailCheckYys(String emailCheckYys) {
        this.emailCheckYys = emailCheckYys;
    }

    public String getHomeAddrCheckYys() {
        return homeAddrCheckYys;
    }

    public void setHomeAddrCheckYys(String homeAddrCheckYys) {
        this.homeAddrCheckYys = homeAddrCheckYys;
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