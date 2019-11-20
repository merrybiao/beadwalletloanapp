package com.waterelephant.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name ="bw_metlife_insurance_apply_record")
public class BwMetlifeInsuranceApplyRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderNo;//工单编号
	private String loanDate;//放款日期
	private Integer loanDays;//放款天数
	private Integer loanMonths;//放款月数
	private Double loanAmount;//放款金额
	
	private String insuredName;//姓名
	private String insuredGender;//性别
	private String insuredMobile;//手机号
	private String insuredIdType;//证件类型 默认0 身份证
	private String insuredIdNo;//身份证号
	private String insuredBirthday;//生日
	
	private String isSick;//有无长期病假、长期接受门诊治疗或住院治疗？
	private String isAbsenteeism;//近一年内有无因患病而缺勤达15天以上？
	private String isSeriousIllness;//现在或过去有无罹患恶性肿瘤、心肌梗塞、白血病、肝硬化、肾功能衰竭、再生障碍性贫血？
	private String isOccupationalDisease;//职业病、先天性或遗传性疾病、帕金森氏病、精神病、癫痫病、法定传染病、艾滋病或艾滋病病毒携带者？
	private String isDisability;//有无身体残障？
	private String isPregnancy;//有无已怀孕？
	private String goAbroad;//有无因工作原因需要前往国外？
	
	private String productNo;//产品名/记录来源
	private String message;//返回描述
	private Integer applyState;//申请状态 1:解析失败 2:申请失败 3 申请成功 
	private Date createTime;
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public Integer getLoanDays() {
		return loanDays;
	}
	public void setLoanDays(Integer loanDays) {
		this.loanDays = loanDays;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredGender() {
		return insuredGender;
	}
	public void setInsuredGender(String insuredGender) {
		this.insuredGender = insuredGender;
	}
	public String getInsuredMobile() {
		return insuredMobile;
	}
	public void setInsuredMobile(String insuredMobile) {
		this.insuredMobile = insuredMobile;
	}
	public String getInsuredIdType() {
		return insuredIdType;
	}
	public void setInsuredIdType(String insuredIdType) {
		this.insuredIdType = insuredIdType;
	}
	public String getInsuredIdNo() {
		return insuredIdNo;
	}
	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}
	public String getInsuredBirthday() {
		return insuredBirthday;
	}
	public void setInsuredBirthday(String insuredBirthday) {
		this.insuredBirthday = insuredBirthday;
	}
	public String getIsSick() {
		return isSick;
	}
	public void setIsSick(String isSick) {
		this.isSick = isSick;
	}
	public String getIsAbsenteeism() {
		return isAbsenteeism;
	}
	public void setIsAbsenteeism(String isAbsenteeism) {
		this.isAbsenteeism = isAbsenteeism;
	}
	public String getIsSeriousIllness() {
		return isSeriousIllness;
	}
	public void setIsSeriousIllness(String isSeriousIllness) {
		this.isSeriousIllness = isSeriousIllness;
	}
	public String getIsOccupationalDisease() {
		return isOccupationalDisease;
	}
	public void setIsOccupationalDisease(String isOccupationalDisease) {
		this.isOccupationalDisease = isOccupationalDisease;
	}
	public String getIsDisability() {
		return isDisability;
	}
	public void setIsDisability(String isDisability) {
		this.isDisability = isDisability;
	}
	public String getIsPregnancy() {
		return isPregnancy;
	}
	public void setIsPregnancy(String isPregnancy) {
		this.isPregnancy = isPregnancy;
	}
	public String getGoAbroad() {
		return goAbroad;
	}
	public void setGoAbroad(String goAbroad) {
		this.goAbroad = goAbroad;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getApplyState() {
		return applyState;
	}
	public void setApplyState(Integer applyState) {
		this.applyState = applyState;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}
}

