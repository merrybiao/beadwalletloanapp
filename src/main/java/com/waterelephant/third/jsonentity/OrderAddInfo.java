package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 联系人
 * Created by dengyan on 2017/7/20.
 */
public class OrderAddInfo {
	
	@JSONField(name = "order_no")
	private String orderNo;
	
    @JSONField(name = "familiy_live_type")
    private String familiyLiveType; // 居住方式 1:自有住房，无贷款、2:自有住房、有贷款、3:与父母/配偶同住、4:租房、5:公司宿舍、6:自建房7:其他 8:学生公寓

    @JSONField(name = "user_marriage")
    private String userMarriage; // 婚姻状况　1：未婚2：已婚无子女3：已婚有子女4：离异5：丧偶6：复婚

    @JSONField(name = "emergency_contact_personA_relationship")
    private String emergencyContactPersonARelationship; // 紧急联系人A 1：父母 2;配偶 3:兄弟 4: 姐妹 5 :朋友 6:父亲 7:母亲

    @JSONField(name = "emergency_contact_personA_name")
    private String emergencyContactPersonAName; // 紧急联系人A姓名

    @JSONField(name = "emergency_contact_personA_phone")
    private String emergencyContactPersonAPhone; // 紧急联系人A电话

    @JSONField(name = "emergency_contact_personB_relationship")
    private String emergencyContactPersonBRelationship; // 紧急联系人B 1：父母 2;配偶 3:兄弟 4: 姐妹 5 :朋友 6:父亲 7:母亲

    @JSONField(name = "emergency_contact_personB_name")
    private String emergencyContactPersonBName; // 紧急联系人B姓名

    @JSONField(name = "emergency_contact_personB_phone")
    private String emergencyContactPersonBPhone; // 紧急联系人B电话

    @JSONField(name = "contract1A_number")
    private String contract1ANumber; // 直系亲属联系方式A

    @JSONField(name = "contract1AName")
    private String contract1AName; // 直系亲属姓名A

    @JSONField(name = "contract1A_relationship")
    private String contract1ARelationship; // 直系亲属关系A 1：配偶；2：父亲；3：母亲；4：已成年子女

    @JSONField(name = "company_name")
    private String companyName; // 公司名称

    @JSONField(name = "company_addr_detail")
    private String companyAddrDetail; // 公司地址

    @JSONField(name = "company_number")
    private  String companyNumber; // 公司电话

    @JSONField(name = "business_name")
    private String businessName; // 经营公司名称

    @JSONField(name = "business_number")
    private String businessNumber; // 经营公司电话

    @JSONField(name = "amount_of_staff")
    private String amountOfStaff; // 员工人数

    @JSONField(name = "business_address")
    private String businessAddress; // 经营公司地址

    @JSONField(name = "time_enrollment")
    private String timeEnrollment; // 入学时间

    @JSONField(name = "school_name")
    private String schoolName; // 学校名称

    @JSONField(name = "ip_address")
    private String ipAddress; // Ip地址

    @JSONField(name = "user_email")
    private String userEmail; // 常用邮箱

    @JSONField(name = "addr_detail")
    private String addrDetail; // 居住地址
    
    @JSONField(name = "device_info_all")
    private DeviceInfoAll deviceInfoAll;
    
    @JSONField(name = "contacts")
    private Contacts contacts;
    
    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFamiliyLiveType() {
        return familiyLiveType;
    }

    public void setFamiliyLiveType(String familiyLiveType) {
        this.familiyLiveType = familiyLiveType;
    }

    public String getUserMarriage() {
        return userMarriage;
    }

    public void setUserMarriage(String userMarriage) {
        this.userMarriage = userMarriage;
    }

    public String getEmergencyContactPersonARelationship() {
        return emergencyContactPersonARelationship;
    }

    public void setEmergencyContactPersonARelationship(String emergencyContactPersonARelationship) {
        this.emergencyContactPersonARelationship = emergencyContactPersonARelationship;
    }

    public String getEmergencyContactPersonAName() {
        return emergencyContactPersonAName;
    }

    public void setEmergencyContactPersonAName(String emergencyContactPersonAName) {
        this.emergencyContactPersonAName = emergencyContactPersonAName;
    }

    public String getEmergencyContactPersonAPhone() {
        return emergencyContactPersonAPhone;
    }

    public void setEmergencyContactPersonAPhone(String emergencyContactPersonAPhone) {
        this.emergencyContactPersonAPhone = emergencyContactPersonAPhone;
    }

    public String getEmergencyContactPersonBRelationship() {
        return emergencyContactPersonBRelationship;
    }

    public void setEmergencyContactPersonBRelationship(String emergencyContactPersonBRelationship) {
        this.emergencyContactPersonBRelationship = emergencyContactPersonBRelationship;
    }

    public String getEmergencyContactPersonBName() {
        return emergencyContactPersonBName;
    }

    public void setEmergencyContactPersonBName(String emergencyContactPersonBName) {
        this.emergencyContactPersonBName = emergencyContactPersonBName;
    }

    public String getEmergencyContactPersonBPhone() {
        return emergencyContactPersonBPhone;
    }

    public void setEmergencyContactPersonBPhone(String emergencyContactPersonBPhone) {
        this.emergencyContactPersonBPhone = emergencyContactPersonBPhone;
    }

    public String getContract1ANumber() {
        return contract1ANumber;
    }

    public void setContract1ANumber(String contract1ANumber) {
        this.contract1ANumber = contract1ANumber;
    }

    public String getContract1AName() {
        return contract1AName;
    }

    public void setContract1AName(String contract1AName) {
        this.contract1AName = contract1AName;
    }

    public String getContract1ARelationship() {
        return contract1ARelationship;
    }

    public void setContract1ARelationship(String contract1ARelationship) {
        this.contract1ARelationship = contract1ARelationship;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddrDetail() {
        return companyAddrDetail;
    }

    public void setCompanyAddrDetail(String companyAddrDetail) {
        this.companyAddrDetail = companyAddrDetail;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getAmountOfStaff() {
        return amountOfStaff;
    }

    public void setAmountOfStaff(String amountOfStaff) {
        this.amountOfStaff = amountOfStaff;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getTimeEnrollment() {
        return timeEnrollment;
    }

    public void setTimeEnrollment(String timeEnrollment) {
        this.timeEnrollment = timeEnrollment;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public DeviceInfoAll getDeviceInfoAll() {
		return deviceInfoAll;
	}

	public void setDeviceInfoAll(DeviceInfoAll deviceInfoAll) {
		this.deviceInfoAll = deviceInfoAll;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}
    
    
}
