package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 认证被拒记录
 *
 * @author hwuei
 *
 */
@Table(name = "bw_reject_record")
public class BwRejectRecord implements Serializable {

    private static final long serialVersionUID = -7132302601592455677L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rejectInfo;
    private String rejectCode;
    private Long orderId;
    private Date createTime;
    private Integer rejectType;
    private Integer source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectInfo() {
        return rejectInfo;
    }

    public void setRejectInfo(String rejectInfo) {
        this.rejectInfo = rejectInfo;
    }

    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRejectType() {
        return rejectType;
    }

    public void setRejectType(Integer rejectType) {
        this.rejectType = rejectType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwRejectRecord[");
        builder.append(" [id] = ");
        builder.append(id);
        builder.append(" 被拒信息[rejectInfo] = ");
        builder.append(rejectInfo);
        builder.append(" 被拒代码[rejectCode] = ");
        builder.append(rejectCode);
        builder.append(" 工单id[orderId] = ");
        builder.append(orderId);
        builder.append(" [createTime] = ");
        builder.append(createTime);
        builder.append(" 被拒类型：0.永久被拒 1.非永久被拒[rejectType] = ");
        builder.append(rejectType);
        builder.append(" 数据来源：1.国政通 2.前海 3.宜信 4.闪银  5.同盾 6.芝麻信用 7.其它 8.魔蝎 9.融360[source] = ");
        builder.append(source);
        return builder.toString();
    }
}
