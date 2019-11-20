package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_rate_dictionary")
public class BwRateDictionary implements Serializable {

    private static final long serialVersionUID = 3295331202098803223L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 利率类型 1借款等额本息 2借款先息后本
     */
    private Integer rateType;
    /**
     * 期限
     */
    private Integer rateTerm;

    /**
     * 借款年利率
     */
    private Double borrowerRate;
    /**
     * 合同年利率
     */
    private Double contractRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRateType() {
        return rateType;
    }

    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }

    public Integer getRateTerm() {
        return rateTerm;
    }

    public void setRateTerm(Integer rateTerm) {
        this.rateTerm = rateTerm;
    }

    public Double getBorrowerRate() {
        return borrowerRate;
    }

    public void setBorrowerRate(Double borrowerRate) {
        this.borrowerRate = borrowerRate;
    }

    public Double getContractRate() {
        return contractRate;
    }

    public void setContractRate(Double contractRate) {
        this.contractRate = contractRate;
    }



}
