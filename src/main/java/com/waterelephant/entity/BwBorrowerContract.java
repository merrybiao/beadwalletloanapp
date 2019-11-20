package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款合同表
 *
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月10日 下午3:06:05
 */
public class BwBorrowerContract implements Serializable {

    private Long id;
    // 合同流水号
    private String contractNo;
    // 借款人id
    private String borrowerName;
    // 合同银行卡号
    private String bankCard;
    // 合同金额
    private Double contractAmount;
    // 签约时间
    private Date contractTime;
    // 交易类型，1：放款转入，2：还款利息，3：还款本金 4风险备用金转入 5 平息差转出 6 风险备用金转出 7 抵押人转出
    private Integer contractType;
    // 交易描述
    private String tradeRemark;

    private Long orderId;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getTradeRemark() {
        return tradeRemark;
    }

    public void setTradeRemark(String tradeRemark) {
        this.tradeRemark = tradeRemark;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
