//package com.waterelephant.sxyDrainage.entity.xinYongGuanJia;
//
//import java.io.Serializable;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * 
// * <p>Title: XygjOperatorBase</p>  
// * <p>Description: base 用户基本信息</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Table(name = "bw_xygj_base")
//public class BwXygjBase implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 6049947754986852321L;
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	private Long order_id;   // 我方订单号
//	private String truename; // 运营商登记姓名
//	private String address;  // 身份证地址
//	private String city;  	 // 手机号归属城市
//	private String idcard;   // 运营商登记身份证
//	private String certify;  // 是否实名认证（实名认证、非实名认证、未确认）
//	private String mobile;   // 手机号
//	private String type;     // 运营商类型
//	private String token;    // 认证时的token
//	private String balance;  // 当前余额（元）
//	private String province; // 手机号归属省份
//	private String createtime;  // 认证时间
//	private String opentime;    // 开通时间
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public Long getOrder_id() {
//		return order_id;
//	}
//	public void setOrder_id(Long order_id) {
//		this.order_id = order_id;
//	}
//	public String getTruename() {
//		return truename;
//	}
//	public void setTruename(String truename) {
//		this.truename = truename;
//	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getIdcard() {
//		return idcard;
//	}
//	public void setIdcard(String idcard) {
//		this.idcard = idcard;
//	}
//	public String getCertify() {
//		return certify;
//	}
//	public void setCertify(String certify) {
//		this.certify = certify;
//	}
//	public String getMobile() {
//		return mobile;
//	}
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getToken() {
//		return token;
//	}
//	public void setToken(String token) {
//		this.token = token;
//	}
//	public String getBalance() {
//		return balance;
//	}
//	public void setBalance(String balance) {
//		this.balance = balance;
//	}
//	public String getProvince() {
//		return province;
//	}
//	public void setProvince(String province) {
//		this.province = province;
//	}
//	public String getCreatetime() {
//		return createtime;
//	}
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}
//	public String getOpentime() {
//		return opentime;
//	}
//	public void setOpentime(String opentime) {
//		this.opentime = opentime;
//	}
//	
//	
//}
