package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 通话记录
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 13:37
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallLog {

    /**
     * contact_noon : 19
     * talk_seconds : 19294
     * talk_cnt : 148
     * contact_3m : 81
     * msg_cnt : 99
     * contact_1m : 40
     * unknown_cnt : 0
     * contact_eveing : 64
     * contact_1w : 4
     * phone_info : 未知
     * called_seconds : 9224
     * detail : 1
     * call_cnt : 75
     * called_cnt : 73
     * contact_weekday : 168
     * receive_cnt : 0
     * phone : 18271928109
     * call_seconds : 10070
     * first_contact_date : 2016-12-02 07:10:52
     * contact_afternoon : 52
     * contact_early_morning : 11
     * last_contact_date : 2017-05-06 18:36:52
     * contact_night : 60
     * phone_label : 未知
     * send_cnt : 99
     * phone_location : 湖北
     * contact_morning : 43
     * contact_weekend : 79
     */

    private int contact_noon;
    private int talk_seconds;
    private int talk_cnt;
    private int contact_3m;
    private int msg_cnt;
    private int contact_1m;
    private int unknown_cnt;
    private int contact_eveing;
    private int contact_1w;
    private String phone_info;
    private int called_seconds;
    private List<Detail> detail;
    private int call_cnt;
    private int called_cnt;
    private int contact_weekday;
    private int receive_cnt;
    private String phone;
    private int call_seconds;
    private String first_contact_date;
    private int contact_afternoon;
    private int contact_early_morning;
    private String last_contact_date;
    private int contact_night;
    private String phone_label;
    private int send_cnt;
    private String phone_location;
    private int contact_morning;
    private int contact_weekend;

    public int getContact_noon() {
        return contact_noon;
    }

    public void setContact_noon(int contact_noon) {
        this.contact_noon = contact_noon;
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

    public int getContact_3m() {
        return contact_3m;
    }

    public void setContact_3m(int contact_3m) {
        this.contact_3m = contact_3m;
    }

    public int getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(int msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public int getContact_1m() {
        return contact_1m;
    }

    public void setContact_1m(int contact_1m) {
        this.contact_1m = contact_1m;
    }

    public int getUnknown_cnt() {
        return unknown_cnt;
    }

    public void setUnknown_cnt(int unknown_cnt) {
        this.unknown_cnt = unknown_cnt;
    }

    public int getContact_eveing() {
        return contact_eveing;
    }

    public void setContact_eveing(int contact_eveing) {
        this.contact_eveing = contact_eveing;
    }

    public int getContact_1w() {
        return contact_1w;
    }

    public void setContact_1w(int contact_1w) {
        this.contact_1w = contact_1w;
    }

    public String getPhone_info() {
        return phone_info;
    }

    public void setPhone_info(String phone_info) {
        this.phone_info = phone_info;
    }

    public int getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(int called_seconds) {
        this.called_seconds = called_seconds;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public int getCall_cnt() {
        return call_cnt;
    }

    public void setCall_cnt(int call_cnt) {
        this.call_cnt = call_cnt;
    }

    public int getCalled_cnt() {
        return called_cnt;
    }

    public void setCalled_cnt(int called_cnt) {
        this.called_cnt = called_cnt;
    }

    public int getContact_weekday() {
        return contact_weekday;
    }

    public void setContact_weekday(int contact_weekday) {
        this.contact_weekday = contact_weekday;
    }

    public int getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(int receive_cnt) {
        this.receive_cnt = receive_cnt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(int call_seconds) {
        this.call_seconds = call_seconds;
    }

    public String getFirst_contact_date() {
        return first_contact_date;
    }

    public void setFirst_contact_date(String first_contact_date) {
        this.first_contact_date = first_contact_date;
    }

    public int getContact_afternoon() {
        return contact_afternoon;
    }

    public void setContact_afternoon(int contact_afternoon) {
        this.contact_afternoon = contact_afternoon;
    }

    public int getContact_early_morning() {
        return contact_early_morning;
    }

    public void setContact_early_morning(int contact_early_morning) {
        this.contact_early_morning = contact_early_morning;
    }

    public String getLast_contact_date() {
        return last_contact_date;
    }

    public void setLast_contact_date(String last_contact_date) {
        this.last_contact_date = last_contact_date;
    }

    public int getContact_night() {
        return contact_night;
    }

    public void setContact_night(int contact_night) {
        this.contact_night = contact_night;
    }

    public String getPhone_label() {
        return phone_label;
    }

    public void setPhone_label(String phone_label) {
        this.phone_label = phone_label;
    }

    public int getSend_cnt() {
        return send_cnt;
    }

    public void setSend_cnt(int send_cnt) {
        this.send_cnt = send_cnt;
    }

    public String getPhone_location() {
        return phone_location;
    }

    public void setPhone_location(String phone_location) {
        this.phone_location = phone_location;
    }

    public int getContact_morning() {
        return contact_morning;
    }

    public void setContact_morning(int contact_morning) {
        this.contact_morning = contact_morning;
    }

    public int getContact_weekend() {
        return contact_weekend;
    }

    public void setContact_weekend(int contact_weekend) {
        this.contact_weekend = contact_weekend;
    }
}
