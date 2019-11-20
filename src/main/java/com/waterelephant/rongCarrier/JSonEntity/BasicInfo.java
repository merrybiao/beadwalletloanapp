package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 基础信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 10:56
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BasicInfo {


    /**
     * reg_time : 2009-09-06
     * operator_zh : 中国移动
     * phone : 15071361812
     * current_balance : 96.25
     * id_card :
     * name_check : 3
     * real_name : xx健
     * operator : CHINAMOBILE_HUBEI
     * if_call_emergency1 : 3
     * if_call_emergency2 : 3
     * phone_location : 湖北
     * ave_monthly_consumption : 67.32
     * id_card_check : 4
     */

    private String reg_time;
    private String operator_zh;
    private String phone;
    private double current_balance;
    private String id_card;
    private int name_check;
    private String real_name;
    private String operator;
    private int if_call_emergency1;
    private int if_call_emergency2;
    private String phone_location;
    private double ave_monthly_consumption;
    private int id_card_check;

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getOperator_zh() {
        return operator_zh;
    }

    public void setOperator_zh(String operator_zh) {
        this.operator_zh = operator_zh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(double current_balance) {
        this.current_balance = current_balance;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getName_check() {
        return name_check;
    }

    public void setName_check(int name_check) {
        this.name_check = name_check;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getIf_call_emergency1() {
        return if_call_emergency1;
    }

    public void setIf_call_emergency1(int if_call_emergency1) {
        this.if_call_emergency1 = if_call_emergency1;
    }

    public int getIf_call_emergency2() {
        return if_call_emergency2;
    }

    public void setIf_call_emergency2(int if_call_emergency2) {
        this.if_call_emergency2 = if_call_emergency2;
    }

    public String getPhone_location() {
        return phone_location;
    }

    public void setPhone_location(String phone_location) {
        this.phone_location = phone_location;
    }

    public double getAve_monthly_consumption() {
        return ave_monthly_consumption;
    }

    public void setAve_monthly_consumption(double ave_monthly_consumption) {
        this.ave_monthly_consumption = ave_monthly_consumption;
    }

    public int getId_card_check() {
        return id_card_check;
    }

    public void setId_card_check(int id_card_check) {
        this.id_card_check = id_card_check;
    }

    @Override
    public String toString() {
        return "BasicInfo{" +
                "reg_time='" + reg_time + '\'' +
                ", operator_zh='" + operator_zh + '\'' +
                ", phone='" + phone + '\'' +
                ", current_balance=" + current_balance +
                ", id_card='" + id_card + '\'' +
                ", name_check=" + name_check +
                ", real_name='" + real_name + '\'' +
                ", operator='" + operator + '\'' +
                ", if_call_emergency1=" + if_call_emergency1 +
                ", if_call_emergency2=" + if_call_emergency2 +
                ", phone_location='" + phone_location + '\'' +
                ", ave_monthly_consumption=" + ave_monthly_consumption +
                ", id_card_check=" + id_card_check +
                '}';
    }
}
