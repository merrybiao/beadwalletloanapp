package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Table;

/**
 * bwBlacklist 实体类 黑名单 2017/01/11 11:44 wrh
 */

@Table(name = "bw_blacklist")
public class BwBlacklist implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;//
    private String name;// 客户
    private String card;// 身份证号
    private String phone;// 手机号
    private Integer sort;// 类型：1：黑名单，2：灰名单拒，3：白名单
    private String remark;// 备注
    private Long sysUserId;// 提交人Id
    private Integer status;// 状态审核：0，未审核，1通过,2 拒绝
    private Date createTime;//
    private Date updateTime;//

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
