package com.waterelephant.drainage.util.fqgj.jkzj;

import com.alibaba.fastjson.JSONObject;

public class CallerResponse extends JSONObject {

    private Integer error;
    private String msg;


    public CallerResponse() {
        this.error = 200;
        this.msg = "success";
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
