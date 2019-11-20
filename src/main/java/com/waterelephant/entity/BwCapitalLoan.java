package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * BwCapitalPush.java
 *
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: 资方推送记录
 */
@Table(name = "bw_capital_loan")
public class BwCapitalLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键ID
    private Long orderId;// 工单ID
    private Integer capitalId;// 理财渠道
    private String capitalNo;
    private Integer pushStatus;// 状态。0：推送失败，1：推送成功，2：还款清单成功，3：还款清单失败
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Integer pushCount;
    private String code;
    private String msg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(Integer capitalId) {
        this.capitalId = capitalId;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
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

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCapitalNo() {
        return capitalNo;
    }

    public void setCapitalNo(String capitalNo) {
        this.capitalNo = capitalNo;
    }

}
