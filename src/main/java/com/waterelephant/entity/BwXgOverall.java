package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lwl
 *
 */
@Table(name = "bw_xg_overall")
public class BwXgOverall implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long borrowerId; // 借款人ID
    private String score; // 西瓜分
    private String searchId; // 报告编号
    private String reportTime; // 报告生成时间
    private String userType; // 用户类型 1:实名认证;2:非实名认证
    private String userName; // 用户姓名
    private String idCard; // 身份证号
    private String phone; // 手机号
    private String emergencyName1; // 紧急联系人1
    private String emergencyRelation1; // 关系
    private String emergencyPhone1; // 手机号
    private String emergencyName2; // 紧急联系人2
    private String emergencyRelation2; // 关系
    private String emergencyPhone2; // 手机号
    private String operator; // 运营商编码
    private String operatorZh; // 运营商中文
    private String phoneLocation; // 号码归属地
    private Integer idCardCheck; // 身份证验证
    private Integer nameCheck; // 姓名验证
    private String basicInfoIdCard; // 基础信息中的身份证
    private String basicInfoRealName; // 基础信息中的姓名
    private Float aveMonthlyConsumption; // 每月平均消费
    private Float currentBalance; // 当前账户余额
    private String regTime; // 注册时间
    private Integer ifCallEmergency1; // 是否联系过紧急联系人1
    private Integer ifCallEmergency2; // 是否联系过紧急联系人2
    private Integer blacklistCnt; // 黑名单验证
    private Integer searchedCnt; // 被查询次数
    private Integer loanRecordCnt; // 贷款记录
    private String contactDistributionLocation; // 朋友圈分布 -- 地址
    private Float contactDistributionRatio; // 朋友圈分布 -- 占比
    private Integer bothCallCnt; // 互通电话的号码数量
    private Float nightActivityRatio; // 夜间活动情况
    private Float nightMsgRatio; // 夜间活动情况
    private String activeDaysStartDay; // 活跃开始时间
    private Integer activeDaysTotalDays; // 总活跃天数
    private String activeDaysEndDay; // 结束时间
    private Integer activeDaysStopDays; // 非活跃总天数
    private String activeDaysStopDaysDetail; // 非活跃天数细节
    private Integer activeDaysStop3Days; // 连续3天非活跃次数
    private String activeDaysStop3DaysDetail; // 连续3天非活跃详情
    private Date createDate; // 创建时间

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
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

    public Float getAveMonthlyConsumption() {
        return aveMonthlyConsumption;
    }

    public void setAveMonthlyConsumption(Float aveMonthlyConsumption) {
        this.aveMonthlyConsumption = aveMonthlyConsumption;
    }

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
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

    public Float getContactDistributionRatio() {
        return contactDistributionRatio;
    }

    public void setContactDistributionRatio(Float contactDistributionRatio) {
        this.contactDistributionRatio = contactDistributionRatio;
    }

    public Integer getBothCallCnt() {
        return bothCallCnt;
    }

    public void setBothCallCnt(Integer bothCallCnt) {
        this.bothCallCnt = bothCallCnt;
    }

    public Float getNightActivityRatio() {
        return nightActivityRatio;
    }

    public void setNightActivityRatio(Float nightActivityRatio) {
        this.nightActivityRatio = nightActivityRatio;
    }

    public Float getNightMsgRatio() {
        return nightMsgRatio;
    }

    public void setNightMsgRatio(Float nightMsgRatio) {
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

    public Integer getActiveDaysStop3Days() {
        return activeDaysStop3Days;
    }

    public void setActiveDaysStop3Days(Integer activeDaysStop3Days) {
        this.activeDaysStop3Days = activeDaysStop3Days;
    }

    public String getActiveDaysStop3DaysDetail() {
        return activeDaysStop3DaysDetail;
    }

    public void setActiveDaysStop3DaysDetail(String activeDaysStop3DaysDetail) {
        this.activeDaysStop3DaysDetail = activeDaysStop3DaysDetail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
