package com.waterelephant.operatorData.xg.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="bw_xg_overall")
public class XgOperatorOverall implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009235852440808868L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrowerId;
    private String score;
    private String searchId;
    private String reportTime;
    private Integer userType;
    private String userName;
    private String idCard;
    private String phone;
    private String emergencyName1;
    private String emergencyRelation1;
    private String emergencyPhone1;
    private String emergencyName2;
    private String emergencyRelation2;
    private String emergencyPhone2;
    private String operator;
    private String operatorZh;
    private String phoneLocation;
    private Integer idCardCheck;
    private Integer nameCheck;
    private String basicInfoIdCard;
    private String basicInfoRealName;
    private Double aveMonthlyConsumption;
    private Double currentBalance;
    private String regTime;
    private Integer ifCallEmergency1;
    private Integer ifCallEmergency2;
    private Integer blacklistCnt;
    private Integer searchedCnt;
    private Integer loanRecordCnt;
    private String contactDistributionLocation;
    private Double contactDistributionRatio;
    private Integer bothCallCnt;
    private Double nightActivityRatio;
    private Double nightMsgRatio;
    private String activeDaysStartDay;
    private Integer activeDaysTotalDays;
    private String activeDaysEndDay;
    private Integer activeDaysStopDays;
    private String activeDaysStopDaysDetail;
    private String activeDaysStop3DaysDetail;
    private Integer activeDaysStop3Days;
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmergencyName1() {
        return emergencyName1;
    }

    public void setEmergencyName1(String emergencyName1) {
        this.emergencyName1 = emergencyName1;
    }

    public String getEmergencyRelation1() {
        return emergencyRelation1;
    }

    public void setEmergencyRelation1(String emergencyRelation1) {
        this.emergencyRelation1 = emergencyRelation1;
    }

    public String getEmergencyPhone1() {
        return emergencyPhone1;
    }

    public void setEmergencyPhone1(String emergencyPhone1) {
        this.emergencyPhone1 = emergencyPhone1;
    }

    public String getEmergencyName2() {
        return emergencyName2;
    }

    public void setEmergencyName2(String emergencyName2) {
        this.emergencyName2 = emergencyName2;
    }

    public String getEmergencyRelation2() {
        return emergencyRelation2;
    }

    public void setEmergencyRelation2(String emergencyRelation2) {
        this.emergencyRelation2 = emergencyRelation2;
    }

    public String getEmergencyPhone2() {
        return emergencyPhone2;
    }

    public void setEmergencyPhone2(String emergencyPhone2) {
        this.emergencyPhone2 = emergencyPhone2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorZh() {
        return operatorZh;
    }

    public void setOperatorZh(String operatorZh) {
        this.operatorZh = operatorZh;
    }

    public String getPhoneLocation() {
        return phoneLocation;
    }

    public void setPhoneLocation(String phoneLocation) {
        this.phoneLocation = phoneLocation;
    }

    public Integer getIdCardCheck() {
        return idCardCheck;
    }

    public void setIdCardCheck(Integer idCardCheck) {
        this.idCardCheck = idCardCheck;
    }

    public Integer getNameCheck() {
        return nameCheck;
    }

    public void setNameCheck(Integer nameCheck) {
        this.nameCheck = nameCheck;
    }

    public String getBasicInfoIdCard() {
        return basicInfoIdCard;
    }

    public void setBasicInfoIdCard(String basicInfoIdCard) {
        this.basicInfoIdCard = basicInfoIdCard;
    }

    public String getBasicInfoRealName() {
        return basicInfoRealName;
    }

    public void setBasicInfoRealName(String basicInfoRealName) {
        this.basicInfoRealName = basicInfoRealName;
    }

    public Double getAveMonthlyConsumption() {
        return aveMonthlyConsumption;
    }

    public void setAveMonthlyConsumption(Double aveMonthlyConsumption) {
        this.aveMonthlyConsumption = aveMonthlyConsumption;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Integer getIfCallEmergency1() {
        return ifCallEmergency1;
    }

    public void setIfCallEmergency1(Integer ifCallEmergency1) {
        this.ifCallEmergency1 = ifCallEmergency1;
    }

    public Integer getIfCallEmergency2() {
        return ifCallEmergency2;
    }

    public void setIfCallEmergency2(Integer ifCallEmergency2) {
        this.ifCallEmergency2 = ifCallEmergency2;
    }

    public Integer getBlacklistCnt() {
        return blacklistCnt;
    }

    public void setBlacklistCnt(Integer blacklistCnt) {
        this.blacklistCnt = blacklistCnt;
    }

    public Integer getSearchedCnt() {
        return searchedCnt;
    }

    public void setSearchedCnt(Integer searchedCnt) {
        this.searchedCnt = searchedCnt;
    }

    public Integer getLoanRecordCnt() {
        return loanRecordCnt;
    }

    public void setLoanRecordCnt(Integer loanRecordCnt) {
        this.loanRecordCnt = loanRecordCnt;
    }

    public String getContactDistributionLocation() {
        return contactDistributionLocation;
    }

    public void setContactDistributionLocation(String contactDistributionLocation) {
        this.contactDistributionLocation = contactDistributionLocation;
    }

    public Double getContactDistributionRatio() {
        return contactDistributionRatio;
    }

    public void setContactDistributionRatio(Double contactDistributionRatio) {
        this.contactDistributionRatio = contactDistributionRatio;
    }

    public Integer getBothCallCnt() {
        return bothCallCnt;
    }

    public void setBothCallCnt(Integer bothCallCnt) {
        this.bothCallCnt = bothCallCnt;
    }

    public Double getNightActivityRatio() {
        return nightActivityRatio;
    }

    public void setNightActivityRatio(Double nightActivityRatio) {
        this.nightActivityRatio = nightActivityRatio;
    }

    public Double getNightMsgRatio() {
        return nightMsgRatio;
    }

    public void setNightMsgRatio(Double nightMsgRatio) {
        this.nightMsgRatio = nightMsgRatio;
    }

    public String getActiveDaysStartDay() {
        return activeDaysStartDay;
    }

    public void setActiveDaysStartDay(String activeDaysStartDay) {
        this.activeDaysStartDay = activeDaysStartDay;
    }

    public Integer getActiveDaysTotalDays() {
        return activeDaysTotalDays;
    }

    public void setActiveDaysTotalDays(Integer activeDaysTotalDays) {
        this.activeDaysTotalDays = activeDaysTotalDays;
    }

    public String getActiveDaysEndDay() {
        return activeDaysEndDay;
    }

    public void setActiveDaysEndDay(String activeDaysEndDay) {
        this.activeDaysEndDay = activeDaysEndDay;
    }

    public Integer getActiveDaysStopDays() {
        return activeDaysStopDays;
    }

    public void setActiveDaysStopDays(Integer activeDaysStopDays) {
        this.activeDaysStopDays = activeDaysStopDays;
    }

    public String getActiveDaysStopDaysDetail() {
        return activeDaysStopDaysDetail;
    }

    public void setActiveDaysStopDaysDetail(String activeDaysStopDaysDetail) {
        this.activeDaysStopDaysDetail = activeDaysStopDaysDetail;
    }

    public String getActiveDaysStop3DaysDetail() {
        return activeDaysStop3DaysDetail;
    }

    public void setActiveDaysStop3DaysDetail(String activeDaysStop3DaysDetail) {
        this.activeDaysStop3DaysDetail = activeDaysStop3DaysDetail;
    }

    public Integer getActiveDaysStop3Days() {
        return activeDaysStop3Days;
    }

    public void setActiveDaysStop3Days(Integer activeDaysStop3Days) {
        this.activeDaysStop3Days = activeDaysStop3Days;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
