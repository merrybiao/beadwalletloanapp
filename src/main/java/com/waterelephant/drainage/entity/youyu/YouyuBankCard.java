package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 
 * 银行卡的实体
 * YouyuBankCard.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

public class YouyuBankCard {
	private String order_no	;	    //  	订单编号		
	private String user_id;		     // 身份证号			
	private String open_bank;	 //  	开户行			
	private String bank_code;	 //  	银行code		
	private String user_name;	    //  	用户名			
	private String user_mobile;	    //  	开户手机		
	private String bank_card;	 //  	银行卡号		
	private String bank_province; //  		开户行所在省
	private String bank_city;	 //  	开户行所在市	
	private String device_type;	    //  	设备类型		
	private String device_num;	 //  	设备型号		
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOpen_bank() {
		return open_bank;
	}
	public void setOpen_bank(String open_bank) {
		this.open_bank = open_bank;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_mobile() {
		return user_mobile;
	}
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	public String getBank_card() {
		return bank_card;
	}
	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getBank_province() {
		return bank_province;
	}
	public void setBank_province(String bank_province) {
		this.bank_province = bank_province;
	}
	public String getBank_city() {
		return bank_city;
	}
	public void setBank_city(String bank_city) {
		this.bank_city = bank_city;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getDevice_num() {
		return device_num;
	}
	public void setDevice_num(String device_num) {
		this.device_num = device_num;
	}
	@Override
	public String toString() {
		return "YouyuBankCard [order_no=" + order_no + ", user_id=" + user_id + ", open_bank=" + open_bank
				+ ", bank_code=" + bank_code + ", user_name=" + user_name + ", user_mobile=" + user_mobile
				+ ", bank_card=" + bank_card + ", bank_province=" + bank_province + ", bank_city=" + bank_city
				+ ", device_type=" + device_type + ", device_num=" + device_num + "]";
	}
	
	
}
