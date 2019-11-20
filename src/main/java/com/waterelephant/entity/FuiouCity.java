package com.waterelephant.entity;

import java.io.Serializable;

public class FuiouCity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String code;
    private String cityName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
