package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *
 * Module:
 *
 * WoNiuOrderPush.java
 *
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <蜗牛聚财工单实体>
 */
@Table(name = "wn_order_push")
public class WoNiuOrder implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号
    private String thirdNo; // 水象订单号
    private String tenderId; // 标id
    private String userName; // 借款人姓名
    private String mobile; // 手机号
    private String idCard; // 身份证
    private String bankCard; // 银行卡号
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String money; // 到账金额
    private String status; // 工单状态:0.待债匹，1：债匹中，2：已债匹 3：未债匹 4：满标 5：放款成功 6：放款失败
    private String notifyStatus; // 通知回调状态 1.通知成功
    private String remark;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdNo() {
        return thirdNo;
    }

    public void setThirdNo(String thirdNo) {
        this.thirdNo = thirdNo;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WoNiuOrder [id=" + id + ", thirdNo=" + thirdNo + ", tenderId=" + tenderId + ", userName=" + userName + ", mobile=" + mobile + ", idCard=" + idCard + ", bankCard=" + bankCard
                + ", createTime=" + createTime + ", updateTime=" + updateTime + ", money=" + money + ", status=" + status + ", notifyStatus=" + notifyStatus + ", remark=" + remark + ", type=" + type
                + "]";
    }


}
