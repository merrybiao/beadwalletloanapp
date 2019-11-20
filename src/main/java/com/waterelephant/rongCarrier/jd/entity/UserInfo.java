package com.waterelephant.rongCarrier.jd.entity;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_jd_user_info")
public class UserInfo {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;
	private long borrowerId;
	private String loginName;
	private String userName;
	private String setLoginName;
	private String nickname;
	private String sex;
	private Date birthday;
	private String hobbies;
	private String email;
	private String realName;
	private String marriage;
	private String income;
	private String idCard;
	private String education;
	private String industry;
	private String isQqBound;
	private String isWechatBound;
	private String accountGrade;
	private String accountType;
	private Double btCreditPoint;
	private Double btOverdraft;
	private Double btQuota;
	private Date authTime;
	private String bindingPhone;
	private String authChannel;
	private String financialService;
	private Date createTime;
	private Date updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(long borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSetLoginName() {
		return setLoginName;
	}
	public void setSetLoginName(String setLoginName) {
		this.setLoginName = setLoginName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getIsQqBound() {
		return isQqBound;
	}
	public void setIsQqBound(String isQqBound) {
		this.isQqBound = isQqBound;
	}
	public String getIsWechatBound() {
		return isWechatBound;
	}
	public void setIsWechatBound(String isWechatBound) {
		this.isWechatBound = isWechatBound;
	}
	public String getAccountGrade() {
		return accountGrade;
	}
	public void setAccountGrade(String accountGrade) {
		this.accountGrade = accountGrade;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Double getBtCreditPoint() {
		return btCreditPoint;
	}
	public void setBtCreditPoint(Double btCreditPoint) {
		this.btCreditPoint = btCreditPoint;
	}
	public Double getBtOverdraft() {
		return btOverdraft;
	}
	public void setBtOverdraft(Double btOverdraft) {
		this.btOverdraft = btOverdraft;
	}
	public Double getBtQuota() {
		return btQuota;
	}
	public void setBtQuota(Double btQuota) {
		this.btQuota = btQuota;
	}
	public Date getAuthTime() {
		return authTime;
	}
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
	public String getBindingPhone() {
		return bindingPhone;
	}
	public void setBindingPhone(String bindingPhone) {
		this.bindingPhone = bindingPhone;
	}
	public String getAuthChannel() {
		return authChannel;
	}
	public void setAuthChannel(String authChannel) {
		this.authChannel = authChannel;
	}
	public String getFinancialService() {
		return financialService;
	}
	public void setFinancialService(String financialService) {
		this.financialService = financialService;
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
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", borrowerId=" + borrowerId
				+ ", loginName=" + loginName + ", userName=" + userName
				+ ", setLoginName=" + setLoginName + ", nickname=" + nickname
				+ ", sex=" + sex + ", birthday=" + birthday + ", hobbies="
				+ hobbies + ", email=" + email + ", realName=" + realName
				+ ", marriage=" + marriage + ", income=" + income + ", idCard="
				+ idCard + ", education=" + education + ", industry="
				+ industry + ", isQqBound=" + isQqBound + ", isWechatBound="
				+ isWechatBound + ", accountGrade=" + accountGrade
				+ ", accountType=" + accountType + ", btCreditPoint="
				+ btCreditPoint + ", btOverdraft=" + btOverdraft + ", btQuota="
				+ btQuota + ", authTime=" + authTime + ", bindingPhone="
				+ bindingPhone + ", authChannel=" + authChannel
				+ ", financialService=" + financialService + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
