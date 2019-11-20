/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
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
 * Module: 商户订单关联表
 *
 * BwMerchantOrder.java
 *
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "bw_merchant_order")
public class BwMerchantOrder implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键
    private Long orderId;// 订单ID
    private Long merchantId;// 商户ID
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Long borrowerId;// 用户ID
    private String remark;// 备注
    private String ext1;// 备选字段1(地区编码)
    private String ext2;// 备选字段2(推广员编码)
    private String ext3;// 备选字段3(来源：0非扫码，1扫商户吗)
    private Long mallCouponId;// 商城优惠券ID
    private Integer whetherPay = 0;// 是否购买商城优惠券(0：未购买，1：已购买)

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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public Long getMallCouponId() {
        return mallCouponId;
    }

    public void setMallCouponId(Long mallCouponId) {
        this.mallCouponId = mallCouponId;
    }

    public Integer getWhetherPay() {
        return whetherPay;
    }

    public void setWhetherPay(Integer whetherPay) {
        this.whetherPay = whetherPay;
    }

}
