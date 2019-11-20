package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 输入信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 10:54
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class InputInfo {


    /**
     * id_card : 420602199110092511
     * emergency_phone2 :
     * emergency_phone1 :
     * emergency_name2 :
     * emergency_relation1 :
     * emergency_name1 :
     * phone : 15071361812
     * user_name : ???é?????
     * emergency_relation2 :
     */

    private String id_card;
    private String emergency_phone2;
    private String emergency_phone1;
    private String emergency_name2;
    private String emergency_relation1;
    private String emergency_name1;
    private String phone;
    private String user_name;
    private String emergency_relation2;

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getEmergency_phone2() {
        return emergency_phone2;
    }

    public void setEmergency_phone2(String emergency_phone2) {
        this.emergency_phone2 = emergency_phone2;
    }

    public String getEmergency_phone1() {
        return emergency_phone1;
    }

    public void setEmergency_phone1(String emergency_phone1) {
        this.emergency_phone1 = emergency_phone1;
    }

    public String getEmergency_name2() {
        return emergency_name2;
    }

    public void setEmergency_name2(String emergency_name2) {
        this.emergency_name2 = emergency_name2;
    }

    public String getEmergency_relation1() {
        return emergency_relation1;
    }

    public void setEmergency_relation1(String emergency_relation1) {
        this.emergency_relation1 = emergency_relation1;
    }

    public String getEmergency_name1() {
        return emergency_name1;
    }

    public void setEmergency_name1(String emergency_name1) {
        this.emergency_name1 = emergency_name1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmergency_relation2() {
        return emergency_relation2;
    }

    public void setEmergency_relation2(String emergency_relation2) {
        this.emergency_relation2 = emergency_relation2;
    }
}
