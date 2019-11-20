package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 上网记录实体类
 *
 * @author lwl
 *
 */
@Table(name = "bw_operate_netlog")
public class BwOperateNetlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 主键
    private Integer borrowerId; // 借款人id
    private String month; // 月份
    private Integer totalSize; // 总条数
    private String startTime; // 开始时间
    private String bizType; // 业务类型
    private String flowType; // 流量类型
    private String businessName; // 业务名称
    private String flowName; // 流量名称
    private String totalTime; // 时长
    private String ratType; // 通信类型
    private String netType; // 流量类型
    private String upTraffic; // 上传流量
    private String downTraffic; // 下载流量
    private String totalTraffic; // 总流量
    private String clientIp; // 本地ip
    private String userAgent; // 浏览器标示
    private String accessIp;
    private String webSite; // 访问网址
    private String domainName; // 域名
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRatType() {
        return ratType;
    }

    public void setRatType(String ratType) {
        this.ratType = ratType;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getUpTraffic() {
        return upTraffic;
    }

    public void setUpTraffic(String upTraffic) {
        this.upTraffic = upTraffic;
    }

    public String getDownTraffic() {
        return downTraffic;
    }

    public void setDownTraffic(String downTraffic) {
        this.downTraffic = downTraffic;
    }

    public String getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(String totalTraffic) {
        this.totalTraffic = totalTraffic;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
