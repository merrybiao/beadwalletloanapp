package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (code:dkdh001)
 * 短信记录实体类
 *
 * @author lwl
 */

@Table(name = "bw_operate_msg")
public class BwOperateMsg implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id; // 主键
    private Long borrowerId; // 借款人id
    private String month; // 月份
    private Integer totalSize; // 总条数
    private String sendTime; // 发送时间
    private Integer tradeWay; // 短信状态
    private String receiverPhone; // 对方号码
    private String businessName; // 业务类型
    private String fee; // 费用
    private String tradeAddr; // 短信地址
    private Integer tradeType; // 信息类型
    private Date createTime;

    private String flag1; // 备用字段
    private String flag2; // 备用字段
    private Integer flag3; // 备用字段
    private Integer flag4; // 备用字段

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getTradeWay() {
        return tradeWay;
    }

    public void setTradeWay(Integer tradeWay) {
        this.tradeWay = tradeWay;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public void setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public Integer getFlag3() {
        return flag3;
    }

    public void setFlag3(Integer flag3) {
        this.flag3 = flag3;
    }

    public Integer getFlag4() {
        return flag4;
    }

    public void setFlag4(Integer flag4) {
        this.flag4 = flag4;
    }
}
