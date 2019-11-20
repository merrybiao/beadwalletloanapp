package com.waterelephant.drainage.jsonentity.fqgj;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/28 9:29
 */
public class FqgjSupplementOrderInfo {

	@JSONField(name = "residence_type")
    private String familiy_livetype;
	
	@JSONField(name = "company_name")
    private String company_name;
	
	@JSONField(name = "company_addr_detail")
    private String company_addr_detail;
	
    @JSONField(name = "emergency_contact_personA_relationship")
    private String emergency_contact_personA_relationship;
    
    @JSONField(name = "emergency_contact_personA_name")
    private String emergency_contact_personA_name;
    
    @JSONField(name = "emergency_contact_personA_phone")
    private String emergency_contact_personA_phone;
    
    @JSONField(name = "emergency_contact_personB_relationship")
    private String emergency_contact_personB_relationship;
    
    @JSONField(name = "company_number")
    private String company_number;
    
    @JSONField(name = "emergency_contact_personB_name")
    private String emergency_contact_personB_name;
    
    @JSONField(name = "emergency_contact_personB_phone")
    private String emergency_contact_personB_phone;

    @JSONField(name = "industry")
    private int industry;

    @JSONField(name = "qq")
    private String qq;
    
    @JSONField(name = "email")
    private String user_email;
    
    @JSONField(name = "pay_day")
    private String pay_day;
    
    @JSONField(name = "person_address")
    private String addr_detail;
    
    @JSONField(name = "user_marriage")
    private String user_marriage;
    
    @JSONField(name = "order_no")
    private String order_no;
    
    @JSONField(name = "ID_Positive")
    private List<String> id_positive;
    
    @JSONField(name = "ID_Negative")
    private List<String> id_negative;
    
    @JSONField(name = "photo_hand_ID")
    private List<String> photo_hand_id;
    
    @JSONField(name = "photo_assay")
    private List<String> photo_assay;
    
    @JSONField(name = "device_info_all")
    private FqgjSupplementDeviceInfoAll device_info_all;
    
    @JSONField(name = "contacts")
    private FqgjSupplementContact contacts;


    public String getFamiliy_livetype() {
        return familiy_livetype;
    }

    public void setFamiliy_livetype(String familiy_livetype) {
        this.familiy_livetype = familiy_livetype;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_addr_detail() {
        return company_addr_detail;
    }

    public void setCompany_addr_detail(String company_addr_detail) {
        this.company_addr_detail = company_addr_detail;
    }

    public String getEmergency_contact_personA_relationship() {
        return emergency_contact_personA_relationship;
    }

    public void setEmergency_contact_personA_relationship(String emergency_contact_personA_relationship) {
        this.emergency_contact_personA_relationship = emergency_contact_personA_relationship;
    }

    public String getEmergency_contact_personA_name() {
        return emergency_contact_personA_name;
    }

    public void setEmergency_contact_personA_name(String emergency_contact_personA_name) {
        this.emergency_contact_personA_name = emergency_contact_personA_name;
    }

    public String getEmergency_contact_personA_phone() {
        return emergency_contact_personA_phone;
    }

    public void setEmergency_contact_personA_phone(String emergency_contact_personA_phone) {
        this.emergency_contact_personA_phone = emergency_contact_personA_phone;
    }

    public String getEmergency_contact_personB_relationship() {
        return emergency_contact_personB_relationship;
    }

    public void setEmergency_contact_personB_relationship(String emergency_contact_personB_relationship) {
        this.emergency_contact_personB_relationship = emergency_contact_personB_relationship;
    }

    public String getCompany_number() {
        return company_number;
    }

    public void setCompany_number(String company_number) {
        this.company_number = company_number;
    }

    public String getEmergency_contact_personB_name() {
        return emergency_contact_personB_name;
    }

    public void setEmergency_contact_personB_name(String emergency_contact_personB_name) {
        this.emergency_contact_personB_name = emergency_contact_personB_name;
    }

    public String getEmergency_contact_personB_phone() {
        return emergency_contact_personB_phone;
    }

    public void setEmergency_contact_personB_phone(String emergency_contact_personB_phone) {
        this.emergency_contact_personB_phone = emergency_contact_personB_phone;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPay_day() {
        return pay_day;
    }

    public void setPay_day(String pay_day) {
        this.pay_day = pay_day;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }

    public String getUser_marriage() {
        return user_marriage;
    }

    public void setUser_marriage(String user_marriage) {
        this.user_marriage = user_marriage;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public List<String> getId_positive() {
        return id_positive;
    }

    public void setId_positive(List<String> id_positive) {
        this.id_positive = id_positive;
    }

    public List<String> getId_negative() {
        return id_negative;
    }

    public void setId_negative(List<String> id_negative) {
        this.id_negative = id_negative;
    }

    public List<String> getPhoto_hand_id() {
        return photo_hand_id;
    }

    public void setPhoto_hand_id(List<String> photo_hand_id) {
        this.photo_hand_id = photo_hand_id;
    }

    public List<String> getPhoto_assay() {
        return photo_assay;
    }

    public void setPhoto_assay(List<String> photo_assay) {
        this.photo_assay = photo_assay;
    }

    public void setPhoto_assay(ArrayList<String> photo_assay) {
        this.photo_assay = photo_assay;
    }

    public FqgjSupplementDeviceInfoAll getDevice_info_all() {
        return device_info_all;
    }

    public void setDevice_info_all(FqgjSupplementDeviceInfoAll device_info_all) {
        this.device_info_all = device_info_all;
    }

    public FqgjSupplementContact getContacts() {
        return contacts;
    }

    public void setContacts(FqgjSupplementContact contacts) {
        this.contacts = contacts;
    }
}
