package com.waterelephant.entity;

import java.io.Serializable;

public class OrderState implements Serializable {

    private Integer state;
    private Long time;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


}
