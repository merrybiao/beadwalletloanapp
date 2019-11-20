package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_tb_user")
public class BwTbUser implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bindingWeiboNickname;// 绑定的微博昵称
    private String tianmaoVipLevel;// 天猫会员等级
    private String address;// 详细地址
    private String tianmaoAccount;// 天猫账户
    private String rateSummary;// 好评率
    private Integer tianmaoGrade;// 天猫积分
    private String tianmaoCreditLevel;// 天猫信誉评级
    private String bindingPhone;// 绑定手机
    private String loginName;// 淘宝帐户名
    private Long borrowerId;// 借款人ID
    private String bindingWeiboAccount;// 绑定的微博帐号
    private String accountName;// 淘宝会员名
    private String name;// 真实姓名
    private String phoneNumber;// 固定电话
    private String vipLevel;// 会员等级
    private Date createDate;
    private Long orderId;// 订单ID
    private Integer buyerCredit;// 淘宝买家级别
    private String email;// 登陆邮箱
    private String authentication;// 是否已完成身份认证
    private String divisionCode;// 行政区域编码

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBindingWeiboNickname() {
        return this.bindingWeiboNickname;
    }

    public void setBindingWeiboNickname(String bindingWeiboNickname) {
        this.bindingWeiboNickname = bindingWeiboNickname;
    }

    public String getTianmaoVipLevel() {
        return this.tianmaoVipLevel;
    }

    public void setTianmaoVipLevel(String tianmaoVipLevel) {
        this.tianmaoVipLevel = tianmaoVipLevel;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTianmaoAccount() {
        return this.tianmaoAccount;
    }

    public void setTianmaoAccount(String tianmaoAccount) {
        this.tianmaoAccount = tianmaoAccount;
    }

    public String getRateSummary() {
        return this.rateSummary;
    }

    public void setRateSummary(String rateSummary) {
        this.rateSummary = rateSummary;
    }

    public Integer getTianmaoGrade() {
        return this.tianmaoGrade;
    }

    public void setTianmaoGrade(Integer tianmaoGrade) {
        this.tianmaoGrade = tianmaoGrade;
    }

    public String getTianmaoCreditLevel() {
        return this.tianmaoCreditLevel;
    }

    public void setTianmaoCreditLevel(String tianmaoCreditLevel) {
        this.tianmaoCreditLevel = tianmaoCreditLevel;
    }

    public String getBindingPhone() {
        return this.bindingPhone;
    }

    public void setBindingPhone(String bindingPhone) {
        this.bindingPhone = bindingPhone;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBindingWeiboAccount() {
        return this.bindingWeiboAccount;
    }

    public void setBindingWeiboAccount(String bindingWeiboAccount) {
        this.bindingWeiboAccount = bindingWeiboAccount;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVipLevel() {
        return this.vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBuyerCredit() {
        return this.buyerCredit;
    }

    public void setBuyerCredit(Integer buyerCredit) {
        this.buyerCredit = buyerCredit;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getDivisionCode() {
        return this.divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

}
