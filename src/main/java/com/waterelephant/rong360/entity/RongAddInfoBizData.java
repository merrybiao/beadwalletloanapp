package com.waterelephant.rong360.entity;

import java.util.List;
@Deprecated
public class RongAddInfoBizData {
	private String order_no;
	private List<String> ID_Positive;
	private List<String> ID_Negative;
	private List<String> photo_hand_ID;
	private String company_name;
	private String work_industry;
	private String addr_detail;
	private String havecar;
	private String havehouse;
	private String user_marriage;
	private String emergency_contact_personA_name;
	private String emergency_contact_personA_phone;
	private String emergency_contact_personB_name;
	private String emergency_contact_personB_phone;
	
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public List<String> getID_Positive() {
		return ID_Positive;
	}
	public void setID_Positive(List<String> iD_Positive) {
		ID_Positive = iD_Positive;
	}
	public List<String> getID_Negative() {
		return ID_Negative;
	}
	public void setID_Negative(List<String> iD_Negative) {
		ID_Negative = iD_Negative;
	}
	public List<String> getPhoto_hand_ID() {
		return photo_hand_ID;
	}
	public void setPhoto_hand_ID(List<String> photo_hand_ID) {
		this.photo_hand_ID = photo_hand_ID;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getWork_industry() {
		return work_industry;
	}
	public void setWork_industry(String work_industry) {
		this.work_industry = work_industry;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	public String getHavecar() {
		return havecar;
	}
	public void setHavecar(String havecar) {
		this.havecar = havecar;
	}
	public String getHavehouse() {
		return havehouse;
	}
	public void setHavehouse(String havehouse) {
		this.havehouse = havehouse;
	}
	public String getUser_marriage() {
		return user_marriage;
	}
	public void setUser_marriage(String user_marriage) {
		this.user_marriage = user_marriage;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongAddInfoBizData [");
		if (order_no != null) {
			builder.append("order_no=");
			builder.append(order_no);
			builder.append(", ");
		}
		if (ID_Positive != null) {
			builder.append("ID_Positive=");
			builder.append(ID_Positive);
			builder.append(", ");
		}
		if (ID_Negative != null) {
			builder.append("ID_Negative=");
			builder.append(ID_Negative);
			builder.append(", ");
		}
		if (photo_hand_ID != null) {
			builder.append("photo_hand_ID=");
			builder.append(photo_hand_ID);
			builder.append(", ");
		}
		if (company_name != null) {
			builder.append("company_name=");
			builder.append(company_name);
			builder.append(", ");
		}
		if (work_industry != null) {
			builder.append("work_industry=");
			builder.append(work_industry);
			builder.append(", ");
		}
		if (addr_detail != null) {
			builder.append("addr_detail=");
			builder.append(addr_detail);
			builder.append(", ");
		}
		if (havecar != null) {
			builder.append("havecar=");
			builder.append(havecar);
			builder.append(", ");
		}
		if (havehouse != null) {
			builder.append("havehouse=");
			builder.append(havehouse);
			builder.append(", ");
		}
		if (user_marriage != null) {
			builder.append("user_marriage=");
			builder.append(user_marriage);
			builder.append(", ");
		}
		if (emergency_contact_personA_name != null) {
			builder.append("emergency_contact_personA_name=");
			builder.append(emergency_contact_personA_name);
			builder.append(", ");
		}
		if (emergency_contact_personA_phone != null) {
			builder.append("emergency_contact_personA_phone=");
			builder.append(emergency_contact_personA_phone);
			builder.append(", ");
		}
		if (emergency_contact_personB_name != null) {
			builder.append("emergency_contact_personB_name=");
			builder.append(emergency_contact_personB_name);
			builder.append(", ");
		}
		if (emergency_contact_personB_phone != null) {
			builder.append("emergency_contact_personB_phone=");
			builder.append(emergency_contact_personB_phone);
		}
		builder.append("]");
		return builder.toString();
	}
}