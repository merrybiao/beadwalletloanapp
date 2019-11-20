package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_risk_blacklist")
public class BwCreditDhbRiskBlacklist implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastAppearIdcardInBlacklist;// 最近该身份证出现在黑名单中时间
    private String inCourtBlacklist;// 是否命中法院黑名单
    private String inP2pBlacklist;// 是否命中网贷黑名单
    private Date createTime;
    private String idcardInBlacklist;// 身份证是否命中黑名单
    private String phoneInBlacklist;// 手机号是否命中黑名单
    private String inBankBlacklist;// 是否命中银行黑名单
    private Long infoId;
    private String lastAppearPhoneInBlacklist;// 最近该手机号出现在黑名单中时间

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastAppearIdcardInBlacklist() {
        return this.lastAppearIdcardInBlacklist;
    }

    public void setLastAppearIdcardInBlacklist(String lastAppearIdcardInBlacklist) {
        this.lastAppearIdcardInBlacklist = lastAppearIdcardInBlacklist;
    }

    public String getInCourtBlacklist() {
        return this.inCourtBlacklist;
    }

    public void setInCourtBlacklist(String inCourtBlacklist) {
        this.inCourtBlacklist = inCourtBlacklist;
    }

    public String getInP2pBlacklist() {
        return this.inP2pBlacklist;
    }

    public void setInP2pBlacklist(String inP2pBlacklist) {
        this.inP2pBlacklist = inP2pBlacklist;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdcardInBlacklist() {
        return this.idcardInBlacklist;
    }

    public void setIdcardInBlacklist(String idcardInBlacklist) {
        this.idcardInBlacklist = idcardInBlacklist;
    }

    public String getPhoneInBlacklist() {
        return this.phoneInBlacklist;
    }

    public void setPhoneInBlacklist(String phoneInBlacklist) {
        this.phoneInBlacklist = phoneInBlacklist;
    }

    public String getInBankBlacklist() {
        return this.inBankBlacklist;
    }

    public void setInBankBlacklist(String inBankBlacklist) {
        this.inBankBlacklist = inBankBlacklist;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getLastAppearPhoneInBlacklist() {
        return this.lastAppearPhoneInBlacklist;
    }

    public void setLastAppearPhoneInBlacklist(String lastAppearPhoneInBlacklist) {
        this.lastAppearPhoneInBlacklist = lastAppearPhoneInBlacklist;
    }

}
