package com.waterelephant.rongCarrier.JSonEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import com.waterelephant.utils.CommUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特别信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:16
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserPortraitSpecialCallInfo {

    /**
     * called_cnt : 2
     * talk_seconds : 50
     * talk_cnt : 2
     * msg_cnt : 0
     * called_seconds : 50
     * receive_cnt : 0
     * detail : {"2017-01":1,"2017-04":1}
     * call_cnt : 0
     * unknown_cnt : 0
     * send_cnt : 0
     * call_seconds : 0
     * phone_list : ["02759828693","18186105206"]
     */

    private int called_cnt;
    private int talk_seconds;
    private int talk_cnt;
    private int msg_cnt;
    private int called_seconds;
    private int receive_cnt;
    private int call_cnt;
    private int unknown_cnt;
    private int send_cnt;
    private int call_seconds;
    private String phone_list;
    private String detail;
//    private Map<String,Object> detail;
    // TODO detail

    public int getCalled_cnt() {
        return called_cnt;
    }

    public void setCalled_cnt(int called_cnt) {
        this.called_cnt = called_cnt;
    }

    public int getTalk_seconds() {
        return talk_seconds;
    }

    public void setTalk_seconds(int talk_seconds) {
        this.talk_seconds = talk_seconds;
    }

    public int getTalk_cnt() {
        return talk_cnt;
    }

    public void setTalk_cnt(int talk_cnt) {
        this.talk_cnt = talk_cnt;
    }

    public int getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(int msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public int getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(int called_seconds) {
        this.called_seconds = called_seconds;
    }

    public int getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(int receive_cnt) {
        this.receive_cnt = receive_cnt;
    }


    public int getCall_cnt() {
        return call_cnt;
    }

    public void setCall_cnt(int call_cnt) {
        this.call_cnt = call_cnt;
    }

    public int getUnknown_cnt() {
        return unknown_cnt;
    }

    public void setUnknown_cnt(int unknown_cnt) {
        this.unknown_cnt = unknown_cnt;
    }

    public int getSend_cnt() {
        return send_cnt;
    }

    public void setSend_cnt(int send_cnt) {
        this.send_cnt = send_cnt;
    }

    public int getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(int call_seconds) {
        this.call_seconds = call_seconds;
    }

    public String getPhone_list() {
        return phone_list;
    }

    public void setPhone_list(String phone_list) {
        this.phone_list = phone_list;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    //    public Map<String,Object> getDetail() {
//        return detail;
//    }
//
//    public void setDetail(String detail) {
//        if(CommUtils.isNull(detail) || detail.length()<3){
//            this.detail = new HashMap();
//        }else {
//            this.detail = JSON.parseObject(detail);
//        }
//
//    }
}
