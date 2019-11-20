package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @Title: BwBankCardChange.java
 * @Description:
 * @author wangkun
 * @date 2017年4月12日 下午3:02:26
 * @version V1.0
 */
@Table(name = "bw_bank_card_change")
public class BwBankCardChange implements Serializable {

    private static final long serialVersionUID = 7975280039831443833L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrowerId;// 借口人id
    private String cardNo;// 银行卡号
    private String provinceCode;//
    private String cityCode;//
    private String bankCode;//
    private String bankName;// 开户行名称
    private String phone;// 预留手机号
    private Integer signStatus;// 签约状态 0:未签约 1:富友已签约 2：连连已签约',
    private Date createTimeOld;//
    private Date updateTimeOld;//
    @Column(name = "create_time")
    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Date getCreateTimeOld() {
        return createTimeOld;
    }

    public void setCreateTimeOld(Date createTimeOld) {
        this.createTimeOld = createTimeOld;
    }

    public Date getUpdateTimeOld() {
        return updateTimeOld;
    }

    public void setUpdateTimeOld(Date updateTimeOld) {
        this.updateTimeOld = updateTimeOld;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
