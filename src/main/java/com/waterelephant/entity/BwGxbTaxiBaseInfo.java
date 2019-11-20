package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwGxbTaxiBaseInfo.java 公信宝-滴滴出租-用户信息
 *
 * @author dinglinhao
 * @date 2018年6月29日13:47:33
 *
 */
@Table(name = "bw_gxb_taxi_baseInfo")
public class BwGxbTaxiBaseInfo implements Serializable {

    private static final long serialVersionUID = 7772600600508570418L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键

    // 用户授权唯一性标志
    private String sequenceNo;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 是否本人账号，1是0否
     */
    private Integer status = 0;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户性别 0未知 1男2女
     */
    private Integer gender = 0;

    /**
     * 用户级别
     */
    private Integer level;

    /**
     * 用户级别描述 2白银 3黄金 4白金 5钻石 6黑金
     */
    private String levelName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 芝麻分
     */
    private Integer sesameScore;
    /**
     * 用户身份证(未实名的用户无此字段)
     */
    private String idCard;
    /**
     * 用户所在企业名(未开通企业滴滴的用户无此字段)
     */
    private String company;

    private Date lastUpdateDate;

    private Date insertTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSesameScore() {
        return sesameScore;
    }

    public void setSesameScore(Integer sesameScore) {
        this.sesameScore = sesameScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
