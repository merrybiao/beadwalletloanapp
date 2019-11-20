package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwFeedbackInfo entity. @author MyEclipse Persistence Tools
 */
@Table(name = "bw_feedback_info")
public class BwFeedbackInfo implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contactPhone;
    private String feedbackContent;
    private Long borrowerId;
    private Date createTime;
    private Date updateTime;

    // Constructors

    /** default constructor */
    public BwFeedbackInfo() {}

    /** full constructor */
    public BwFeedbackInfo(String contactPhone, String feedbackContent, Long borrowerId, Date createTime, Date updateTime) {
        this.contactPhone = contactPhone;
        this.feedbackContent = feedbackContent;
        this.borrowerId = borrowerId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFeedbackContent() {
        return this.feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getupdateTime() {
        return this.updateTime;
    }

    public void setupdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
