package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_yxaf_risk")
public class BwYxafRisk implements Serializable {
    private static final long serialVersionUID = -4768290479436347256L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String idCard;
    private String risk_item_type_code;
    private String risk_item_value;
    private String risk_item_type;
    private String risk_type;
    private String risk_type_code;
    private String source;
    private String source_code;
    private String risk_time;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRisk_item_type_code() {
        return risk_item_type_code;
    }

    public void setRisk_item_type_code(String risk_item_type_code) {
        this.risk_item_type_code = risk_item_type_code;
    }

    public String getRisk_item_value() {
        return risk_item_value;
    }

    public void setRisk_item_value(String risk_item_value) {
        this.risk_item_value = risk_item_value;
    }

    public String getRisk_item_type() {
        return risk_item_type;
    }

    public void setRisk_item_type(String risk_item_type) {
        this.risk_item_type = risk_item_type;
    }

    public String getRisk_type() {
        return risk_type;
    }

    public void setRisk_type(String risk_type) {
        this.risk_type = risk_type;
    }

    public String getRisk_type_code() {
        return risk_type_code;
    }

    public void setRisk_type_code(String risk_type_code) {
        this.risk_type_code = risk_type_code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public String getRisk_time() {
        return risk_time;
    }

    public void setRisk_time(String risk_time) {
        this.risk_time = risk_time;
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
