package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lwl
 *
 */
@Table(name = "bw_operate_voice_top20_number")
public class BwOperateVoiceTop20Number implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 有效联系次数
     */
    private Integer validContact;
    /**
     * 通讯录前20与手机联系次数小于等于5的次数
     */
    private Integer top20ContactNumber;
    /**
     * 通讯录前20与手机联系次数小于等于5的平均次数
     */
    private Double top20ContactNumberAverage;
    /**
     * 通讯录前20与手机联系次数小于等于5的次数
     */
    private Integer telContactNumber;

    private Date createTime;

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

    public Integer getValidContact() {
        return validContact;
    }

    public void setValidContact(Integer validContact) {
        this.validContact = validContact;
    }

    public Integer getTop20ContactNumber() {
        return top20ContactNumber;
    }

    public void setTop20ContactNumber(Integer top20ContactNumber) {
        this.top20ContactNumber = top20ContactNumber;
    }

    public Double getTop20ContactNumberAverage() {
        return top20ContactNumberAverage;
    }

    public void setTop20ContactNumberAverage(Double top20ContactNumberAverage) {
        this.top20ContactNumberAverage = top20ContactNumberAverage;
    }

    public Integer getTelContactNumber() {
        return telContactNumber;
    }

    public void setTelContactNumber(Integer telContactNumber) {
        this.telContactNumber = telContactNumber;
    }

}
