package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_qhzx_black")
public class BwQhzxBlack implements Serializable {
    private static final long serialVersionUID = 2601427986345171237L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String idCard;
    private String sourceId;
    private String rskScore;
    private String rskMark;
    private String dataBuildTime;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getRskScore() {
        return rskScore;
    }

    public void setRskScore(String rskScore) {
        this.rskScore = rskScore;
    }

    public String getRskMark() {
        return rskMark;
    }

    public void setRskMark(String rskMark) {
        this.rskMark = rskMark;
    }

    public String getDataBuildTime() {
        return dataBuildTime;
    }

    public void setDataBuildTime(String dataBuildTime) {
        this.dataBuildTime = dataBuildTime;
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
