package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_bigfintech_report")
public class BwBigsFintech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mobile;

    private String orderId;

    private String borrowerId;

    private String phoneApply;

    private Date timeQuery;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getPhoneApply() {
        return phoneApply;
    }

    public void setPhoneApply(String phoneApply) {
        this.phoneApply = phoneApply;
    }

    public Date getTimeQuery() {
        return timeQuery;
    }

    public void setTimeQuery(Date timeQuery) {
        this.timeQuery = timeQuery;
    }

}
