package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjCarrierTel implements Serializable{

    @JSONField(name="teldata")
    private List<FqgjCarrierTelData> teldata = new ArrayList<>();

    public List<FqgjCarrierTelData> getTeldata() {
        return teldata;
    }

    public void setTeldata(List<FqgjCarrierTelData> teldata) {
        this.teldata = teldata;
    }
}
