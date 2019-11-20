package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 特殊种类号码
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:36
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SpecialCate {

    /**
     * called_cnt : 3
     * talk_seconds : 114
     * cate : 出行类
     * talk_cnt : 3
     * receive_cnt : 0
     * month_detail : 1
     * called_seconds : 114
     * msg_cnt : 0
     * call_cnt : 0
     * unknown_cnt : 0
     * send_cnt : 0
     * phone_detail : 1
     * call_seconds : 0
     */

    private int called_cnt;
    private int talk_seconds;
    private String cate;
    private int talk_cnt;
    private int receive_cnt;
    private int called_seconds;
    private int msg_cnt;
    private int call_cnt;
    private int unknown_cnt;
    private int send_cnt;
    private List<SpecialCatePhoneDetail> phone_detail;
    private List<SpecialCateMonthDetail> month_detail;

    private int call_seconds;

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

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public int getTalk_cnt() {
        return talk_cnt;
    }

    public void setTalk_cnt(int talk_cnt) {
        this.talk_cnt = talk_cnt;
    }

    public int getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(int receive_cnt) {
        this.receive_cnt = receive_cnt;
    }


    public int getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(int called_seconds) {
        this.called_seconds = called_seconds;
    }

    public int getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(int msg_cnt) {
        this.msg_cnt = msg_cnt;
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

    public List<SpecialCatePhoneDetail> getPhone_detail() {
        return phone_detail;
    }

    public void setPhone_detail(List<SpecialCatePhoneDetail> phone_detail) {
        this.phone_detail = phone_detail;
    }

    public List<SpecialCateMonthDetail> getMonth_detail() {
        return month_detail;
    }

    public void setMonth_detail(List<SpecialCateMonthDetail> month_detail) {
        this.month_detail = month_detail;
    }

    public int getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(int call_seconds) {
        this.call_seconds = call_seconds;
    }
}
