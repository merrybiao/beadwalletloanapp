package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.Table;


/**
 *
 * @ClassName: BwCheckRecord
 * @Description: TODO(审核记录实体类)
 * @author SongYajun
 * @date 2016年8月27日 上午10:04:43
 *
 */
@Table(name = "bw_check_record")
public class BwCheckRecord implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id; // 编号
    private String comment; // 审核意见
    private int result; // 审核结果
    private Long statusId; // 工单状态
    private Long sysUserId; // 审核人
    private Long orderId; // 工单id
    private Date createTime; // 创建时间


    public BwCheckRecord() {}

    public BwCheckRecord(Long id, String comment, int result, Long statusId, Long sysUserId, Long orderId, Date createTime) {
        this.id = id;
        this.comment = comment;
        this.result = result;
        this.statusId = statusId;
        this.sysUserId = sysUserId;
        this.orderId = orderId;
        this.createTime = createTime;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public int getResult() {
        return result;
    }


    public void setResult(int result) {
        this.result = result;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
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


}
