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
 * @create_date 2017-6-8 9:36:18
 */
@Table(name="bw_tb_user")
@JsonIgnoreProperties(ignoreUnknown=true)
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //
    private Long borrowerId; // 借款人ID
    private Long orderId; // 订单ID

    @JSONField(name="login_name")
    private String loginName; // 淘宝帐户名
    @JSONField(name="vip_level")
    private String vipLevel; // 会员等级
    @JSONField(name="buyer_credit")
    private Integer buyerCredit; // 淘宝买家级别
    @JSONField(name="rate_summary")
    private String rateSummary; // 好评率
    @JSONField(name="account_name")
    private String accountName; // 淘宝会员名
    @JSONField(name="email")
    private String email; // 登陆邮箱
    @JSONField(name="binding_phone")
    private String bindingPhone; // 绑定手机
    @JSONField(name="authentication")
    private String authentication; // 是否已完成身份认证
    @JSONField(name="binding_weibo_account")
    private String bindingWeiboAccount; // 绑定的微博帐号
    @JSONField(name="binding_weibo_nickname")
    private String bindingWeiboNickname; // 绑定的微博昵称
    @JSONField(name="name")
    private String name; // 真实姓名
    @JSONField(name="division_code")
    private String divisionCode; // 行政区域编码
    @JSONField(name="address")
    private String address; // 详细地址
    @JSONField(name="tianmao_account")
    private String tianmaoAccount; // 天猫账户
    @JSONField(name="tianmao_grade")
    private Integer tianmaoGrade; // 天猫积分
    @JSONField(name="tianmao_vip_level")
    private String tianmaoVipLevel; // 天猫会员等级
    @JSONField(name="tianmao_credit_level")
    private String tianmaoCreditLevel; // 天猫信誉评级
    @JSONField(name="phone_number")
    private String phoneNumber; // 固定电话
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

    public void setLoginName(String loginName){
        this.loginName=loginName;
    }
    public String getLoginName(){
        return this.loginName;
    }

    public void setVipLevel(String vipLevel){
        this.vipLevel=vipLevel;
    }
    public String getVipLevel(){
        return this.vipLevel;
    }

    public void setBuyerCredit(Integer buyerCredit){
        this.buyerCredit=buyerCredit;
    }
    public Integer getBuyerCredit(){
        return this.buyerCredit;
    }

    public void setRateSummary(String rateSummary){
        this.rateSummary=rateSummary;
    }
    public String getRateSummary(){
        return this.rateSummary;
    }

    public void setAccountName(String accountName){
        this.accountName=accountName;
    }
    public String getAccountName(){
        return this.accountName;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setBindingPhone(String bindingPhone){
        this.bindingPhone=bindingPhone;
    }
    public String getBindingPhone(){
        return this.bindingPhone;
    }

    public void setAuthentication(String authentication){
        this.authentication=authentication;
    }
    public String getAuthentication(){
        return this.authentication;
    }

    public void setBindingWeiboAccount(String bindingWeiboAccount){
        this.bindingWeiboAccount=bindingWeiboAccount;
    }
    public String getBindingWeiboAccount(){
        return this.bindingWeiboAccount;
    }

    public void setBindingWeiboNickname(String bindingWeiboNickname){
        this.bindingWeiboNickname=bindingWeiboNickname;
    }
    public String getBindingWeiboNickname(){
        return this.bindingWeiboNickname;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setDivisionCode(String divisionCode){
        this.divisionCode=divisionCode;
    }
    public String getDivisionCode(){
        return this.divisionCode;
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return this.address;
    }

    public void setTianmaoAccount(String tianmaoAccount){
        this.tianmaoAccount=tianmaoAccount;
    }
    public String getTianmaoAccount(){
        return this.tianmaoAccount;
    }

    public void setTianmaoGrade(Integer tianmaoGrade){
        this.tianmaoGrade=tianmaoGrade;
    }
    public Integer getTianmaoGrade(){
        return this.tianmaoGrade;
    }

    public void setTianmaoVipLevel(String tianmaoVipLevel){
        this.tianmaoVipLevel=tianmaoVipLevel;
    }
    public String getTianmaoVipLevel(){
        return this.tianmaoVipLevel;
    }

    public void setTianmaoCreditLevel(String tianmaoCreditLevel){
        this.tianmaoCreditLevel=tianmaoCreditLevel;
    }
    public String getTianmaoCreditLevel(){
        return this.tianmaoCreditLevel;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setCreateDate(Date createDate){
        this.createDate=createDate;
    }
    public Date getCreateDate(){
        return this.createDate;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TbUser[");
        sb.append("id=");
        sb.append(id);
        sb.append(",borrowerId=");
        sb.append(borrowerId);
        sb.append(",orderId=");
        sb.append(orderId);
        sb.append(",loginName=");
        sb.append(loginName);
        sb.append(",vipLevel=");
        sb.append(vipLevel);
        sb.append(",buyerCredit=");
        sb.append(buyerCredit);
        sb.append(",rateSummary=");
        sb.append(rateSummary);
        sb.append(",accountName=");
        sb.append(accountName);
        sb.append(",email=");
        sb.append(email);
        sb.append(",bindingPhone=");
        sb.append(bindingPhone);
        sb.append(",authentication=");
        sb.append(authentication);
        sb.append(",bindingWeiboAccount=");
        sb.append(bindingWeiboAccount);
        sb.append(",bindingWeiboNickname=");
        sb.append(bindingWeiboNickname);
        sb.append(",name=");
        sb.append(name);
        sb.append(",divisionCode=");
        sb.append(divisionCode);
        sb.append(",address=");
        sb.append(address);
        sb.append(",tianmaoAccount=");
        sb.append(tianmaoAccount);
        sb.append(",tianmaoGrade=");
        sb.append(tianmaoGrade);
        sb.append(",tianmaoVipLevel=");
        sb.append(tianmaoVipLevel);
        sb.append(",tianmaoCreditLevel=");
        sb.append(tianmaoCreditLevel);
        sb.append(",phoneNumber=");
        sb.append(phoneNumber);
        sb.append(",createDate=");
        sb.append(createDate);
        sb.append("]");
        return sb.toString();
    }
}
