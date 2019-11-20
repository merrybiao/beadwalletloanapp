package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 认证状态表
 *
 * @author hwuei
 */
@Table(name = "bw_auth_status")
public class BwAuthStatus implements Serializable {

    private static final long serialVersionUID = -6309016692617376336L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 借款人id
     */
    private Long borrowerId;
    /**
     * 社保认证状态
     */
    private Integer insureAuthStatus;
    /**
     * 工作信息认证状态
     */
    private Integer workAuthStatus;
    /**
     * 借款人身份信息认证状态
     */
    private Integer borrowerAuthStatus;
    /**
     * 上传证件认证状态
     */
    private int uploadAuthStatus;

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

    public int getInsureAuthStatus() {
        return insureAuthStatus;
    }

    public void setInsureAuthStatus(int insureAuthStatus) {
        this.insureAuthStatus = insureAuthStatus;
    }

    public int getWorkAuthStatus() {
        return workAuthStatus;
    }

    public void setWorkAuthStatus(int workAuthStatus) {
        this.workAuthStatus = workAuthStatus;
    }

    public int getBorrowerAuthStatus() {
        return borrowerAuthStatus;
    }

    public void setBorrowerAuthStatus(int borrowerAuthStatus) {
        this.borrowerAuthStatus = borrowerAuthStatus;
    }

    public int getUploadAuthStatus() {
        return uploadAuthStatus;
    }

    public void setUploadAuthStatus(int uploadAuthStatus) {
        this.uploadAuthStatus = uploadAuthStatus;
    }


}
