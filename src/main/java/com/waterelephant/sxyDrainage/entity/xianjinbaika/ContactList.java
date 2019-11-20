///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.xianjinbaika;
//
///**
// * Module:(code:xjbk)
// * <p>
// * ContactList.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class ContactList {
//    private String phone_num;// string 号码 contact_list
//    private String phone_num_loc;// string 号码归属地 contact_list
//    private String contact_name;// string 号码标注 contact_list
//    private String needs_type;// string 需求类别 contact_list
//    private int call_cnt;// int 通话次数 contact_list
//    private Double call_len;// float 通话时长 contact_list
//    private int call_out_cnt;// int 呼出次数 contact_list
//    private Double call_out_len;// float 呼出时间 contact_list
//    private int call_in_cnt;// int 呼入次数 contact_list
//    private Double call_in_len;// float 呼入时间 contact_list
//    private String p_relation;// string 关系推测 contact_list
//    private int contact_1w;// int 最近一周联系次数 contact_list
//    private int contact_1m;// int 最近一月联系次数 contact_list
//    private int contact_3m;// int 最近三月联系次数 contact_list
//    private int contact_3m_plus;// int 三个月以上联系次数 contact_list
//    private int contact_early_morning;// int 凌晨联系次数 contact_list
//    private int contact_morning;// int 上午联系次数 contact_list
//    private int contact_noon;// int 中午联系次数 contact_list
//    private int contact_afternoon;// int 下午联系次数 contact_list
//    private int contact_night;// int 晚上联系次数 contact_list
//    private boolean contact_all_day;// bool 是否全天联系 contact_list
//    private int contact_weekday;// int 周中联系次数 contact_list
//    private int contact_weekend;// int 周末联系次数 contact_list
//    private int contact_holiday;// int 节假日联系次数 contact_list
//
//    public String getPhone_num() {
//        return phone_num;
//    }
//
//    public void setPhone_num(String phone_num) {
//        this.phone_num = phone_num;
//    }
//
//    public String getPhone_num_loc() {
//        return phone_num_loc;
//    }
//
//    public void setPhone_num_loc(String phone_num_loc) {
//        this.phone_num_loc = phone_num_loc;
//    }
//
//    public String getContact_name() {
//        return contact_name;
//    }
//
//    public void setContact_name(String contact_name) {
//        this.contact_name = contact_name;
//    }
//
//    public String getNeeds_type() {
//        return needs_type;
//    }
//
//    public void setNeeds_type(String needs_type) {
//        this.needs_type = needs_type;
//    }
//
//    public int getCall_cnt() {
//        return call_cnt;
//    }
//
//    public void setCall_cnt(int call_cnt) {
//        this.call_cnt = call_cnt;
//    }
//
//    public Double getCall_len() {
//        return call_len;
//    }
//
//    public void setCall_len(Double call_len) {
//        this.call_len = call_len;
//    }
//
//    public int getCall_out_cnt() {
//        return call_out_cnt;
//    }
//
//    public void setCall_out_cnt(int call_out_cnt) {
//        this.call_out_cnt = call_out_cnt;
//    }
//
//    public Double getCall_out_len() {
//        return call_out_len;
//    }
//
//    public void setCall_out_len(Double call_out_len) {
//        this.call_out_len = call_out_len;
//    }
//
//    public int getCall_in_cnt() {
//        return call_in_cnt;
//    }
//
//    public void setCall_in_cnt(int call_in_cnt) {
//        this.call_in_cnt = call_in_cnt;
//    }
//
//    public Double getCall_in_len() {
//        return call_in_len;
//    }
//
//    public void setCall_in_len(Double call_in_len) {
//        this.call_in_len = call_in_len;
//    }
//
//    public String getP_relation() {
//        return p_relation;
//    }
//
//    public void setP_relation(String p_relation) {
//        this.p_relation = p_relation;
//    }
//
//    public int getContact_1w() {
//        return contact_1w;
//    }
//
//    public void setContact_1w(int contact_1w) {
//        this.contact_1w = contact_1w;
//    }
//
//    public int getContact_1m() {
//        return contact_1m;
//    }
//
//    public void setContact_1m(int contact_1m) {
//        this.contact_1m = contact_1m;
//    }
//
//    public int getContact_3m() {
//        return contact_3m;
//    }
//
//    public void setContact_3m(int contact_3m) {
//        this.contact_3m = contact_3m;
//    }
//
//    public int getContact_3m_plus() {
//        return contact_3m_plus;
//    }
//
//    public void setContact_3m_plus(int contact_3m_plus) {
//        this.contact_3m_plus = contact_3m_plus;
//    }
//
//    public int getContact_early_morning() {
//        return contact_early_morning;
//    }
//
//    public void setContact_early_morning(int contact_early_morning) {
//        this.contact_early_morning = contact_early_morning;
//    }
//
//    public int getContact_morning() {
//        return contact_morning;
//    }
//
//    public void setContact_morning(int contact_morning) {
//        this.contact_morning = contact_morning;
//    }
//
//    public int getContact_noon() {
//        return contact_noon;
//    }
//
//    public void setContact_noon(int contact_noon) {
//        this.contact_noon = contact_noon;
//    }
//
//    public int getContact_afternoon() {
//        return contact_afternoon;
//    }
//
//    public void setContact_afternoon(int contact_afternoon) {
//        this.contact_afternoon = contact_afternoon;
//    }
//
//    public int getContact_night() {
//        return contact_night;
//    }
//
//    public void setContact_night(int contact_night) {
//        this.contact_night = contact_night;
//    }
//
//    public boolean isContact_all_day() {
//        return contact_all_day;
//    }
//
//    public void setContact_all_day(boolean contact_all_day) {
//        this.contact_all_day = contact_all_day;
//    }
//
//    public int getContact_weekday() {
//        return contact_weekday;
//    }
//
//    public void setContact_weekday(int contact_weekday) {
//        this.contact_weekday = contact_weekday;
//    }
//
//    public int getContact_weekend() {
//        return contact_weekend;
//    }
//
//    public void setContact_weekend(int contact_weekend) {
//        this.contact_weekend = contact_weekend;
//    }
//
//    public int getContact_holiday() {
//        return contact_holiday;
//    }
//
//    public void setContact_holiday(int contact_holiday) {
//        this.contact_holiday = contact_holiday;
//    }
//}
