package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:50
 */
public class FqgjMobile {
    @JSONField(name="user")
    private FqgjCarrierUser user;
    @JSONField(name="tel")
    private List<FqgjCarrierTel> tel;
    @JSONField(name="msg")
    private List<FqgjCarrierMsg> msg;
    @JSONField(name="bill")
    private List<FqgjCarrierBill> bill;
    @JSONField(name="net")
    private List<FqgjCarrierNet> net;
    @JSONField(name="recharge")
    private List<FqgjCarrierRecharge> recharge;

    public FqgjCarrierUser getUser() {
        return user;
    }

    public void setUser(FqgjCarrierUser user) {
        this.user = user;
    }

    public List<FqgjCarrierTel> getTel() {
        return tel;
    }

    public void setTel(List<FqgjCarrierTel> tel) {
        this.tel = tel;
    }

    public List<FqgjCarrierMsg> getMsg() {
        return msg;
    }

    public void setMsg(List<FqgjCarrierMsg> msg) {
        this.msg = msg;
    }

    public List<FqgjCarrierBill> getBill() {
        return bill;
    }

    public void setBill(List<FqgjCarrierBill> bill) {
        this.bill = bill;
    }

    public List<FqgjCarrierNet> getNet() {
        return net;
    }

    public void setNet(List<FqgjCarrierNet> net) {
        this.net = net;
    }

    public List<FqgjCarrierRecharge> getRecharge() {
        return recharge;
    }

    public void setRecharge(List<FqgjCarrierRecharge> recharge) {
        this.recharge = recharge;
    }
}
