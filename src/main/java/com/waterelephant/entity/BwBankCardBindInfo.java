package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 银行卡绑卡信息
 */
@Table(name = "bw_bank_card_bind_info")
public class BwBankCardBindInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrowerId;
    private Long bankCardId;

    /**
     * 绑定渠道，1.宝付认证，2.连连，3.易宝，4.宝付协议
     */
    private Integer bindChannel;

    /**
     * 渠道银行卡编码
     */
    private String channelBankCode;

    /**
     * 绑定编号
     */
    private String bindNo;
    private Date createTime;
    private Date updateTime;

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

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public Integer getBindChannel() {
        return bindChannel;
    }

    public void setBindChannel(Integer bindChannel) {
        this.bindChannel = bindChannel;
    }

    public String getChannelBankCode() {
        return channelBankCode;
    }

    public void setChannelBankCode(String channelBankCode) {
        this.channelBankCode = channelBankCode;
    }

    public String getBindNo() {
        return bindNo;
    }

    public void setBindNo(String bindNo) {
        this.bindNo = bindNo;
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
