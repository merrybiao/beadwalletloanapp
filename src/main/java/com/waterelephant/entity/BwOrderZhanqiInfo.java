package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工单推送信息 20160106
 *
 * @author duxiaoyong
 *
 */
@Table(name = "bw_order_zhanqi_info")
public class BwOrderZhanqiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键ID
    private String orderNo;// 工单号
    private Integer zhanqiStatus;// 展期状态。0：失败，1：成功
    private String zhanqiRemark;// 备注
    private Date createTime;// 创建时间
    private Integer type;// 1:还款，2：展期

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getZhanqiStatus() {
        return zhanqiStatus;
    }

    public void setZhanqiStatus(Integer zhanqiStatus) {
        this.zhanqiStatus = zhanqiStatus;
    }

    public String getZhanqiRemark() {
        return zhanqiRemark;
    }

    public void setZhanqiRemark(String zhanqiRemark) {
        this.zhanqiRemark = zhanqiRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
