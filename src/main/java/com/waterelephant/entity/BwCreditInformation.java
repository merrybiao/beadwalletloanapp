package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_information")
public class BwCreditInformation implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键id
    private Integer rejectType;// 被拒类型
    private Date updateTime;
    private Date createTime;
    private String name;// 姓名
    private String idCard;// 身份证号
    private String remark;// 描述
    private Integer source;// 数据来源：1.国政通 2.前海 3.宜信 4.闪银 5.同盾 6.芝麻信用 7.其它 8.魔蝎 9.融360
    private String rejectInfo;// 拒绝信息
    private Integer type;// 类型

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRejectType() {
        return this.rejectType;
    }

    public void setRejectType(Integer rejectType) {
        this.rejectType = rejectType;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRejectInfo() {
        return this.rejectInfo;
    }

    public void setRejectInfo(String rejectInfo) {
        this.rejectInfo = rejectInfo;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
