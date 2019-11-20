package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_tb_zhifubao_binding")
public class BwTbZhifubaoBinding implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountType;// 支付宝账户类型
    private String zhifubaoAccount;// 支付宝账户
    private String huabeiCreditAmount;// 花呗可用额度
    private String huabeiTotalCreditAmount;// 花呗总额度
    private String bindingPhone;// 绑定的手机号
    private Long borrowerId;// 水象ID
    private String balance;// 支付宝余额
    private String totalQuotient;// 余额宝账户余额
    private String verifiedName;// 支付宝实名认证的姓名
    private String verifiedIdCard;// 支付宝实名认证的身份证号
    private String totalProfit;// 余额宝历史累计收益
    private Date createDate;
    private Long orderId;// 订单ID

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getZhifubaoAccount() {
        return this.zhifubaoAccount;
    }

    public void setZhifubaoAccount(String zhifubaoAccount) {
        this.zhifubaoAccount = zhifubaoAccount;
    }

    public String getHuabeiCreditAmount() {
        return this.huabeiCreditAmount;
    }

    public void setHuabeiCreditAmount(String huabeiCreditAmount) {
        this.huabeiCreditAmount = huabeiCreditAmount;
    }

    public String getHuabeiTotalCreditAmount() {
        return this.huabeiTotalCreditAmount;
    }

    public void setHuabeiTotalCreditAmount(String huabeiTotalCreditAmount) {
        this.huabeiTotalCreditAmount = huabeiTotalCreditAmount;
    }

    public String getBindingPhone() {
        return this.bindingPhone;
    }

    public void setBindingPhone(String bindingPhone) {
        this.bindingPhone = bindingPhone;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTotalQuotient() {
        return this.totalQuotient;
    }

    public void setTotalQuotient(String totalQuotient) {
        this.totalQuotient = totalQuotient;
    }

    public String getVerifiedName() {
        return this.verifiedName;
    }

    public void setVerifiedName(String verifiedName) {
        this.verifiedName = verifiedName;
    }

    public String getVerifiedIdCard() {
        return this.verifiedIdCard;
    }

    public void setVerifiedIdCard(String verifiedIdCard) {
        this.verifiedIdCard = verifiedIdCard;
    }

    public String getTotalProfit() {
        return this.totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
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

}
