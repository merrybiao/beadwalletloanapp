package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_jd_user_info")
public class BwJdUserInfo implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date birthday;// 生日
    private String income;// 月收入
    private String accountType;// 会员类型
    private String education;// 教育程度
    private String userName;// 用户名
    private String isWechatBound;// 是否绑定微信账号
    private String idCard;// 身份证
    private String realName;// 真实姓名
    private String industry;// 所在行业
    private Double btOverdraft;// 白条欠款
    private Double btCreditPoint;// 白条信用分
    private String bindingPhone;// 绑定手机
    private Date updateTime;// 数据更新时间
    private Long borrowerId;// 水象用户id
    private String marriage;// 婚姻状况
    private Date authTime;// 白条认证时间
    private String nickname;// 昵称
    private String financialService;// 金融服务
    private Double btQuota;// 白条额度
    private String setLoginName;// 网页中用户设置的登录名
    private String email;// 邮箱
    private Date createTime;// 数据创建时间
    private String sex;// 性别
    private String authChannel;// 认证渠道
    private String loginName;// 登陆名
    private String hobbies;// 兴趣爱好
    private String accountGrade;// 会员级别
    private String isQqBound;// 是否绑定QQ账号

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIncome() {
        return this.income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsWechatBound() {
        return this.isWechatBound;
    }

    public void setIsWechatBound(String isWechatBound) {
        this.isWechatBound = isWechatBound;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Double getBtOverdraft() {
        return this.btOverdraft;
    }

    public void setBtOverdraft(Double btOverdraft) {
        this.btOverdraft = btOverdraft;
    }

    public Double getBtCreditPoint() {
        return this.btCreditPoint;
    }

    public void setBtCreditPoint(Double btCreditPoint) {
        this.btCreditPoint = btCreditPoint;
    }

    public String getBindingPhone() {
        return this.bindingPhone;
    }

    public void setBindingPhone(String bindingPhone) {
        this.bindingPhone = bindingPhone;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getMarriage() {
        return this.marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public Date getAuthTime() {
        return this.authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFinancialService() {
        return this.financialService;
    }

    public void setFinancialService(String financialService) {
        this.financialService = financialService;
    }

    public Double getBtQuota() {
        return this.btQuota;
    }

    public void setBtQuota(Double btQuota) {
        this.btQuota = btQuota;
    }

    public String getSetLoginName() {
        return this.setLoginName;
    }

    public void setSetLoginName(String setLoginName) {
        this.setLoginName = setLoginName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAuthChannel() {
        return this.authChannel;
    }

    public void setAuthChannel(String authChannel) {
        this.authChannel = authChannel;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getHobbies() {
        return this.hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAccountGrade() {
        return this.accountGrade;
    }

    public void setAccountGrade(String accountGrade) {
        this.accountGrade = accountGrade;
    }

    public String getIsQqBound() {
        return this.isQqBound;
    }

    public void setIsQqBound(String isQqBound) {
        this.isQqBound = isQqBound;
    }

}
