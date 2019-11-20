package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 复审记录表
 *
 * @author DOY
 *
 */
@Table(name = "bw_reconsider")
public class BwReconsider implements java.io.Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号
    private Long orderId; // 工单Id
    private String reconsider;// 复议内容
    private Date createTime;// 创建时间
    private Long reconsiderType;// 复议类型
    private Long userId; // 复议人

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

    public String getReconsider() {
        return reconsider;
    }

    public void setReconsider(String reconsider) {
        this.reconsider = reconsider;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getReconsiderType() {
        return reconsiderType;
    }

    public void setReconsiderType(Long reconsiderType) {
        this.reconsiderType = reconsiderType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
