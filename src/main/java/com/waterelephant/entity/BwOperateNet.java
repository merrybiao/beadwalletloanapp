package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (code:dkdh001)
 * 流量记录实体类
 *
 * @author lwl
 */
@Table(name = "bw_operate_net")
public class BwOperateNet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id; // 主键
    private Long borrowerId; // 借款人id
    private String month; // 月份
    private Integer totalSize; // 总条数
    private String fee; // 通信费
    private String netType; // 网络类型
    private String netWay; // 上网方式
    private String preferentialFee; // 优惠项/套餐优惠
    private String startTime; // 起始时间
    private String totalTime; // 总时长
    private String totalTraffic; // 总流量
    private String tradeAddr; // 通信地点
    private Date createTime;

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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNetWay() {
        return netWay;
    }

    public void setNetWay(String netWay) {
        this.netWay = netWay;
    }

    public String getPreferentialFee() {
        return preferentialFee;
    }

    public void setPreferentialFee(String preferentialFee) {
        this.preferentialFee = preferentialFee;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(String totalTraffic) {
        this.totalTraffic = totalTraffic;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public void setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
