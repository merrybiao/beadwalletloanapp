package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 18:08
 */
public class FqgjOrder {
    @JSONField(name="order_info")
    private FqgjOrderInfo order_info;
    @JSONField(name="apply_detail")
    private FqgjApplyDetail apply_detail;
    @JSONField(name="add_info")
    private FqgjAddInfo add_info;

    public FqgjOrderInfo getOrder_info() {
        return order_info;
    }

    public void setOrder_info(FqgjOrderInfo order_info) {
        this.order_info = order_info;
    }

    public FqgjApplyDetail getApply_detail() {
        return apply_detail;
    }

    public void setApply_detail(FqgjApplyDetail apply_detail) {
        this.apply_detail = apply_detail;
    }

    public FqgjAddInfo getAdd_info() {
        return add_info;
    }

    public void setAdd_info(FqgjAddInfo add_info) {
        this.add_info = add_info;
    }
}
