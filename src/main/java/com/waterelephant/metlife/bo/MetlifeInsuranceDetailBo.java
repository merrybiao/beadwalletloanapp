package com.waterelephant.metlife.bo;

import java.io.Serializable;
import java.util.Date;

public class MetlifeInsuranceDetailBo implements Serializable {

	
	private static final long serialVersionUID = -5383870633125668794L;
	private String uuid;//流水号
	private String batchNo;//批次号
	private String sendTime;//发送时间
	private String grpContPlancode;//保险套餐编号
	private String trimDate;//整理日期
	private String grantLoansDate;//借款日期
	private String grantLoansEndDate;//到期还款日期
	private String loanContractCode;//借款合同编号
	private Double loanContractAmount;//借款合同金额
	
	private Double rate;//费率
	private Double amount;//保险金额
	private Double premium;//保费
	
	private String insuredName;//被保人姓名
	private Integer insuredIdType=0;//被保人证件类型 0 身份证 目前只支持身份证
	private String insuredIdNo;//被保人证件号
	private Integer insuredGender;//被保人性别 0男 1女
	private String insuredBirthday;//被保人出生年月
	private String insuredMobile;//被保人手机号
	
	private Character isSick;//有无长期病假、长期接受门诊治疗或住院治疗？
	private Character isAbsenteeism;//近一年内有无因患病而缺勤达15天以上？
	private Character isSeriousIllness;//现在或过去有无罹患恶性肿瘤、心肌梗塞、白血病、肝硬化、肾功能衰竭、再生障碍性贫血？
	private Character isOccupationalDisease;//职业病、先天性或遗传性疾病、帕金森氏病、精神病、癫痫病、法定传染病、艾滋病或艾滋病病毒携带者？
	private Character isDisability;//有无身体残障？
	private Character isPregnancy;//有无已怀孕？
	private Character goAbroad;//有无因工作原因需要前往国外？
	private Integer insureState;
	private String policyNumber;//保险单号
	private Date updateTime;//更新时间
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
	public int getInsuredIdType() {
		return insuredIdType;
	}
	public void setInsuredIdType(int insuredIdType) {
		this.insuredIdType = insuredIdType;
	}
	public String getInsuredIdNo() {
		return insuredIdNo;
	}
	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}
	public int getInsuredGender() {
		return insuredGender;
	}
	public void setInsuredGender(int insuredGender) {
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
	public char getIsSick() {
		return isSick;
	}
	public void setIsSick(char isSick) {
		this.isSick = isSick;
	}
	public char getIsAbsenteeism() {
		return isAbsenteeism;
	}
	public void setIsAbsenteeism(char isAbsenteeism) {
		this.isAbsenteeism = isAbsenteeism;
	}
	public char getIsSeriousIllness() {
		return isSeriousIllness;
	}
	public void setIsSeriousIllness(char isSeriousIllness) {
		this.isSeriousIllness = isSeriousIllness;
	}
	public char getIsOccupationalDisease() {
		return isOccupationalDisease;
	}
	public void setIsOccupationalDisease(char isOccupationalDisease) {
		this.isOccupationalDisease = isOccupationalDisease;
	}
	public char getIsDisability() {
		return isDisability;
	}
	public void setIsDisability(char isDisability) {
		this.isDisability = isDisability;
	}
	public char getIsPregnancy() {
		return isPregnancy;
	}
	public void setIsPregnancy(char isPregnancy) {
		this.isPregnancy = isPregnancy;
	}
	public char getGoAbroad() {
		return goAbroad;
	}
	public void setGoAbroad(char goAbroad) {
		this.goAbroad = goAbroad;
	}
	public Integer getInsureState() {
		return insureState;
	}
	public void setInsureState(Integer insureState) {
		this.insureState = insureState;
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
