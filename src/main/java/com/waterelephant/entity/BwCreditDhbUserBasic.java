package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_user_basic")
public class BwCreditDhbUserBasic implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String birthday;// 生日日期
    private String lastAppearPhone;// 手机号最近出现时间
    private String phoneOperator;// 手机运营商
    private String lastAppearIdcard;// 身份证最近出现时间
    private String gender;// 性别
    private Date createTime;
    private String idcardCity;// 身份证户籍城市
    private Integer recordPhoneDays;// 手机号记录天数
    private String idcardRegion;// 身份证户籍地区
    private Integer usedPhonesCnt;// 关联手机号数量
    private String idcardProvince;// 身份证户籍省份
    private String phoneProvince;// 手机归属地省份
    private Integer idcardValidate;// 身份证是否是有效身份证
    private Long infoId;
    private Integer usedIdcardsCnt;// 关联身份证数量
    private String phoneCity;// 手机归属地城市
    private Integer recordIdcardDays;// 身份证号记录天数
    private Integer age;// 年龄

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLastAppearPhone() {
        return this.lastAppearPhone;
    }

    public void setLastAppearPhone(String lastAppearPhone) {
        this.lastAppearPhone = lastAppearPhone;
    }

    public String getPhoneOperator() {
        return this.phoneOperator;
    }

    public void setPhoneOperator(String phoneOperator) {
        this.phoneOperator = phoneOperator;
    }

    public String getLastAppearIdcard() {
        return this.lastAppearIdcard;
    }

    public void setLastAppearIdcard(String lastAppearIdcard) {
        this.lastAppearIdcard = lastAppearIdcard;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdcardCity() {
        return this.idcardCity;
    }

    public void setIdcardCity(String idcardCity) {
        this.idcardCity = idcardCity;
    }

    public Integer getRecordPhoneDays() {
        return this.recordPhoneDays;
    }

    public void setRecordPhoneDays(Integer recordPhoneDays) {
        this.recordPhoneDays = recordPhoneDays;
    }

    public String getIdcardRegion() {
        return this.idcardRegion;
    }

    public void setIdcardRegion(String idcardRegion) {
        this.idcardRegion = idcardRegion;
    }

    public Integer getUsedPhonesCnt() {
        return this.usedPhonesCnt;
    }

    public void setUsedPhonesCnt(Integer usedPhonesCnt) {
        this.usedPhonesCnt = usedPhonesCnt;
    }

    public String getIdcardProvince() {
        return this.idcardProvince;
    }

    public void setIdcardProvince(String idcardProvince) {
        this.idcardProvince = idcardProvince;
    }

    public String getPhoneProvince() {
        return this.phoneProvince;
    }

    public void setPhoneProvince(String phoneProvince) {
        this.phoneProvince = phoneProvince;
    }

    public Integer getIdcardValidate() {
        return this.idcardValidate;
    }

    public void setIdcardValidate(Integer idcardValidate) {
        this.idcardValidate = idcardValidate;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Integer getUsedIdcardsCnt() {
        return this.usedIdcardsCnt;
    }

    public void setUsedIdcardsCnt(Integer usedIdcardsCnt) {
        this.usedIdcardsCnt = usedIdcardsCnt;
    }

    public String getPhoneCity() {
        return this.phoneCity;
    }

    public void setPhoneCity(String phoneCity) {
        this.phoneCity = phoneCity;
    }

    public Integer getRecordIdcardDays() {
        return this.recordIdcardDays;
    }

    public void setRecordIdcardDays(Integer recordIdcardDays) {
        this.recordIdcardDays = recordIdcardDays;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
