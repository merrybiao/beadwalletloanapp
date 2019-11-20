package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 运营商基础信息
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjCarrierUser implements Serializable {

    @JSONField(name="user_source")
    private String userSource;

    @JSONField(name="id_card")
    private String idCard;//身份证号

    @JSONField(name="addr")
    private String addr;

    @JSONField(name="real_name")
    private String realName; //真实姓名

    @JSONField(name="phone_remain")
    private String phoneRemain;

    @JSONField(name="phone")
    private String phone;

    @JSONField(name="reg_time")
    private String regTime;//号码注册时间

    @JSONField(name="score")
    private String score;

    @JSONField(name="contact_phone")
    private String contactPhone;

    @JSONField(name="star_level")
    private String starLevel;

    //用户实名状态
    @JSONField(name="authentication")
    private String authentication;

    @JSONField(name="phone_status")
    private String phoneStatus;

    //套餐名称
    @JSONField(name="package_name")
    private String packageName;


    public String getUserSource() {
        return userSource;
    }

    public FqgjCarrierUser setUserSource(String userSource) {
        this.userSource = userSource;
        return this;
    }

    public String getIdCard() {
        return idCard;
    }

    public FqgjCarrierUser setIdCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public String getAddr() {
        return addr;
    }

    public FqgjCarrierUser setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public FqgjCarrierUser setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getPhoneRemain() {
        return phoneRemain;
    }

    public FqgjCarrierUser setPhoneRemain(String phoneRemain) {
        this.phoneRemain = phoneRemain;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public FqgjCarrierUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getRegTime() {
        return regTime;
    }

    public FqgjCarrierUser setRegTime(String regTime) {
        this.regTime = regTime;
        return this;
    }

    public String getScore() {
        return score;
    }

    public FqgjCarrierUser setScore(String score) {
        this.score = score;
        return this;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public FqgjCarrierUser setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public FqgjCarrierUser setStarLevel(String starLevel) {
        this.starLevel = starLevel;
        return this;
    }

    public String getAuthentication() {
        return authentication;
    }

    public FqgjCarrierUser setAuthentication(String authentication) {
        this.authentication = authentication;
        return this;
    }

    public String getPhoneStatus() {
        return phoneStatus;
    }

    public FqgjCarrierUser setPhoneStatus(String phoneStatus) {
        this.phoneStatus = phoneStatus;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public FqgjCarrierUser setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }
}
