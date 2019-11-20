package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwAheadRepay entity. @author MyEclipse Persistence Tools
 */
@Table(name = "bw_ahead_repay")
public class BwAheadRepay implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date repayDate;
    private Double repayAmount;
    private Long orderId;
    private Date createTime;

    // Constructors

    /** default constructor */
    public BwAheadRepay() {}


    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getRepayAmount() {
        return this.repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public Date getRepayDate() {
        return repayDate;
    }


    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}
