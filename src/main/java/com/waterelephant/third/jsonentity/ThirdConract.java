package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 拉取合同
 * Created by dengyan on 2017/7/31.
 */
public class ThirdConract {

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "contract_return_url")
    private String contractReurnUrl;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContractReurnUrl() {
        return contractReurnUrl;
    }

    public void setContractReurnUrl(String contractReurnUrl) {
        this.contractReurnUrl = contractReurnUrl;
    }
}
