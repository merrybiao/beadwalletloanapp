package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 审批结论拉取
 * Created by DIY on 2017/7/31.
 */
public class ApproveStatusData {

    @JSONField(name = "order_no")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
