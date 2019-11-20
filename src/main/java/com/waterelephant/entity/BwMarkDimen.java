package com.waterelephant.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwMarkDimen entity. @author MyEclipse Persistence Tools
 */

@Table(name = "bw_mark_dimen")
public class BwMarkDimen implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dimen;
    private Date createTime;
    private Date updateTime;
    private Short flag;
    private List<BwMarkSubject> list;

    // Constructors

    /** default constructor */
    public BwMarkDimen() {}

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getFlag() {
        return this.flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }

    public List<BwMarkSubject> getList() {
        return list;
    }

    public void setList(List<BwMarkSubject> list) {
        this.list = list;
    }

    public String getDimen() {
        return dimen;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
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
