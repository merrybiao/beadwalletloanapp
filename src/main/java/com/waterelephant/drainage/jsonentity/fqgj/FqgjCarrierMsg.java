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
public class FqgjCarrierMsg implements Serializable {

    @JSONField(name="msgdata")
    private List<FqgjCarrierMsgData> carrierMsgVOs=new ArrayList<>();

    public List<FqgjCarrierMsgData> getCarrierMsgVOs() {
        return carrierMsgVOs;
    }

    public void setCarrierMsgVOs(List<FqgjCarrierMsgData> carrierMsgVOs) {
        this.carrierMsgVOs = carrierMsgVOs;
    }
}
