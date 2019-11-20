package com.waterelephant.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 身份认证来源 code0088
 *
 * @author dengyan
 *
 */
@Table(name = "bw_verify_source")
public class BwVerifySource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long adjunctId;
    private Long borrowerId;
    private Long orderId;
    private Long verifySource; // 认证来源：0、人工，1、face++ 2、商汤

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAdjunctId() {
        return adjunctId;
    }

    public void setAdjunctId(Long adjunctId) {
        this.adjunctId = adjunctId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getVerifySource() {
        return verifySource;
    }

    public void setVerifySource(Long verifySource) {
        this.verifySource = verifySource;
    }
}
