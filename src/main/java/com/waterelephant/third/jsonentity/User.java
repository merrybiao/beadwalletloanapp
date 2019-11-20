package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 运营商基础信息
 * Created by dengyan on 2017/7/20.
 */
public class User {

    @JSONField(name = "user_source")
    private String userSource; // 号码类型

    @JSONField(name = "id_card")
    private String idCard; // 身份证号

    @JSONField(name = "addr")
    private String addr; // 注册改号码所填写的地址

    @JSONField(name = "real_name")
    private String realName; // 用户姓名

    @JSONField(name = "phone_remain")
    private String phoneRemain; // 当前账户余额

    @JSONField(name = "phone")
    private String phone; // 电话号码

    @JSONField(name = "reg_time")
    private String regTime; // 入网时间

    @JSONField(name = "score")
    private String score; // 用户积分

    @JSONField(name = "contact_phone")
    private String contactPhone; // 联系人号码

    @JSONField(name = "star_level")
    private String starLevel; // 用户星级

    @JSONField(name = "authentication")
    private String authentication; // 用户实名状态

    @JSONField(name = "phone_status")
    private String phoneStatus; // 客户状态

    @JSONField(name = "package_name")
    private String packageName; // 套餐名称

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneRemain() {
        return phoneRemain;
    }

    public void setPhoneRemain(String phoneRemain) {
        this.phoneRemain = phoneRemain;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(String phoneStatus) {
        this.phoneStatus = phoneStatus;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
