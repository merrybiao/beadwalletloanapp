package com.waterelephant.entity;

public class CheckResult {
    private boolean result;
    private String code;
    private String desc;
    private Integer rejectType;
    private Integer soucre;
    private String dataBuildTime;

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRejectType() {
        return this.rejectType;
    }

    public void setRejectType(Integer rejectType) {
        this.rejectType = rejectType;
    }

    public Integer getSoucre() {
        return this.soucre;
    }

    public void setSoucre(Integer soucre) {
        this.soucre = soucre;
    }

    public String getDataBuildTime() {
        return this.dataBuildTime;
    }

    public void setDataBuildTime(String dataBuildTime) {
        this.dataBuildTime = dataBuildTime;
    }
}
