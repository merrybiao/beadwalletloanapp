package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_metlife_insurance_detail")
public class BwMetlifeInsuranceDetail implements Serializable {

	private static final long serialVersionUID = 3161960277901860610L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;//流水号
	private String batchNo;//批次号
	private String requestType;//请求类型
	private String sendTime;//发送时间
	private String grpContPlancode;//保险套餐编号
	private String trimDate;//整理日期
	private String grantLoansDate;//借款日期
	private String grantLoansEndDate;//到期还款日期
	private String loanContractCode;//借款合同编号
	private Double loanContractAmount;//借款合同金额
	private Integer polTermMonths;//保险期限月数  借贷机构为借款人投保的保障期限按月计算的月数，为整数
	private Double polTermYears;//保险期限年数 借贷机构为借款人投保的保障期限按月计算的年数，目前只支持的年数为：1、2、3、1.5，分别表示1年、2年、3年、1.5年
	
	private Double rate;//费率
	private Double amount;//保险金额
	private Double premium;//保费
	
	private String insuredName;//被保人姓名
	private Integer insuredIdType=0;//被保人证件类型 0 身份证 目前只支持身份证
	private String insuredIdNo;//被保人证件号
	private Integer insuredGender;//被保人性别 0男 1女
	private String insuredBirthday;//被保人出生年月
	private String insuredMobile;//被保人手机号
	
	private String isSick;//有无长期病假、长期接受门诊治疗或住院治疗？
	private String isAbsenteeism;//近一年内有无因患病而缺勤达15天以上？
	private String isSeriousIllness;//现在或过去有无罹患恶性肿瘤、心肌梗塞、白血病、肝硬化、肾功能衰竭、再生障碍性贫血？
	private String isOccupationalDisease;//职业病、先天性或遗传性疾病、帕金森氏病、精神病、癫痫病、法定传染病、艾滋病或艾滋病病毒携带者？
	private String isDisability;//有无身体残障？
	private String isPregnancy;//有无已怀孕？
	private String goAbroad;//有无因工作原因需要前往国外？
	
	private Date createTime;//创建时间
	private String policyNumber;//保险单号
	private Date updateTime;//更新时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getGrpContPlancode() {
		return grpContPlancode;
	}
	public void setGrpContPlancode(String grpContPlancode) {
		this.grpContPlancode = grpContPlancode;
	}
	public String getTrimDate() {
		return trimDate;
	}
	public void setTrimDate(String trimDate) {
		this.trimDate = trimDate;
	}
	public String getGrantLoansDate() {
		return grantLoansDate;
	}
	public void setGrantLoansDate(String grantLoansDate) {
		this.grantLoansDate = grantLoansDate;
	}
	public String getGrantLoansEndDate() {
		return grantLoansEndDate;
	}
	public void setGrantLoansEndDate(String grantLoansEndDate) {
		this.grantLoansEndDate = grantLoansEndDate;
	}
	public String getLoanContractCode() {
		return loanContractCode;
	}
	public void setLoanContractCode(String loanContractCode) {
		this.loanContractCode = loanContractCode;
	}
	public Double getLoanContractAmount() {
		return loanContractAmount;
	}
	public void setLoanContractAmount(Double loanContractAmount) {
		this.loanContractAmount = loanContractAmount;
	}
	public Integer getPolTermMonths() {
		return polTermMonths;
	}
	public void setPolTermMonths(Integer polTermMonths) {
		this.polTermMonths = polTermMonths;
	}
	public Double getPolTermYears() {
		return polTermYears;
	}
	public void setPolTermYears(Double polTermYears) {
		this.polTermYears = polTermYears;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public Integer getInsuredIdType() {
		return insuredIdType;
	}
	public void setInsuredIdType(Integer insuredIdType) {
		this.insuredIdType = insuredIdType;
	}
	public String getInsuredIdNo() {
		return insuredIdNo;
	}
	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}
	public Integer getInsuredGender() {
		return insuredGender;
	}
	public void setInsuredGender(Integer insuredGender) {
		this.insuredGender = insuredGender;
	}
	public String getInsuredBirthday() {
		return insuredBirthday;
	}
	public void setInsuredBirthday(String insuredBirthday) {
		this.insuredBirthday = insuredBirthday;
	}
	public String getInsuredMobile() {
		return insuredMobile;
	}
	public void setInsuredMobile(String insuredMobile) {
		this.insuredMobile = insuredMobile;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
