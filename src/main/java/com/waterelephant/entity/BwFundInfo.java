package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_fund_info")
public class BwFundInfo implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键ID
    private String birthday;// 生日
    private String nation;// 名族
    private String idCard;// 身份证号
    private String formatAccProp;// 格式化后的户口性质
    private String fundMonthMoney;// 共积金月申报人民币，缴费基数
    private String realName;// 姓名
    private String fundCity;// 公积金城市
    private String fundCode;// 公积金编号
    private String livePostcode;// 居住地址邮编
    private String balance;// 余额
    private String workerNation;// 职工性质
    private String cellphone;// 手机号
    private String fromatFundStatus;// 公积金缴纳状态（正常缴纳，停止缴纳）
    private String accProp;// 户口性质
    private String accAddr;// 户口地址
    private String email;// 邮箱
    private String comName;// 缴费公司名称
    private Date createTime;// 创建时间
    private String fundStatus;// 格式化公积金缴纳状态（正常，停止）
    private String sex;// 性别
    private String workStartDay;// 开始工作时间
    private String degree;// 学历
    private String maritalStatus;// 婚姻状况
    private String phone;// 座机号
    private String formatDegree;// 格式化后的学历（院士，博士，硕士，大学本科，大学专科，高中，初中，小学）
    private String comCode;// 缴费公司机构码
    private Long orderId;// 工单id
    private String fundBaseMoney;// 公积金缴纳基数
    private String liveAddr;// 居住地址

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFormatAccProp() {
        return this.formatAccProp;
    }

    public void setFormatAccProp(String formatAccProp) {
        this.formatAccProp = formatAccProp;
    }

    public String getFundMonthMoney() {
        return this.fundMonthMoney;
    }

    public void setFundMonthMoney(String fundMonthMoney) {
        this.fundMonthMoney = fundMonthMoney;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFundCity() {
        return this.fundCity;
    }

    public void setFundCity(String fundCity) {
        this.fundCity = fundCity;
    }

    public String getFundCode() {
        return this.fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getLivePostcode() {
        return this.livePostcode;
    }

    public void setLivePostcode(String livePostcode) {
        this.livePostcode = livePostcode;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWorkerNation() {
        return this.workerNation;
    }

    public void setWorkerNation(String workerNation) {
        this.workerNation = workerNation;
    }

    public String getCellphone() {
        return this.cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFromatFundStatus() {
        return this.fromatFundStatus;
    }

    public void setFromatFundStatus(String fromatFundStatus) {
        this.fromatFundStatus = fromatFundStatus;
    }

    public String getAccProp() {
        return this.accProp;
    }

    public void setAccProp(String accProp) {
        this.accProp = accProp;
    }

    public String getAccAddr() {
        return this.accAddr;
    }

    public void setAccAddr(String accAddr) {
        this.accAddr = accAddr;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComName() {
        return this.comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFundStatus() {
        return this.fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorkStartDay() {
        return this.workStartDay;
    }

    public void setWorkStartDay(String workStartDay) {
        this.workStartDay = workStartDay;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFormatDegree() {
        return this.formatDegree;
    }

    public void setFormatDegree(String formatDegree) {
        this.formatDegree = formatDegree;
    }

    public String getComCode() {
        return this.comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFundBaseMoney() {
        return this.fundBaseMoney;
    }

    public void setFundBaseMoney(String fundBaseMoney) {
        this.fundBaseMoney = fundBaseMoney;
    }

    public String getLiveAddr() {
        return this.liveAddr;
    }

    public void setLiveAddr(String liveAddr) {
        this.liveAddr = liveAddr;
    }

}
