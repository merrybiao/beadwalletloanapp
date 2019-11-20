package com.waterelephant.drainage.jsonentity.fqgj;

import java.io.Serializable;

/**
 * Created by fq_qiguo on 2017/6/7.
 */
@SuppressWarnings("serial")
public class LocalCallVO implements Serializable {


    private String phone;

    private String name;

    private String callType;

    private Long callTime;

    private Integer callDuration;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Long getCallTime() {
        return callTime;
    }

    public void setCallTime(Long callTime) {
        this.callTime = callTime;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }
}
