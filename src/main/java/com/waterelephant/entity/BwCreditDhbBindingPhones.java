package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_binding_phones")
public class BwCreditDhbBindingPhones implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneProvince;// 手机归属地省份
    private String lastAppearPhone;// 最近此手机号出现时间
    private String phoneOperator;// 手机运营商
    private Integer otherNamesCnt;// 此号码绑定其他姓名个数
    private Date createTime;
    private Long infoId;
    private Integer searchOrgsCnt;// 查询此手机号的机构数
    private String otherPhone;// 绑定其他手机号码
    private String phoneCity;// 手机归属地城市

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneProvince() {
        return this.phoneProvince;
    }

    public void setPhoneProvince(String phoneProvince) {
        this.phoneProvince = phoneProvince;
    }

    public String getLastAppearPhone() {
        return this.lastAppearPhone;
    }

    public void setLastAppearPhone(String lastAppearPhone) {
        this.lastAppearPhone = lastAppearPhone;
    }

    public String getPhoneOperator() {
        return this.phoneOperator;
    }

    public void setPhoneOperator(String phoneOperator) {
        this.phoneOperator = phoneOperator;
    }

    public Integer getOtherNamesCnt() {
        return this.otherNamesCnt;
    }

    public void setOtherNamesCnt(Integer otherNamesCnt) {
        this.otherNamesCnt = otherNamesCnt;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Integer getSearchOrgsCnt() {
        return this.searchOrgsCnt;
    }

    public void setSearchOrgsCnt(Integer searchOrgsCnt) {
        this.searchOrgsCnt = searchOrgsCnt;
    }

    public String getOtherPhone() {
        return this.otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getPhoneCity() {
        return this.phoneCity;
    }

    public void setPhoneCity(String phoneCity) {
        this.phoneCity = phoneCity;
    }

}
