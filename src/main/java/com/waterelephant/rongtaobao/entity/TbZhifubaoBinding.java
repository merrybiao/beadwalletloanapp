package com.waterelephant.rongtaobao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 实体类
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:19
 */
@Table(name="bw_tb_zhifubao_binding")
@JsonIgnoreProperties(ignoreUnknown=true)
public class TbZhifubaoBinding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //
    private Long borrowerId; // 水象ID
    private Long orderId; // 订单ID
    private String balance; // 支付宝余额
    @JSONField(name="total_profit")
    private String totalProfit; // 余额宝历史累计收益
    @JSONField(name="total_quotient")
    private String totalQuotient; // 余额宝账户余额
    @JSONField(name="huabei_credit_amount")
    private String huabeiCreditAmount; // 花呗可用额度
    @JSONField(name="huabei_total_credit_amount")
    private String huabeiTotalCreditAmount; // 花呗总额度
    @JSONField(name="zhifubao_account")
    private String zhifubaoAccount; // 支付宝账户
    @JSONField(name="binding_phone")
    private String bindingPhone; // 绑定的手机号
    @JSONField(name="account_type")
    private String accountType; // 支付宝账户类型
    @JSONField(name="verified_name")
    private String verifiedName; // 支付宝实名认证的姓名
    @JSONField(name="verified_id_card")
    private String verifiedIdCard; // 支付宝实名认证的身份证号
    private Date createDate; //

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }

    public void setBorrowerId(Long borrowerId){
        this.borrowerId=borrowerId;
    }
    public Long getBorrowerId(){
        return this.borrowerId;
    }

    public void setOrderId(Long orderId){
        this.orderId=orderId;
    }
    public Long getOrderId(){
        return this.orderId;
    }

    public void setBalance(String balance){
        this.balance=balance;
    }
    public String getBalance(){
        return this.balance;
    }

    public void setTotalProfit(String totalProfit){
        this.totalProfit=totalProfit;
    }
    public String getTotalProfit(){
        return this.totalProfit;
    }

    public void setTotalQuotient(String totalQuotient){
        this.totalQuotient=totalQuotient;
    }
    public String getTotalQuotient(){
        return this.totalQuotient;
    }

    public void setHuabeiCreditAmount(String huabeiCreditAmount){
        this.huabeiCreditAmount=huabeiCreditAmount;
    }
    public String getHuabeiCreditAmount(){
        return this.huabeiCreditAmount;
    }

    public void setHuabeiTotalCreditAmount(String huabeiTotalCreditAmount){
        this.huabeiTotalCreditAmount=huabeiTotalCreditAmount;
    }
    public String getHuabeiTotalCreditAmount(){
        return this.huabeiTotalCreditAmount;
    }

    public void setZhifubaoAccount(String zhifubaoAccount){
        this.zhifubaoAccount=zhifubaoAccount;
    }
    public String getZhifubaoAccount(){
        return this.zhifubaoAccount;
    }

    public void setBindingPhone(String bindingPhone){
        this.bindingPhone=bindingPhone;
    }
    public String getBindingPhone(){
        return this.bindingPhone;
    }

    public void setAccountType(String accountType){
        this.accountType=accountType;
    }
    public String getAccountType(){
        return this.accountType;
    }

    public void setVerifiedName(String verifiedName){
        this.verifiedName=verifiedName;
    }
    public String getVerifiedName(){
        return this.verifiedName;
    }

    public void setVerifiedIdCard(String verifiedIdCard){
        this.verifiedIdCard=verifiedIdCard;
    }
    public String getVerifiedIdCard(){
        return this.verifiedIdCard;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TbZhifubaoBinding[");
        sb.append("id=");
        sb.append(id);
        sb.append(",borrowerId=");
        sb.append(borrowerId);
        sb.append(",orderId=");
        sb.append(orderId);
        sb.append(",balance=");
        sb.append(balance);
        sb.append(",totalProfit=");
        sb.append(totalProfit);
        sb.append(",totalQuotient=");
        sb.append(totalQuotient);
        sb.append(",huabeiCreditAmount=");
        sb.append(huabeiCreditAmount);
        sb.append(",huabeiTotalCreditAmount=");
        sb.append(huabeiTotalCreditAmount);
        sb.append(",zhifubaoAccount=");
        sb.append(zhifubaoAccount);
        sb.append(",bindingPhone=");
        sb.append(bindingPhone);
        sb.append(",accountType=");
        sb.append(accountType);
        sb.append(",verifiedName=");
        sb.append(verifiedName);
        sb.append(",verifiedIdCard=");
        sb.append(verifiedIdCard);
        sb.append("]");
        return sb.toString();
    }
}
