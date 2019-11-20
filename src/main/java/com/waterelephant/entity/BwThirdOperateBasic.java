package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 渠道运营商基础数据
 *
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/6
 * @since JDK 1.8
 */
@Table(name = "bw_third_operate_basic")
public class BwThirdOperateBasic {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 我方订单号 */
    private Long orderId;
    /** 渠道来源 */
    private Integer channel;
    /** 号码类型 */
    private String userSource;
    /** 身份证号 */
    private String idCard;
    /** 注册该号码所填写的地址 */
    private String addr;
    /** 用户姓名 */
    private String realName;
    /** 当前账户余额 */
    private String phoneRemain;
    /** 电话号码 */
    private String phone;
    /** 注册时间 */
    private Date regTime;
    /** 查询ID */
    private String searchId;
    /** 用户积分该手机号至信息采集时的积分情况 */
    private String score;
    /** 其他联系人号码官网展示的该手机号其他联系人号码 */
    private String contactPhone;
    /** 用户星级官网展示的用户星级 */
    private String starLevel;
    /** 用户实名状态0：未知1：已实名/已登记/已审核/已认证2：未实名/未登记/未认证 */
    private String authentication;
    /** 客户状态0：未知1：正常2：停机3：单向停机（单向停机指的是可以收短信电话）4：预销户5：销户6：过户7：改号8：此号码不存在9：挂失/冻结 */
    private String phoneStatus;
    /** 套餐名称该手机号所办理套餐 */
    private String packageName;
    /** 备注1 */
    private String mark1;
    /** 备注2 */
    private String mark2;
    /** 备注3 */
    private String mark3;

    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
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

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
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
