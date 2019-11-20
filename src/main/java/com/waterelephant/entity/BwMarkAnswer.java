package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwMarkOption entity. @author MyEclipse Persistence Tools
 */
@Table(name = "bw_mark_answer")
public class BwMarkAnswer implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    private Short mark;
    private Long subjectId;
    private Date createTime;
    private Date updateTime;
    private Short flag;

    // Constructors

    /** default constructor */
    public BwMarkAnswer() {}

    /** full constructor */
    public BwMarkAnswer(String answer, Short mark, Long subjectId, Date createTime, Short flag) {
        this.answer = answer;
        this.mark = mark;
        this.subjectId = subjectId;
        this.createTime = createTime;
        this.flag = flag;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getMark() {
        return this.mark;
    }

    public void setMark(Short mark) {
        this.mark = mark;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Short getFlag() {
        return this.flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
