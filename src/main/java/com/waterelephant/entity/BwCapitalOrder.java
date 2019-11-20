package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwCapitalLimit
 *
 * @author 崔雄健
 * @date 2017年4月6日
 * @description 资方订单表
 */
@Table(name = "bw_capital_order")
public class BwCapitalOrder implements java.io.Serializable {

    private static final long serialVersionUID = 15153112312L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号
    private Long capitalId;
    private Integer type; // 订单类型
    private Date createTime;// 创建时间
    private Long orderId;
    private String orderNo;
    private String idCard;
    private String capitalNo;
    private Date updateTime;
    private String orderStatus; // 订单状态
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(Long capitalId) {
        this.capitalId = capitalId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCapitalNo() {
        return capitalNo;
    }

    public void setCapitalNo(String capitalNo) {
        this.capitalNo = capitalNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
