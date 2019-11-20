package sxdzsmsTest.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class SmsContent {
	
	@JSONField(name="phone",ordinal =2)
	private String phone;
	
	@JSONField(name="msg",ordinal =1)
	private String msg;
	
	@JSONField(name="type",ordinal =4)
	private String type;
	
	@JSONField(name="businessScenario",ordinal =3)
	private String businessScenario;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessScenario() {
		return businessScenario;
	}

	public void setBusinessScenario(String businessScenario) {
		this.businessScenario = businessScenario;
	}
	
	

}
