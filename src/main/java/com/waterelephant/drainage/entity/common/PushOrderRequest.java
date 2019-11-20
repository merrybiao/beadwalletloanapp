package com.waterelephant.drainage.entity.common;

import java.util.List;

import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwIdentityCard2;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;

/**
 * 公共方法 - 推送订单
 * <p>
 * <p>
 * Module:
 * <p>
 * PushOrderRequest.java
 *
 * @author liuDaodao
 * @version 1.0
 * @description: <描述>
 * @since JDK 1.8
 */
public class PushOrderRequest {
    // 基本信息
    private String thirdOrderNo; // 第三方订单号（必填）
    private String userName; // 用户名（必填）
    private String phone; // 手机号（必填）
    private String idCard; // 身份证号（必填）
    private Integer channelId; // 渠道编号（必填）
    private Integer sesameScore; // 芝麻分

    // 工单信息
    private Double expectMoney;// 用户预期借款金额（必填）
    private Integer expectNumber;// 预借期数（必填）

    // 图片地址
    private String idCardFrontImage; // 身份证正面照片地址
    private String idCardBackImage; // 身份证反面照片地址
    private String idCardHanderImage; // 身份证手持地址

    // 其它信息
    private BwWorkInfo bwWorkInfo; // 工作信息
    private List<BwContactList> bwContactList; // 通讯录（必填）
    private BwOperateBasic bwOperateBasic; // 运营商（必填）
    private List<BwOperateVoice> bwOperateVoiceList; // 通话记录（必填）
    private BwIdentityCard2 bwIdentityCard; // 身份证信息（必填）
    private BwPersonInfo bwPersonInfo; // 亲属联系人（必填）
    private BwBankCard bwBankCard; // 银行卡信息

    private Integer orderStatus; // 订单状态设置（非空表示不设置）
    private int invokeTime; // 调用次数（不必为此字段赋值）

    // 2018-05-17添加
    private Object operaterData;// 运营商报告数据

    /**
     * 2018-06-19 添加设备信息
     */
    private Object deviceInfo;

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardFrontImage() {
        return idCardFrontImage;
    }

    public void setIdCardFrontImage(String idCardFrontImage) {
        this.idCardFrontImage = idCardFrontImage;
    }

    public String getIdCardBackImage() {
        return idCardBackImage;
    }

    public void setIdCardBackImage(String idCardBackImage) {
        this.idCardBackImage = idCardBackImage;
    }

    public String getIdCardHanderImage() {
        return idCardHanderImage;
    }

    public void setIdCardHanderImage(String idCardHanderImage) {
        this.idCardHanderImage = idCardHanderImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getSesameScore() {
        return sesameScore;
    }

    public void setSesameScore(Integer sesameScore) {
        this.sesameScore = sesameScore;
    }

    public Double getExpectMoney() {
        return expectMoney;
    }

    public void setExpectMoney(Double expectMoney) {
        this.expectMoney = expectMoney;
    }

    public Integer getExpectNumber() {
        return expectNumber;
    }

    public void setExpectNumber(Integer expectNumber) {
        this.expectNumber = expectNumber;
    }

    public BwWorkInfo getBwWorkInfo() {
        return bwWorkInfo;
    }

    public void setBwWorkInfo(BwWorkInfo bwWorkInfo) {
        this.bwWorkInfo = bwWorkInfo;
    }

    public List<BwContactList> getBwContactList() {
        return bwContactList;
    }

    public void setBwContactList(List<BwContactList> bwContactList) {
        this.bwContactList = bwContactList;
    }

    public BwOperateBasic getBwOperateBasic() {
        return bwOperateBasic;
    }

    public void setBwOperateBasic(BwOperateBasic bwOperateBasic) {
        this.bwOperateBasic = bwOperateBasic;
    }

    public List<BwOperateVoice> getBwOperateVoiceList() {
        return bwOperateVoiceList;
    }

    public void setBwOperateVoiceList(List<BwOperateVoice> bwOperateVoiceList) {
        this.bwOperateVoiceList = bwOperateVoiceList;
    }

    public BwIdentityCard2 getBwIdentityCard() {
        return bwIdentityCard;
    }

    public void setBwIdentityCard(BwIdentityCard2 bwIdentityCard) {
        this.bwIdentityCard = bwIdentityCard;
    }

    public BwPersonInfo getBwPersonInfo() {
        return bwPersonInfo;
    }

    public void setBwPersonInfo(BwPersonInfo bwPersonInfo) {
        this.bwPersonInfo = bwPersonInfo;
    }

    public BwBankCard getBwBankCard() {
        return bwBankCard;
    }

    public void setBwBankCard(BwBankCard bwBankCard) {
        this.bwBankCard = bwBankCard;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getInvokeTime() {
        return invokeTime;
    }

    public void setInvokeTime(int invokeTime) {
        this.invokeTime = invokeTime;
    }

    public Object getOperaterData() {
        return operaterData;
    }

    public void setOperaterData(Object operaterData) {
        this.operaterData = operaterData;
    }

    public Object getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Object deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
