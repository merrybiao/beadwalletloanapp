package com.waterelephant.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "bw_mark_subject")
public class BwMarkSubject implements java.io.Serializable {

    private static final long serialVersionUID = 6195143600493549696L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private Long dimenId;
    private Date createTime;
    private Date updateTime;
    private Short flag;
    private List<BwMarkAnswer> list;

    public BwMarkSubject() {}


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getDimenId() {
        return this.dimenId;
    }

    public void setDimenId(Long dimenId) {
        this.dimenId = dimenId;
    }

    public Short getFlag() {
        return this.flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
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


    public List<BwMarkAnswer> getList() {
        return list;
    }


    public void setList(List<BwMarkAnswer> list) {
        this.list = list;
    }

}
