package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_operate_basic")
public class BwOperateBasic implements Serializable {

    private static final long serialVersionUID = -8085306290585078978L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userSource;
    private String idCard;
    private String addr;
    private String realName;
    private String phoneRemain;
    private String phone;
    private Long borrowerId;
    private Date regTime;
    private String searchId;
    private Date createTime;
    private Date updateTime;

    // 新增字段(code360)
    private String score;// 用户积分 该手机号至信息采集时的积分情况
    private String contactPhone;// 其他联系人号码 官网展示的该手机号其他联系人号码
    private String starLevel;// 用户星级 官网展示的用户星级
    private String authentication;// 用户实名状态 0：未知 1：已实名/已登记/已审核/已认证 2：未实名/未登记/未认证
    private String phoneStatus;// 客户状态 0：未知1：正常2：停机3：单向停机（单向停机指的是可以收短信电话）4：预销户5：销户6：过户7：改号8：此号码不存在9：挂失/冻结
    private String packageName;// 套餐名称 该手机号所办理套餐

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
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
